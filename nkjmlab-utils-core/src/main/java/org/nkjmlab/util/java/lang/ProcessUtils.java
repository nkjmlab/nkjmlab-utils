package org.nkjmlab.util.java.lang;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.nkjmlab.util.java.function.Try;

public class ProcessUtils {
  private static final org.nkjmlab.util.java.logging.SimpleLogger log =
      org.nkjmlab.util.java.logging.LogManager.createLogger();

  public static boolean isWindowsOs() {
    return System.getProperty("os.name").toLowerCase().indexOf("windows") > -1;
  }

  public static boolean isJapaneseOs() {
    return Locale.getDefault() == Locale.JAPANESE || Locale.getDefault() == Locale.JAPAN;
  }

  /**
   * Reads read standard output after process finish.
   *
   * @param process
   * @return
   */
  private static String readStandardOutputAfterProcessFinish(Process process) {
    try {
      byte[] b = process.getInputStream().readAllBytes();
      return new String(
          b, isWindowsOs() && isJapaneseOs() ? "MS932" : StandardCharsets.UTF_8.toString());
    } catch (IOException e) {
      throw Try.rethrow(e);
    }
  }

  public static Optional<Integer> getProcessIdBidingPort(int port) {
    try {
      List<String> command =
          isWindowsOs()
              ? List.of("cmd", "/c", "netstat", "-ano")
              : List.of("lsof", "-i", ":" + port, "-s", "TCP:LISTEN", "-t");

      log.info("command for check process is [{}]", command);

      ProcessBuilder pb = new ProcessBuilder(command);
      pb.redirectErrorStream(true);
      Process proc = pb.start();
      String[] lines = readStandardOutputAfterProcessFinish(proc).split(System.lineSeparator());
      return isWindowsOs() ? procNetstat(lines, port) : procLsof(lines);
    } catch (Exception e) {
      throw Try.rethrow(e);
    }
  }

  static Optional<Integer> procNetstat(String[] lines, int port) {
    List<NetstatLine> tmp =
        Arrays.stream(lines)
            .filter(l -> l.trim().split("\\s+").length == 5)
            .map(l -> toNetstatLine(l))
            .toList();
    return tmp.stream()
        .filter(n -> n.localAddress().contains(":" + port) && n.state.contains("LISTENING"))
        .findAny()
        .map(n -> n.pid());
  }

  static Optional<Integer> procLsof(String[] lines) {
    return lines.length == 0 || lines[0].length() == 0
        ? Optional.empty()
        : Optional.of(Integer.valueOf(lines[0]));
  }

  private static NetstatLine toNetstatLine(String line) {
    String[] t = line.trim().split("\\s+");
    return new NetstatLine(t[0], t[1], t[2], t[3], Integer.parseInt(t[4]));
  }

  private record NetstatLine(
      String protocol, String localAddress, String externalAddress, String state, int pid) {}

  /**
   * If the process binding the the given port, it will be stopped.
   *
   * @param port
   * @return
   */
  public static boolean stopProcessBindingPortIfExists(int port) {
    return stopProcessBindingPortIfExists(port, 10, TimeUnit.SECONDS);
  }

  public static boolean stopProcessBindingPortIfExists(int port, long timeout, TimeUnit unit) {
    return getProcessIdBidingPort(port)
        .map(
            pid -> {
              try {
                log.info(
                    "process [{}] is binding port [{}]. try killing by ProcessHandle.destory()",
                    pid,
                    port);
                Optional<ProcessHandle> optProc = ProcessHandle.of(Long.valueOf(pid));
                if (optProc.isEmpty()) {
                  return true;
                }
                ProcessHandle proc = optProc.get();
                proc.destroy();

                long start = System.currentTimeMillis();

                while (proc.isAlive()) {
                  long durationInMillis = System.currentTimeMillis() - start;
                  if (durationInMillis > TimeUnit.MICROSECONDS.convert(timeout, unit)) {
                    log.error("Process [{}] is active yet.");
                    return false;
                  }
                  TimeUnit.SECONDS.sleep(1);
                }
                return true;
              } catch (InterruptedException e) {
                throw Try.rethrow(e);
              }
            })
        .orElse(false);
  }

  public static boolean killForceProcessBindingPortIfExists(int port) {
    return getProcessIdBidingPort(port)
        .map(
            pid -> {
              try {
                List<String> command =
                    isWindowsOs()
                        ? List.of("taskkill", "/F", "/T", "/PID", pid.toString())
                        : List.of("kill", "-9", pid.toString());
                log.info("process [{}] is binding port [{}]. try killing {}", pid, port, command);
                ProcessBuilder pb = new ProcessBuilder(command);
                pb.start().waitFor();
              } catch (InterruptedException | IOException e) {
                throw Try.rethrow(e);
              }
              // log.info("Success to stop the process [{}] biding port :[{}].", pid, port);
              return true;
            })
        .orElse(false);
  }
}

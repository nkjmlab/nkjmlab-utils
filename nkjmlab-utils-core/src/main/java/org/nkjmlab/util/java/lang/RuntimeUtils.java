package org.nkjmlab.util.java.lang;

import java.io.IOException;
import org.nkjmlab.util.java.logging.SimpleLogger;

public class RuntimeUtils {
  private static final org.nkjmlab.util.java.logging.SimpleLogger log =
      org.nkjmlab.util.java.logging.LogManager.createLogger();

  public static String getMemoryUsege() {
    final Runtime runtime = Runtime.getRuntime();
    return String.format("{total: %d MB, free: %d MB, used: %d MB, max: %d MB}",
        (runtime.totalMemory() / 1000 / 1000), (runtime.freeMemory() / 1000 / 1000),
        ((runtime.totalMemory() - runtime.freeMemory()) / 1000 / 1000),
        (runtime.maxMemory() / 1000 / 1000));
  }

  public static String getPid() {
    return java.lang.management.ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
  }


  public static void callSystemExitWhenReadingSystemInput() {
    try {
      System.out.println("Press ENTER key to call System.exit() in the console ... > ");
      System.in.read();
    } catch (IOException e) {
      log.error(e, e);
    } finally {
      log.info("System.exit(0) call immediately.");
      System.exit(0);
    }
  }

  public static void addShutdownLog(SimpleLogger log, String msg, Object... params) {
    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      String message = "[SHUTDOWN HOOK]" + ParameterizedStringFormatter.DEFAULT.format(msg, params);
      log.error(message);
      System.err.println(message); // This line is important for flash Logger I guess.
      // org.apache.logging.log4j.LogManager.shutdown();
    }));
  }


}

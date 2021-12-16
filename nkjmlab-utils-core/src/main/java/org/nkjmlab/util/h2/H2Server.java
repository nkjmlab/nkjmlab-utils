package org.nkjmlab.util.h2;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.sql.DataSource;
import org.h2.server.web.WebServer;
import org.h2.tools.Server;
import org.nkjmlab.util.java.function.Try;
import org.nkjmlab.util.java.lang.ParameterizedStringUtils;

public class H2Server {
  private static final org.nkjmlab.util.java.logging.SimpleLogger log =
      org.nkjmlab.util.java.logging.LogManager.createLogger();

  private static final String DEFALUT_H2_CLASS_PATH = getClassPathOf("^h2-.*.jar$");
  private static final int DEFAULT_TCP_PORT = 9092;
  private static final String TCP_PASSWORD = "TCP_PASSWORD";
  private static final int DEFAULT_WEB_PORT = 8082;
  private static final String WEB_ADMIN_PASSWORD = "WEB_ADMIN_PASSWORD";
  private static final List<String> DEFAULT_H2_SERVER_OPTIONS = List.of("-ifNotExists");

  private static String getClassPathOf(String regex) {
    String cps = System.getProperty("java.class.path");
    List<String> h2Jars = Arrays.stream(cps.split(File.pathSeparator))
        .filter(jarNames -> new File(jarNames).getName().matches(regex))
        .collect(Collectors.toList());
    if (h2Jars.size() == 1) {
      return h2Jars.get(0);
    } else {
      throw new IllegalStateException(
          ParameterizedStringUtils.newString("H2 jar should be one in class path ({})", cps));
    }
  }

  private static boolean isActive(int port) {
    try (ServerSocket socket = new ServerSocket(port)) {
      return false;
    } catch (IOException e) {
      return true;
    }
  }


  /**
   * Creates a new Web console server thread. If webDaemon is true, the Web console server shutdown
   * after the main thread finishes.
   *
   * @param webDaemon
   * @return
   */
  public static WebServer createWebConsoleServerThread(boolean webDaemon) {
    try {
      Server server =
          Server.createWebServer(webDaemon ? new String[] {"-webPort", "0", "-webDaemon"}
              : new String[] {"-webPort", "0"});
      server.start();
      log.info("H2 Web Server is start at {}", server.getURL());
      WebServer webServer = (WebServer) server.getService();
      return webServer;
    } catch (SQLException e) {
      throw Try.rethrow(e);
    }
  }

  /**
   * Open a new browser tab or window.
   *
   * @param webServer
   * @param conn
   */
  public static void openBrowser(WebServer webServer, Connection conn) {
    try {
      webServer.addSession(conn);
      String url = webServer.addSession(conn);
      Server.openBrowser(url);
      log.info("Database open on browser = {}", url);
    } catch (Exception e) {
      log.error(e, e);
    }
  }

  /**
   * Open a new browser tab or window.
   *
   * @param webServer
   * @param dataSource
   */
  public static void openBrowser(WebServer webServer, DataSource dataSource) {
    Try.runOrElseThrow(() -> openBrowser(webServer, dataSource.getConnection()), Try::rethrow);
  }


  /**
   * Shutdowns default TCP server binding on default port (9092).
   */
  public static void shutdownTcpServer() {
    shutdownTcpServer(DEFAULT_TCP_PORT, TCP_PASSWORD, Long.MAX_VALUE, TimeUnit.SECONDS);
  }

  /**
   *
   * @param tcpPassword is a password of tcpPassword Of H2 Console server. It is not password of
   *        Database administrator.
   */
  public static void shutdownTcpServer(int tcpPort, String tcpPassword, long timeout,
      TimeUnit unit) {
    if (!isActive(tcpPort)) {
      log.info("H2 TCP server is not active.");
      return;
    }
    try {
      log.info("H2 TCP server will shutdown ...");
      Server.shutdownTcpServer("tcp://localhost:" + tcpPort, tcpPassword, false, false);
      long start = System.currentTimeMillis();
      while (isActive(tcpPort)) {
        long durationInMilli = System.currentTimeMillis() - start;
        if (durationInMilli > TimeUnit.MICROSECONDS.convert(timeout, unit)) {
          break;
        }
        TimeUnit.SECONDS.sleep(1);
      }
    } catch (SQLException | InterruptedException e) {
      log.error(e.getMessage());
    }
    if (isActive(tcpPort)) {
      log.warn("H2 TCP server is still active.");
    } else {
      log.info("H2 TCP server stopped.");
    }

  }

  /**
   * Starts H2 server process and wait for start server.
   */

  public static void startTcpAndWebConsoleServerProcessAndWaitFor() {
    startTcpAndWebConsoleServerProcessAndWaitFor(DEFAULT_TCP_PORT, TCP_PASSWORD, DEFAULT_WEB_PORT,
        WEB_ADMIN_PASSWORD, Long.MAX_VALUE, TimeUnit.SECONDS);
  }


  /**
   * Starts H2 server process and wait.
   *
   * @param tcpPort
   * @param tcpPassword
   * @param webPort
   * @param webAdminPassword
   * @param timeout
   * @param unit
   * @param options
   */
  public static void startTcpAndWebConsoleServerProcessAndWaitFor(int tcpPort, String tcpPassword,
      int webPort, String webAdminPassword, long timeout, TimeUnit unit, String... options) {
    startTcpAndWebConsoleServerProcessAndWaitFor(DEFALUT_H2_CLASS_PATH, tcpPort, tcpPassword,
        webPort, webAdminPassword, timeout, unit, options);
  }

  public static void startTcpAndWebConsoleServerProcessAndWaitFor(String h2ClassPath, int tcpPort,
      String tcpPassword, int webPort, String webAdminPassword, long timeout, TimeUnit unit,
      String... options) {

    if (isActive(tcpPort)) {
      log.info("H2 TCP server has been already activated at http://localhost:{}", tcpPort);
    }
    if (isActive(webPort)) {
      log.info("H2 Web console server has been already activated at http://localhost:{}", webPort);
    }

    if (isActive(tcpPort) && isActive(webPort)) {
      return;
    }


    List<String> _args =
        new ArrayList<>(List.of("java", "-cp", h2ClassPath, "org.h2.tools.Server"));
    _args.addAll(DEFAULT_H2_SERVER_OPTIONS);

    if (!isActive(tcpPort)) {
      _args.addAll(List.of("-tcp", "-tcpPort", tcpPort + "", "-tcpPassword", tcpPassword));
    }
    if (!isActive(webPort)) {
      _args
          .addAll(List.of("-web", "-webPort", webPort + "", "-webAdminPassword", webAdminPassword));
    }

    List<String> args =
        Stream.concat(_args.stream(), Arrays.stream(options)).collect(Collectors.toList());


    try {
      ProcessBuilder pb = new ProcessBuilder(args.toArray(String[]::new));
      pb.redirectErrorStream(true);
      log.debug(
          "[TCP available={}, Web available={}] Try to start H2 server and wait [{} {}] at the longest",
          isActive(tcpPort), isActive(webPort), timeout, unit);
      // log.debug("[TCP available={}, Web available={}] Try to start H2 server and wait [{} {}] at
      // the longest, command=
      // {}", isActive(tcpPort), isActive(webPort), timeout, unit, pb.command());
      pb.start();
      long start = System.currentTimeMillis();

      while (!isActive(tcpPort) || !isActive(webPort)) {
        long durationInMilli = System.currentTimeMillis() - start;
        if (durationInMilli > TimeUnit.MICROSECONDS.convert(timeout, unit)) {
          break;
        }
        TimeUnit.SECONDS.sleep(1);
      }
      if (isActive(tcpPort)) {
        log.info("H2 Tcp server is available at http://localhost:{}", tcpPort);
      } else {
        log.error("Fail to start or has not started h2 TCP server yet.");
      }
      if (isActive(webPort)) {
        log.info("H2 Web console server is available at http://localhost:{}", webPort);
      } else {
        log.error("Fail to start or has not started H2 Web console server yet.");
      }

    } catch (IOException | InterruptedException e) {
      throw Try.rethrow(e);
    }

  }



}

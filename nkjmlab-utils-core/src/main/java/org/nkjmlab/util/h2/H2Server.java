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

public class H2Server {
  private static final org.nkjmlab.util.java.logging.Logger log =
      org.nkjmlab.util.java.logging.LogManager.getLogger();

  private static final String DEFALUT_H2_CLASS_PATH = getClassPathOf("^h2-.*.jar$");
  private static final int DEFAULT_TCP_PORT = 9092;
  private static final String TCP_PASSWORD = "TCP_PASSWORD";
  private static final int DEFAULT_WEB_PORT = 8082;
  private static final String WEB_ADMIN_PASSWORD = "WEB_ADMIN_PASSWORD";

  private static final long DEFAULT_START_WAIT_TIME = 4;
  private static final long DEFAULT_SHUTDOWN_WAIT_TIME = 2;
  private static final TimeUnit DEFAULT_TIME_UNIT = TimeUnit.SECONDS;

  private static String getClassPathOf(String regex) {
    for (String cp : System.getProperty("java.class.path").split(File.pathSeparator)) {
      if (new File(cp).getName().matches(regex)) {
        return cp;
      }
    }
    throw new RuntimeException(regex + " not found");
  }

  private static boolean isActive(int port) {
    try (ServerSocket socket = new ServerSocket(port)) {
      return false;
    } catch (IOException e) {
      return true;
    }
  }

  public static void openBrowser(Connection conn, boolean keepAlive) {
    try {
      Server server = Server.createWebServer(keepAlive ? new String[] {"-webPort", "0"}
          : new String[] {"-webPort", "0", "-webDaemon"});
      server.start();
      log.info("H2 Temporal Web console is start at {}", server.getURL());

      WebServer webServer = (WebServer) server.getService();
      webServer.addSession(conn);
      String url = webServer.addSession(conn);
      Server.openBrowser(url);
      log.info("Database open on browser = {}", url);
    } catch (Exception e) {
      log.error(e, e);
    }
  }


  public static void openBrowser(DataSource dataSource, boolean keepAlive) {
    Try.runOrThrow(() -> openBrowser(dataSource.getConnection(), keepAlive), Try::rethrow);
  }

  public static void shutdownTcpServer() {
    shutdownTcpServer(DEFAULT_TCP_PORT, TCP_PASSWORD, DEFAULT_SHUTDOWN_WAIT_TIME,
        DEFAULT_TIME_UNIT);
  }

  /**
   *
   * @param tcpPassword is a password of tcpPassword Of H2 Server. It is not password of DB admin.
   */
  public static void shutdownTcpServer(int tcpPort, String tcpPassword, long wait, TimeUnit unit) {
    if (!isActive(tcpPort)) {
      log.info("H2 server is not active.");
      return;
    }
    try {
      log.info("Try to start shutdown h2 server...");
      Server.shutdownTcpServer("tcp://localhost:" + tcpPort, tcpPassword, false, false);
      unit.sleep(wait);
    } catch (SQLException | InterruptedException e) {
      log.error(e.getMessage());
    }
    if (isActive(tcpPort)) {
      log.warn("H2 server is still active.");
    } else {
      log.info("H2 server is stopped.");
    }

  }


  public static void startServerProcessAndWait() {
    startServerProcessAndWaitFor(DEFAULT_TCP_PORT, TCP_PASSWORD, DEFAULT_WEB_PORT,
        WEB_ADMIN_PASSWORD);
  }



  public static void startServerProcessAndWaitFor(int tcpPort, String tcpPassword, int webPort,
      String webAdminPassword, String... options) {
    startServerProcessAndWaitFor(DEFALUT_H2_CLASS_PATH, tcpPort, tcpPassword, webPort,
        webAdminPassword, DEFAULT_START_WAIT_TIME, DEFAULT_TIME_UNIT, options);
  }


  public static void startServerProcessAndWaitFor(long waitTime, TimeUnit unit) {
    startServerProcessAndWaitFor(DEFALUT_H2_CLASS_PATH, DEFAULT_TCP_PORT, TCP_PASSWORD,
        DEFAULT_WEB_PORT, WEB_ADMIN_PASSWORD, waitTime, unit);
  }

  public static void startServerProcessAndWaitFor(String h2ClassPath, int tcpPort,
      String tcpPassword, int webPort, String webAdminPassword, long timeout, TimeUnit unit,
      String... options) {

    if (isActive(tcpPort)) {
      log.info("H2 TCP server has been already activated at http://localhost:{}.", tcpPort);
    }
    if (isActive(webPort)) {
      log.info("H2 Web console server has been already activated at http://localhost:{}.", webPort);
    }

    if (isActive(tcpPort) && isActive(webPort)) {
      return;
    }


    List<String> _args =
        new ArrayList<>(List.of("java", "-cp", h2ClassPath, "org.h2.tools.Server", "-ifNotExists"));

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
      log.info("Try to start H2 server and wait [{} {}] at the longest, command= {}", timeout, unit,
          pb.command());
      Process process = pb.start();
      process.waitFor(timeout, unit);

      if (isActive(tcpPort)) {
        log.info("H2 Tcp server is active at http://localhost:{}", tcpPort);
      } else {
        log.error("Fail to start h2 tcp server.");
      }
      if (isActive(webPort)) {
        log.info("H2 Web console server is active at http://localhost:{}", webPort);
      } else {
        log.error("Fail to start h2 web console server.");
      }
    } catch (IOException | InterruptedException e) {
      log.error(e, e);
    }

  }


}

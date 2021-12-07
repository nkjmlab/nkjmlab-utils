package org.nkjmlab.util.java.logging;

import org.nkjmlab.util.java.function.Try;

public class LogManager {
  static {
    isEnableLog4jApi();
  }
  private static final boolean enableLog4jCore = isEnableLog4jCore();

  public static Logger getLogger() {
    return enableLog4jCore ? new Log4jLogger() : NopLogger.INSTANCE;
  }

  private static boolean isEnableLog4jApi() {
    boolean ret = Try.getOrElse(() -> {
      Class.forName("org.apache.logging.log4j.Logger");
      return true;
    }, false);
    if (!ret) {
      System.err.println("ERROR " + LogManager.class.getName()
          + " could not find a logging implementation of log4j2. Please add log4j-core to the classpath. "
          + NopLogger.class.getSimpleName() + " (No operation logger) is used...");
    }
    return ret;
  }

  private static boolean isEnableLog4jCore() {
    return Try.getOrElse(() -> {
      Class.forName("org.apache.logging.log4j.core.Logger");
      return true;
    }, false);
  }

  public static Logger getLogger(org.apache.logging.log4j.Logger log) {
    return new Log4jLogger(log);
  }


}

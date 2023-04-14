package org.nkjmlab.util.java.logging;

import org.nkjmlab.util.java.function.Try;
import org.nkjmlab.util.log4j.Log4jLogger;

public class LogManager {
  static {
    isEnableLog4jApi();
  }
  private static final boolean enableLog4jCore = isEnableLog4jCore();

  private static boolean isEnableLog4jApi() {
    boolean ret = Try.getOrElse(() -> {
      Class.forName("org.apache.logging.log4j.Logger");
      return true;
    }, false);
    if (!ret) {
      System.err.println("WARNING " + LogManager.class.getName()
          + " could not find a logging implementation of log4j2. Please add log4j-core to the classpath. "
          + "No operation logger (" + NopLogger.class.getSimpleName() + ")  will be used...");
    }
    return ret;
  }

  private static boolean isEnableLog4jCore() {
    return Try.getOrElse(() -> {
      Class.forName("org.apache.logging.log4j.core.Logger");
      return true;
    }, false);
  }

  public static SimpleLogger createLogger() {
    return enableLog4jCore ? new Log4jLogger() : NopLogger.INSTANCE;
  }

  public static SimpleLogger createLogger(org.apache.logging.log4j.Logger log) {
    return new Log4jLogger(log);
  }

  public static SimpleLogger createLogger(String name) {
    return new Log4jLogger(name);
  }


}

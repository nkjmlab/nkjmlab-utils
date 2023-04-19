package org.nkjmlab.util.java.logging;

import org.nkjmlab.util.java.function.Try;

public class LogManager {
  private static final boolean enableLog4jCore = isEnableLog4jCore();

  private static boolean isEnableLog4jCore() {
    String ORG_APACHE_LOGGING_LOG4J_CORE_LOGGER = "org.apache.logging.log4j.core.Logger";
    boolean ret = Try.getOrElse(() -> {
      Class.forName(ORG_APACHE_LOGGING_LOG4J_CORE_LOGGER);
      return true;
    }, false);
    if (!ret) {
      System.err.println("nkjmlab-utils-core: WARNING " + "Failed to load class '"
          + ORG_APACHE_LOGGING_LOG4J_CORE_LOGGER
          + "'. If you need the log of nkjmlab-utils-core library, add log4j-core to the classpath.");
      System.err.println("nkjmlab-utils-core: WARNING " + "No operation logger will be used.");
    }
    return ret;
  }

  public static SimpleLogger createLogger() {
    return enableLog4jCore ? new org.nkjmlab.util.log4j.Log4jLogger() : NopLogger.INSTANCE;
  }

  public static SimpleLogger createLogger(org.apache.logging.log4j.Logger log) {
    return enableLog4jCore ? new org.nkjmlab.util.log4j.Log4jLogger(log) : NopLogger.INSTANCE;
  }

  public static SimpleLogger createLogger(String name) {
    return enableLog4jCore ? new org.nkjmlab.util.log4j.Log4jLogger(name) : NopLogger.INSTANCE;
  }


}

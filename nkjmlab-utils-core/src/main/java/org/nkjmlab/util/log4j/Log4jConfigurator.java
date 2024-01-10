package org.nkjmlab.util.log4j;

import java.net.URISyntaxException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.nkjmlab.util.java.function.Try;

public class Log4jConfigurator {
  private static final org.nkjmlab.util.java.logging.SimpleLogger log =
      org.nkjmlab.util.java.logging.LogManager.createLogger();

  private static volatile boolean override = true;

  public static void setOverride(boolean override) {
    Log4jConfigurator.override = override;
  }

  public static void overrideByBundledXmlConfiguration(Level level, boolean lineInfo) {
    if (!override) {
      return;
    }
    String xmlLocation = "log4j2-" + level + "-" + (lineInfo ? "loc" : "noloc") + ".xml";

    try {
      getContext().setConfigLocation(Log4jConfigurator.class.getResource(xmlLocation).toURI());
      log.info(
          "Reconfigure log4j2 by xml is success. level={}, lineInfo={}, xml={}",
          level,
          lineInfo,
          xmlLocation);
    } catch (URISyntaxException e) {
      Try.rethrow(e);
    }
  }

  private static LoggerContext getContext() {
    return (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false);
  }

  public static void setLogLevel(Class<?> clazz, Level logLevel) {
    LoggerContext ctx = getContext();
    ctx.getConfiguration().getLoggerConfig(clazz.getName()).setLevel(logLevel);
  }
}

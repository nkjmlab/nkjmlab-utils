package org.nkjmlab.util.log4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.nkjmlab.util.java.function.Try;
import org.nkjmlab.util.java.net.UrlUtils;

public class Log4jConfigurator {
  private static final org.nkjmlab.util.java.logging.SimpleLogger log =
      org.nkjmlab.util.java.logging.LogManager.createLogger();

  private static volatile boolean override = true;

  public static void setOverride(boolean override) {
    Log4jConfigurator.override = override;
  }

  public static void overrideByXmlConfiguration(URI resourceUri) {
    if (!override) {
      return;
    }
    getContext().setConfigLocation(resourceUri);
  }

  public static void overrideByBundledXmlConfiguration(Level level, boolean lineInfo) {
    if (!override) {
      return;
    }
    String xmlLocation = getBundledXmlConfiguration(level, lineInfo);
    URL url = UrlUtils.of(xmlLocation);

    try (InputStream is = url.openStream()) {
      overrideByXmlConfiguration(url.toURI());
      log.info("Reconfigure log4j2 is success. level={}, lineInfo={},xml={}", level, lineInfo,
          xmlLocation);
    } catch (URISyntaxException | IOException e) {
      Try.rethrow(e);
    }
  }


  private static String getBundledXmlConfiguration(Level level, boolean lineInfo) {
    String lineInfoStr = lineInfo ? "loc" : "noloc";
    String codeSourceLocation =
        Log4jConfigurator.class.getProtectionDomain().getCodeSource().getLocation().toString();
    String resourceRootLocation =
        codeSourceLocation.endsWith(".jar") ? "jar:" + codeSourceLocation + "!/"
            : codeSourceLocation;
    String xmlLocation = resourceRootLocation + "log4j2" + "-" + level + "-" + lineInfoStr + ".xml";
    return xmlLocation;
  }

  private static LoggerContext getContext() {
    return (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false);
  }

  public static void setLogLevel(Class<?> clazz, Level logLevel) {
    LoggerContext ctx = getContext();
    ctx.getConfiguration().getLoggerConfig(clazz.getName()).setLevel(logLevel);
  }



}

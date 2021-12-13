package org.nkjmlab.util.log4j;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.function.Consumer;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.nkjmlab.util.java.lang.MethodInvokerInfoUtils;

public class Log4jConfigurator {
  private static final org.apache.logging.log4j.Logger log =
      org.apache.logging.log4j.LogManager.getLogger();
  private static volatile boolean override = true;

  public static void setOverride(boolean override) {
    Log4jConfigurator.override = override;
  }

  public static void overrideByBundledConfiguration(Level level, boolean lineInfo) {
    if (!override) {
      return;
    }

    log.trace("Reconfigure log4j2, level={}, lineInfo={}", level, lineInfo);
    LoggerContext context =
        (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false);
    String lineInfoStr = lineInfo ? "loc" : "noloc";

    log.trace("For debug, ", Log4jConfigurator.class.getResource("/log4j2.xml"));

    String xmlLocation =
        Log4jConfigurator.class.getProtectionDomain().getCodeSource().getLocation().toString();
    xmlLocation = xmlLocation.endsWith(".jar") ? "jar:" + xmlLocation + "!/" : xmlLocation;
    xmlLocation = xmlLocation + "log4j2" + "-" + level + "-" + lineInfoStr + ".xml";
    log.trace("try reading xml {}", xmlLocation);

    try {
      URL url = new URL(xmlLocation);
      log.trace("try reading xml {}", xmlLocation);
      try (InputStream is = url.openStream()) {
        if (is == null) {
          throw new IOException();
        }
        context.setConfigLocation(url.toURI());
        log.info("Reconfigure log4j is success. level={}, lineInfo={},xml={}", level, lineInfo,
            xmlLocation);
      } catch (URISyntaxException e) {
        log.warn("{} is invalid uri.", xmlLocation);
        log.warn(e, e);
      } catch (IOException e) {
        log.warn("{} can not be read.", xmlLocation);
        log.warn(e, e);
      }
    } catch (MalformedURLException e) {
      log.error(e, e);
    }

  }

  public static void overrideByConfiguration(Class<?> clazz, String pathToResource) {
    if (!override) {
      return;
    }
    LoggerContext context =
        (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false);
    try {
      context.setConfigLocation(clazz.getResource(pathToResource).toURI());
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
  }

  public static void overrideByConfiguration(File path) {
    if (!override) {
      return;
    }
    LoggerContext context =
        (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false);
    context.setConfigLocation(path.toURI());
  }


  public static void configureLogger(String name, Consumer<LoggerConfig> configurator) {
    LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
    configurator.accept(ctx.getConfiguration().getLoggerConfig(name));
    ctx.updateLoggers();
  }

  public static void configureLogger(Class<?> clazz, Consumer<LoggerConfig> configure) {
    configureLogger(clazz.getCanonicalName(), configure);
  }

  public static void setLogLevel(Class<?> clazz, Level logLevel) {
    configureLogger(clazz.getCanonicalName(),
        conf -> conf.setLevel(org.apache.logging.log4j.Level.WARN));
  }

  public static void setLogLevel(String name, Level logLevel) {
    configureLogger(name, conf -> conf.setLevel(org.apache.logging.log4j.Level.WARN));
  }


  public static void configureLogger(Consumer<LoggerConfig> configure) {
    String name = MethodInvokerInfoUtils.getInvokerClassName(4, new Throwable().getStackTrace());
    configureLogger(name, configure);
  }

  public static void setLogLevel(Level logLevel) {
    String name = MethodInvokerInfoUtils.getInvokerClassName(4, new Throwable().getStackTrace());
    configureLogger(name, conf -> conf.setLevel(org.apache.logging.log4j.Level.WARN));
  }



}

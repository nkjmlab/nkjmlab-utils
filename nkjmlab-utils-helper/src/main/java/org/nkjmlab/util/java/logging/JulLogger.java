package org.nkjmlab.util.java.logging;

import static org.nkjmlab.util.java.logging.Logger.Category.*;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import org.nkjmlab.util.java.lang.MethodInvokerInfoUtils;
import org.nkjmlab.util.java.lang.StringUtils;

public class JulLogger implements org.nkjmlab.util.java.logging.Logger {

  private static final java.util.logging.Logger defaultLogger = createDefaultLogger();

  private static Logger createDefaultLogger() {
    Logger logger = java.util.logging.Logger.getLogger(JulLogger.class.getName());
    logger.setLevel(Level.FINE);
    ConsoleHandler consoleHandler = new ConsoleHandler();
    consoleHandler.setFormatter(new Formatter() {
      @Override
      public String format(LogRecord record) {
        return formatMessage(record) + System.lineSeparator();
      }
    });
    consoleHandler.setLevel(Level.FINE);
    logger.addHandler(consoleHandler);
    return logger;
  }

  private final java.util.logging.Logger logger;

  public JulLogger(java.util.logging.Logger logger) {
    this.logger = logger;
  }

  public static org.nkjmlab.util.java.logging.Logger getLogger() {
    return new JulLogger(defaultLogger);
  }

  @Override
  public void trace(String format, Object... params) {
    this.logger.finer(
        MethodInvokerInfoUtils.getInvokerSummary(3, TRACE.name(), new Throwable().getStackTrace())
            + " " + StringUtils.format(format, params));
  }

  @Override
  public void debug(String format, Object... params) {
    this.logger.fine(
        MethodInvokerInfoUtils.getInvokerSummary(3, DEBUG.name(), new Throwable().getStackTrace())
            + " " + StringUtils.format(format, params));
  }


  @Override
  public void info(String format, Object... params) {
    this.logger.info(
        MethodInvokerInfoUtils.getInvokerSummary(3, INFO.name(), new Throwable().getStackTrace())
            + " " + StringUtils.format(format, params));
  }

  @Override
  public void warn(String format, Object... params) {
    this.logger.warning(
        MethodInvokerInfoUtils.getInvokerSummary(3, WARN.name(), new Throwable().getStackTrace())
            + " " + StringUtils.format(format, params));
  }

  @Override
  public void error(String format, Object... params) {
    this.logger.severe(
        MethodInvokerInfoUtils.getInvokerSummary(3, ERROR.name(), new Throwable().getStackTrace())
            + " " + StringUtils.format(format, params));
  }

  @Override
  public void error(Throwable message, Throwable throwable) {
    this.logger.severe(
        MethodInvokerInfoUtils.getInvokerSummary(3, ERROR.name(), new Throwable().getStackTrace())
            + " " + StringUtils.format(message != null ? message.toString()
                : "" + throwable != null ? throwable.toString() : ""));
  }

  @Override
  public void warn(Throwable message, Throwable throwable) {
    this.logger.warning(
        MethodInvokerInfoUtils.getInvokerSummary(3, ERROR.name(), new Throwable().getStackTrace())
            + " " + StringUtils.format(message != null ? message.toString()
                : "" + throwable != null ? throwable.toString() : ""));

  }

}

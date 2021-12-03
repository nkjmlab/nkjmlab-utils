package org.nkjmlab.util.java.logging;

import java.util.Arrays;
import org.apache.logging.log4j.Level;

public class Log4jLogger implements Logger {

  private org.apache.logging.log4j.Logger logger;

  public Log4jLogger() {
    this(org.apache.logging.log4j.LogManager.getLogger());
  }

  public Log4jLogger(org.apache.logging.log4j.Logger log) {
    this.logger = log;
  }

  @Override
  public void trace(String format, Object... params) {
    this.logger.trace(format, params);
  }

  @Override
  public void debug(String format, Object... params) {
    this.logger.printf(Level.DEBUG,
        "%n  " + getCaller(new Throwable().getStackTrace()) + " " + format, params);
  }

  @Override
  public void info(String format, Object... params) {
    this.logger.printf(Level.INFO,
        "%n  " + getCaller(new Throwable().getStackTrace()) + " " + format, params);
  }

  @Override
  public void warn(String format, Object... params) {
    this.logger.printf(Level.WARN,
        "%n  " + getCaller(new Throwable().getStackTrace()) + " " + format, params);
  }

  @Override
  public void error(String format, Object... params) {
    this.logger.printf(Level.ERROR,
        "%n  " + getCaller(new Throwable().getStackTrace()) + " " + format, params);
  }

  @Override
  public void error(Throwable message, Throwable throwable) {
    this.logger.error(message, throwable);
  }

  private static String getCaller(StackTraceElement[] stackTraceElements) {
    String caller = Arrays.stream(stackTraceElements)
        .filter(s -> !s.getClassName().startsWith("org.nkjmlab.util.java.logging")
            && !s.getClassName().startsWith("java."))
        .findFirst().map(se -> se.getClassName() + "." + se.getMethodName() + "(" + se.getFileName()
            + ":" + se.getLineNumber() + ")")
        .orElseGet(() -> "");
    return caller;
  }

}

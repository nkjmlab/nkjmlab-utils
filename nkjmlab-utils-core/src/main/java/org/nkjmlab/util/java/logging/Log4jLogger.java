package org.nkjmlab.util.java.logging;

import static org.nkjmlab.util.java.lang.MessageUtils.*;
import org.apache.logging.log4j.Level;
import org.nkjmlab.util.java.lang.MessageUtils;
import org.nkjmlab.util.java.lang.MethodInvokerInfoUtils;

public class Log4jLogger implements Logger {


  private void printf(int depth, Level level, String format, Object... params) {
    StackTraceElement[] se = new Throwable().getStackTrace();

    logger.printf(level,
        "%n  " + MethodInvokerInfoUtils.getInvokerSummary(depth, se) + newMessage(format, params));
  }


  private org.apache.logging.log4j.Logger logger;

  public Log4jLogger() {
    this(org.apache.logging.log4j.LogManager.getLogger());
  }

  public Log4jLogger(String name) {
    this(org.apache.logging.log4j.LogManager.getLogger(name));
  }

  public Log4jLogger(org.apache.logging.log4j.Logger log) {
    this.logger = log;
  }

  @Override
  public void debug(String format, Object... params) {
    printf(2, Level.DEBUG, format, params);
  }

  @Override
  public void error(String format, Object... params) {
    printf(2, Level.ERROR, format, params);
  }

  @Override
  public void error(Throwable message, Throwable throwable) {
    this.logger.error(message, throwable);
  }

  @Override
  public void info(String format, Object... params) {
    printf(2, Level.INFO, format, params);
  }

  @Override
  public void trace(String format, Object... params) {
    this.logger.trace(MessageUtils.newMessage(format, params));
  }

  @Override
  public void warn(String format, Object... params) {
    printf(2, Level.WARN, format, params);
  }

  @Override
  public void warn(Throwable message, Throwable throwable) {
    this.logger.warn(message, throwable);
  }

}

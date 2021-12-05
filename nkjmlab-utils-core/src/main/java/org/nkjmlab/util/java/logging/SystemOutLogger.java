package org.nkjmlab.util.java.logging;

import static org.nkjmlab.util.java.logging.Logger.Category.*;
import java.io.File;
import org.nkjmlab.util.java.lang.ExceptionUtils;

public class SystemOutLogger implements Logger {

  private static void systemErrorPrintln(Category category, String format, Object... params) {
    Logger.systemErrorPrintln(4, category.name(), format, params);
  }

  private static void systemOutPrintln(Category category, String format, Object... params) {
    Logger.systemOutPrintln(4, category.name(), format, params);
  }

  public SystemOutLogger() {}

  @Override
  public void debug(String format, Object... params) {
    systemOutPrintln(DEBUG, format, params);
  }

  @Override
  public void error(String format, Object... params) {
    systemErrorPrintln(ERROR, format, params);
  }

  @Override
  public void error(Throwable message, Throwable throwable) {
    systemErrorPrintln(ERROR,
        message + File.separator + ExceptionUtils.getMessageWithStackTrace(throwable));
  }

  @Override
  public void info(String format, Object... params) {
    systemOutPrintln(INFO, format, params);
  }

  @Override
  public void trace(String format, Object... params) {
    systemOutPrintln(TRACE, format, params);
  }

  @Override
  public void warn(String format, Object... params) {
    systemErrorPrintln(WARN, format, params);
  }

  @Override
  public void warn(Throwable message, Throwable throwable) {
    systemErrorPrintln(WARN,
        message + File.separator + ExceptionUtils.getMessageWithStackTrace(throwable));
  }

}

package org.nkjmlab.util.logging;

import static org.nkjmlab.util.logging.SimpleLogger.Category.*;

public class SystemOutLogger implements SimpleLogger {

  public SystemOutLogger() {}

  @Override
  public void trace(String format, Object... params) {
    SimpleLogger.systemOutPrintln(TRACE, format, params);
  }

  @Override
  public void debug(String format, Object... params) {
    SimpleLogger.systemOutPrintln(DEBUG, format, params);
  }

  @Override
  public void info(String format, Object... params) {
    SimpleLogger.systemOutPrintln(INFO, format, params);
  }

  @Override
  public void warn(String format, Object... params) {
    SimpleLogger.systemErrorPrintln(WARN, format, params);
  }

  @Override
  public void error(String format, Object... params) {
    SimpleLogger.systemErrorPrintln(ERROR, format, params);
  }


}

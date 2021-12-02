package org.nkjmlab.util.logging;

import static org.nkjmlab.util.logging.SimpleLogger.Category.*;
import org.nkjmlab.util.lang.MethodInvokerInfoUtils;
import org.nkjmlab.util.lang.StringUtils;

public interface SimpleLogger {

  public enum Category {
    TRACE, DEBUG, INFO, WARN, ERROR,
  }

  void trace(String format, Object... params);

  void debug(String format, Object... params);

  void info(String format, Object... params);

  void warn(String format, Object... params);

  void error(String format, Object... params);


  public static void systemErrorPrintln(Category category, String format, Object... params) {
    systemErrorPrintln(category.name(), format, params);
  }

  public static void systemOutPrintln(Category category, String format, Object... params) {
    systemOutPrintln(category.name(), format, params);
  }

  public static void systemErrorPrintln(String label, String msg, Object... params) {
    System.err.println(
        MethodInvokerInfoUtils.getInvokerSummary(5, label) + " " + StringUtils.format(msg, params));
  }

  public static void systemOutPrintln(String label, String msg, Object... params) {
    System.out.println(
        MethodInvokerInfoUtils.getInvokerSummary(5, label) + " " + StringUtils.format(msg, params));
  }

  public static class Log {
    public static void trace(String format, Object... params) {
      SimpleLogger.systemOutPrintln(TRACE, format, params);
    }

    public static void debug(String format, Object... params) {
      SimpleLogger.systemOutPrintln(DEBUG, format, params);
    }

    public static void info(String format, Object... params) {
      SimpleLogger.systemOutPrintln(INFO, format, params);
    }

    public static void warn(String format, Object... params) {
      SimpleLogger.systemErrorPrintln(WARN, format, params);
    }

    public static void error(String format, Object... params) {
      SimpleLogger.systemErrorPrintln(ERROR, format, params);
    }
  }

}

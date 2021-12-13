package org.nkjmlab.util.java.logging;

import static org.nkjmlab.util.java.lang.ParameterizedStringUtils.*;
import static org.nkjmlab.util.java.logging.Logger.Category.*;
import org.nkjmlab.util.java.lang.MethodInvokerInfoUtils;

public interface Logger {

  public enum Category {
    TRACE, DEBUG, INFO, WARN, ERROR,
  }

  void trace(String format, Object... params);

  void debug(String format, Object... params);

  void info(String format, Object... params);

  void warn(String format, Object... params);

  void error(String format, Object... params);

  void warn(Throwable message, Throwable throwable);

  void error(Throwable message, Throwable throwable);



  public static void systemErrorPrintln(int depth, String label, String msg, Object... params) {
    System.err.println(
        MethodInvokerInfoUtils.getInvokerLogMessage(depth, label, new Throwable().getStackTrace())
            + " " + newString(msg, params));
  }

  public static void systemOutPrintln(int depth, String label, String msg, Object... params) {
    System.out.println(
        MethodInvokerInfoUtils.getInvokerLogMessage(depth, label, new Throwable().getStackTrace())
            + " " + newString(msg, params));
  }

  public static class Log {
    public static void trace(String format, Object... params) {
      systemOutPrintln(TRACE, format, params);
    }

    public static void debug(String format, Object... params) {
      systemOutPrintln(DEBUG, format, params);
    }

    public static void info(String format, Object... params) {
      systemOutPrintln(INFO, format, params);
    }

    public static void warn(String format, Object... params) {
      systemErrorPrintln(WARN, format, params);
    }

    public static void error(String format, Object... params) {
      systemErrorPrintln(ERROR, format, params);
    }

    private static void systemErrorPrintln(Category category, String format, Object... params) {
      Logger.systemErrorPrintln(4, category.name(), format, params);
    }

    private static void systemOutPrintln(Category category, String format, Object... params) {
      Logger.systemOutPrintln(4, category.name(), format, params);
    }

  }


}

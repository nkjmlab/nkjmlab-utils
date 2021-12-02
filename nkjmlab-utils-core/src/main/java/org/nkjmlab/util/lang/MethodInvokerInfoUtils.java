package org.nkjmlab.util.lang;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MethodInvokerInfoUtils {
  private static final DateTimeFormatter dateTimeFormatter =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

  public static String getInvokerInfoElement() {
    return getInvokerInfoElement(1);
  }

  public static String getInvokerInfoElement(int depth) {
    StackTraceElement e = getStackTraceElement(depth);
    return "(" + (e.getFileName() != null ? new File(e.getFileName()).getName() : "") + ":"
        + e.getLineNumber() + ") ";
  }

  public static String getInvokerClassName(int depth) {
    StackTraceElement e = getStackTraceElement(depth);
    return e.getClassName() != null ? e.getClassName() : "";
  }


  private static String getInvokerClassName(StackTraceElement e) {
    return e.getClassName() != null ? e.getClassName() : "";
  }

  private static String getInvokerMethodName(StackTraceElement e) {
    return e.getMethodName() != null ? e.getMethodName() : "";
  }

  private static String getInvokerFileName(StackTraceElement e) {
    return e.getFileName() != null ? e.getFileName() : "";
  }

  private static int getInvokerLineNumber(StackTraceElement e) {
    return e.getLineNumber();
  }

  /**
   * @param depth
   * @param prefix DEBUG, INFO, ERROR ....
   * @return
   */
  public static String getInvokerSummary(int depth, String prefix) {
    StackTraceElement e = getStackTraceElement(depth);
    return dateTimeFormatter.format(LocalDateTime.now()) + " " + String.format("%5s", prefix) + " ["
        + Thread.currentThread().getName() + "] " + getInvokerClassName(e) + "."
        + getInvokerMethodName(e) + "(" + getInvokerFileName(e) + ":" + getInvokerLineNumber(e)
        + ")";
  }

  private static StackTraceElement getStackTraceElement(int index) {
    StackTraceElement[] stackTrace = new Throwable().getStackTrace();
    if (index < 0) {
      return stackTrace[0];
    } else if (index >= stackTrace.length) {
      return stackTrace[stackTrace.length - 1];
    } else {
      return stackTrace[index];
    }
  }
}

package org.nkjmlab.util.java.lang;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MethodInvokerInfoUtils {
  private static final DateTimeFormatter dateTimeFormatter =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");


  public static String getInvokerInfoElement(int depth, StackTraceElement[] stackTraceElements) {
    StackTraceElement e = getStackTraceElement(depth, stackTraceElements);
    return "(" + (e.getFileName() != null ? new File(e.getFileName()).getName() : "") + ":"
        + e.getLineNumber() + ") ";
  }

  public static String getInvokerClassName(int depth, StackTraceElement[] stackTraceElements) {
    StackTraceElement e = getStackTraceElement(depth, stackTraceElements);
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
   * @param stackTraceElements TODO
   * @return
   */
  public static String getInvokerSummary(int depth, String prefix,
      StackTraceElement[] stackTraceElements) {
    StackTraceElement e = getStackTraceElement(depth, new Throwable().getStackTrace());
    return dateTimeFormatter.format(LocalDateTime.now()) + " " + String.format("%5s", prefix) + " ["
        + Thread.currentThread().getName() + "] " + getInvokerClassName(e) + "."
        + getInvokerMethodName(e) + "(" + getInvokerFileName(e) + ":" + getInvokerLineNumber(e)
        + ")";
  }

  private static StackTraceElement getStackTraceElement(int index,
      StackTraceElement[] stackTraceElements) {
    if (index < 0) {
      return stackTraceElements[0];
    } else if (index >= stackTraceElements.length) {
      return stackTraceElements[stackTraceElements.length - 1];
    } else {
      return stackTraceElements[index];
    }
  }
}

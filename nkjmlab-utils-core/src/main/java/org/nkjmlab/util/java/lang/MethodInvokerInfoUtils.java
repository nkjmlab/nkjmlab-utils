package org.nkjmlab.util.java.lang;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MethodInvokerInfoUtils {
  private static final DateTimeFormatter dateTimeFormatter =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");


  public static String getInvokerClassName(int depth, StackTraceElement[] stackTraceElements) {
    StackTraceElement e = getStackTraceElement(depth, stackTraceElements);
    return e.getClassName() != null ? e.getClassName() : "";
  }

  private static String getInvokerClassName(StackTraceElement e) {
    return e.getClassName() != null ? e.getClassName() : "";
  }


  public static String getInvokerClassNameAndMethodName(StackTraceElement e) {
    return getInvokerClassName(e) + "." + getInvokerMethodName(e);
  }

  private static String getInvokerFileName(StackTraceElement e) {
    return e.getFileName() != null ? e.getFileName() : "";
  }

  private static String getInvokerFileNameAndLineNumber(StackTraceElement e) {
    return getInvokerFileName(e) + ":" + getInvokerLineNumber(e);
  }

  public static String getInvokerFileNameAndLineNumber(int depth,
      StackTraceElement[] stackTraceElements) {
    StackTraceElement e = getStackTraceElement(depth, stackTraceElements);
    return getInvokerFileNameAndLineNumber(e);
  }

  private static int getInvokerLineNumber(StackTraceElement e) {
    return e.getLineNumber();
  }

  private static String getInvokerMethodName(StackTraceElement e) {
    return e.getMethodName() != null ? e.getMethodName() : "";
  }

  /**
   *
   * @param depth
   * @param prefix DEBUG, INFO, ERROR ....
   * @param stackTraceElements generated by <code>new Throwable().getStackTrace()</code>
   * @return
   */
  public static String getInvokerLogMessage(int depth, String prefix,
      StackTraceElement[] stackTraceElements) {
    return dateTimeFormatter.format(LocalDateTime.now()) + " " + String.format("%5s", prefix) + " ["
        + Thread.currentThread().getName() + "] " + getInvokerSummary(depth, stackTraceElements);
  }

  public static String getInvokerSummary(int depth, StackTraceElement[] stackTraceElements) {
    StackTraceElement e = getStackTraceElement(depth, stackTraceElements);
    return getInvokerClassNameAndMethodName(e) + "(" + getInvokerFileNameAndLineNumber(e) + ") ";
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

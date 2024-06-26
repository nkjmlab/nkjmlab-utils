package org.nkjmlab.util.java.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;

public class RetryUtils {
  private static final org.nkjmlab.util.java.logging.SimpleLogger log =
      org.nkjmlab.util.java.logging.LogManager.createLogger();

  private RetryUtils() {}

  public static void retry(Runnable action, int maxRetry, long interval, TimeUnit unit) {
    retry(action, () -> true, maxRetry, interval, unit);
  }

  public static void retry(
      Runnable action, BooleanSupplier breakCond, int maxRetry, long interval, TimeUnit timeUnit) {
    for (int i = 0; i < maxRetry; i++) {
      try {
        action.run();
        if (breakCond.getAsBoolean()) {
          return;
        }
      } catch (Exception e) {
        log.warn(e.getMessage(), e);
      }
      ThreadUtils.sleep(interval, timeUnit);
    }
    throw new RuntimeException("Failed to try (" + maxRetry + " times).");
  }

  public static <T extends Object> T retry(
      Callable<T> action, int maxRetry, long interval, TimeUnit unit) {
    return retry(action, (r) -> true, maxRetry, interval, unit);
  }

  public static <T extends Object> T retry(
      Callable<T> action, Predicate<T> breakCond, int maxRetry, long interval, TimeUnit timeUnit) {
    T result = null;
    for (int i = 0; i < maxRetry; i++) {
      try {
        result = action.call();
        if (breakCond.test(result)) {
          return result;
        }
      } catch (Exception e) {
        log.warn(e.getMessage(), e);
      }
      ThreadUtils.sleep(interval, timeUnit);
    }
    throw new RuntimeException(
        "Failed to try ("
            + maxRetry
            + " times). The result of last try is as follows. "
            + (result != null ? result.toString() : "null"));
  }
}

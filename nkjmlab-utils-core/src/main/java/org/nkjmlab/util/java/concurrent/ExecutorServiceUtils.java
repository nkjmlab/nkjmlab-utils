package org.nkjmlab.util.java.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceUtils {
  private static final org.nkjmlab.util.java.logging.SimpleLogger log =
      org.nkjmlab.util.java.logging.LogManager.createLogger();

  public static boolean shutdownAndAwaitTermination(ExecutorService executorService, long timeout,
      TimeUnit unit) {
    executorService.shutdown();
    try {
      log.info("Awaiting shutdown for {} ....", executorService);
      boolean b = executorService.awaitTermination(timeout, unit);
      log.info("{} is shutdown.", executorService);
      return b;
    } catch (InterruptedException e) {
      log.info("Awating shutdown is timeout.");
      log.debug(e.getMessage());
      return false;
    }
  }

  public static void shutdownAndKeepAwaitingTermination(ExecutorService executorService) {
    log.info("Awaiting shutdown for {} ....", executorService);
    executorService.shutdown();
    keepAwaitingTermination(executorService);
    log.info("{} is completely shutdown.", executorService);
  }

  public static void keepAwaitingTermination(ExecutorService executorService) {
    try {
      executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
    } catch (InterruptedException e) {
      throw new IllegalStateException("Unexpected interrpution occurred.", e);
    }
  }

  public static List<Runnable> shutdownNowAfterAwaiting(ExecutorService executorService,
      long timeout, TimeUnit unit) {
    if (shutdownAndAwaitTermination(executorService, timeout, unit)) {
      return new ArrayList<>();
    } else {
      return executorService.shutdownNow();
    }
  }

  public static ScheduledFuture<?> scheduleWithFixedDelay(ScheduledExecutorService srv,
      Runnable command, long initialDelay, long delay, TimeUnit unit) {
    return srv.scheduleWithFixedDelay(() -> {
      try {
        command.run();
      } catch (Throwable e) {
        log.error(e, e);
      }
    }, initialDelay, delay, unit);
  }

}

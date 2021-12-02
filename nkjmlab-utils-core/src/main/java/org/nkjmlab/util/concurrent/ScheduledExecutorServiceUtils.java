package org.nkjmlab.util.concurrent;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;

public class ScheduledExecutorServiceUtils {
  private static org.apache.logging.log4j.Logger log = LogManager.getLogger();

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

package org.nkjmlab.util.java.concurrent;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class ExecutorServiceUtilsTest {

  private ScheduledExecutorService executorService;

  @AfterEach
  public void tearDown() {
    if (executorService != null) {
      executorService.shutdownNow();
    }
  }

  @Test
  public void testShutdownAndAwaitTermination() throws InterruptedException {
    executorService = Executors.newScheduledThreadPool(1);
    assertTrue(
        ExecutorServiceUtils.shutdownAndAwaitTermination(
            executorService, 100, TimeUnit.MILLISECONDS));
  }

  @Test
  public void testShutdownAndKeepAwaitingTermination() {
    executorService = Executors.newScheduledThreadPool(1);
    executorService.submit(
        () -> {
          try {
            Thread.sleep(1000);
          } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
          }
        });
    assertDoesNotThrow(
        () -> ExecutorServiceUtils.shutdownAndKeepAwaitingTermination(executorService));
  }

  @Test
  public void testShutdownNowAfterAwaiting() {
    executorService = Executors.newScheduledThreadPool(1);
    executorService.submit(
        () -> {
          while (!Thread.currentThread().isInterrupted()) {}
        });
    List<Runnable> remainingTasks =
        ExecutorServiceUtils.shutdownNowAfterAwaiting(executorService, 100, TimeUnit.MILLISECONDS);
    assertTrue(remainingTasks.isEmpty());
  }

  @Test
  public void testScheduleWithFixedDelay() throws InterruptedException {
    executorService = Executors.newScheduledThreadPool(1);
    Runnable command = () -> System.out.println("Running...");
    assertDoesNotThrow(
        () ->
            ExecutorServiceUtils.scheduleWithFixedDelay(
                executorService, command, 100, 100, TimeUnit.MILLISECONDS));
    Thread.sleep(500); // Let the scheduled tasks run for a bit
  }
}

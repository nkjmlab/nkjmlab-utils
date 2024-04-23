package org.nkjmlab.util.java.concurrent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.Test;

class CallOnceTaskTest {

  @Test
  public void testTaskIsExecutedOnlyOnce() throws InterruptedException {
    CountDownLatch latch = new CountDownLatch(1);
    Runnable task =
        () -> {
          latch.countDown();
        };

    CallOnceTask callOnceTask = new CallOnceTask(task);

    ExecutorService executor = Executors.newFixedThreadPool(10);
    for (int i = 0; i < 10; i++) {
      executor.submit(callOnceTask);
    }
    executor.shutdown();

    assertTrue(
        latch.await(1, java.util.concurrent.TimeUnit.SECONDS),
        "Task should have been executed exactly once");
    assertEquals(0, latch.getCount(), "Task did not execute the expected number of times");
  }
}

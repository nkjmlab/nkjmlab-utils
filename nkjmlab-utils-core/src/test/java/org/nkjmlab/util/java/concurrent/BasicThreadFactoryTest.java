package org.nkjmlab.util.java.concurrent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.Thread.UncaughtExceptionHandler;

import org.junit.jupiter.api.Test;

class BasicThreadFactoryTest {

  @Test
  public void testThreadCreation() {
    BasicThreadFactory factory =
        BasicThreadFactory.builder()
            .setThreadNamePrefix("TestThread")
            .setDaemon(true)
            .setPriority(Thread.MAX_PRIORITY)
            .build();

    Runnable r =
        () -> {
          try {
            Thread.sleep(100);
          } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
          }
        };

    Thread thread = factory.newThread(r);
    thread.start();

    assertTrue(thread.getName().startsWith("TestThread-"));
    assertTrue(thread.isDaemon());
    assertEquals(Thread.MAX_PRIORITY, thread.getPriority());
  }

  @Test
  public void testUncaughtExceptionHandler() {
    UncaughtExceptionHandler handler =
        (t, e) -> System.out.println("Caught exception: " + e.getMessage());
    BasicThreadFactory factory =
        BasicThreadFactory.builder()
            .setThreadNamePrefix("ExceptionThread")
            .setUncaughtExceptionHandler(handler)
            .build();

    Runnable r =
        () -> {
          throw new RuntimeException("This is a test exception");
        };

    Thread thread = factory.newThread(r);
    thread.setUncaughtExceptionHandler(
        (t, e) -> assertEquals("This is a test exception", e.getMessage()));
    thread.start();
  }
}

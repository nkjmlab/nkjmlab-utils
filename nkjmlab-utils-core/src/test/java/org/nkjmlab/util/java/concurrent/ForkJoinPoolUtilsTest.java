package org.nkjmlab.util.java.concurrent;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinTask;

import org.junit.jupiter.api.Test;

class ForkJoinPoolUtilsTest {

  @Test
  public void testExecuteWithRunnable() {
    Runnable task = () -> System.out.println("Task executed");
    assertDoesNotThrow(() -> ForkJoinPoolUtils.executeWith(task));
  }

  @Test
  public void testSubmitWithCallable() throws ExecutionException, InterruptedException {
    String expected = "Callable result";
    ForkJoinTask<String> result = ForkJoinPoolUtils.submitWith(() -> expected);
    assertEquals(expected, result.get());
  }

  @Test
  public void testSubmitWithRunnable() throws ExecutionException, InterruptedException {
    Runnable task = () -> System.out.println("Runnable task executed");
    ForkJoinTask<?> result = ForkJoinPoolUtils.submitWith(task);
    assertNotNull(result);
    assertDoesNotThrow(() -> result.get());
  }

  @Test
  public void testAvailableProcessorsMinus() {
    int processors = ForkJoinPoolUtils.availableProcessors();
    assertTrue(processors > 0); // Ensure there's at least one processor available
    int minusOne = ForkJoinPoolUtils.getAvailableProcessorsMinus(1);
    assertEquals(processors - 1, minusOne);
  }
}

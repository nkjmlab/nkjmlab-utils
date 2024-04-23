package org.nkjmlab.util.java.concurrent;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

class RetryUtilsTest {

  @Test
  public void testRetryRunnable() {
    final int[] counter = {0};
    Runnable task = () -> counter[0]++;
    RetryUtils.retry(task, 3, 100, TimeUnit.MILLISECONDS);
    assertEquals(1, counter[0]);
  }

  @Test
  public void testRetryCallable() {
    Callable<Integer> task =
        new Callable<Integer>() {
          private int count = 0;

          @Override
          public Integer call() {
            if (++count == 3) {
              return 42;
            }
            throw new IllegalStateException("Not yet!");
          }
        };
    Integer result = RetryUtils.retry(task, 3, 100, TimeUnit.MILLISECONDS);
    assertEquals(42, result);
  }

  @Test
  public void testRetryCallableWithCondition() {
    Callable<Integer> task =
        new Callable<Integer>() {
          private int count = 0;

          @Override
          public Integer call() {
            return ++count;
          }
        };
    Integer result = RetryUtils.retry(task, r -> r == 3, 5, 100, TimeUnit.MILLISECONDS);
    assertEquals(3, result);
  }
}

package org.nkjmlab.util.java.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

public class ForkJoinPoolUtils {

  public static void executeWith(Runnable task) {
    executeWith(getAvailableProcessorsMinus(1), task);
  }

  public static void executeWith(int threadsNum, Runnable task) {
    ForkJoinPool pool = new ForkJoinPool(threadsNum);
    pool.execute(task);
    pool.shutdown();
  }

  public static <T> ForkJoinTask<T> submitWith(Callable<T> task) {
    return submitWith(getAvailableProcessorsMinus(1), task);
  }

  public static <T> ForkJoinTask<T> submitWith(int threadsNum, Callable<T> task) {
    ForkJoinPool pool = new ForkJoinPool(threadsNum);
    ForkJoinTask<T> result = pool.submit(task);
    pool.shutdown();
    return result;
  }

  public static ForkJoinTask<?> submitWith(Runnable task) {
    return submitWith(getAvailableProcessorsMinus(1), task);
  }

  public static ForkJoinTask<?> submitWith(int threadsNum, Runnable task) {
    ForkJoinPool pool = new ForkJoinPool(threadsNum);
    ForkJoinTask<?> result = pool.submit(task);
    pool.shutdown();
    return result;
  }


  public static int getAvailableProcessorsMinus(int threadsNum) {
    if (threadsNum < 0) {
      throw new IllegalArgumentException("Arg should be eq or greater than 0");
    }
    return Math.max(availableProcessors() - threadsNum, 1);
  }

  public static int availableProcessors() {
    return Runtime.getRuntime().availableProcessors();
  }
}

package org.nkjmlab.util.java.concurrent;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ThreadUtils {
  private ThreadUtils() {}

  public static void sleep(long timeout, TimeUnit timeUnit) {
    try {
      timeUnit.sleep(timeout);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  public static List<String> getActiveThreadNames() {
    Thread[] threads = new Thread[Thread.activeCount()];
    Thread.enumerate(threads);
    return Arrays.stream(threads).map((t) -> t.getName()).collect(Collectors.toList());
  }
}

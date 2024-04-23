package org.nkjmlab.util.java.concurrent;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

class ThreadUtilsTest {

  @Test
  public void testSleep() {
    long start = System.currentTimeMillis();
    ThreadUtils.sleep(200, TimeUnit.MILLISECONDS);
    long end = System.currentTimeMillis();
    assertTrue((end - start) >= 200);
  }

  @Test
  public void testGetActiveThreadNames() {
    List<String> threadNames = ThreadUtils.getActiveThreadNames();
    assertTrue(threadNames.contains("main"));
  }
}

package org.nkjmlab.util.java.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;

class IteratorUtilsTest {
  @Test
  public void testToListWithInitialSize() {
    Iterator<Integer> iterator = Arrays.asList(1, 2, 3, 4).iterator();
    List<Integer> result = IteratorUtils.toList(iterator, 4);
    assertEquals(Arrays.asList(1, 2, 3, 4), result);
    assertEquals(4, result.size());
  }

  @Test
  public void testToListWithoutInitialSize() {
    Iterator<Integer> iterator = Arrays.asList(5, 6, 7, 8, 9).iterator();
    List<Integer> result = IteratorUtils.toList(iterator);
    assertEquals(Arrays.asList(5, 6, 7, 8, 9), result);
    assertEquals(5, result.size());
  }
}

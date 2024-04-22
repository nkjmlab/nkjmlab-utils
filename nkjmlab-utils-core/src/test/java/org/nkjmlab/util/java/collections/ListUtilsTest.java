package org.nkjmlab.util.java.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

class ListUtilsTest {

  @Test
  public void testPartition() {
    List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
    List<List<Integer>> partitions = ListUtils.partition(list, 3);
    assertEquals(3, partitions.size());
    assertEquals(Arrays.asList(1, 2), partitions.get(0));
    assertEquals(Arrays.asList(3, 4), partitions.get(1));
    assertEquals(Collections.singletonList(5), partitions.get(2));
  }

  @Test
  public void testAreAllUnique() {
    List<Integer> uniqueList = Arrays.asList(1, 2, 3, 4, 5);
    List<Integer> nonUniqueList = Arrays.asList(1, 2, 3, 3, 5);
    assertTrue(ListUtils.areAllUnique(uniqueList));
    assertFalse(ListUtils.areAllUnique(nonUniqueList));
  }
}

package org.nkjmlab.util.java.collections;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

class CollectionUtilsTest {

  @Test
  public void testGetRandomWithNonEmptyCollection() {
    List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
    Optional<Integer> result = CollectionUtils.getRandom(list);
    assertTrue(result.isPresent());
    assertTrue(list.contains(result.get()));
  }

  @Test
  public void testGetRandomWithEmptyCollection() {
    List<Integer> emptyList = Collections.emptyList();
    Optional<Integer> result = CollectionUtils.getRandom(emptyList);
    assertFalse(result.isPresent());
  }

  @Test
  public void testGetRandomWithNull() {
    Optional<Integer> result = CollectionUtils.getRandom(null);
    assertFalse(result.isPresent());
  }

  @Test
  public void testGetRandomRepeatedlyToEnsureRandomness() {
    List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    boolean different = false;
    Integer firstResult = CollectionUtils.getRandom(list).orElse(null);
    assertNotNull(firstResult);

    // Run the method 100 times to check for different results
    for (int i = 0; i < 100; i++) {
      Integer newResult = CollectionUtils.getRandom(list).orElse(null);
      if (!firstResult.equals(newResult)) {
        different = true;
        break;
      }
    }
    assertTrue(different, "Should have different results in multiple runs to confirm randomness");
  }
}

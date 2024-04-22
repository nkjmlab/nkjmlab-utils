package org.nkjmlab.util.java.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class LruCacheTest {

  @Test
  public void testLruCacheEvictionPolicy() {
    LruCache<Integer, String> lruCache = new LruCache<>(3);
    lruCache.put(1, "one");
    lruCache.put(2, "two");
    lruCache.put(3, "three");

    assertEquals(3, lruCache.size());

    lruCache.get(1); // Access to make it recently used
    lruCache.put(4, "four"); // Should evict the least recently used item (2)

    assertFalse(lruCache.containsKey(2), "Least recently used (key=2) should be evicted");
    assertTrue(lruCache.containsKey(1), "Key 1 should exist");
    assertTrue(lruCache.containsKey(3), "Key 3 should exist");
    assertTrue(lruCache.containsKey(4), "Key 4 should exist");
  }

  @Test
  public void testLruCacheCapacity() {
    LruCache<Integer, String> lruCache = new LruCache<>(5);
    for (int i = 0; i < 5; i++) {
      lruCache.put(i, "Value " + i);
    }

    assertEquals(5, lruCache.size(), "Cache should be exactly at max capacity");

    lruCache.put(6, "Value 6");
    assertEquals(5, lruCache.size(), "Cache should not exceed max capacity");
    assertFalse(lruCache.containsKey(0), "First entry should be evicted");
  }
}

package org.nkjmlab.util.java.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConcurrentLruCacheTest {

  private ConcurrentLruCache<Integer, String> cache;

  @BeforeEach
  public void setUp() {
    cache = new ConcurrentLruCache<>(3); // Create a small LRU cache for testing
  }

  @Test
  public void testCacheEvictsLeastRecentlyUsed() {
    cache.put(1, "one");
    cache.put(2, "two");
    cache.put(3, "three");
    assertEquals("one", cache.get(1)); // Access to make 1 recently used
    cache.put(4, "four"); // This should evict "two" which is the least recently used

    assertNull(cache.get(2), "Expected 'two' to be evicted");
    assertNotNull(cache.get(1), "Expected 'one' to still exist");
    assertNotNull(cache.get(3), "Expected 'three' to still exist");
    assertNotNull(cache.get(4), "Expected 'four' to exist");
  }

  @Test
  public void testCacheSizeDoesNotExceedMax() {
    cache.put(1, "one");
    cache.put(2, "two");
    cache.put(3, "three");
    cache.put(4, "four");

    assertTrue(cache.size() <= 3, "Cache size should not exceed 3");
  }

  @Test
  public void testCachePutAll() {
    Map<Integer, String> map = new HashMap<>();
    map.put(1, "one");
    map.put(2, "two");
    map.put(3, "three");

    cache.putAll(map);
    assertEquals(3, cache.size(), "Cache should contain exactly 3 items");
    assertEquals("one", cache.get(1));
    assertEquals("two", cache.get(2));
    assertEquals("three", cache.get(3));
  }

  @Test
  public void testCacheClear() {
    cache.put(1, "one");
    cache.put(2, "two");
    cache.put(3, "three");
    cache.clear();

    assertTrue(cache.isEmpty(), "Cache should be empty after clear");
  }

  @Test
  public void testContainsKey() {
    cache.put(1, "one");
    assertTrue(cache.containsKey(1), "Cache should contain key 1");
    assertFalse(cache.containsKey(2), "Cache should not contain key 2");
  }

  @Test
  public void testContainsValue() {
    cache.put(1, "one");
    assertTrue(cache.containsValue("one"), "Cache should contain value 'one'");
    assertFalse(cache.containsValue("two"), "Cache should not contain value 'two'");
  }

  @Test
  public void testKeySetValuesEntrySet() {
    cache.put(1, "one");
    cache.put(2, "two");
    cache.put(3, "three");

    assertEquals(3, cache.keySet().size(), "Key set should reflect the cache content");
    assertEquals(3, cache.values().size(), "Values collection should reflect the cache content");
    assertEquals(3, cache.entrySet().size(), "Entry set should reflect the cache content");
  }
}

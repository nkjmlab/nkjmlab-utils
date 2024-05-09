package org.nkjmlab.util.java.lang;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

class RecordUtilsTest {
  public record TestRecord(String name, int age) {}

  @Test
  public void testConvertMapToRecord() {
    Map<String, Object> map = new LinkedHashMap<>();
    map.put("name", "Alice");
    map.put("age", 30);

    TestRecord record = RecordUtils.convertMapToRecord(map, TestRecord.class);
    assertEquals("Alice", record.name());
    assertEquals(30, record.age());
  }

  @Test
  public void testConvertRecordToMap() {
    TestRecord record = new TestRecord("Bob", 25);
    Map<String, Object> map = RecordUtils.convertRecordToMap(record);

    assertEquals("Bob", map.get("name"));
    assertEquals(25, map.get("age"));
  }
}

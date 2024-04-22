package org.nkjmlab.util.jackson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;

class JacksonMapperTest {

  private JacksonMapper mapper;

  @BeforeEach
  public void setUp() {
    mapper = JacksonMapper.getDefaultMapper();
  }

  @Test
  public void testToJson() throws Exception {
    Map<String, Object> map = Map.of("key", "value");
    String json = mapper.toJson(map);
    assertEquals("{\"key\":\"value\"}", json);
  }

  @Test
  public void testFromJson() throws Exception {
    String json = "{\"key\":\"value\"}";
    Map<String, Object> map = mapper.toMap(json);
    assertEquals("value", map.get("key"));
  }

  @Test
  public void testJsonRoundtrip() throws Exception {
    Map<String, Object> original = Map.of("key", "value");
    String json = mapper.toJson(original);
    Map<String, Object> parsed = mapper.toMap(json);
    assertEquals(original, parsed);
  }

  @Test
  public void testToList() throws Exception {
    String json = "[{\"key\": \"value1\"}, {\"key\": \"value2\"}]";
    List<Map<String, Object>> list = mapper.toList(json);
    assertEquals(2, list.size());
    assertEquals("value1", list.get(0).get("key"));
  }

  @Test
  public void testWriteJsonToFile() throws Exception {
    Map<String, Object> map = Map.of("key", "value");
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    mapper.toJsonAndWrite(map, out, false);
    String json = new String(out.toByteArray(), StandardCharsets.UTF_8);
    assertEquals("{\"key\":\"value\"}", json);
  }

  @Test
  public void testReadJsonFromFile() throws Exception {
    String json = "{\"key\": \"value\"}";
    ByteArrayInputStream in = new ByteArrayInputStream(json.getBytes());
    Map<String, Object> result = mapper.toMap(new InputStreamReader(in, StandardCharsets.UTF_8));
    assertEquals("value", result.get("key"));
  }

  @Test
  public void testConvertValue() {
    Map<String, Object> source = Map.of("key", "value");
    TypeReference<Map<String, Object>> typeRef = new TypeReference<>() {};
    Map<String, Object> result = mapper.convertValue(source, typeRef);
    assertEquals("value", result.get("key"));
  }

  @Test
  public void testHandleNull() {
    assertNull(mapper.toObject((String) null, Map.class));
  }

  @Test
  public void testExceptionHandling() {
    String invalidJson = "{key:value}";
    assertThrows(Exception.class, () -> mapper.toMap(invalidJson));
  }

  @Test
  public void testPrettyPrint() throws Exception {
    Map<String, Object> map = Map.of("key", "value");
    String json = mapper.toJson(map, true);
    assertTrue(json.contains("\n") && json.contains("  "), "JSON should be pretty-printed");
  }

  @Test
  public void testConcurrency() throws InterruptedException {
    Map<String, Object> map = Map.of("key", "value");
    Runnable task =
        () -> {
          String json = mapper.toJson(map);
          Map<String, Object> result = mapper.toMap(json);
          assertEquals("value", result.get("key"));
        };
    List<Thread> threads =
        IntStream.range(0, 100).mapToObj(i -> new Thread(task)).collect(Collectors.toList());
    threads.forEach(Thread::start);
    for (Thread thread : threads) {
      thread.join();
    }
  }

  public static class CustomObject {
    private String name;
    @JsonIgnore private int age;

    public CustomObject(String name, int age) {
      this.name = name;
      this.age = age;
    }

    public String getName() {
      return name;
    }

    public int getAge() {
      return age;
    }
  }

  @Test
  public void testCustomObjectSerialization() throws Exception {
    CustomObject obj = new CustomObject("Alice", 30);
    String json = mapper.toJson(obj);
    assertFalse(json.contains("age"), "Age should not be serialized due to @JsonIgnore");
    assertTrue(json.contains("Alice"), "Name should be serialized");
  }

  @Test
  public void testLargeJson() throws Exception {
    Map<String, String> largeMap = new HashMap<>();
    for (int i = 0; i < 10000; i++) {
      largeMap.put("key" + i, "value" + i);
    }
    String json = mapper.toJson(largeMap);
    assertNotNull(json);
    assertTrue(json.length() > 100000, "JSON string should be large");
  }
}

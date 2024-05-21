package org.nkjmlab.util.jackson;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

class JacksonMapperTest {

  private JacksonMapper mapper;

  @BeforeEach
  public void setUp() {
    mapper = JacksonMapper.getDefaultMapper();
  }

  @Test
  public void testLocalDateSerialization() throws JsonProcessingException {

    LocalDate date = LocalDate.of(2021, 5, 21);
    LocalDate json = mapper.toObject(mapper.toJson(date), LocalDate.class);

    assertThat(json).isEqualTo(date);
  }

  @Test
  public void testLocalDateDeserialization() throws JsonProcessingException {

    String json = "\"2021-05-21\"";
    LocalDate date = mapper.toObject(json, LocalDate.class);

    assertThat(date).isEqualTo(LocalDate.of(2021, 5, 21));
  }

  @Test
  public void testLocalDateTimeSerialization() throws JsonProcessingException {

    LocalDateTime dateTime = LocalDateTime.of(2021, 5, 21, 15, 30, 0);
    LocalDateTime json = mapper.toObject(mapper.toJson(dateTime), LocalDateTime.class);

    assertThat(json).isEqualTo(dateTime);
  }

  @Test
  public void testLocalDateTimeDeserialization() throws JsonProcessingException {

    String json = "\"2021-05-21T15:30:00\"";
    LocalDateTime dateTime = mapper.toObject(json, LocalDateTime.class);

    assertThat(dateTime).isEqualTo(LocalDateTime.of(2021, 5, 21, 15, 30, 0));
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

  @Test
  public void testConvertValue_withClass() {
    String json = "{\"name\":\"John\",\"age\":30}";
    Map<String, Object> map = mapper.toObject(json, new TypeReference<Map<String, Object>>() {});
    Person person = mapper.convertValue(map, Person.class);

    assertThat(person).isNotNull();
    assertThat(person.getName()).isEqualTo("John");
    assertThat(person.getAge()).isEqualTo(30);
  }

  @Test
  public void testConvertValue_withTypeReference() {
    String json = "{\"name\":\"John\",\"age\":30}";
    Map<String, Object> map = mapper.toObject(json, new TypeReference<Map<String, Object>>() {});
    TypeReference<Person> typeReference = new TypeReference<Person>() {};
    Person person = mapper.convertValue(map, typeReference);

    assertThat(person).isNotNull();
    assertThat(person.getName()).isEqualTo("John");
    assertThat(person.getAge()).isEqualTo(30);
  }

  @Test
  public void testToJson1() {
    Person person = new Person("John", 30);
    String json = mapper.toJson(person);

    assertThat(json).isEqualTo("{\"name\":\"John\",\"age\":30}");
  }

  @Test
  public void testToJsonPrettyPrint() {
    Person person = new Person("John", 30);
    String json = mapper.toJson(person, true);

    assertThat(json).containsIgnoringWhitespaces("{\n  \"name\" : \"John\",\n  \"age\" : 30\n}");
  }

  @Test
  public void testToJsonAndWrite() throws Exception {
    Person person = new Person("John", 30);
    File file = File.createTempFile("test", ".json");
    file.deleteOnExit();

    mapper.toJsonAndWrite(person, file, false);

    try (FileReader reader = new FileReader(file)) {
      char[] buffer = new char[256];
      int length = reader.read(buffer);
      String json = new String(buffer, 0, length);

      assertThat(json).isEqualTo("{\"name\":\"John\",\"age\":30}");
    }
  }

  @Test
  public void testToList_fromString() {
    String json = "[{\"name\":\"John\",\"age\":30},{\"name\":\"Jane\",\"age\":25}]";
    List<Map<String, Object>> list = mapper.toList(json);

    assertThat(list).hasSize(2);
    assertThat(list.get(0).get("name")).isEqualTo("John");
    assertThat(list.get(1).get("name")).isEqualTo("Jane");
  }

  @Test
  public void testToMap_fromString() {
    String json = "{\"name\":\"John\",\"age\":30}";
    Map<String, Object> map = mapper.toMap(json);

    assertThat(map).hasSize(2);
    assertThat(map.get("name")).isEqualTo("John");
    assertThat(map.get("age")).isEqualTo(30);
  }

  @Test
  public void testToObject_fromString() {
    String json = "{\"name\":\"John\",\"age\":30}";
    Person person = mapper.toObject(json, Person.class);

    assertThat(person).isNotNull();
    assertThat(person.getName()).isEqualTo("John");
    assertThat(person.getAge()).isEqualTo(30);
  }

  @Test
  public void testToObject_fromInputStream() throws Exception {
    String json = "{\"name\":\"John\",\"age\":30}";
    InputStream inputStream = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
    Person person = mapper.toObject(inputStream, Person.class);

    assertThat(person).isNotNull();
    assertThat(person.getName()).isEqualTo("John");
    assertThat(person.getAge()).isEqualTo(30);
  }

  @Test
  public void testToObject_fromReader() throws Exception {
    String json = "{\"name\":\"John\",\"age\":30}";
    StringReader reader = new StringReader(json);
    Person person = mapper.toObject(reader, Person.class);

    assertThat(person).isNotNull();
    assertThat(person.getName()).isEqualTo("John");
    assertThat(person.getAge()).isEqualTo(30);
  }

  @Test
  public void testToObject_fromFile() throws Exception {
    String json = "{\"name\":\"John\",\"age\":30}";
    File file = File.createTempFile("test", ".json");
    file.deleteOnExit();

    try (FileWriter writer = new FileWriter(file)) {
      writer.write(json);
    }

    Person person = mapper.toObject(file, Person.class);

    assertThat(person).isNotNull();
    assertThat(person.getName()).isEqualTo("John");
    assertThat(person.getAge()).isEqualTo(30);
  }

  // Sample Person class for testing
  static class Person {
    private String name;
    private int age;

    public Person() {}

    public Person(String name, int age) {
      this.name = name;
      this.age = age;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public int getAge() {
      return age;
    }

    public void setAge(int age) {
      this.age = age;
    }
  }
}

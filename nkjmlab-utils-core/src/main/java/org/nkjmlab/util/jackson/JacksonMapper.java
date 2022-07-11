package org.nkjmlab.util.jackson;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.ClassUtils;
import org.nkjmlab.util.java.function.Try;
import org.nkjmlab.util.java.json.JsonMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JacksonMapper implements JsonMapper {

  private static final JacksonMapper DEFAULT_MAPPER =
      new JacksonMapper(createDefaultObjectMapper());
  private static final JacksonMapper IGNORE_UNKNOWN_PROPERTIES_MAPPER =
      new JacksonMapper(createIgnoreUnknownPropertiesObjectMapper());

  public static JacksonMapper create(ObjectMapper mapper) {
    return new JacksonMapper(mapper);
  }

  private static ObjectMapper createIgnoreUnknownPropertiesObjectMapper() {
    ObjectMapper objectMapper = createDefaultObjectMapper();
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    return objectMapper;
  }

  public static ObjectMapper createDefaultObjectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    if (isJavaTimeModuleEnable()) {
      objectMapper.registerModule(new JavaTimeModule());
      objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }
    return objectMapper;
  }

  private static boolean isJavaTimeModuleEnable() {
    return Try.getOrElse(() -> {
      Class.forName("com.fasterxml.jackson.datatype.jsr310.JavaTimeModule");
      return true;
    }, false);
  }

  public static JacksonMapper getIgnoreUnknownPropertiesMapper() {
    return IGNORE_UNKNOWN_PROPERTIES_MAPPER;
  }


  public static JacksonMapper getDefaultMapper() {
    return DEFAULT_MAPPER;
  }

  private final ObjectMapper mapper;

  private JacksonMapper(ObjectMapper mapper) {
    this.mapper = mapper;
  }


  @Override
  public <T> T convertValue(Object fromValue, Class<T> toValueType) {
    if (fromValue == null) {
      return null;
    }
    if (ClassUtils.isAssignable(fromValue.getClass(), toValueType)) {
      @SuppressWarnings("unchecked")
      T fromValue2 = (T) fromValue;
      return fromValue2;
    }
    return mapper.convertValue(fromValue, toValueType);
  }

  @Override
  public Type convertValue(Object fromValue, Type toValueType) {
    if (fromValue == null) {
      return null;
    }
    return mapper.convertValue(fromValue, mapper.getTypeFactory().constructType(toValueType));

  }

  public <T> T convertValue(Object fromValue, TypeReference<T> toValueType) {
    return mapper.convertValue(fromValue, toValueType);
  }

  public ObjectMapper getObjectMapper() {
    return mapper;
  }

  @Override
  public String toJson(Object obj) {
    return toJson(obj, false);
  }

  @Override
  public String toJson(Object obj, boolean prettyPrint) {
    return Try.getOrElseThrow(() -> {
      if (prettyPrint) {
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
      } else {
        return mapper.writeValueAsString(obj);
      }
    }, e -> Try.rethrow(e));
  }

  @Override
  public void toJsonAndWrite(Object obj, File out, boolean prettyPrint) {
    Try.runOrElseThrow(() -> {
      if (prettyPrint) {
        mapper.writerWithDefaultPrettyPrinter().writeValue(out, obj);
      } else {
        mapper.writeValue(out, obj);
      }
    }, e -> Try.rethrow(e));
  }

  @Override
  public void toJsonAndWrite(Object obj, OutputStream out, boolean prettyPrint) {
    Try.runOrElseThrow(() -> {
      if (prettyPrint) {
        mapper.writerWithDefaultPrettyPrinter().writeValue(out, obj);
      } else {
        mapper.writeValue(out, obj);
      }
    }, e -> Try.rethrow(e));
  }

  @Override
  public void toJsonAndWrite(Object obj, Writer out, boolean prettyPrint) {
    Try.runOrElseThrow(() -> {
      if (prettyPrint) {
        mapper.writerWithDefaultPrettyPrinter().writeValue(out, obj);
      } else {
        mapper.writeValue(out, obj);
      }
    }, e -> Try.rethrow(e));
  }

  @Override
  public List<Map<String, Object>> toList(String json) {
    return toObject(json, new TypeReference<List<Map<String, Object>>>() {});
  }

  @Override
  public List<Map<String, Object>> toList(File in) {
    return toObject(in, new TypeReference<List<Map<String, Object>>>() {});
  }

  @Override
  public List<Map<String, Object>> toList(Reader in) {
    return toObject(in, new TypeReference<List<Map<String, Object>>>() {});
  }

  @Override
  public List<Map<String, Object>> toList(InputStream in) {
    return toObject(in, new TypeReference<List<Map<String, Object>>>() {});
  }


  @Override
  public Map<String, Object> toMap(File in) {
    return toObject(in, new TypeReference<Map<String, Object>>() {});
  }

  @Override
  public Map<String, Object> toMap(Reader in) {
    return toObject(in, new TypeReference<Map<String, Object>>() {});
  }

  @Override
  public Map<String, Object> toMap(InputStream in) {
    return toObject(in, new TypeReference<Map<String, Object>>() {});
  }

  @Override
  public Map<String, Object> toMap(String json) {
    return toObject(json, new TypeReference<Map<String, Object>>() {});
  }


  @Override
  public <T> T toObject(File in, Class<T> clazz) {
    return Try.getOrElseThrow(() -> mapper.readValue(in, clazz), e -> Try.rethrow(e));
  }

  public <T> T toObject(File in, TypeReference<T> clazz) {
    return Try.getOrElseThrow(() -> mapper.readValue(in, clazz), e -> Try.rethrow(e));
  }

  @Override
  public <T> T toObject(byte[] in, Class<T> clazz) {
    return Try.getOrElseThrow(() -> mapper.readValue(in, clazz), e -> Try.rethrow(e));
  }

  @Override
  public <T> T toObject(InputStream in, Class<T> clazz) {
    return Try.getOrElseThrow(() -> mapper.readValue(in, clazz), e -> Try.rethrow(e));
  }

  public <T> T toObject(InputStream in, TypeReference<T> clazz) {
    return Try.getOrElseThrow(() -> mapper.readValue(in, clazz), e -> Try.rethrow(e));
  }

  @Override
  public <T> T toObject(Reader in, Class<T> clazz) {
    return Try.getOrElseThrow(() -> mapper.readValue(in, clazz), e -> Try.rethrow(e));
  }

  public <T> T toObject(Reader in, TypeReference<T> clazz) {
    return Try.getOrElseThrow(() -> mapper.readValue(in, clazz), e -> Try.rethrow(e));
  }

  @Override
  public <T> T toObject(String json, Class<T> clazz) {
    return Try.getOrElseThrow(() -> mapper.readValue(json, clazz), e -> Try.rethrow(e));
  }


  @Override
  public Object toObject(String json, Object hint) {
    if (hint instanceof JavaType) {
      return toObject(json, (JavaType) hint);
    } else if (hint instanceof TypeReference) {
      TypeReference<?> hint2 = (TypeReference<?>) hint;
      return toObject(json, hint2);
    } else if (hint instanceof Class<?>) {
      return toObject(json, (Class<?>) hint);
    } else {
      throw new IllegalArgumentException("hint is invalid.");
    }
  }

  @Override
  public Object toObject(String json, Type hint) {
    try {
      if (hint instanceof Class<?>) {
        return toObject(json, (Class<?>) hint);
      } else {
        return mapper.readValue(json, mapper.getTypeFactory().constructType(hint));
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public <T> T toObject(String json, TypeReference<T> clazz) {
    return Try.getOrElseThrow(() -> mapper.readValue(json, clazz), e -> Try.rethrow(e));
  }



}

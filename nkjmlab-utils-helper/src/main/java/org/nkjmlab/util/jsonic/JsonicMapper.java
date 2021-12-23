package org.nkjmlab.util.jsonic;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;
import org.nkjmlab.util.java.io.FileUtils;
import org.nkjmlab.util.java.json.JsonMapper;
import net.arnx.jsonic.JSON;
import net.arnx.jsonic.JSONException;

public class JsonicMapper implements JsonMapper {

  private static final JsonicMapper DEFAULT_MAPPER = new JsonicMapper();

  public static JsonicMapper getDefaultMapper() {
    return DEFAULT_MAPPER;
  }

  @Override
  public <T> T toObject(String json, Class<T> clazz) {
    return decode(json, clazz);
  }

  @Override
  public <T> T toObject(File in, Class<T> clazz) {
    return decode(in, clazz);
  }

  @Override
  public <T> T toObject(Reader in, Class<T> clazz) {
    return decode(in, clazz);
  }

  @Override
  public <T> T toObject(InputStream in, Class<T> clazz) {
    return decode(in, clazz);
  }

  @Override
  public String toJson(Object obj) {
    return toJson(obj, false);
  }

  @Override
  public void toJsonAndWrite(Object obj, File out, boolean prettyPrint) {
    encode(obj, out, prettyPrint);
  }

  @Override
  public void toJsonAndWrite(Object obj, Writer out, boolean prettyPrint) {
    encode(obj, out, prettyPrint);
  }

  @Override
  public void toJsonAndWrite(Object obj, OutputStream out, boolean prettyPrint) {
    encode(obj, out, prettyPrint);
  }

  @Override
  public Object toObject(String json, Object hint) {
    if (hint instanceof Type) {
      return toObject(json, (Type) hint);
    } else {
      throw new IllegalArgumentException("hint is invalid.");
    }
  }

  @Override
  public Object toObject(String json, Type hint) {
    return decode(json, (Type) hint);
  }

  @Override
  public String toJson(Object obj, boolean prettyPrint) {
    return encode(obj, prettyPrint);
  }

  @Override
  public <T> T convertValue(Object fromValue, Class<T> toValueType) {
    return decode(encode(fromValue), toValueType);
  }

  @Override
  public Type convertValue(Object fromValue, Type toValueType) {
    return decode(encode(fromValue), toValueType);
  }

  @Override
  public Map<String, Object> toMap(String json) {
    return decode(json);
  }

  @Override
  public Map<String, Object> toMap(File in) {
    return decode(in);
  }

  @Override
  public Map<String, Object> toMap(Reader in) {
    return decode(in);
  }

  @Override
  public Map<String, Object> toMap(InputStream in) {
    return decode(in);
  }

  @Override
  public List<Map<String, Object>> toList(File in) {
    return decode(in);
  }

  @Override
  public List<Map<String, Object>> toList(InputStream in) {
    return decode(in);
  }

  @Override
  public List<Map<String, Object>> toList(Reader in) {
    return decode(in);
  }

  @Override
  public List<Map<String, Object>> toList(String json) {
    return decode(json);
  }


  private static <T> T decode(String source) {
    return JSON.decode(source);
  }

  private static Type decode(String json, Type hint) {
    return JSON.decode(json, hint);
  }

  private static <T> T decode(String source, Class<T> clazz) {
    return JSON.decode(source, clazz);
  }

  private static <T> T decode(File file) {
    return decode(FileUtils.newBufferedReader(file.toPath()));
  }

  private static <T> T decode(InputStream in) {
    return decode(new InputStreamReader(in));
  }

  @SuppressWarnings("unchecked")
  private static <T> T decode(Reader reader) {
    try {
      return (T) JSON.decode(reader);
    } catch (JSONException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  private static <T> T decode(File file, Class<T> clazz) {
    return decode(FileUtils.newBufferedReader(file.toPath()), clazz);
  }

  private static <T> T decode(InputStream in, Class<T> clazz) {
    return decode(new InputStreamReader(in), clazz);
  }

  private static <T> T decode(Reader reader, Class<T> clazz) {
    try {
      return JSON.decode(reader, clazz);
    } catch (JSONException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  private static String encode(Object source) {
    return encode(source, false);
  }

  private static String encode(Object source, boolean prettyPrint) {
    try {
      return JSON.encode(source, prettyPrint);
    } catch (JSONException e) {
      throw new RuntimeException(e);
    }
  }

  private static void encode(Object source, File file, boolean prettyPrint) {
    encode(source, FileUtils.newBufferedWriter(file.toPath(), StandardCharsets.UTF_8,
        StandardOpenOption.CREATE), prettyPrint);
  }

  private static void encode(Object source, OutputStream out, boolean prettyPrint) {
    encode(source, new OutputStreamWriter(out, StandardCharsets.UTF_8), prettyPrint);
  }

  private static void encode(Object source, Appendable appendable, boolean prettyPrint) {
    try {
      JSON.encode(source, appendable, prettyPrint);
    } catch (JSONException | IOException e) {
      throw new RuntimeException(e);
    }
  }


}

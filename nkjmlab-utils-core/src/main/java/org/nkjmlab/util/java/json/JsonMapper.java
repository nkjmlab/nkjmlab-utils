package org.nkjmlab.util.java.json;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public interface JsonMapper {

  /**
   * Serializing the given value into JSON, and then binding JSON data into value of the given type.
   *
   * @param <T>
   * @param fromValue
   * @param toValueType
   * @return
   */
  <T> T convertValue(Object fromValue, Class<T> toValueType);

  /**
   * Serializing the given value into JSON, and then binding JSON data into value of the given type.
   *
   * @param fromValue
   * @param toValueType
   * @return
   */
  Type convertValue(Object fromValue, Type toValueType);

  /**
   * From Object to JSON String
   *
   * @param obj
   * @return
   */
  String toJson(Object obj);

  /**
   * From Object to JSON String
   *
   * @param obj
   * @param prettyPrint
   * @return
   */
  String toJson(Object obj, boolean prettyPrint);

  /**
   * Convert object to JSON and write it to File.
   *
   * @param obj
   * @param out
   * @param prettyPrint
   */
  void toJsonAndWrite(Object obj, File out, boolean prettyPrint);

  /**
   * Convert object to JSON and write it to OutputStream.
   *
   * @param obj
   * @param out
   * @param prettyPrint
   */
  void toJsonAndWrite(Object obj, OutputStream out, boolean prettyPrint);

  /**
   * Convert object to JSON and write it to Writer.
   *
   * @param obj
   * @param out
   * @param prettyPrint
   */
  void toJsonAndWrite(Object obj, Writer out, boolean prettyPrint);

  List<Map<String, Object>> toList(File in);

  List<Map<String, Object>> toList(InputStream in);

  List<Map<String, Object>> toList(Reader in);

  List<Map<String, Object>> toList(String json);


  Map<String, Object> toMap(File in);

  Map<String, Object> toMap(InputStream in);


  Map<String, Object> toMap(Reader in);

  Map<String, Object> toMap(String json);

  /**
   * From JSON file to object
   *
   * @param <T>
   * @param in
   * @param clazz
   * @return
   */
  <T> T toObject(File in, Class<T> clazz);

  /**
   * From JSON InputStream to Object
   *
   * @param <T>
   * @param in
   * @param clazz
   * @return
   */
  <T> T toObject(InputStream in, Class<T> clazz);

  /**
   * From JSON reader to object
   *
   * @param <T>
   * @param in
   * @param clazz
   * @return
   */
  <T> T toObject(Reader in, Class<T> clazz);

  /**
   * From JSON string to object
   *
   * @param <T>
   * @param json
   * @param clazz
   * @return
   */
  <T> T toObject(String json, Class<T> clazz);

  /**
   * From JSON string to object
   *
   * @param json
   * @param hint
   * @return
   */
  Object toObject(String json, Object hint);

  /**
   * From JSON string to object
   *
   * @param json
   * @param hint
   * @return
   */
  Object toObject(String json, Type hint);

}

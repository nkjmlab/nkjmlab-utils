package org.nkjmlab.util.java.lang;

import java.lang.reflect.RecordComponent;
import java.util.LinkedHashMap;
import java.util.Map;

import org.nkjmlab.util.java.function.Try;

public class RecordUtils {
  private RecordUtils() {}

  public static <T extends Record> T convertMapToRecord(Map<String, Object> src, Class<T> toType) {
    try {
      RecordComponent[] recordComponents = toType.getRecordComponents();
      Class<?>[] types = new Class<?>[recordComponents.length];
      Object[] args = new Object[recordComponents.length];
      for (int i = 0; i < recordComponents.length; i++) {
        types[i] = recordComponents[i].getType();
        args[i] = src.get(recordComponents[i].getName());
      }
      return toType.getDeclaredConstructor(types).newInstance(args);
    } catch (Exception e) {
      throw Try.rethrow(e);
    }
  }

  public static <T extends Record> Map<String, Object> convertRecordToMap(T src) {
    try {
      RecordComponent[] recordComponents = src.getClass().getRecordComponents();
      Map<String, Object> destMap = new LinkedHashMap<>();
      for (int i = 0; i < recordComponents.length; i++) {
        destMap.put(recordComponents[i].getName(), recordComponents[i].getAccessor().invoke(src));
      }
      return destMap;
    } catch (Exception e) {
      throw Try.rethrow(e);
    }
  }
}

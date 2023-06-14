package org.nkjmlab.util.sorm;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.RecordComponent;
import org.nkjmlab.sorm4j.internal.util.StringCache;
import org.nkjmlab.sorm4j.result.BasicRowMap;
import org.nkjmlab.sorm4j.result.RowMap;
import org.nkjmlab.util.java.function.Try;

public class RowMapUtils {
  public static <T extends Record> RowMap toRowMap(Class<T> srcRecord) {
    try {
      RecordComponent[] recordComponents = srcRecord.getRecordComponents();
      BasicRowMap destMap = new BasicRowMap();
      for (int i = 0; i < recordComponents.length; i++) {
        destMap.put(recordComponents[i].getName(),
            recordComponents[i].getAccessor().invoke(srcRecord));
      }
      return destMap;
    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
        | SecurityException e) {
      throw Try.rethrow(e);
    }
  }

  public static <T extends Record, S extends RowMap> T toRecord(Class<T> recordType, S srcMap) {
    try {
      RecordComponent[] recordComponents = recordType.getRecordComponents();
      Class<?>[] types = new Class<?>[recordComponents.length];
      Object[] args = new Object[recordComponents.length];
      for (int i = 0; i < recordComponents.length; i++) {
        types[i] = recordComponents[i].getType();
        args[i] = srcMap.get(StringCache.toCanonicalCase(recordComponents[i].getName()));
      }
      return recordType.getDeclaredConstructor(types).newInstance(args);
    } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
        | InvocationTargetException | NoSuchMethodException | SecurityException e) {
      throw Try.rethrow(e);
    }
  }

}

package org.nkjmlab.util.java.lang;

import static java.util.Map.entry;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.nkjmlab.util.java.function.Try;

public final class ClassUtils {
  private ClassUtils() {}

  private static final Map<Class<?>, Class<?>> primitiveToWrapperMap =
      Map.ofEntries(
          entry(Boolean.TYPE, Boolean.class),
          entry(Byte.TYPE, Byte.class),
          entry(Character.TYPE, Character.class),
          entry(Short.TYPE, Short.class),
          entry(Integer.TYPE, Integer.class),
          entry(Long.TYPE, Long.class),
          entry(Double.TYPE, Double.class),
          entry(Float.TYPE, Float.class));

  private static final Map<Class<?>, Class<?>> wrapperToPrimitiveMap =
      primitiveToWrapperMap.entrySet().stream()
          .collect(Collectors.toUnmodifiableMap(en -> en.getValue(), en -> en.getKey()));

  private static final Map<String, Class<?>> nameToClassMap =
      Stream.of(
              Object.class,
              Boolean.class,
              Character.class,
              Byte.class,
              Short.class,
              Integer.class,
              Long.class,
              Float.class,
              Double.class,
              boolean.class,
              char.class,
              byte.class,
              short.class,
              int.class,
              long.class,
              float.class,
              double.class)
          .collect(Collectors.toUnmodifiableMap(e -> e.getName(), e -> e));

  public static Class<?> primitiveToWrapper(Class<?> clazz) {
    return primitiveToWrapperMap.get(clazz);
  }

  public static Class<?> wrapperToPrimitive(Class<?> clazz) {
    return wrapperToPrimitiveMap.get(clazz);
  }

  public static Class<?> convertToClass(String className) {
    Class<?> ret = nameToClassMap.get(className);
    if (ret != null) {
      return ret;
    }
    try {
      return Class.forName(className);
    } catch (ClassNotFoundException e) {
      throw Try.rethrow(e);
    }
  }

  public static boolean isAssignable(final Class<?> src, final Class<?> dest) {
    if (dest == null) {
      return false;
    }
    if (src == null) {
      return !dest.isPrimitive();
    }
    if (src.isPrimitive() && dest.isPrimitive()) {
      return isPrimitiveWideningAssignable(src, dest);
    } else if (src.isPrimitive() && !dest.isPrimitive()) {
      Class<?> srcW = primitiveToWrapper(src);
      return srcW == null ? false : srcW.equals(dest) ? true : dest.isAssignableFrom(srcW);
    } else if (!src.isPrimitive() && dest.isPrimitive()) {
      Class<?> srcP = wrapperToPrimitive(src);
      return srcP == null ? false : isPrimitiveWideningAssignable(srcP, dest);
    } else {
      return dest.isAssignableFrom(src);
    }
  }

  private static boolean isPrimitiveWideningAssignable(final Class<?> src, final Class<?> dest) {
    if (src.equals(dest)) {
      return true;
    }
    switch (src.getName()) {
      case "int":
        return dest == Long.TYPE || dest == Float.TYPE || dest == Double.TYPE;
      case "long":
        return dest == Float.TYPE || dest == Double.TYPE;
      case "float":
        return dest == Double.TYPE;
      case "boolean", "double":
        return false;
      case "char", "short":
        return dest == Integer.TYPE
            || dest == Long.TYPE
            || dest == Float.TYPE
            || dest == Double.TYPE;
      case "byte":
        return dest == Short.TYPE
            || dest == Integer.TYPE
            || dest == Long.TYPE
            || dest == Float.TYPE
            || dest == Double.TYPE;
      default:
        return false;
    }
  }
}

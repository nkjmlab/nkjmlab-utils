package org.nkjmlab.util.java.lang.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ReflectionUtils {

  public static Class<?> getCallerClass(int i) {
    StackTraceElement[] ste = Thread.currentThread().getStackTrace();
    return ste[i].getClass();
  }

  public static <T> T newInstance(Constructor<? extends T> constractor, Object... initargs) {
    try {
      return constractor.newInstance(initargs);
    } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
        | InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> Constructor<? extends T> getConstuctor(Class<T> clazz,
      Class<?>... parameterTypes) {
    try {
      return clazz.getConstructor(parameterTypes);
    } catch (NoSuchMethodException | SecurityException e) {
      throw new RuntimeException(e);
    }
  }

}

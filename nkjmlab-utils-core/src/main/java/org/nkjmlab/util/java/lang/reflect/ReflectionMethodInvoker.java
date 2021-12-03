package org.nkjmlab.util.java.lang.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @see http://d.hatena.ne.jp/ashigeru/20090120/1232469594
 *
 * @author nkjm
 *
 */
public class ReflectionMethodInvoker {
  private static final org.nkjmlab.util.java.logging.Logger log =
      org.nkjmlab.util.java.logging.LogManager.getLogger();

  public static boolean isDefined(Object obj, String name, Object... args) {
    if (getApplicableMethods(obj.getClass(), name, toTypes(args)).isEmpty()) {
      return false;
    }
    return true;
  }

  public static Object invoke(Object obj, String name, Object... args) {
    try {
      return getMostSpecificMethod(obj.getClass(), name, toTypes(args)).invoke(obj, args);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new RuntimeException(e);
    }
  }

  private static Class<?>[] toTypes(Object... args) {
    List<Class<?>> argTypes = new ArrayList<Class<?>>();
    for (Object o : args) {
      if (o == null) {
        argTypes.add(null);
      } else {
        argTypes.add(o.getClass());
      }
    }
    return argTypes.toArray(new Class[0]);
  }

  private static Method getMostSpecificMethod(Class<?> type, String name,
      Class<?>... actualArgTypes) {
    List<Method> candidates = getApplicableMethods(type, name, actualArgTypes);
    if (candidates.isEmpty()) {
      throw new RuntimeException(
          "Can't find method. " + type + "." + name + "(" + Arrays.asList(actualArgTypes) + ")");
    }
    Method mostSpecificMethod = candidates.get(0);

    for (Method candidate : candidates) {
      if (isMoreSpecificThan(candidate, mostSpecificMethod)) {
        mostSpecificMethod = candidate;
      }
    }
    return mostSpecificMethod;

  }

  private static boolean isMoreSpecificThan(Method candidate, Method mostSpecificMethod) {
    Class<?>[] candidateParams = candidate.getParameterTypes();
    Class<?>[] mostSpecificParams = mostSpecificMethod.getParameterTypes();
    for (int i = 0; i < candidateParams.length; i++) {
      if (candidateParams[i] == mostSpecificParams[i]) {
        continue;
      } else if (isSubTypeOf(candidateParams[i], mostSpecificParams[i])) {
        return true;
      }
    }
    return false;
  }

  private static List<Method> getApplicableMethods(Class<?> type, String name,
      Class<?>... actualArgTypes) {
    List<Method> result = new ArrayList<>();
    for (Method m : type.getMethods()) {
      if (m.getName().equals(name)) {
        Class<?>[] formalArgTypes = m.getParameterTypes();
        if (isApplicable(actualArgTypes, formalArgTypes)) {
          result.add(m);
        }
      }
    }
    return result;
  }

  private static boolean isApplicable(Class<?>[] actualArgTypes, Class<?>[] formalArgTypes) {
    if (actualArgTypes.length != formalArgTypes.length) {
      return false;
    }
    for (int i = 0; i < actualArgTypes.length; i++) {
      if (!isSubTypeOf(actualArgTypes[i], formalArgTypes[i])) {
        return false;
      }
    }
    return true;
  }

  private static boolean isSubTypeOf(Class<?> actual, Class<?> formal) {
    if (actual == null) {
      return true;
    }
    return formal.isAssignableFrom(actual);
  }

  public static <T> T newInstance(Class<T> clazz, Object... args) {
    List<Class<?>> cs = Arrays.stream(args).map(a -> a.getClass()).collect(Collectors.toList());
    try {
      return clazz.getConstructor(cs.toArray(new Class[0])).newInstance(args);
    } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
        | InvocationTargetException | NoSuchMethodException | SecurityException e) {
      throw new RuntimeException(e);
    }
  }
}


package org.nkjmlab.util.jsonrpc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import org.apache.commons.lang3.ClassUtils;
import org.nkjmlab.util.java.json.JsonMapper;
import org.nkjmlab.util.java.lang.ParameterizedStringFormat;


public class JsonRpcUtils {

  public static Method findMethod(Class<?> clazz, JsonRpcRequest req) {
    String methodName = req.getMethod();
    Object[] params = req.getParams();
    Optional<Method> om = Stream.of(clazz.getMethods()).filter(m -> {
      if (!m.getName().equals(methodName)) {
        return false;
      }
      if (m.getParameterCount() != params.length) {
        return false;
      }
      Class<?>[] formalArgTypes = m.getParameterTypes();
      Class<?>[] actualArgTypes = Stream.of(params).map(o -> o.getClass()).toArray(Class[]::new);

      for (int i = 0; i < params.length; i++) {
        if (!ClassUtils.isAssignable(actualArgTypes[i], formalArgTypes[i])
            && !ClassUtils.isAssignable(actualArgTypes[i], Map.class)
            && !ClassUtils.isAssignable(actualArgTypes[i], List.class)) {
          return false;
        }
      }
      return true;
    }).findAny();

    return om.orElseThrow(() -> new IllegalArgumentException(
        "Method not found => " + "methodName=[" + methodName + "], params=["
            + ParameterizedStringFormat.LENGTH_16.convertToStringWithType(params) + "]"));
  }



  public static Object invokeMethod(Object instance, Method method, Object[] params,
      JsonMapper mapper)
      throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    Class<?>[] formalArgClasses = method.getParameterTypes();
    Object[] actualArgs = new Object[formalArgClasses.length];
    for (int i = 0; i < actualArgs.length; i++) {
      Object actualArg = params[i];
      Class<?> formalArgClass = formalArgClasses[i];
      if (actualArg == null) {
        actualArgs[i] = null;
      } else if (ClassUtils.isAssignable(actualArg.getClass(), formalArgClass)) {
        actualArgs[i] = actualArg;
      } else {
        actualArgs[i] = mapper.convertValue(actualArg, formalArgClass);
      }
    }
    return method.invoke(instance, actualArgs);
  }



}

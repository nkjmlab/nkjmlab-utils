
package org.nkjmlab.util.jsonrpc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.lang3.ClassUtils;
import org.nkjmlab.sorm4j.internal.util.Try;
import org.nkjmlab.util.java.json.JsonMapper;


public class JsonRpcUtils {

  private static final AtomicInteger id = new AtomicInteger();
  private static final Map<String, Method> methodTable = new ConcurrentHashMap<>();


  public static JsonRpcRequest createRequest(String methodName, Object... args) {
    JsonRpcRequest req = new JsonRpcRequest();
    req.setId(Integer.toString(id.incrementAndGet()));
    req.setMethod(methodName);
    req.setParams(args);
    return req;
  }

  public static JsonRpcResponse callJsonRpc(JsonMapper mapper, Object target,
      JsonRpcRequest request) {
    try {
      Object result = invokeMethod(mapper, target, request);
      JsonRpcResponse jres = toJsonRpcResponse(result, request);
      return jres;
    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
      throw Try.rethrow(e);
    }
  }



  private static Object invokeMethod(JsonMapper mapper, Object target, JsonRpcRequest request)
      throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    Method method = findMethod(target, request);
    Object result = invokeMethod(target, method, request.getParams(), mapper);
    return result;
  }


  private static Method findMethod(Object target, JsonRpcRequest req) {
    int paramLength = req.getParams().length;

    String key =
        String.valueOf(target.getClass().getName() + "-" + req.getMethod() + "-" + paramLength);

    return methodTable.computeIfAbsent(key,
        k -> findMethod(target.getClass(), req.getMethod(), paramLength));
  }

  private static Method findMethod(Class<?> clazz, String methodName, int paramCount) {
    for (Method m : clazz.getMethods()) {
      if (m.getName().equals(methodName)) {
        Class<?>[] t = m.getParameterTypes();
        if (t.length == paramCount) {
          return m;
        }
      }
    }
    throw new IllegalArgumentException("Invalid method call => " + "methodName=[" + methodName
        + "], parameters count=" + paramCount);
  }



  private static JsonRpcResponse toJsonRpcResponse(Object result, JsonRpcRequest req) {
    JsonRpcResponse res = new JsonRpcResponse();
    res.setId(req.getId());
    res.setResult(result);
    return res;
  }

  private static Object invokeMethod(Object instance, Method method, Object[] params,
      JsonMapper mapper) {
    // Type[] pTypes = method.getGenericParameterTypes();
    try {
      Class<?>[] pClasses = method.getParameterTypes();
      Object[] args = new Object[pClasses.length];
      for (int i = 0; i < args.length; i++) {
        Object param = params[i];
        Class<?> pClass = pClasses[i];
        if (param == null) {
          args[i] = null;
        } else if (ClassUtils.isAssignable(param.getClass(), pClass)) {
          args[i] = param;
        } else {
          if (String.class.equals(pClass) && (param instanceof Map || param instanceof List)) {
            args[i] = mapper.convertValue(param.toString(), String.class);
          } else {
            args[i] = mapper.convertValue(param, pClass);
          }
        }
      }
      return method.invoke(instance, args);
    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
      throw Try.rethrow(e);
    }
  }


}

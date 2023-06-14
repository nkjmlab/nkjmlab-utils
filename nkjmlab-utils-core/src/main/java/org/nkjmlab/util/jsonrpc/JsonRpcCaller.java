
package org.nkjmlab.util.jsonrpc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;
import org.apache.commons.lang3.ClassUtils;
import org.nkjmlab.util.java.json.JsonMapper;
import org.nkjmlab.util.java.lang.ParameterizedStringFormatter;


/**
 *
 * note: This class depends on {@link org.apache.commons.lang3.ClassUtils}.
 *
 */
public class JsonRpcCaller {

  private final Map<String, Method> methodTable = new ConcurrentHashMap<>();
  private final JsonMapper mapper;


  public JsonRpcCaller(JsonMapper mapper) {
    this.mapper = mapper;
  }

  public JsonRpcResponse callJsonRpc(Object target, JsonRpcRequest request) {
    Method method = null;
    try {
      method = findMethod(target, request);
    } catch (Exception e) {
      return new JsonRpcResponse(request.getId(), JsonRpcError.createMethodNotFound(e));
    }
    try {
      Object jres = invokeMethod(target, method, request.getParams(), mapper);
      return new JsonRpcResponse(request.getId(), jres);
    } catch (IllegalAccessException | IllegalArgumentException e) {
      return new JsonRpcResponse(request.getId(), JsonRpcError.createInvalidParams(e));
    } catch (InvocationTargetException e) {
      return new JsonRpcResponse(request.getId(), JsonRpcError.createInvalidParams(e.getCause()));
    } catch (Throwable e) {
      return new JsonRpcResponse(request.getId(), JsonRpcError.createInternalError(e));
    }
  }

  private Method findMethod(Object target, JsonRpcRequest req) {

    String key = target.getClass().getName() + "#" + req.getMethod() + "(" + String.join(", ",
        Stream.of(req.getParams()).map(o -> o.getClass().getCanonicalName()).toArray(String[]::new))
        + ")";

    return methodTable.computeIfAbsent(key, k -> findMethod(target.getClass(), req));
  }

  public JsonRpcRequest toJsonRpcRequest(String json) {
    return mapper.toObject(json, JsonRpcRequest.class);
  }

  private Method findMethod(Class<?> clazz, JsonRpcRequest req) {
    String methodName = req.getMethod();
    Object[] params = req.getParams();
    Optional<Method> om = Stream.of(clazz.getMethods()).filter(m -> {
      if (!m.getName().equals(methodName)) {
        return false;
      }
      if (m.getParameterCount() != params.length) {
        return false;
      }
      return true;
      // Class<?>[] formalArgTypes = m.getParameterTypes();
      // Class<?>[] actualArgTypes = Stream.of(params).map(o -> o.getClass()).toArray(Class[]::new);
      //
      // for (int i = 0; i < params.length; i++) {
      // if (!ClassUtils.isAssignable(actualArgTypes[i], formalArgTypes[i])
      // && !ClassUtils.isAssignable(actualArgTypes[i], Map.class)
      // && !ClassUtils.isAssignable(actualArgTypes[i], List.class)) {
      // return false;
      // }
      // }
      // return true;
    }).findAny();

    return om.orElseThrow(() -> new IllegalArgumentException(
        "Method not found => " + "methodName=[" + methodName + "], params=["
            + ParameterizedStringFormatter.LENGTH_16.formatParameterWithType(params) + "]"));
  }



  private static Object invokeMethod(Object instance, Method method, Object[] params,
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

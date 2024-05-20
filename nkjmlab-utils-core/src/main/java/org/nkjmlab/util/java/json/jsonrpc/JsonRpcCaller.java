package org.nkjmlab.util.java.json.jsonrpc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import org.nkjmlab.util.java.json.JsonMapper;
import org.nkjmlab.util.java.lang.ClassUtils;
import org.nkjmlab.util.java.lang.ParameterizedStringFormatter;

/**
 * The JsonRpcCaller class is responsible for invoking methods on a target object based on JSON-RPC
 * requests. It uses reflection to find and call the appropriate methods and handles the conversion
 * of JSON parameters to Java objects.
 */
public class JsonRpcCaller {

  private final Map<String, Method> methodTable = new ConcurrentHashMap<>();
  private final JsonMapper mapper;

  public JsonRpcCaller(JsonMapper mapper) {
    this.mapper = mapper;
  }

  public JsonRpcRequest toJsonRpcRequest(String json) {
    return mapper.toObject(json, JsonRpcRequest.class);
  }

  /**
   * Calls the JSON-RPC method on the target object using the given request.
   *
   * @param target the target object on which the method is to be called
   * @param request the JsonRpcRequest containing the method name and parameters
   * @return the JsonRpcResponse containing the result or error
   */
  public JsonRpcResponse callJsonRpc(Object target, JsonRpcRequest request) {
    String id = request.id();
    String methodName = request.method();
    Object[] params = request.params();
    return callJsonRpc(target, id, methodName, params);
  }

  private JsonRpcResponse callJsonRpc(
      Object target, String id, String methodName, Object[] params) {
    Method method = null;
    try {
      method = findMethodWithNameAndArgs(target, methodName, params);
    } catch (Exception e) {
      if (findMethodWithName(target, methodName)) {
        return new JsonRpcResponse(id, JsonRpcError.createInvalidParams(e));
      } else {
        return new JsonRpcResponse(id, JsonRpcError.createMethodNotFound(e));
      }
    }
    try {
      Object jres = invokeMethod(target, method, params, mapper);
      return new JsonRpcResponse(id, jres);
    } catch (IllegalAccessException | IllegalArgumentException e) {
      return new JsonRpcResponse(id, JsonRpcError.createInvalidParams(e));
    } catch (InvocationTargetException e) {
      return new JsonRpcResponse(id, JsonRpcError.createInvalidParams(e.getCause()));
    } catch (Throwable e) {
      return new JsonRpcResponse(id, JsonRpcError.createInternalError(e));
    }
  }

  private boolean findMethodWithName(Object target, String methodName) {
    return Stream.of(target.getClass().getMethods())
            .filter(m -> m.getName().equals(methodName))
            .count()
        > 0;
  }

  private Method findMethodWithNameAndArgs(Object target, String methodName, Object[] params) {
    String key =
        target.getClass().getName()
            + "#"
            + methodName
            + "("
            + String.join(
                ", ",
                Stream.of(params).map(o -> o.getClass().getCanonicalName()).toArray(String[]::new))
            + ")";

    return methodTable.computeIfAbsent(
        key, k -> findMethodWithArgs(target.getClass(), methodName, params));
  }

  private Method findMethodWithArgs(Class<?> clazz, String methodName, Object[] params) {
    Optional<Method> om =
        Stream.of(clazz.getMethods())
            .filter(m -> m.getName().equals(methodName) && m.getParameterCount() == params.length)
            .findAny();

    return om.orElseThrow(
        () ->
            new IllegalArgumentException(
                "Method not found => "
                    + "methodName=["
                    + methodName
                    + "], params=["
                    + ParameterizedStringFormatter.LENGTH_16.formatParameterWithType(params)
                    + "]"));
  }

  /**
   * Invokes the specified method on the given instance with the provided parameters.
   *
   * @param instance the target instance
   * @param method the method to be invoked
   * @param params the parameters to be passed to the method
   * @param mapper the JsonMapper to convert parameter values if necessary
   * @return the result of the method invocation
   * @throws IllegalAccessException if this Method object is enforcing Java language access control
   *     and the underlying method is inaccessible
   * @throws IllegalArgumentException if the method is passed an inappropriate argument
   * @throws InvocationTargetException if the underlying method throws an exception
   */
  private static Object invokeMethod(
      Object instance, Method method, Object[] params, JsonMapper mapper)
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
        // forcefully convert using JsonMapper
        actualArgs[i] = mapper.convertValue(actualArg, formalArgClass);
      }
    }
    return method.invoke(instance, actualArgs);
  }
}

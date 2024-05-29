package org.nkjmlab.util.java.json.jsonrpc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import org.nkjmlab.util.java.json.JsonMapper;
import org.nkjmlab.util.java.stream.StreamUtils;

/**
 * A JSON-RPC method invoker that uses a {@link JsonMapper} to convert parameters and invoke
 * methods.
 */
public class JsonRpcMethodInvokerWithJsonMapper implements JsonRpcMethodInvoker {

  private final Map<String, Method> methodsTable = new ConcurrentHashMap<>();
  private final JsonMapper mapper;

  /**
   * Constructs a new {@code JsonRpcMethodInvokerWithJsonMapper} with the specified {@code
   * JsonMapper}.
   *
   * @param mapper the {@code JsonMapper} to use for parameter conversion
   */
  public JsonRpcMethodInvokerWithJsonMapper(JsonMapper mapper) {
    this.mapper = mapper;
  }

  @Override
  public Method findMethod(Class<?> clazz, String methodName, Object[] params) {
    int parameterCount = params == null ? 0 : params.length;
    String key = clazz.getName() + "#" + methodName + "(" + parameterCount + " args)";
    return methodsTable.computeIfAbsent(
        key,
        k ->
            Stream.of(clazz.getMethods())
                .filter(
                    m -> m.getName().equals(methodName) && m.getParameterCount() == parameterCount)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(key + " is not found")));
  }

  @Override
  public Object invokeMethod(Object instance, Method method, Object[] params)
      throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    Object[] actualArgs =
        convertParametersIfNeeded(
            params == null ? new Object[0] : params, method.getParameterTypes());
    return method.invoke(instance, actualArgs);
  }

  private Object[] convertParametersIfNeeded(Object[] params, Class<?>[] requiredClasses) {
    return StreamUtils.zip(Stream.of(params), Stream.of(requiredClasses))
        .map(
            t -> {
              Object param = t.getT1();
              Class<?> requiredClass = t.getT2();
              return mapper.convertValue(param, requiredClass);
            })
        .toArray();
  }
}

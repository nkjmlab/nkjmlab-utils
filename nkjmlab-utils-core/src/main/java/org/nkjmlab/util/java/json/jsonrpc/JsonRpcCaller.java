package org.nkjmlab.util.java.json.jsonrpc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.stream.Stream;

/**
 * The JsonRpcCaller class is responsible for invoking methods on a target object based on JSON-RPC
 * requests. It uses reflection to find and call the appropriate methods and handles the conversion
 * of JSON parameters to Java objects.
 */
public class JsonRpcCaller {

  private final JsonRpcMethodInvoker invoker;

  public JsonRpcCaller(JsonRpcMethodInvoker invoker) {
    this.invoker = invoker;
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
      method = invoker.findMethod(target.getClass(), methodName, params);
    } catch (Exception e) {
      if (findMethodWithName(target, methodName)) {
        return new JsonRpcResponse(id, JsonRpcError.createInvalidParams(e));
      } else {
        return new JsonRpcResponse(id, JsonRpcError.createMethodNotFound(e));
      }
    }
    try {
      Object jres = invoker.invokeMethod(target, method, params);
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
}

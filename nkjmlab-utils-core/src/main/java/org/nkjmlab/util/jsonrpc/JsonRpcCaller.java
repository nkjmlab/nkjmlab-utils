
package org.nkjmlab.util.jsonrpc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;
import org.nkjmlab.util.java.json.JsonMapper;


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
      Object jres = JsonRpcUtils.invokeMethod(target, method, request.getParams(), mapper);
      return new JsonRpcResponse(request.getId(), jres);
    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
      return new JsonRpcResponse(request.getId(), JsonRpcError.createInvalidParams(e));
    } catch (Throwable e) {
      return new JsonRpcResponse(request.getId(), JsonRpcError.createInternalError(e));
    }
  }

  private Method findMethod(Object target, JsonRpcRequest req) {

    String key = target.getClass().getName() + "#" + req.getMethod() + "(" + String.join(", ",
        Stream.of(req.getParams()).map(o -> o.getClass().getCanonicalName()).toArray(String[]::new))
        + ")";

    return methodTable.computeIfAbsent(key, k -> JsonRpcUtils.findMethod(target.getClass(), req));
  }

  public JsonRpcRequest toJsonRpcRequest(String json) {
    return mapper.toObject(json, JsonRpcRequest.class);
  }


}

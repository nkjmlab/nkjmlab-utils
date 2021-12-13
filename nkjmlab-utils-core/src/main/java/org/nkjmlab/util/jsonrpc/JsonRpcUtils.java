
package org.nkjmlab.util.jsonrpc;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.lang3.ClassUtils;
import org.nkjmlab.util.java.io.IoStreamUtils;
import org.nkjmlab.util.java.json.JsonMapper;


public class JsonRpcUtils {
  private static final org.nkjmlab.util.java.logging.Logger log =
      org.nkjmlab.util.java.logging.LogManager.getLogger();

  private static final AtomicInteger id = new AtomicInteger();


  public static JsonRpcRequest createRequest(Method method, Object... args) {
    JsonRpcRequest req = new JsonRpcRequest();
    req.setId(Integer.toString(id.incrementAndGet()));
    req.setMethod(method.getName());
    req.setParams(args);
    return req;
  }



  public static JsonRpcResponse callJsonRpc(JsonMapper mapper, Object service,
      JsonRpcRequest jreq) {
    try {
      Object result = invokeMethod(service, jreq, mapper);
      JsonRpcResponse jres = toJsonRpcResponse(result, jreq);
      return jres;
    } catch (Exception e) {
      JsonRpcResponse jres = toJsonRpcErrorResponse(mapper.toJson(e), e, jreq);
      return jres;
    }
  }



  public static Object invokeMethod(Object service, JsonRpcRequest req, JsonMapper mapper) {
    Method method = findMethod(service, req);
    if (method == null) {
      String emsg = "method is invalid. "
          + (req.getMethod() != null ? "methodName=" + req.getMethod() : "no method name")
          + ", params=" + Arrays.deepToString(req.getParams());
      log.error(emsg);
      throw new RuntimeException(emsg);
    }
    try {
      Object result = invokeMethod(service, method, req.getParams(), mapper);
      return result;
    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
      log.error("method={}, params={}", method, req.getParams());
      log.error(e1, e1);
      throw new RuntimeException(e1);
    }
  }

  private static Map<String, Method> methodTable = new ConcurrentHashMap<>();

  private static Method findMethod(Object service, JsonRpcRequest req) {
    int paramLength = req.getParams().length;

    String key =
        String.valueOf(service.getClass().getName() + "-" + req.getMethod() + "-" + paramLength);

    return methodTable.computeIfAbsent(key, k -> {
      for (Class<?> clz : service.getClass().getInterfaces()) {
        Method method = findMethod(clz, req.getMethod(), paramLength);
        if (method != null) {
          log.info("[{}] is binded to [{}]", key, method);
          return method;
        }
      }
      return null;
    });
  }

  public static Method findMethod(Class<?> clazz, String methodName, int paramCount) {
    for (Method m : clazz.getMethods()) {
      if (m.getName().equals(methodName)) {
        Class<?>[] t = m.getParameterTypes();
        if (t.length == paramCount) {
          return m;
        }
      }
    }
    return null;
  }


  public static JsonRpcRequest toJsonRpcRequest(JsonMapper mapper, InputStream is) {
    try {
      String str = IoStreamUtils.readAsString(is, StandardCharsets.UTF_8);
      JsonRpcRequest jreq = mapper.toObject(str, JsonRpcRequest.class);
      return jreq;
    } catch (Throwable e) {
      log.error(e, e);
      throw new RuntimeException(e);
    }
  }



  public static JsonRpcResponse toJsonRpcResponse(Object result, JsonRpcRequest req) {
    JsonRpcResponse res = new JsonRpcResponse();
    res.setId(req.getId());
    res.setResult(result);
    return res;
  }

  private static JsonRpcResponse toJsonRpcErrorResponse(String faultString, Throwable t,
      JsonRpcRequest jreq) {
    JsonRpcResponse jres = new JsonRpcResponse();
    jres.setId(jreq.getId());
    jres.setError(JsonRpcError.createRpcFault("Server.userException", faultString, t));
    return jres;
  }



  private static Object invokeMethod(Object instance, Method method, Object[] params,
      JsonMapper mapper)
      throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    // Type[] pTypes = method.getGenericParameterTypes();
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
  }


}

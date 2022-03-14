package org.nkjmlab.util.javax.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.nkjmlab.sorm4j.internal.util.Try;
import org.nkjmlab.util.java.io.IoStreamUtils;
import org.nkjmlab.util.java.json.JsonMapper;
import org.nkjmlab.util.jsonrpc.JsonRpcRequest;
import org.nkjmlab.util.jsonrpc.JsonRpcResponse;
import org.nkjmlab.util.jsonrpc.JsonRpcUtils;

public class JsonRpcService {

  private JsonMapper mapper;

  public JsonRpcService(JsonMapper mapper) {
    this.mapper = mapper;
  }


  public JsonRpcResponse callHttpJsonRpc(Object target, HttpServletRequest request,
      HttpServletResponse response) {
    return callHttpJsonRpc(target, toJsonRpcRequest(request), response);
  }

  public JsonRpcResponse callHttpJsonRpc(Object target, JsonRpcRequest request,
      HttpServletResponse response) {

    response.setContentType("application/json;charset=UTF-8");
    response.setHeader("Access-Control-Allow-Origin", "*");
    response.setHeader("Access-Control-Allow-Methods", "*");
    response.setHeader("Access-Control-Allow-Headers", "*");

    JsonRpcResponse result = JsonRpcUtils.callJsonRpc(mapper, target, request);
    if (result.hasError()) {
      response.setStatus(500);
    } else {
      response.setStatus(200);
    }
    return result;
    // return toJsonRpcErrorResponse(
    // e.getMessage() != null ? e.getMessage() : e.getCause().getMessage(), e, request);

  }

  // private static JsonRpcResponse toJsonRpcErrorResponse(String faultString, Throwable t,
  // JsonRpcRequest jreq) {
  // JsonRpcResponse jres = new JsonRpcResponse();
  // jres.setId(jreq.getId());
  // jres.setError(JsonRpcError.createRpcFault("Server.userException", faultString, t));
  // return jres;
  // }


  public JsonRpcRequest toJsonRpcRequest(HttpServletRequest request) {
    try {
      String str = IoStreamUtils.readAsString(getInputStream(request), StandardCharsets.UTF_8);
      JsonRpcRequest jreq = mapper.toObject(str, JsonRpcRequest.class);
      return jreq;
    } catch (IOException e) {
      throw Try.rethrow(e);
    }
  }


  private static InputStream getInputStream(HttpServletRequest request) throws IOException {
    InputStream is = request.getInputStream();
    String contentEncoding = request.getHeader("Content-Encoding");

    if (contentEncoding == null) {
      return is;
    }
    if (contentEncoding.equals("deflate")) {
      return new InflaterInputStream(is);
    } else if (contentEncoding.equals("gzip")) {
      return new GZIPInputStream(is);
    } else {
      return is;
    }
  }

}

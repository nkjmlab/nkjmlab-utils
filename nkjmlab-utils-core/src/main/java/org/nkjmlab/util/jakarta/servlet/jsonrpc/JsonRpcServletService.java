package org.nkjmlab.util.jakarta.servlet.jsonrpc;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;
import org.nkjmlab.util.java.function.Try;
import org.nkjmlab.util.java.io.ReaderUtils;
import org.nkjmlab.util.java.json.JsonMapper;
import org.nkjmlab.util.jsonrpc.JsonRpcCaller;
import org.nkjmlab.util.jsonrpc.JsonRpcRequest;
import org.nkjmlab.util.jsonrpc.JsonRpcResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JsonRpcServletService {

  private final JsonMapper mapper;
  private final JsonRpcCaller jsonRpcCaller;
  private final Map<String, String> responseHeaders;

  private static final Map<String, String> DEFAULT_RESPONSE_HEADERS =
      Map.of("Access-Control-Allow-Origin", "*", "Access-Control-Allow-Methods", "*",
          "Access-Control-Allow-Headers", "*");

  public JsonRpcServletService(JsonMapper mapper) {
    this(mapper, DEFAULT_RESPONSE_HEADERS);
  }

  public JsonRpcServletService(JsonMapper mapper, Map<String, String> responseHeaders) {
    this.mapper = mapper;
    this.jsonRpcCaller = new JsonRpcCaller(mapper);
    this.responseHeaders = responseHeaders;
  }


  public JsonRpcServletResponse callHttpJsonRpc(Object target, HttpServletRequest request,
      HttpServletResponse response) {
    return callHttpJsonRpc(target, toJsonRpcRequest(request), response);
  }

  public JsonRpcServletResponse callHttpJsonRpc(Object target, JsonRpcRequest jreq,
      HttpServletResponse response) {

    JsonRpcResponse jres = jsonRpcCaller.callJsonRpc(target, jreq);

    if (jres.hasError()) {
      response.setStatus(500);
    } else {
      response.setStatus(200);
    }

    prepareResponse(response);

    return new JsonRpcServletResponse(jres, toJsonString(jres), response);
  }



  private void prepareResponse(HttpServletResponse response) {
    response.setContentType("application/json;charset=UTF-8");
    responseHeaders.entrySet().stream()
        .forEach(en -> response.setHeader(en.getKey(), en.getValue()));
  }

  /**
   * Convert {@link HttpServletRequest} to {@link JsonRpcRequest}
   *
   * @param request
   * @return
   */
  public JsonRpcRequest toJsonRpcRequest(HttpServletRequest request) {
    InputStream is = getInputStream(request);
    InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
    String str = ReaderUtils.readAsString(reader);
    JsonRpcRequest jreq = jsonRpcCaller.toJsonRpcRequest(str);
    return jreq;
  }


  private static InputStream getInputStream(HttpServletRequest request) {
    try {
      String contentEncoding = request.getHeader("Content-Encoding");

      if (contentEncoding == null) {
        return request.getInputStream();
      }
      if (contentEncoding.equals("deflate")) {
        return new InflaterInputStream(request.getInputStream());
      } else if (contentEncoding.equals("gzip")) {
        return new GZIPInputStream(request.getInputStream());
      } else {
        return request.getInputStream();
      }
    } catch (IOException e) {
      throw Try.rethrow(e);
    }

  }

  public String toJsonString(JsonRpcResponse jres) {
    return mapper.toJson(jres);
  }

}

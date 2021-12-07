package org.nkjmlab.util.javax.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.nkjmlab.util.java.json.JsonMapper;
import org.nkjmlab.util.java.json.jsonrpc.JsonRpcRequest;
import org.nkjmlab.util.java.json.jsonrpc.JsonRpcResponse;
import org.nkjmlab.util.java.json.jsonrpc.JsonRpcUtils;

public class JsonRpcService {
  private static final org.nkjmlab.util.java.logging.Logger log =
      org.nkjmlab.util.java.logging.LogManager.getLogger();

  private JsonMapper mapper;

  public JsonRpcService(JsonMapper mapper) {
    this.mapper = mapper;
  }

  public JsonRpcResponse callJsonRpc(Object service, JsonRpcRequest jreq) {
    return JsonRpcUtils.callJsonRpc(mapper, service, jreq);
  }

  public JsonRpcResponse callHttpJsonRpc(Object service, HttpServletRequest req,
      HttpServletResponse res) {
    JsonRpcRequest jreq = toJsonRpcRequest(mapper, req);
    return callHttpJsonRpc(service, jreq, res);
  }

  public JsonRpcResponse callHttpJsonRpc(Object service, JsonRpcRequest jreq,
      HttpServletResponse res) {

    setContentTypeToJson(res);
    setAccessControlAllowOriginToWildCard(res);
    setAccessControlAllowMethodsToWildCard(res);
    setAccessControlAllowHeadersToWildCard(res);

    JsonRpcResponse result = JsonRpcUtils.callJsonRpc(mapper, service, jreq);
    if (result.hasError()) {
      res.setStatus(500);
    } else {
      res.setStatus(200);
    }
    return result;
  }

  public static JsonRpcRequest toJsonRpcRequest(JsonMapper mapper, HttpServletRequest req) {
    try {
      return JsonRpcUtils.toJsonRpcRequest(mapper, getInputStream(req));
    } catch (Throwable e) {
      log.error(e, e);
      throw new RuntimeException(e);
    }
  }

  private static InputStream getInputStream(HttpServletRequest req) throws IOException {
    InputStream is = req.getInputStream();
    String contentEncoding = req.getHeader("Content-Encoding");

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

  public static void setContentTypeToJson(HttpServletResponse res) {
    res.setContentType("application/json;charset=UTF-8");
  }

  public static void setAccessControlAllowOriginToWildCard(HttpServletResponse res) {
    res.setHeader("Access-Control-Allow-Origin", "*");
  }

  public static void setAccessControlAllowMethodsToWildCard(HttpServletResponse res) {
    res.setHeader("Access-Control-Allow-Methods", "*");

  }

  public static void setAccessControlAllowHeadersToWildCard(HttpServletResponse res) {
    res.setHeader("Access-Control-Allow-Headers", "*");

  }

}

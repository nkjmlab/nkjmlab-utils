package org.nkjmlab.util.jakarta.servlet.jsonrpc;

import org.nkjmlab.util.jsonrpc.JsonRpcResponse;
import jakarta.servlet.http.HttpServletResponse;

public class JsonRpcServletResponse {

  private final JsonRpcResponse jsonRpcResponse;
  private final String json;

  private final HttpServletResponse servletResponse;


  public JsonRpcServletResponse(JsonRpcResponse jsonRpcResponse, String json,
      HttpServletResponse servletResponse) {
    this.json = json;
    this.jsonRpcResponse = jsonRpcResponse;
    this.servletResponse = servletResponse;
  }

  public JsonRpcResponse getJsonRpcResponse() {
    return jsonRpcResponse;
  }

  public HttpServletResponse getServletResponse() {
    return servletResponse;
  }

  public String getJson() {
    return json;
  }

}

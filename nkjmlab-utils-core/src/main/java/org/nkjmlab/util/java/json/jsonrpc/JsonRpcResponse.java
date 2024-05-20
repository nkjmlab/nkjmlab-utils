package org.nkjmlab.util.java.json.jsonrpc;

public record JsonRpcResponse(String jsonrpc, String id, Object result, JsonRpcError error) {

  public JsonRpcResponse(String id, JsonRpcError error) {
    this("2.0", id, null, error);
  }

  public JsonRpcResponse(String id, Object result) {
    this("2.0", id, result, null);
  }

  public boolean hasError() {
    return error != null;
  }
}

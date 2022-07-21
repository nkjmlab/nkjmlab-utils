package org.nkjmlab.util.jsonrpc;

public class JsonRpcResponse {

  private String jsonrpc = "2.0";

  private String id;
  private Object result;
  private JsonRpcError error;

  public JsonRpcResponse() {}

  public JsonRpcResponse(String id, JsonRpcError error) {
    this.id = id;
    this.error = error;
  }

  public JsonRpcResponse(String id, Object result) {
    this.id = id;
    this.result = result;
  }

  public boolean hasError() {
    return error != null;
  }

  public JsonRpcError getError() {
    return error;
  }

  public void setError(JsonRpcError error) {
    this.error = error;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Object getResult() {
    return result;
  }

  public void setResult(Object result) {
    this.result = result;
  }

  public String getJsonrpc() {
    return jsonrpc;
  }

  public void setJsonrpc(String jsonrpc) {
    this.jsonrpc = jsonrpc;
  }

  @Override
  public String toString() {
    return "JsonRpcResponse [jsonrpc=" + jsonrpc + ", id=" + id + ", result=" + result + ", error="
        + error + "]";
  }


}

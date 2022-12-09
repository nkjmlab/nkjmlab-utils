package org.nkjmlab.util.jsonrpc;

import java.util.Arrays;

/**
 * The class represents a JSON-RPC request object.
 *
 * @see <a href="https://www.jsonrpc.org/specification#request_object">JSON-RPC 2.0
 *      Specification</a>
 * @author nkjm
 *
 */
public class JsonRpcRequest {

  private String jsonrpc;
  private String id;
  private String method;
  private Object[] params;

  public JsonRpcRequest() {
    this(null, null, new Object[0]);
  }

  public JsonRpcRequest(String id, String method, Object[] params) {
    this.id = id;
    this.method = method;
    this.params = params;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public Object[] getParams() {
    return params;
  }

  public void setParams(Object[] params) {
    if (params == null) {
      this.params = new Object[0];
      return;
    }
    this.params = params;
  }

  public String getJsonrpc() {
    return jsonrpc;
  }

  public void setJsonrpc(String jsonrpc) {
    this.jsonrpc = jsonrpc;
  }

  @Override
  public String toString() {
    return "JsonRpcRequest [jsonrpc=" + jsonrpc + ", id=" + id + ", method=" + method + ", params="
        + Arrays.toString(params) + "]";
  }


}

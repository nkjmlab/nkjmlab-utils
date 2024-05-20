package org.nkjmlab.util.java.json.jsonrpc;

/**
 * The class represents a JSON-RPC request object.
 *
 * @see <a href="https://www.jsonrpc.org/specification#request_object">JSON-RPC 2.0
 *     Specification</a>
 * @author nkjm
 */
public record JsonRpcRequest(String jsonrpc, String id, String method, Object[] params) {

  public JsonRpcRequest() {
    this("2.0", null, null, new Object[0]);
  }

  public JsonRpcRequest(String id, String method, Object[] params) {
    this("2.0", id, method, params);
  }
}

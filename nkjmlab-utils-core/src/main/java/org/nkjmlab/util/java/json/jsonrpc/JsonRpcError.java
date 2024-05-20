package org.nkjmlab.util.java.json.jsonrpc;

import org.nkjmlab.util.java.lang.ExceptionUtils;

/**
 * @see <a href="https://www.jsonrpc.org/specification#error_object">JSON-RPC 2.0 Specification</a>
 * @author nkjm
 */
public record JsonRpcError(int code, String message, String data) {

  public static JsonRpcError create(int faultCode, String faultString, Throwable t) {
    return new JsonRpcError(faultCode, faultString, ExceptionUtils.getMessageWithStackTrace(t));
  }

  /**
   * The JSON sent is not a valid Request object.
   *
   * @see <a href="https://www.jsonrpc.org/specification#error_object">JSON-RPC 2.0
   *     Specification</a>
   * @param e
   * @return
   */
  public static JsonRpcError createInvalidRequest(Throwable e) {
    return create(-32600, "Invalid Request", e);
  }

  /**
   * The method does not exist / is not available.
   *
   * @see <a href="https://www.jsonrpc.org/specification#error_object">JSON-RPC 2.0
   *     Specification</a>
   * @param e
   * @return
   */
  public static JsonRpcError createMethodNotFound(Throwable e) {
    return create(-32601, "Method not found", e);
  }

  /**
   * Invalid method parameter(s).
   *
   * @see <a href="https://www.jsonrpc.org/specification#error_object">JSON-RPC 2.0
   *     Specification</a>
   * @param e
   * @return
   */
  public static JsonRpcError createInvalidParams(Throwable e) {
    return create(-32602, "Invalid params", e);
  }

  /**
   * Internal JSON-RPC error.
   *
   * @see <a href="https://www.jsonrpc.org/specification#error_object">JSON-RPC 2.0
   *     Specification</a>
   * @param e
   * @return
   */
  public static JsonRpcError createInternalError(Throwable e) {
    return create(-32603, "Internal error", e);
  }

  /**
   * Invalid JSON was received by the server. An error occurred on the server while parsing the JSON
   * text.
   *
   * @see <a href="https://www.jsonrpc.org/specification#error_object">JSON-RPC 2.0
   *     Specification</a>
   * @param e
   * @return
   */
  public static JsonRpcError createParseError(Exception e) {
    return create(-32700, "Parse error", e);
  }
}

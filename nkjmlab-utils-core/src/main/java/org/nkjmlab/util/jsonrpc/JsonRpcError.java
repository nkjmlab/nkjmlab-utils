package org.nkjmlab.util.jsonrpc;

import org.nkjmlab.util.java.lang.ExceptionUtils;

/**
 * @see <a href="https://www.jsonrpc.org/specification#error_object">JSON-RPC 2.0 Specification</a>
 * @author nkjm
 *
 */
public class JsonRpcError {

  private int code;
  private String message;
  private String data;

  public JsonRpcError() {}

  public JsonRpcError(int code, String message, String data) {
    this.code = code;
    this.message = message;
    this.data = data;
  }

  public static JsonRpcError create(int faultCode, String faultString, Throwable t) {
    return new JsonRpcError(faultCode, faultString, ExceptionUtils.getMessageWithStackTrace(t));
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public void setData(String data) {
    this.data = data;
  }

  public String getMessage() {
    return message;
  }


  public String getData() {
    return data;
  }


  @Override
  public String toString() {
    return "JsonRpcError [code=" + code + ", message=" + message + ", data=" + data + "]";
  }

  /**
   *
   * The JSON sent is not a valid Request object.
   *
   * @param e
   * @return
   */

  public static JsonRpcError createInvalidRequest(Throwable e) {
    return create(-32600, "Invalid Request", e);
  }

  /**
   * The method does not exist / is not available.
   *
   * @param e
   * @return
   */
  public static JsonRpcError createMethodNotFound(Throwable e) {
    return create(-32601, "Method not found", e);
  }

  /**
   * Invalid method parameter(s).
   *
   * @param e
   * @return
   */
  public static JsonRpcError createInvalidParams(Throwable e) {
    return create(-32602, "Invalid params", e);
  }

  /**
   * Internal JSON-RPC error.
   *
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
   * @param e
   * @return
   */
  public static JsonRpcError createParseError(Exception e) {
    return create(-32700, "Parse error", e);
  }



}

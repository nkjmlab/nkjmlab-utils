package org.nkjmlab.util.jakarta.servlet.jsonrpc;

import org.nkjmlab.util.java.json.JsonMapper;
import org.nkjmlab.util.java.json.jsonrpc.JsonRpcRequest;
import org.nkjmlab.util.java.json.jsonrpc.JsonRpcResponse;
import org.nkjmlab.util.java.lang.ParameterizedStringFormatter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class BasicJsonRpcServletService extends JsonRpcServletService {

  private static final org.nkjmlab.util.java.logging.SimpleLogger log =
      org.nkjmlab.util.java.logging.LogManager.createLogger();

  public BasicJsonRpcServletService(JsonMapper mapper) {
    super(mapper);
  }

  /**
   * Handle request JSON RPC service.
   *
   * @param ctx
   * @return
   */
  public JsonRpcServletResponse handle(
      Object service, HttpServletRequest req, HttpServletResponse res) {
    JsonRpcRequest jreq = toJsonRpcRequest(req);
    JsonRpcServletResponse jrpcSrvletRes = callHttpJsonRpc(service, jreq, res);
    JsonRpcResponse jres = jrpcSrvletRes.getJsonRpcResponse();
    if (jres.hasError()) {
      log.warn(
          ParameterizedStringFormatter.LENGTH_512.format(
              "[{}#{}], Req: id={}, Error: code={}, msg={}, detail = {}",
              service.getClass().getName(),
              jreq.method(),
              jreq.id(),
              jres.error().code(),
              jres.error().message(),
              jres.error().data()));
    }
    return jrpcSrvletRes;
  }
}

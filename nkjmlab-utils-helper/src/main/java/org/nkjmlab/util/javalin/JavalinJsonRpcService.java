package org.nkjmlab.util.javalin;

import org.nkjmlab.util.jakarta.servlet.JsonRpcService;
import org.nkjmlab.util.java.json.JsonMapper;
import org.nkjmlab.util.java.lang.ParameterizedStringFormat;
import org.nkjmlab.util.jsonrpc.JsonRpcRequest;
import org.nkjmlab.util.jsonrpc.JsonRpcResponse;
import io.javalin.http.Context;

public class JavalinJsonRpcService extends JsonRpcService {

  private static final org.nkjmlab.util.java.logging.SimpleLogger log =
      org.nkjmlab.util.java.logging.LogManager.createLogger();


  public JavalinJsonRpcService(JsonMapper mapper) {
    super(mapper);
  }


  /**
   * Handle context with JSON RPC service.
   *
   * @param ctx
   * @param service
   */
  public void handle(Context ctx, Object service) {
    JsonRpcRequest jreq = toJsonRpcRequest(ctx.req());
    JsonRpcResponse jres = callHttpJsonRpc(service, jreq, ctx.res());
    if (jres.hasError()) {
      log.warn(ParameterizedStringFormat.LENGTH_512.format(
          "[{}#{}], Req: id={}, method={}, Error: code={}, msg={}, detail = {}",
          service.getClass().getName(), jreq.getMethod(), jreq.getId(), jres.getError().getCode(),
          jres.getError().getMessage(), jres.getError().getData()));
    }
    String ret = toJsonString(jres);
    ctx.result(ret);
  }

}

package org.nkjmlab.util.javalin;

import org.nkjmlab.util.java.json.JsonMapper;
import org.nkjmlab.util.javax.servlet.JsonRpcService;
import org.nkjmlab.util.jsonrpc.JsonRpcRequest;
import org.nkjmlab.util.jsonrpc.JsonRpcResponse;
import io.javalin.http.Context;

public class JavalinJsonRpcService extends JsonRpcService {

  private static final org.nkjmlab.util.java.logging.SimpleLogger log =
      org.nkjmlab.util.java.logging.LogManager.createLogger();


  public JavalinJsonRpcService(JsonMapper mapper) {
    super(mapper);
  }

  public void handle(Context ctx, Object service) {
    JsonRpcRequest jreq = toJsonRpcRequest(ctx.req);
    JsonRpcResponse jres = callHttpJsonRpc(service, jreq, ctx.res);
    String ret = toJsonString(jres);
    if (jres.hasError()) {
      log.warn("In {}, Error: {}, {}, Req: {}, {}", service.getClass().getSimpleName(),
          jres.getError().getCode(), jres.getError().getMessage(), jreq.getId(), jreq.getMethod());
    }
    ctx.result(ret);
  }

}

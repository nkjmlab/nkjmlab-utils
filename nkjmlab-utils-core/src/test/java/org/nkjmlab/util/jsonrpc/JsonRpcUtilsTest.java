package org.nkjmlab.util.jsonrpc;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.nkjmlab.util.jackson.JacksonMapper;

class JsonRpcUtilsTest {


  @Test
  void testCallJsonRpc() {
    JsonRpcRequest req = JsonRpcUtils.createRequest("getString", "hoge");
    assertThat(req.getMethod()).isEqualTo("getString");
    assertThat(req.getParams()[0]).isEqualTo("hoge");
    JsonRpcResponse res =
        JsonRpcUtils.callJsonRpc(JacksonMapper.getDefaultMapper(), new StubClass(), req);
    assertThat(res.getResult()).isEqualTo("hoge");
  }

  @Test
  void testCallJsonRpcFail() {

    assertThatThrownBy(() -> {
      JsonRpcRequest req = JsonRpcUtils.createRequest("getStrin", "hoge");
      JsonRpcUtils.callJsonRpc(JacksonMapper.getDefaultMapper(), new StubClass(), req);
    }).isInstanceOfSatisfying(Exception.class,
        e -> assertThat(e.getMessage()).contains("Invalid method"));
  }


  public class StubClass {
    public String getString(String str) {
      return str;
    }
  }
}

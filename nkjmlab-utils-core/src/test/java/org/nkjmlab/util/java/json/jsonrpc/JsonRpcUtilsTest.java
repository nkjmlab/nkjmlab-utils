package org.nkjmlab.util.java.json.jsonrpc;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.nkjmlab.util.jackson.JacksonMapper;

class JsonRpcUtilsTest {

  @Test
  void testCallJsonRpc() {
    Object[] params = {"hoge"};
    JsonRpcRequest req = new JsonRpcRequest("1", "getString", params);
    assertThat(req.method()).isEqualTo("getString");
    assertThat(req.params()[0]).isEqualTo("hoge");
    JsonRpcResponse res =
        new JsonRpcCaller(JacksonMapper.getDefaultMapper()).callJsonRpc(new StubClass(), req);
    assertThat(res.result()).isEqualTo("hoge");
  }

  @Test
  void testCallJsonRpcFail() {

    Object[] params = {"hogehogehogehogehogehogehogehogehogehogehogehogehogehogehogehoge"};
    JsonRpcRequest req = new JsonRpcRequest("2", "getStrin", params);
    JsonRpcResponse res =
        new JsonRpcCaller(JacksonMapper.getDefaultMapper()).callJsonRpc(new StubClass(), req);
    assertThat(res.error()).isNotNull();
  }

  public class StubClass {
    public String getString(String str) {
      return str;
    }
  }
}

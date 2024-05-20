package org.nkjmlab.util.java.json.jsonrpc;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.nkjmlab.util.jackson.JacksonMapper;

class JsonRpcCallerTest {
  private final JsonRpcCaller caller = new JsonRpcCaller(JacksonMapper.getDefaultMapper());

  @Test
  void testCallJsonRpc() {
    JsonRpcResponse response =
        caller.callJsonRpc(new Service.Impl(), new JsonRpcRequest("1", "getString", new Object[0]));
    assertThat(response.result().toString()).isEqualTo("stringVal");
  }

  @Test
  void testCallJsonRpc2() {
    JsonRpcResponse response =
        caller.callJsonRpc(
            new Service.Impl(), new JsonRpcRequest("1", "convertString", new Object[] {"1"}));
    assertThat(response.result().toString()).isEqualTo("1");
  }

  @Test
  void testCallJsonRpcInvalidParam1() {
    JsonRpcResponse response =
        caller.callJsonRpc(
            new Service.Impl(), new JsonRpcRequest("1", "getString", new Object[] {"test"}));
    assertThat(response.error().code()).isEqualTo(-32602);
    assertThat(response.error().message()).isEqualTo("Invalid params");
  }

  @Test
  void testCallJsonRpcInvalidParam2() {
    JsonRpcResponse response =
        caller.callJsonRpc(
            new Service.Impl(), new JsonRpcRequest("1", "convertString", new Object[] {"1", "2"}));
    assertThat(response.error().code()).isEqualTo(-32602);
    assertThat(response.error().message()).isEqualTo("Invalid params");
  }

  @Test
  void testCallJsonRpcMethodNotFound() {
    JsonRpcResponse response =
        caller.callJsonRpc(new Service.Impl(), new JsonRpcRequest("1", "getStrin", null));
    assertThat(response.error().code()).isEqualTo(-32601);
    assertThat(response.error().message()).isEqualTo("Method not found");
  }
}

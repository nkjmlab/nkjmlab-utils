package org.nkjmlab.util.java.json.jsonrpc;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class JsonRpcErrorTest {

  @Test
  void testCreateMethodNotFound() {
    Throwable e = new Throwable("Method not found");
    JsonRpcError error = JsonRpcError.createMethodNotFound(e);

    assertThat(error).isNotNull();
    assertThat(error.code()).isEqualTo(-32601);
    assertThat(error.message()).isEqualTo("Method not found");
  }

  @Test
  void testCreateInvalidParams() {
    Throwable e = new Throwable("Invalid params");
    JsonRpcError error = JsonRpcError.createInvalidParams(e);

    assertThat(error).isNotNull();
    assertThat(error.code()).isEqualTo(-32602);
    assertThat(error.message()).isEqualTo("Invalid params");
  }

  @Test
  void testCreateInternalError() {
    Throwable e = new Throwable("Internal error");
    JsonRpcError error = JsonRpcError.createInternalError(e);

    assertThat(error).isNotNull();
    assertThat(error.code()).isEqualTo(-32603);
    assertThat(error.message()).isEqualTo("Internal error");
  }
}

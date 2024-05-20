package org.nkjmlab.util.java.json.jsonrpc;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JsonRpcResponseTest {

  private JsonRpcResponse jsonRpcResponse;

  @BeforeEach
  void setUp() {
    jsonRpcResponse = new JsonRpcResponse("1", new Object());
  }

  @Test
  void testHasError() {
    assertThat(jsonRpcResponse.hasError()).isFalse();
    jsonRpcResponse = new JsonRpcResponse("1", new JsonRpcError(0, "error", null));
    assertThat(jsonRpcResponse.hasError()).isTrue();
  }

  @Test
  void testGetError() {
    JsonRpcError error = new JsonRpcError(0, "error", null);
    jsonRpcResponse = new JsonRpcResponse("1", error);
    assertThat(jsonRpcResponse.error()).isEqualTo(error);
  }

  @Test
  void testGetId() {
    assertThat(jsonRpcResponse.id()).isEqualTo("1");
  }

  @Test
  void testGetResult() {
    Object result = new Object();
    jsonRpcResponse = new JsonRpcResponse("1", result);
    assertThat(jsonRpcResponse.result()).isEqualTo(result);
  }
}

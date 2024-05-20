package org.nkjmlab.util.java.json.jsonrpc;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JsonRpcRequestTest {

  private JsonRpcRequest jsonRpcRequest;

  @BeforeEach
  void setUp() {
    jsonRpcRequest = new JsonRpcRequest("1", "testMethod", new Object[] {"param1", "param2"});
  }

  @Test
  void testGetId() {
    assertThat(jsonRpcRequest.id()).isEqualTo("1");
  }

  @Test
  void testGetMethod() {
    assertThat(jsonRpcRequest.method()).isEqualTo("testMethod");
  }

  @Test
  void testGetParams() {
    assertThat(jsonRpcRequest.params()).isEqualTo(new Object[] {"param1", "param2"});
  }
}

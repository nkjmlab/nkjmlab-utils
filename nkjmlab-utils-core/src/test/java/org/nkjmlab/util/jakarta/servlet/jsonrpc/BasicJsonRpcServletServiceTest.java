package org.nkjmlab.util.jakarta.servlet.jsonrpc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.nkjmlab.util.jackson.JacksonMapper;
import org.nkjmlab.util.java.json.jsonrpc.JsonRpcRequest;
import org.nkjmlab.util.java.json.jsonrpc.JsonRpcResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

class BasicJsonRpcServletServiceTest {

  @Mock private JacksonMapper jacksonMapper;

  @Mock private HttpServletRequest httpServletRequest;

  @Mock private HttpServletResponse httpServletResponse;

  @Mock private JsonRpcRequest jsonRpcRequest;

  @Mock private JsonRpcResponse jsonRpcResponse;

  @InjectMocks private BasicJsonRpcServletService basicJsonRpcServletService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testHandle() {
    Object service = new Object();
    when(basicJsonRpcServletService.toJsonRpcRequest(httpServletRequest))
        .thenReturn(jsonRpcRequest);
    when(basicJsonRpcServletService.callHttpJsonRpc(
            eq(service), eq(jsonRpcRequest), eq(httpServletResponse)))
        .thenReturn(new JsonRpcServletResponse(jsonRpcResponse, "json", httpServletResponse));
    when(jsonRpcResponse.hasError()).thenReturn(false);

    JsonRpcServletResponse result =
        basicJsonRpcServletService.handle(service, httpServletRequest, httpServletResponse);

    assertThat(result).isNotNull();
    assertThat(result.getJsonRpcResponse()).isEqualTo(jsonRpcResponse);
    verify(basicJsonRpcServletService, times(1)).toJsonRpcRequest(httpServletRequest);
    verify(basicJsonRpcServletService, times(1))
        .callHttpJsonRpc(eq(service), eq(jsonRpcRequest), eq(httpServletResponse));
    verify(jsonRpcResponse, times(1)).hasError();
  }
}

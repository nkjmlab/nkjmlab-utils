package org.nkjmlab.util.java.json.jsonrpc;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.nkjmlab.util.jackson.JacksonMapper;
import org.nkjmlab.util.java.json.JsonMapper;
import org.nkjmlab.util.java.net.UrlUtils;

class JsonRpcClientFactoryTest {

  @Mock private JsonMapper jsonMapper;

  @InjectMocks private JsonRpcClientFactory jsonRpcClientFactory;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testCreate() {
    String url = "http://example.com";
    Service client =
        JsonRpcClientFactory.create(
            JacksonMapper.getDefaultMapper(), Service.class, UrlUtils.of(url));

    assertThat(client).isNotNull();
  }
}

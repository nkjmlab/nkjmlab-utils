package org.nkjmlab.util.openai.chat;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.nkjmlab.util.openai.chat.model.ChatResponse;

class ChatCompletionTest {

  @Test
  void test() {
    ChatResponse res =
        ChatCompletion.builder()
            .apiKeyFromProperties("/conf/openai4j.properties")
            .build()
            .createCompletion("What do you think would be good for dinner tonight?");
    assertThat(res.choices().size()).isNotEqualTo(0);
  }
}

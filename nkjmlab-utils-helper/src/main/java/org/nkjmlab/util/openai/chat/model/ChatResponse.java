package org.nkjmlab.util.openai.chat.model;

import java.util.List;
import org.nkjmlab.util.openai.chat.model.ChatRequest.ChatMessage;

public record ChatResponse(String id, String object, Long created, List<ChatResponse.Choice> choices,
    ChatResponse.Usage usage, ChatResponse.Error error) {

  public record Choice(Integer index, ChatMessage message, String finish_reason) {
  }

  public record Usage(Integer prompt_tokens, Integer completion_tokens, Integer total_tokens) {
  }

  public record Error(String message, String type, String code) {
  }

}

package org.nkjmlab.util.openai.chat.model;

import java.util.List;
import org.nkjmlab.util.openai.chat.model.ChatRequest.ChatMessage;
import org.nkjmlab.util.openai.chat.model.ChatResponse.Choice;
import org.nkjmlab.util.openai.chat.model.ChatResponse.Error;
import org.nkjmlab.util.openai.chat.model.ChatResponse.Usage;

public record ChatResponse(String id, String object, Long created, List<Choice> choices,
    Usage usage, Error error) {

  public record Choice(Integer index, ChatMessage message, String finish_reason) {
  }

  public record Usage(Integer prompt_tokens, Integer completion_tokens, Integer total_tokens) {
  }

  public record Error(String message, String type, String code) {
  }

}

package org.nkjmlab.util.openai.chat.model;

import java.util.Arrays;
import java.util.List;

public record ChatRequest(
    String model,
    Float temperature,
    Float top_p,
    Integer n,
    Integer max_tokens,
    Float presence_penalty,
    Float frequency_penalty,
    String user,
    List<ChatRequest.ChatMessage> messages) {

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private String model = Model.GPT_3_5_TURBO.getValue();
    private Float temperature;
    private Float top_p;
    private Integer n;
    private Integer max_tokens;
    private Float presence_penalty;
    private Float frequency_penalty;
    private String user;
    private List<ChatMessage> messages;

    public Builder model(Model model) {
      this.model = model.getValue();
      return this;
    }

    public Builder model(String model) {
      this.model = model;
      return this;
    }

    public Builder messages(ChatMessage... messages) {
      this.messages = Arrays.asList(messages);
      return this;
    }

    public ChatRequest build() {
      return new ChatRequest(
          model,
          temperature,
          top_p,
          n,
          max_tokens,
          presence_penalty,
          frequency_penalty,
          user,
          messages);
    }
  }

  /**
   * <a href="https://platform.openai.com/docs/models/">Models - OpenAI API</a>
   *
   * @author nkjm
   */
  public static enum Model {
    GPT_3_5_TURBO("gpt-3.5-turbo"),
    GPT_3_5_TURBO_16K("gpt-3.5-turbo-16k"),
    GPT_3_5_TURBO_0613("gpt-3.5-turbo-0613"),
    GPT_3_5_TURBO_16K_0613("gpt-3.5-turbo-16k-0613"),
    GPT_4("gpt-4"),
    GPT_4_32K("gpt-4-32k"),
    GPT_4_0613("gpt-4-0613"),
    GPT_4_32K_0613("gpt-4-32k-0613");

    private final String value;

    Model(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return getValue();
    }
  }
  /**
   * <a href="https://platform.openai.com/docs/guides/chat/introduction">Chat completion - OpenAI
   * API</a>
   *
   * @author nkjm
   */
  public static record ChatMessage(String role, String content) {

    public static ChatMessage of(ChatMessage.Role role, String text) {
      return new ChatMessage(role.getValue(), text);
    }

    /**
     * <a href="https://platform.openai.com/docs/guides/chat/introduction">Chat completion - OpenAI
     * API</a>
     *
     * @author nkjm
     */
    public static enum Role {
      USER("user"),
      SYSTEM("system"),
      ASSISTANT("assistant");

      private final String value;

      Role(String value) {
        this.value = value;
      }

      public String getValue() {
        return value;
      }

      @Override
      public String toString() {
        return getValue();
      }
    }
  }
}

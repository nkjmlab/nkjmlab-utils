package org.nkjmlab.util.openai.chat;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Properties;
import org.nkjmlab.util.openai.OpenAi4jException;
import org.nkjmlab.util.openai.chat.model.ChatRequest;
import org.nkjmlab.util.openai.chat.model.ChatRequest.ChatMessage;
import org.nkjmlab.util.openai.chat.model.ChatRequest.ChatMessage.Role;
import org.nkjmlab.util.openai.chat.model.ChatResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * <a href="https://platform.openai.com/docs/api-reference/chat/create">API Reference - OpenAI
 * API</a>
 *
 * @author nkjm
 *
 */
public class ChatCompletion {

  private final String authHeader;
  private final URI apiUrl;
  private final HttpClient httpClient;
  private final ObjectMapper objectMapper;

  public static void main(String[] args) {
    ChatResponse res = ChatCompletion.builder().build()
        .createCompletion("What do you think would be good for dinner tonight?");
    System.out.println(res);
  }


  private ChatCompletion(Builder builder) {
    this.authHeader = "Bearer " + builder.apiKey;
    this.apiUrl = builder.apiUrl;

    this.httpClient = HttpClient.newBuilder()
        .connectTimeout(Duration.of(builder.timeout, ChronoUnit.MILLIS)).build();

    this.objectMapper = builder.objectMapper;
  }



  public ChatResponse createCompletion(String text) throws OpenAi4jException {
    return createCompletion(
        ChatRequest.builder().messages(ChatMessage.of(Role.USER, text)).build());
  }

  public ChatResponse createCompletion(ChatRequest chatRequest) throws OpenAi4jException {
    try {
      HttpRequest req = createHttpRequest(chatRequest);
      HttpResponse<byte[]> res = httpClient.send(req, BodyHandlers.ofByteArray());
      ChatResponse chatResponse = objectMapper.readValue(res.body(), ChatResponse.class);

      return chatResponse;
    } catch (Exception e) {
      throw new OpenAi4jException("error occurs when it calls completion api", e);
    }
  }

  private HttpRequest createHttpRequest(ChatRequest chatRequest) throws JsonProcessingException {
    return HttpRequest.newBuilder().uri(apiUrl).header("Authorization", authHeader)
        .header("Content-Type", "application/json")
        .POST(HttpRequest.BodyPublishers.ofByteArray(objectMapper.writeValueAsBytes(chatRequest)))
        .build();
  }


  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private String apiKey = readApiKey();
    private long timeout = 2 * 60 * 1000L;
    private URI apiUrl = toURI("https://api.openai.com/v1/chat/completions");
    private ObjectMapper objectMapper =
        new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);


    private static String readApiKey() {
      {
        String key = readResource("/openai4j.properties");
        if (key != null) {
          return key;
        }
      }
      {
        String key = System.getenv("OPENAI4J_SECRET_TOKEN");
        if (key != null && key.toString().length() > 0) {
          return key.toString();
        }
      }
      return null;
    }

    private static String readResource(String resourceName) {
      try (InputStream is = ChatCompletion.class.getResourceAsStream(resourceName)) {
        Properties props = new Properties();
        props.load(is);
        Object key = props.get("secretKey");
        if (key != null && key.toString().length() > 0) {
          return key.toString();
        }
        return null;
      } catch (Exception e) {
        return null;
      }

    }

    public Builder apiKey(String apiKey) {
      this.apiKey = apiKey;
      return this;
    }

    public Builder apiKeyResource(String resourceName) {
      this.apiKey = readResource(resourceName);
      return this;
    }

    public Builder timeout(long timeout) {
      this.timeout = timeout;
      return this;
    }

    public Builder apiUrl(String apiUrl) {

      this.apiUrl = toURI(apiUrl);
      return this;
    }

    public Builder mapper(ObjectMapper mapper) {
      this.objectMapper = mapper;
      return this;
    }

    public ChatCompletion build() {
      return new ChatCompletion(this);
    }

    private static URI toURI(String uri) {
      try {
        return new URI(uri);
      } catch (URISyntaxException e) {
        throw new OpenAi4jException(uri + " is invalid", e);
      }
    }


  }

}

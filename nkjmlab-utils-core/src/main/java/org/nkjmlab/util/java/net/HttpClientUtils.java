package org.nkjmlab.util.java.net;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.nkjmlab.util.java.function.Try;

public class HttpClientUtils {

  private static final HttpClient client = newDefaultHttpClient();

  public static HttpResponse<String> getResponse(HttpClient client, URI uri) {
    try {
      HttpRequest request = HttpRequest.newBuilder().uri(uri).build();
      return client.send(request, HttpResponse.BodyHandlers.ofString());
    } catch (IOException | InterruptedException e) {
      throw Try.rethrow(e);
    }
  }

  public static String getContent(HttpClient client, URI uri) {
    return getResponse(client, uri).body();
  }

  public static String getContent(HttpClient client, String uri) {
    return getContent(client, UrlUtils.toUri(uri));
  }

  public static String getContent(URI uri) {
    return getResponse(client, uri).body();
  }

  public static String getContent(String uri) {
    return getContent(client, UrlUtils.toUri(uri));
  }

  public static HttpClient newDefaultHttpClient() {
    return HttpClient.newHttpClient();
  }

}

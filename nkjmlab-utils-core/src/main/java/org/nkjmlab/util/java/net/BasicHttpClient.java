package org.nkjmlab.util.java.net;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import org.nkjmlab.util.java.function.Try;

public class BasicHttpClient {

  private static final BasicHttpClient DEFAULT_HTTP_CLIENT =
      new BasicHttpClient(HttpClient.newHttpClient());


  public static BasicHttpClient getDefaultBasicHttpClient() {
    return DEFAULT_HTTP_CLIENT;
  }

  private HttpClient client;

  public BasicHttpClient(HttpClient client) {
    this.client = client;
  }

  public String getBody(URI uri) {
    return getResponse(uri).body();
  }

  public HttpResponse<String> getResponse(URI uri) {
    try {
      HttpRequest request = HttpRequest.newBuilder().uri(uri).build();
      return client.send(request, HttpResponse.BodyHandlers.ofString());
    } catch (IOException | InterruptedException e) {
      throw Try.rethrow(e);
    }
  }

  public void download(URI uri, File outputFile) {
    try (
        InputStream inputStream = new ByteArrayInputStream(
            getResponse(uri).body().getBytes(StandardCharsets.UTF_8.toString()));
        OutputStream outputStream = new FileOutputStream(outputFile)) {
      final byte[] buffer = new byte[8192];
      int n;
      while ((n = inputStream.read(buffer)) != -1) {
        outputStream.write(buffer, 0, n);
      }
    } catch (UnsupportedOperationException | IOException e) {
      throw Try.rethrow(e);
    }
  }



}

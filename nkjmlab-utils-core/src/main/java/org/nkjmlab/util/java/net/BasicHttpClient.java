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

  public static BasicHttpClient newBasicHttpClient() {
    return new BasicHttpClient(HttpClient.newHttpClient());
  }

  private final HttpClient client;

  public BasicHttpClient(HttpClient client) {
    this.client = client;
  }

  public HttpResponse<String> getResponse(URI uri) {
    return send(HttpRequest.newBuilder().uri(uri).build());
  }

  public HttpResponse<String> send(HttpRequest request) {
    try {
      return client.send(request, HttpResponse.BodyHandlers.ofString());
    } catch (IOException | InterruptedException e) {
      throw Try.rethrow(e);
    }
  }

  public File download(URI uri, File outputFile) {
    try (InputStream is =
            new ByteArrayInputStream(
                getResponse(uri).body().getBytes(StandardCharsets.UTF_8.toString()));
        OutputStream os = new FileOutputStream(outputFile)) {
      final byte[] buffer = new byte[8192];
      int n;
      while ((n = is.read(buffer)) != -1) {
        os.write(buffer, 0, n);
      }
      return outputFile;
    } catch (UnsupportedOperationException | IOException e) {
      throw Try.rethrow(e);
    }
  }

  public HttpClient getHttpClient() {
    return client;
  }
}

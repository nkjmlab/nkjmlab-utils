package org.nkjmlab.util.java.net;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;

import org.nkjmlab.util.java.function.Try;

public class UriUtils {

  public static String getFileName(URI uri) {
    return Path.of(uri).getFileName().toString();
  }

  public static URI of(String uri) {
    try {
      return new URI(uri);
    } catch (URISyntaxException e) {
      throw Try.rethrow(e);
    }
  }

  public static URI of(URL url) {
    try {
      return url.toURI();
    } catch (URISyntaxException e) {
      throw Try.rethrow(e);
    }
  }
}

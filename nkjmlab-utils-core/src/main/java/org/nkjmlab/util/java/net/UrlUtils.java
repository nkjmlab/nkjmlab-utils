package org.nkjmlab.util.java.net;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import org.nkjmlab.util.java.function.Try;

public class UrlUtils {

  public static URL of(String url) {
    try {
      return new URL(url);
    } catch (MalformedURLException e) {
      throw Try.rethrow(e);
    }
  }

  public static String getHostUrl(String url) {
    return toHostUrl(of(url));
  }

  public static URL of(URI uri) {
    try {
      return uri.toURL();
    } catch (MalformedURLException e) {
      throw Try.rethrow(e);
    }
  }

  public static String toHostUrl(URL requestUrl) {
    return requestUrl.getProtocol() + "://" + requestUrl.getHost()
        + (requestUrl.getPort() == -1 ? "" : ":" + requestUrl.getPort());
  }

}

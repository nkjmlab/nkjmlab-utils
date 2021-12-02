package org.nkjmlab.util.net;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class UrlUtils {

  public static URL of(String url) {
    try {
      return new URL(url);
    } catch (MalformedURLException e) {
      throw new IllegalArgumentException(url);
    }
  }

  public static String getHostUrl(String url) {
    return toHostUrl(of(url));
  }

  public static URL of(URI uri) {
    try {
      return uri.toURL();
    } catch (MalformedURLException e) {
      throw new IllegalArgumentException(uri.toString());
    }
  }

  public static URI toUri(String url) {
    try {
      return of(url).toURI();
    } catch (URISyntaxException e) {
      throw new IllegalArgumentException(url);
    }
  }

  public static String toHostUrl(URL requestUrl) {
    return requestUrl.getProtocol() + "://" + requestUrl.getHost()
        + (requestUrl.getPort() == -1 ? "" : ":" + requestUrl.getPort());
  }

}

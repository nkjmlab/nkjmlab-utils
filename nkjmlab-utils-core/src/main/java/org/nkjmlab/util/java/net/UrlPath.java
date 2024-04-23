package org.nkjmlab.util.java.net;

import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * URL Path
 *
 * @author nkjm
 */
public class UrlPath {

  private final String path;
  private final List<String> elements;

  private UrlPath(String path) {
    if (!path.startsWith("/")) {
      throw new RuntimeException(String.format("path [{}] should be start with /", path));
    }
    this.path = path;
    String trim = path.substring(1);
    this.elements = Arrays.asList(trim.split("/"));
  }

  /**
   * Path should be start with "/".
   *
   * <pre>
   * "/" => [""]
   * "/mmse.html" => ["mmse.html"]
   * "/form" => ["form"]
   * "/form/" => ["form"]
   * "/form/mmse.html" => ["form","mmse.html"]
   * </pre>
   *
   * @param path
   * @return
   */
  public static UrlPath of(String path) {
    return new UrlPath(path);
  }

  /**
   * Append "index.html" if the last character is "/".
   *
   * @return
   */
  public UrlPath appendDirectoryIndex() {
    return of(path.endsWith("/") ? path + "index.html" : path);
  }

  public static UrlPath of(URL url) {
    return of(url.getPath());
  }

  public static UrlPath of(URI uri) {
    return of(uri.getPath());
  }

  public String get(int index) {
    return elements.get(index);
  }

  /**
   * "/form/mmse.html" =>"mmse.html"
   *
   * @return
   */
  public String getLast() {
    return getFromTail(0);
  }

  /**
   * of("/form/mmse.html").getFromTail(1) => "form"
   *
   * @param index
   * @return
   */
  public String getFromTail(int index) {
    int i = elements.size() - (index + 1);
    return i < 0 ? null : elements.get(i);
  }

  public List<String> getElements() {
    return elements;
  }

  public int size() {
    return elements.size();
  }

  public String getPath() {
    return path;
  }

  @Override
  public String toString() {
    return "UrlPathElements [path=" + path + ", elements=" + elements + "]";
  }

  public UrlPath removeStart(String str) {
    return of(path.replaceFirst("^" + str, ""));
  }
}

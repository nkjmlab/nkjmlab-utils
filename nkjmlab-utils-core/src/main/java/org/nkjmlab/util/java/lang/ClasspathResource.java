package org.nkjmlab.util.java.lang;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;

import org.nkjmlab.util.java.net.UriUtils;

public class ClasspathResource {

  private final Class<?> clazz;
  private final String name;
  private final URL url;
  private final URI uri;

  public static ClasspathResource of(Class<?> clazz, String name) {
    return new ClasspathResource(clazz, name);
  }

  ClasspathResource(Class<?> clazz, String name) {
    this.clazz = clazz;
    this.name = name;
    this.url = clazz.getResource(name);
    this.uri = UriUtils.of(url);
  }

  public File getResourceAsFile() {
    return new File(getResourceAsUri());
  }

  public Path getResourceAsPath() {
    return Path.of(getResourceAsUri());
  }

  public URI getResourceAsUri() {
    return uri;
  }

  public URL getResourceAsUrl() {
    return url;
  }

  public String getResourceProtocol() {
    return getResourceAsUrl().getProtocol();
  }

  public InputStream getResourceAsStream() {
    return clazz.getResourceAsStream(name);
  }

  public URI resolve(String str) {
    return uri.resolve(str);
  }
}

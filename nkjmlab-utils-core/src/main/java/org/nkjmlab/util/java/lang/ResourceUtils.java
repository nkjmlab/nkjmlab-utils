package org.nkjmlab.util.java.lang;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.nkjmlab.sorm4j.internal.util.Try;

public class ResourceUtils {

  private static BufferedReader getResourceAsBufferedReader(Class<?> clazz, String resourceName) {
    return new BufferedReader(getResourceAsInputStreamReader(clazz, resourceName));
  }

  public static File getResourceAsFile(Class<?> clazz, String file) {
    return new File(getResourceAsUri(clazz, file));
  }

  public static File getResourceAsFile(String file) {
    return getResourceAsFile(ResourceUtils.class, file);
  }

  public static InputStream getResourceAsInputStream(Class<?> clazz, File file) {
    return getResourceAsInputStream(clazz, toResourceName(file));
  }

  public static InputStream getResourceAsInputStream(Class<?> clazz, String resourceName) {
    return clazz.getResourceAsStream(resourceName);
  }

  public static InputStream getResourceAsInputStream(String resourceName) {
    return getResourceAsInputStream(ResourceUtils.class, resourceName);
  }

  private static InputStreamReader getResourceAsInputStreamReader(Class<?> clazz,
      String resourceName) {
    return new InputStreamReader(getResourceAsInputStream(clazz, resourceName));
  }

  public static URI getResourceAsUri(Class<?> clazz, String file) {
    try {
      return clazz.getResource(file).toURI();
    } catch (URISyntaxException e) {
      throw Try.rethrow(e);
    }
  }

  public static URI getResourceAsUri(String file) {
    return getResourceAsUri(ResourceUtils.class, file);
  }

  public static List<String> readAllLines(Class<?> clazz, String resourceName) {
    List<String> lines = new ArrayList<>();
    try (BufferedReader br = getResourceAsBufferedReader(clazz, resourceName)) {
      String line;
      while ((line = br.readLine()) != null) {
        lines.add(line);
      }
    } catch (IOException e) {
      throw Try.rethrow(e);
    }
    return lines;

  }

  private static String toResourceName(File file) {
    return toResourceName(file.getPath());
  }

  private static String toResourceName(String name) {
    return name.replaceAll("\\\\", "/");
  }

}

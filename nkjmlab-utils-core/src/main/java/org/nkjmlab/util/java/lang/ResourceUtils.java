package org.nkjmlab.util.java.lang;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.nkjmlab.util.java.function.Try;
import org.nkjmlab.util.java.io.SystemFileUtils;

public class ResourceUtils {

  public static List<String> readAllLines(Class<?> clazz, String resourcePath) {
    return readAllLinesFromResource(clazz, resourcePath, StandardCharsets.UTF_8);
  }

  public static List<String> readAllLinesFromResource(
      Class<?> clazz, String resourcePath, Charset cs) {
    try (InputStream in = clazz.getResourceAsStream(resourcePath);
        InputStreamReader ir = new InputStreamReader(in, cs);
        BufferedReader br = new BufferedReader(ir)) {
      List<String> ret = new ArrayList<>();
      String line;
      while ((line = br.readLine()) != null) {
        ret.add(line);
      }
      return ret;
    } catch (IOException e) {
      throw Try.rethrow(e);
    }
  }

  public static File copyResourceToTempDir(Class<?> clazz, String resourcePath) {
    byte[] bytes = readAllByteFromResource(clazz, resourcePath);
    String[] pathElements = resourcePath.split("/");

    File parent = new File(SystemFileUtils.getTempDirectory(), clazz.getCanonicalName());
    File destFile =
        new File(parent, System.currentTimeMillis() + "-" + pathElements[pathElements.length - 1]);
    parent.mkdirs();

    try (FileOutputStream out = new FileOutputStream(destFile)) {
      out.write(bytes);
    } catch (IOException e) {
      throw Try.rethrow(e);
    }
    return destFile;
  }

  public static byte[] readAllByteFromResource(Class<?> clazz, String resourcePath) {
    try (InputStream in = clazz.getResourceAsStream(resourcePath);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

      byte[] buffer = new byte[1024];
      int bytesRead;

      while ((bytesRead = in.read(buffer)) != -1) {
        outputStream.write(buffer, 0, bytesRead);
      }

      return outputStream.toByteArray();

    } catch (IOException e) {
      throw Try.rethrow(e);
    }
  }

  public static File getResourceAsFile(Class<?> clazz, String resourceName) {
    return new File(getResourceAsUri(clazz, resourceName));
  }

  public static File getResourceAsFile(String resourceName) {
    return getResourceAsFile(ResourceUtils.class, resourceName);
  }

  public static URI getResourceAsUri(Class<?> clazz, String resourceName) {
    try {
      return clazz.getResource(resourceName).toURI();
    } catch (URISyntaxException e) {
      throw Try.rethrow(e);
    }
  }

  public static URI getResourceAsUri(String resourceName) {
    return getResourceAsUri(ResourceUtils.class, resourceName);
  }

  public static String toResourceName(File file) {
    return toResourceName(file.getPath());
  }

  private static String toResourceName(String name) {
    return name.replaceAll("\\\\", "/");
  }

  public static File getResourceRootAsFile() {
    return getResourceAsFile(ResourceUtils.class, "/");
  }
}

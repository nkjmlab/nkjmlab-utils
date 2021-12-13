package org.nkjmlab.util.java.net;

import java.net.URI;
import java.nio.file.Path;

public class UriUtils {

  public static String getFileName(URI uri) {
    return Path.of(uri).getFileName().toString();
  }

}

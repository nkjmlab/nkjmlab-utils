package org.nkjmlab.util.java.io;

import java.io.IOException;
import java.io.Reader;
import org.nkjmlab.util.java.function.Try;

public class ReaderUtils {

  public static String readAsString(Reader reader) {
    try {
      final char[] buffer = new char[1024];
      final StringBuilder sb = new StringBuilder();

      while (true) {
        int size = reader.read(buffer);
        if (size == -1) {
          break;
        }
        sb.append(buffer, 0, size);
      }
      return sb.toString();
    } catch (IOException e) {
      throw Try.rethrow(e);
    }
  }

}

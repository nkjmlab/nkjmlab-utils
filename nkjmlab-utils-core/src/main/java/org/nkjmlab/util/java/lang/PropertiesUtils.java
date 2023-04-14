package org.nkjmlab.util.java.lang;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.nkjmlab.util.java.function.Try;

public class PropertiesUtils {

  private PropertiesUtils() {}

  public static Properties load(String resourceName) {
    Properties props = new Properties();
    try (InputStream is = PropertiesUtils.class.getResourceAsStream(resourceName)) {
      props.load(is);
    } catch (IOException e) {
      Try.rethrow(e);
    }
    return props;
  }

}

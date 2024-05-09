package org.nkjmlab.util.java.lang;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.nkjmlab.util.java.lang.JavaSystemProperties.JavaSystemProperty;

class JavaSystemPropertiesTest {

  @Test
  void test() {
    JavaSystemProperties props = JavaSystemProperties.create();
    assertThat(props.get(JavaSystemProperty.JAVA_VERSION).length()).isNotEqualTo(0);
    assertThat(JavaSystemProperties.isOnNameContainsWindows())
        .isEqualTo(System.getProperty("os.name").contains("Windows"));
  }
}

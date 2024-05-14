package org.nkjmlab.util.java.lang;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.nkjmlab.util.java.lang.JavaSystemProperties.JavaSystemProperty;

class JavaSystemPropertiesTest {

  @Test
  void test() {
    JavaSystemProperties props = JavaSystemProperties.create();
    assertThat(props.get(JavaSystemProperty.JAVA_VERSION).length()).isNotEqualTo(0);
    assertThat(JavaSystemProperties.isOsNameContainsWindows())
        .isEqualTo(System.getProperty("os.name").contains("Windows"));

    assertThat(JavaSystemProperty.of("os.name")).isEqualTo(JavaSystemProperty.OS_NAME);
    assertThat(JavaSystemProperty.OS_NAME.toPropertyName()).isEqualTo("os.name");
    assertThat(JavaSystemProperty.OS_NAME.name()).isEqualTo("OS_NAME");
    assertThat(JavaSystemProperty.OS_NAME.toString()).isEqualTo("os.name");
  }
}

package org.nkjmlab.util.java.lang;

import org.junit.jupiter.api.Test;

class SystemPropertyUtilsTest {

  @Test
  void testGetJavaProperties() {
    System.out.println(JavaSystemProperties.create());
  }
}

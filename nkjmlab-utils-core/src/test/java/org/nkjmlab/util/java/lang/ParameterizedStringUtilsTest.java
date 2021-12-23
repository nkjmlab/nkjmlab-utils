package org.nkjmlab.util.java.lang;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;

class ParameterizedStringUtilsTest {

  @Test
  void testNewStringStringObjectArray() {
    assertThat(ParameterizedStringUtils.newString("{}", 1)).isEqualTo("1");
    assertThat(ParameterizedStringUtils.newString("{}{}", 1)).isEqualTo("1{}");
    assertThat(ParameterizedStringUtils.newString("{}", 1, 2)).isEqualTo("1");
    assertThat(ParameterizedStringUtils.newString("My name is {}. My score is {}", "Alice", 100))
        .isEqualTo("My name is Alice. My score is 100");
  }

}

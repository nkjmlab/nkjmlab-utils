package org.nkjmlab.util.java.lang;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;

class ParameterizedStringUtilsTest {

  @Test
  void testNewStringStringObjectArray() {
    Object[] params = {1};
    assertThat(ParameterizedStringFormatter.DEFAULT.format("{}", params)).isEqualTo("1");
    Object[] params1 = {1};
    assertThat(ParameterizedStringFormatter.DEFAULT.format("{}{}", params1)).isEqualTo("1{}");
    Object[] params2 = {1, 2};
    assertThat(ParameterizedStringFormatter.DEFAULT.format("{}", params2)).isEqualTo("1");
    Object[] params3 = {"Alice", 100};
    assertThat(ParameterizedStringFormatter.DEFAULT.format("My name is {}. My score is {}", params3))
        .isEqualTo("My name is Alice. My score is 100");
  }

}

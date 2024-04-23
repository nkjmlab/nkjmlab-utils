package org.nkjmlab.util.java.lang;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ParameterizedStringFormatterTest {

  @Test
  void testNewStringStringObjectArray() {
    Object[] params = {1};
    assertThat(ParameterizedStringFormatter.DEFAULT.format("{}", params)).isEqualTo("1");
    Object[] params1 = {1};
    assertThat(ParameterizedStringFormatter.DEFAULT.format("{}{}", params1)).isEqualTo("1{}");
    Object[] params2 = {1, 2};
    assertThat(ParameterizedStringFormatter.DEFAULT.format("{}", params2)).isEqualTo("1");
    Object[] params3 = {"Alice", 100};
    assertThat(
            ParameterizedStringFormatter.DEFAULT.format("My name is {}. My score is {}", params3))
        .isEqualTo("My name is Alice. My score is 100");
  }

  @Test
  public void testFormat() {
    ParameterizedStringFormatter formatter = ParameterizedStringFormatter.LENGTH_16;

    String result = formatter.format("My name is {}. I am {} years old.", "Alice", 30);
    assertThat(result).isEqualTo("My name is Alice. I am 30 years old.");
  }

  @Test
  public void testFormatWithLongString() {
    ParameterizedStringFormatter formatter = ParameterizedStringFormatter.LENGTH_8;

    String result = formatter.format("This is a long {} and a short sentence", "sentence that should be truncated");
    assertThat(result).isEqualTo("This is a long sentence... and a short sentence");
  }

  @Test
  public void testFormatParameterWithType() {
    ParameterizedStringFormatter formatter = ParameterizedStringFormatter.LENGTH_16;

    String result = formatter.formatParameterWithType("Alice", 30, 5.75);
    assertThat(result).isEqualTo("(String) Alice, (Integer) 30, (Double) 5.75");
  }

  @Test
  public void testFormatParameter() {
    ParameterizedStringFormatter formatter = ParameterizedStringFormatter.LENGTH_16;

    String result = formatter.formatParameter("Alice", 30, 5.75);
    assertThat(result).isEqualTo("Alice, 30, 5.75");
  }

  @Test
  public void testNewString() {
    String msg = "My name is {}. I am {} years old.";
    String result = ParameterizedStringFormatter.newString(msg, "{}", 16, "Alice", 30);
    assertThat(result).isEqualTo("My name is Alice. I am 30 years old.");
  }

  @Test
  public void testNewStringWithArrayParameter() {
    String msg = "Numbers: {}";
    int[] numbers = {1, 2, 3};
    String result = ParameterizedStringFormatter.newString(msg, "{}", 16, (Object) numbers);
    assertThat(result).isEqualTo("Numbers: [1, 2, 3]");
  }
}

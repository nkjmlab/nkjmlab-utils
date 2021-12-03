package org.nkjmlab.util.java.lang;

import static org.nkjmlab.util.java.lang.StringUtils.*;
import org.junit.jupiter.api.Test;

class StringUtilsTest {

  @Test
  void test() {
    System.out.println(getStringList("A", "Z"));
    System.out.println(getStringList("A", "z"));
    System.out.println(getStringList("A", 2));
    System.out.println(getStringList("A", 26));
    System.out.println(getStringList("a", "z"));
    System.out.println(getStringList("あ", "ん"));
    System.out.println(getStringList("ア", "ン"));
  }

}

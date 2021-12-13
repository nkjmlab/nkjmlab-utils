package org.nkjmlab.util.java.lang;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StringUtils {
  public static String trimLast(String str, char x) {
    if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == x) {
      str = str.substring(0, str.length() - 1);
    }
    return str;
  }

  /**
   * https://ja.wikipedia.org/wiki/Unicode%E4%B8%80%E8%A6%A7_0000-0FFF
   *
   * @param from
   * @param to
   * @return
   */
  public static List<String> getStringList(String from, String to) {
    char fc = from.toCharArray()[0];
    char tc = to.toCharArray()[0];
    return getStringList(fc, tc);
  }

  public static List<String> getStringList(char from, char to) {
    return IntStream.rangeClosed(from, to).mapToObj(i -> String.valueOf((char) i))
        .collect(Collectors.toList());
  }

  public static List<String> getStringList(String from, int num) {
    char fc = from.toCharArray()[0];
    return getStringList(fc, (char) (fc + num - 1));

  }

  public static final List<String> upperAlphabets =
      Collections.unmodifiableList(getStringList("A", "Z"));
  public static final List<String> lowerAlphabets =
      Collections.unmodifiableList(getStringList("a", "z"));

  public static String toUpperAlphabet(int order) {
    return upperAlphabets.get(order);
  }

  public static String toLowerAlphabet(int order) {
    return lowerAlphabets.get(order);
  }
}

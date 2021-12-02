package org.nkjmlab.util.math;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class BitPatternUtils {

  /**
   *
   * Gets bit patterns.
   *
   *
   * <pre>
   *
   * <code>getBitpatterns(3)</code> returns
   * [
   *  [0, 0, 0], [0, 0, 1], [0, 1, 0], [0, 1, 1],
   *  [1, 0, 0], [1, 0, 1], [1, 1, 0], [1, 1, 1]
   * ]
   * </pre>
   *
   * @param n length of bit.
   * @return order by ascendant
   */
  public static List<List<Integer>> getBitPatternsAsInt(int n) {
    return getBitPatterns(n).stream()
        .map(pattern -> pattern.stream().map(b -> b ? 1 : 0).collect(Collectors.toList()))
        .collect(Collectors.toList());
  }

  /**
   *
   * Gets bit patterns.
   *
   *
   * <pre>
   *
   * <code>getBitpatterns(3)</code> returns
   * <code>
   * [
   *  [false, false, false], [false, false, true], [false, true, false], [false, true, true],
   *  [true, false, false], [true, false, true], [true, true, false], [true, true, true]
   * ]
   * </code>
   * </pre>
   *
   * @param n length of bit.
   * @return order by ascendant
   */

  public static List<List<Boolean>> getBitPatterns(int n) {
    List<List<Boolean>> ret = new ArrayList<>(n);

    // (1 << n) means 2^n
    for (int i = 0; i < (1 << n); i++) {
      List<Boolean> tmp = new ArrayList<>();

      // Check the bit flag of i
      for (int j = 0; j < n; j++) {
        tmp.add((i & (1 << j)) > 0);
      }
      Collections.reverse(tmp);
      ret.add(tmp);
    }
    return ret;
  }


}

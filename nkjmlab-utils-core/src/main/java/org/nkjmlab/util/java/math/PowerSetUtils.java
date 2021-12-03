package org.nkjmlab.util.java.math;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PowerSetUtils {

  public static void main(String[] args) {
    System.out.println(getPowerSet(List.of(1, 2, 3)));
  }

  /**
   *
   * Gets power set.
   *
   * <pre>
   *
   * <code>getPowerSet(List.of(1,2,3))</code> returns
   * [[],  [1],    [2],    [1, 2],
   *  [3], [1, 3], [2, 3], [1, 2, 3]]
   * </pre>
   *
   * @param src
   * @return
   */
  public static <T extends Object> List<List<T>> getPowerSet(List<T> src) {
    return BitPatternUtils.getBitPatterns(src.size()).stream().map(pattern -> {
      List<T> ret = new ArrayList<>();
      for (int i = 0; i < pattern.size(); i++) {
        if (pattern.get(i)) {
          ret.add(src.get(src.size() - 1 - i));
        }
      }
      Collections.reverse(ret);
      return ret;
    }).collect(Collectors.toList());
  }

  public static <T extends Object> Set<Set<T>> getPowerSet(Set<T> src) {
    List<T> ret = new ArrayList<>(src);
    return BitPatternUtils.getBitPatterns(ret.size()).stream().map(pattern -> {
      Set<T> subset = new HashSet<>();
      for (int i = 0; i < pattern.size(); i++) {
        if (pattern.get(i)) {
          subset.add(ret.get(ret.size() - 1 - i));
        }
      }
      return subset;
    }).collect(Collectors.toSet());

  }


}

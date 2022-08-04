package org.nkjmlab.util.java.math;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Combination {


  /**
   *
   * @param <T>
   * @param elements source
   * @param k The number of element in a tuple
   * @return
   */
  public static <T> List<List<T>> getCombination(List<T> elements, int k) {
    List<List<Boolean>> bitPatterns = BitPatternUtils.getBitPatterns(elements.size()).stream()
        .filter(bits -> bits.stream().filter(b -> b).count() == k).collect(Collectors.toList());

    List<List<T>> ret = bitPatterns.stream().map(pattern -> {
      List<T> tuple = new ArrayList<>();
      for (int i = 0; i < pattern.size(); i++) {
        if (pattern.get(i)) {
          tuple.add(elements.get(i));
        }
      }
      return tuple;
    }).collect(Collectors.toList());
    return ret;
  }

}

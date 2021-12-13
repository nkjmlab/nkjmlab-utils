package org.nkjmlab.util.commons.math3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.math3.util.Combinations;

public class Combination<T extends Comparable<T>> {

  private List<T> elements;

  @SafeVarargs
  public Combination(T... elements) {
    this.elements = Arrays.asList(elements);
  }

  /**
   * @see Combinations#Combinations
   * @param k
   * @return
   */
  public List<List<T>> getCombination(int k) {
    List<List<T>> ret = new ArrayList<>();
    new Combinations(elements.size(), k).forEach(arr -> {
      List<T> t = new ArrayList<>();
      for (int i = 0; i < arr.length; i++) {
        t.add(elements.get(arr[i]));
      }
      ret.add(t);
    });
    return ret;
  }

}

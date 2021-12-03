package org.nkjmlab.util.java.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Permutation<T> {

  private final Set<List<T>> tuples;

  private Permutation(Set<List<T>> tuples) {
    this.tuples = tuples;
  }


  public Set<List<T>> values() {
    return tuples;
  }

  @SafeVarargs
  public static <T> Permutation<T> of(T... elems) {
    return new Permutation<>(permutation(elems));
  }

  @SafeVarargs
  public static <T> Set<List<T>> permutation(T... elems) {
    return createPermutation(new HashSet<>(), elems.length, Arrays.asList(elems));
  }

  private static <T> Set<List<T>> createPermutation(Set<List<T>> sequences, int n, List<T> list) {
    if (n == 1) {
      sequences.add(new ArrayList<>(list));
      return sequences;
    }
    for (int i = 1; i <= n; i++) {
      createPermutation(sequences, n - 1, list);
      swap(list, n % 2 == 0 ? i - 1 : 0, n - 1);
    }
    return sequences;
  }

  private static <T> void swap(List<T> list, int i, int j) {
    T tmp = list.get(j);
    list.set(j, list.get(i));
    list.set(i, tmp);
  }


}

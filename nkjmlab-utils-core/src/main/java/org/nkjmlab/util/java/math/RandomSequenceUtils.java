package org.nkjmlab.util.java.math;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class RandomSequenceUtils {

  /**
   * @param startInclusive
   * @param endExclusive
   * @param length
   * @return
   */
  public static List<Integer> createUniqueRandomValues(int startInclusive, int endExclusive,
      int length) {
    List<Integer> seq = ThreadLocalRandom.current().ints(startInclusive, endExclusive).boxed()
        .collect(Collectors.toList());
    Collections.shuffle(seq);
    return seq.subList(0, length);
  }

  /**
   *
   * @param startInclusive
   * @param endExclusive
   * @param length
   * @return
   */
  public static List<Integer> createSortedUniqueRandomValues(int startInclusive, int endExclusive,
      int length) {
    List<Integer> values = createUniqueRandomValues(startInclusive, endExclusive, length);
    Collections.sort(values);
    return values;
  }


}

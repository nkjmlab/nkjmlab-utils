package org.nkjmlab.util.math;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RandomUtils {
  /**
   * 指定の範囲内で重複のないランダムな値のリストを作成する．
   *
   * @param startInclusive
   * @param endExclusive
   * @param length
   * @return
   */
  public static List<Integer> createUniqueRandomValuesInRange(int startInclusive, int endExclusive,
      int length) {
    List<Integer> seq =
        IntStream.range(startInclusive, endExclusive).boxed().collect(Collectors.toList());
    Collections.shuffle(seq);
    List<Integer> values = seq.subList(0, length);
    return values;
  }


}

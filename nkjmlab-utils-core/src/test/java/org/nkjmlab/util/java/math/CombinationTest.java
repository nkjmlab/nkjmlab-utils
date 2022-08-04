package org.nkjmlab.util.java.math;

import static org.assertj.core.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.Test;

class CombinationTest {

  @Test
  void test() {
    List<List<Integer>> ret = Combination.getCombination(List.of(1, 2, 3, 4), 3);

    assertThat(ret).containsExactlyInAnyOrder(List.of(1, 2, 3), List.of(1, 2, 4), List.of(1, 3, 4),
        List.of(2, 3, 4));
  }

}

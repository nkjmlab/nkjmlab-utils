package org.nkjmlab.util.java.math;

import static org.assertj.core.api.Assertions.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class PermutationTest {

  @Test
  void testOf() {
    Permutation<Integer> ret = Permutation.of(1, 2, 3, 4);
    assertThat(ret.values().size()).isEqualTo(24);
    Set<List<Integer>> set = ret.values().stream().collect(Collectors.toSet());
    assertThat(set.size()).isEqualTo(24);
  }

  @Test
  void testOfDup() {
    Permutation<Integer> ret = Permutation.of(1, 1, 3, 4);
    assertThat(ret.values().size()).isEqualTo(12);
    Set<List<Integer>> set = ret.values().stream().collect(Collectors.toSet());
    assertThat(set.size()).isEqualTo(12);
  }


}

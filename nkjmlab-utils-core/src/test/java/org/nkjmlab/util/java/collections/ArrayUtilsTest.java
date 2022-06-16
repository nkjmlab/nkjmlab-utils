package org.nkjmlab.util.java.collections;

import static org.assertj.core.api.Assertions.*;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class ArrayUtilsTest {

  @Test
  void testToStringWithType() {
    assertThat(ArrayUtils.toStringWithType((Object[]) null)).isEqualTo("null");
    assertThat(ArrayUtils.toStringWithType((Object) null)).isEqualTo("[null]");
    assertThat(ArrayUtils.toStringWithType("1", "2")).isEqualTo("[1 (String), 2 (String)]");
    assertThat(ArrayUtils.toStringWithType(1, 2)).isEqualTo("[1 (Integer), 2 (Integer)]");
    assertThat(ArrayUtils.toStringWithType(LocalDate.of(2022, 04, 04)))
        .isEqualTo("[2022-04-04 (LocalDate)]");

    assertThat(ArrayUtils.toStringWithType(new int[] {1, 2})).isEqualTo("[[1, 2] (int[])]");
    assertThat(ArrayUtils.toStringWithType((Object) new Integer[] {1, 2}))
        .isEqualTo("[[1, 2] (Integer[])]");
  }

}

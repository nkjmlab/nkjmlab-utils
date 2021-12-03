package org.nkjmlab.util.java.math;

import static org.assertj.core.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.nkjmlab.util.java.math.CartesianProduct;
import org.nkjmlab.util.java.math.CartesianProduct.CartesianProduct2;
import org.nkjmlab.util.java.math.CartesianProduct.CartesianProduct3;

class CartesianProductTest {
  private static org.apache.logging.log4j.Logger log =
      org.apache.logging.log4j.LogManager.getLogger();

  @Test
  void testOfProduct20() {
    CartesianProduct2<String, Integer> ret =
        CartesianProduct.of(List.of("a", "b", "c"), List.of(1, 2));
    assertThat(ret.toString()).isEqualTo("[(a . 1), (a . 2), (b . 1), (b . 2), (c . 1), (c . 2)]");
  }

  @Test
  void testOfProduct2() {
    CartesianProduct2<String, Integer> ret = CartesianProduct.of(List.of("a", "b"), List.of(1, 2));
    assertThat(ret.toString()).isEqualTo("[(a . 1), (a . 2), (b . 1), (b . 2)]");
  }


  @Test
  void testOfProduct3() {
    CartesianProduct3<Integer, String, Integer> ret =
        CartesianProduct.of(List.of(3, 4), List.of("a", "b"), List.of(1, 2));
    assertThat(ret.toString())
        .isEqualTo("[(3 . (a . 1)), (3 . (a . 2)), (3 . (b . 1)), (3 . (b . 2)),"
            + " (4 . (a . 1)), (4 . (a . 2)), (4 . (b . 1)), (4 . (b . 2))]");
  }

}

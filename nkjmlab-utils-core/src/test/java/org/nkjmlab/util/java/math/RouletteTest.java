package org.nkjmlab.util.java.math;

import static org.assertj.core.api.Assertions.*;
import java.util.Map;
import org.junit.jupiter.api.Test;

class RouletteTest {

  @Test
  void testGetRouletteValue() {
    // a=0% b=20% c=80%
    Roulette<String> r = new Roulette<>(Map.of("a", 0, "b", 1, "c", 4));

    double a = 0;
    double b = 0;
    double c = 0;
    int n = 1000000;
    for (int i = 0; i < n; i++) {
      String s = r.getRouletteValue();
      switch (s) {
        case "a":
          a++;
          break;
        case "b":
          b++;
          break;
        case "c":
          c++;
          break;
        default:
          break;
      }
    }

    assertThat(a / n).isEqualTo(0);
    assertThat(b / n).isBetween(0.2 - 0.01, 0.2 + 0.01);
    assertThat(c / n).isBetween(0.8 - 0.01, 0.8 + 0.01);

  }

}

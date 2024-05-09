package org.nkjmlab.util.junit;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

class JUnitCallerUtilsTest {

  @Test
  void test() {
    AtomicInteger i = new AtomicInteger();
    JUnitCallerUtils.skipIfCalledByJUnit(() -> i.incrementAndGet());
    assertThat(i.intValue()).isEqualTo(0);
  }
}

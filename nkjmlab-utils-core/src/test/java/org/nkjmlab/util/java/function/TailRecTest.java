package org.nkjmlab.util.java.function;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

class TailRecTest {

  private static final Logger log = LogManager.getLogger();


  /**
   * Sums from 1 to n
   *
   * @param n
   * @return
   */
  private long sum(long n) {
    return sumTailCallFunction(0, n).call();
  }

  private FunctionCall<Long> sumTailCallFunction(long accum, long n) {
    if (n == 0) {
      return FunctionCall.done(accum);
    }
    log.debug("sumTailCall({},{})", accum + n, n - 1);
    return FunctionCall.create(() -> sumTailCallFunction(n + accum, n - 1));
  }

  @Test
  void test() {
    System.out.println(sum(10));

  }
}

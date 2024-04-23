package org.nkjmlab.util.java.function;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;

class FunctionCallTest {

  Supplier<FunctionCall<AtomicInteger>> foo(AtomicInteger counter) {
    return () -> {
      if (counter.get() < 10) {
        counter.getAndIncrement();
        return FunctionCall.create(foo(counter));
      } else {
        return FunctionCall.done(counter);
      }
    };
  }

  @Test
  public void testCallWithRecursiveFunction() {
    AtomicInteger counter = new AtomicInteger();
    FunctionCall<AtomicInteger> recursiveFunctionCall = FunctionCall.create(foo(counter));
    assertEquals(10, recursiveFunctionCall.call().get());
  }

  @Test
  public void testCallWithDone() {
    FunctionCall<String> doneCall = FunctionCall.done("Completed");
    assertEquals("Completed", doneCall.call());
  }
}

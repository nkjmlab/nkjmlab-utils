package org.nkjmlab.util.java.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.Test;

class BasicThreadFactoryTest {

  @Test
  void test() {
    ExecutorService exec2 =
        Executors.newSingleThreadExecutor(BasicThreadFactory.builder("scn-interp", false).build());
    exec2.submit(() -> System.out.println(exec2));

    ExecutorService exec1 = Executors.newSingleThreadExecutor();
    exec1.submit(() -> System.out.println(exec1));


    System.out.println(exec1);


  }

}

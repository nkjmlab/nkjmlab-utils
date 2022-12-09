package org.nkjmlab.util.java.lang;

import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class ProcessUtilsTest {

  @Test
  void test() {
    System.out.println(ProcessHandle.allProcesses().map(p -> p.info().command().orElse(""))
        .collect(Collectors.toList()));
  }

}

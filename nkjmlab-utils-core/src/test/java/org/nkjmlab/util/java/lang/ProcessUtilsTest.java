package org.nkjmlab.util.java.lang;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

class ProcessUtilsTest {

  @Test
  void test() {
    // The user and the command are included in the info but the arguments are not included in the
    // info ．
    List<String> ret =
        ProcessHandle.allProcesses()
            .map(p -> p.info().toString())
            .filter(s -> !s.equals("[]"))
            .collect(Collectors.toList());
    assertThat(ret.size() != 0).isTrue();
  }
}

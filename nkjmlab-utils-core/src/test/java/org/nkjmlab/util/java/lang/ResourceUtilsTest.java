package org.nkjmlab.util.java.lang;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ResourceUtilsTest {

  @Test
  void test() {
    assertThat(ResourceUtils.getRootCodeSourceLocation(ResourceUtils.class).toString())
        .contains("nkjmlab-utils/nkjmlab-utils/nkjmlab-utils-core/target/classes/");
    assertThat(ResourceUtils.getCodeSourceLocation(ResourceUtils.class).toString())
        .contains(
            "nkjmlab-utils/nkjmlab-utils/nkjmlab-utils-core/target/classes/org/nkjmlab/util/java/lang/");
  }
}

package org.nkjmlab.util.java.net;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

class UrlPathTest {

  @Test
  void test() {
    {
      assertThrows(
          RuntimeException.class,
          () -> {
            UrlPath elems = UrlPath.of("").appendDirectoryIndex();
            elems.getElements();
          });
    }
    {
      UrlPath elems = UrlPath.of("/").appendDirectoryIndex();
      assertThat(elems.getElements()).isEqualTo(List.of("index.html"));
    }
    {
      UrlPath elems = UrlPath.of("/index.html").appendDirectoryIndex();
      assertThat(elems.getElements()).isEqualTo(List.of("index.html"));
    }
    {
      UrlPath elems = UrlPath.of("/2/exit-icu/form/").appendDirectoryIndex();
      assertThat(elems.getElements()).isEqualTo(List.of("2", "exit-icu", "form", "index.html"));
    }
    {
      UrlPath elems = UrlPath.of("/2/exit-icu/form").appendDirectoryIndex();
      assertThat(elems.getElements()).isEqualTo(List.of("2", "exit-icu", "form"));
    }
    {
      UrlPath elems = UrlPath.of("/2/exit-icu/form/mmse.html").appendDirectoryIndex();
      assertThat(elems.getLast()).isEqualTo("mmse.html");
      assertThat(elems.getFromTail(2)).isEqualTo("exit-icu");
      assertThat(elems.getElements()).isEqualTo(List.of("2", "exit-icu", "form", "mmse.html"));
    }
  }
}

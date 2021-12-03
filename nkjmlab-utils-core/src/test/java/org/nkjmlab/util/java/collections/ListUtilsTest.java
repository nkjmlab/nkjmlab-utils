package org.nkjmlab.util.java.collections;

import static org.nkjmlab.util.java.collections.ListUtils.*;
import java.util.List;
import org.junit.jupiter.api.Test;

class ListUtilsTest {
  private static final org.nkjmlab.util.java.logging.Logger log =
      org.nkjmlab.util.java.logging.LogManager.getLogger();

  @Test
  void test() {

    List<Integer> list = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    System.out.println(partition(list, 1));
    System.out.println(partition(list, 2));
    System.out.println(partition(list, 3));
    System.out.println(partition(list, 4));
    System.out.println(partition(list, 5));
    System.out.println(partition(list, 20));
  }

}

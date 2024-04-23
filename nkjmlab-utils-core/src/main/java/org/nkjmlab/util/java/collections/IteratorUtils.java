package org.nkjmlab.util.java.collections;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IteratorUtils {
  private IteratorUtils() {}

  public static <E> List<E> toList(Iterator<? extends E> iterator, int initialSize) {
    List<E> list = new ArrayList<>(initialSize);
    while (iterator.hasNext()) {
      list.add(iterator.next());
    }
    return list;
  }

  public static <E> List<E> toList(Iterator<? extends E> iterator) {
    return toList(iterator, 10);
  }
}

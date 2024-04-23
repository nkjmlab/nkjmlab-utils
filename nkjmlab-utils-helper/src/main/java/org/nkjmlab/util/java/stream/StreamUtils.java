package org.nkjmlab.util.java.stream;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class StreamUtils {

  public static <T> Stream<T> stream(Iterator<T> iterator) {
    Stream<T> stream =
        StreamSupport.stream(
            Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED), false);
    return stream;
  }

  public static <T> Stream<T> stream(Iterable<T> iterable) {
    return stream(iterable.iterator());
  }
}

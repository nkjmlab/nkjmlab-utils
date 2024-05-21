package org.nkjmlab.util.java.stream;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.nkjmlab.util.java.math.Tuple;
import org.nkjmlab.util.java.math.Tuple.Tuple2;

/** Utility class for working with streams. */
public class StreamUtils {

  /**
   * Creates a sequential {@code Stream} from the given {@code Iterator}.
   *
   * @param <T> the type of the stream elements
   * @param iterator the {@code Iterator} to create a stream from
   * @return a sequential {@code Stream} over the elements described by the given {@code Iterator}
   */
  public static <T> Stream<T> stream(Iterator<T> iterator) {
    Stream<T> stream =
        StreamSupport.stream(
            Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED), false);
    return stream;
  }

  /**
   * Creates a sequential {@code Stream} from the given {@code Iterable}.
   *
   * @param <T> the type of the stream elements
   * @param iterable the {@code Iterable} to create a stream from
   * @return a sequential {@code Stream} over the elements described by the given {@code Iterable}
   */
  public static <T> Stream<T> stream(Iterable<T> iterable) {
    return stream(iterable.iterator());
  }

  /**
   * Zips two streams into a single stream of {@code Tuple2} elements. The resulting stream contains
   * elements formed by pairing elements from the two input streams. The length of the resulting
   * stream is the shorter of the two input streams.
   *
   * @param <T> the type of elements in the first stream
   * @param <U> the type of elements in the second stream
   * @param first the first stream to zip
   * @param second the second stream to zip
   * @return a stream of {@code Tuple2} elements, each containing an element from the first stream
   *     and an element from the second stream
   */
  public static <T, U> Stream<Tuple2<? extends T, ? extends U>> zip(
      Stream<? extends T> first, Stream<? extends U> second) {

    Iterator<? extends T> firstIterator = first.iterator();
    Iterator<? extends U> secondIterator = second.iterator();

    Iterator<Tuple2<? extends T, ? extends U>> zippedIterator =
        new Iterator<Tuple2<? extends T, ? extends U>>() {
          @Override
          public boolean hasNext() {
            return firstIterator.hasNext() && secondIterator.hasNext();
          }

          @Override
          public Tuple2<? extends T, ? extends U> next() {
            return Tuple.of(firstIterator.next(), secondIterator.next());
          }
        };

    return stream(zippedIterator);
  }
}

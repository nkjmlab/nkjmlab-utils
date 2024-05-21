package org.nkjmlab.util.java.stream;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.nkjmlab.util.java.math.Tuple;
import org.nkjmlab.util.java.math.Tuple.Tuple2;

public class StreamUtilsTest {

  @Test
  public void testStreamFromIterator() {
    List<String> list = Arrays.asList("a", "b", "c");
    Iterator<String> iterator = list.iterator();
    Stream<String> stream = StreamUtils.stream(iterator);

    assertThat(stream).containsExactly("a", "b", "c");
  }

  @Test
  public void testStreamFromIterable() {
    List<String> list = Arrays.asList("a", "b", "c");
    Iterable<String> iterable = list;
    Stream<String> stream = StreamUtils.stream(iterable);

    assertThat(stream).containsExactly("a", "b", "c");
  }

  @Test
  public void testZipStreams() {
    List<Integer> list1 = Arrays.asList(1, 2, 3);
    List<String> list2 = Arrays.asList("a", "b", "c");

    Stream<Integer> stream1 = list1.stream();
    Stream<String> stream2 = list2.stream();

    Stream<Tuple2<? extends Integer, ? extends String>> zippedStream =
        StreamUtils.zip(stream1, stream2);

    List<Tuple2<? extends Integer, ? extends String>> result = zippedStream.toList();
    assertThat(result).hasSize(3);
    assertThat(result).containsExactly(Tuple.of(1, "a"), Tuple.of(2, "b"), Tuple.of(3, "c"));
  }

  @Test
  public void testZipStreamsDifferentSizes() {
    List<Integer> list1 = Arrays.asList(1, 2);
    List<String> list2 = Arrays.asList("a", "b", "c");

    Stream<Integer> stream1 = list1.stream();
    Stream<String> stream2 = list2.stream();

    Stream<Tuple2<? extends Integer, ? extends String>> zippedStream =
        StreamUtils.zip(stream1, stream2);

    List<Tuple2<? extends Integer, ? extends String>> result = zippedStream.toList();
    assertThat(result).hasSize(2);
    assertThat(result).containsExactly(Tuple.of(1, "a"), Tuple.of(2, "b"));
  }
}

package org.nkjmlab.util.math;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class TailRec<T> {
  private final Supplier<TailRec<T>> next;
  private final boolean done;
  private final T result;

  private TailRec(Supplier<TailRec<T>> next, boolean done, T result) {
    this.next = next;
    this.done = done;
    this.result = result;
  }

  public T get() {
    return Stream.iterate(this, a -> a.next.get()).filter(a -> a.done).map(a -> a.result)
        .findFirst().get();
  }

  public static <T> TailRec<T> call(Supplier<TailRec<T>> next) {
    return new TailRec<>(next, false, null);
  }

  public static <T> TailRec<T> done(T result) {
    return new TailRec<>(() -> null, true, result);
  }
}

package org.nkjmlab.util.java.function;

import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * *
 *
 * @see <a href=
 *     "https://backpaper0.github.io/ghosts/optimized_tail_call_recursive_fibonacci_in_java.html">optimized_tail_call_recursive_fibonacci_in_java.html</a>
 * @param <T>
 */
public class FunctionCall<T> {
  private final Supplier<FunctionCall<T>> functionCall;
  private final boolean done;
  private final T result;

  private FunctionCall(Supplier<FunctionCall<T>> functionCall, boolean done, T result) {
    this.functionCall = functionCall;
    this.done = done;
    this.result = result;
  }

  /**
   * Calls this function and gets a result.
   *
   * @return
   */
  public T call() {
    return Stream.iterate(this, caller -> caller.functionCall.get())
        .filter(caller -> caller.done)
        .map(caller -> caller.result)
        .findFirst()
        .get();
  }

  /**
   * Creates a function call.
   *
   * @param <T>
   * @param functionCall
   * @return
   */
  public static <T> FunctionCall<T> create(Supplier<FunctionCall<T>> functionCall) {
    return new FunctionCall<>(functionCall, false, null);
  }

  public static <T> FunctionCall<T> done(T result) {
    return new FunctionCall<>(() -> null, true, result);
  }
}

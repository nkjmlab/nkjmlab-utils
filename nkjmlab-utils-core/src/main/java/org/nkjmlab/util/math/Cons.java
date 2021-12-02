package org.nkjmlab.util.math;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import org.nkjmlab.util.math.Tuple.Tuple2;
import org.nkjmlab.util.math.Tuple.Tuple3;


public class Cons {
  public static <A> Cons1<A> of(A a) {
    return new Cons1<A>(a);
  }

  public static <A, B> Cons2<A, B> of(A a, B b) {
    return new Cons2<A, B>(a, b);
  }

  public static <A, B, C> Cons3<A, B, C> of(A a, B b, C c) {
    return new Cons3<A, B, C>(a, b, c);
  }

  public static <A, B, C, D> Cons4<A, B, C, D> of(A a, B b, C c, D d) {
    return new Cons4<A, B, C, D>(a, b, c, d);
  }

  public static <A, B, C, D, E> Cons5<A, B, C, D, E> of(A a, B b, C c, D d, E e) {
    return new Cons5<A, B, C, D, E>(a, b, c, d, e);
  }

  public static <A, B, C, D, E, F> Cons6<A, B, C, D, E, F> of(A a, B b, C c, D d, E e, F f) {
    return new Cons6<A, B, C, D, E, F>(a, b, c, d, e, f);
  }

  private static class _Cons<A, B> {
    public final A car;
    public final B cdr;

    public _Cons(A car, B cdr) {
      this.car = car;
      this.cdr = cdr;
    }

    @Override
    public String toString() {
      if (car == null) {
        return "null";
      }
      if (cdr == null) {
        return car.toString();
      }
      return "(" + (car != null ? car.toString() : "null") + " . "
          + (cdr != null ? cdr.toString() : "null") + ")";
    }

    @Override
    public int hashCode() {
      return Objects.hash(car, cdr);
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      }
      if (!(obj instanceof _Cons)) {
        return false;
      }
      _Cons<?, ?> other = (_Cons<?, ?>) obj;
      return Objects.equals(car, other.car) && Objects.equals(cdr, other.cdr);
    }


  }

  public static class Cons1<A> extends _Cons<A, Object> {
    public Cons1(A a) {
      super(a, null);
    }

    public void accept(Consumer<A> f) {
      f.accept(car);
    }

    public <T> T apply(Function<A, T> f) {
      return f.apply(car);
    }
  }

  public static class Cons2<A, B> extends _Cons<A, Cons1<B>> {
    public Cons2(A a, B b) {
      super(a, Cons.of(b));
    }

    public void accept(Function<A, Consumer<B>> f) {
      cdr.accept(f.apply(car));
    }

    public <T> T apply(Function<A, Function<B, T>> f) {
      return cdr.apply(f.apply(car));
    }

    public Tuple2<A, B> toTuple() {
      return Tuple.of(car, cdr.car);
    }

  }
  public static class Cons3<A, B, C> extends _Cons<A, Cons2<B, C>> {
    public Cons3(A a, B b, C c) {
      super(a, Cons.of(b, c));
    }

    public void accept(Function<A, Function<B, Consumer<C>>> f) {
      cdr.accept(f.apply(car));
    }

    public <T> T apply(Function<A, Function<B, Function<C, T>>> f) {
      return cdr.apply(f.apply(car));
    }

    public Tuple3<A, B, C> toTuple() {
      return Tuple.of(car, cdr.car, cdr.cdr.car);
    }

  }

  public static class Cons4<A, B, C, D> extends _Cons<A, Cons3<B, C, D>> {
    public Cons4(A a, B b, C c, D d) {
      super(a, Cons.of(b, c, d));
    }

    public void accept(Function<A, Function<B, Function<C, Consumer<D>>>> f) {
      cdr.accept(f.apply(car));
    }

    public <T> T apply(Function<A, Function<B, Function<C, Function<D, T>>>> f) {
      return cdr.apply(f.apply(car));
    }
  }

  public static class Cons5<A, B, C, D, E> extends _Cons<A, Cons4<B, C, D, E>> {
    public Cons5(A a, B b, C c, D d, E e) {
      super(a, Cons.of(b, c, d, e));
    }

    public void accept(Function<A, Function<B, Function<C, Function<D, Consumer<E>>>>> f) {
      cdr.accept(f.apply(car));
    }

    public <T> T apply(Function<A, Function<B, Function<C, Function<D, Function<E, T>>>>> f) {
      return cdr.apply(f.apply(car));
    }

  }

  public static class Cons6<A, B, C, D, E, F> extends _Cons<A, Cons5<B, C, D, E, F>> {
    public Cons6(A a, B b, C c, D d, E e, F f) {
      super(a, Cons.of(b, c, d, e, f));
    }

    public void accept(
        Function<A, Function<B, Function<C, Function<D, Function<E, Consumer<F>>>>>> f) {
      cdr.accept(f.apply(car));
    }

    public <T> T apply(
        Function<A, Function<B, Function<C, Function<D, Function<E, Function<F, T>>>>>> f) {
      return cdr.apply(f.apply(car));
    }

  }



}

package org.nkjmlab.util.java.math;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.nkjmlab.util.java.math.Cons.Cons1;
import org.nkjmlab.util.java.math.Cons.Cons2;
import org.nkjmlab.util.java.math.Cons.Cons3;
import org.nkjmlab.util.java.math.Cons.Cons4;
import org.nkjmlab.util.java.math.Cons.Cons5;
import org.nkjmlab.util.java.math.Cons.Cons6;

public class CartesianProduct {

  public static class CartesianProduct1<A> {

    private final List<Cons1<A>> conses;

    public CartesianProduct1(List<Cons1<A>> conses) {
      this.conses = conses;
    }


    public List<Cons1<A>> values() {
      return conses;
    }

    public void accept(Consumer<A> f) {
      conses.forEach(c -> c.accept(f));
    }

    public <R> Stream<R> map(Function<A, R> f) {
      return conses.stream().map(t -> t.apply(f));
    }

    @Override
    public String toString() {
      return conses.toString();
    }

  }


  public static class CartesianProduct2<A, B> {

    private final List<Cons2<A, B>> conses;

    public CartesianProduct2(List<Cons2<A, B>> conses) {
      this.conses = conses;
    }

    @Override
    public String toString() {
      return conses.toString();
    }

    public List<Cons2<A, B>> values() {
      return conses;
    }

    public void accept(Function<A, Consumer<B>> f) {
      conses.forEach(c -> c.accept(f));
    }

    public <R> Stream<R> map(Function<A, Function<B, R>> f) {
      return conses.stream().map(t -> t.apply(f));
    }

  }

  public static class CartesianProduct3<A, B, C> {

    private final List<Cons3<A, B, C>> conses;

    public CartesianProduct3(List<Cons3<A, B, C>> conses) {
      this.conses = conses;
    }

    @Override
    public String toString() {
      return conses.toString();
    }

    public List<Cons3<A, B, C>> values() {
      return conses;
    }

    public void forEach(Function<A, Function<B, Consumer<C>>> f) {
      conses.forEach(c -> c.accept(f));
    }

    public <R> Stream<R> map(Function<A, Function<B, Function<C, R>>> f) {
      return conses.stream().map(t -> t.apply(f));
    }

  }


  public static class CartesianProduct4<A, B, C, D> {

    private final List<Cons4<A, B, C, D>> conses;

    public CartesianProduct4(List<Cons4<A, B, C, D>> conses) {
      this.conses = conses;
    }

    @Override
    public String toString() {
      return conses.toString();
    }

    public List<Cons4<A, B, C, D>> values() {
      return conses;
    }

    public void accept(Function<A, Function<B, Function<C, Consumer<D>>>> f) {
      conses.forEach(c -> c.accept(f));
    }

    public <R> Stream<R> map(Function<A, Function<B, Function<C, Function<D, R>>>> f) {
      return conses.stream().map(t -> t.apply(f));
    }
  }

  public static class CartesianProduct5<A, B, C, D, E> {

    private final List<Cons5<A, B, C, D, E>> conses;

    public CartesianProduct5(List<Cons5<A, B, C, D, E>> conses) {
      this.conses = conses;
    }

    @Override
    public String toString() {
      return conses.toString();
    }

    public List<Cons5<A, B, C, D, E>> values() {
      return conses;
    }

    public void accept(Function<A, Function<B, Function<C, Function<D, Consumer<E>>>>> f) {
      conses.forEach(c -> c.accept(f));
    }

    public <R> Stream<R> map(Function<A, Function<B, Function<C, Function<D, Function<E, R>>>>> f) {
      return conses.stream().map(t -> t.apply(f));
    }

  }
  public static class CartesianProduct6<A, B, C, D, E, F> {

    private final List<Cons6<A, B, C, D, E, F>> conses;

    public CartesianProduct6(List<Cons6<A, B, C, D, E, F>> conses) {
      this.conses = conses;
    }

    @Override
    public String toString() {
      return conses.toString();
    }

    public List<Cons6<A, B, C, D, E, F>> values() {
      return conses;
    }

    public void accept(
        Function<A, Function<B, Function<C, Function<D, Function<E, Consumer<F>>>>>> f) {
      conses.forEach(c -> c.accept(f));
    }

    public <R> Stream<R> map(
        Function<A, Function<B, Function<C, Function<D, Function<E, Function<F, R>>>>>> f) {
      return conses.stream().map(t -> t.apply(f));
    }

  }

  public static <A> CartesianProduct1<A> of(List<A> as) {
    return new CartesianProduct1<>(as.stream().map(a -> Cons.of(a)).collect(Collectors.toList()));
  }

  public static <A, B> CartesianProduct2<A, B> of(List<A> as, List<B> bs) {
    return new CartesianProduct2<>(
        as.stream().flatMap(a -> bs.stream().map(b -> Cons.of(a, b))).collect(Collectors.toList()));
  }

  public static <A, B, C> CartesianProduct3<A, B, C> of(List<A> as, List<B> bs, List<C> cs) {
    return new CartesianProduct3<>(createFlatProduct3(as, of(bs, cs).values()));

  }

  public static <A, B, C, D> CartesianProduct4<A, B, C, D> of(List<A> as, List<B> bs, List<C> cs,
      List<D> ds) {
    return new CartesianProduct4<>(createFlatProduct4(as, of(bs, cs, ds).values()));
  }


  public static <A, B, C, D, E> CartesianProduct5<A, B, C, D, E> of(List<A> as, List<B> bs,
      List<C> cs, List<D> ds, List<E> es) {
    return new CartesianProduct5<>(createFlatProduct5(as, of(bs, cs, ds, es).values()));
  }

  public static <A, B, C, D, E, F> CartesianProduct6<A, B, C, D, E, F> of(List<A> as, List<B> bs,
      List<C> cs, List<D> ds, List<E> es, List<F> fs) {
    return new CartesianProduct6<>(createFlatProduct6(as, of(bs, cs, ds, es, fs).values()));
  }


  private static <A, B, C> List<Cons3<A, B, C>> createFlatProduct3(List<A> as,
      List<Cons2<B, C>> bcs) {
    return as.stream().flatMap(a -> bcs.stream().map(bc -> Cons.of(a, bc.car, bc.cdr.car)))
        .collect(Collectors.toList());
  }

  private static <A, B, C, D> List<Cons4<A, B, C, D>> createFlatProduct4(List<A> as,
      List<Cons3<B, C, D>> bcds) {
    return as.stream()
        .flatMap(a -> bcds.stream().map(bcd -> Cons.of(a, bcd.car, bcd.cdr.car, bcd.cdr.cdr.car)))
        .collect(Collectors.toList());
  }

  private static <A, B, C, D, E> List<Cons5<A, B, C, D, E>> createFlatProduct5(List<A> as,
      List<Cons4<B, C, D, E>> bcdes) {
    return as.stream()
        .flatMap(a -> bcdes.stream().map(
            bcde -> Cons.of(a, bcde.car, bcde.cdr.car, bcde.cdr.cdr.car, bcde.cdr.cdr.cdr.car)))
        .collect(Collectors.toList());
  }

  private static <A, B, C, D, E, F> List<Cons6<A, B, C, D, E, F>> createFlatProduct6(List<A> as,
      List<Cons5<B, C, D, E, F>> bcdefs) {
    return as.stream()
        .flatMap(
            a -> bcdefs.stream()
                .map(bcdef -> Cons.of(a, bcdef.car, bcdef.cdr.car, bcdef.cdr.cdr.car,
                    bcdef.cdr.cdr.cdr.car, bcdef.cdr.cdr.cdr.cdr.car)))
        .collect(Collectors.toList());
  }

}

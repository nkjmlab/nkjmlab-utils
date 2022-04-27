package org.nkjmlab.util.java.math;

import java.util.Objects;

/**
 * Represents a tuple of objects.
 *
 * @author nkjm
 *
 */
public final class Tuple {
  private Tuple() {}

  public static <T1> Tuple1<T1> of(T1 t1) {
    return new Tuple1<>(t1);
  }

  public static <T1, T2> Tuple2<T1, T2> of(T1 t1, T2 t2) {
    return new Tuple2<>(t1, t2);
  }

  public static <T1, T2, T3> Tuple3<T1, T2, T3> of(T1 t1, T2 t2, T3 t3) {
    return new Tuple3<>(t1, t2, t3);
  }

  public static <T1, T2, T3, T4> Tuple4<T1, T2, T3, T4> of(T1 t1, T2 t2, T3 t3, T4 t4) {
    return new Tuple4<>(t1, t2, t3, t4);
  }

  public static <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> of(T1 t1, T2 t2, T3 t3, T4 t4,
      T5 t5) {
    return new Tuple5<>(t1, t2, t3, t4, t5);
  }

  public static <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> of(T1 t1, T2 t2, T3 t3,
      T4 t4, T5 t5, T6 t6) {
    return new Tuple6<>(t1, t2, t3, t4, t5, t6);
  }

  public static class Tuple1<T1> {

    private final T1 t1;

    Tuple1(T1 t1) {
      this.t1 = t1;
    }

    /**
     * Gets a t1.
     *
     * @return
     */
    public T1 getT1() {
      return t1;
    }


    @Override
    public String toString() {
      return "(" + (getT1() == null ? "null" : getT1()) + ")";
    }

    @Override
    public int hashCode() {
      return Objects.hash(t1);
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (!(obj instanceof Tuple1))
        return false;
      Tuple1<?> other = (Tuple1<?>) obj;
      return Objects.equals(t1, other.t1);
    }


  }

  /**
   * Represents a tuple of objects
   *
   * @author nkjm
   *
   * @param <T1>
   * @param <T2>
   */
  public static class Tuple2<T1, T2> extends Tuple1<T1> {

    private final T2 t2;

    Tuple2(T1 t1, T2 t2) {
      super(t1);
      this.t2 = t2;
    }

    /**
     * Gets a t2.
     *
     * @return
     */
    public T2 getT2() {
      return t2;
    }

    @Override
    public String toString() {
      return "(" + (getT1() == null ? "null" : getT1()) + ", "
          + (getT2() == null ? "null" : getT2()) + ")";
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = super.hashCode();
      result = prime * result + Objects.hash(t2);
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (!super.equals(obj))
        return false;
      if (!(obj instanceof Tuple2))
        return false;
      Tuple2<?, ?> other = (Tuple2<?, ?>) obj;
      return Objects.equals(t2, other.t2);
    }

  }
  /**
   * Represents a tuple of objects
   *
   * @author nkjm
   *
   * @param <T1>
   * @param <T2>
   * @param <T3>
   */
  public static class Tuple3<T1, T2, T3> extends Tuple2<T1, T2> {

    private final T3 t3;

    Tuple3(T1 t1, T2 t2, T3 t3) {
      super(t1, t2);
      this.t3 = t3;
    }


    /**
     * Gets a t3.
     *
     * @return
     */
    public T3 getT3() {
      return t3;
    }


    @Override
    public String toString() {
      return "(" + (getT1() == null ? "null" : getT1()) + ", "
          + (getT2() == null ? "null" : getT2()) + ", " + (getT3() == null ? "null" : getT3())
          + ")";
    }


    @Override
    public int hashCode() {
      final int prime = 31;
      int result = super.hashCode();
      result = prime * result + Objects.hash(t3);
      return result;
    }


    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (!super.equals(obj))
        return false;
      if (!(obj instanceof Tuple3))
        return false;
      Tuple3<?, ?, ?> other = (Tuple3<?, ?, ?>) obj;
      return Objects.equals(t3, other.t3);
    }

  }

  public static class Tuple4<T1, T2, T3, T4> extends Tuple3<T1, T2, T3> {

    private final T4 t4;

    Tuple4(T1 t1, T2 t2, T3 t3, T4 t4) {
      super(t1, t2, t3);
      this.t4 = t4;
    }

    /**
     * Gets a t4.
     *
     * @return
     */
    public T4 getT4() {
      return t4;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = super.hashCode();
      result = prime * result + Objects.hash(t4);
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (!super.equals(obj))
        return false;
      if (!(obj instanceof Tuple4))
        return false;
      Tuple4<?, ?, ?, ?> other = (Tuple4<?, ?, ?, ?>) obj;
      return Objects.equals(t4, other.t4);
    }

    @Override
    public String toString() {
      return "(" + (getT1() == null ? "null" : getT1()) + ", "
          + (getT2() == null ? "null" : getT2()) + ", " + (getT3() == null ? "null" : getT3())
          + ", " + (getT4() == null ? "null" : getT4()) + ")";
    }

  }


  public static class Tuple5<T1, T2, T3, T4, T5> extends Tuple4<T1, T2, T3, T4> {

    private final T5 t5;

    Tuple5(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5) {
      super(t1, t2, t3, t4);
      this.t5 = t5;
    }

    /**
     * Gets a t5.
     *
     * @return
     */
    public T5 getT5() {
      return t5;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = super.hashCode();
      result = prime * result + Objects.hash(t5);
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (!super.equals(obj))
        return false;
      if (!(obj instanceof Tuple5))
        return false;
      Tuple5<?, ?, ?, ?, ?> other = (Tuple5<?, ?, ?, ?, ?>) obj;
      return Objects.equals(t5, other.t5);
    }

    @Override
    public String toString() {
      return "(" + (getT1() == null ? "null" : getT1()) + ", "
          + (getT2() == null ? "null" : getT2()) + ", " + (getT3() == null ? "null" : getT3())
          + ", " + (getT4() == null ? "null" : getT4()) + ", "
          + (getT5() == null ? "null" : getT5()) + ")";
    }

  }
  public static class Tuple6<T1, T2, T3, T4, T5, T6> extends Tuple5<T1, T2, T3, T4, T5> {

    private final T6 t6;

    Tuple6(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6) {
      super(t1, t2, t3, t4, t5);
      this.t6 = t6;
    }

    /**
     * Gets a t6.
     *
     * @return
     */
    public T6 getT6() {
      return t6;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = super.hashCode();
      result = prime * result + Objects.hash(t6);
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (!super.equals(obj))
        return false;
      if (!(obj instanceof Tuple6))
        return false;
      Tuple6<?, ?, ?, ?, ?, ?> other = (Tuple6<?, ?, ?, ?, ?, ?>) obj;
      return Objects.equals(t6, other.t6);
    }

    @Override
    public String toString() {
      return "(" + (getT1() == null ? "null" : getT1()) + ", "
          + (getT2() == null ? "null" : getT2()) + ", " + (getT3() == null ? "null" : getT3())
          + ", " + (getT4() == null ? "null" : getT4()) + ", "
          + (getT5() == null ? "null" : getT5()) + ", t6=" + (getT6() == null ? "null" : getT6())
          + ")";
    }

  }
}

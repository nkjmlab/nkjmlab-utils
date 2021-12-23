package org.nkjmlab.util.jscience;

import static javax.measure.unit.SI.*;
import javax.measure.quantity.Acceleration;
import javax.measure.quantity.Dimensionless;
import javax.measure.quantity.Duration;
import javax.measure.quantity.Length;
import javax.measure.quantity.Quantity;
import javax.measure.quantity.Velocity;
import javax.measure.unit.NonSI;
import javax.measure.unit.Unit;
import org.jscience.physics.amount.Amount;

public class AmountUtils {

  public static final Amount<Dimensionless> ZERO = Amount.valueOf(0, Unit.ONE);
  public static final Amount<Dimensionless> ONE = Amount.valueOf(0, Unit.ONE);
  public static final Amount<Length> ZERO_METRE = Amount.valueOf(0, METRE);
  public static final Amount<Duration> ZERO_SECOND = Amount.valueOf(0, SECOND);

  public static final Amount<Velocity> ZERO_METRES_PER_SECOND =
      Amount.valueOf(0, METRES_PER_SECOND);

  public static Amount<Acceleration> ZERO_METRES_PER_SQUARE_SECOND =
      Amount.valueOf(0, METRES_PER_SQUARE_SECOND);

  public static final Amount<Duration> ONE_HOUR = Amount.valueOf(0, NonSI.HOUR);;


  public static <T extends Quantity> Amount<T> plusOne(Amount<T> val) {
    return val.plus(Amount.valueOf(1, val.getUnit()));
  }

  public static String toStringWithUnit(Amount<?> val) {
    return toStringWithUnit(val, 0);
  }

  public static String toStringWithUnit(Amount<?> val, int precision) {
    return String.format("%." + precision + "f %s", val.getEstimatedValue(), val.getUnit());
  }


  public static <T extends Quantity> boolean isEqualOrGreaterThan(Amount<T> a, Amount<T> b) {
    return a.equals(b) || a.isGreaterThan(b);
  }


  public static <T extends Quantity> boolean isEqualOrLessThan(Amount<T> a, Amount<T> b) {
    return a.equals(b) || a.isLessThan(b);
  }


  public static <T extends Quantity> Amount<T> max(Amount<T> a, Amount<T> b) {
    return a.compareTo(b) <= 0 ? b : a;
  }


  public static <T extends Quantity> Amount<T> min(Amount<T> a, Amount<T> b) {
    return a.compareTo(b) <= 0 ? a : b;
  }


  public static <T extends Quantity> int mod(Amount<T> dividend, Amount<T> divisor) {
    Unit<T> unit = dividend.getUnit();
    return (int) (dividend.longValue(unit) % divisor.longValue(unit));

  }

  public static <T extends Quantity> Amount<T> parseAs(CharSequence csq, Unit<T> unit) {
    return Amount.valueOf(csq + unit.toString()).to(unit);
  }


  public static <T extends Quantity> Amount<T> times(Amount<?> a, Amount<?> b, Unit<T> unit) {
    return a.times(b).to(unit);
  }

}

package org.nkjmlab.util.jscience;

/*
 * JScience - Java(TM) Tools and Libraries for the Advancement of Sciences. Copyright (C) 2007 -
 * JScience (http://jscience.org/) All rights reserved.
 *
 * Permission to use, copy, modify, and distribute this software is freely granted, provided that
 * this notice is preserved.
 */


import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.measure.Measure;
import javax.measure.converter.AddConverter;
import javax.measure.converter.RationalConverter;
import javax.measure.converter.UnitConverter;
import javax.measure.quantity.Quantity;
import javax.measure.unit.Unit;
import org.apache.commons.math3.util.FastMath;
import org.jscience.physics.amount.Amount;

/**
 * <p>
 * This class represents a measure whose value is an arbitrary-precision decimal number.
 * </p>
 *
 * <p>
 * When converting, applications may supply the <code>java.math.Context</code>:[code]
 * DecimalMeasure<Velocity> c = DecimalMeasure.valueOf("299792458 m/s"); DecimalMeasure<Velocity>
 * milesPerHour = c.to(MILES_PER_HOUR, MathContext.DECIMAL128); System.out.println(milesPerHour);
 *
 * > 670616629.3843951324266284896206156 mph [/code]
 *
 * @author <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @version 4.3, October 3, 2007
 */
public class BigDecimalMeasure<Q extends Quantity> extends Measure<BigDecimal, Q> {

  private static final long serialVersionUID = 1L;
  private static MathContext defaultMathContext = MathContext.DECIMAL128;



  private static final Map<Amount<? extends Quantity>, BigDecimalMeasure<? extends Quantity>> CACHE =
      new ConcurrentHashMap<>();

  ;

  public static <T extends Quantity> BigDecimalMeasure<T> fromWithCache(Amount<T> a) {
    return (BigDecimalMeasure<T>) CACHE.computeIfAbsent(a, key -> toBigDecimalMeasure(key));
  }

  public static <T extends Quantity> BigDecimalMeasure<T> from(Amount<T> a) {
    return toBigDecimalMeasure(a);
  }

  public static <T extends Quantity> BigDecimalMeasure<T> from(double val, Unit<T> u) {
    return valueOf(BigDecimal.valueOf(val), u);
  }

  public static <T extends Quantity> BigDecimalMeasure<T> from(long val, Unit<T> u) {
    return valueOf(BigDecimal.valueOf(val), u);
  }

  public static <T extends Quantity> BigDecimalMeasure<T> from(String val, Unit<T> u) {
    return valueOf(new BigDecimal(val), u);
  }

  public static MathContext getDefaultMathContext() {
    return defaultMathContext;
  }

  public static void setDefaultMathContext(MathContext dEFAULT_MATH_CONTEXT) {
    defaultMathContext = dEFAULT_MATH_CONTEXT;
  }

  public static <T extends Quantity> BigDecimal toBigDecimal(BigDecimalMeasure<T> v, Unit<T> unit) {
    return v.to(unit).getValue();
  }

  public static <T extends Quantity> BigDecimalMeasure<T> toBigDecimalMeasure(Amount<T> a) {
    BigDecimal val = a.isExact() ? BigDecimal.valueOf(a.getExactValue())
        : BigDecimal.valueOf(a.getEstimatedValue());
    return valueOf(val, a.getUnit());
  }

  /**
   * Returns the decimal measure for the specified number stated in the specified unit.
   *
   * @param decimal the measurement value.
   * @param unit the measurement unit.
   */
  public static <Q extends Quantity> BigDecimalMeasure<Q> valueOf(BigDecimal decimal,
      Unit<Q> unit) {
    return new BigDecimalMeasure<Q>(decimal, unit);
  }

  /**
   * Returns the decimal measure for the specified textual representation. This method first reads
   * the <code>BigDecimal</code> value, then the unit if any (value and unit should be separated by
   * white spaces).
   *
   * @param csq the decimal measure representation (including unit if any).
   * @throws NumberFormatException if the specified character sequence is not a valid representation
   *         of decimal measure.
   */
  @SuppressWarnings("unchecked")
  public static <Q extends Quantity> BigDecimalMeasure<Q> valueOf(CharSequence csq) {
    String str = csq.toString();
    int numberLength = str.length();
    int unitStartIndex = -1;
    for (int i = 0; i < str.length(); i++) {
      if (Character.isWhitespace(str.charAt(i))) {
        for (int j = i + 1; j < str.length(); j++) {
          if (!Character.isWhitespace(str.charAt(j))) {
            unitStartIndex = j;
            break;
          }
        }
        numberLength = i;
        break;
      }
    }
    BigDecimal decimal = new BigDecimal(str.substring(0, numberLength));
    Unit unit = Unit.ONE;
    if (unitStartIndex > 0) {
      unit = Unit.valueOf(str.substring(unitStartIndex));
    }
    return new BigDecimalMeasure<Q>(decimal, unit);
  }

  private final MathContext mathContext;

  /**
   * Holds the BigDecimal value.
   */
  private final BigDecimal _value;

  /**
   * Holds the unit.
   */
  private final Unit<Q> _unit;

  /**
   * Creates a decimal measure for the specified number stated in the specified unit.
   */
  public BigDecimalMeasure(BigDecimal value, Unit<Q> unit) {
    this._value = value;
    this._unit = unit;
    this.mathContext = defaultMathContext;
  }

  public <Q extends Quantity> BigDecimalMeasure<Q> as(Unit<Q> unit) {
    return to(unit, mathContext);
  }


  public BigDecimalMeasure<? extends Quantity> divide(
      BigDecimalMeasure<? extends Quantity> divisor) {
    BigDecimal val = getValue().divide(divisor.getValue(), mathContext);
    return valueOf(val, getUnit().divide(divisor.getUnit()));
  }

  @Override
  public double doubleValue(Unit<Q> unit) {
    if ((unit == _unit) || (unit.equals(_unit)))
      return _value.doubleValue();
    return _unit.getConverterTo(unit).convert(_value.doubleValue());
  }



  @Override
  public Unit<Q> getUnit() {
    return _unit;
  }


  @Override
  public BigDecimal getValue() {
    return _value;
  }



  public BigDecimalMeasure<Q> minus(BigDecimalMeasure<Q> b) {
    Unit<Q> unit = getUnit();
    return valueOf(toBigDecimal().subtract(toBigDecimal(b, unit)), unit);
  }

  public BigDecimalMeasure<Q> plus(BigDecimalMeasure<Q> b) {
    Unit<Q> unit = getUnit();
    return valueOf(toBigDecimal().add(toBigDecimal(b, unit)), unit);
  }

  public BigDecimalMeasure<? extends Quantity> pow(int n) {
    return valueOf(BigDecimal.valueOf(FastMath.pow(toBigDecimal().doubleValue(), (double) n)),
        getUnit().pow(n));
  }

  public BigDecimalMeasure<? extends Quantity> times(BigDecimalMeasure<? extends Quantity> timer) {
    BigDecimal val = getValue().multiply(timer.getValue(), mathContext);
    BigDecimalMeasure<? extends Quantity> t = valueOf(val, getUnit().times(timer.getUnit()));
    return t;
  }

  /**
   * Returns the decimal measure equivalent to this measure but stated in the specified unit. This
   * method will raise an ArithmeticException if the resulting measure does not have a terminating
   * decimal expansion.
   *
   * @param unit the new measurement unit.
   * @return the measure stated in the specified unit.
   * @throws ArithmeticException if the converted measure value does not have a terminating decimal
   *         expansion
   * @see #to(Unit, MathContext)
   */
  @Override
  public BigDecimalMeasure<Q> to(Unit<Q> unit) {
    return to(unit, mathContext);
  }


  /**
   * Returns the decimal measure equivalent to this measure but stated in the specified unit, the
   * conversion is performed using the specified math context.
   *
   * @param unit the new measurement unit.
   * @param mathContext the mathContext used to convert <code>BigDecimal</code> values or
   *        <code>null</code> if none.
   * @return the measure stated in the specified unit.
   * @throws ArithmeticException if the result is inexact but the rounding mode is
   *         <code>MathContext.UNNECESSARY</code> or <code>mathContext.precision == 0</tt> and the
   *         quotient has a non-terminating decimal expansion.
   */
  public <Q extends Quantity> BigDecimalMeasure<Q> to(Unit<Q> unit, MathContext mathContext) {
    if ((unit == _unit) || (unit.equals(_unit)))
      return (BigDecimalMeasure<Q>) this;
    UnitConverter cvtr = _unit.getConverterTo(unit);
    if (cvtr instanceof RationalConverter) {
      RationalConverter factor = (RationalConverter) cvtr;
      BigDecimal dividend = BigDecimal.valueOf(factor.getDividend());
      BigDecimal divisor = BigDecimal.valueOf(factor.getDivisor());
      BigDecimal result = mathContext == null ? _value.multiply(dividend).divide(divisor)
          : _value.multiply(dividend, mathContext).divide(divisor, mathContext);
      return new BigDecimalMeasure<Q>(result, unit);
    } else if (cvtr.isLinear()) {
      BigDecimal factor = BigDecimal.valueOf(cvtr.convert(1.0));
      BigDecimal result =
          mathContext == null ? _value.multiply(factor) : _value.multiply(factor, mathContext);
      return new BigDecimalMeasure<Q>(result, unit);
    } else if (cvtr instanceof AddConverter) {
      BigDecimal offset = BigDecimal.valueOf(((AddConverter) cvtr).getOffset());
      BigDecimal result =
          mathContext == null ? _value.add(offset) : _value.add(offset, mathContext);
      return new BigDecimalMeasure<Q>(result, unit);
    } else { // Non-linear and not an offset, convert the double value.
      BigDecimal result = BigDecimal.valueOf(cvtr.convert(_value.doubleValue()));
      return new BigDecimalMeasure<Q>(result, unit);
    }
  }

  public Amount<Q> toAmount() {
    return Amount.valueOf(doubleValue(getUnit()), getUnit());
  }

  private BigDecimal toBigDecimal() {
    return toBigDecimal(this, getUnit());
  }

}

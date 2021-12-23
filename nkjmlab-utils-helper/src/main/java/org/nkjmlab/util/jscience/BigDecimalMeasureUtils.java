package org.nkjmlab.util.jscience;

import java.math.BigDecimal;
import javax.measure.quantity.Dimensionless;
import javax.measure.quantity.Length;
import javax.measure.quantity.Quantity;
import javax.measure.unit.Unit;

public class BigDecimalMeasureUtils {

  public static final BigDecimalMeasure<Dimensionless> ZERO = BigDecimalMeasureUtils.one(0);
  public static final BigDecimalMeasure<Dimensionless> ONE = BigDecimalMeasureUtils.one(1);
  public static final BigDecimalMeasure<Length> ZERO_METRE =
      BigDecimalMeasure.fromWithCache(AmountUtils.ZERO_METRE);



  public static BigDecimalMeasure<Dimensionless> one(long val) {
    return BigDecimalMeasure.valueOf(BigDecimal.valueOf(val), Unit.ONE);
  }

  public static <T extends Quantity> BigDecimalMeasure<T> max(BigDecimalMeasure<T> a,
      BigDecimalMeasure<T> b) {
    return a.compareTo(b) <= 0 ? b : a;
  }

  public static <T extends Quantity> BigDecimalMeasure<T> min(BigDecimalMeasure<T> a,
      BigDecimalMeasure<T> b) {
    return a.compareTo(b) <= 0 ? a : b;
  }


}

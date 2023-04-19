package org.nkjmlab.util.java.lang;

public final class StringFormatter {

  private StringFormatter() {}

  /**
   * @see ParameterizedStringFormatter#format(String, Object...)
   * @return
   */
  public static String format(String msg, Object... params) {
    return ParameterizedStringFormatter.NO_LENGTH_LIMIT.format(msg, params);
  }

  public static String format(int maxLength, String msg, Object... params) {
    ParameterizedStringFormatter formatter = getFormatter(maxLength);
    return formatter.format(msg, params);
  }

  private static ParameterizedStringFormatter getFormatter(int maxLength) {
    switch (maxLength) {
      case 8:
        return ParameterizedStringFormatter.LENGTH_8;
      case 16:
        return ParameterizedStringFormatter.LENGTH_16;
      case 32:
        return ParameterizedStringFormatter.LENGTH_32;
      case 64:
        return ParameterizedStringFormatter.LENGTH_64;
      case 128:
        return ParameterizedStringFormatter.LENGTH_128;
      case 256:
        return ParameterizedStringFormatter.LENGTH_256;
      case 512:
        return ParameterizedStringFormatter.LENGTH_512;
      default:
        return new ParameterizedStringFormatter(maxLength);
    }
  }


}

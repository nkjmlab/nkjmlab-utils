package org.nkjmlab.util.java.lang;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Stream;

public final class ParameterizedStringFormatter {

  private static final String DEFAULT_PLACEHOLDER = "{}";

  public static final ParameterizedStringFormatter LENGTH_8 =
      new ParameterizedStringFormatter(DEFAULT_PLACEHOLDER, 8);
  public static final ParameterizedStringFormatter LENGTH_16 =
      new ParameterizedStringFormatter(DEFAULT_PLACEHOLDER, 16);
  public static final ParameterizedStringFormatter LENGTH_32 =
      new ParameterizedStringFormatter(DEFAULT_PLACEHOLDER, 32);
  public static final ParameterizedStringFormatter LENGTH_64 =
      new ParameterizedStringFormatter(DEFAULT_PLACEHOLDER, 64);
  public static final ParameterizedStringFormatter LENGTH_128 =
      new ParameterizedStringFormatter(DEFAULT_PLACEHOLDER, 128);
  public static final ParameterizedStringFormatter LENGTH_256 =
      new ParameterizedStringFormatter(DEFAULT_PLACEHOLDER, 256);
  public static final ParameterizedStringFormatter LENGTH_512 =
      new ParameterizedStringFormatter(DEFAULT_PLACEHOLDER, 512);
  public static final ParameterizedStringFormatter LENGTH_1024 =
      new ParameterizedStringFormatter(DEFAULT_PLACEHOLDER, 1024);
  public static final ParameterizedStringFormatter NO_LENGTH_LIMIT =
      new ParameterizedStringFormatter(DEFAULT_PLACEHOLDER, Integer.MAX_VALUE);

  public static final ParameterizedStringFormatter DEFAULT = NO_LENGTH_LIMIT;

  private final String placeholder;
  private final int maxLength;

  /**
   *
   * @param maxLength of a string of one parameter
   */
  public ParameterizedStringFormatter(int maxLength) {
    this(DEFAULT_PLACEHOLDER, maxLength);
  }

  /**
   *
   * @param placeholder
   * @param maxLength of a string of one parameter
   */
  public ParameterizedStringFormatter(String placeholder, int maxLength) {
    this.placeholder = placeholder;
    this.maxLength = maxLength;
  }

  /**
   *
   * Creates a new parameterized message like <a href=
   * "https://logging.apache.org/log4j/2.x/log4j-api/apidocs/org/apache/logging/log4j/message/ParameterizedMessageFactory.html#newMessage-java.lang.String-java.lang.Object...-">ParameterizedMessageFactory
   * (Apache Log4j API 2.14.1 API)</a>
   *
   * <p>
   * This method simulate as follows:
   * <code>ParameterizedMessageFactory.INSTANCE.newMessage(msg, params).getFormattedMessage()</code>
   *
   * <p>
   * Examples
   *
   * <pre>
   * format("My name is {}. My score is {}", "Alice",100) =&gt; "My name is Alice. My score is 100";
   *
   * format("{}" , 1) =&gt; "1";
   *
   * format("{}{}", 1) =&gt; "1{}";
   *
   * format("{}", 1, 2) =&gt; "1";
   *
   * @param msg
   * @param params
   * @return
   */
  public String format(String msg, Object... params) {
    return newString(msg, placeholder, maxLength, params);
  }

  public String formatParameterWithType(Object... params) {
    return String.join(", ",
        Stream.of(params).map(p -> parameterToStringWithType(maxLength, p)).toArray(String[]::new));
  }


  public String formatParameter(Object... params) {
    return String.join(", ",
        Stream.of(params).map(p -> parameterToString(maxLength, p)).toArray(String[]::new));
  }


  public static String newString(String msg, String placeholder, int maxLength, Object... params) {
    if (params == null || params.length == 0) {
      return msg;
    }
    return newStringAux(msg, placeholder, params.length,
        index -> parameterToString(maxLength, params[index]));
  }



  private static String trim(String string, int maxLength) {
    return string.length() <= maxLength ? string : (string.substring(0, maxLength) + "...");
  }

  private static String newStringAux(String msg, String placeholder, int numOfParameter,
      Function<Integer, String> parameterReplacer) {
    if (msg == null) {
      return "null";
    }
    final StringBuilder sbuf = new StringBuilder(msg.length() + 50);
    int i = 0;
    int j;
    final int placeholderStringLength = placeholder.length();
    for (int p = 0; p < numOfParameter; p++) {
      j = msg.indexOf(placeholder, i);
      if (j == -1) {
        break;
      }
      sbuf.append(msg, i, j);
      sbuf.append(parameterReplacer.apply(p));
      i = j + placeholderStringLength;
    }
    sbuf.append(msg, i, msg.length());
    return sbuf.toString();
  }

  private static String parameterToString(int maxLength, Object param) {
    if (param == null) {
      return "null";
    } else if (param.getClass().isArray()) {
      String s = Arrays.deepToString(new Object[] {param});
      return trim(s.substring(1, s.length()), maxLength);
    } else {
      return trim(param.toString(), maxLength);
    }
  }

  private static String parameterToStringWithType(int maxLength, Object param) {
    if (param == null) {
      return "null";
    } else if (param.getClass().isArray()) {
      String s = Arrays.deepToString(new Object[] {param});
      return "(" + param.getClass().getSimpleName() + ") "
          + trim(s.substring(1, s.length()), maxLength);
    } else {
      return "(" + param.getClass().getSimpleName() + ") " + trim(param.toString(), maxLength);
    }
  }

}

package org.nkjmlab.util.java.lang;

import java.util.Arrays;
import java.util.function.Function;

public class ParameterizedStringUtils {

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
   * @param msg
   * @param params
   * @return
   */
  public static String newString(String msg, Object... params) {
    if (params == null || params.length == 0) {
      return msg;
    }
    return newString(msg, "{}", params.length, index -> {
      Object o = params[index];
      if (o == null) {
        return "null";
      } else if (o.getClass().isArray()) {
        String s = Arrays.deepToString(new Object[] {o});
        return s.substring(1, s.length());
      } else {
        return o.toString();
      }
    });
  }

  public static String newString(String messege, String parameter, int numOfPlaceholder,
      Function<Integer, String> parameterReplacer) {
    final int placeholderLength = parameter.length();
    final StringBuilder sbuf = new StringBuilder(messege.length() + 50);
    int i = 0;
    int j;
    for (int p = 0; p < numOfPlaceholder; p++) {
      j = messege.indexOf(parameter, i);
      sbuf.append(messege, i, j);
      sbuf.append(parameterReplacer.apply(p));
      i = j + placeholderLength;
    }
    sbuf.append(messege, i, messege.length());
    return sbuf.toString();
  }

}

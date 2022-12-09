package org.nkjmlab.util.jakarta.servlet;

import java.util.Optional;
import jakarta.servlet.http.HttpServletRequest;

public class HttpRequestUtils {

  public static Optional<String> getXForwardedFor(HttpServletRequest request) {
    return Optional.ofNullable(request.getHeader("X-Forwarded-For"));
  }


}

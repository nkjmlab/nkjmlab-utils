package org.nkjmlab.util.java.web;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class BasicAuthValidator {
  private final String credential;
  private final String wwwAuthenticateResponseHeaderValue;

  public BasicAuthValidator(String realm, String userId, String password) {
    this.credential =
        Base64.getEncoder()
            .encodeToString((userId + ":" + password).getBytes(StandardCharsets.UTF_8));
    this.wwwAuthenticateResponseHeaderValue = "Basic realm=\"" + realm + "\"";
  }

  public boolean validateBasicAuth(String authorizationHeaderValue) {
    if (authorizationHeaderValue == null) {
      return false;
    }
    String basicAuthCredInHeader = authorizationHeaderValue.replace("Basic", "").trim();
    return credential.equals(basicAuthCredInHeader);
  }

  public String getWwwAuthenticateResponseHeaderValue() {
    return wwwAuthenticateResponseHeaderValue;
  }

  @Override
  public String toString() {
    return "BasicAuthValidator [credential="
        + "********"
        + ", responseHeaderValue="
        + wwwAuthenticateResponseHeaderValue
        + "]";
  }
}

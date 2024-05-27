package org.nkjmlab.util.java.web;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.function.Predicate;

public class BasicAuthValidator {
  private final String wwwAuthenticateResponseHeaderValue;
  private final Predicate<BasicAuthCredential> credentialValidator;

  public BasicAuthValidator(String realm, Predicate<BasicAuthCredential> credentialValidator) {
    this.wwwAuthenticateResponseHeaderValue = "Basic realm=\"" + realm + "\"";
    this.credentialValidator = credentialValidator;
  }

  public BasicAuthResult validateBasicAuth(String authorizationHeaderValue) {
    try {
      BasicAuthCredential cred = toCredential(authorizationHeaderValue);
      return cred == null ? new BasicAuthResult(false, null) : validateBasicAuth(cred);
    } catch (Exception e) {
      return new BasicAuthResult(false, null);
    }
  }

  public BasicAuthResult validateBasicAuth(BasicAuthCredential credential) {
    return new BasicAuthResult(credentialValidator.test(credential), credential.username());
  }

  public static BasicAuthCredential toCredential(String authorizationHeaderValue) {
    try {
      if (authorizationHeaderValue == null) {
        return null;
      }
      String basicAuthCredInHeader = authorizationHeaderValue.replaceFirst("Basic", "").trim();
      byte[] decodedBytes = Base64.getDecoder().decode(basicAuthCredInHeader);
      String[] decodedStrings = new String(decodedBytes, StandardCharsets.UTF_8).split(":");
      String username = decodedStrings[0];
      String password = decodedStrings[1];
      return new BasicAuthCredential(username, password);
    } catch (Exception e) {
      return null;
    }
  }

  public String getWwwAuthenticateResponseHeaderValue() {
    return wwwAuthenticateResponseHeaderValue;
  }

  @Override
  public String toString() {
    return "BasicAuthValidator [responseHeaderValue=" + wwwAuthenticateResponseHeaderValue + "]";
  }

  public record BasicAuthCredential(String username, String password) {}

  public record BasicAuthResult(boolean authenticated, String username) {}
}

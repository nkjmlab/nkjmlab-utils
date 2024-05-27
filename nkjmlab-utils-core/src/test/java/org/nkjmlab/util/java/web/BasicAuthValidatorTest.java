package org.nkjmlab.util.java.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Base64;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.nkjmlab.util.java.web.BasicAuthValidator.BasicAuthCredential;
import org.nkjmlab.util.java.web.BasicAuthValidator.BasicAuthResult;

public class BasicAuthValidatorTest {

  private BasicAuthValidator validator;

  @BeforeEach
  public void setUp() {
    Predicate<BasicAuthCredential> credentialValidator =
        cred -> "user".equals(cred.username()) && "pass".equals(cred.password());
    validator = new BasicAuthValidator("test-realm", credentialValidator);
  }

  @Test
  public void testValidateBasicAuth_ValidCredentials() {
    String credentials = "user:pass";
    String authHeaderValue = "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes());

    BasicAuthResult result = validator.validateBasicAuth(authHeaderValue);
    assertTrue(result.authenticated());
    assertEquals("user", result.username());
  }

  @Test
  public void testValidateBasicAuth_InvalidCredentials() {
    String credentials = "user:wrongpass";
    String authHeaderValue = "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes());

    BasicAuthResult result = validator.validateBasicAuth(authHeaderValue);
    assertFalse(result.authenticated());
    assertEquals("user", result.username());
  }

  @Test
  public void testValidateBasicAuth_NullHeader() {
    BasicAuthResult result = validator.validateBasicAuth((String) null);
    assertFalse(result.authenticated());
    assertEquals(null, result.username());
  }

  @Test
  public void testValidateBasicAuth_InvalidHeaderFormat() {
    String authHeaderValue = "InvalidHeaderFormat";

    BasicAuthResult result = validator.validateBasicAuth(authHeaderValue);
    assertFalse(result.authenticated());
    assertEquals(null, result.username());
  }

  @Test
  public void testToCredential_ValidHeader() {
    String credentials = "user:pass";
    String authHeaderValue = "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes());

    BasicAuthCredential credential = BasicAuthValidator.toCredential(authHeaderValue);
    assertEquals("user", credential.username());
    assertEquals("pass", credential.password());
  }

  @Test
  public void testToCredential_InvalidHeader() {
    String authHeaderValue = "InvalidHeader";

    BasicAuthCredential credential = BasicAuthValidator.toCredential(authHeaderValue);
    assertEquals(null, credential);
  }

  @Test
  public void testGetWwwAuthenticateResponseHeaderValue() {
    String expectedHeaderValue = "Basic realm=\"test-realm\"";
    assertEquals(expectedHeaderValue, validator.getWwwAuthenticateResponseHeaderValue());
  }

  @Test
  public void testToString() {
    String expectedString = "BasicAuthValidator [responseHeaderValue=Basic realm=\"test-realm\"]";
    assertEquals(expectedString, validator.toString());
  }
}

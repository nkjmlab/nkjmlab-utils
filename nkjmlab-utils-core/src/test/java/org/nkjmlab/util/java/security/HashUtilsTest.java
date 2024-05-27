package org.nkjmlab.util.java.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class HashUtilsTest {

  @Test
  public void testGetAvailableAlgorithms() {
    Set<String> algorithms = HashUtils.getAvailableAlgorithms();
    assertNotNull(algorithms);
    System.out.println("Available algorithms: " + algorithms);
  }

  @Test
  public void testGetMessageDigest() {
    String algorithm = "SHA-256";
    MessageDigest digest = HashUtils.getMessageDigest(algorithm);
    assertNotNull(digest);
    assertEquals(algorithm, digest.getAlgorithm());
  }

  @Test
  public void testHash() {
    String message = "testMessage";
    String salt = "testSalt";
    MessageDigest digest = HashUtils.getMessageDigest("SHA-256");
    String expectedHash = hashManually(message + salt, digest);

    String actualHash = HashUtils.hash(message, digest, StandardCharsets.UTF_8, salt);
    assertEquals(expectedHash, actualHash);
  }

  private String hashManually(String input, MessageDigest digest) {
    byte[] hashedBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
    return Base64.getEncoder().encodeToString(hashedBytes);
  }
}

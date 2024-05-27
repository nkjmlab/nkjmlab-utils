package org.nkjmlab.util.java.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class StandardHashAlgorithmTest {

  @Test
  public void testOf() {
    StandardHashAlgorithm algorithm = StandardHashAlgorithm.of("SHA-256");
    assertNotNull(algorithm);
    assertEquals(StandardHashAlgorithm.SHA_256, algorithm);
  }

  @Test
  public void testHash() {
    String message = "testMessage";
    String salt = "testSalt";
    StandardHashAlgorithm algorithm = StandardHashAlgorithm.SHA_256;
    String expectedHash =
        HashUtils.hash(message, algorithm.getDigest(), StandardCharsets.UTF_8, salt);

    String actualHash = algorithm.hash(message, StandardCharsets.UTF_8, salt);
    assertEquals(expectedHash, actualHash);
  }
}

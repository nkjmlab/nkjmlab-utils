package org.nkjmlab.util.java.security;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class HashGeneratorTest {

  @Test
  public void testHashWithSalt() {
    HashGenerator hashGenerator = new HashGenerator(StandardHashAlgorithm.SHA_256);
    String message = "testMessage";
    String salt = "testSalt";
    String expectedHash = StandardHashAlgorithm.SHA_256.hash(message, StandardCharsets.UTF_8, salt);

    String actualHash = hashGenerator.hash(message, salt);
    assertEquals(expectedHash, actualHash);
  }
}

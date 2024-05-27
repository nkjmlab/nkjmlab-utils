package org.nkjmlab.util.java.security;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class SaltedHashGaneratorTest {

  @Test
  public void testHash() {
    String salt = "testSalt";
    SaltedHashGanerator saltedHashGanerator =
        new SaltedHashGanerator(StandardHashAlgorithm.SHA_256, salt);
    String message = "testMessage";
    String expectedHash = StandardHashAlgorithm.SHA_256.hash(message, StandardCharsets.UTF_8, salt);

    String actualHash = saltedHashGanerator.hash(message);
    assertEquals(expectedHash, actualHash);
  }
}

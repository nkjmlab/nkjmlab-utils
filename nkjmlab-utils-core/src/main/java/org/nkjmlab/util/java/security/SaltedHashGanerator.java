package org.nkjmlab.util.java.security;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class SaltedHashGanerator extends HashGenerator {

  private final String salt;

  public SaltedHashGanerator(StandardHashAlgorithm algorithmName, Charset charset, String salt) {
    super(algorithmName, charset);
    this.salt = salt;
  }

  public SaltedHashGanerator(StandardHashAlgorithm algorithmName, String salt) {
    this(algorithmName, StandardCharsets.UTF_8, salt);
  }

  public String hash(String message) {
    return super.hash(message, salt);
  }
}

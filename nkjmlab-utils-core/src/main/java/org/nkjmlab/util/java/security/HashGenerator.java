package org.nkjmlab.util.java.security;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class HashGenerator {

  private final StandardHashAlgorithm algorithmName;

  private final Charset charset;

  public HashGenerator(StandardHashAlgorithm algorithmName, Charset charset) {
    this.algorithmName = algorithmName;
    this.charset = charset;
  }

  public HashGenerator(StandardHashAlgorithm algorithmName) {
    this(algorithmName, StandardCharsets.UTF_8);
  }

  public String hash(String message, String salt) {
    return algorithmName.hash(message, charset, salt);
  }
}

package org.nkjmlab.util.java.security;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.nkjmlab.util.java.security.HashUtils.Algorithms;

public class HashGenerator {

  private final Algorithms algorithmName;

  private final Charset charset;

  public HashGenerator(Algorithms algorithmName, Charset charset) {
    this.algorithmName = algorithmName;
    this.charset = charset;
  }

  public HashGenerator(Algorithms algorithmName) {
    this(algorithmName, StandardCharsets.UTF_8);
  }

  public String hash(String message, String salt) {
    return HashUtils.hash(message, algorithmName, charset, salt);
  }

}

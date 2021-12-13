package org.nkjmlab.util.java.security;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.nkjmlab.util.java.security.HashUtils.Algorithms;

public class SaltedHashGanerator {

  private final Algorithms algorithmName;

  private final Charset charset;

  private final String salt;

  public SaltedHashGanerator(Algorithms algorithmName, Charset charset, String salt) {
    this.algorithmName = algorithmName;
    this.charset = charset;
    this.salt = salt;
  }

  public SaltedHashGanerator(Algorithms algorithmName, String salt) {
    this(algorithmName, StandardCharsets.UTF_8, salt);
  }

  public String hash(String message) {
    return HashUtils.hash(message, algorithmName, charset, salt);
  }


}

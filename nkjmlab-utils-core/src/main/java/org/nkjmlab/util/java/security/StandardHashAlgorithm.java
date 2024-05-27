package org.nkjmlab.util.java.security;

import java.nio.charset.Charset;
import java.security.MessageDigest;

public enum StandardHashAlgorithm {
  MD5("MD5"),
  SHA_256("SHA-256"),
  SHA_512("SHA-512");

  private final String algorithmName;
  private final MessageDigest digest;

  private StandardHashAlgorithm(String algorithmName) {
    this.algorithmName = algorithmName;
    this.digest = HashUtils.getMessageDigest(algorithmName);
  }

  @Override
  public String toString() {
    return algorithmName;
  }

  public MessageDigest getDigest() {
    return digest;
  }

  public static StandardHashAlgorithm of(String n) {
    return StandardHashAlgorithm.valueOf(n.replace("-", "_"));
  }

  public String hash(String message, Charset charset, String salt) {
    return HashUtils.hash(message, digest, charset, salt);
  }
}

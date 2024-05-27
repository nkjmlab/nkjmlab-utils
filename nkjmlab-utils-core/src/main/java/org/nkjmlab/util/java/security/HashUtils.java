package org.nkjmlab.util.java.security;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Base64;
import java.util.Set;

public class HashUtils {

  public static Set<String> getAvailableAlgorithms() {
    return Security.getAlgorithms("MessageDigest");
  }

  public static MessageDigest getMessageDigest(String algorithmName) {
    try {
      return java.security.MessageDigest.getInstance(algorithmName);
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }

  public static String hash(String message, MessageDigest digest, Charset charset, String salt) {
    byte[] hashedBytes = digest.digest((message + salt).getBytes(charset));
    return Base64.getEncoder().encodeToString(hashedBytes);
  }
}

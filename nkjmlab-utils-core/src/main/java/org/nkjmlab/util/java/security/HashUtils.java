package org.nkjmlab.util.java.security;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HashUtils {

  public enum Algorithms {
    MD2("MD2"), MD5("MD5"), SHA1("SHA-1"), SHA224("SHA-224"), SHA256("SHA-256"), SHA384(
        "SHA-384"), SHA512("SHA-512");

    private final String algorithmName;

    private Algorithms(String algorithmName) {
      this.algorithmName = algorithmName;
    }

    @Override
    public String toString() {
      return algorithmName;
    }

    public static Set<Algorithms> getAvailableAlgorithms() {
      Set<String> aNames = Security.getAlgorithms("MessageDigest");
      return Stream.of(Algorithms.values()).filter(a -> aNames.contains(a.toString()))
          .collect(Collectors.toUnmodifiableSet());
    }

  }

  public static Set<String> getAvailableAlgorithms() {
    return Security.getAlgorithms("MessageDigest");
  }

  /**
   *
   * @param message
   * @param algorithm e.g. Algorithms.SHA256
   * @param charset e.g. StandardCharsets.UTF_8
   * @param salt
   * @return
   */
  public static String hash(String message, Algorithms algorithm, Charset charset, String salt) {
    byte[] digest = messageDigest(message, algorithm, charset, salt);
    StringBuilder hashStr = new StringBuilder();
    for (int i = 0, l = digest.length; i < l; i++) {
      int h = digest[i];
      if (h < 0) {
        hashStr.append(Integer.toHexString(h + 256));
      } else {
        if (h < 16) {
          hashStr.append("0");
        }
        hashStr.append(Integer.toHexString(h));
      }
    }
    return hashStr.toString();
  }


  /**
   *
   * @param message
   * @param algorithm
   * @param charset
   * @param salt
   * @return
   */
  private static byte[] messageDigest(String message, Algorithms algorithm, Charset charset,
      String salt) {
    try {
      MessageDigest digest = java.security.MessageDigest.getInstance(algorithm.toString());
      return digest.digest((message + salt).getBytes(charset));
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }


}

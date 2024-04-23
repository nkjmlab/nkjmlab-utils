package org.nkjmlab.util.java.io;

import java.util.Scanner;

public class WaitSystemInUtils {
  private WaitSystemInUtils() {}

  public static String waitAndReadSystemIn(String prompt) {
    Scanner scanner = null;
    String name = null;
    try {
      scanner = new Scanner(System.in);
      System.out.print(prompt);
      scanner.nextLine();
    } finally {
      if (scanner != null) {
        scanner.close();
      }
    }
    return name;
  }
}

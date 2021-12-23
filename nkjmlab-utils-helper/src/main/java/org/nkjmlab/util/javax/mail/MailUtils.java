package org.nkjmlab.util.javax.mail;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import org.apache.commons.io.input.CloseShieldInputStream;

public class MailUtils {

  @SuppressWarnings("deprecation")
  public static String promptToInputPassword(String loginId, String host) {
    System.out.print("$ password for " + loginId + "@" + host + " >");
    try (Scanner s = new Scanner(new CloseShieldInputStream(System.in))) {
      String str = s.nextLine();
      return str;
    }
  }

  @SuppressWarnings("deprecation")
  public static boolean promptYesOrNo(String message) {
    System.out.print(message);
    try (Scanner s = new Scanner(new CloseShieldInputStream(System.in))) {
      String val = s.nextLine();
      switch (val) {
        case "y": {
          return true;
        }
        case "n": {
          return false;
        }
        default:
          System.out.println("input [y or n]");
          return promptYesOrNo(message);
      }
    }
  }


  public static String getMailHeader(MimeMessage message) {
    try {
      String from = "from: " + String.join(",",
          Arrays.stream(message.getFrom()).map(a -> a.toString()).collect(Collectors.toList()));
      String to = "to: " + String.join(",", Arrays.stream(message.getRecipients(RecipientType.TO))
          .map(a -> a.toString()).collect(Collectors.toList()));
      String cc = "cc: " + String.join(",", Arrays.stream(message.getRecipients(RecipientType.CC))
          .map(a -> a.toString()).collect(Collectors.toList()));
      String bcc =
          "bcc: " + String.join(",", Arrays.stream(message.getRecipients(RecipientType.BCC))
              .map(a -> a.toString()).collect(Collectors.toList()));
      String subject = message.getSubject();

      return String.join(System.lineSeparator(), from, to, cc, bcc, subject);
    } catch (MessagingException e) {
      throw new RuntimeException(e);
    }

  }

}

package org.nkjmlab.util.javax.mail;

import java.util.Arrays;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender {
  private final String loginId;
  private final String password;
  private final String host;
  private final int port;

  public MailSender(String host, int port, String loginId, String password) {
    this.host = host;
    this.port = port;
    this.loginId = loginId;
    this.password = password;
  }

  /**
   * @param to
   * @param bcc the mail address for BCC
   * @param subject
   * @param text
   * @return
   */
  public MimeMessage createMimeMessage(
      String from, String to, String cc, String bcc, String subject, String text) {
    try {
      Session session = createSession();
      MimeMessage mimeMessage = new MimeMessage(session);
      mimeMessage.setFrom(new InternetAddress(from));
      mimeMessage.addRecipient(RecipientType.TO, new InternetAddress(to));
      if (cc != null && cc.length() != 0) {
        mimeMessage.addRecipient(RecipientType.CC, new InternetAddress(cc));
      }
      if (bcc != null && bcc.length() != 0) {
        mimeMessage.addRecipient(RecipientType.BCC, new InternetAddress(bcc));
      }
      mimeMessage.setSubject(subject);
      mimeMessage.setText(text);
      mimeMessage.saveChanges();
      return mimeMessage;
    } catch (MessagingException e) {
      throw new RuntimeException(e);
    }
  }

  public void sendMessage(MimeMessage message) {
    try {
      Transport.send(message);
    } catch (MessagingException e) {
      throw new RuntimeException(e);
    }
  }

  public static String getMailHeader(MimeMessage message) {
    try {
      String from =
          "from: "
              + String.join(
                  ",",
                  Arrays.stream(message.getFrom())
                      .map(a -> a.toString())
                      .collect(Collectors.toList()));
      String to =
          "to: "
              + String.join(
                  ",",
                  Arrays.stream(message.getRecipients(RecipientType.TO))
                      .map(a -> a.toString())
                      .collect(Collectors.toList()));
      String cc =
          "cc: "
              + String.join(
                  ",",
                  Arrays.stream(message.getRecipients(RecipientType.CC))
                      .map(a -> a.toString())
                      .collect(Collectors.toList()));
      String bcc =
          "bcc: "
              + String.join(
                  ",",
                  Arrays.stream(message.getRecipients(RecipientType.BCC))
                      .map(a -> a.toString())
                      .collect(Collectors.toList()));
      String subject = message.getSubject();

      return String.join(System.lineSeparator(), from, to, cc, bcc, subject);
    } catch (MessagingException e) {
      throw new RuntimeException(e);
    }
  }

  private Session createSession() {

    Properties properties = new Properties();

    properties.setProperty("mail.smtp.host", host);
    properties.setProperty("mail.smtp.port", String.valueOf(port));

    if (port != 25) {
      properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
      properties.setProperty("mail.smtp.socketFactory.fallback", "false");
      properties.setProperty("mail.smtp.socketFactory.port", String.valueOf(port));
    }
    properties.setProperty("mail.smtp.connectiontimeout", "50000");
    properties.setProperty("mail.smtp.timeout", "50000");

    // properties.setProperty("mail.debug", String.valueOf(true));
    properties.setProperty("mail.smtp.auth", "true");

    return Session.getInstance(properties, createAuthenticator());
  }

  private Authenticator createAuthenticator() {
    Authenticator auth =
        new Authenticator() {
          @Override
          protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(loginId, password);
          }
        };
    return auth;
  }
}

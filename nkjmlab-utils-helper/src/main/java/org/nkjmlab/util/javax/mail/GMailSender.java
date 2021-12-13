package org.nkjmlab.util.javax.mail;

public class GMailSender extends MailSender {

  public GMailSender(String loginId, String password) {
    super("smtp.gmail.com", 465, loginId, password);
  }

}

package org.nkjmlab.util.javax.mail;

public class GmailSender extends MailSender {

  public GmailSender(String loginId, String password) {
    super("smtp.gmail.com", 465, loginId, password);
  }

}

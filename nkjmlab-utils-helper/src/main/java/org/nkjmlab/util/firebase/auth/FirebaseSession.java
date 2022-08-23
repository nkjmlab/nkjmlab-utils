package org.nkjmlab.util.firebase.auth;


import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.nkjmlab.util.firebase.auth.FirebaseAccountsTable.FirebaseAccount;

public class FirebaseSession {

  private static final String FIREBASE_ACCOUNT = "FIREBASE_ACCOUNT";

  private final HttpSession session;


  public static FirebaseSession wrap(HttpSession session) {
    return new FirebaseSession(session);
  }

  private FirebaseSession(HttpSession session) {
    this.session = session;
  }

  public boolean isSigninFirebase() {
    return getFirebaseAccount().isPresent();
  }


  /**
   *
   * @param firebaseToken
   * @param intervalSec seconds
   */
  public void signIn(FirebaseAccount firebaseAccount, int intervalSec) {
    setAttribute(FIREBASE_ACCOUNT, firebaseAccount);
    setMaxInactiveInterval(intervalSec);
  }

  public Optional<FirebaseAccount> getFirebaseAccount() {
    return getAttribute(FIREBASE_ACCOUNT).map(o -> (FirebaseAccount) o);
  }

  public Optional<Object> getAttribute(String name) {
    return Optional.ofNullable(session.getAttribute(name));
  }

  public String getSessionId() {
    return session.getId();
  }

  public HttpSession getSession() {
    return session;
  }

  /**
   * @see {@link HttpSession#invalidate()}
   *
   */
  public void invalidate() {
    session.invalidate();
  }

  /**
   * @see {@link HttpSession#setAttribute(String, Object)}
   *
   * @param name
   * @param value
   */
  public void setAttribute(String name, Object value) {
    session.setAttribute(name, value);
  }

  /**
   * @see {@link HttpSession#setMaxInactiveInterval(int)}
   * @param interval
   */
  public void setMaxInactiveInterval(int interval) {
    session.setMaxInactiveInterval(interval);
  }

}

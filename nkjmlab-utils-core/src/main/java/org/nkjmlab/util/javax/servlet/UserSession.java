package org.nkjmlab.util.javax.servlet;

import java.util.Optional;
import javax.servlet.http.HttpSession;

public class UserSession {

  private static final String USER_ID = "userId";

  public static UserSession wrap(HttpSession session) {
    return new UserSession(session);
  }

  private final HttpSession session;

  protected UserSession(HttpSession session) {
    this.session = session;
  }

  public Object getAttribute(String key) {
    return session.getAttribute(key);
  }

  public String getId() {
    return session.getId();
  }

  public HttpSession getSession() {
    return session;
  }

  public Optional<String> getUserId() {
    return Optional.ofNullable(getAttribute(USER_ID)).map(o -> o.toString());
  }

  public void invalidate() {
    session.invalidate();
  }

  public boolean isLogined() {
    return getAttribute(USER_ID) != null;
  }

  public void setAttribute(String key, Object value) {
    session.setAttribute(key, value);
  }

  public void setMaxInactiveInterval(int maxInterval) {
    session.setMaxInactiveInterval(maxInterval);
  }

  public void setUserId(String userId) {
    session.setAttribute(USER_ID, userId);
  }

}

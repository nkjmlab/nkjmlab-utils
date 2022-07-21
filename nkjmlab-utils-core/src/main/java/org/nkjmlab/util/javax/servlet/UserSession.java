package org.nkjmlab.util.javax.servlet;

import java.util.Optional;
import javax.servlet.http.HttpSession;

public class UserSession {

  private static final String USER_ID = "USER_ID";

  public static UserSession wrap(HttpSession session) {
    return new UserSession(session);
  }

  private final HttpSession session;

  protected UserSession(HttpSession session) {
    this.session = session;
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

  public Optional<String> getUserId() {
    return getAttribute(USER_ID).map(o -> o.toString());
  }

  /**
   * @see {@link HttpSession#invalidate()}
   *
   */
  public void invalidate() {
    session.invalidate();
  }

  public boolean isLogined() {
    return getAttribute(USER_ID).isPresent();
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

  public void setUserId(String userId) {
    session.setAttribute(USER_ID, userId);
  }

}

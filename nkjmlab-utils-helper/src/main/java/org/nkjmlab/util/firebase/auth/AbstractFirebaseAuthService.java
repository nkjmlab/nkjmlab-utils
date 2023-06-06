package org.nkjmlab.util.firebase.auth;

import java.util.Optional;

public abstract class AbstractFirebaseAuthService implements FirebaseAuthService {

  private final FirebaseSigninSessionsTable signinSessionsTable = new FirebaseSigninSessionsTable();



  @Override
  public Optional<FirebaseSigninSession> signin(String sessionId, String idToken) {
    return isAcceptableIdToken(idToken)
        .map(token -> Optional.of(signinSessionsTable.signin(sessionId, token)))
        .orElse(Optional.empty());
  }

  @Override
  public Optional<FirebaseSigninSession> selectBySessionId(String sessionId) {
    FirebaseSigninSession s = signinSessionsTable.selectByPrimaryKey(sessionId);
    return s != null ? Optional.of(s) : Optional.empty();
  }

  @Override
  public boolean isSignin(String sessionId) {
    return signinSessionsTable.exists(sessionId);
  }

  @Override
  public boolean signout(String sessionId) {
    signinSessionsTable.deleteByPrimaryKey(sessionId);
    return true;
  }


}

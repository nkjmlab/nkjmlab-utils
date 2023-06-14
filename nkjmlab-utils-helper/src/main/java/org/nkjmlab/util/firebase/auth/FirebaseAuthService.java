package org.nkjmlab.util.firebase.auth;

import java.util.Optional;

public interface FirebaseAuthService extends FirebaseAuthHandler {

  Optional<FirebaseSigninSession> selectBySessionId(String sessionId);

  Optional<FirebaseSigninSession> signin(String sessionId, String idToken);


  boolean isSignin(String sessionId);

  boolean signout(String sessionId);



}

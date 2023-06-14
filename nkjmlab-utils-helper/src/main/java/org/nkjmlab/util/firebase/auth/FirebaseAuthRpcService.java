package org.nkjmlab.util.firebase.auth;

public interface FirebaseAuthRpcService {

  boolean isSigninToFirebase();

  FirebaseSigninSession signinWithFirebase(String idToken);

  boolean signout();



}

package org.nkjmlab.util.firebase.auth;

public interface FirebaseAuthRpcService {

  boolean isSignin();

  FirebaseSigninSession signin(String idToken);

  boolean signout();



}

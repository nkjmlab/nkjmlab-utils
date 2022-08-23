package org.nkjmlab.util.firebase.auth;

import org.nkjmlab.util.firebase.auth.FirebaseAccountsTable.FirebaseAccount;

public interface FirebaseRpcServiceInterface {

  boolean isSigninToFirebase();

  FirebaseAccount signinWithFirebase(String idToken);

  boolean signoutFromFirebase();



}

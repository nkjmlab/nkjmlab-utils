package org.nkjmlab.util.firebase.auth;

import jakarta.servlet.http.HttpServletRequest;

public class BasicFirebaseAuthRpcService implements FirebaseAuthRpcService {

  public static class Factory {
    private final FirebaseAuthService firebaseAuthService;

    public Factory(FirebaseAuthService firebaseAuthService) {
      this.firebaseAuthService = firebaseAuthService;
    }

    public BasicFirebaseAuthRpcService create(HttpServletRequest request) {
      return new BasicFirebaseAuthRpcService(firebaseAuthService, request);
    }

  }

  private final FirebaseAuthService firebaseAuthService;
  private final HttpServletRequest request;

  private BasicFirebaseAuthRpcService(FirebaseAuthService firebaseAuthService,
      HttpServletRequest request) {
    this.firebaseAuthService = firebaseAuthService;
    this.request = request;
  }

  @Override
  public boolean isSigninToFirebase() {
    return firebaseAuthService.isSignin(request.getSession().getId());
  }

  @Override
  public FirebaseSigninSession signinWithFirebase(String idToken) {
    return firebaseAuthService.signin(request.getSession().getId(), idToken).orElse(null);
  }


  @Override
  public boolean signout() {
    return firebaseAuthService.signout(request.getSession().getId());
  }



}

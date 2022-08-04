package org.nkjmlab.util.firebase.auth;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.nkjmlab.util.firebase.auth.FirebaseAccountsTable.FirebaseAccount;
import com.google.firebase.auth.FirebaseToken;

public class FirebaseRpcService implements FirebaseRpcServiceInterface {
  private static final org.apache.logging.log4j.Logger log =
      org.apache.logging.log4j.LogManager.getLogger();

  private final BasicFirebaseService firebaseService;
  private final FirebaseAccountsTable firebaseAccountsTable;
  private final HttpServletRequest request;

  public FirebaseRpcService(BasicFirebaseService firebaseService,
      FirebaseAccountsTable firebaseAccountsTable, HttpServletRequest request) {
    this.firebaseService = firebaseService;
    this.firebaseAccountsTable = firebaseAccountsTable;
    this.request = request;
  }

  @Override
  public boolean isSigninToFirebase() {
    FirebaseSession session = FirebaseSession.wrap(request.getSession());
    return session.isSigninFirebase();
  }

  @Override
  public FirebaseAccount signinWithFirebase(String idToken) {
    FirebaseAccount account = verifyIdTokenAndGetFirebaseAccount(idToken).orElseThrow();
    FirebaseSession session = FirebaseSession.wrap(request.getSession());
    session.signIn(account, 10 * 60 * 60);
    return session.getFirebaseAccount().orElseThrow();
  }

  private Optional<FirebaseAccount> verifyIdTokenAndGetFirebaseAccount(String idToken) {
    FirebaseToken firebaseToken = firebaseService.verifyIdToken(idToken).orElseThrow();
    FirebaseAccount account =
        firebaseAccountsTable.readByEmail(firebaseToken.getEmail()).orElseThrow();
    return Optional.ofNullable(account);
  }

  @Override
  public boolean signoutFromFirebase() {
    request.getSession().invalidate();
    return true;
  }


}

package org.nkjmlab.util.firebase.auth;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;
import org.nkjmlab.util.java.function.Try;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

public class BasicFirebaseService {

  private final FirebaseAuth firebaseAuth;

  public BasicFirebaseService(File firebaseServiceAccountJson) {
    try (FileInputStream serviceAccount = new FileInputStream(firebaseServiceAccountJson)) {
      ServiceAccountCredentials credentials = ServiceAccountCredentials.fromStream(serviceAccount);
      FirebaseOptions options = FirebaseOptions.builder().setCredentials(credentials).build();
      FirebaseApp firebaseApp = FirebaseApp.initializeApp(options);
      this.firebaseAuth = FirebaseAuth.getInstance(firebaseApp);
    } catch (IOException e) {
      throw Try.rethrow(e);
    }

  }

  public Optional<FirebaseToken> verifyIdToken(String idToken) {
    if (idToken == null || idToken.length() == 0) {
      return Optional.empty();
    }
    try {
      FirebaseToken verifiedToken = firebaseAuth.verifyIdToken(idToken);
      return verifiedToken.isEmailVerified() ? Optional.of(verifiedToken) : Optional.empty();
    } catch (FirebaseAuthException e) {
      return Optional.empty();
    }
  }

}

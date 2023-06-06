package org.nkjmlab.util.firebase.auth;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.nkjmlab.util.java.function.Try;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

public class BasicFirebaseAuthHandler implements FirebaseAuthHandler {
  private final Set<String> acceptableEmails;
  private final FirebaseAuth firebaseAuth;

  public BasicFirebaseAuthHandler(Collection<String> acceptableEmails,
      ServiceAccountCredentials credentials) {
    FirebaseOptions options = FirebaseOptions.builder().setCredentials(credentials).build();
    FirebaseApp firebaseApp = FirebaseApp.initializeApp(options);
    this.firebaseAuth = FirebaseAuth.getInstance(firebaseApp);
    this.acceptableEmails = new HashSet<>(acceptableEmails);
  }

  @Override
  public Optional<FirebaseToken> isAcceptableIdToken(String idToken) {
    if (idToken == null || idToken.length() == 0) {
      return Optional.empty();
    }
    try {
      FirebaseToken verifiedToken = firebaseAuth.verifyIdToken(idToken);
      if (!verifiedToken.isEmailVerified()) {
        return Optional.empty();
      }
      String email = verifiedToken.getEmail();

      if (!acceptableEmails.contains(email)) {
        return Optional.empty();
      }

      return Optional.of(verifiedToken);
    } catch (FirebaseAuthException e) {
      return Optional.empty();
    }
  }

  public static BasicFirebaseAuthHandler create(Collection<String> acceptableEmails,
      File firebaseServiceAccountJson) {
    try (InputStream serviceAccount = new FileInputStream(firebaseServiceAccountJson)) {
      ServiceAccountCredentials credentials = ServiceAccountCredentials.fromStream(serviceAccount);
      return new BasicFirebaseAuthHandler(acceptableEmails, credentials);
    } catch (IOException e) {
      throw Try.rethrow(e);
    }
  }
}

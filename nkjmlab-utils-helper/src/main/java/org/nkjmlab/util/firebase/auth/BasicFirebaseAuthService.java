package org.nkjmlab.util.firebase.auth;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Optional;

import org.nkjmlab.sorm4j.internal.util.Try;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.firebase.auth.FirebaseToken;

public class BasicFirebaseAuthService extends AbstractFirebaseAuthService {

  private final BasicFirebaseAuthHandler authHandler;

  private BasicFirebaseAuthService(
      Collection<String> acceptableEmails, ServiceAccountCredentials credentials) {
    this.authHandler = new BasicFirebaseAuthHandler(acceptableEmails, credentials);
  }

  public static BasicFirebaseAuthService create(
      Collection<String> acceptableEmails, File firebaseServiceAccountJson) {
    try (InputStream serviceAccount = new FileInputStream(firebaseServiceAccountJson)) {
      ServiceAccountCredentials credentials = ServiceAccountCredentials.fromStream(serviceAccount);
      return new BasicFirebaseAuthService(acceptableEmails, credentials);
    } catch (IOException e) {
      throw Try.rethrow(e);
    }
  }

  @Override
  public Optional<FirebaseToken> isAcceptableIdToken(String idToken) {
    return authHandler.isAcceptableIdToken(idToken);
  }
}

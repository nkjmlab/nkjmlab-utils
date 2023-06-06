package org.nkjmlab.util.firebase.auth;

import java.util.Optional;
import com.google.firebase.auth.FirebaseToken;

public interface FirebaseAuthHandler {

  Optional<FirebaseToken> isAcceptableIdToken(String idToken);

}

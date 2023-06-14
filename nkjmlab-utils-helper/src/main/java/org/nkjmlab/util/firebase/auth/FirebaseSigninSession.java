package org.nkjmlab.util.firebase.auth;

import java.time.LocalDateTime;
import org.nkjmlab.sorm4j.annotation.OrmRecord;
import org.nkjmlab.sorm4j.util.table_def.annotation.Index;
import org.nkjmlab.sorm4j.util.table_def.annotation.NotNull;
import org.nkjmlab.sorm4j.util.table_def.annotation.PrimaryKey;
import com.google.firebase.auth.FirebaseToken;

@OrmRecord
public record FirebaseSigninSession(@PrimaryKey String sessionId, @Index String email,
    String username, @NotNull LocalDateTime loginAt) {

  public static FirebaseSigninSession of(String sessionId, FirebaseToken token) {
    return new FirebaseSigninSession(sessionId, token.getEmail(), token.getName(),
        LocalDateTime.now());

  }
}

package org.nkjmlab.util.firebase.auth;

import java.util.Optional;

import org.nkjmlab.sorm4j.Sorm;
import org.nkjmlab.sorm4j.util.h2.BasicH2Table;
import org.nkjmlab.sorm4j.util.h2.datasource.H2LocalDataSourceFactory;

import com.google.firebase.auth.FirebaseToken;

public class FirebaseSigninSessionsTable extends BasicH2Table<FirebaseSigninSession> {

  public FirebaseSigninSessionsTable(Sorm sorm) {
    super(sorm, FirebaseSigninSession.class);
  }

  public FirebaseSigninSessionsTable() {
    this(Sorm.create(H2LocalDataSourceFactory.createTemporalInMemoryDataSource()));
  }

  public Optional<FirebaseSigninSession> readByEmail(String email) {
    return Optional.ofNullable(selectOneAllEqual("email", email));
  }

  /**
   * Register sign in session. If session id is already registered. Token will be override.
   *
   * @param sessionId
   * @param token
   * @return
   */
  public FirebaseSigninSession signin(String sessionId, FirebaseToken token) {
    FirebaseSigninSession s = FirebaseSigninSession.of(sessionId, token);
    merge(s);
    return s;
  }
}

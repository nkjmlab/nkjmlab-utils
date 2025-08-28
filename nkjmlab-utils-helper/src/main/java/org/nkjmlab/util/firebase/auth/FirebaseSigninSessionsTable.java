package org.nkjmlab.util.firebase.auth;

import java.util.Optional;
import org.nkjmlab.sorm4j.Sorm;
import org.nkjmlab.sorm4j.extension.h2.datasource.H2DataSourceFactory;
import org.nkjmlab.sorm4j.extension.h2.orm.table.definition.H2DefinedTableBase;
import com.google.firebase.auth.FirebaseToken;

public class FirebaseSigninSessionsTable extends H2DefinedTableBase<FirebaseSigninSession> {

  public FirebaseSigninSessionsTable(Sorm sorm) {
    super(sorm, FirebaseSigninSession.class);
  }

  public FirebaseSigninSessionsTable() {
    this(Sorm.create(H2DataSourceFactory.createTemporalInMemoryDataSource()));
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

package org.nkjmlab.util.firebase.auth;

import java.util.Optional;
import org.nkjmlab.sorm4j.Sorm;
import org.nkjmlab.sorm4j.annotation.OrmRecord;
import org.nkjmlab.sorm4j.util.h2.BasicH2Table;
import org.nkjmlab.sorm4j.util.table_def.annotation.Index;
import org.nkjmlab.sorm4j.util.table_def.annotation.PrimaryKey;
import org.nkjmlab.util.firebase.auth.FirebaseAccountsTable.FirebaseAccount;

public class FirebaseAccountsTable extends BasicH2Table<FirebaseAccount> {


  public FirebaseAccountsTable(Sorm sorm) {
    super(sorm, FirebaseAccount.class);
  }

  public Optional<FirebaseAccount> readByEmail(String email) {
    return Optional.ofNullable(selectOneAllEqual("email", email));
  }

  @OrmRecord
  public static record FirebaseAccount(@PrimaryKey @Index String email, String userId) {
  }

}

package org.nkjmlab.util.firebase.auth;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.nkjmlab.sorm4j.Sorm;
import org.nkjmlab.util.java.lang.ResourceUtils;

class FirebaseAccountsTableTest {

  @Test
  void test() {
    Sorm sorm = Sorm.create("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;");
    FirebaseAccountsTable firebaseAccountsTable = new FirebaseAccountsTable(sorm);
    firebaseAccountsTable.dropTableIfExists().createTableIfNotExists().createIndexesIfNotExists();
    firebaseAccountsTable.insert(firebaseAccountsTable
        .readCsvWithHeader(ResourceUtils.getResourceAsFile("/conf/fb-accounts.csv.sample")));
    assertThat(firebaseAccountsTable.readByEmail("example@example.com").get().email())
        .isEqualTo("example@example.com");
  }

}

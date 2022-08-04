package org.nkjmlab.util.h2;

import static org.assertj.core.api.Assertions.*;
import java.io.File;
import org.junit.jupiter.api.Test;
import org.nkjmlab.sorm4j.util.h2.datasource.H2LocalDataSourceFactory;

class LocalDataSourceFactoryTest {

  @Test
  void testGetDatabasePath() {
    assertThat(H2LocalDataSourceFactory.builder(new File("~/db/dir"), "dbname", "", "").build()
        .getDatabasePath()).isEqualTo("C:/Users/nkjm/db/dir/dbname");
  }


  @Test
  void testGetServerUrl() {
    assertThat(H2LocalDataSourceFactory.builder(new File("C:/nkjm/db/dir"), "dbname", "", "").build()
        .getServerModeJdbcUrl()).isEqualTo("jdbc:h2:tcp://localhost/C:/nkjm/db/dir/dbname");
  }

}

package org.nkjmlab.util.h2;

import java.io.File;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import org.nkjmlab.sorm4j.util.h2.sql.H2CsvFunctions;

class H2StatementUtilsTest {


  @Test
  void testGetCsvWriteSql() {
    String ret = H2CsvFunctions.getCallCsvWriteSql(new File("hoge.csv"), "select * from test_table",
        StandardCharsets.UTF_8, ",");
    System.out.println(ret);
  }

  @Test
  void testGetCsvReadSql() {
    String ret =
        H2CsvFunctions.getCsvReadSql(new File("hoge.csv"), null, StandardCharsets.UTF_8, ",");
    System.out.println(ret);
  }

}

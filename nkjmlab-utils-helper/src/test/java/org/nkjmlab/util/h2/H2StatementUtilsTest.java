package org.nkjmlab.util.h2;

import java.io.File;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

class H2StatementUtilsTest {


  @Test
  void testGetCsvWriteSql() {
    String ret = H2StatementUtils.getCsvWriteSql(new File("hoge.csv"), "select * from test_table",
        StandardCharsets.UTF_8, ",");
    System.out.println(ret);
  }

  @Test
  void testGetCsvReadSql() {
    String ret =
        H2StatementUtils.getCsvReadSql(new File("hoge.csv"), null, StandardCharsets.UTF_8, ",");
    System.out.println(ret);
  }

}

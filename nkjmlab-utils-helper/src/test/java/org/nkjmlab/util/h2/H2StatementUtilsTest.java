package org.nkjmlab.util.h2;

import java.io.File;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

class H2StatementUtilsTest {


  @Test
  void testGetCsvWriteSql() {
    String ret = H2SqlUtils.getCsvWriteSql(new File("hoge.csv"), StandardCharsets.UTF_8, ",",
        "select * from test_table");
    System.out.println(ret);
  }

  @Test
  void testGetCsvReadSql() {
    String ret = H2SqlUtils.getCsvReadSql(new File("hoge.csv"), StandardCharsets.UTF_8, ",", null);
    System.out.println(ret);
  }

}

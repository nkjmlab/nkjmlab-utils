package org.nkjmlab.util.h2;

import static org.nkjmlab.sorm4j.sql.SelectSql.*;
import java.io.File;
import java.nio.charset.Charset;

public class H2StatementUtils {


  /**
   *
   * @param toFile
   * @param colmuns
   * @param charset
   * @param fieldSeparator
   * @return
   */
  public static String getCsvReadSql(File toFile, String[] colmuns, Charset charset,
      String fieldSeparator) {
    String csvOptions = "charset=" + charset.name() + " fieldSeparator=" + fieldSeparator;
    String csvStmt = "call csvread(" + literal(toFile.getAbsolutePath()) + ","
        + (colmuns == null ? "null" : String.join("|", colmuns)) + "," + literal(csvOptions) + ")";
    return csvStmt;
  }


  /**
   *
   * Example.
   *
   * <pre>
   * call csvwrite('E:\User\nkjmlab\nkjmlab-utils-helper\hoge.csv','select * from
   * test_table','charset=UTF-8 fieldSeparator=,')
   *
   * @see <a href= "http://www.h2database.com/html/functions.html#csvwrite">Functions - CSVWRITE</a>
   *
   * @param toFile
   * @param selectSql
   * @param charset
   * @param fieldSeparator
   * @return
   */
  public static String getCsvWriteSql(File toFile, String selectSql, Charset charset,
      String fieldSeparator) {
    String csvOptions = "charset=" + charset.name() + " fieldSeparator=" + fieldSeparator;
    String csvStmt = "call csvwrite(" + literal(toFile.getAbsolutePath()) + "," + literal(selectSql)
        + "," + literal(csvOptions) + ")";
    return csvStmt;

  }


}

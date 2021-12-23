package org.nkjmlab.util.h2;

import static java.lang.String.*;
import static org.nkjmlab.sorm4j.sql.SelectSql.*;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class H2SqlUtils {

  public static String getCsvReadSql(File fromFile, String fieldSeparator, List<String> colmuns) {
    return getCsvReadSql(fromFile, StandardCharsets.UTF_8, fieldSeparator, colmuns);
  }



  /**
   * @see <a href= "https://www.h2database.com/html/functions.html#csvread">Functions - CSVREAD</a>
   * @param fromFile
   * @param charset
   * @param fieldSeparator
   * @param colmuns null means the all rows.
   * @return
   */
  public static String getCsvReadSql(File fromFile, Charset charset, String fieldSeparator,
      List<String> colmuns) {
    String csvOptions = "charset=" + charset.name() + " fieldSeparator=" + fieldSeparator;
    String csvStmt = "csvread(" + literal(fromFile.getAbsolutePath()) + ","
        + (colmuns == null ? "null" : String.join("|", colmuns)) + "," + literal(csvOptions) + ")";
    return csvStmt;
  }


  /**
   *
   * Example.
   *
   * <pre>
   * csvwrite('C:\User\nkjmlab\nkjmlab-utils-helper\foo.csv','select * from
   * test_table','charset=UTF-8 fieldSeparator=,')
   *
   * @see <a href= "https://www.h2database.com/html/functions.html#csvwrite">Functions -
   *      CSVWRITE</a>
   *
   * @param toFile
   * @param charset
   * @param fieldSeparator
   * @param selectSql
   * @return
   */
  public static String getCsvWriteSql(File toFile, Charset charset, String fieldSeparator,
      String selectSql) {
    String csvOptions = "charset=" + charset.name() + " fieldSeparator=" + fieldSeparator;
    String csvStmt = "csvwrite(" + literal(toFile.getAbsolutePath()) + "," + literal(selectSql)
        + "," + literal(csvOptions) + ")";
    return csvStmt;
  }

  /**
   * @see #getCsvWriteSql(File, Charset, String, String)
   */
  public static String getCsvWriteSql(File toFile, String fieldSeparator, String selectSql) {
    return getCsvWriteSql(toFile, StandardCharsets.UTF_8, fieldSeparator, selectSql);
  }


  public static String getRenameTableSql(String fromTableName, String toTableName) {
    return "ALTER TABLE " + fromTableName + " RENAME TO " + toTableName;
  }


  public static String getCreateIndexOnSql(String tableName, String... columns) {
    return "create index if not exists " + "index_" + tableName + "_" + join("_", columns) + " on "
        + tableName + "(" + String.join(", ", columns) + ")";
  }


  public static String dropTablesCascadeIfExist(String tableName) {
    return "DROP TABLE IF EXISTS " + tableName + " CASCADE";
  }


}

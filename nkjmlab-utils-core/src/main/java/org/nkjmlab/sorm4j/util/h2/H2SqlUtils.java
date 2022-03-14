package org.nkjmlab.sorm4j.util.h2;

import static java.lang.String.*;

public class H2SqlUtils {


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

package org.nkjmlab.sorm4j.util.h2;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.nkjmlab.sorm4j.table.Table;
import org.nkjmlab.sorm4j.util.h2.sql.H2CsvFunctions;
import org.nkjmlab.sorm4j.util.h2.sql.H2CsvReadSql;

public interface H2Table<T> extends Table<T> {

  default String getReadCsvAndSelectSql(File csvFile, Charset charset, String separator) {
    return H2CsvReadSql.builder(csvFile).setCharset(charset).setFieldSeparator(separator)
        .setColumns(getTableMetaData().getNotAutoGeneratedColumns()).build()
        .getCsvReadAndSelectSql();
  }

  default List<T> readCsvWithHeader(File csvFile) {
    return readCsvWithHeader(csvFile, StandardCharsets.UTF_8, ",");
  }

  default List<T> readCsvWithHeader(File csvFile, Charset charset, String separator) {
    return getOrm().readList(getValueType(), getReadCsvAndSelectSql(csvFile, charset, separator));
  }

  default File writeCsv(File toFile) {
    getOrm().executeUpdate(H2CsvFunctions.getCallCsvWriteSql(toFile,
        "select * from " + getTableName(), StandardCharsets.UTF_8, ""));
    return toFile;
  }

}

package org.nkjmlab.util.orangesignal_csv;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.orangesignal.csv.Csv;
import com.orangesignal.csv.CsvConfig;
import com.orangesignal.csv.handlers.ColumnNameMapListHandler;
import com.orangesignal.csv.handlers.StringArrayListHandler;
import com.orangesignal.csv.manager.CsvEntityManager;

public class OrangeSignalCsvUtils {

  public static CsvConfig createDefaultCsvConfig() {
    CsvConfig cfg = new CsvConfig(',', '"', '"');
    cfg.setQuoteDisabled(false);// Enables quote characters, which are disabled by default.
    cfg.setEscapeDisabled(false); // Enables the escape character (to quote within quoted text),
                                  // which is disabled by default
    cfg.setBreakString("\n"); // Replaces the line feed in the item value with \n.
    cfg.setIgnoreEmptyLines(true);// Ignore blank lines.
    cfg.setIgnoreLeadingWhitespaces(true);// Removes whitespace before the item value.
    cfg.setIgnoreTrailingWhitespaces(true);// Removes whitespace after the item value.
    return cfg;
  }

  public static CsvConfig createDefaultTsvConfig() {
    CsvConfig config = createDefaultCsvConfig();
    config.setSeparator('\t');
    return config;
  }

  public static CsvEntityManager createDefaultCsvEntityManager() {
    return new CsvEntityManager(createDefaultCsvConfig());
  }

  public static List<String[]> readStringArrayList(CsvConfig csvConf, File file) {
    try {
      return readStringArrayList(csvConf, new FileInputStream(file));
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  public static List<String[]> readStringArrayList(CsvConfig csvConf, InputStream in) {
    return readStringArrayList(csvConf, new InputStreamReader(in));
  }

  public static List<String[]> readStringArrayList(CsvConfig csvConf, Reader reader) {
    try {
      return Csv.load(reader, csvConf, new StringArrayListHandler());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static List<Map<String, String>> readColumnNameMapList(Reader reader) {
    return readColumnNameMapList(createDefaultCsvConfig(), reader);
  }

  public static List<Map<String, String>> readColumnNameMapList(CsvConfig csvConf, File file) {
    try {
      return readColumnNameMapList(csvConf, new FileInputStream(file));
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  public static List<Map<String, String>> readColumnNameMapList(CsvConfig csvConf, InputStream in) {
    return readColumnNameMapList(csvConf, new InputStreamReader(in));
  }

  public static List<Map<String, String>> readColumnNameMapList(CsvConfig csvConf, Reader reader) {
    try {
      return Csv.load(reader, csvConf, new ColumnNameMapListHandler());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static List<Map<String, String>> readColumnNameMapList(File file) {
    try {
      return readColumnNameMapList(createDefaultCsvConfig(), new FileReader(file));
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> List<T> readList(Class<T> clazz, File file) {
    return readList(createDefaultCsvConfig(), clazz, file);
  }

  public static <T> List<T> readList(Class<T> clazz, InputStream in) {
    return readList(createDefaultCsvConfig(), clazz, in);
  }

  public static <T> List<T> readList(Class<T> clazz, Reader reader) {
    return readList(createDefaultCsvConfig(), clazz, reader);
  }

  public static <T> List<T> readList(CsvConfig csvConf, Class<T> clazz, InputStream in) {
    return readList(csvConf, clazz, new InputStreamReader(in));
  }

  public static <T> List<T> readList(CsvConfig csvConf, Class<T> clazz, File file) {
    try {
      return readList(csvConf, clazz, new FileInputStream(file));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> List<T> readList(CsvConfig csvConf, Class<T> clazz, Reader reader) {
    try {
      return new CsvEntityManager(csvConf).load(clazz).from(reader);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> List<T> readList(CsvConfig csvConf, Class<T> clazz, String resourceName) {
    try (InputStreamReader reader =
        new InputStreamReader(OrangeSignalCsvUtils.class.getResourceAsStream(resourceName))) {
      return readList(csvConf, clazz, reader);
    } catch (IOException e) {
      throw new IllegalArgumentException(e);
    }
  }

  public static List<Row> readRows(final File file, final String encoding, final CsvConfig cfg,
      int offset, int limit) {
    try {
      StringArrayListHandler handler = new StringArrayListHandler();
      handler.setOffset(offset);
      handler.setLimit(limit);
      List<Row> lines = Csv.load(file, encoding, cfg, handler).stream().map(line -> new Row(line))
          .collect(Collectors.toList());
      return lines;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static List<Row> readAllRows(final File file, final String encoding, final CsvConfig cfg) {
    return readRows(file, encoding, cfg, 0, Integer.MAX_VALUE);
  }

  public static List<Row> readRows(final InputStream in, final String encoding, final CsvConfig cfg,
      int offset, int limit) {
    try {
      StringArrayListHandler handler = new StringArrayListHandler();
      handler.setOffset(offset);
      handler.setLimit(limit);
      List<Row> lines = Csv.load(in, encoding, cfg, handler).stream().map(line -> new Row(line))
          .collect(Collectors.toList());
      return lines;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static List<Row> readAllRows(final InputStream in, final String encoding,
      final CsvConfig cfg) {
    return readRows(in, encoding, cfg, 0, Integer.MAX_VALUE);
  }

  public static List<Row> readAllRows(final File file, final String encoding) {
    return readRows(file, encoding, createDefaultCsvConfig(), 0, Integer.MAX_VALUE);
  }

  public static List<Row> readAllRows(final File file) {
    return readRows(file, StandardCharsets.UTF_8.toString(), createDefaultCsvConfig(), 0,
        Integer.MAX_VALUE);
  }

  public static List<Row> readAllRows(final File file, CsvConfig cfg) {
    return readRows(file, StandardCharsets.UTF_8.toString(), cfg, 0, Integer.MAX_VALUE);
  }

  public static final class Row {

    private final List<String> cells;

    public Row(String[] line) {
      this.cells = Arrays.asList(line);
    }

    public Row(List<String> line) {
      this.cells = line;
    }

    public String get(int index) {
      try {
        return cells.get(index).trim();
      } catch (Exception e) {
        return null;
      }
    }

    public Integer getAsInteger(int index) {
      try {
        return Integer.valueOf(get(index));
      } catch (Exception e) {
        return null;
      }
    }

    public Double getAsDouble(int index) {
      try {
        return Double.valueOf(get(index));
      } catch (Exception e) {
        return null;
      }
    }

    public boolean isHeadCellEquals(String str) {
      return get(0).equals(str);
    }

    public Boolean getAsBoolean(int index) {
      try {
        return Boolean.valueOf(get(index));
      } catch (Exception e) {
        return null;
      }
    }

    public Float getAsFloat(int index) {
      try {
        return Float.valueOf(get(index));
      } catch (Exception e) {
        return null;
      }
    }

    @Override
    public String toString() {
      return "Row [cells=" + cells + "]";
    }

  }

}

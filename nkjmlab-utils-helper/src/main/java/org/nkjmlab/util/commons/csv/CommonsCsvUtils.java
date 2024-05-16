package org.nkjmlab.util.commons.csv;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.nkjmlab.sorm4j.internal.util.Try;

public class CommonsCsvUtils {

  /**
   * @param format
   * @param writer
   * @param csvLines
   */
  public static void write(CSVFormat format, Appendable writer, List<String[]> csvLines) {
    try (CSVPrinter printer = format.print(writer)) {
      for (String[] line : csvLines) {
        printer.printRecord((Object[]) line);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Example
   *
   * <pre>
   * CsvUtils.write(CSVFormat.TDF, outFile, StandardCharsets.UTF_8, true, csvLines);
   *
   * @param format
   * @param outFile
   * @param charset
   * @param append
   * @param csvLines
   */
  public static void write(
      CSVFormat format, File outFile, Charset charset, boolean append, List<String[]> csvLines) {
    try (Writer writer = new FileWriter(outFile, charset, append)) {
      write(format, writer, csvLines);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static void readAndConsumeCsvRecord(
      CSVFormat format, File srcFile, Charset charset, Consumer<CSVRecord> consumer) {
    try (Reader in = new FileReader(srcFile, charset)) {
      Iterable<CSVRecord> records = format.parse(in);
      for (CSVRecord record : records) {
        consumer.accept(record);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * *
   *
   * <pre>
   * List<CSVRecord> list = CsvUtils.readCsvRecordList(CSVFormat.DEFAULT, new
   * File("2022-01-19.csv"), StandardCharsets.UTF_8);
   *
   * @param format
   * @param srcFile
   * @param charset
   * @return
   */
  public static List<CSVRecord> readCsvRecordList(CSVFormat format, File srcFile, Charset charset) {
    try (Reader in = new FileReader(srcFile, charset)) {
      return readCsvRecordList(format, in);
    } catch (IOException e) {
      throw Try.rethrow(e);
    }
  }

  /**
   * @param format
   * @param reader
   * @return
   */
  public static List<CSVRecord> readCsvRecordList(CSVFormat format, Reader reader) {
    try {
      Iterable<CSVRecord> records = format.parse(reader);
      return StreamSupport.stream(records.spliterator(), false).collect(Collectors.toList());
    } catch (IOException e) {
      throw Try.rethrow(e);
    }
  }
}

package org.nkjmlab.util.poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.nkjmlab.sorm4j.internal.util.ParameterizedStringFormatter;

public class BasicExcelSheet {

  private final File file;
  private final String sheetName;

  public BasicExcelSheet(File file, String sheetName) {
    super();
    this.file = file;
    this.sheetName = sheetName;
  }

  public static Builder builder(File file, String sheetName) {
    return new Builder(file, sheetName);
  }

  public static class Builder {
    private final File file;
    private final String sheetName;

    public Builder(File file, String sheetName) {
      this.file = file;
      this.sheetName = sheetName;
    }

    public BasicExcelSheet build() {
      return new BasicExcelSheet(file, sheetName);
    }
  }

  public <T> T procSheet(Function<Sheet, T> sheetFunction) {
    try (FileInputStream in = new FileInputStream(file);
        Workbook wb = WorkbookFactory.create(in)) {
      Sheet sheet = wb.getSheet(sheetName);
      return sheetFunction.apply(sheet);
    } catch (EncryptedDocumentException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * @param cellSeparatorAfterConverted
   * @param cellQuoteStringAfterConverted
   * @param nullStringAfterConverted
   * @return
   */
  public List<String> readAllRows(
      String cellSeparatorAfterConverted,
      String cellQuoteStringAfterConverted,
      String nullStringAfterConverted) {
    return readAllCells().stream()
        .map(
            row -> {
              List<String> convertedCells =
                  row.stream()
                      .map(
                          cell -> {
                            String val = toStringValue(cell);
                            return cellQuoteStringAfterConverted
                                + ((val == null || val.equals("null"))
                                    ? nullStringAfterConverted
                                    : val)
                                + cellQuoteStringAfterConverted;
                          })
                      .collect(Collectors.toList());
              return String.join(cellSeparatorAfterConverted, convertedCells);
            })
        .collect(Collectors.toList());
  }

  public List<List<Cell>> readAllCells() {
    return procSheet(
        sheet ->
            stream(sheet)
                .map(row -> stream(row).collect(Collectors.toList()))
                .collect(Collectors.toList()));
  }

  private static <T> Stream<T> stream(Iterable<T> iterable) {
    Stream<T> stream =
        StreamSupport.stream(
            Spliterators.spliteratorUnknownSize(iterable.iterator(), Spliterator.ORDERED), false);
    return stream;
  }

  public Map<String, Integer> readFirstRowAsHeader() {
    return procSheet(
        sheet -> {
          Row r = sheet.getRow(0);
          Map<String, Integer> columnNames = new HashMap<>();
          for (int i = 0; i < r.getLastCellNum(); i++) {
            columnNames.put(r.getCell(i).toString(), i);
          }
          return columnNames;
        });
  }

  public Cell readCell(int rowIndex, int columnIndex) {
    return procSheet(sheet -> readCell(sheet, rowIndex, columnIndex));
  }

  public static Cell readCell(Sheet sheet, int rowIndex, int columnIndex) {
    Row row = sheet.getRow(rowIndex);
    if (row != null) {
      Cell cell = row.getCell(columnIndex);
      return cell;
    }
    return null;
  }

  /**
   * Example of getting the value of a merged cell as a String
   *
   * @param cell
   * @return
   */
  public static String toMergedCellString(Cell cell) {
    Sheet sheet = cell.getSheet();
    int size = cell.getSheet().getNumMergedRegions();
    for (int i = 0; i < size; i++) {
      CellRangeAddress range = sheet.getMergedRegion(i);
      if (cell.getRowIndex() == range.getFirstRow()
          && cell.getColumnIndex() == range.getFirstColumn()) {
        Cell upplerLeftCell = readCell(sheet, range.getFirstRow(), range.getFirstColumn());
        return toStringValue(upplerLeftCell);
      }
    }
    return null;
  }

  public static String toStringValue(Cell cell) {
    switch (cell.getCellType()) {
      case STRING:
        return cell.getStringCellValue();
      case NUMERIC:
        if (DateUtil.isCellDateFormatted(cell)) {
          return toTimestamp(cell.getDateCellValue()).toString();
        }
        return Double.toString(cell.getNumericCellValue());
      case BOOLEAN:
        return Boolean.toString(cell.getBooleanCellValue());
      case FORMULA:
        return toStringFormulaValue(cell);
      case BLANK:
        return toMergedCellString(cell);
      default:
        return null;
    }
  }

  private static Timestamp toTimestamp(Date date) {
    return toTimestamp(toLocalDateTime(date));
  }

  private static Timestamp toTimestamp(LocalDateTime date) {
    return Timestamp.valueOf(date);
  }

  private static LocalDateTime toLocalDateTime(Date date) {
    return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
  }

  /**
   * Example of calculating a formula in a cell and getting it as a String
   *
   * @param cell
   * @return
   */
  public static String toStringFormulaValue(Cell cell) {
    Workbook book = cell.getSheet().getWorkbook();
    CreationHelper helper = book.getCreationHelper();
    FormulaEvaluator evaluator = helper.createFormulaEvaluator();
    CellValue value = evaluator.evaluate(cell);
    switch (value.getCellType()) {
      case STRING:
        return value.getStringValue();
      case NUMERIC:
        return Double.toString(value.getNumberValue());
      case BOOLEAN:
        return Boolean.toString(value.getBooleanValue());
      default:
        throw new IllegalArgumentException(
            ParameterizedStringFormatter.DEFAULT.format("{} is invalid", cell));
    }
  }

  /**
   * Example of getting the value cached in a cell as a String
   *
   * @param cell
   * @return
   */
  public static String toStringCachedFormulaValue(Cell cell) {
    switch (cell.getCachedFormulaResultType()) {
      case STRING:
        return cell.getStringCellValue();
      case NUMERIC:
        return Double.toString(cell.getNumericCellValue());
      case BOOLEAN:
        return Boolean.toString(cell.getBooleanCellValue());
      default:
        throw new IllegalArgumentException(
            ParameterizedStringFormatter.DEFAULT.format("{} is invalid", cell));
    }
  }
}

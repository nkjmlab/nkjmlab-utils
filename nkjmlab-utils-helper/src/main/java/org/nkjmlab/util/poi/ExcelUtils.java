package org.nkjmlab.util.poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.nkjmlab.sorm4j.internal.util.ParameterizedStringUtils;
import org.nkjmlab.sorm4j.internal.util.Try;
import org.nkjmlab.util.java.time.DateTimeUtils;

public class ExcelUtils {

  /**
   *
   * @param file
   * @param sheetName
   * @param cellSeparatorAfterConverted
   * @param cellQuoteStringAfterConverted
   * @param nullStringAfterConverted
   * @return
   */
  public static List<String> readAllRows(File file, String sheetName,
      String cellSeparatorAfterConverted, String cellQuoteStringAfterConverted,
      String nullStringAfterConverted) {
    try (FileInputStream in = new FileInputStream(file); Workbook wb = WorkbookFactory.create(in)) {
      Sheet sheet = wb.getSheet(sheetName);
      List<String> allRows = new ArrayList<>();
      sheet.forEach(row -> {
        List<String> convertedCells = new ArrayList<>();
        row.forEach(cell -> {
          String val = ExcelUtils.getStringValue(cell);
          convertedCells.add(cellQuoteStringAfterConverted
              + ((val == null || val.equals("null")) ? nullStringAfterConverted : val)
              + cellQuoteStringAfterConverted);
        });
        allRows.add(String.join(cellSeparatorAfterConverted, convertedCells));
      });
      return allRows;
    } catch (EncryptedDocumentException | IOException e) {
      throw Try.rethrow(e);
    }
  }

  public static Map<String, Integer> getColumnNames(Sheet sheet) {
    Row r = sheet.getRow(0);
    Map<String, Integer> columnNames = new HashMap<>();
    for (int i = 0; i < r.getLastCellNum(); i++) {
      columnNames.put(r.getCell(i).toString(), i);
    }
    return columnNames;
  }

  public static String getStringValue(Cell cell) {
    switch (cell.getCellType()) {
      case STRING:
        return cell.getStringCellValue();
      case NUMERIC:
        if (HSSFDateUtil.isCellDateFormatted(cell)) {
          return DateTimeUtils.toTimestamp(cell.getDateCellValue()).toString();
        }
        return Double.toString(cell.getNumericCellValue());
      case BOOLEAN:
        return Boolean.toString(cell.getBooleanCellValue());
      case FORMULA:
        // return cell.getCellFormula();
        return getStringFormulaValue(cell);
      case BLANK:
        return getStringRangeValue(cell);
      default:
        return null;
    }
  }

  /**
   * Example of calculating a formula in a cell and getting it as a String
   *
   * @param cell
   * @return
   */

  public static String getStringFormulaValue(Cell cell) {
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
            ParameterizedStringUtils.newString("{} is invalid", cell));
    }
  }

  /**
   * Example of getting the value cached in a cell as a String
   *
   * @param cell
   * @return
   */
  public static String getStringCachedFormulaValue(Cell cell) {
    switch (cell.getCachedFormulaResultType()) {
      case STRING:
        return cell.getStringCellValue();
      case NUMERIC:
        return Double.toString(cell.getNumericCellValue());
      case BOOLEAN:
        return Boolean.toString(cell.getBooleanCellValue());
      default:
        throw new IllegalArgumentException(
            ParameterizedStringUtils.newString("{} is invalid", cell));
    }
  }

  /**
   * Example of getting the value of a combined cell as a String
   *
   * @param cell
   * @return
   */
  public static String getStringRangeValue(Cell cell) {
    int rowIndex = cell.getRowIndex();
    int columnIndex = cell.getColumnIndex();
    Sheet sheet = cell.getSheet();
    int size = sheet.getNumMergedRegions();
    for (int i = 0; i < size; i++) {
      CellRangeAddress range = sheet.getMergedRegion(i);
      if (range.isInRange(rowIndex, columnIndex)) {
        Cell firstCell = getCell(sheet, range.getFirstRow(), range.getFirstColumn()); // 左上のセルを取得
        return getStringValue(firstCell);
      }
    }
    return null;
  }

  public static Cell getCell(Sheet sheet, int rowIndex, int columnIndex) {
    Row row = sheet.getRow(rowIndex);
    if (row != null) {
      Cell cell = row.getCell(columnIndex);
      return cell;
    }
    return null;
  }

}

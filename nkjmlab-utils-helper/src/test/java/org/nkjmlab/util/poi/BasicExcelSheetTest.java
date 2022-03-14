package org.nkjmlab.util.poi;

import static org.assertj.core.api.Assertions.*;
import java.net.URISyntaxException;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.junit.jupiter.api.Test;
import org.nkjmlab.util.java.lang.ResourceUtils;

class BasicExcelSheetTest {
  private static BasicExcelSheet excelSheet =
      BasicExcelSheet.builder(ResourceUtils.getResourceAsFile("/test.xlsx"), "Sheet1").build();

  @Test
  void testGetCell() throws URISyntaxException {
    List<List<Cell>> cells = excelSheet.readAllCells();
    assertThat(cells.get(0).get(0).toString()).isEqualTo("comment");
    assertThat(cells.get(1).get(1).toString()).isEqualTo("is");
    assertThat(cells.get(6).get(0).toString()).isEqualTo("merged cell1");
    assertThat(cells.get(6).get(1).toString()).isEqualTo("");
  }

}

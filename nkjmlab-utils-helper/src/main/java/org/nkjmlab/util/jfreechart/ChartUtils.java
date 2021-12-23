package org.nkjmlab.util.jfreechart;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;

public class ChartUtils {

  public static void saveChartAsPng(File file, JFreeChart chart, int width, int height) {
    try {
      org.jfree.chart.ChartUtils.saveChartAsPNG(file, chart, width, height);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static byte[] encodeAsPng(JFreeChart chart, int width, int height) {
    try {
      return org.jfree.chart.ChartUtils.encodeAsPNG(createBufferedImage(chart, width, height));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static ChartFrame viewInFrame(JFreeChart chart, String title, int x, int y, int width,
      int height) {
    ChartFrame frame = new ChartFrame(title, chart);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setBounds(x, y, width, height);
    frame.setVisible(true);
    return frame;
  }

  public void setLegacyTheme() {
    ChartFactory.setChartTheme(StandardChartTheme.createLegacyTheme());
  }

  public void setDefaultTheme() {
    ChartFactory.setChartTheme(StandardChartTheme.createJFreeTheme());
  }

  public static BufferedImage createBufferedImage(JFreeChart chart, int width, int height) {
    return chart.createBufferedImage(width, height);
  }

}

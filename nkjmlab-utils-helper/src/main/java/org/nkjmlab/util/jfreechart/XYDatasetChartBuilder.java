package org.nkjmlab.util.jfreechart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.Title;
import org.jfree.data.Range;
import org.jfree.data.xy.DefaultXYDataset;

public class XYDatasetChartBuilder extends ChartBuilder {

  private Map<Integer, DefaultXYDataset> datasets = new HashMap<>();
  private Map<Integer, XYItemRenderer> datasetRenderers = new HashMap<>();

  private String xAxisLabel = "x";
  private String yAxisLabel = "y";
  private ChartType type = ChartType.SCATTER_PLOT;

  private Range xRange;
  private Range yRange;

  private boolean legend = true;
  private List<Title> subtitles = new ArrayList<>();


  public static void main(String[] args) {
    XYDatasetChartBuilder chartBuilder = new XYDatasetChartBuilder();
    chartBuilder.addSeries("hoge", new int[][] {{1, 2, 10}, {4, 5, 6}});
    chartBuilder.setType(ChartType.XY_LINE_CHART);
    JFreeChart chart = chartBuilder.build();
    ChartUtils.viewInFrame(chart, "hoge", 0, 0, 500, 500);
  }

  public enum ChartType {
    SCATTER_PLOT, TIME_SERIES_CHART, XY_AREA_CHART, XY_LINE_CHART, XY_STEP_AREA_CHART, XY_STEP_CHART
  }

  public void addRenderer(XYItemRenderer renderer) {
    this.datasetRenderers.put(0, renderer);
  }

  public void addRenderer(int datasetId, XYItemRenderer renderer) {
    this.datasetRenderers.put(datasetId, renderer);
  }


  public void addSeries(Comparable<?> seriesKey, int[][] data) {
    addSeries(0, seriesKey, data);
  }

  public void addSeries(int datasetId, Comparable<?> seriesKey, int[][] data) {
    datasets.computeIfAbsent(datasetId, key -> new DefaultXYDataset());
    double[][] tmp = new double[data.length][data[0].length];

    for (int i = 0; i < data.length; i++) {
      for (int j = 0; j < data[0].length; j++) {
        tmp[i][j] = data[i][j];
      }
    }
    datasets.get(datasetId).addSeries(seriesKey, tmp);
  }

  public void addSeries(Comparable<?> seriesKey, double[][] data) {
    addSeries(0, seriesKey, data);
  }

  public void addSeries(int datasetId, Comparable<?> seriesKey, double[][] data) {
    datasets.computeIfAbsent(datasetId, key -> new DefaultXYDataset());
    datasets.get(datasetId).addSeries(seriesKey, data);
  }

  public void setLabels(String graphTitle, String xAxisLabel, String yAxisLabel,
      Title[] subtitles) {
    this.graphTitle = graphTitle;
    this.xAxisLabel = xAxisLabel;
    this.yAxisLabel = yAxisLabel;
    this.subtitles.addAll(Arrays.asList(subtitles));
  }

  public void setType(ChartType type) {
    this.type = type;
  }

  public JFreeChart build() {
    JFreeChart chart = chreateChart(type, datasets.get(0));

    List<Integer> keys = new ArrayList<>(datasets.keySet());
    Collections.sort(keys);
    XYPlot plot = chart.getXYPlot();

    for (int i = 0; i < keys.size(); i++) {
      if (i == 0) {
        continue;
      }
      plot.setDataset(i, datasets.get(i));
      plot.setRenderer(i, datasetRenderers.getOrDefault(i, new XYLineAndShapeRenderer()));
    }

    return chart;
  }

  private JFreeChart chreateChart(ChartType type, DefaultXYDataset dataset) {
    ChartFactory.setChartTheme(StandardChartTheme.createLegacyTheme());
    JFreeChart chart;
    switch (type) {
      case SCATTER_PLOT:
        chart = ChartFactory.createScatterPlot(graphTitle, xAxisLabel, yAxisLabel, dataset,
            PlotOrientation.VERTICAL, legend, true, false);
        break;
      case TIME_SERIES_CHART:
        chart = ChartFactory.createTimeSeriesChart(graphTitle, xAxisLabel, yAxisLabel, dataset,
            legend, true, false);
        break;
      case XY_AREA_CHART:
        chart = ChartFactory.createXYAreaChart(graphTitle, xAxisLabel, yAxisLabel, dataset,
            PlotOrientation.VERTICAL, legend, true, false);
        break;
      case XY_LINE_CHART:
        chart = ChartFactory.createXYLineChart(graphTitle, xAxisLabel, yAxisLabel, dataset,
            PlotOrientation.VERTICAL, legend, true, false);
        break;
      case XY_STEP_AREA_CHART:
        chart = ChartFactory.createXYStepAreaChart(graphTitle, xAxisLabel, yAxisLabel, dataset,
            PlotOrientation.VERTICAL, legend, true, false);
        break;
      case XY_STEP_CHART:
        chart = ChartFactory.createXYStepChart(graphTitle, xAxisLabel, yAxisLabel, dataset,
            PlotOrientation.VERTICAL, legend, true, false);
        break;
      default:
        chart = ChartFactory.createScatterPlot(graphTitle, xAxisLabel, yAxisLabel, dataset,
            PlotOrientation.VERTICAL, legend, true, false);
    }
    if (xRange != null) {
      chart.getXYPlot().getDomainAxis().setRange(xRange);
    }
    if (yRange != null) {
      chart.getXYPlot().getRangeAxis().setRange(yRange);
    }

    if (!legend) {
      chart.removeLegend();
    }

    chart.setSubtitles(subtitles);

    return chart;

  }

  public void removeLegend() {
    this.legend = false;
  }

  public void setXRange(Range xRange) {
    this.xRange = xRange;
  }

  public void setYRange(Range yRange) {
    this.yRange = yRange;

  }

}

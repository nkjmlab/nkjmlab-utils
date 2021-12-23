package org.nkjmlab.util.jfreechart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class CategoryDatasetChartBuilder extends ChartBuilder {

  private DefaultCategoryDataset dataset = new DefaultCategoryDataset();

  private String categoryAxisLabel = "x";
  private String valueAxisLabel = "y";
  private ChartType type = ChartType.LINE_CHART;

  public enum ChartType {
    LINE_CHART, AREA_CHART, BAR_CAHRT, BAR_CHART_3D, STACKED_AREA_CHART, STACKED_BAR_CHART
  }

  public void addValue(Number value, Comparable<?> rowKey, Comparable<?> columnKey) {
    dataset.addValue(value, rowKey, columnKey);
  }

  public void setLabel(String graphTitle, String categoryAxisLabel, String valueAxisLabel) {
    this.graphTitle = graphTitle;
    this.categoryAxisLabel = categoryAxisLabel;
    this.valueAxisLabel = valueAxisLabel;
  }

  public void setType(ChartType type) {
    this.type = type;
  }

  public JFreeChart build() {
    return createChart(type);
  }

  public JFreeChart createChart(ChartType type) {
    ChartFactory.setChartTheme(StandardChartTheme.createLegacyTheme());
    switch (type) {
      case LINE_CHART:
        return ChartFactory.createLineChart(graphTitle, categoryAxisLabel, valueAxisLabel, dataset,
            PlotOrientation.VERTICAL, true, false, false);
      case AREA_CHART:
        return ChartFactory.createAreaChart(graphTitle, categoryAxisLabel, valueAxisLabel, dataset,
            PlotOrientation.VERTICAL, true, false, false);
      case BAR_CAHRT:
        return ChartFactory.createBarChart(graphTitle, categoryAxisLabel, valueAxisLabel, dataset,
            PlotOrientation.VERTICAL, true, false, false);
      case STACKED_AREA_CHART:
        return ChartFactory.createStackedAreaChart(graphTitle, categoryAxisLabel, valueAxisLabel,
            dataset, PlotOrientation.VERTICAL, true, false, false);
      case STACKED_BAR_CHART:
        return ChartFactory.createStackedBarChart(graphTitle, categoryAxisLabel, valueAxisLabel,
            dataset, PlotOrientation.VERTICAL, true, false, false);
      default:
        return ChartFactory.createLineChart(graphTitle, categoryAxisLabel, valueAxisLabel, dataset,
            PlotOrientation.VERTICAL, true, false, false);
    }

  }

}

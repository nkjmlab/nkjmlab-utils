package org.nkjmlab.util.jfreechart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Day;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

public class TimeSeriesChartBuilder extends ChartBuilder {

  private TimeSeriesCollection dataset = new TimeSeriesCollection();
  private String xAxisLabel = "time";
  private String yAxisLabel = "value";

  private ChartType type = ChartType.TIME_SERIES_CHART;

  public enum ChartType {
    TIME_SERIES_CHART
  }

  public static void main(String[] args) {
    TimeSeriesChartBuilder s1 = new TimeSeriesChartBuilder();
    s1.add("Tweets", new Day(1, 6, 2016), 1);
    s1.add("Tweets", new Day(2, 6, 2016), 3);
    s1.add("Tweets", new Day(3, 6, 2016), 6);
    ChartUtils.viewInFrame(s1.build(), "Tweets", 0, 0, 640, 480);

  }

  public void addSeries(TimeSeries series) {
    dataset.addSeries(series);
  }

  public void add(Comparable<?> key, RegularTimePeriod period, Number value) {
    add(key, period, value.doubleValue());
  }

  public void add(Comparable<?> key, RegularTimePeriod period, double value) {
    if (dataset.getSeries(key) == null) {
      dataset.addSeries(new TimeSeries(key));
    }
    dataset.getSeries(key).add(period, value);
  }

  public JFreeChart build() {
    return createChart(type);
  }

  public JFreeChart createChart(ChartType type) {
    switch (type) {
      case TIME_SERIES_CHART:
        return ChartFactory.createTimeSeriesChart(graphTitle, xAxisLabel, yAxisLabel, dataset, true,
            true, false);
      default:
        return ChartFactory.createTimeSeriesChart(graphTitle, xAxisLabel, yAxisLabel, dataset, true,
            true, false);
    }

  }

}

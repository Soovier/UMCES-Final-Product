package org.umces.umces;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.markers.SeriesMarkers;

public class CoordinateGraph {
	public static ArrayList<Double> xData = new ArrayList<>(Arrays.asList(0.0));
	public static ArrayList<Double> yData = new ArrayList<>(Arrays.asList(0.0));

	public void main() {
		// Create Chart
		XYChart chart = new XYChartBuilder().width(600).height(400).title("K-Folds Graph").xAxisTitle("K-Folds")
				.yAxisTitle("False Discovery Rate").build();

		// Customize Chart
		chart.getStyler().setLegendVisible(true);
		chart.getStyler().setLegendPadding(10);
		chart.getStyler().setMarkerSize(15);

		chart.getStyler().setToolTipsEnabled(true);

		// Add series
		XYSeries series = chart.addSeries("Data Points", xData, yData);
		series.setMarker(SeriesMarkers.CIRCLE);

		// Show the chart
		new SwingWrapper<>(chart).displayChart();

		// Save the chart as a PNG file
		try {
			BitmapEncoder.saveBitmap(chart, "chart_output.png", BitmapFormat.PNG);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
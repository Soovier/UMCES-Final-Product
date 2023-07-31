package org.umces.umces;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.markers.SeriesMarkers;

public class CoordinateGraph {
	public static ArrayList<Double> kxData = new ArrayList<>(Arrays.asList(0.0));
	public static ArrayList<Double> kyData = new ArrayList<>(Arrays.asList(0.0));

	public static ArrayList<Double> pxData = new ArrayList<>(Arrays.asList(0.0));
	public static ArrayList<Double> pyData = new ArrayList<>(Arrays.asList(0.0));

	public static ArrayList<Double> cxData = new ArrayList<>(Arrays.asList(0.0));
	public static ArrayList<Double> cyData = new ArrayList<>(Arrays.asList(0.0));

	public static ArrayList<Double> oxData = new ArrayList<>(Arrays.asList(0.0));
	public static ArrayList<Double> oyData = new ArrayList<>(Arrays.asList(0.0));

	public static ArrayList<Double> fxData = new ArrayList<>(Arrays.asList(0.0));
	public static ArrayList<Double> fyData = new ArrayList<>(Arrays.asList(0.0));

	public static ArrayList<Double> gxData = new ArrayList<>(Arrays.asList(0.0));
	public static ArrayList<Double> gyData = new ArrayList<>(Arrays.asList(0.0));

	public static ArrayList<Double> sxData = new ArrayList<>(Arrays.asList(0.0));
	public static ArrayList<Double> syData = new ArrayList<>(Arrays.asList(0.0));

	public static Map<String, List<Double>> data = new HashMap<>();

	{
		data.put("kxData", kxData);
		data.put("kyData", kyData);

		data.put("pxData", pxData);
		data.put("pyData", pyData);

		data.put("cxData", cxData);
		data.put("cyData", cyData);

		data.put("oxData", oxData);
		data.put("oyData", oyData);

		data.put("fxData", fxData);
		data.put("fyData", fyData);

		data.put("gxData", gxData);
		data.put("gyData", gyData);

		data.put("sxData", sxData);
		data.put("syData", syData);
	}

	public static void main(String[] args) {
		CoordinateGraph test = new CoordinateGraph();
		test.main();
	}

	public void main() {
		System.out.println("RAN");

		for (Map.Entry<String, List<Double>> e : data.entrySet()) {
			System.out.println(e.getKey() + " " + e.getValue());
		}
		System.out.println("Done");
		// Create Chart
		XYChart chart = new XYChartBuilder().width(600).height(400).title("K-Folds Graph").xAxisTitle("K-Folds")
				.yAxisTitle("False Discovery Rate").build();

		// Customize Chart
		chart.getStyler().setLegendVisible(true);
		chart.getStyler().setLegendPadding(10);
		chart.getStyler().setMarkerSize(15);

		chart.getStyler().setToolTipsEnabled(true);

		// Add series
		XYSeries series = chart.addSeries("Kingdom Data", kxData, kyData);
		series.setMarker(SeriesMarkers.CIRCLE);

		XYSeries series2 = chart.addSeries("Phylum Data", pxData, pyData);
		series2.setMarker(SeriesMarkers.CIRCLE);
		series2.setFillColor(Color.red);

		XYSeries series3 = chart.addSeries("Class Data", cxData, cyData);
		series3.setMarker(SeriesMarkers.CIRCLE);
		series3.setFillColor(Color.green);

		XYSeries series4 = chart.addSeries("Order Data", oxData, oyData);
		series4.setMarker(SeriesMarkers.CIRCLE);
		series4.setFillColor(Color.black);

		XYSeries series5 = chart.addSeries("Family Data", fxData, fyData);
		series5.setMarker(SeriesMarkers.CIRCLE);
		series5.setFillColor(Color.blue);

		XYSeries series6 = chart.addSeries("Genus Data", gxData, gyData);
		series6.setMarker(SeriesMarkers.CIRCLE);
		series6.setFillColor(Color.CYAN);

		XYSeries series7 = chart.addSeries("Species Data", sxData, syData);
		series7.setMarker(SeriesMarkers.CIRCLE);
		series7.setFillColor(Color.GRAY);

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
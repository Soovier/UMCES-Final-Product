package MainJavaClasses;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.markers.SeriesMarkers;

public class XChartExample {

	public static void main(String[] args) throws IOException {
		// Create Chart
		XYChart chart = new XYChartBuilder().width(800).height(600).title("Line Chart Example").xAxisTitle("X")
				.yAxisTitle("Y").build();

		// Customize Chart
		chart.getStyler().setLegendVisible(true);
		chart.getStyler().setPlotGridLinesVisible(false);

		// Add data series
		HashMap<String, double[]> dataMap = new HashMap<>();
		dataMap.put("gxData", new double[] { 2.0, 4.0, 8.0, 16.0 });
		dataMap.put("kxData", new double[] { 2.0, 4.0, 8.0, 16.0 });
		dataMap.put("oyData",
				new double[] { 0.028457333385026946, 0.036022832459117554, 0.04838709677419355, 0.01195219123505976 });
		dataMap.put("pxData", new double[] { 2.0, 4.0, 8.0, 16.0 });
		dataMap.put("fyData",
				new double[] { 0.08045442208522353, 0.09996914532551682, 0.12442396313364056, 0.04750229849831444 });
		dataMap.put("gyData",
				new double[] { 0.24605764002175096, 0.30590961761297797, 0.39846272098385854, 0.21939245167229213 });
		dataMap.put("cxData", new double[] { 2.0, 4.0, 8.0, 16.0 });
		dataMap.put("fxData", new double[] { 2.0, 4.0, 8.0, 16.0 });
		dataMap.put("oxData", new double[] { 2.0, 4.0, 8.0, 16.0 });
		dataMap.put("syData",
				new double[] { 0.5136198547215496, 0.566237506352702, 0.7176586325627152, 0.5364751452550033 });
		dataMap.put("sxData", new double[] { 2.0, 4.0, 8.0, 16.0 });
		dataMap.put("kyData", new double[] { 0.0 });
		dataMap.put("cyData", new double[] { 6.590680003101497E-4, 6.942301758716445E-4, 7.680491551459293E-4, 0.0 });
		dataMap.put("pyData", new double[] { 7.753440589261485E-5, 3.0852294639413806E-4, 6.144393241167435E-4, 0.0 });

		for (Map.Entry<String, double[]> entry : dataMap.entrySet()) {
			String seriesName = entry.getKey();
			double[] xData = dataMap.get(seriesName);
			double[] yData = entry.getValue();

			if (xData.length != yData.length) {
				throw new IllegalArgumentException("X and Y-Axis sizes are not the same for series: " + seriesName);
			}

			XYSeries series = chart.addSeries(seriesName, xData, yData);
			series.setMarker(SeriesMarkers.NONE);
		}

		// Show the chart
//		new SwingWrapper<>(chart).displayChart();

		// Save the chart as an image
		BitmapEncoder.saveBitmap(chart, "./LineChartExample", BitmapFormat.PNG);
	}
}
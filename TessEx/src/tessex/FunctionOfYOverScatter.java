package tessex;
import java.awt.Dimension;
import java.util.*;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.function.Function2D;
import org.jfree.data.function.PolynomialFunction2D;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

public class FunctionOfYOverScatter
{
    	
    public FunctionOfYOverScatter(double[] a)
    {
        XYPlot plot = new XYPlot();

        XYDataset scatterPlotDataset = getScatterPlotDataset();
        plot.setDataset(0, scatterPlotDataset);
        plot.setRenderer(0, new XYLineAndShapeRenderer(false, true));
        plot.setDomainAxis(0, new NumberAxis("Y - Axis"));
        plot.setRangeAxis(0, new NumberAxis("X - Axis"));
        plot.mapDatasetToDomainAxis(0, 0);
        plot.mapDatasetToRangeAxis(0, 0);
        double minY = -2.0;
        double maxY = 2.0;
        XYDataset functionDataset = 
            getFunctionDataset(a, minY, maxY);
        plot.setDataset(1, functionDataset);
        plot.setRenderer(1, new XYLineAndShapeRenderer(true, false));

        JFreeChart chart = new JFreeChart("Function of Y over X",
            JFreeChart.DEFAULT_TITLE_FONT, plot, true);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(500, 270));
        ApplicationFrame frame = new ApplicationFrame("Example");
        frame.setContentPane(chartPanel);
        frame.pack();
        frame.setVisible(true);
    }

    private XYDataset getFunctionDataset(
        double[] a,
        double minY, double maxY)
    {
        double[] a1 = a;
        Function2D p = new PolynomialFunction2D(a1);
        XYSeries series = sampleFunctionOverY(p, minY, maxY, 100, "Function");
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        return dataset;
    }

    public static XYSeries sampleFunctionOverY(Function2D f, double start,
        double end, int samples, Comparable<?> seriesKey)
    {
        XYSeries series = new XYSeries(seriesKey, false);
        double step = (end - start) / (samples - 1);
        for (int i = 0; i < samples; i++)
        {
            double y = start + step * i;
            series.add(f.getValue(y), y);
        }
        return series;
    }


    private XYDataset getScatterPlotDataset()
    {
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries data = new XYSeries("Scatterplot");
        data.add(3.0, 2.0);
        data.add(1.7, 1.0);
        data.add(2.0, -1.0);
        data.add(1.0, 1.8);
        dataset.addSeries(data);
        return dataset;
    }


}
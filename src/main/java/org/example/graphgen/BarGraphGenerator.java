package org.example.graphgen;

import org.example.data.FundData;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DefaultDrawingSupplier;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.text.DecimalFormat;

import static org.jfree.chart.plot.DefaultDrawingSupplier.createStandardSeriesShapes;

@Component
public class BarGraphGenerator implements GraphGenerator<BarGraphInputData> {

    final Paint[] paintSequence;
    final MyChartTheme theme;

    public BarGraphGenerator(Paint[] paintSequence, MyChartTheme theme) {
        this.paintSequence = paintSequence;
        this.theme = theme;
    }

    private CategoryDataset createDataset(FundData fundData) {
        DefaultCategoryDataset valueDataset = new DefaultCategoryDataset();
        valueDataset.addValue(fundData.getSharesWeighting(), "shares", "");
        valueDataset.addValue(fundData.getBondsWeighting(), "bonds", "");
        valueDataset.addValue(fundData.getCommoditiesWeighting(), "commodities", "");
        valueDataset.addValue(fundData.getCashWeighting(), "cash", "");
        return valueDataset;
    }

    @Override
    public JFreeChart generateGraph(BarGraphInputData inputData) {
        CategoryDataset dataset = createDataset(inputData.getFundData());
        JFreeChart chart = ChartFactory.createBarChart(inputData.getChartTitle(), "", "", dataset);
        this.theme.getChartTheme()
                .setDrawingSupplier(new DefaultDrawingSupplier(paintSequence,
                        new Color[]{Color.white},
                        new Color[]{Color.lightGray},
                        new Stroke[]{
                                new BasicStroke(1.0f, BasicStroke.CAP_SQUARE,
                                        BasicStroke.JOIN_BEVEL)},
                        new Stroke[]{new BasicStroke(1.0f, BasicStroke.CAP_SQUARE,
                                BasicStroke.JOIN_BEVEL)},
                        createStandardSeriesShapes()
                ));

        chart.getCategoryPlot().setRangeZeroBaselinePaint(Color.black);
        chart.getCategoryPlot().setRangeZeroBaselineVisible(true);
        chart.getCategoryPlot().setRangeZeroBaselineStroke(new BasicStroke(1.9f));
        theme.apply(chart);
        CategoryPlot plot = (CategoryPlot) chart.getPlot();

        NumberAxis axis = (NumberAxis) plot.getRangeAxis();
        DecimalFormat decimalFormat = new DecimalFormat("#%");
        axis.setNumberFormatOverride(decimalFormat);
        return chart;
    }
}

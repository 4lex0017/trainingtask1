package org.example.graphgen;

import org.example.data.FundData;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.DefaultDrawingSupplier;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.List;

@Component
public class LineGraphGenerator implements GraphGenerator<LineGraphInputData> {

    final MyChartTheme theme;

    @Autowired
    public LineGraphGenerator(MyChartTheme theme) {
        this.theme = theme;
    }

    private CategoryDataset createDataset(List<FundData> fundData, int fundId) {
        DefaultCategoryDataset valueDataset = new DefaultCategoryDataset();
        for (FundData fundDatum : fundData) {
            if (fundDatum.getFundId() == fundId) {
                valueDataset.addValue(fundDatum.getPrice(), fundDatum.getFundName(), fundDatum.getDate());
            }
        }
        return valueDataset;
    }

    @Override
    public JFreeChart generateGraph(LineGraphInputData inputData) {
        String chartTitle = inputData.getChartTitle();
        CategoryDataset dataset = createDataset(inputData.getFundData(), inputData.getFund());
        JFreeChart lineChart = ChartFactory.createLineChart(chartTitle, "date", "price in EUR", dataset);
        lineChart.getCategoryPlot()
                .getDomainAxis()
                .setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
//        lineChart.getCategoryPlot().getRangeAxis().setRange(0, 119);
        theme.getChartTheme().setDrawingSupplier(new DefaultDrawingSupplier());
        theme.apply(lineChart);
        lineChart.getCategoryPlot().setRangeZeroBaselinePaint(Color.black);
        lineChart.getCategoryPlot().setRangeZeroBaselineVisible(true);
        lineChart.getCategoryPlot().setRangeZeroBaselineStroke(new BasicStroke(
                1.5f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER,
                1.0f, new float[]{0.5f, 3.5f}, 0.0f
        ));
        lineChart.getCategoryPlot().setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
        return lineChart;
    }
}

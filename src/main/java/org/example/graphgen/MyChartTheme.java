package org.example.graphgen;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
public class MyChartTheme {

    private final StandardChartTheme chartTheme;

    @Autowired
    MyChartTheme() {
        this.chartTheme = (StandardChartTheme) StandardChartTheme.createJFreeTheme();
        chartTheme.setPlotBackgroundPaint(Color.WHITE);
        chartTheme.setChartBackgroundPaint(Color.WHITE);
        chartTheme.setRangeGridlinePaint(Color.BLACK);
        chartTheme.setPlotOutlinePaint(Color.WHITE);
        chartTheme.setBaselinePaint(Color.BLACK);
        chartTheme.setCrosshairPaint(Color.GREEN);
        chartTheme.setBarPainter(new StandardBarPainter());

    }

    public StandardChartTheme getChartTheme() {
        return chartTheme;
    }

    public void apply(JFreeChart chart) {
        chartTheme.apply(chart);
    }

}

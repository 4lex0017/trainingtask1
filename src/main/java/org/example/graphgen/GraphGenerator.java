package org.example.graphgen;

import org.jfree.chart.JFreeChart;

public interface GraphGenerator<T> {

    JFreeChart generateGraph(T inputData);
}

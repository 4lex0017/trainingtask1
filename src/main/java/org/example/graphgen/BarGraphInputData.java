package org.example.graphgen;

import org.example.data.FundData;

public class BarGraphInputData {
    private final String chartTitle;
    private final FundData fundData;

    public BarGraphInputData(String chartTitle, FundData fundData) {
        this.chartTitle = chartTitle;
        this.fundData = fundData;
    }

    public String getChartTitle() {
        return chartTitle;
    }

    public FundData getFundData() {
        return fundData;
    }
}

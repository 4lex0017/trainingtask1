package org.example.graphgen;

import org.example.data.FundData;

import java.util.List;

public class LineGraphInputData {
    private final String chartTitle;
    private final List<FundData> fundData;
    private final int fund;

    public LineGraphInputData(String chartTitle, List<FundData> fundData, int fund) {
        this.chartTitle = chartTitle;
        this.fundData = fundData;
        this.fund = fund;
    }

    public String getChartTitle() {
        return chartTitle;
    }

    public List<FundData> getFundData() {
        return fundData;
    }

    public int getFund() {
        return fund;
    }
}
package org.example.database;

import org.example.data.FundData;

import java.util.List;

public interface FundDataDAO {

    void clearTable();

    void generateFundDataTable();

    List<FundData> findAllFundData();

    void saveFundData(FundData data);
}

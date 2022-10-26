package org.example.database.springdata.jpa;

import org.example.data.FundData;
import org.example.database.FundDataDAO;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile({"springData & !mongoDB"})
public class FundDataDAOSpringData implements FundDataDAO {

    private final FundDataJpaRepository fundDataJpaRepository;

    public FundDataDAOSpringData(FundDataJpaRepository fundDataJpaRepository) {
        this.fundDataJpaRepository = fundDataJpaRepository;
    }


    @Override
    public void clearTable() {
        fundDataJpaRepository.deleteAllInBatch();
    }

    @Override
    public void generateFundDataTable() {
    }

    @Override
    public List<FundData> findAllFundData() {
        return fundDataJpaRepository.findAll();
    }

    @Override
    public void saveFundData(FundData data) {
        fundDataJpaRepository.save(data);
    }
}

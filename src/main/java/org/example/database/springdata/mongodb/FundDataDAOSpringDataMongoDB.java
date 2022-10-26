package org.example.database.springdata.mongodb;

import org.example.data.FundData;
import org.example.data.FundDataMongo;
import org.example.database.FundDataDAO;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Profile({"springData & mongoDB"})
public class FundDataDAOSpringDataMongoDB implements FundDataDAO {

    private final FundDataMongoRepository fundDataRepository;

    public FundDataDAOSpringDataMongoDB(FundDataMongoRepository fundDataRepository) {
        this.fundDataRepository = fundDataRepository;
    }


    @Override
    public void clearTable() {
        fundDataRepository.deleteAll();
    }

    @Override
    public void generateFundDataTable() {
    }

    @Override
    public List<FundData> findAllFundData() {
        return fundDataRepository.findAll().stream().map(FundData::new).collect(Collectors.toList());
    }

    @Override
    public void saveFundData(FundData data) {
        fundDataRepository.save(new FundDataMongo(data));
    }
}

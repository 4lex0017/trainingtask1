package org.example.database.springjdbc;

import org.example.data.FundData;
import org.example.database.FundDataDAO;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("springJDBC")
public class FundDataDAOSpringJdbc implements FundDataDAO {

    final private String FUND_DATA_TABLE_NAME = "fund_data";

    final private JdbcTemplate jdbcTemplate;

    public FundDataDAOSpringJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void clearTable() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS " + FUND_DATA_TABLE_NAME + "; ");
    }

    @Override
    public void generateFundDataTable() {
        String sqlCreateFundDataTable = "CREATE TABLE IF NOT EXISTS " + FUND_DATA_TABLE_NAME +
                "(id        Serial," +
                "fundId     Integer NOT NULL," +
                "fundName   VarChar(255) NOT NULL," +
                "price      Integer NOT NULL," +
                "date       Date NOT NULL," +
                "shares_weighting   float8," +
                "bonds_weighting    float8," +
                "commodities_weighting   float8," +
                "cash_weighting float8," +
                "UNIQUE(date,fundId)," +
                "PRIMARY KEY (id)" +
                ")";
        jdbcTemplate.execute(sqlCreateFundDataTable);
    }

    @Override
    public void saveFundData(FundData data) {
        String sqlCreateFundData = "Insert Into " + FUND_DATA_TABLE_NAME + "(" +
                "fundId," +
                "fundName," +
                "price," +
                "date," +
                "shares_weighting," +
                "bonds_weighting," +
                "commodities_weighting," +
                "cash_weighting)" +
                "Values (?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sqlCreateFundData,
                data.getFundId(),
                data.getFundName(),
                data.getPrice(),
                data.getDate(),
                data.getSharesWeighting(),
                data.getBondsWeighting(),
                data.getCommoditiesWeighting(),
                data.getCashWeighting());

    }

    @Override
    public List<FundData> findAllFundData() {
        String sqlGetAllFundData = "Select * from " + FUND_DATA_TABLE_NAME + " ORDER BY date , fundId";
        return jdbcTemplate.query(sqlGetAllFundData, new BeanPropertyRowMapper<>(FundData.class));
//        return jdbcTemplate.query(sqlGetAllFundData, new FundDataMapper());
    }
}

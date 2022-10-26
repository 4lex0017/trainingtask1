package org.example.data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.time.format.DateTimeFormatter.BASIC_ISO_DATE;

@Entity
@Table(name = "fund_data", uniqueConstraints = {@UniqueConstraint(columnNames = {"date", "fundid"})})
public class FundData implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "fundname", nullable = false)
    private String fundName;

    @Column(name = "fundid", nullable = false)
    private int fundId;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "shares_weighting")
    private Double sharesWeighting;

    @Column(name = "bonds_weighting")
    private Double bondsWeighting;

    @Column(name = "commodities_weighting")
    private Double commoditiesWeighting;

    @Column(name = "cash_weighting")
    private Double cashWeighting;


    public FundData() {
        //Default constructor
    }

    public FundData(FundDataMongo data) {
        this(data.getFundName(), data.getFundId(), data.getPrice(), data.getDate(), data.getSharesWeighting(), data.getBondsWeighting(), data.getCommoditiesWeighting(), data.getCashWeighting());
    }

    public FundData(String fundName, int fundId, int price, LocalDate date, Double sharesWeighting, Double bondsWeighting, Double commoditiesWeighting, Double cashWeighting) {
        this.fundName = fundName;
        this.fundId = fundId;
        this.price = price;
        this.date = date;
        this.sharesWeighting = sharesWeighting;
        this.bondsWeighting = bondsWeighting;
        this.commoditiesWeighting = commoditiesWeighting;
        this.cashWeighting = cashWeighting;
    }

    public FundData(String fundName, int fundId, int price, String date, Double sharesWeighting, Double bondsWeighting, Double commoditiesWeighting, Double cashWeighting) {
        this(fundName, fundId, price, parseDate(date), sharesWeighting, bondsWeighting, commoditiesWeighting, cashWeighting);
    }

    public FundData(String[] data, String date) {
        this(data[TableSpec.NAME],
                Integer.parseInt(data[TableSpec.ID]),
                Integer.parseInt(data[TableSpec.Price]),
                parseDate(date),
                data[TableSpec.sharesWeighting].equals("") ? null : Double.parseDouble(data[TableSpec.sharesWeighting]),
                data[TableSpec.bondsWeighting].equals("") ? null : Double.parseDouble(data[TableSpec.bondsWeighting]),
                data[TableSpec.commoditiesWeighting].equals("") ? null : Double.parseDouble(data[TableSpec.commoditiesWeighting]),
                data[TableSpec.cashWeighting].equals("") ? null : Double.parseDouble(data[TableSpec.cashWeighting]));

    }

    public String getFundName() {
        return fundName;
    }

    public int getFundId() {
        return fundId;
    }

    public int getPrice() {
        return price;
    }

    public LocalDate getDate() {
        return date;
    }

    public Double getSharesWeighting() {
        return sharesWeighting;
    }

    public Double getBondsWeighting() {
        return bondsWeighting;
    }

    public Double getCommoditiesWeighting() {
        return commoditiesWeighting;
    }

    public Double getCashWeighting() {
        return cashWeighting;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setFundName(String fundName) {
        this.fundName = fundName;
    }

    public void setFundId(int fundId) {
        this.fundId = fundId;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setSharesWeighting(Double sharesWeighting) {
        this.sharesWeighting = sharesWeighting;
    }

    public void setBondsWeighting(Double bondsWeighting) {
        this.bondsWeighting = bondsWeighting;
    }

    public void setCommoditiesWeighting(Double commoditiesWeighting) {
        this.commoditiesWeighting = commoditiesWeighting;
    }

    public void setCashWeighting(Double cashWeighting) {
        this.cashWeighting = cashWeighting;
    }

    static LocalDate parseDate(String date) {
        // sample filename: data_20201001.csv
        Pattern p = Pattern.compile("_(\\d+)\\.");
        Matcher m = p.matcher(date);
        if (!m.find()) throw new RuntimeException();

        return LocalDate.parse(m.group(1), BASIC_ISO_DATE);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", FundData.class.getSimpleName() + "[", "]")
                .add("name='" + fundName + "'")
                .add("id=" + fundId)
                .add("price=" + price)
                .add("date=" + date)
                .add("sharesWeighting=" + sharesWeighting)
                .add("bondsWeighting=" + bondsWeighting)
                .add("commoditiesWeighting=" + commoditiesWeighting)
                .add("cashWeighting=" + cashWeighting)
                .toString();
    }
}

package org.example.data;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.time.format.DateTimeFormatter.BASIC_ISO_DATE;

@Document
public class FundDataMongo {

    @Id
    private ObjectId id;

    private String fundName;

    private int fundId;

    private int price;

    private LocalDate date;

    private Double sharesWeighting;

    private Double bondsWeighting;

    private Double commoditiesWeighting;

    private Double cashWeighting;


    public FundDataMongo() {
        //Default constructor
    }

    public FundDataMongo(String fundName, int fundId, int price, LocalDate date, Double sharesWeighting, Double bondsWeighting, Double commoditiesWeighting, Double cashWeighting) {
        this.fundName = fundName;
        this.fundId = fundId;
        this.price = price;
        this.date = date;
        this.sharesWeighting = sharesWeighting;
        this.bondsWeighting = bondsWeighting;
        this.commoditiesWeighting = commoditiesWeighting;
        this.cashWeighting = cashWeighting;
    }

    public FundDataMongo(String fundName, int fundId, int price, String date, Double sharesWeighting, Double bondsWeighting, Double commoditiesWeighting, Double cashWeighting) {
        this(fundName, fundId, price, parseDate(date), sharesWeighting, bondsWeighting, commoditiesWeighting, cashWeighting);
    }

    public FundDataMongo(String[] data, String date) {
        this(data[TableSpec.NAME],
                Integer.parseInt(data[TableSpec.ID]),
                Integer.parseInt(data[TableSpec.Price]),
                parseDate(date),
                data[TableSpec.sharesWeighting].equals("") ? null : Double.parseDouble(data[TableSpec.sharesWeighting]),
                data[TableSpec.bondsWeighting].equals("") ? null : Double.parseDouble(data[TableSpec.bondsWeighting]),
                data[TableSpec.commoditiesWeighting].equals("") ? null : Double.parseDouble(data[TableSpec.commoditiesWeighting]),
                data[TableSpec.cashWeighting].equals("") ? null : Double.parseDouble(data[TableSpec.cashWeighting]));

    }

    public FundDataMongo(FundData data) {
        this(data.getFundName(), data.getFundId(), data.getPrice(), data.getDate(), data.getSharesWeighting(), data.getBondsWeighting(), data.getCommoditiesWeighting(), data.getCashWeighting());
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

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
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
        return new StringJoiner(", ", FundDataMongo.class.getSimpleName() + "[", "]")
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

package org.example.data;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class FundDataCsvReader implements FundDataReader {
    @Override
    public List<FundData> read(String fileName) {
        try (CSVReader csvReader = new CSVReader(new FileReader(fileName))) {

            List<String[]> data = csvReader.readAll();

            return data.stream()
                    .skip(1) // skip headers
                    .filter(strings -> strings.length == 7)
                    .map(row -> new FundData(row, fileName))
                    .collect(toList());

        } catch (IOException | CsvException e) {
            throw new IllegalStateException("CSV file not found", e);
        }

    }

    @Override
    public boolean readsFile(String file) {
        return file.endsWith(".csv");
    }
}

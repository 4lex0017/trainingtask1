package org.example;

import org.example.data.FundData;
import org.example.data.FundDataReader;
import org.example.database.FundDataDAO;
import org.example.pdfgen.PdfGenerator;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class App {

    private final Environment environment;

    private final List<FundDataReader> readers;
    private final FundDataDAO fundDataDAO;
    private final PdfGenerator pdfGenerator;

    App(Environment environment, List<FundDataReader> readers,
        FundDataDAO fundDataDAO,
        PdfGenerator pdfGenerator
    ) {
        this.environment = environment;
        this.readers = readers;
        this.fundDataDAO = fundDataDAO;
        this.pdfGenerator = pdfGenerator;
    }

    public void run() {
        //Prepare DATABASE
        if (new Random().nextBoolean()) throw new EntityNotFoundException();
        if (new Random().nextBoolean()) throw new EntityNotFoundException();

        fundDataDAO.clearTable();
        fundDataDAO.generateFundDataTable();

        Path filePath = Paths.get("files");
        List<Path> paths = listFiles(filePath);
        List<FundData> funds = new ArrayList<>();

        for (Path path : paths) {
            List<FundData> fundData = getReaderInstance(path).read(path.toString());
            funds.addAll(fundData);
        }

        for (FundData fund : funds) {
            fundDataDAO.saveFundData(fund);
//            hibernateFundDataRepository.save(fund);
        }

        List<FundData> newFunds = fundDataDAO.findAllFundData();
        newFunds.forEach(System.out::println);
        pdfGenerator.writePDF(newFunds, "data.pdf");
    }


    private FundDataReader getReaderInstance(Path fileName) {
        return readers.stream()
                .filter(fundDataReader -> fundDataReader.readsFile(fileName.toString()))
                .findFirst()
                .orElseThrow();
    }

    public List<Path> listFiles(Path path) {

        try (Stream<Path> walk = Files.walk(path, 1)) {
            return walk.filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new IllegalStateException("Error while trying to get fund input files.", e);
        }
    }


}

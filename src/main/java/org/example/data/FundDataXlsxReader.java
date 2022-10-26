package org.example.data;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class FundDataXlsxReader implements FundDataReader {

    @Override
    public List<FundData> read(String fileName) {
        try (XSSFWorkbook myWorkbook = new XSSFWorkbook(fileName)) {
            XSSFSheet mySheet = myWorkbook.getSheetAt(0);
            Iterator<Row> rowIterator = mySheet.rowIterator();
            List<FundData> funds = new ArrayList<>();

            // Skip first row
            if (rowIterator.hasNext()) rowIterator.next();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                int id = getIntValue(row, 0);
                String fundName = row.getCell(1).getStringCellValue();
                int price = getIntValue(row, 2);
                Double cell3 = getNumericCellValue(row, 3);
                Double cell4 = getNumericCellValue(row, 4);
                Double cell5 = getNumericCellValue(row, 5);
                Double cell6 = getNumericCellValue(row, 6);
                funds.add(new FundData(fundName, id, price, fileName, cell3, cell4, cell5, cell6));
            }
            return funds;
        } catch (IOException e) {
            throw new IllegalStateException("File is not a .xlse", e);
        }
    }

    private static int getIntValue(Row row, int cellNum) {
        Double numericCellValue = getNumericCellValue(row, cellNum);
        if (numericCellValue == null) throw new RuntimeException();
        else return numericCellValue.intValue();
    }

    private static Double getNumericCellValue(Row row, int cellnum) {
        Cell cell = row.getCell(cellnum);
        if (cell == null || cell.getCellType().equals(CellType.BLANK)) return null;
        return cell.getNumericCellValue();
    }


    @Override
    public boolean readsFile(String file) {
        return file.endsWith(".xlsx");
    }
}

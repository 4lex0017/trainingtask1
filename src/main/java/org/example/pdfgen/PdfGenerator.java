package org.example.pdfgen;

import com.itextpdf.awt.PdfGraphics2D;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import org.example.data.FundData;
import org.example.graphgen.BarGraphGenerator;
import org.example.graphgen.BarGraphInputData;
import org.example.graphgen.LineGraphGenerator;
import org.example.graphgen.LineGraphInputData;
import org.jfree.chart.JFreeChart;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class PdfGenerator {

    private final BarGraphGenerator barGraphGenerator;
    private final LineGraphGenerator lineGraphGenerator;

    public PdfGenerator(BarGraphGenerator bar_graph_generator, LineGraphGenerator line_graph_generator) {
        barGraphGenerator = bar_graph_generator;
        lineGraphGenerator = line_graph_generator;
    }


    public void writePDF(List<FundData> funds, String s) {
        Document document = new Document();
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(s));
            writeDocument(funds, document, writer);
            writer.close();
        } catch (DocumentException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private void writeDocument(List<FundData> funds, Document document, PdfWriter writer) {
        document.open();
        PdfContentByte pdfContentByte = writer.getDirectContent();
        float width = PageSize.A4.getWidth();
        float height = PageSize.A4.getHeight() * 0.95f;

        float halfHeight = height / 2;

        List<FundData> sortedFunds = funds.stream().sorted(Comparator.comparing(FundData::getDate)).collect(toList());

        FundData latestFundData = funds.stream().max(Comparator.comparing(FundData::getDate)).orElseThrow();
        generateGraphs(sortedFunds.stream().filter(fundData ->
                (fundData.getFundId() == latestFundData.getFundId())).collect(toList()), pdfContentByte, width, halfHeight, latestFundData);

        document.newPage();

        FundData secondLatestFundData = funds.stream().sorted(Comparator.comparing(FundData::getDate).reversed()).skip(1).findFirst().orElseThrow();
        generateGraphs(sortedFunds.stream().filter(fundData -> (fundData.getFundId() == secondLatestFundData.getFundId())).collect(toList()), pdfContentByte, width, halfHeight, secondLatestFundData);

        document.close();
    }

    private void generateGraphs(List<FundData> funds, PdfContentByte pdfContentByte, float width, float halfHeight, FundData latestFundData) {
        generateGraphForPDF(pdfContentByte, width, halfHeight, halfHeight, barGraphGenerator.generateGraph(new BarGraphInputData("Weighting", latestFundData)));
        generateGraphForPDF(pdfContentByte, width, halfHeight, 0, lineGraphGenerator.generateGraph(new LineGraphInputData("Price", funds, latestFundData.getFundId())));
    }

    private static void generateGraphForPDF(PdfContentByte pdfContentByte, float width, float halfHeight, float halfHeight1, JFreeChart graphGenerator) {
        PdfTemplate bar = pdfContentByte.createTemplate(width, halfHeight);
        Graphics2D graphics2D1 = new PdfGraphics2D(bar, width, halfHeight);
        Rectangle2D rectangle2D1 = new Rectangle2D.Double(0, 0, width, halfHeight * 0.9f);
        graphGenerator.draw(graphics2D1, rectangle2D1);
        graphics2D1.dispose();
        pdfContentByte.addTemplate(bar, 0, halfHeight1);
    }
}

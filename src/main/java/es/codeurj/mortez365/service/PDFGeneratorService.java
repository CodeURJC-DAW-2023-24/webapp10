package es.codeurj.mortez365.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import es.codeurj.mortez365.model.Bet;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class PDFGeneratorService {

    public void export(HttpServletResponse response) throws IOException {

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
/*
        List<Bet> allBets = bets.findAll();
        Double totalWinningAmount = 0.0;
        Double totalBet = 0.0;
        Double totalBenefit = 0.0;
        for (Bet bet : allBets) {
            totalWinningAmount += bet.getWinning_amount();
            totalBet += bet.getBet_amount();
            totalBenefit = bet.getProfit();
        }*/

        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(18);

        Paragraph paragraph = new Paragraph("This is a title.", fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);

        Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
        fontParagraph.setSize(12);

        Paragraph paragraph2 = new Paragraph("This is a paragraph.", fontParagraph);
        paragraph2.setAlignment(Paragraph.ALIGN_LEFT);

        document.add(paragraph);
        document.add(paragraph2);
        document.close();
    }

}

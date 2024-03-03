package es.codeurj.mortez365.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import es.codeurj.mortez365.model.Bet;
import es.codeurj.mortez365.repository.BetRepository;
import es.codeurj.mortez365.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Service
public class PDFGeneratorService {

    @Autowired
    private BetRepository betRepository;

    public void export(HttpServletResponse response, Principal principal) throws IOException {

        // Initialize document
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        // To get all the bets
        List<Bet> allBets = betRepository.findAll();

        // Title font
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(28);

        // First title
        Paragraph paragraph = new Paragraph("Historial de apuestas - Usuario:\n" + principal.getName() + "\n", fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(paragraph);

        // Paragraph font
        Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
        fontParagraph.setSize(12);

        for (Bet bet : allBets) {
            Paragraph new_paragraph = new Paragraph(String.valueOf(allBets.indexOf(bet) + 1) + ".- " + bet.getEvent().getName() + "\nCantidad apostada: "
                    + bet.getBet_amount() + "€" + "\nCantidad ganada: " + bet.getWinning_amount() + "€" + "\nBeneficio obtenido: "
                    + bet.getProfit() + "€", fontParagraph);
            new_paragraph.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(new_paragraph);
        }

        document.close();
    }

}

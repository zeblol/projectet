package Domain;

import java.io.FileOutputStream;
import java.util.Date;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

/**
 *
 * @author Kirstine
 */
public class PDFGenerator {

    private String FILE = "c:/temp/Fakturering.pdf";
    private Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    private Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL, BaseColor.RED);
    private Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
    private Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);
    
    String cNavn;
    int cID;
    String cEmail;
    int cPhoneNumber;
    String cAddress;
    int cZip;
    int oID;
    Date from;
    Date to;
    double totalPris;
    double depositum;
    Date forfaldDato;
    int banknummer;
    String userName;
    
    Controller c = new Controller();
    Customer customer = new Customer(cID, cNavn, cEmail, cPhoneNumber, cAddress, cZip);
    // Order order = new Order(oID, cID, order.getFromDate(from), order.getToDate(to));

    public void create() {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(FILE));
            document.open();
            addMetaData(document);
            addTitlePage(document);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // iText allows to add metadata to the PDF which can be viewed in your Adobe
    // Reader
    // under File -> Properties
    private static void addMetaData(Document document) {
        document.addTitle("Fakturering af ordre");
        document.addSubject("Using iText");
        document.addKeywords("Java, PDF, iText");
        document.addAuthor("Hellebæk Festudlejning");
        document.addCreator("Hellebæk Festudlejning");
    }

    private void addTitlePage(Document document)
            throws DocumentException {
        Paragraph preface = new Paragraph();
        // Tomme linjer
        addEmptyLine(preface, 1);
        // Overskrift
        preface.add(new Paragraph("Tak for deres ordre hos Hellebæk Festudlejning", catFont));

        addEmptyLine(preface, 2);
        preface.add(new Paragraph("Kære " + cNavn + ".", subFont)); // generer navnet via cID fra Order.
        preface.add(new Paragraph("Vi har reserveret følgende produkter til Dem, mellem den "
                + datoFra + " - " + datoTil + ".", subFont)); // generer datoer fra Order.
        // indsæt her koden til at udskrive ordredetaljer

        addEmptyLine(preface, 3);

        preface.add(new Paragraph("Ordren er sat til at blive leveret til følgende adresse: "
                + adresse + ".", subFont)); // generer adresse via cID fra Order.
        // indsæt her koden til at udskrive kundens adresse

        addEmptyLine(preface, 3);

        preface.add(new Paragraph("Den samlede pris for Deres ordre, med ordrenummer: " + oID
                + ", er: " + totalPris, subFont)); // få oID fra Order, og totalPrisen regnes ud i controlleren
        preface.add(new Paragraph("Depositum for Deres bestilling er: " + depositum + ".", subFont)); // 1/3 af totalPrisen

        addEmptyLine(preface, 1);
        preface.add(new Paragraph("Depositummet bedes indbetalt senest " + forfaldDato
                + ", ellers vil yderligere gebyr blive pålagt.", redFont)); // forfaldDato regnes ud fra fraDato

        addEmptyLine(preface, 1);
        preface.add(new Paragraph("Indbetaling kan ske via bankoverførsel: " + banknummer + ".", smallBold)); // banknummer genereres ud fra kundenummer og ordrenummer, samt genkendelsesnummer.

        addEmptyLine(preface, 3);

        preface.add(new Paragraph("Hellebæk Festudlejning ønsker Dem en rigtig god fest!", subFont));
        preface.add(new Paragraph("Venlig hilsen: " + userName + ", " + new Date(), subFont)); // userName skal genereres via DB

        document.add(preface);
    }

    private void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}

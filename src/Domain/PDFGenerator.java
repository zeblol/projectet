package Domain;

import java.io.FileOutputStream;
import java.util.Date;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;

/**
 *
 * @author Kirstine
 */
public class PDFGenerator {

    private String FILE;
    private Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    private Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL, BaseColor.RED);
    private Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
    private Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);
    
    private double depositum;
    private Date forfaldDato;
    private Date idag;
    private double total;
    private int kontonr = 12345678;
    private Customer customer;
    private Order order;
//    private Employee emp;

    // Frederik
    public PDFGenerator(Customer c, Order o, double depositum, double total) {
        customer = c;
        order = o;
//        emp = e;
        this.depositum = depositum;
        idag = new Date(System.currentTimeMillis());
        this.total = total;
        FILE = "c:/temp/" + System.currentTimeMillis() + "-" + order.getOID() + ".pdf";
    }
    
    //Charlotte
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
    // Kirstine
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
        preface.add(new Paragraph("Tak for Deres ordre hos Hellebæk Festudlejning", catFont));
        // hilsen og datoer for kunden og dennes ordre:
        addEmptyLine(preface, 2);
        preface.add(new Paragraph("Kære " + customer.getcName() + ".", subFont));
        preface.add(new Paragraph("Vi har reserveret følgende produkter til Dem, mellem den "
                + order.getFromDate() + " - " + order.getToDate() + ".", subFont));
        // ordredetaljerne:
        preface.add(new Paragraph("" + order.getOrderDetails().toString()));

        addEmptyLine(preface, 2);
        
        // adresse:
        preface.add(new Paragraph("Ordren er sat til at blive leveret til følgende adresse: " + "\n"
                + customer.getcAddress().toString() + ".", subFont));
        
        addEmptyLine(preface, 3);
        // Informationer til indbetaling:
        preface.add(new Paragraph("Den samlede pris for Deres ordre, med ordrenummer: " + order.getOID()
                + ", er: " + total, subFont));
        preface.add(new Paragraph("Depositum for Deres bestilling er: " + depositum + ".", subFont)); // 1/3 af totalPrisen

        addEmptyLine(preface, 1);
        preface.add(new Paragraph("Depositummet bedes indbetalt senest " + forfaldDato
                + ", ellers vil yderligere gebyr blive pålagt.", redFont)); // forfaldDato regnes ud fra fraDato
        // Overførselsinformationer:
        addEmptyLine(preface, 1);
        preface.add(new Paragraph("Indbetaling kan ske via bankoverførsel: " + "+71<00000"
                + order.getOID() + order.getCID() + "+" + kontonr + "<", smallBold));

        addEmptyLine(preface, 3);
        // Afslutning
        preface.add(new Paragraph("Hellebæk Festudlejning ønsker Dem en rigtig god fest!", subFont));
//        preface.add(new Paragraph("Venlig hilsen: " + emp.geteNavn() + ", " + idag, subFont));

        document.add(preface);
    }

    private void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}

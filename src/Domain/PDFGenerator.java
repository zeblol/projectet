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
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private ArrayList<OrderDetail> odl;
    private double nsp;
    private ArrayList list;
    

    // Frederik
    public PDFGenerator(Customer c, Order o, double depositum, double total) {
        customer = c;
        order = o;
//        emp = e;
        this.depositum = depositum;
        idag = new Date(System.currentTimeMillis());
        this.total = total;
    }

    public PDFGenerator(ArrayList<OrderDetail> odl, double nsp, Order o) {
        this.odl = odl;
        this.nsp = nsp;
        order = o;
    }
    
    public PDFGenerator(ArrayList l){
        list = l;
    }
    
    //Charlotte
    public void create() {
        FILE = "c:/temp/" + System.currentTimeMillis() + "-" + order.getOID() + ".pdf";
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

    // Frederik og Kirstine
    public void createNedskrivningPDF() {
        if (odl.isEmpty()) {
            return;
        }
        Document document = new Document();
        FILE = "c:\\temp\\Nedskrivninger\\n" + System.currentTimeMillis() + "-" + odl.get(0).getoID() + ".pdf";
        try {
            PdfWriter.getInstance(document, new FileOutputStream(FILE));
            document.open();
            document.addTitle("Nedskrivning af ordre");
            document.addSubject("Using iText");
            document.addKeywords("Java, PDF, iText");
            document.addAuthor("Hellebæk Festudlejning");
            document.addCreator("Hellebæk Festudlejning");


            Paragraph preface = new Paragraph();
            // Tomme linjer
            addEmptyLine(preface, 1);
            // Overskrift
            preface.add(new Paragraph("Nedskrivning af udlejet materiale hos Hellebæk Festudlejning", catFont));
            addEmptyLine(preface, 2);
            preface.add(new Paragraph("For ordre nummer: " + odl.get(0).getoID() + " er registreret følgende nedskrivninger: ", subFont));
            for (int i = 0; i < odl.size(); i++) {
                preface.add(new Paragraph("" + odl.get(i).toString()));
            }
            addEmptyLine(preface, 2);
            // Informationer til indbetaling:
            preface.add(new Paragraph("Den samlede pris for nedskrivningen er: " + nsp, subFont));
            addEmptyLine(preface, 1);
            Calendar c = new GregorianCalendar();
            Date d = new Date();
            c.setTimeInMillis(d.getTime());
            c.add(Calendar.DAY_OF_MONTH, 8);
            preface.add(new Paragraph("Det skyldige beløb bedes indbetalt senest " + c.get(Calendar.DAY_OF_MONTH)
                    + "/" + (c.get(Calendar.MONTH) + 1) + " " + c.get(Calendar.YEAR)
                    + ", ellers vil yderligere gebyr blive pålagt.", redFont)); // forfaldDato regnes ud fra dags dato
            // Overførselsinformationer:
            addEmptyLine(preface, 1);
            preface.add(new Paragraph("Indbetaling kan ske via bankoverførsel: " + "+71<00000"
                    + order.getOID() + order.getCID() + "+" + kontonr + "<", smallBold));
            addEmptyLine(preface, 3);
            // Afslutning
            preface.add(new Paragraph("Med venlig hilsen Hellebæk Festudlejning.", subFont));
            document.add(preface);
            document.close();
        } catch (DocumentException | FileNotFoundException ex) {
            Logger.getLogger(PDFGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //Sebastian
    public void createPakkelistePDF(String datoFra, String datoTil){
        if(list.isEmpty()){
            return;
        }
        Document document = new Document();
        FILE = "c:\\temp\\Pakkelister\\p" + System.currentTimeMillis() + ".pdf";
        
         try {
            PdfWriter.getInstance(document, new FileOutputStream(FILE));
            document.open();
            document.addTitle("Pakkeliste for ordrer");
            document.addSubject("Using iText");
            document.addKeywords("Java, PDF, iText");
            document.addAuthor("Hellebæk Festudlejning");
            document.addCreator("Hellebæk Festudlejning");
            
            Paragraph preface = new Paragraph();
            //tom linje
            addEmptyLine(preface, 1);
            //overskrift
            preface.add(new Paragraph("Pakkeliste for Ordrer", catFont));
            addEmptyLine(preface, 2);
            preface.add("I perioden " + datoFra + " til " + datoTil + " er følgende ordrer registreret:");
             for (int i = 0; i < list.size(); i++)
             {
                 addEmptyLine(preface, 2);
                 Order o = (Order)list.get(i);
                 ArrayList oList = new ArrayList();
                 oList = o.getOrderDetails();
                 preface.add(""+o.getOID());
                 for (int j = 0; j < oList.size(); j++)
                 {
                     preface.add("\t"+oList.get(j));
                 }
             }
            addEmptyLine(preface, 2);
            preface.add(new Paragraph("Hellebæk Festudlejning.", subFont));
            document.add(preface);
            document.close();
         }catch (DocumentException | FileNotFoundException ex) {
            Logger.getLogger(PDFGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // Charlotte
    public void createStatusLastbilerPDF(String datoFra, String datoTil){
                if(list.isEmpty()){
            return;
        }
        Document document = new Document();
        FILE = "c:\\temp\\Statuslister (Lastbiler)\\l" + System.currentTimeMillis() + ".pdf";
        
         try {
            PdfWriter.getInstance(document, new FileOutputStream(FILE));
            document.open();
            document.addTitle("Status over Lastbiler");
            document.addSubject("Using iText");
            document.addKeywords("Java, PDF, iText");
            document.addAuthor("Hellebæk Festudlejning");
            document.addCreator("Hellebæk Festudlejning");
            
            Paragraph preface = new Paragraph();
            //tom linje
            addEmptyLine(preface, 1);
            //overskrift
            preface.add(new Paragraph("Status over lastbiler", catFont));
            addEmptyLine(preface, 2);
            preface.add("I perioden " + datoFra + " til " + datoTil + " er følgende lastbiler registreret:");
             for (int i = 0; i < list.size(); i++)
             {
                 Order vl = (Order)list.get(i);
                 if(!vl.getVehicles().isEmpty()){
                     addEmptyLine(preface, 2);
                     ArrayList vlList = new ArrayList();
                     vlList = vl.getOrderDetails();
                     preface.add(""+vl.getOID());
                     for (int j = 0; j < vlList.size(); j++)
                     {
                         preface.add("\t"+vlList.get(j));
                     }
                 }
             }
            addEmptyLine(preface, 2);
            preface.add(new Paragraph("Hellebæk Festudlejning.", subFont));
            document.add(preface);
            document.close();
         }catch (DocumentException | FileNotFoundException ex) {
            Logger.getLogger(PDFGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
    //Sebastian
    public void createStatusInstallers(String datoFra, String datoTil){
        if(list.isEmpty()){
            return;
        }
        Document document = new Document();
        FILE = "c:\\temp\\Statuslister (Montører)\\I" + System.currentTimeMillis() + ".pdf";
        
         try {
            PdfWriter.getInstance(document, new FileOutputStream(FILE));
            document.open();
            document.addTitle("Status over Installers");
            document.addSubject("Using iText");
            document.addKeywords("Java, PDF, iText");
            document.addAuthor("Hellebæk Festudlejning");
            document.addCreator("Hellebæk Festudlejning");
            
            Paragraph preface = new Paragraph();
            //tom linje
            addEmptyLine(preface, 1);
            //overskrift
            preface.add(new Paragraph("Status over montører", catFont));
            addEmptyLine(preface, 2);
            preface.add("I perioden " + datoFra + " til " + datoTil + " er følgende montører registreret:");
             for (int i = 0; i < list.size(); i++)
             {
                 Order o = (Order)list.get(i);
                 if(!o.getInstallers().isEmpty()){
                     addEmptyLine(preface, 2);
                     ArrayList iList = new ArrayList();
                     iList = o.getInstallers();
                     preface.add(""+o.getOID());
                     for (int j = 0; j < iList.size(); j++)
                     {
                         preface.add("\t"+iList.get(j));
                     }
                 }
             }
            addEmptyLine(preface, 2);
            preface.add(new Paragraph("Hellebæk Festudlejning.", subFont));
            document.add(preface);
            document.close();
         }catch (DocumentException | FileNotFoundException ex) {
            Logger.getLogger(PDFGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Charlotte
    public void createOrdrelistePDF(String datoFra, String datoTil){       
        if(list.isEmpty()){
            return;
        }
        Document document = new Document();
        FILE = "c:\\temp\\Ordrelister\\o" + System.currentTimeMillis() + ".pdf";
        
         try {
            PdfWriter.getInstance(document, new FileOutputStream(FILE));
            document.open();
            document.addTitle("Ordreliste");
            document.addSubject("Using iText");
            document.addKeywords("Java, PDF, iText");
            document.addAuthor("Hellebæk Festudlejning");
            document.addCreator("Hellebæk Festudlejning");
            
            Paragraph preface = new Paragraph();
            //tom linje
            addEmptyLine(preface, 1);
            //overskrift
            preface.add(new Paragraph("Ordreliste", catFont));
            addEmptyLine(preface, 2);
            preface.add("I perioden " + datoFra + " til " + datoTil + " er følgende ordrer registreret:");
             for (int i = 0; i < list.size(); i++)
             {
                 addEmptyLine(preface, 2);
                 Order o = (Order)list.get(i);
                 preface.add("\t"+ o );
             }          
            addEmptyLine(preface, 2);
            preface.add(new Paragraph("Hellebæk Festudlejning.", subFont));
            document.add(preface);
            document.close();
         }catch (DocumentException | FileNotFoundException ex) {
            Logger.getLogger(PDFGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

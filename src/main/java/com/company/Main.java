package com.company;


import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import org.apache.pdfbox.io.IOUtils;

import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Calendar;
import java.util.Date;


/**
 * @author Bruno Lowagie (iText Software)
 */
public class Main {
    public static final String src = "C:\\Users\\Josef\\Desktop\\Pdf\\billing\\facture.pdf";
    public static final String src2 = "C:\\Users\\Josef\\Desktop\\Pdf\\billing\\facture2.pdf";
    public static final String save = "C:\\Users\\Josef\\Desktop\\Pdf\\billing\\test.pdf";
    public static final String IMG = "C:\\Users\\Josef\\Desktop\\Pdf\\billing\\facture.jpg";

    public static void main(String[] args) throws IOException, DocumentException {
        File file = new File(src);
        file.getParentFile().mkdirs();
        Billing bill = new Billing();
        bill.setClient("Josef Yakopow");
        bill.setDate("15/03/18");
        bill.setReference("F16-5845");

        Trainee t1 = new Trainee("Test1", "Test1");
        Trainee t2 = new Trainee("Test2", "Test2");
        Trainee t3 = new Trainee("Test3", "Test3");
        Trainee t4 = new Trainee("Test4", "Test4");

        Convention con1 = new Convention("SP1111", "CV17-4945", "12/05/17", "18/01/18", "10", "50", "820", "7800 €", "testestestestestest");
        Convention con2 = new Convention("SP2222", "CV17-4988", "12/06/17", "18/02/18", "11", "51", "821", "8800 €", "testestestestestest");
        Convention con3 = new Convention("SP3333", "CV17-4977", "12/07/17", "18/03/18", "20", "52", "822", "9800 €", "testestestestestest");
        Convention con4 = new Convention("SP4444", "CV17-4966", "12/08/17", "18/04/18", "30", "53", "823", "10800 €", "testestestestestest");
        Convention con5 = new Convention("SP5555", "CV17-4955", "12/09/17", "18/05/18", "40", "54", "824", "11800 €", "testestestestestest");

        con1.getTraineeList().add(t1);
        con2.getTraineeList().add(t1);
        con2.getTraineeList().add(t2);
        con3.getTraineeList().add(t1);
        con3.getTraineeList().add(t2);
        con3.getTraineeList().add(t3);
        con4.getTraineeList().add(t1);
        con4.getTraineeList().add(t2);
        con4.getTraineeList().add(t3);
        con4.getTraineeList().add(t4);
        con5.getTraineeList().add(t1);
        con5.getTraineeList().add(t1);
        con5.getTraineeList().add(t1);
        con5.getTraineeList().add(t1);
        con5.getTraineeList().add(t1);
        con5.getTraineeList().add(t1);
        con5.getTraineeList().add(t1);


        bill.getConventionList().add(con1);
        bill.getConventionList().add(con2);
        bill.getConventionList().add(con3);
        bill.getConventionList().add(con4);
        bill.getConventionList().add(con5);


        new SmallTable().manipulatePdf(src, save, bill);
    }

    public static class SmallTable {


        public void manipulatePdf(String src, String save, Billing bill) throws DocumentException, IOException {
            /*ByteArrayOutputStream file = new ByteArrayOutputStream();*/

            /**
             * open pdf and put into stamper
             * create font
             */
            PdfReader reader = new PdfReader(src);
            ByteArrayOutputStream file = new ByteArrayOutputStream();
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(save));

            Rectangle pagesize = reader.getPageSize(1);
            Font font = FontFactory.getFont(FontFactory.defaultEncoding, 8, 1, BaseColor.WHITE);
            Font fontBlack = FontFactory.getFont(FontFactory.defaultEncoding, 8, 1, BaseColor.BLACK);
            ColumnText column = new ColumnText(stamper.getOverContent(1));
            Rectangle rectPage1 = new Rectangle(5, 105, 600, 600);
            column.setSimpleColumn(rectPage1);

            PdfPTable footer = new PdfPTable(3);
            footer.setWidths(new int[]{1, 3, 1});

            /**
             * create table structure
             */

            for (Convention c : bill.getConventionList()) {

                PdfPTable table = new PdfPTable(8);
                table.setWidths(new int[]{4, 3, 1, 1, 1, 1, 2, 2});
                table.setKeepTogether(true);


                PdfPTable title = new PdfPTable(2);
                title.setWidths(new int[]{2, 3});
                PdfPTable total = new PdfPTable(1);


                table.setHeaderRows(1);
                total.setHeaderRows(1);

                Paragraph[] p = new Paragraph[]{new Paragraph("Désignation", font),
                        new Paragraph("Stagiaire", font),
                        new Paragraph("Du", font),
                        new Paragraph("Au", font),
                        new Paragraph("Qté", font),
                        new Paragraph("Unité", font),
                        new Paragraph("P.U", font),
                        new Paragraph("Total.HT", font)};
                for (int i = 0; i < p.length; i++) {

                    PdfPCell cell = new PdfPCell();
                    cell.setBackgroundColor(new BaseColor(139, 79, 91));
                    p[i].setAlignment(Element.ALIGN_CENTER);
                    cell.addElement(p[i]);
                    cell.setBorderWidth((float) 0.6);
                    table.addCell(cell);

                }

                for (int k = 0; k < 2; k++) {


                    if (k == 0) {
                        PdfPCell cell = new PdfPCell(new Paragraph(("Session: " + c.getSession() + "                       Convention : " + c.getConvention()), font));
                        cell.setBackgroundColor(new BaseColor(139, 79, 91));
                        cell.setBorderWidth((float) 0.6);
                        title.addCell(cell);
                    } else {
                        PdfPCell cell = new PdfPCell(new Paragraph());
                        cell.setBackgroundColor(new BaseColor(255, 255, 255));
                        cell.setBorderWidth((float) 0);
                        title.addCell(cell);


                    }

                }

                for (int i = 1; i <= c.getTraineeList().size(); i++) {

                    table.addCell(new PdfPCell(new Paragraph(c.getDesignation(), fontBlack))).setBorderWidth((float) 0.6);
                    table.addCell(new PdfPCell(new Paragraph(c.getTraineeList().get(i - 1).getFname(), fontBlack))).setBorderWidth((float) 0.6);
                    table.addCell(new PdfPCell(new Paragraph(c.getStart(), fontBlack))).setBorderWidth((float) 0.6);
                    table.addCell(new PdfPCell(new Paragraph(c.getEnd(), fontBlack))).setBorderWidth((float) 0.6);
                    table.addCell(new PdfPCell(new Paragraph(c.getQuantity(), fontBlack))).setBorderWidth((float) 0.6);
                    table.addCell(new PdfPCell(new Paragraph(c.getUnit(), fontBlack))).setBorderWidth((float) 0.6);
                    table.addCell(new PdfPCell(new Paragraph(c.getPu(), fontBlack))).setBorderWidth((float) 0.6);
                    table.addCell(new PdfPCell(new Paragraph(c.getHt(), fontBlack))).setBorderWidth((float) 0.6);


                }


                table.setWidthPercentage(100);
                title.setWidthPercentage(100);
                total.setWidthPercentage(90);


                title.setSpacingBefore(28);
                table.setSpacingBefore(10);


                total.addCell(new PdfPCell(title)).setBorderWidth(0);
                total.addCell(new PdfPCell(table)).setBorderWidth(0);



                /*total.addCell(table);*/
                column.addElement(total);


              /*  column.addElement(title);
                column.addElement(table);
*/

                int pagecount = 1;
                int status = column.go();
                while (ColumnText.hasMoreText(status)) {
                    status = triggerNewPage(stamper, pagesize, column, rectPage1, ++pagecount, reader);
                }
            }
            for (int g=0;g<12;g++){
                footer.addCell(new PdfPCell(new Paragraph("TEST"+g, fontBlack))).setBorderWidth((float) 0.6);


            }
            footer.setWidthPercentage(100);
            column.addElement(footer);

            stamper.close();
            reader.close();


        }

        public int triggerNewPage(PdfStamper stamper, Rectangle pagesize, ColumnText column, Rectangle rect, int pagecount, PdfReader reader) throws DocumentException, IOException {

            PdfReader reader1 = new PdfReader(src2);
            PdfImportedPage page = stamper.getImportedPage(reader, 1);
            stamper.insertPage(2, reader1.getPageSize(1));
            PdfContentByte pb = stamper.getUnderContent(2);
            pb.addTemplate(page, 0, 0);


            /*stamper.insertPage(pagecount, pagesize);*/
            PdfContentByte canvas = stamper.getOverContent(pagecount);
            column.setCanvas(canvas);
            column.setSimpleColumn(rect);
            return column.go();
        }
    }

    private static byte[] readBytesFromFile(String filePath) {

        FileInputStream fileInputStream = null;
        byte[] bytesArray = null;

        try {

            File file = new File(filePath);
            bytesArray = new byte[(int) file.length()];

            //read file into bytes[]
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytesArray);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        return bytesArray;

    }

}
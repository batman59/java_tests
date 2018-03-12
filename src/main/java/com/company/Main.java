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
        new SmallTable().manipulatePdf(src, save);
    }

    public static class SmallTable {


        public void manipulatePdf(String src, String save) throws DocumentException, IOException {
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
            Rectangle rectPage1 = new Rectangle(5,105,600,600);
            column.setSimpleColumn(rectPage1);






            /**
             * create table structure
             */

            for (int j = 0; j < 9; j++) {

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

                for (int i = 1; i <= 16; i++) {

                    if (i<9)
                    table.addCell(new PdfPCell(new Paragraph("test" + i, fontBlack))).setBorderWidth((float) 0.6);
                    else
                        table.addCell(new PdfPCell(new Paragraph("test" + i, fontBlack))).setBorderWidth((float) 0.6);
                }
                for (int k = 0; k < 2; k++) {


                    if (k==0) {
                        PdfPCell cell = new PdfPCell(new Paragraph(("Session: SP3714                       Convention : CV17-4945"), font));
                        cell.setBackgroundColor(new BaseColor(139, 79, 91));
                        cell.setBorderWidth((float) 0.6);
                        title.addCell(cell);
                    }
                    else{
                        PdfPCell cell = new PdfPCell(new Paragraph());
                        cell.setBackgroundColor(new BaseColor(255, 255, 255));
                        cell.setBorderWidth((float) 0);
                        title.addCell(cell);


                    }

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
                    status = triggerNewPage(stamper, pagesize, column, rectPage1, ++pagecount,reader);
                }
            }


            stamper.close();
            reader.close();

         /*   FacesContext context = FacesContext.getCurrentInstance();
            HttpServletResponse res = (HttpServletResponse) context.getExternalContext().getResponse();
            res.reset();
            res.setContentType("application/pdf");
            res.setHeader("Content-disposition", "inline;filename=test.pdf");

            OutputStream out = res.getOutputStream();
            file.writeTo(out);
            out.flush();
            out.close();
            context.responseComplete();*/
        }

        public int triggerNewPage(PdfStamper stamper, Rectangle pagesize, ColumnText column, Rectangle rect, int pagecount,PdfReader reader) throws DocumentException, IOException {

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
package com.decipher.agriculture.farmreport;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;


@Component
public class PdfTableHeader extends PdfPageEventHelper {

    private static final Logger logger = LoggerFactory.getLogger(PdfTableHeader.class);

    /**
     * The header text.
     */
    String[] header;
    /**
     * The template with the total number of pages.
     */
    PdfTemplate total;


    LineSeparator UNDERLINE = new LineSeparator(1, 100, null, Element.ALIGN_CENTER, -2);


    public void setHeader(String[] header) {
        this.header = header;
    }

    @Override
    public void onOpenDocument(PdfWriter writer, Document document) {
        total = writer.getDirectContent().createTemplate(30, 16);
        logger.info("PdfTemplate  onOpenDocument " + total);
    }

    @Override
    public void onStartPage(PdfWriter writer, Document document) {
        logger.info("PdfTemplate " + total);
        createTitle(writer, document);

    }

    private void createTitle(PdfWriter writer, Document document) {
        Rectangle rect = writer.getBoxSize("art");

   	 /*Rectangle corner=new Rectangle(rect.getRight()-120, rect.getTop()-30, rect.getRight()+20 , rect.getTop()+20);
        corner.setBackgroundColor(BaseColor.RED);*/

        PdfPTable table = new PdfPTable(1);

        try {

       	/* document.add(corner);*/
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_RIGHT, new Phrase("Date : " + new SimpleDateFormat("dd/MM/yyyy").format(new Date()), PdfTemplateGen.FONT[3]), rect.getRight(), rect.getTop(), 0);

            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_RIGHT, new Phrase("Page : " + writer.getPageNumber() + "     of     ", PdfTemplateGen.FONT[3]), rect.getRight() - 17, rect.getTop() - 18, 0);

            //table.setWidths(new Integer[]{2});
            table.setTotalWidth(527);
            table.setLockedWidth(true);
            table.getDefaultCell().setFixedHeight(18);

            PdfPCell cell = new PdfPCell(Image.getInstance(total));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setPadding(0);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

            table.addCell(cell);
            table.writeSelectedRows(0, -1, 50, 784, writer.getDirectContent());

        } catch (DocumentException de) {
            logger.error(de.getMessage(), de);
            throw new ExceptionConverter(de);
        }

        logger.info("rect " + rect);

        ColumnText.showTextAligned(writer.getDirectContent(),
                Element.ALIGN_CENTER, new Phrase(header[0], PdfTemplateGen.FONT[2]),
                (rect.getLeft() + rect.getRight()) / 2, rect.getTop(), 0);

        ColumnText.showTextAligned(writer.getDirectContent(),
                Element.ALIGN_CENTER, new Phrase(header[1], PdfTemplateGen.FONT[3]),
                (rect.getLeft() + rect.getRight()) / 2, rect.getTop() - 20, 0);

       
       /* Paragraph phrase=new Paragraph();
        phrase.setSpacingBefore(30);
        phrase.add(UNDERLINE);
        ColumnText.showTextAligned(writer.getDirectContent(),
                Element.ALIGN_CENTER, phrase,
                rect.getLeft(), rect.getBottom(), 0);*/


        Paragraph p = new Paragraph(Chunk.NEWLINE);
        p.add(Chunk.NEWLINE);
        p.add(Chunk.NEWLINE);
        p.add(UNDERLINE);
        p.setSpacingAfter(30);
        try {
            document.add(p);
        } catch (DocumentException e) {
            logger.error(e.getMessage(), e);
        }
    }

}

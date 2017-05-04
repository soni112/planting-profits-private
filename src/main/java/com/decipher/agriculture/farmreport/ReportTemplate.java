package com.decipher.agriculture.farmreport;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

/**
 * Created by Satan on 11/29/2015.
 */
public class ReportTemplate {

    public static final Font[] FONT = new Font[7];
    public static final Font TIMESROMAN_14_BOLD = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
    public static final Font TIMESROMAN_14_BOLD_RED = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD, BaseColor.RED);
    public static final Font TIMESROMAN_14_NORMAL = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL);
    public static final Font TIMESROMAN_12_NORMAL = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
    public static final Font TIMESROMAN_10_NORMAL = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
    public static final Font TIMESROMAN_10_BOLD = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
    public static final Font TIMESROMAN_12_BOLD = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
    public static final Font TIMESROMAN_8_NORMAL = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.NORMAL);
    public static final Font TIMESROMAN_8_BOLD = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLD);

    static {

        FONT[0] = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
        FONT[1] = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD);
        FONT[2] = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
        FONT[3] = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
        FONT[4] = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.NORMAL);
        FONT[5] = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
        FONT[6] = new Font(Font.FontFamily.HELVETICA, 9, Font.ITALIC);
    }

    public static class CellBuilder {
        public static PdfPCell getTableCell() {
            PdfPCell cell = new PdfPCell();
            return cell;
        }

        public static PdfPCell setBorder(PdfPCell cell, int border, float borderWidth, float left, float right, float top, float bottom, boolean useBorderPadding) {
            setBorder(cell, border, borderWidth, left, right, top, bottom);
            cell.setUseBorderPadding(useBorderPadding);
            return cell;
        }

        public static PdfPCell setBorder(PdfPCell cell, int border, float borderWidth, float left, float right, float top, float bottom) {
            setBorder(cell, border, borderWidth, left, right, top);
            cell.setBorderWidthBottom(bottom);
            return cell;
        }

        public static PdfPCell setBorder(PdfPCell cell, int border, float borderWidth, float left, float right, float top) {
            setBorder(cell, border, borderWidth, left, right);
            cell.setBorderWidthTop(top);
            return cell;
        }

        public static PdfPCell setBorder(PdfPCell cell, int border, float borderWidth, float left, float right) {
            setBorder(cell, border, borderWidth, left);
            cell.setBorderWidthRight(right);
            return cell;
        }

        public static PdfPCell setBorder(PdfPCell cell, int border, float borderWidth, float left) {
            setBorder(cell, border, borderWidth);
            cell.setBorderWidthLeft(left);
            return cell;
        }

        public static PdfPCell setBorder(PdfPCell cell, int border, float borderWidth) {
            setBorder(cell, border);
            cell.setBorderWidth(borderWidth);
            return cell;
        }

        public static PdfPCell setBorder(PdfPCell cell, int border) {
            cell.setBorder(border);
            return cell;
        }

    }

    public static class NoBorderNoHeaderTable {
        public static PdfPCell getDataCell(String text){
            PdfPCell cell = new PdfPCell(new Phrase(text, ReportTemplate.TIMESROMAN_8_NORMAL));
            cell.setUseBorderPadding(false);
            cell.setBorderWidth(0);
            return cell;
        }

        public static PdfPCell getEmptyCell(){
            PdfPCell cell = new PdfPCell();
            cell.setUseBorderPadding(false);
            cell.setBorderWidth(0);
            return cell;
        }
    }

    public static class BoldHeaderBottomBorderTable {
        public static PdfPCell getHeaderCell(String text){
            PdfPCell cell = new PdfPCell(new Phrase(text, ReportTemplate.TIMESROMAN_8_BOLD));
            cell.setUseBorderPadding(true);
            cell.setBorderWidth(0);
            cell.setBorderWidthBottom(1);
            return cell;
        }

        public static PdfPCell getDataCell(String text){
            PdfPCell cell = new PdfPCell(new Phrase(text, ReportTemplate.TIMESROMAN_8_NORMAL));
            cell.setUseBorderPadding(true);
            cell.setBorderWidth(0);
            return cell;
        }
    }

    public static class BoldHeaderBoxBorderTable{
        public static PdfPCell getHeaderCell(String text){
            PdfPCell cell = new PdfPCell(new Phrase(text, ReportTemplate.TIMESROMAN_8_BOLD));
            cell.setUseBorderPadding(true);
            cell.setBorderWidth(1);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cell.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOX);
            return cell;
        }

        public static PdfPCell getDataCell(String text){
            PdfPCell cell = new PdfPCell(new Phrase(text, ReportTemplate.TIMESROMAN_8_NORMAL));
            cell.setUseBorderPadding(true);
            cell.setBorderWidth(1);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cell.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOX);
            return cell;
        }

        /**
         * @Added - Abhishek
         * @date - 12-1-2016
         * @desc - Blank cell for table
         */
        public static PdfPCell getBlankCell(){
            PdfPCell cell = new PdfPCell(new Phrase("", ReportTemplate.TIMESROMAN_8_NORMAL));
            cell.setUseBorderPadding(true);
            cell.setBorderWidth(1);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cell.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOX);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            return cell;
        }
    }

    public static PdfPCell getFooterCellSmallNoBorder(String text){
        PdfPCell cell = new PdfPCell(new Phrase(text, TIMESROMAN_8_NORMAL));
        cell.setPaddingTop(5);
        cell.setPaddingBottom(5);
        cell.setUseBorderPadding(true);
        cell.setBorderWidth(0);
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }


    public static PdfPTable getFullWidthTable(int column){
        PdfPTable table = new PdfPTable(column);
        table.setWidthPercentage(100);
        return table;
    }

    public static PdfPCell getSectionHeaderCell(String text){
        Phrase sectionHeaderPara = new Phrase(text, ReportTemplate.TIMESROMAN_14_BOLD);
        PdfPCell sectionHeaderCell = new PdfPCell(sectionHeaderPara);
        sectionHeaderCell.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
        sectionHeaderCell.setUseBorderPadding(true);
        sectionHeaderCell.setBorderWidth(1);
        sectionHeaderCell.setBorder(Rectangle.BOX);
        sectionHeaderCell.setBackgroundColor(new BaseColor(193, 183, 157));
        return sectionHeaderCell;
    }

    public static PdfPCell getBoxBorderCell(){
        PdfPCell cell = getBoxBorderWithoutLeftPaddingCell();
        cell.setUseBorderPadding(true);
        return cell;
    }

    public static PdfPCell getBoxBorderWithoutLeftPaddingCell(){
        PdfPCell cell = new PdfPCell();
        cell.setUseBorderPadding(true);
        cell.setBorderWidth(1);
        cell.setBorder(Rectangle.BOX);
        return cell;
    }

    public static PdfPCell getSectionSubHeaderCellWithBoxBorder(String text){
        Phrase sectionSubHeaderPara = new Phrase(text, ReportTemplate.TIMESROMAN_14_BOLD);
        PdfPCell sectionSubHeaderCell = new PdfPCell(sectionSubHeaderPara);
        sectionSubHeaderCell.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
        sectionSubHeaderCell.setUseBorderPadding(true);
        sectionSubHeaderCell.setBorderWidth(1);
        sectionSubHeaderCell.setBorder(Rectangle.BOX);
        return sectionSubHeaderCell;
    }

    public static PdfPCell getSectionSubHeaderCellWithoutBorder(String text){
        Phrase sectionSubHeaderPara = new Phrase(text, ReportTemplate.TIMESROMAN_14_BOLD);
        PdfPCell sectionSubHeaderCell = new PdfPCell(sectionSubHeaderPara);
        sectionSubHeaderCell.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
        sectionSubHeaderCell.setUseBorderPadding(true);
        sectionSubHeaderCell.setBorderWidth(0);
        sectionSubHeaderCell.setBorder(Rectangle.NO_BORDER);
        return sectionSubHeaderCell;
    }

    public static PdfPCell getSectionSubHeaderCellWithoutBorderSmall(String text){
        Phrase sectionSubHeaderPara = new Phrase(text, ReportTemplate.TIMESROMAN_10_BOLD);
        PdfPCell sectionSubHeaderCell = new PdfPCell(sectionSubHeaderPara);
        sectionSubHeaderCell.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
        sectionSubHeaderCell.setUseBorderPadding(true);
        sectionSubHeaderCell.setBorderWidth(0);
        sectionSubHeaderCell.setBorder(Rectangle.NO_BORDER);
        return sectionSubHeaderCell;
    }


    public static Chunk getNewLineChunk() {
        return new Chunk("\n");
    }

}

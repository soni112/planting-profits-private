package com.decipher.agriculture.farmreport;


import com.decipher.agriculture.AppConstants;
import com.decipher.agriculture.data.account.AccountDocuments;
import com.decipher.agriculture.data.account.AccountDocumentsType;
import com.decipher.agriculture.data.account.AppRole;
import com.decipher.agriculture.service.account.AccountService;
import com.decipher.util.AgricultureStandard;
import com.decipher.util.PlantingProfitLogger;
import com.decipher.util.listner.SpringApplicationContextListener;
import com.decipher.view.form.account.AccountView;
import com.decipher.view.form.farmDetails.FarmInfoView;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.codehaus.jettison.json.JSONException;
import org.springframework.context.ApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Created by Satan on 11/29/2015.
 */
public class FarmReportGenerator extends AbstractItextPdfView {

    private SectionOnePDFGenerator sectionOnePDFGenerator;
    private SectionTwoPDFGenerator sectionTwoPDFGenerator;
    private SectionThreePDFGenerator sectionThreePDFGenerator;
    private AccountView accountView;

    private void loadData(Map<String, Object> model) throws JSONException {
        if (model == null)
            return;
        /**
         * @changed - Abhishek
         * @date - 12-12-2015
         * @update - 11-01-2016
         */
        this.accountView = (AccountView) model.get(AppConstants.ACCOUNT_VIEW);

        sectionTwoPDFGenerator = new SectionTwoPDFGenerator((ReportDataPage2) model.get(AppConstants.REPORT_DATA_2),
                (FarmInfoView) model.get(AppConstants.BASE_FARM_INFO_REPORT));

        sectionOnePDFGenerator = new SectionOnePDFGenerator(sectionTwoPDFGenerator,
                (AccountView) model.get(AppConstants.ACCOUNT_VIEW),
                (FarmInfoView) model.get(AppConstants.BASE_FARM_INFO_REPORT),
                (ReportDataPage1) model.get(AppConstants.REPORT_DATA_1),
                (String) model.get(AppConstants.SELECTED_STRATEGY_NAME),
                (String) model.get(AppConstants.ESTIMATED_INCOME));

        sectionThreePDFGenerator = new SectionThreePDFGenerator((String) model.get(AppConstants.SELECTED_STRATEGY_NAME),
                (ReportDataPage3) model.get(AppConstants.REPORT_DATA_3),
                (FarmInfoView) model.get(AppConstants.BASE_FARM_INFO_REPORT));
    }

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
                                    HttpServletRequest request, HttpServletResponse response) throws Exception {
        PlantingProfitLogger.info("model = " + model);
        loadData(model);
        Rectangle rect = new Rectangle(36, 54, 559, 788);
        rect.setBorder(Rectangle.BOTTOM);
        writer.setBoxSize("art", rect);
        String filePath = request.getServletContext().getRealPath("/images/logo_pdf.png");

        ApplicationContext applicationContext = SpringApplicationContextListener.getApplicationContext();

        AccountService accountService = applicationContext.getBean(AccountService.class);

        if (Objects.equals(accountView.getRole(), AppRole.ROLE_PROFESSIONAL)) {
            Set<AccountDocuments> userDocumentList = accountService.getUserDocuments(accountView.getAccount());
            for (AccountDocuments userDocument : userDocumentList) {

                if (Objects.equals(userDocument.getAccountDocumentsType(), AccountDocumentsType.LOGO)) {
                    filePath = System.getProperty("user.home") + "/Planting_Profit_Data/UserData/" + userDocument.getDocumentHolder().getId() + "/logo/" + userDocument.getDocumentPath();
                }
            }
        }

        writer.setPageEvent(new FarmReportPageEvent(this, filePath));
        document.open();
        document.addAuthor("Agriculture-Application");
        sectionOnePDFGenerator.buildSectionOne(document);
        sectionTwoPDFGenerator.buildSectionTwo(document,/*, request.getServletContext().getRealPath("/jugaad.png")*/writer); // <- juggad.png will be parallel to WEB-INF
        sectionThreePDFGenerator.buildSectionThree(document);

        response.setHeader("Content-disposition", "attachment; filename=Planting Profits Farm Report.pdf");
    }

    private class FarmReportPageEvent extends PdfPageEventHelper {

        private String logoImagePath;
        private Image logoImage;
        private FarmReportGenerator parentInstance;
        /**
         * The template with the total number of pages.
         */
        PdfTemplate total;

        public FarmReportGenerator getParentInstance() {
            return parentInstance;
        }

        @Override
        public void onOpenDocument(PdfWriter writer, Document document) {
            total = writer.getDirectContent().createTemplate(30, 16);
        }

        private void setParentInstance(FarmReportGenerator parentInstance) {
            this.parentInstance = parentInstance;
        }

        private Image getLogoImage() {
            return logoImage;
        }

        private FarmReportPageEvent(FarmReportGenerator parent, String logoImagePath) {
            this.logoImagePath = logoImagePath;
            setLogoImage();
            setParentInstance(parent);
        }

        private void setLogoImage() {
            try {
                logoImage = Image.getInstance(logoImagePath);
            } catch (BadElementException | IOException e) {
                PlantingProfitLogger.error(e);
            }
        }

        @Override
        public void onStartPage(PdfWriter writer, Document document) {
            //Rectangle rect = writer.getBoxSize("art");

            //ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_RIGHT, new Phrase("Page : "+writer.getPageNumber()+" of ",ReportTemplate.TIMESROMAN_12_NORMAL), rect.getRight()-17, rect.getTop()-18, 0);

            PdfPTable headerTable = new PdfPTable(3);
            headerTable.setWidthPercentage(100);

            // Add First Row to header Table

            PdfPCell imageCell = new PdfPCell(getLogoImage());
            imageCell.setBorderWidth(1);
            imageCell.setFixedHeight(60);
            imageCell.setPaddingLeft(10);
            imageCell.setUseBorderPadding(true);
            imageCell.setBackgroundColor(new BaseColor(193, 183, 157));
            imageCell.setBorderWidthRight(0.0f);
            headerTable.addCell(imageCell);


            Paragraph productionPlanPara = new Paragraph("Production Plan", ReportTemplate.FONT[1]);
            productionPlanPara.setAlignment(Paragraph.ALIGN_CENTER);
            PdfPCell nameCell = new PdfPCell(productionPlanPara);
            nameCell.setBorderWidth(1);
            nameCell.setVerticalAlignment(Element.ALIGN_CENTER);
            nameCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            nameCell.setUseBorderPadding(true);
            nameCell.setBackgroundColor(new BaseColor(193, 183, 157));
            nameCell.setBorderWidthLeft(0.0f);
            nameCell.setPaddingTop(10);
            headerTable.addCell(nameCell);

//            Paragraph sponsorText = new Paragraph("Sponsor Branding or Professional's Reference and Certification", ReportTemplate.FONT[6]);
            Paragraph sponsorText = new Paragraph("", ReportTemplate.FONT[6]);
            sponsorText.setAlignment(Element.ALIGN_CENTER);
            PdfPCell sponsorCell = new PdfPCell(sponsorText);
            sponsorCell.setVerticalAlignment(Element.ALIGN_CENTER);
            sponsorCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            sponsorCell.setBackgroundColor(new BaseColor(193, 183, 157));
            sponsorCell.setBorderWidth(1);
            sponsorCell.setUseBorderPadding(true);
            sponsorCell.setBorderWidthLeft(0.0f);
            headerTable.addCell(sponsorCell);

            headerTable.completeRow();

            // First Row is added, now add second row to header table
            Paragraph accountInfoPara = new Paragraph();
            accountInfoPara.setFont(ReportTemplate.FONT[5]);
            Chunk accountName = new Chunk(parentInstance.sectionOnePDFGenerator.getAccountView().getFirstName() + " " + parentInstance.sectionOnePDFGenerator.getAccountView().getLastName(), ReportTemplate.FONT[5]);
            accountInfoPara.add(accountName);
            accountInfoPara.add(ReportTemplate.getNewLineChunk());
            StringBuilder address = new StringBuilder();
            if (parentInstance.sectionOnePDFGenerator.getAccountView().getPhysical_Address_Line_1() != null) {
                address.append(parentInstance.sectionOnePDFGenerator.getAccountView().getPhysical_Address_Line_1() + "\n");
            }
            if (parentInstance.sectionOnePDFGenerator.getAccountView().getPhysical_Address_City() != null) {
                address.append(parentInstance.sectionOnePDFGenerator.getAccountView().getPhysical_Address_City());
            }
            if (parentInstance.sectionOnePDFGenerator.getAccountView().getPhysical_Address_Zip() != null) {
                if (parentInstance.sectionOnePDFGenerator.getAccountView().getPhysical_Address_City() != null) {
                    address.append("-");
                }
                address.append(parentInstance.sectionOnePDFGenerator.getAccountView().getMailing_Address_Zip());
            }

            Chunk accountAddress = new Chunk(address.toString());
            accountInfoPara.add(accountAddress);
            accountInfoPara.add(ReportTemplate.getNewLineChunk());
            String phone_no = parentInstance.sectionOnePDFGenerator.getAccountView().getPhone_No();
            Chunk accountPhoneNo = new Chunk(phone_no == null ? "" : phone_no);
            accountInfoPara.add(accountPhoneNo);
            accountInfoPara.add(ReportTemplate.getNewLineChunk());
            Chunk accountEmail = new Chunk(parentInstance.sectionOnePDFGenerator.getAccountView().getEmail_Address());
            accountInfoPara.add(accountEmail);
            PdfPCell accountInfoCell = new PdfPCell(accountInfoPara);
            accountInfoCell.setBorderWidth(1);
            accountInfoCell.setBorderWidthTop(0);
            accountInfoCell.setUseBorderPadding(true);
            accountInfoCell.setRowspan(2);
            accountInfoCell.setMinimumHeight(35);
            headerTable.addCell(accountInfoCell);


            Chunk farmName = new Chunk(parentInstance.sectionOnePDFGenerator.getFarmInfoView().getFarmName(), ReportTemplate.TIMESROMAN_14_BOLD);
            Paragraph farmInfoPara = new Paragraph();
            farmInfoPara.setAlignment(Paragraph.ALIGN_CENTER);
            farmInfoPara.add(farmName);
            StringBuilder farmAddressString = new StringBuilder();
            if (parentInstance.sectionOnePDFGenerator.getFarmInfoView().getPhysicalLocation() != null)
                farmAddressString.append(parentInstance.sectionOnePDFGenerator.getFarmInfoView().getPhysicalLocation());

            Chunk farmAddress = new Chunk(farmAddressString.toString(), ReportTemplate.TIMESROMAN_12_NORMAL);
            farmInfoPara.add(ReportTemplate.getNewLineChunk());
            farmInfoPara.add(farmAddress);

            PdfPCell farmInfoCell = new PdfPCell(farmInfoPara);
            farmInfoCell.setBorderWidth(0);
            farmInfoCell.setUseBorderPadding(true);
            farmInfoCell.setBorderWidthRight(1);
            farmInfoCell.setBorderWidthBottom(1);
            farmInfoCell.setRowspan(2);
            farmInfoCell.setMinimumHeight(35);
            farmInfoCell.setVerticalAlignment(Element.ALIGN_CENTER);
            farmInfoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerTable.addCell(farmInfoCell);

            Chunk datePrinted = new Chunk("Date Printed\n", ReportTemplate.TIMESROMAN_14_BOLD);
            Chunk date = new Chunk(AgricultureStandard.FORMATTER.format(Calendar.getInstance().getTime()), ReportTemplate.TIMESROMAN_12_NORMAL);

            Paragraph datePara = new Paragraph();
            datePara.setAlignment(Paragraph.ALIGN_CENTER);
            datePara.add(datePrinted);
            datePara.add(date);
            PdfPCell dateCell = new PdfPCell(datePara);
            dateCell.setBorderWidth(0);
            dateCell.setBorderWidthRight(1);
            dateCell.setUseBorderPadding(true);
            dateCell.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
            dateCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            dateCell.setMinimumHeight(35);
            headerTable.addCell(dateCell);

            /**
             * @changed - Abhishek
             * @date - 16-01-2016
             * @desc - added dynamic page number functionality
             */
            /*Paragraph pagePara = new Paragraph();

            pagePara.add(new Chunk("Page 1 of #", ReportTemplate.TIMESROMAN_12_NORMAL));*//*

            pagePara.add(new Chunk("Page " + writer.getPageNumber() + " of ", ReportTemplate.TIMESROMAN_12_NORMAL));

            pagePara.setAlignment(Paragraph.ALIGN_CENTER);
            PdfPCell pageCell = new PdfPCell(pagePara);
            pageCell.setBorderWidth(0);
            pageCell.setBorderWidthTop(1);
            pageCell.setBorderWidthRight(1);
            pageCell.setBorderWidthBottom(1);
            pageCell.setUseBorderPadding(true);
            pageCell.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
            pageCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            pageCell.setMinimumHeight(35);*/
            /**
             * @changed - Abhishek
             * @date - 16-01-2016
             * @desc - added dynamic page number functionality
             */
            PdfPTable pageNumberTable = new PdfPTable(2);
            pageNumberTable.setWidthPercentage(100);
            try {
                pageNumberTable.setWidths(new int[]{60, 40});
            } catch (DocumentException e) {
                e.printStackTrace();
            }

            PdfPCell currentPageCell = new PdfPCell(new Phrase("Page " + writer.getPageNumber() + " of "));
            currentPageCell.setBorder(0);
            /*currentPageCell.setBorderWidthBottom(1);*/
            currentPageCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            pageNumberTable.addCell(currentPageCell);
            try {
                PdfPCell totalPageNumberCell = new PdfPCell(Image.getInstance(total));
                totalPageNumberCell.setBorder(0);
                /*totalPageNumberCell.setBorderWidthRight(1);
                totalPageNumberCell.setBorderWidthBottom(1);*/
                totalPageNumberCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                pageNumberTable.addCell(totalPageNumberCell);
            } catch (BadElementException e) {
                e.printStackTrace();
            }
            pageNumberTable.completeRow();

            headerTable.addCell(pageNumberTable);


            /*headerTable.addCell(pageCell);*/
            headerTable.completeRow();

            try {
                document.add(headerTable);
            } catch (DocumentException e) {
                PlantingProfitLogger.error(e);
            }
        }

        @Override
        public void onCloseDocument(PdfWriter writer, Document document) {
//            ColumnText.showTextAligned(total, Element.ALIGN_RIGHT, new Phrase(String.valueOf(writer.getPageNumber() - 1),ReportTemplate.TIMESROMAN_8_NORMAL), 4, 2, 0);
            ColumnText.showTextAligned(total, Element.ALIGN_LEFT,
                    new Phrase(String.valueOf(writer.getPageNumber() - 1)),
                    2, 2, 0);
        }

    }
}

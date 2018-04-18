package com.decipher.agriculture.farmreport;

import com.decipher.agriculture.data.farm.PlanByStrategy;
import com.decipher.view.form.farmDetails.CropFieldChociesView;
import com.decipher.view.form.farmDetails.CropTypeView;
import com.decipher.view.form.farmDetails.CropsGroupView;
import com.decipher.view.form.farmDetails.FarmInfoView;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;
import java.util.List;

/**
 * Created by Satan on 11/30/2015.
 */
public class SectionThreePDFGenerator {

    public String getSelectedStrategy() {
        return selectedStrategy;
    }

    public SectionThreePDFGenerator(String selectedStrategy, ReportDataPage3 reportDataPage3, FarmInfoView farmInfoView) {
        this.selectedStrategy = selectedStrategy;
        this.reportDataPage3 = reportDataPage3;
        this.farmInfoView = farmInfoView;
    }

    public void setSelectedStrategy(String selectedStrategy) {
        this.selectedStrategy = selectedStrategy;
    }

    private String selectedStrategy;
    private ReportDataPage3 reportDataPage3;
    private FarmInfoView farmInfoView;

    public FarmInfoView getFarmInfoView() {
        return farmInfoView;
    }

    public void setFarmInfoView(FarmInfoView farmInfoView) {
        this.farmInfoView = farmInfoView;
    }

    public ReportDataPage3 getReportDataPage3() {
        return reportDataPage3;
    }

    public void setReportDataPage3(ReportDataPage3 reportDataPage3) {
        this.reportDataPage3 = reportDataPage3;
    }

    public void buildSectionThree(Document document) throws DocumentException {

        if (farmInfoView.getStrategy() != PlanByStrategy.PLAN_BY_FIELDS) {
            return;
        }

        document.newPage();

        PdfPTable titleTable = new PdfPTable(1);
        titleTable.setWidthPercentage(100);

        // Strategy
        titleTable.addCell(ReportTemplate.getSectionHeaderCell("Exhibit 1 - Crop Field Assignments for " + selectedStrategy));

        // Estimated Income
//        Chunk potentialProfitText = new Chunk("Estimated Income: ", ReportTemplate.TIMESROMAN_14_BOLD_RED);
        Chunk potentialProfitText = new Chunk("Estimated Income: ", ReportTemplate.TIMESROMAN_14_BOLD);

        /**
         * @changed - Abhishek
         * @date - 11-01-2016
         * @desc - getting data from objects instead of direct calculation of output
         */
        /*Chunk potentialProfit = new Chunk("$" + reportDataPage3.getPotentialProfit(farmInfoView), ReportTemplate.TIMESROMAN_14_NORMAL);*/
        Chunk potentialProfit = new Chunk("$" + SectionOnePDFGenerator.getEstimatedIncome(), ReportTemplate.TIMESROMAN_14_NORMAL);

        Paragraph potentialProfitPara = new Paragraph();
        potentialProfitPara.add(potentialProfitText);
        potentialProfitPara.add(potentialProfit);
        PdfPCell potentialProfitCell = new PdfPCell(potentialProfitPara);
        potentialProfitCell.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
        potentialProfitCell.setUseBorderPadding(true);
        potentialProfitCell.setBorderWidth(0);
        potentialProfitCell.setBorder(Rectangle.NO_BORDER);
        titleTable.addCell(potentialProfitCell);

        // Create  CropField Assignment Table
        PdfPCell cropFieldTableCell = new PdfPCell();
        cropFieldTableCell.addElement(getCropFieldAssignmentTable());
        cropFieldTableCell.setUseBorderPadding(true);
        cropFieldTableCell.setBorderWidth(0);
        cropFieldTableCell.setPaddingTop(5);
        cropFieldTableCell.setPaddingBottom(10);
        titleTable.addCell(cropFieldTableCell);

        // Strategy
        titleTable.addCell(ReportTemplate.getSectionHeaderCell("Exhibit 2 - Crop Field Choices for " + selectedStrategy));

        // Create  CropField Choice Table
        PdfPCell cropFieldChoiceTableCell = new PdfPCell();
        cropFieldChoiceTableCell.addElement(getCropFieldChoicesTable());
        cropFieldChoiceTableCell.setUseBorderPadding(true);
        cropFieldChoiceTableCell.setBorderWidth(0);
        cropFieldChoiceTableCell.setPaddingTop(5);
        cropFieldChoiceTableCell.setPaddingBottom(10);
        titleTable.addCell(cropFieldChoiceTableCell);

        /**
         * @Added - Abhishek
         * @date - 16-01-2016
         * @desc - Added table for crop acreage limits
         */
        // Strategy
        titleTable.addCell(ReportTemplate.getSectionHeaderCell("Crop Acreage Limits for " + selectedStrategy));

        // Create  Crop Acreage Limits Table
        PdfPCell cropAcreageLimitsTableCell = new PdfPCell();
        cropAcreageLimitsTableCell.addElement(getCropAcreageLimitsTable());
        cropAcreageLimitsTableCell.setUseBorderPadding(true);
        cropAcreageLimitsTableCell.setBorderWidth(0);
        cropAcreageLimitsTableCell.setPaddingTop(5);
        cropAcreageLimitsTableCell.setPaddingBottom(10);
        titleTable.addCell(cropAcreageLimitsTableCell);

        document.add(titleTable);
    }

    private PdfPTable getCropFieldChoicesTable() {
        /**
         * @changed - Abhishek
         * @date - 16-01-2016
         * @desc - updated according to updated syntax of reportDataPage3.getCropFieldAssignments()
         */
        /*Object[] objects = reportDataPage3.getCropFieldChoice(farmInfoView);*/
        Object[] objects = reportDataPage3.getCropFieldChoice();
        List<CropTypeView> cropsHeaderList = (List<CropTypeView>) objects[0];
        Map<String, Object> dataMap = (Map<String, Object>) objects[1];

        PdfPTable table = new PdfPTable(cropsHeaderList.size() + 1);
        table.setWidthPercentage(100);

        // Create Headers
        PdfPCell fieldHeaderCell = new PdfPCell(new Phrase("Field/Crop", ReportTemplate.TIMESROMAN_10_NORMAL));
        fieldHeaderCell.setBorderWidth(0.5f);
        fieldHeaderCell.setBorderColor(new BaseColor(131, 154, 103));
        fieldHeaderCell.setBorder(Rectangle.BOX);
        fieldHeaderCell.setUseBorderPadding(true);
        fieldHeaderCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        fieldHeaderCell.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
        fieldHeaderCell.setBackgroundColor(new BaseColor(247, 205, 114));
        table.addCell(fieldHeaderCell);

        for(CropTypeView ctv : cropsHeaderList){
            PdfPCell cropHeaderCell = new PdfPCell(new Phrase(ctv.getCropName(), ReportTemplate.TIMESROMAN_10_NORMAL));
            cropHeaderCell.setBorderWidth(0.5f);
            cropHeaderCell.setBorderColor(new BaseColor(131, 154, 103));
            cropHeaderCell.setBorder(Rectangle.BOX);
            cropHeaderCell.setUseBorderPadding(true);
            cropHeaderCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cropHeaderCell.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
            cropHeaderCell.setBackgroundColor(new BaseColor(247, 205, 114));
            table.addCell(cropHeaderCell);
        }
        table.completeRow();

        // Create Data
        for(String fieldName : dataMap.keySet()){
            PdfPCell fieldCell = new PdfPCell(new Phrase(fieldName, ReportTemplate.TIMESROMAN_10_NORMAL));
            fieldCell.setBorderWidth(0.5f);
            fieldCell.setBorderColor(new BaseColor(131, 154, 103));
            fieldCell.setBorder(Rectangle.BOX);
            fieldCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            fieldCell.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
            fieldCell.setUseBorderPadding(true);
            fieldCell.setBackgroundColor(new BaseColor(247, 205, 114));
            table.addCell(fieldCell);

            // Add Dynamic Data Dynamic Rows
            List<CropFieldChociesView> cropsDataList = (List<CropFieldChociesView>) dataMap.get(fieldName);
            for(CropFieldChociesView c : cropsDataList){
                PdfPCell dataCell = new PdfPCell(new Phrase(c.getCropFieldChoice().equals("true") ? "X" : "", ReportTemplate.TIMESROMAN_10_NORMAL));
                dataCell.setBorderWidth(0.5f);
                dataCell.setBorderColor(new BaseColor(131, 154, 103));
                dataCell.setBorder(Rectangle.BOX);
                dataCell.setUseBorderPadding(true);
                dataCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                dataCell.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
                dataCell.setBackgroundColor(new BaseColor(223, 240, 216));
                table.addCell(dataCell);
            }

            table.completeRow();
        }

        return table;
    }

    private PdfPTable getCropFieldAssignmentTable() {
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);

        // Create Header Cells Field, Acreage, Crop
        PdfPCell fieldHeaderCell = new PdfPCell(new Phrase("Field", ReportTemplate.TIMESROMAN_10_NORMAL));
        fieldHeaderCell.setBorderWidth(0.5f);
        fieldHeaderCell.setBorderColor(new BaseColor(131, 154, 103));
        fieldHeaderCell.setBorder(Rectangle.BOX);
        fieldHeaderCell.setUseBorderPadding(true);
        fieldHeaderCell.setBackgroundColor(new BaseColor(247, 205, 114));
        table.addCell(fieldHeaderCell);

        PdfPCell acreageCell = new PdfPCell(new Phrase("Acreage", ReportTemplate.TIMESROMAN_10_NORMAL));
        acreageCell.setBorderWidth(0.5f);
        acreageCell.setBorderColor(new BaseColor(131, 154, 103));
        acreageCell.setBorder(Rectangle.BOX);
        acreageCell.setUseBorderPadding(true);
        acreageCell.setBackgroundColor(new BaseColor(247, 205, 114));
        table.addCell(acreageCell);

        PdfPCell cropCell = new PdfPCell(new Phrase("Crop", ReportTemplate.TIMESROMAN_10_NORMAL));
        cropCell.setBorderWidth(0.5f);
        cropCell.setBorderColor(new BaseColor(131, 154, 103));
        cropCell.setBorder(Rectangle.BOX);
        cropCell.setUseBorderPadding(true);
        cropCell.setBackgroundColor(new BaseColor(247, 205, 114));
        table.addCell(cropCell);

        table.completeRow();

        /**
         * @changed - Abhishek
         * @date - 16-01-2016
         * @desc - updated according to updated syntax of reportDataPage3.getCropFieldAssignments()
         */
        // Create table data dynamically
        /*List<Map<String, Object>> outputDetailsForFarm = reportDataPage3.getCropFieldAssignments(farmInfoView);*/
        List<Map<String, Object>> outputDetailsForFarm = reportDataPage3.getCropFieldAssignments();
        for (int i = 0; i < outputDetailsForFarm.size(); i++) {
            PdfPCell fieldNameDataCell = new PdfPCell(new Phrase("" + outputDetailsForFarm.get(i).get("field"), ReportTemplate.TIMESROMAN_10_NORMAL));
            fieldNameDataCell.setBorderWidth(0.5f);
            fieldNameDataCell.setBorderColor(new BaseColor(131, 154, 103));
            fieldNameDataCell.setBorder(Rectangle.BOX);
            fieldNameDataCell.setUseBorderPadding(true);
            fieldNameDataCell.setBackgroundColor(new BaseColor(223, 240, 216));
            table.addCell(fieldNameDataCell);

            PdfPCell acreageDataCell = new PdfPCell(new Phrase("" + outputDetailsForFarm.get(i).get("size"), ReportTemplate.TIMESROMAN_10_NORMAL));
            acreageDataCell.setBorderWidth(0.5f);
            acreageDataCell.setBorderColor(new BaseColor(131, 154, 103));
            acreageDataCell.setBorder(Rectangle.BOX);
            acreageDataCell.setUseBorderPadding(true);
            acreageDataCell.setBackgroundColor(new BaseColor(223, 240, 216));
            table.addCell(acreageDataCell);

            PdfPCell cropDataCell = new PdfPCell(new Phrase("" + outputDetailsForFarm.get(i).get("crop"), ReportTemplate.TIMESROMAN_10_NORMAL));
            cropDataCell.setBorderWidth(0.5f);
            cropDataCell.setBorderColor(new BaseColor(131, 154, 103));
            cropDataCell.setBorder(Rectangle.BOX);
            cropDataCell.setUseBorderPadding(true);
            cropDataCell.setBackgroundColor(new BaseColor(223, 240, 216));
            table.addCell(cropDataCell);

            table.completeRow();
        }

        return table;
    }

    /**
     * @Added - Abhishek
     * @date - 16-01-2016
     * @desc - getting table for crop acreage limits table
     */
    private PdfPTable getCropAcreageLimitsTable(){
        PdfPTable table;
        if (farmInfoView.getStrategy().equals(PlanByStrategy.PLAN_BY_ACRES)){
            table = new PdfPTable(6);
        } else {
            table = new PdfPTable(4);
        }

        table.setWidthPercentage(100);

        // Create Header Cells Field, Acreage, Crop
        PdfPCell fieldHeaderCell = new PdfPCell(new Phrase("Crop", ReportTemplate.TIMESROMAN_10_NORMAL));
        fieldHeaderCell.setBorderWidth(0.5f);
        fieldHeaderCell.setBorderColor(new BaseColor(131, 154, 103));
        fieldHeaderCell.setBorder(Rectangle.BOX);
        fieldHeaderCell.setUseBorderPadding(true);
        fieldHeaderCell.setBackgroundColor(new BaseColor(247, 205, 114));
        table.addCell(fieldHeaderCell);

        PdfPCell maxLimit = new PdfPCell(new Phrase("Max Limit", ReportTemplate.TIMESROMAN_10_NORMAL));
        maxLimit.setBorderWidth(0.5f);
        maxLimit.setBorderColor(new BaseColor(131, 154, 103));
        maxLimit.setBorder(Rectangle.BOX);
        maxLimit.setUseBorderPadding(true);
        maxLimit.setBackgroundColor(new BaseColor(247, 205, 114));
        table.addCell(maxLimit);

        PdfPCell minLimit = new PdfPCell(new Phrase("Min Limit", ReportTemplate.TIMESROMAN_10_NORMAL));
        minLimit.setBorderWidth(0.5f);
        minLimit.setBorderColor(new BaseColor(131, 154, 103));
        minLimit.setBorder(Rectangle.BOX);
        minLimit.setUseBorderPadding(true);
        minLimit.setBackgroundColor(new BaseColor(247, 205, 114));
        table.addCell(minLimit);

        if (farmInfoView.getStrategy().equals(PlanByStrategy.PLAN_BY_ACRES)){
            PdfPCell impactingIncome = new PdfPCell(new Phrase("Impacting Income", ReportTemplate.TIMESROMAN_10_NORMAL));
            impactingIncome.setBorderWidth(0.5f);
            impactingIncome.setBorderColor(new BaseColor(131, 154, 103));
            impactingIncome.setBorder(Rectangle.BOX);
            impactingIncome.setUseBorderPadding(true);
            impactingIncome.setBackgroundColor(new BaseColor(247, 205, 114));
            table.addCell(impactingIncome);

            PdfPCell toIncreaseIncome = new PdfPCell(new Phrase("To Increase Income", ReportTemplate.TIMESROMAN_10_NORMAL));
            toIncreaseIncome.setBorderWidth(0.5f);
            toIncreaseIncome.setBorderColor(new BaseColor(131, 154, 103));
            toIncreaseIncome.setBorder(Rectangle.BOX);
            toIncreaseIncome.setUseBorderPadding(true);
            toIncreaseIncome.setBackgroundColor(new BaseColor(247, 205, 114));
            table.addCell(toIncreaseIncome);
        }

        PdfPCell cropCell = new PdfPCell(new Phrase("Acreage Assigned       ", ReportTemplate.TIMESROMAN_10_NORMAL));
        cropCell.setBorderWidth(0.5f);
        cropCell.setBorderColor(new BaseColor(131, 154, 103));
        cropCell.setBorder(Rectangle.BOX);
        cropCell.setUseBorderPadding(true);
        cropCell.setBackgroundColor(new BaseColor(247, 205, 114));

        table.addCell(cropCell);

        table.completeRow();

        JSONArray cropLimitContentArray = reportDataPage3.getCropLimitContent();

        for (Object o : cropLimitContentArray) {
            JSONObject cropLimitDetails = (JSONObject) o;

            PdfPCell cropNameDataCell = new PdfPCell(new Phrase(cropLimitDetails.get("cropName").toString(), ReportTemplate.TIMESROMAN_10_NORMAL));
            cropNameDataCell.setBorderWidth(0.5f);
            cropNameDataCell.setBorderColor(new BaseColor(131, 154, 103));
            cropNameDataCell.setBorder(Rectangle.BOX);
            cropNameDataCell.setUseBorderPadding(true);
            cropNameDataCell.setBackgroundColor(new BaseColor(223, 240, 216));
            table.addCell(cropNameDataCell);


            PdfPCell cropMinLimitsDataCell = new PdfPCell(new Phrase(cropLimitDetails.get("minLimit").toString(), ReportTemplate.TIMESROMAN_10_NORMAL));
            cropMinLimitsDataCell.setBorderWidth(0.5f);
            cropMinLimitsDataCell.setBorderColor(new BaseColor(131, 154, 103));
            cropMinLimitsDataCell.setBorder(Rectangle.BOX);
            cropMinLimitsDataCell.setUseBorderPadding(true);
            cropMinLimitsDataCell.setBackgroundColor(new BaseColor(223, 240, 216));
            table.addCell(cropMinLimitsDataCell);

            PdfPCell cropMaxLimitsDataCell = new PdfPCell(new Phrase(cropLimitDetails.get("maxLimit").toString(), ReportTemplate.TIMESROMAN_10_NORMAL));
            cropMaxLimitsDataCell.setBorderWidth(0.5f);
            cropMaxLimitsDataCell.setBorderColor(new BaseColor(131, 154, 103));
            cropMaxLimitsDataCell.setBorder(Rectangle.BOX);
            cropMaxLimitsDataCell.setUseBorderPadding(true);
            cropMaxLimitsDataCell.setBackgroundColor(new BaseColor(223, 240, 216));
            table.addCell(cropMaxLimitsDataCell);

            if (farmInfoView.getStrategy().equals(PlanByStrategy.PLAN_BY_ACRES)){
                PdfPCell impactingProfitDataCell = new PdfPCell(new Phrase(cropLimitDetails.get("impactingIncome").toString(), ReportTemplate.TIMESROMAN_10_NORMAL));
                impactingProfitDataCell.setBorderWidth(0.5f);
                impactingProfitDataCell.setBorderColor(new BaseColor(131, 154, 103));
                impactingProfitDataCell.setBorder(Rectangle.BOX);
                impactingProfitDataCell.setUseBorderPadding(true);
                impactingProfitDataCell.setBackgroundColor(new BaseColor(223, 240, 216));
                table.addCell(impactingProfitDataCell);

                PdfPCell toIncreaseIncomeDataCell = new PdfPCell(new Phrase(cropLimitDetails.get("incDecIncome").toString(), ReportTemplate.TIMESROMAN_10_NORMAL));
                toIncreaseIncomeDataCell.setBorderWidth(0.5f);
                toIncreaseIncomeDataCell.setBorderColor(new BaseColor(131, 154, 103));
                toIncreaseIncomeDataCell.setBorder(Rectangle.BOX);
                toIncreaseIncomeDataCell.setUseBorderPadding(true);
                toIncreaseIncomeDataCell.setBackgroundColor(new BaseColor(223, 240, 216));
                table.addCell(toIncreaseIncomeDataCell);
            }

           Object ACRAGE= cropLimitDetails.get ( "acreagePlanted" );

            PdfPCell impactingProfitDataCell = new PdfPCell(new Phrase(""+ACRAGE, ReportTemplate.TIMESROMAN_10_NORMAL));
            impactingProfitDataCell.setBorderWidth(0.5f);
            impactingProfitDataCell.setBorderColor(new BaseColor(131, 154, 103));
            impactingProfitDataCell.setBorder(Rectangle.BOX);
            impactingProfitDataCell.setUseBorderPadding(true);
            impactingProfitDataCell.setBackgroundColor(new BaseColor(223, 240, 216));
            table.addCell(impactingProfitDataCell);
        }

        /*HashMap<String, Object> cropAcreageLimts = reportDataPage3.getCropAcreageLimts();

        List<CropTypeView> cropTypeViewList = (List<CropTypeView>)cropAcreageLimts.get("cropTypeViewList");
        List<CropsGroupView> cropsGroupViewsList = (List<CropsGroupView>)cropAcreageLimts.get("cropsGroupViewsList");

        for (CropTypeView cropTypeView : cropTypeViewList) {

            if (cropTypeView.getSelected()) {
                PdfPCell cropNameDataCell = new PdfPCell(new Phrase(cropTypeView.getCropName(), ReportTemplate.TIMESROMAN_10_NORMAL));
                cropNameDataCell.setBorderWidth(0.5f);
                cropNameDataCell.setBorderColor(new BaseColor(131, 154, 103));
                cropNameDataCell.setBorder(Rectangle.BOX);
                cropNameDataCell.setUseBorderPadding(true);
                cropNameDataCell.setBackgroundColor(new BaseColor(223, 240, 216));
                table.addCell(cropNameDataCell);

                String cropLimits;
                String impactingProfit;

                if (cropTypeView.getMinimumAcres().equals("") && cropTypeView.getMaximumAcres().equals("")) {
                    cropLimits = "No Crop Limit Specified";
                    impactingProfit = "No";
                } else if(cropTypeView.getMinimumAcres().equals("") && !cropTypeView.getMaximumAcres().equals("")) {
                    cropLimits = "No more than " + cropTypeView.getMaximumAcres() + " Acres";
                    impactingProfit = "Yes";
                } else if(!cropTypeView.getMinimumAcres().equals("") && cropTypeView.getMaximumAcres().equals("")) {
                    cropLimits = "At least " + cropTypeView.getMinimumAcres() + " Acres";
                    impactingProfit = "Yes";
                } else if(!cropTypeView.getMinimumAcres().equals("") && !cropTypeView.getMaximumAcres().equals("")) {
                    cropLimits = cropTypeView.getMinimumAcres() + " to " + cropTypeView.getMaximumAcres() + " Acres";
                    impactingProfit = "Yes";
                } else {
                    cropLimits = "";
                    impactingProfit = "";
                }

                PdfPCell cropLimitsDataCell = new PdfPCell(new Phrase(cropLimits, ReportTemplate.TIMESROMAN_10_NORMAL));
                cropLimitsDataCell.setBorderWidth(0.5f);
                cropLimitsDataCell.setBorderColor(new BaseColor(131, 154, 103));
                cropLimitsDataCell.setBorder(Rectangle.BOX);
                cropLimitsDataCell.setUseBorderPadding(true);
                cropLimitsDataCell.setBackgroundColor(new BaseColor(223, 240, 216));
                table.addCell(cropLimitsDataCell);

                PdfPCell impactingProfitDataCell = new PdfPCell(new Phrase(impactingProfit, ReportTemplate.TIMESROMAN_10_NORMAL));
                impactingProfitDataCell.setBorderWidth(0.5f);
                impactingProfitDataCell.setBorderColor(new BaseColor(131, 154, 103));
                impactingProfitDataCell.setBorder(Rectangle.BOX);
                impactingProfitDataCell.setUseBorderPadding(true);
                impactingProfitDataCell.setBackgroundColor(new BaseColor(223, 240, 216));
                table.addCell(impactingProfitDataCell);
            }

        }

        for (CropsGroupView cropsGroupView : cropsGroupViewsList) {

            PdfPCell cropNameDataCell = new PdfPCell(new Phrase(cropsGroupView.getCropsGroupName(), ReportTemplate.TIMESROMAN_10_NORMAL));
            cropNameDataCell.setBorderWidth(0.5f);
            cropNameDataCell.setBorderColor(new BaseColor(131, 154, 103));
            cropNameDataCell.setBorder(Rectangle.BOX);
            cropNameDataCell.setUseBorderPadding(true);
            cropNameDataCell.setBackgroundColor(new BaseColor(223, 240, 216));
            table.addCell(cropNameDataCell);

            String cropLimits;
            String impactingProfit;

            if (cropsGroupView.getMinimumAcres().equals("") && cropsGroupView.getMaximumAcres().equals("")) {
                cropLimits = "No Crop Limit Specified";
                impactingProfit = "No";
            } else if(cropsGroupView.getMinimumAcres().equals("") && !cropsGroupView.getMaximumAcres().equals("")) {
                cropLimits = "No more than " + cropsGroupView.getMaximumAcres() + " Acres";
                impactingProfit = "Yes";
            } else if(!cropsGroupView.getMinimumAcres().equals("") && cropsGroupView.getMaximumAcres().equals("")) {
                cropLimits = "At least " + cropsGroupView.getMinimumAcres() + " Acres";
                impactingProfit = "Yes";
            } else if(!cropsGroupView.getMinimumAcres().equals("") && !cropsGroupView.getMaximumAcres().equals("")) {
                cropLimits = cropsGroupView.getMinimumAcres() + " to " + cropsGroupView.getMaximumAcres() + " Acres";
                impactingProfit = "Yes";
            } else {
                cropLimits = "";
                impactingProfit = "";
            }

            PdfPCell cropLimitsDataCell = new PdfPCell(new Phrase(cropLimits, ReportTemplate.TIMESROMAN_10_NORMAL));
            cropLimitsDataCell.setBorderWidth(0.5f);
            cropLimitsDataCell.setBorderColor(new BaseColor(131, 154, 103));
            cropLimitsDataCell.setBorder(Rectangle.BOX);
            cropLimitsDataCell.setUseBorderPadding(true);
            cropLimitsDataCell.setBackgroundColor(new BaseColor(223, 240, 216));
            table.addCell(cropLimitsDataCell);

            PdfPCell impactingProfitDataCell = new PdfPCell(new Phrase(impactingProfit, ReportTemplate.TIMESROMAN_10_NORMAL));
            impactingProfitDataCell.setBorderWidth(0.5f);
            impactingProfitDataCell.setBorderColor(new BaseColor(131, 154, 103));
            impactingProfitDataCell.setBorder(Rectangle.BOX);
            impactingProfitDataCell.setUseBorderPadding(true);
            impactingProfitDataCell.setBackgroundColor(new BaseColor(223, 240, 216));
            table.addCell(impactingProfitDataCell);

        }*/

        return table;
    }

}
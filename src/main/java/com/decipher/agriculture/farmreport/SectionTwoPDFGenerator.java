package com.decipher.agriculture.farmreport;


import com.decipher.agriculture.data.farm.PlanByStrategy;
import com.decipher.util.AgricultureStandardUtils;
import com.decipher.view.form.farmDetails.CropResourceUsageView;
import com.decipher.view.form.farmDetails.CropTypeView;
import com.decipher.view.form.farmDetails.FarmInfoView;
import com.decipher.view.form.farmDetails.FarmOutputDetailsView;
import com.decipher.view.form.scenario.FarmStrategyScenarioView;
import com.decipher.view.form.strategy.FarmCustomStrategyView;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;

/**
 * Created by Satan on 12/1/2015.
 */
@SuppressWarnings("unchecked")
public class SectionTwoPDFGenerator {

    /**
     * @chanegd - Abhishek
     * @date - 11-01-2016
     * @desc - Only data objects to be used are used not using any service to populate data objects
     */
    private ReportDataPage2 reportDataPage2;
    private FarmInfoView farmInfoView;

    public SectionTwoPDFGenerator(ReportDataPage2 reportDataPage2, FarmInfoView farmInfoView){
        /**
         * @Chanegd - Abhishek
         * @date - 11-01-2016
         * @desc - Optimized with data supplied by controller to ReportDataBeans removing overhead of data getting directly from service
         */
        this.reportDataPage2 = reportDataPage2;
        this.farmInfoView = farmInfoView;

    }

    public void buildSectionTwo(Document document, PdfWriter writer) throws DocumentException, IOException {
        document.newPage();

        PdfPTable table = new PdfPTable(1);
        table.setWidthPercentage(100);

        /**
         * @change - Abhishek
         * @date - 09-12-2015
         */
        table.addCell(ReportTemplate.getSectionHeaderCell("Farm Information for " + SectionOnePDFGenerator.getSelectedStrategy()));

        PdfPCell farmInformationTableCell = new PdfPCell(getFarmInformationTable());
        farmInformationTableCell.setPaddingTop(5);
        table.addCell(farmInformationTableCell);
        table.addCell(ReportTemplate.getSectionHeaderCell("Strategy Comparison"));

        PdfPTable estimateIncomeCropAcreageTable = ReportTemplate.getFullWidthTable(1);

        PdfPCell sectionSubHeaderCellWithoutBorderSmall = ReportTemplate.getSectionSubHeaderCellWithoutBorderSmall("Est Income and Crop Acreage");
        sectionSubHeaderCellWithoutBorderSmall.setBorderWidth(0);
        sectionSubHeaderCellWithoutBorderSmall.setBorderWidthLeft(1);
        sectionSubHeaderCellWithoutBorderSmall.setBorderWidthRight(1);
        estimateIncomeCropAcreageTable.addCell(sectionSubHeaderCellWithoutBorderSmall);
        estimateIncomeCropAcreageTable.addCell(getParentTable("CropAcreage"));

        table.addCell(estimateIncomeCropAcreageTable);

        PdfPTable resourcesTable = ReportTemplate.getFullWidthTable(1);
        PdfPCell resourcesSectionSubHeaderCellWithoutBorderSmall = ReportTemplate.getSectionSubHeaderCellWithoutBorderSmall("Resources");
        resourcesSectionSubHeaderCellWithoutBorderSmall.setBorderWidth(0);
        resourcesSectionSubHeaderCellWithoutBorderSmall.setBorderWidthLeft(1);
        resourcesSectionSubHeaderCellWithoutBorderSmall.setBorderWidthRight(1);
        resourcesTable.addCell(resourcesSectionSubHeaderCellWithoutBorderSmall);
        resourcesTable.addCell(getParentTable("resource"));

        table.addCell(resourcesTable);

        PdfPTable riskManagementTable = ReportTemplate.getFullWidthTable(1);
        PdfPCell riskManagementSectionSubHeaderCellWithoutBorderSmall = ReportTemplate.getSectionSubHeaderCellWithoutBorderSmall("Risk Management");
        riskManagementSectionSubHeaderCellWithoutBorderSmall.setBorderWidth(0);
        riskManagementSectionSubHeaderCellWithoutBorderSmall.setBorderWidthLeft(1);
        riskManagementSectionSubHeaderCellWithoutBorderSmall.setBorderWidthRight(1);
        riskManagementTable.addCell(riskManagementSectionSubHeaderCellWithoutBorderSmall);
        riskManagementTable.addCell(getRiskManagementTable());

        table.addCell(riskManagementTable);


        PdfPTable conservationManagementTable = ReportTemplate.getFullWidthTable(1);
        PdfPCell conservationManagementSectionSubHeaderCellWithoutBorderSmall = ReportTemplate.getSectionSubHeaderCellWithoutBorderSmall("Conservation Management");
        conservationManagementSectionSubHeaderCellWithoutBorderSmall.setBorderWidth(0);
        conservationManagementSectionSubHeaderCellWithoutBorderSmall.setBorderWidthLeft(1);
        conservationManagementSectionSubHeaderCellWithoutBorderSmall.setBorderWidthRight(1);
        conservationManagementTable.addCell(conservationManagementSectionSubHeaderCellWithoutBorderSmall);
        conservationManagementTable.addCell(getParentTable("conservation"));

        table.addCell(conservationManagementTable);
        document.add(table);
    }

    private PdfPTable getFarmInformationTable(){
        PdfPTable table = new PdfPTable(12);
        table.setWidthPercentage(100);

        // Create Table header cells
        PdfPCell cropNameHeader = ReportTemplate.BoldHeaderBottomBorderTable.getHeaderCell("Crop");
        cropNameHeader.setBorderWidth(0);
        cropNameHeader.setBorderWidthLeft(1);
        cropNameHeader.setBorderWidthBottom(1);
        cropNameHeader.setBorderWidthTop(1);
        cropNameHeader.setBorderWidthRight(1);
        table.addCell(cropNameHeader);

        PdfPCell unitsHeader = ReportTemplate.BoldHeaderBottomBorderTable.getHeaderCell("Units");
        unitsHeader.setBorderWidth(0);
        unitsHeader.setBorderWidthLeft(0);
        unitsHeader.setBorderWidthBottom(1);
        unitsHeader.setBorderWidthTop(1);
        unitsHeader.setBorderWidthRight(1);
        table.addCell(unitsHeader);

//        table.addCell(ReportTemplate.BoldHeaderBottomBorderTable.getHeaderCell("Units"));

        PdfPCell estYieldHeader = ReportTemplate.BoldHeaderBottomBorderTable.getHeaderCell("Est. Yield");
        estYieldHeader.setBorderWidth(0);
        estYieldHeader.setBorderWidthLeft(0);
        estYieldHeader.setBorderWidthBottom(1);
        estYieldHeader.setBorderWidthTop(1);
        estYieldHeader.setBorderWidthRight(1);
        table.addCell(estYieldHeader);

//        table.addCell(ReportTemplate.BoldHeaderBottomBorderTable.getHeaderCell("Est. Yield"));

        PdfPCell estPriceHeader = ReportTemplate.BoldHeaderBottomBorderTable.getHeaderCell("Est. Price");
        estPriceHeader.setBorderWidth(0);
        estPriceHeader.setBorderWidthLeft(0);
        estPriceHeader.setBorderWidthBottom(1);
        estPriceHeader.setBorderWidthTop(1);
        estPriceHeader.setBorderWidthRight(1);
        table.addCell(estPriceHeader);

//        table.addCell(ReportTemplate.BoldHeaderBottomBorderTable.getHeaderCell("Est. Price"));

        PdfPCell estVariableProductionHeader = ReportTemplate.BoldHeaderBottomBorderTable.getHeaderCell("Est. Var Production Costs Per Acre");
        estVariableProductionHeader.setBorderWidth(0);
        estVariableProductionHeader.setBorderWidthLeft(0);
        estVariableProductionHeader.setBorderWidthBottom(1);
        estVariableProductionHeader.setBorderWidthTop(1);
        estVariableProductionHeader.setBorderWidthRight(1);
        table.addCell(estVariableProductionHeader);

//        table.addCell(ReportTemplate.BoldHeaderBottomBorderTable.getHeaderCell("Est. Var Production Costs Per Acre"));

        PdfPCell estIncomeHeader = ReportTemplate.BoldHeaderBottomBorderTable.getHeaderCell("Est. Income\nPer Acre");
        estIncomeHeader.setBorderWidth(0);
        estIncomeHeader.setBorderWidthLeft(0);
        estIncomeHeader.setBorderWidthBottom(1);
        estIncomeHeader.setBorderWidthTop(1);
        estIncomeHeader.setBorderWidthRight(1);
        table.addCell(estIncomeHeader);

//        table.addCell(ReportTemplate.BoldHeaderBottomBorderTable.getHeaderCell("Est. Income\nPer Acre"));

        PdfPCell forwardSalesHeader = ReportTemplate.BoldHeaderBottomBorderTable.getHeaderCell("Forward Sales");
        forwardSalesHeader.setBorderWidth(0);
        forwardSalesHeader.setBorderWidthLeft(0);
        forwardSalesHeader.setBorderWidthBottom(1);
        forwardSalesHeader.setBorderWidthTop(1);
        forwardSalesHeader.setBorderWidthRight(1);
        table.addCell(forwardSalesHeader);

//        table.addCell(ReportTemplate.BoldHeaderBottomBorderTable.getHeaderCell("Forward Sales"));

        PdfPCell forwardSalesPriceHeader = ReportTemplate.BoldHeaderBottomBorderTable.getHeaderCell("Forward Sales Price");
        forwardSalesPriceHeader.setBorderWidth(0);
        forwardSalesPriceHeader.setBorderWidthLeft(0);
        forwardSalesPriceHeader.setBorderWidthBottom(1);
        forwardSalesPriceHeader.setBorderWidthTop(1);
        forwardSalesPriceHeader.setBorderWidthRight(1);
        table.addCell(forwardSalesPriceHeader);

//        table.addCell(ReportTemplate.BoldHeaderBottomBorderTable.getHeaderCell("Forward Sales Price"));

        PdfPCell forwardSalesQuantityHeader = ReportTemplate.BoldHeaderBottomBorderTable.getHeaderCell("Forward Sales Quantity");
        forwardSalesQuantityHeader.setBorderWidth(0);
        forwardSalesQuantityHeader.setBorderWidthLeft(0);
        forwardSalesQuantityHeader.setBorderWidthBottom(1);
        forwardSalesQuantityHeader.setBorderWidthTop(1);
        forwardSalesQuantityHeader.setBorderWidthRight(1);
        table.addCell(forwardSalesQuantityHeader);

//        table.addCell(ReportTemplate.BoldHeaderBottomBorderTable.getHeaderCell("Forward Sales Quantity"));

        PdfPCell forwardSalesAcresHeader = ReportTemplate.BoldHeaderBottomBorderTable.getHeaderCell("Forward Sales Acres");
        forwardSalesAcresHeader.setBorderWidth(0);
        forwardSalesAcresHeader.setBorderWidthLeft(0);
        forwardSalesAcresHeader.setBorderWidthBottom(1);
        forwardSalesAcresHeader.setBorderWidthTop(1);
        forwardSalesAcresHeader.setBorderWidthRight(1);
        table.addCell(forwardSalesAcresHeader);

//        table.addCell(ReportTemplate.BoldHeaderBottomBorderTable.getHeaderCell("Forward Sales Acres"));

        PdfPCell minCropHeader = ReportTemplate.BoldHeaderBottomBorderTable.getHeaderCell("Min Crop\nAcreage Limits");
        minCropHeader.setBorderWidth(0);
        minCropHeader.setBorderWidthLeft(0);
        minCropHeader.setBorderWidthBottom(1);
        minCropHeader.setBorderWidthTop(1);
        minCropHeader.setBorderWidthRight(1);
        table.addCell(minCropHeader);

//        table.addCell(ReportTemplate.BoldHeaderBottomBorderTable.getHeaderCell("Min Crop\nAcreage Limits"));

        PdfPCell manCropHeader = ReportTemplate.BoldHeaderBottomBorderTable.getHeaderCell("Max Crop\nAcreage Limits");
        manCropHeader.setBorderWidth(0);
        manCropHeader.setBorderWidthLeft(0);
        manCropHeader.setBorderWidthBottom(1);
        manCropHeader.setBorderWidthTop(1);
        manCropHeader.setBorderWidthRight(1);
        table.addCell(manCropHeader);

//        table.addCell(ReportTemplate.BoldHeaderBottomBorderTable.getHeaderCell("Max Crop\nAcreage Limits"));

//        table.addCell(ReportTemplate.BoldHeaderBottomBorderTable.getHeaderCell("Other Resources"));

        // Add Data Dynamically
        /**
         * @Chanegd - Abhishek
         * @date - 11-01-2016
         * @desc - According to the updated syntax of ReportDataPage2
         */
        List<CropTypeView> cropTypeViewList = reportDataPage2.getCropDetails();
        for (CropTypeView crop : cropTypeViewList){
            if(crop.getSelected()){

                PdfPCell cropNameCell = ReportTemplate.BoldHeaderBottomBorderTable.getDataCell(crop.getCropName());
                cropNameCell.setBorderWidth(0);
                cropNameCell.setBorderWidthLeft(1);
                cropNameCell.setBorderWidthBottom(1);
                cropNameCell.setBorderWidthRight(1);
//                cropNameCell.setBorderWidthTop(1);
                table.addCell(cropNameCell);

                PdfPCell cropUOMCell = ReportTemplate.BoldHeaderBottomBorderTable.getDataCell(crop.getCropUOM());
                cropUOMCell.setBorderWidth(0);
                cropUOMCell.setBorderWidthBottom(1);
                cropUOMCell.setBorderWidthRight(1);
//                cropUOMCell.setBorderWidthTop(1);
                table.addCell(cropUOMCell);

                PdfPCell cropYieldDataCell = ReportTemplate.BoldHeaderBottomBorderTable.getDataCell(crop.getIntExpCropYield());
                cropYieldDataCell.setBorderWidth(0);
                cropYieldDataCell.setBorderWidthBottom(1);
                cropYieldDataCell.setBorderWidthRight(1);
//                cropYieldDataCell.setBorderWidthTop(1);
                cropYieldDataCell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                table.addCell(cropYieldDataCell);

                PdfPCell priceDataCell = ReportTemplate.BoldHeaderBottomBorderTable.getDataCell(crop.getIntExpCropPrice() != null ? AgricultureStandardUtils.commaSeparatedForPriceWithThreeDecimal(crop.getIntExpCropPrice().toString()) : "--");
                priceDataCell.setBorderWidth(0);
                priceDataCell.setBorderWidthBottom(1);
                priceDataCell.setBorderWidthRight(1);
//                priceDataCell.setBorderWidthTop(1);
                priceDataCell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                table.addCell(priceDataCell);

                PdfPCell calculationCell  = ReportTemplate.BoldHeaderBottomBorderTable.getDataCell(crop.getCalculatedVariableProductionCostFloat());
                calculationCell.setBorderWidth(0);
                calculationCell.setBorderWidthBottom(1);
                calculationCell.setBorderWidthRight(1);
//                calculationCell.setBorderWidthTop(1);
                table.addCell(calculationCell);

                PdfPCell calculatedProfitCell = ReportTemplate.BoldHeaderBottomBorderTable.getDataCell(crop.getCalculatedProfitString() != null ? crop.getCalculatedProfitString() : "--");
                calculatedProfitCell.setBorderWidth(0);
                calculatedProfitCell.setBorderWidthBottom(1);
                calculatedProfitCell.setBorderWidthRight(1);
//                calculatedProfitCell.setBorderWidthTop(1);
                table.addCell(calculatedProfitCell);
                if(crop.getFirmchecked().equalsIgnoreCase("true")){
                    PdfPCell firmCell = ReportTemplate.BoldHeaderBottomBorderTable.getDataCell("Yes");
                    firmCell.setBorderWidth(0);
                    firmCell.setBorderWidthRight(1);
//                    firmCell.setBorderWidthTop(1);
                    firmCell.setBorderWidthBottom(1);

                    table.addCell(firmCell);
                    PdfPCell priceStrCell = ReportTemplate.BoldHeaderBottomBorderTable.getDataCell(crop.getPriceStr());
                    priceStrCell.setBorderWidth(0);
                    priceStrCell.setBorderWidthRight(1);
//                    priceStrCell.setBorderWidthTop(1);
                    priceStrCell.setBorderWidthBottom(1);
                    table.addCell(priceStrCell);

                    PdfPCell quantityStrCell = ReportTemplate.BoldHeaderBottomBorderTable.getDataCell(crop.getQuantityStr());
                    quantityStrCell.setBorderWidth(0);
                    quantityStrCell.setBorderWidthBottom(1);
//                    quantityStrCell.setBorderWidthTop(1);
                    quantityStrCell.setBorderWidthRight(1);
                    table.addCell(quantityStrCell);

                    PdfPCell acresStrCell = ReportTemplate.BoldHeaderBottomBorderTable.getDataCell(crop.getAcresStr());
                    acresStrCell.setBorderWidth(0);
                    acresStrCell.setBorderWidthBottom(1);
                    acresStrCell.setBorderWidthRight(1);
//                    acresStrCell.setBorderWidthTop(1);
                    table.addCell(acresStrCell);

                } else if(crop.getProposedchecked()){

                    PdfPCell proposedCheckedCell = ReportTemplate.BoldHeaderBottomBorderTable.getDataCell("Yes");
                    proposedCheckedCell.setBorderWidth(0);
                    proposedCheckedCell.setBorderWidthBottom(1);
                    proposedCheckedCell.setBorderWidthRight(1);
//                    proposedCheckedCell.setBorderWidthTop(1);
                    table.addCell(proposedCheckedCell);

                    PdfPCell priceStrCell = ReportTemplate.BoldHeaderBottomBorderTable.getDataCell(crop.getPriceStr());
                    priceStrCell.setBorderWidth(0);
                    priceStrCell.setBorderWidthBottom(1);
                    priceStrCell.setBorderWidthRight(1);
//                    priceStrCell.setBorderWidthTop(1);
                    table.addCell(priceStrCell);

                    PdfPCell quantityStrCell = ReportTemplate.BoldHeaderBottomBorderTable.getDataCell(crop.getQuantityStr());
                    quantityStrCell.setBorderWidth(0);
                    quantityStrCell.setBorderWidthBottom(1);
                    quantityStrCell.setBorderWidthRight(1);
//                    quantityStrCell.setBorderWidthTop(1);
                    table.addCell(quantityStrCell);

                    PdfPCell acresStrCell = ReportTemplate.BoldHeaderBottomBorderTable.getDataCell(crop.getAcresStr());
                    acresStrCell.setBorderWidth(0);
                    acresStrCell.setBorderWidthBottom(1);
                    acresStrCell.setBorderWidthRight(1);
//                    acresStrCell.setBorderWidthTop(1);
                    table.addCell(acresStrCell);
                } else {
                    PdfPCell elseStrCell = ReportTemplate.BoldHeaderBottomBorderTable.getDataCell("No");
                    elseStrCell.setBorderWidth(0);
                    elseStrCell.setBorderWidthBottom(1);
                    elseStrCell.setBorderWidthRight(1);
//                    elseStrCell.setBorderWidthTop(1);
                    table.addCell(elseStrCell);

                    PdfPCell elseFStrCell = ReportTemplate.BoldHeaderBottomBorderTable.getDataCell("");
                    elseFStrCell.setBorderWidth(0);
                    elseFStrCell.setBorderWidthBottom(1);
                    elseFStrCell.setBorderWidthRight(1);
//                    elseFStrCell.setBorderWidthTop(1);
                    table.addCell(elseFStrCell);

                    PdfPCell elseSecStrCell = ReportTemplate.BoldHeaderBottomBorderTable.getDataCell("");
                    elseSecStrCell.setBorderWidth(0);
                    elseSecStrCell.setBorderWidthBottom(1);
                    elseSecStrCell.setBorderWidthRight(1);
//                    elseSecStrCell.setBorderWidthTop(1);
                    table.addCell(elseSecStrCell);

                    PdfPCell elseThiStrCell = ReportTemplate.BoldHeaderBottomBorderTable.getDataCell("");
                    elseThiStrCell.setBorderWidth(0);
                    elseThiStrCell.setBorderWidthBottom(1);
                    elseThiStrCell.setBorderWidthRight(1);
//                    elseThiStrCell.setBorderWidthTop(1);
                    table.addCell(elseThiStrCell);
                }


                PdfPCell minimumAcresCell = ReportTemplate.BoldHeaderBottomBorderTable.getDataCell(crop.getMinimumAcres());
                minimumAcresCell.setBorderWidth(0);
                minimumAcresCell.setBorderWidthBottom(1);
                minimumAcresCell.setBorderWidthRight(1);
//                minimumAcresCell.setBorderWidthTop(1);
                table.addCell(minimumAcresCell);

                PdfPCell maximumAcresCell = ReportTemplate.BoldHeaderBottomBorderTable.getDataCell(crop.getMaximumAcres());
                maximumAcresCell.setBorderWidth(0);
                maximumAcresCell.setBorderWidthBottom(1);
                maximumAcresCell.setBorderWidthRight(1);
//                maximumAcresCell.setBorderWidthTop(1);
                table.addCell(maximumAcresCell);
//                table.addCell(ReportTemplate.BoldHeaderBottomBorderTable.getDataCell(""));
            }
        }

        // Add Footer Cell
        PdfPCell footerCell = ReportTemplate.getFooterCellSmallNoBorder("Crop/Field Choices - See Exhibit 2 if planning by Fields");
        footerCell.setColspan(10);
        footerCell.setPaddingTop(5);
        table.addCell(footerCell);

        return table;
    }

    /**
     * @param tableName of table which is to be applied
     * @return PsfPTable
     * @throws DocumentException, JSONException
     */
    private PdfPTable getParentTable(String tableName) throws DocumentException {
        PdfPTable table = ReportTemplate.getFullWidthTable(5);

        PdfPCell innerTable = ReportTemplate.NoBorderNoHeaderTable.getEmptyCell();
        innerTable.setColspan(5);
        if (tableName.equals("CropAcreage")){
            innerTable.addElement(getEstimatedIncomeCropAcreageTable());
        } else if(tableName.equals("resource")){
            innerTable.addElement(getResourcesTable());
        } else if(tableName.equals("conservation")){
            innerTable.addElement(getConservationManagementTable());
        }

        table.addCell(innerTable);

        PdfPCell estimateIncomeGraph = ReportTemplate.NoBorderNoHeaderTable.getEmptyCell();
        table.addCell(estimateIncomeGraph);

        PdfPCell acreageGraph = ReportTemplate.NoBorderNoHeaderTable.getEmptyCell();
        table.addCell(acreageGraph);

        return table;
    }
    private PdfPTable generateHeaderForResource(int noOfResource) {
        //  New table for resources     /#  Col size dynamic
        PdfPTable table = new PdfPTable(noOfResource + 3);
        table.setWidthPercentage(100);

        PdfPCell strategyCell = ReportTemplate.BoldHeaderBoxBorderTable.getHeaderCell("Strategy");
        strategyCell.setRowspan(3);
        table.addCell(strategyCell);

        PdfPCell estimatedIncomeCell = ReportTemplate.BoldHeaderBoxBorderTable.getHeaderCell("Est. Income");
        estimatedIncomeCell.setRowspan(3);
        table.addCell(estimatedIncomeCell);

        PdfPCell returnOnWorkingCapitalUse = ReportTemplate.BoldHeaderBoxBorderTable.getHeaderCell("Return on Working Capital");
        returnOnWorkingCapitalUse.setRowspan(3);

//        blankCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(returnOnWorkingCapitalUse);


        // Add Resource Usage, colspan will be dynamic depending upon the number of resources
        PdfPCell cropAcreage = ReportTemplate.BoldHeaderBoxBorderTable.getHeaderCell("Resource Use");
        cropAcreage.setColspan(noOfResource);
        table.addCell(cropAcreage);

        table.completeRow();

        return table;
    }

    private PdfPTable generateHeaderForEstimatedIncomeCropAcreage(int noOfCrops) {


        // Table width /#colums will be dynamic /# Table Header
        PdfPTable table = new PdfPTable(noOfCrops + 4);
        table.setWidthPercentage(100);
        //table.setWidths(new Integer[]{10,10,5,10,5,5,5,5,5});

        PdfPCell strategyCell = ReportTemplate.BoldHeaderBoxBorderTable.getHeaderCell("Strategy");
        strategyCell.setRowspan(3);
        table.addCell(strategyCell);

        /**
         * @changed - Abhishek
         * @date - 11-02-2016
         * @desc - changed according to slide#3 of 02102016
         */
        /*PdfPCell estimatedIncomeCell = ReportTemplate.BoldHeaderBoxBorderTable.getHeaderCell("Estimated Income");*/
        PdfPCell estimatedIncomeCell = ReportTemplate.BoldHeaderBoxBorderTable.getHeaderCell("Est. Income");
        estimatedIncomeCell.setRowspan(3);
        table.addCell(estimatedIncomeCell);

        PdfPCell AvgEstIncomeperAcre = ReportTemplate.BoldHeaderBoxBorderTable.getHeaderCell("Avg Est. Income per Acre");
        AvgEstIncomeperAcre.setRowspan(3);
        table.addCell(AvgEstIncomeperAcre);

        PdfPCell acreagePlantedCell = ReportTemplate.BoldHeaderBoxBorderTable.getHeaderCell("Acreage Assigned");
        acreagePlantedCell.setRowspan(3);
        table.addCell(acreagePlantedCell);

        // Add Crop Acreage, colspan will be dynamic depending upon the number of crops
        PdfPCell cropAcreage = ReportTemplate.BoldHeaderBoxBorderTable.getHeaderCell("Crop Acreage");
        cropAcreage.setColspan(noOfCrops);
        table.addCell(cropAcreage);

        table.completeRow();

        return table;

    }

    /**
     * @chaneged - Abhishek
     * @date - 11-01-2016
     * @desc - changed According to all strategies supplied by controller(previously used baseline strategy)
     */
    private PdfPTable getEstimatedIncomeCropAcreageTable() throws DocumentException {

        /**
         * @changed - Abhishek
         * @date - 11-01-2016
         * @desc - Changed according to strategies data supplied by controller
         */

//        List<CropTypeView> cropTypeViewList = (List<CropTypeView>) reportDataPage2.getStrategiesDataForFarm().get(0).get("cropTypeView");
        int noOfCrops = 0;
        PdfPTable table = null;
        Set <String> setForCropHeader = new HashSet <String> ();
        Set <String> setForCropHeaderForAcre = new HashSet <String> ();

        if (reportDataPage2.getFarmInfoView ().getStrategy ().equals ( PlanByStrategy.PLAN_BY_ACRES )) {
            List <List> farmOutputDetailsViewListForMultiStrategy= new ArrayList <> (  );

            for (int i = 0; i < reportDataPage2.getStrategiesDataForFarm ().size (); i++) {
                List <FarmOutputDetailsView> farmOutputDetailsViewListForSingleStrategy = (List <FarmOutputDetailsView>) reportDataPage2.getStrategiesDataForFarm ().get ( i ).get ( "farmOutputDetails" );
                farmOutputDetailsViewListForMultiStrategy.add ( farmOutputDetailsViewListForSingleStrategy );
            }

            for (int i = 0; i < farmOutputDetailsViewListForMultiStrategy.size (); i++) {
                List <FarmOutputDetailsView> farmOutputDetailsViewList = farmOutputDetailsViewListForMultiStrategy.get ( i );
//            List <FarmOutputDetailsView>  farmOutputDetailsViewList= (List <FarmOutputDetailsView>) reportDataPage2.getStrategiesDataForFarm ().get ( 0 ).get ( "farmOutputDetails" );
//               noOfCrops += farmOutputDetailsViewList.size ();
                // Now Add Crops Names
                for (FarmOutputDetailsView farmOutputDetailsView : farmOutputDetailsViewList) {
                    String cropName = "";
                    if (farmOutputDetailsView.getForProposed ()) {
                        cropName = farmOutputDetailsView.getCropTypeView ().getCropName () + " (Proposed)";
                    } else if (farmOutputDetailsView.getForFirm ()) {
                        cropName = farmOutputDetailsView.getCropTypeView ().getCropName () + " (Firm)";
                    } else {
                        cropName = farmOutputDetailsView.getCropTypeView ().getCropName ();
                    }
                    setForCropHeaderForAcre.add ( cropName );
                }
            }
            noOfCrops = setForCropHeaderForAcre.size ();
            table = generateHeaderForEstimatedIncomeCropAcreage ( noOfCrops );
            Iterator iterator = setForCropHeaderForAcre.iterator ();
            while (iterator.hasNext ()) {
                PdfPCell crop = ReportTemplate.BoldHeaderBoxBorderTable.getDataCell ( "" + iterator.next () );
                crop.setRowspan ( 2 );
                table.addCell ( crop );

            }
            //                crop.setRotation(90);
//                crop.rotate();

            table.completeRow ();

        } else if (reportDataPage2.getFarmInfoView ().getStrategy ().equals ( PlanByStrategy.PLAN_BY_FIELDS )) {

//            Map <String,String> hashMapForAcre = (Map <String, String>) reportDataPage2.getBaseSelectedStrategyOutputDetails ().get ( "hashMapForAcre" );
            Map <Integer, Map> hashMapForAcreForMultiStrategy = new HashMap <> ();
            for (int i = 0; i < reportDataPage2.getStrategiesDataForFarm ().size (); i++) {
                Map <String, String> hashMapForAcreForSingleStrategy = (Map <String, String>) reportDataPage2.getStrategiesDataForFarm ().get ( i ).get ( "hashMapForAcre" );
                hashMapForAcreForMultiStrategy.put ( i, hashMapForAcreForSingleStrategy );
            }
//            table = generateHeaderForEstimatedIncomeCropAcreage ( hashMapForAcre.size () );
            for (int i = 0; i < hashMapForAcreForMultiStrategy.size (); i++) {
                Map <String, String> hashMapForAcre = hashMapForAcreForMultiStrategy.get ( i );
                for (String cropKey : hashMapForAcre.keySet ()) {
                    if(cropKey!=null){
                    setForCropHeader.add ( cropKey );}
                }
            }
            table = generateHeaderForEstimatedIncomeCropAcreage ( setForCropHeader.size () );
            Iterator iterator = setForCropHeader.iterator ();
            while (iterator.hasNext ()) {
                PdfPCell crop = ReportTemplate.BoldHeaderBoxBorderTable.getDataCell ( "" + iterator.next () );
                crop.setRowspan ( 2 );
//                    crop.setRotation(90);
//                    crop.rotate();
                table.addCell ( crop );
            }

        }


        // Add dynamic data now
        List <JSONObject> strategiesDataForFarmList = reportDataPage2.getStrategiesDataForFarm ();
        for (JSONObject strategyDataJsonObject : strategiesDataForFarmList) {

            /**
             * @changed - Abhishek
             * @date - 11-01-2016
             */
            FarmCustomStrategyView farmCustomStrategyView = (FarmCustomStrategyView) strategyDataJsonObject.get ( "farmCustomStrategy" );
            table.addCell ( ReportTemplate.BoldHeaderBoxBorderTable.getDataCell ( farmCustomStrategyView.getStrategyName () ) );
            /**
             * @changed - Abhishek
             * @date - 11-02-2016
             * @desc - Applied format of $xxx,xxx
             */
            table.addCell ( ReportTemplate.BoldHeaderBoxBorderTable.getDataCell ( "$" + strategyDataJsonObject.get ( "potentialProfit" ).toString () ) );

            if (farmCustomStrategyView.getFarmCustomStrategy ().getFarmInfo ().getStrategy ().equals ( PlanByStrategy.PLAN_BY_ACRES )) {
                List <FarmOutputDetailsView> farmOutputDetailsViewList = (List <FarmOutputDetailsView>) strategyDataJsonObject.get ( "farmOutputDetails" );
                Double totalAcreage = 0.0;
                Double estimatePerAcr = 0.0;
                Double totalEstimateIncome = 0.0;

                for (FarmOutputDetailsView farmOutputDetailsView : farmOutputDetailsViewList) {
                    if (farmOutputDetailsView.getCropTypeView ().getSelected ()) {
                        totalAcreage += AgricultureStandardUtils.doubleWithOneDecimal ( farmOutputDetailsView.getUsedAcresDouble ());
                        if (farmOutputDetailsView.getRatio () != 0.0) {
                            totalEstimateIncome += farmOutputDetailsView.getProfitDouble ();
                        }
                    }
                }
                if (totalAcreage != 0.0 || totalEstimateIncome != 0.0) {
                    estimatePerAcr = AgricultureStandardUtils.doubleWithOneDecimal (totalEstimateIncome / totalAcreage);
                } else {
                    estimatePerAcr = 0.0;
                }

                table.addCell ( ReportTemplate.BoldHeaderBoxBorderTable.getDataCell ( ""+ estimatePerAcr  ) );
                table.addCell ( ReportTemplate.BoldHeaderBoxBorderTable.getDataCell ( ""+ totalAcreage  ) );
                Iterator iteratorForHeader = setForCropHeaderForAcre.iterator ();
                HashMap setOfUsedAcresAsDouble = new HashMap ();
                HashMap selectedUsedAcresAsDouble = new HashMap ();

                for (FarmOutputDetailsView farmOutputDetailsView : farmOutputDetailsViewList) {
                    if (farmOutputDetailsView.getCropTypeView ().getSelected ()) {
                        if (farmOutputDetailsView.getForProposed ()) {
                            setOfUsedAcresAsDouble.put ( farmOutputDetailsView.getCropTypeView ().getCropName () + " (Proposed)", farmOutputDetailsView.getUsedAcresAsDouble () );
                        } else if (farmOutputDetailsView.getForFirm ()) {
                            setOfUsedAcresAsDouble.put ( farmOutputDetailsView.getCropTypeView ().getCropName () + " (Firm)", farmOutputDetailsView.getUsedAcresAsDouble () );
                        } else {
                            setOfUsedAcresAsDouble.put ( farmOutputDetailsView.getCropTypeView ().getCropName (), farmOutputDetailsView.getUsedAcresAsDouble () );
                        }
                    }
                }

                for (int i = 0; i < setForCropHeaderForAcre.size (); i++) {
                    if (i < setOfUsedAcresAsDouble.size ()) {
                        for (Object cropKey : setOfUsedAcresAsDouble.keySet ()) {
                            i++;
                            selectedUsedAcresAsDouble.put ( cropKey, setOfUsedAcresAsDouble.get ( cropKey ) );
                        }
                    } else {
                        selectedUsedAcresAsDouble.put ( i, null );
                    }
                }
                while (iteratorForHeader.hasNext ()) {
                    String cropName = String.valueOf ( iteratorForHeader.next () );
                    if (selectedUsedAcresAsDouble.get ( cropName ) != null) {
                        table.addCell ( ReportTemplate.BoldHeaderBoxBorderTable.getDataCell ( "" + selectedUsedAcresAsDouble.get ( cropName ) ) );
                    } else {
                        table.addCell ( ReportTemplate.BoldHeaderBoxBorderTable.getDataCell ( "--" ) );
                    }
                }

                 /*  for (FarmOutputDetailsView farmOutputDetailsView : farmOutputDetailsViewList) {
                    if (farmOutputDetailsView.getCropTypeView ().getSelected ()) {
                        table.addCell ( ReportTemplate.BoldHeaderBoxBorderTable.getDataCell ( farmOutputDetailsView.getUsedAcresAsDouble ().toString () ) );
                    }
                }
*/
            } else if (farmCustomStrategyView.getFarmCustomStrategy ().getFarmInfo ().getStrategy ().equals ( PlanByStrategy.PLAN_BY_FIELDS )) {
                Double totalAcreage = 0.0;
                String totalEstimateIncome = null;
                Map <String, String> hashMapForAcre = (Map <String, String>) strategyDataJsonObject.get ( "hashMapForAcre" );
                Map <String, String> hashMapForProfit = (Map <String, String>) strategyDataJsonObject.get ( "hashMapForProfit" );
                double sumOfEstIncomePerAcr = 0.0;
                Set <String> keySet = hashMapForAcre.keySet ();
                for (String cropKey : keySet) {
                    double acreage = Double.parseDouble ( AgricultureStandardUtils.removeAllCommas ( hashMapForAcre.get ( cropKey ).split ( " \\(" )[0] ) );
                    double profit = Double.parseDouble ( AgricultureStandardUtils.removeAllCommas ( hashMapForProfit.get ( cropKey ).split ( "\\(" )[0] ) );
                    if (acreage != 0.0 || profit != 0.0) {
                        sumOfEstIncomePerAcr += profit / acreage;
                        totalAcreage += Double.parseDouble ( AgricultureStandardUtils.removeAllCommas ( hashMapForAcre.get ( cropKey ).split ( " " )[0] ) );
                    }
                }
                if (totalAcreage != 0.0 || sumOfEstIncomePerAcr != 0.0) {
                    totalEstimateIncome = String.valueOf ( (AgricultureStandardUtils.doubleWithOneDecimal ( sumOfEstIncomePerAcr / totalAcreage )) );
                } else {
                    totalEstimateIncome = "NA";
                }

                table.addCell ( ReportTemplate.BoldHeaderBoxBorderTable.getDataCell ( totalEstimateIncome ) );
                table.addCell ( ReportTemplate.BoldHeaderBoxBorderTable.getDataCell ( AgricultureStandardUtils.commaSeparaterForDoublePrice ( totalAcreage ) ) );
                Iterator iterator = setForCropHeader.iterator ();
                HashMap selectedCropHashMap = new HashMap ();
                for (int i = 0; i < setForCropHeader.size (); i++) {
                    if (i < hashMapForAcre.size ()) {
                        for (String cropKey : keySet) {
                            i++;
                            selectedCropHashMap.put ( cropKey, hashMapForAcre.get ( cropKey ).split ( " " )[0] );
                        }
                    } else {
                        selectedCropHashMap.put ( i, null );
                    }
                }
                while (iterator.hasNext ()) {
                    String cropName = String.valueOf ( iterator.next () );
                    if (selectedCropHashMap.get ( cropName ) != null) {
                        table.addCell ( ReportTemplate.BoldHeaderBoxBorderTable.getDataCell ( "" + selectedCropHashMap.get ( cropName ) ) );
                    } else {
                        table.addCell ( ReportTemplate.BoldHeaderBoxBorderTable.getDataCell ( "--" ) );
                    }
                }

                /*for (String cropKey : keySet) {
                        table.addCell ( ReportTemplate.BoldHeaderBoxBorderTable.getDataCell ( hashMapForAcre.get ( cropKey ).split ( " " )[0] ) );
                    }*/
            }
            table.completeRow ();
        }

        return table;

    }

    private PdfPTable getResourcesTable() {
        int noOfResource = 0;
        PdfPTable table = null;
        //  Loading data for resources
        /**
         * @Chanegd - Abhishek
         * @date - 11-01-2016
         * @desc - According to the updated syntax of ReportDataPage2
         */
        /*List<CropResourceUsageView> resourceUsageForFarm = reportDataPage2.getResourceUsageForFarm();


        //  New table for resources     /#  Col size dynamic
        PdfPTable table = new PdfPTable(resourceUsageForFarm.size() + 3);
        table.setWidthPercentage(100);

        PdfPCell strategyCell = ReportTemplate.BoldHeaderBoxBorderTable.getHeaderCell("Strategy");
        strategyCell.setRowspan(3);
        table.addCell(strategyCell);

        PdfPCell estimatedIncomeCell = ReportTemplate.BoldHeaderBoxBorderTable.getHeaderCell("Est. Income");
        estimatedIncomeCell.setRowspan(3);
        table.addCell(estimatedIncomeCell);

        PdfPCell returnOnWorkingCapitalUse = ReportTemplate.BoldHeaderBoxBorderTable.getHeaderCell("Return on Working Capital");
        returnOnWorkingCapitalUse.setRowspan(3);
        *//**
         * @Added - Abhishek
         * @date - 12-1-2016
         * @desc - Blank cell for table
         *//*
//        blankCell.setBackgroundColor(BaseColor.LIGHT_GRAY);

        table.addCell(returnOnWorkingCapitalUse);


        // Add Resource Usage, colspan will be dynamic depending upon the number of resources
        PdfPCell cropAcreage = ReportTemplate.BoldHeaderBoxBorderTable.getHeaderCell("Resource Use");
        cropAcreage.setColspan(resourceUsageForFarm.size());
        table.addCell(cropAcreage);

        table.completeRow();*/


        //  Adding header for resources that are in use dynamically

        LinkedHashSet<String> setForResourceHeader = new LinkedHashSet<>();
        //PdfPCell resourceHeaderCell;
        // PdfPTable table1 = null;
        List<List> resourceListOfAllStrategy = new ArrayList<>();

        List<JSONObject> totalStrategy = reportDataPage2.getStrategiesDataForFarm();

        for (int i = 0; i < reportDataPage2.getStrategiesDataForFarm().size(); i++) {
            List<CropResourceUsageView> resourceListForSingleStrategy = (List<CropResourceUsageView>) reportDataPage2.getStrategiesDataForFarm().get(i).get("resourceList");
            resourceListOfAllStrategy.add(resourceListForSingleStrategy);
        }

        for (int i = 0; i < resourceListOfAllStrategy.size(); i++) {
            List<CropResourceUsageView> cropResourceUsageViews = resourceListOfAllStrategy.get(i);

            for (CropResourceUsageView cropResourceUsageView : cropResourceUsageViews) {
                PdfPCell resourceHeaderCell;
                String resourceName = "";
                if (cropResourceUsageView.isActive()) {
                    if (cropResourceUsageView.getCropResourceUse().equalsIgnoreCase("land")) {
                        //resourceHeaderCell = ReportTemplate.BoldHeaderBoxBorderTable.getDataCell(cropResourceUsageView.getCropResourceUse());
                        resourceName = cropResourceUsageView.getCropResourceUse();
                    } else if (cropResourceUsageView.getCropResourceUse().equalsIgnoreCase("capital")) {
                        //resourceHeaderCell = ReportTemplate.BoldHeaderBoxBorderTable.getDataCell("Working\n" + cropResourceUsageView.getCropResourceUse());
                        resourceName = cropResourceUsageView.getCropResourceUse();
                    } else {
                        //resourceHeaderCell = ReportTemplate.BoldHeaderBoxBorderTable.getDataCell(cropResourceUsageView.getCropResourceUse() + "\n" + cropResourceUsageView.getUoMResource());
                        resourceName = cropResourceUsageView.getCropResourceUse() + "\n" + cropResourceUsageView.getUoMResource();
                    }
                    // resourceHeaderCell.setRowspan(2);
//            resourceHeaderCell.setRotation(90);
//            resourceHeaderCell.rotate();
                    setForResourceHeader.add(resourceName);

                    //resourceName = cropResourceUsageView.getCropResourceUse() + "/n" + cropResourceUsageView.getUoMResource();
                }
            }
        }
        noOfResource = setForResourceHeader.size();
        table = generateHeaderForResource(noOfResource);
        Iterator iterator = setForResourceHeader.iterator();
        while (iterator.hasNext()) {
            PdfPCell resource = ReportTemplate.BoldHeaderBoxBorderTable.getDataCell("" + iterator.next());
            resource.setRowspan(2);
            table.addCell(resource);
        }

            table.completeRow();

            /**
             * @changed - Abhishek
             * @date - 11-01-2016
             * @desc - changed according to strategies data supplied by controller instead of baseline strategy
             */
            //  Adding Data for Resources
        /*for (JSONObject strategyDataJsonObject : allSelectedStrategiesData) {*/
            for (JSONObject strategyDataJsonObject : reportDataPage2.getStrategiesDataForFarm()) {
                table.addCell(ReportTemplate.BoldHeaderBoxBorderTable.getDataCell(((FarmCustomStrategyView) strategyDataJsonObject.get("farmCustomStrategy")).getStrategyName()));
                /**
                 * @changed - Abhishek
                 * @date - 11-02-2016
                 * @desc - Applied format of $xxx,xxx
                 */
                table.addCell(ReportTemplate.BoldHeaderBoxBorderTable.getDataCell("$" + strategyDataJsonObject.get("potentialProfit").toString()));
            /*table.addCell(ReportTemplate.BoldHeaderBoxBorderTable.getDataCell(""));*/
                /**
                 * @Added - Abhishek
                 * @date - 12-1-2016
                 * @desc - Blank cell for table
                 */
                Map<String, String> cropResourceUse = (Map<String, String>) strategyDataJsonObject.get("cropResourceUsed");
                Map<String, String> cropResourceUnused = (Map<String, String>) strategyDataJsonObject.get("cropResourceUnused");

                Double potentialProfit = Double.valueOf(AgricultureStandardUtils.removeAllCommas(strategyDataJsonObject.get("potentialProfit").toString()));
                double workingCapitalUsed = 0.0;
                String returnWorkingCapital = null;
                int index = 0;
                int sizeOfList = ((List<CropResourceUsageView>) strategyDataJsonObject.get("resourceList")).size();
                for (CropResourceUsageView cropResourceUsageView : (List<CropResourceUsageView>) strategyDataJsonObject.get("resourceList")) {
                    index++;

                    if (cropResourceUsageView.getCropResourceUse().equalsIgnoreCase("capital")) {

                        workingCapitalUsed = Double.parseDouble(AgricultureStandardUtils.removeAllCommas(cropResourceUse.get(cropResourceUsageView.getCropResourceUse())));
                    } else if (cropResourceUsageView.getCropResourceUse().equalsIgnoreCase("land")) {
                        workingCapitalUsed = Double.parseDouble(AgricultureStandardUtils.removeAllCommas(cropResourceUse.get(cropResourceUsageView.getCropResourceUse())));
                    }

                    if (index == sizeOfList) {
                        if (workingCapitalUsed != 0 && potentialProfit != 0) {
                            returnWorkingCapital = (AgricultureStandardUtils.doubleWithOneDecimal(potentialProfit / workingCapitalUsed).toString());
                        } else {
                            returnWorkingCapital = "NA";
                        }
                    }

                }
                table.addCell(ReportTemplate.BoldHeaderBoxBorderTable.getDataCell("" + returnWorkingCapital));

//                fetching data for specific resource
                Iterator iteratorForHeader = setForResourceHeader.iterator ();
                HashMap setOfUsedAcresAsDouble = new HashMap ();
                HashMap selectedUsedAcresAsDouble = new HashMap ();

                Map<String, String> cropResourceUseList = (Map<String, String>) strategyDataJsonObject.get("cropResourceUsed");

                for (CropResourceUsageView cropResourceUsageView : (List<CropResourceUsageView>) strategyDataJsonObject.get("resourceList")) {
                    if (cropResourceUsageView.isActive()) {
                        if (cropResourceUsageView.getCropResourceUse().equalsIgnoreCase("capital")) {
                            //table.addCell(ReportTemplate.BoldHeaderBoxBorderTable.getDataCell("$" + cropResourceUsageView.getCropResourceUseAmount()));
                            //AgricultureStandardUtils.removeAllCommas(cropResourceUseList.get(cropResourceUsageView.getCropResourceUse()));
                            setOfUsedAcresAsDouble.put(cropResourceUsageView.getCropResourceUse(), cropResourceUseList.get(cropResourceUsageView.getCropResourceUse()));
                        } else if (cropResourceUsageView.getCropResourceUse().equalsIgnoreCase("land")) {
                            // table.addCell(ReportTemplate.BoldHeaderBoxBorderTable.getDataCell("" + cropResourceUsageView.getCropResourceUseAmount()));
                            setOfUsedAcresAsDouble.put(cropResourceUsageView.getCropResourceUse(), cropResourceUseList.get(cropResourceUsageView.getCropResourceUse()));
                        } else {
                            // table.addCell(ReportTemplate.BoldHeaderBoxBorderTable.getDataCell("" + cropResourceUsageView.getCropResourceUseAmount()));
                            setOfUsedAcresAsDouble.put(cropResourceUsageView.getCropResourceUse(), cropResourceUseList.get(cropResourceUsageView.getCropResourceUse()));
                        }
                    }
                    /*else  {
                        table.addCell(ReportTemplate.BoldHeaderBoxBorderTable.getDataCell("--"));
                    }*/
                }

                 for (int i=0; i< setForResourceHeader.size(); i++) {

                    if (i< setOfUsedAcresAsDouble.size()) {
                        for (Object resource : setOfUsedAcresAsDouble.keySet() ) {
                            i++;
                            selectedUsedAcresAsDouble.put(resource, setOfUsedAcresAsDouble.get(resource));
                        }
                    } else {
                        selectedUsedAcresAsDouble.put(i, null);
                    }
                 }
                while (iteratorForHeader.hasNext ()) {
                    String resource = String.valueOf ( iteratorForHeader.next () ).split("\\n")[0];
                    if (selectedUsedAcresAsDouble.get ( resource)!=null) {
                        table.addCell ( ReportTemplate.BoldHeaderBoxBorderTable.getDataCell
                                ( "" + selectedUsedAcresAsDouble.get ( resource ) ) );
                    } else {
                        table.addCell ( ReportTemplate.BoldHeaderBoxBorderTable.getDataCell( ""+"--" ) );
                    }
                }
                //            List<CropResourceUsageView> resourceList = (List<CropResourceUsageView>) strategyDataJsonObject.get("resourceList");
                //            Map<String, String> cropResourceUsed = (Map<String, String>) reportDataPage2.getBaseSelectedStrategyOutputDetails().get("cropResourceUsed");
                //            for (CropResourceUsageView cropResourceUsageView : resourceList) {
                //                /**
                //                 * @changed - Abhishek
                //                 * @date - 11-02-2016
                //                 * @desc - Applied format of $xxx,xxx
                //                 */
                //
                //            }

                table.completeRow();

            }

            return table;
        }


    private PdfPTable getRiskManagementTable() {
        /**
         * @changed - Abhishek
         * @date - 12-01-2016
         * @desc - Making table columns dynamic according to # of scenarios
         */
        /*PdfPTable table = ReportTemplate.getFullWidthTable(9);*/
        PdfPTable table = ReportTemplate.getFullWidthTable((reportDataPage2.getTotalScenarioCount() == 0 ? 1 : reportDataPage2.getTotalScenarioCount()) + 4);

        PdfPCell strategyCell = ReportTemplate.BoldHeaderBoxBorderTable.getHeaderCell("Strategy");
        if (reportDataPage2.getTotalScenarioCount() !=0 ) {
            strategyCell.setRowspan(3);
        } else {
            strategyCell.setRowspan(2);
        }
        table.addCell(strategyCell);

        PdfPCell incomeCell = ReportTemplate.BoldHeaderBoxBorderTable.getHeaderCell("Est. Income");
        if (reportDataPage2.getTotalScenarioCount() !=0 ) {
            incomeCell.setRowspan(3);
        } else {
            incomeCell.setRowspan(2);
        }
        table.addCell(incomeCell);

        // Risk Management Cell, colspan depends on scenarios
        PdfPCell riskManagementCell = ReportTemplate.BoldHeaderBoxBorderTable.getHeaderCell("Risk Management");
        /**
         * @changed - Abhishek
         * @date - 12-01-2016
         * @desc - Making table columns dynamic according to # of scenarios
         */
        /*riskManagementCell.setColspan(7);*/
        riskManagementCell.setColspan(reportDataPage2.getTotalScenarioCount() + 3);
        table.addCell(riskManagementCell);

        table.completeRow();

//        PdfPCell estimateIncomeContractedCell = ReportTemplate.BoldHeaderBoxBorderTable.getHeaderCell("% Est Income Contracted");
        PdfPCell estimateIncomeContractedCell = ReportTemplate.BoldHeaderBoxBorderTable.getHeaderCell("% Est. Income Forward Sold");
        if (reportDataPage2.getTotalScenarioCount() !=0 ) {
            estimateIncomeContractedCell.setRowspan(2);
        } else {
            estimateIncomeContractedCell.setRowspan(1);
        }
        table.addCell(estimateIncomeContractedCell);

        /**
         * @changed - Abhishek
         * @date - 25-01-2016
         * @desc - changed according to slide# 2 of 12282015
         */
        /*PdfPCell coveredCropInsCell = ReportTemplate.BoldHeaderBoxBorderTable.getHeaderCell("% Covered w/xx Crop Ins");*/
//        PdfPCell coveredCropInsCell = ReportTemplate.BoldHeaderBoxBorderTable.getHeaderCell("% of profit in Hi-risk Crops");
        PdfPCell coveredCropInsCell = ReportTemplate.BoldHeaderBoxBorderTable.getHeaderCell("% of Est. Income in \n Hi-Risk Crops");
        if (reportDataPage2.getTotalScenarioCount() !=0 ) {
            coveredCropInsCell.setRowspan(2);
        } else {
            coveredCropInsCell.setRowspan(1);
        }
        table.addCell(coveredCropInsCell);

        /**
         * @changed - Abhishek
         * @date - 25-01-2016
         * @desc - changed according to slide# 2 of 12282015
         */
        /*PdfPCell acresCoveredCell = ReportTemplate.BoldHeaderBoxBorderTable.getHeaderCell("% Acres Covered w/yy Crop Ins");*/
        PdfPCell acresCoveredCell = ReportTemplate.BoldHeaderBoxBorderTable.getHeaderCell("% of Acreage in \n Hi-risk Crops");
        if (reportDataPage2.getTotalScenarioCount() !=0 ) {
            acresCoveredCell.setRowspan(2);
        } else {
            acresCoveredCell.setRowspan(1);
        }
        table.addCell(acresCoveredCell);

        /**
         * @changed - Abhishek
         * @date - 12-01-2016
         * @desc - Making table columns dynamic according to # of scenarios
         */
        PdfPCell scenarioAnalysisCell = ReportTemplate.BoldHeaderBoxBorderTable.getHeaderCell("Scenario Analysis");
        /*scenarioAnalysisCell.setColspan(4);*/
        scenarioAnalysisCell.setColspan(reportDataPage2.getTotalScenarioCount());
        table.addCell(scenarioAnalysisCell);

//        table.completeRow();

        List<JSONObject> strategiesDataForFarm = reportDataPage2.getStrategiesDataForFarm();
        List<FarmStrategyScenarioView> farmStrategyScenarioViewList = reportDataPage2.getAllScenarios();
        for (FarmStrategyScenarioView farmStrategyScenarioView : farmStrategyScenarioViewList) {
            if (farmStrategyScenarioView.getScenarioId().equals(reportDataPage2.getScenarioId())){
                PdfPCell scenarioHeaderCell = ReportTemplate.BoldHeaderBoxBorderTable.getHeaderCell(farmStrategyScenarioView.getScenarioName());
                table.addCell(scenarioHeaderCell);
            }

        }


        /*for (JSONObject strategyDataJsonObject : strategiesDataForFarm) {
            Map<FarmStrategyScenarioView, JSONObject> scenarioOutputMap = (Map<FarmStrategyScenarioView, JSONObject>) strategyDataJsonObject.get("scenarioDetails");
            Set<FarmStrategyScenarioView> farmStrategyScenarioViewSet = scenarioOutputMap.keySet();

//            if (reportDataPage2.getTotalScenarioCount() !=0 ) {
                for (FarmStrategyScenarioView farmStrategyScenarioView : farmStrategyScenarioViewSet) {

                    PdfPCell scenarioHeaderCell = ReportTemplate.BoldHeaderBoxBorderTable.getHeaderCell(farmStrategyScenarioView.getScenarioName());
                    if (reportDataPage2.getTotalScenarioCount() !=0 ) {
                        scenarioHeaderCell.setRowspan(2);
                    }
                    table.addCell(scenarioHeaderCell);

                }
//            } else {
//                PdfPCell scenarioHeaderCell = ReportTemplate.BoldHeaderBoxBorderTable.getBlankCell();
//                scenarioHeaderCell.setRowspan(2);
//                table.addCell(scenarioHeaderCell);
//            }

        }*/

        table.completeRow();

        /**
         * @chnaged - Abhishek
         * @date - 11-01-2016
         * @desc - changed according to strategies data supplied by controller instead of baseline strategy
         */
        //  Data for risk management table
        int emptyCellCount = 0;
        for (JSONObject strategyDataJsonObject : strategiesDataForFarm) {
            FarmCustomStrategyView farmCustomStrategyView = (FarmCustomStrategyView) strategyDataJsonObject.get("farmCustomStrategy");
            table.addCell(ReportTemplate.BoldHeaderBoxBorderTable.getDataCell(farmCustomStrategyView.getStrategyName()));
            /**
             * @changed - Abhishek
             * @date - 11-02-2016
             * @desc - Applied format of $xxx,xxx
             */
            table.addCell(ReportTemplate.BoldHeaderBoxBorderTable.getDataCell("$" + strategyDataJsonObject.get("potentialProfit").toString()));

            /**
             * @changed - Abhishek
             * @date - 12-01-2016
             * @desc - Added remaining data in table
             */
            /*DecimalFormat decimalFormatter = new DecimalFormat("#,00");
            if (farmCustomStrategyView.getFarmCustomStrategy().getFarmInfo().getStrategy().equals(PlanByStrategy.PLAN_BY_ACRES)) {
//                Double incomeContracted = 0.0;
                Double incomeUnderConservation = 0.0;
                Double landUnderConservation = 0.0;
                List<FarmOutputDetailsView> farmOutputDetailsViewList = (List<FarmOutputDetailsView>) strategyDataJsonObject.get("farmOutputDetails");
                for (FarmOutputDetailsView farmOutputDetailsView : farmOutputDetailsViewList) {
                    *//*if (farmOutputDetailsView.getCropTypeView().getFirmchecked().equalsIgnoreCase("true")) {
                        incomeContracted += farmOutputDetailsView.getUsedCapitalPercentage();
                    }*//*
                    if(farmOutputDetailsView.getCropTypeView().getConservation_Crop().equalsIgnoreCase("true")){
                        incomeUnderConservation += farmOutputDetailsView.getUsedCapitalPercentage();
                        landUnderConservation += farmOutputDetailsView.getUsedAcresPercentage();
                    }
                }

                *//**
                 * @changed - Abhishek
                 * @date - 12-01-2016
                 * @desc - Added remaining data in table
                 *//*
                Map<String, String> mapDifferentValues = (Map<String, String>) reportDataPage2.getBaseSelectedStrategyOutputDetails().get("mapDifferentValues");

                String usedForwardAcresP = mapDifferentValues.get("usedForwardAcresP");

                table.addCell(ReportTemplate.BoldHeaderBoxBorderTable.getDataCell(usedForwardAcresP));
                table.addCell(ReportTemplate.BoldHeaderBoxBorderTable.getDataCell(decimalFormatter.format(incomeUnderConservation)));
                table.addCell(ReportTemplate.BoldHeaderBoxBorderTable.getDataCell(decimalFormatter.format(landUnderConservation)));

            } else if (farmCustomStrategyView.getFarmCustomStrategy().getFarmInfo().getStrategy().equals(PlanByStrategy.PLAN_BY_FIELDS)){

                Double incomeForwardSold = 0.0;
                Double incomeUnderConservation = 0.0;
                Double landUnderConservation = 0.0;
                List<CropTypeView> cropTypeViewList = (List<CropTypeView>) strategyDataJsonObject.get("cropTypeView");
                Map<String, String> hashMapForProfit = (Map<String, String>) strategyDataJsonObject.get("hashMapForProfit");
                Map<String, String> hashMapForAcre = (Map<String, String>) strategyDataJsonObject.get("hashMapForAcre");

                Set<String> keySet = hashMapForAcre.keySet();
                for (String cropKey : keySet) {
                    for (CropTypeView cropTypeView : cropTypeViewList) {
                    *//*if (cropTypeView.getSelected() && cropTypeView.getFirmchecked().equalsIgnoreCase("true")) {
                        String income = hashMapForProfit.get(cropTypeView.getCropName());
                        incomeForwardSold += Double.parseDouble(income.substring(income.indexOf('(') + 1, income.indexOf('%')).replaceAll("\\,", ""));
                    }*//*
                        if (cropTypeView.getSelected()
                                && cropTypeView.getConservation_Crop().equalsIgnoreCase("true")
                                && cropKey.contains(cropKey)) {
                            String income = hashMapForProfit.get(cropKey);
                            incomeUnderConservation += Double.parseDouble(income.substring(income.indexOf('(') + 1, income.indexOf('%')).replaceAll("\\,", ""));
                            String land = hashMapForAcre.get(cropKey);
                            landUnderConservation += Double.parseDouble(land.substring(land.indexOf('(') + 1, land.indexOf('%')).replaceAll("\\,", ""));
                        }
                    }
                }




                *//**
                 * @changed - Abhishek
                 * @date - 12-01-2016
                 * @desc - Added percentage data for conservation management and crop contracted
                 *//*
                Map<String, String> mapDifferentValues = (Map<String, String>) reportDataPage2.getBaseSelectedStrategyOutputDetails().get("mapDifferentValues");

                String usedForwardAcresP = mapDifferentValues.get("usedForwardAcresP");

                table.addCell(ReportTemplate.BoldHeaderBoxBorderTable.getDataCell(usedForwardAcresP));
                table.addCell(ReportTemplate.BoldHeaderBoxBorderTable.getDataCell(decimalFormatter.format(incomeUnderConservation)));
                table.addCell(ReportTemplate.BoldHeaderBoxBorderTable.getDataCell(decimalFormatter.format(landUnderConservation)));

            }*/


//            Map<String, String> mapDifferentValues = (Map<String, String>) reportDataPage2.getBaseSelectedStrategyOutputDetails().get("mapDifferentValues");
            Map<String, String> mapDifferentValues = (Map<String, String>) strategyDataJsonObject.get("mapDifferentValues");
            String usedForwardAcresP = mapDifferentValues.get("usedForwardAcresP");
            table.addCell(ReportTemplate.BoldHeaderBoxBorderTable.getDataCell(usedForwardAcresP));

            Map<String, String> riskAnalysis = reportDataPage2.getRiskAndConservationAnalysis(strategyDataJsonObject);

            table.addCell(ReportTemplate.BoldHeaderBoxBorderTable.getDataCell(riskAnalysis.get("incomeUnderRisk")));
            table.addCell(ReportTemplate.BoldHeaderBoxBorderTable.getDataCell(riskAnalysis.get("landUnderRisk")));

            /**
             * @Added - Abhishek
             * @date - 12-01-2016
             * @desc - Added output details for scenario
             */
            for(int i = 0; i < emptyCellCount ; i++){

                PdfPCell blankPdfCell = ReportTemplate.BoldHeaderBoxBorderTable.getDataCell("");
                blankPdfCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                table.addCell(blankPdfCell);
            }

            JSONArray scenarioDetails = reportDataPage2.getScenarioDetails(farmCustomStrategyView);
            for (Object o : scenarioDetails) {
                JSONObject jsonObject = (JSONObject) o;
                PdfPCell scenarioCell = ReportTemplate.BoldHeaderBoxBorderTable.getDataCell(jsonObject.get("scenarioOutput").toString());
                table.addCell(scenarioCell);
            }

            /*JSONArray scenarioDetails = reportDataPage2.getScenarioDetails(farmCustomStrategyView.getId());

            for (Object o : scenarioDetails) {
                JSONObject jsonObject = (JSONObject) o;
                PdfPCell scenarioCell = ReportTemplate.BoldHeaderBoxBorderTable.getDataCell(jsonObject.get("scenarioOutput").toString());
                table.addCell(scenarioCell);
            }*/

//            /**
//             * @changed - Abhishek
//             * @date - 20-01-2016
//             * @desc - removed bug while generating risk management table if scenario not present in strategies then the view was not clear
//             */
//            /*if (scenarioOutputList.size() != 0) {*/
//                for (Object scenarioOutputObj : scenarioOutputList) {
//                    emptyCellCount++;
//
//                    JSONObject scenarioOutputDetails = (JSONObject) scenarioOutputObj;
//                    JSONObject scenarioOutputJsonObj = (JSONObject) scenarioOutputDetails.get("scenarioOutputDetails");
//                    /**
//                     * @changed - Abhishek
//                     * @date - 11-02-2016
//                     * @desc - Applied format of $xxx,xxx
//                     */
//                    PdfPCell scenarioCell = ReportTemplate.BoldHeaderBoxBorderTable.getDataCell("$" + scenarioOutputJsonObj.get("potentialProfit").toString());
//                    table.addCell(scenarioCell);
//
//                }
//            /**
//             * @changed - Abhishek
//             * @date - 20-01-2016
//             * @desc - removed bug while generating risk management table if scenario not present in strategies then the view was not clear
//             */
//            /*} else {
//                PdfPCell scenarioCell = ReportTemplate.BoldHeaderBoxBorderTable.getBlankCell();
//                table.addCell(scenarioCell);
//            }*/

            table.completeRow();

        }

        return table;

    }

    private PdfPTable getConservationManagementTable()  {
        //  New table for conservation management

        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);

        PdfPCell strategyCell = ReportTemplate.BoldHeaderBoxBorderTable.getHeaderCell("Strategy");
        strategyCell.setRowspan(3);
        table.addCell(strategyCell);

        PdfPCell estimatedIncomeCell = ReportTemplate.BoldHeaderBoxBorderTable.getHeaderCell("Est. Income");
        estimatedIncomeCell.setRowspan(3);
        table.addCell(estimatedIncomeCell);


        PdfPCell conservationGoals = ReportTemplate.BoldHeaderBoxBorderTable.getHeaderCell("Conservation Goals");
        conservationGoals.setColspan(2);
        table.addCell(conservationGoals);

        table.completeRow();

        PdfPCell incomeCell = ReportTemplate.BoldHeaderBoxBorderTable.getHeaderCell("% Est. Acreage Under\nConservation Practices");
        table.addCell(incomeCell);

        PdfPCell acreageCell = ReportTemplate.BoldHeaderBoxBorderTable.getHeaderCell("% Est. Income Under\nConservation Practices");
        table.addCell(acreageCell);

        PdfPCell emptyCell = ReportTemplate.BoldHeaderBoxBorderTable.getHeaderCell("");
        table.addCell(emptyCell);

        table.completeRow();

        /**
         * @chnaged - Abhishek
         * @date - 11-01-2016
         * @desc - changed according to strategies data supplied by controller instead of baseline strategy
         */
        //  Add Data to table
        List<JSONObject> strategiesDataForFarmList = reportDataPage2.getStrategiesDataForFarm();
        for (JSONObject strategyDataJsonObject : strategiesDataForFarmList) {
            FarmCustomStrategyView farmCustomStrategyView = (FarmCustomStrategyView) strategyDataJsonObject.get("farmCustomStrategy");
            table.addCell(ReportTemplate.BoldHeaderBoxBorderTable.getDataCell(farmCustomStrategyView.getStrategyName()));
            /**
             * @changed - Abhishek
             * @date - 11-02-2016
             * @desc - Applied format of $xxx,xxx
             */
            table.addCell(ReportTemplate.BoldHeaderBoxBorderTable.getDataCell("$" + strategyDataJsonObject.get("potentialProfit").toString()));

            /*Double landUnderConservation = 0.0;
            Double incomeUnderConservation = 0.0;
            Double totalUsedLand = 0.0;

            if (farmCustomStrategyView.getFarmCustomStrategy().getFarmInfo().getStrategy().equals(PlanByStrategy.PLAN_BY_ACRES)) {

                for (FarmOutputDetailsView farmOutputDetails : (List<FarmOutputDetailsView>) strategyDataJsonObject.get("farmOutputDetails")) {
                    if (farmOutputDetails.getCropTypeView().getConservation_Crop().equalsIgnoreCase("true")) {
                        landUnderConservation += farmOutputDetails.getUsedAcresPercentage();
                        incomeUnderConservation += farmOutputDetails.getUsedCapitalPercentage();
                    }

                    totalUsedLand += farmOutputDetails.getUsedAcresAsDouble();

                }


            } else if (farmCustomStrategyView.getFarmCustomStrategy().getFarmInfo().getStrategy().equals(PlanByStrategy.PLAN_BY_FIELDS)) {

                List<CropTypeView> cropTypeViewList = (List<CropTypeView>) strategyDataJsonObject.get("cropTypeView");
                Map<String, String> hashMapForAcre = (Map<String, String>) strategyDataJsonObject.get("hashMapForAcre");
                Map<String, String> hashMapForProfit = (Map<String, String>) strategyDataJsonObject.get("hashMapForProfit");

                Set<String> keySet = hashMapForAcre.keySet();
                for (String cropKey : keySet) {
                    for (CropTypeView cropTypeView : cropTypeViewList) {
                        if (cropTypeView.getSelected() && cropKey.contains(cropTypeView.getCropName())
                                && cropTypeView.getConservation_Crop().equalsIgnoreCase("true")) {
                            String land = hashMapForAcre.get(cropKey);
                            landUnderConservation += Double.parseDouble(land.substring(land.indexOf('(') + 1, land.indexOf('%')).replaceAll("\\,", ""));
                            String income = hashMapForProfit.get(cropKey);
                            incomeUnderConservation += Double.parseDouble(income.substring(income.indexOf('(') + 1, income.indexOf('%')).replaceAll("\\,", ""));
                        }
                    }

                }



            }

            *//*String potentialProfit = SectionOnePDFGenerator.getEstimatedIncome().replaceAll("\\,", "");
            landUnderConservation = (landUnderConservation * Double.parseDouble(potentialProfit)) / 100;
            incomeUnderConservation = (incomeUnderConservation * totalUsedLand) / 100;*//*

            DecimalFormat formatter = new DecimalFormat("#.00");*/

            Map<String, String> riskAnalysis = reportDataPage2.getRiskAndConservationAnalysis(strategyDataJsonObject);

            table.addCell(ReportTemplate.BoldHeaderBoxBorderTable.getDataCell(riskAnalysis.get("landUnderConservation")));
            table.addCell(ReportTemplate.BoldHeaderBoxBorderTable.getDataCell(riskAnalysis.get("incomeUnderConservation")));

            table.completeRow();
        }

        return table;
    }
}

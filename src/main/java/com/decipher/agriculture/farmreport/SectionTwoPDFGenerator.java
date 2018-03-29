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
        table.addCell(ReportTemplate.BoldHeaderBottomBorderTable.getHeaderCell("Crop"));
        table.addCell(ReportTemplate.BoldHeaderBottomBorderTable.getHeaderCell("UoM"));
        table.addCell(ReportTemplate.BoldHeaderBottomBorderTable.getHeaderCell("Est. Yield"));
        table.addCell(ReportTemplate.BoldHeaderBottomBorderTable.getHeaderCell("Est. Price"));
        table.addCell(ReportTemplate.BoldHeaderBottomBorderTable.getHeaderCell("Est. Var Costs"));
        table.addCell(ReportTemplate.BoldHeaderBottomBorderTable.getHeaderCell("Est. Income\nPer Acre"));
        table.addCell(ReportTemplate.BoldHeaderBottomBorderTable.getHeaderCell("Forward Sales"));
        table.addCell(ReportTemplate.BoldHeaderBottomBorderTable.getHeaderCell("Forward Sales Price"));
        table.addCell(ReportTemplate.BoldHeaderBottomBorderTable.getHeaderCell("Forward Sales Quantity"));
        table.addCell(ReportTemplate.BoldHeaderBottomBorderTable.getHeaderCell("Forward Acres"));
        table.addCell(ReportTemplate.BoldHeaderBottomBorderTable.getHeaderCell("Min Crop\nAcreage Limits"));
//        table.addCell(ReportTemplate.BoldHeaderBottomBorderTable.getHeaderCell("Max Crop\nAcreage Limits"));
        table.addCell(ReportTemplate.BoldHeaderBottomBorderTable.getHeaderCell("Other Resources"));

        // Add Data Dynamically
        /**
         * @Chanegd - Abhishek
         * @date - 11-01-2016
         * @desc - According to the updated syntax of ReportDataPage2
         */
        List<CropTypeView> cropTypeViewList = reportDataPage2.getCropDetails();
        for (CropTypeView crop : cropTypeViewList){
            if(crop.getSelected()){
                table.addCell(ReportTemplate.BoldHeaderBottomBorderTable.getDataCell(crop.getCropName()));
                table.addCell(ReportTemplate.BoldHeaderBottomBorderTable.getDataCell(crop.getCropUOM()));

                PdfPCell cropYieldDataCell = ReportTemplate.BoldHeaderBottomBorderTable.getDataCell(crop.getIntExpCropYield());
                cropYieldDataCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                table.addCell(cropYieldDataCell);

                PdfPCell priceDataCell = ReportTemplate.BoldHeaderBottomBorderTable.getDataCell(crop.getIntExpCropPrice() != null ? AgricultureStandardUtils.commaSeparatedForPriceWithThreeDecimal(crop.getIntExpCropPrice().toString()) : "--");
                priceDataCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                table.addCell(priceDataCell);

                table.addCell(ReportTemplate.BoldHeaderBottomBorderTable.getDataCell(crop.getCalculatedVariableProductionCostFloat()));
                table.addCell(ReportTemplate.BoldHeaderBottomBorderTable.getDataCell(crop.getCalculatedProfitString() != null ? crop.getCalculatedProfitString() : "--"));
                if(crop.getFirmchecked().equalsIgnoreCase("true")){
                    table.addCell(ReportTemplate.BoldHeaderBottomBorderTable.getDataCell("Yes"));
                    table.addCell(ReportTemplate.BoldHeaderBottomBorderTable.getDataCell(crop.getPriceStr()));
                    table.addCell(ReportTemplate.BoldHeaderBottomBorderTable.getDataCell(crop.getQuantityStr()));
                    table.addCell(ReportTemplate.BoldHeaderBottomBorderTable.getDataCell(crop.getAcresStr()));
                } else if(crop.getProposedchecked()){
                    table.addCell(ReportTemplate.BoldHeaderBottomBorderTable.getDataCell("Yes"));
                    table.addCell(ReportTemplate.BoldHeaderBottomBorderTable.getDataCell(crop.getPriceStr()));
                    table.addCell(ReportTemplate.BoldHeaderBottomBorderTable.getDataCell(crop.getQuantityStr()));
                    table.addCell(ReportTemplate.BoldHeaderBottomBorderTable.getDataCell(crop.getAcresStr()));
                } else {
                    table.addCell(ReportTemplate.BoldHeaderBottomBorderTable.getDataCell("No"));
                    table.addCell(ReportTemplate.BoldHeaderBottomBorderTable.getDataCell(""));
                    table.addCell(ReportTemplate.BoldHeaderBottomBorderTable.getDataCell(""));
                    table.addCell(ReportTemplate.BoldHeaderBottomBorderTable.getDataCell(""));
                }


                table.addCell(ReportTemplate.BoldHeaderBottomBorderTable.getDataCell(crop.getMinimumAcres()));
//                table.addCell(ReportTemplate.BoldHeaderBottomBorderTable.getDataCell(crop.getMaximumAcres()));
                table.addCell(ReportTemplate.BoldHeaderBottomBorderTable.getDataCell(""));
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
        PdfPCell estimatedIncomeCell = ReportTemplate.BoldHeaderBoxBorderTable.getHeaderCell("Est Income");
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

        if (reportDataPage2.getFarmInfoView().getStrategy().equals(PlanByStrategy.PLAN_BY_ACRES)) {

            List<FarmOutputDetailsView> farmOutputDetailsViewList = (List<FarmOutputDetailsView>) reportDataPage2.getStrategiesDataForFarm().get(0).get("farmOutputDetails");
            noOfCrops = farmOutputDetailsViewList.size();

            table = generateHeaderForEstimatedIncomeCropAcreage(noOfCrops);

            // Now Add Crops Names
            for (FarmOutputDetailsView farmOutputDetailsView : farmOutputDetailsViewList) {
                String cropName = "";
                if(farmOutputDetailsView.getForProposed()){
                    cropName = farmOutputDetailsView.getCropTypeView().getCropName() + " (Proposed)";
                } else if(farmOutputDetailsView.getForFirm()){
                    cropName = farmOutputDetailsView.getCropTypeView().getCropName() + " (Firm)";
                } else {
                    cropName = farmOutputDetailsView.getCropTypeView().getCropName();
                }
                PdfPCell crop = ReportTemplate.BoldHeaderBoxBorderTable.getDataCell(cropName);
                crop.setRowspan(2);
//                crop.setRotation(90);
//                crop.rotate();
                table.addCell(crop);
            }

            table.completeRow();

        } else if (reportDataPage2.getFarmInfoView().getStrategy().equals(PlanByStrategy.PLAN_BY_FIELDS)) {

            Map<String, String> hashMapForAcre = (Map<String, String>) reportDataPage2.getBaseSelectedStrategyOutputDetails().get("hashMapForAcre");


            table = generateHeaderForEstimatedIncomeCropAcreage(hashMapForAcre.size());

            for (String cropKey : hashMapForAcre.keySet()) {
                PdfPCell crop = ReportTemplate.BoldHeaderBoxBorderTable.getDataCell(cropKey);
                crop.setRowspan(2);
//                    crop.setRotation(90);
//                    crop.rotate();
                table.addCell(crop);
            }

        }


        // Add dynamic data now
        List<JSONObject> strategiesDataForFarmList = reportDataPage2.getStrategiesDataForFarm();
        for (JSONObject strategyDataJsonObject : strategiesDataForFarmList){

            /**
              * @changed - Abhishek
              * @date - 11-01-2016
              */
            FarmCustomStrategyView farmCustomStrategyView = (FarmCustomStrategyView) strategyDataJsonObject.get("farmCustomStrategy");
            table.addCell(ReportTemplate.BoldHeaderBoxBorderTable.getDataCell(farmCustomStrategyView.getStrategyName()));
            /**
             * @changed - Abhishek
             * @date - 11-02-2016
             * @desc - Applied format of $xxx,xxx
             */
            table.addCell(ReportTemplate.BoldHeaderBoxBorderTable.getDataCell("$" + strategyDataJsonObject.get("potentialProfit").toString()));



            List<FarmOutputDetailsView> farmOutputDetailsViewList1 = (List<FarmOutputDetailsView>) strategyDataJsonObject.get("farmOutputDetails");
            Double totalAcerage1 = 0.0;
            Double totalProfit=0.0;
            Double estimatePerAcr  =0.0;

            for (FarmOutputDetailsView farmOutputDetailsView1 : farmOutputDetailsViewList1) {
                totalAcerage1 += farmOutputDetailsView1.getUsedAcresDouble ();
                totalProfit = farmOutputDetailsView1.getProfitDouble ();
               if(totalAcerage1!=0.0 && totalProfit!=0.0)
               {
                   estimatePerAcr = totalProfit / totalAcerage1;
                   table.addCell ( ReportTemplate.BoldHeaderBoxBorderTable.getDataCell ( AgricultureStandardUtils.commaSeparaterForDoublePrice ( estimatePerAcr ) ) );
               }else{
                   break;
               }
            }


Crop            if (farmCustomStrategyView.getFarmCustomStrategy().getFarmInfo().getStrategy().equals(PlanByStrategy.PLAN_BY_ACRES)) {
                List<FarmOutputDetailsView> farmOutputDetailsViewList = (List<FarmOutputDetailsView>) strategyDataJsonObject.get("farmOutputDetails");
                Double totalAcerage = 0.0;
                for (FarmOutputDetailsView farmOutputDetailsView : farmOutputDetailsViewList) {
                    if (farmOutputDetailsView.getCropTypeView().getSelected()) {
                        totalAcerage += farmOutputDetailsView.getUsedAcresDouble();
                    }
                }

                table.addCell(ReportTemplate.BoldHeaderBoxBorderTable.getDataCell(AgricultureStandardUtils.commaSeparaterForDoublePrice(totalAcerage)));

                for (FarmOutputDetailsView farmOutputDetailsView : farmOutputDetailsViewList) {
                    if (farmOutputDetailsView.getCropTypeView().getSelected()) {
                        table.addCell(ReportTemplate.BoldHeaderBoxBorderTable.getDataCell(farmOutputDetailsView.getUsedAcresAsDouble().toString()));
                    }
                }

            } else if (farmCustomStrategyView.getFarmCustomStrategy().getFarmInfo().getStrategy().equals(PlanByStrategy.PLAN_BY_FIELDS)){

                Double totalAcerage = 0.0;
                Map<String, String> hashMapForAcre = (Map<String, String>) strategyDataJsonObject.get("hashMapForAcre");

                Set<String> keySet = hashMapForAcre.keySet();
                for (String cropKey : keySet) {
                    totalAcerage += Double.parseDouble(AgricultureStandardUtils.removeAllCommas(hashMapForAcre.get(cropKey).split(" ")[0]));
                }

                table.addCell(ReportTemplate.BoldHeaderBoxBorderTable.getDataCell(totalAcerage.toString()));

                for (String cropKey : keySet) {
                    table.addCell(ReportTemplate.BoldHeaderBoxBorderTable.getDataCell(hashMapForAcre.get(cropKey).split(" ")[0]));
                }

            }

            table.completeRow();
        }

        return table;
    }

    private PdfPTable getResourcesTable() {
        //  Loading data for resources
        /**
         * @Chanegd - Abhishek
         * @date - 11-01-2016
         * @desc - According to the updated syntax of ReportDataPage2
         */
        List<CropResourceUsageView> resourceUsageForFarm = reportDataPage2.getResourceUsageForFarm();


        //  New table for resources     /#  Col size dynamic
        PdfPTable table = new PdfPTable(resourceUsageForFarm.size() + 3);
        table.setWidthPercentage(100);

        PdfPCell strategyCell = ReportTemplate.BoldHeaderBoxBorderTable.getHeaderCell("Strategy");
        strategyCell.setRowspan(3);
        table.addCell(strategyCell);

        PdfPCell estimatedIncomeCell = ReportTemplate.BoldHeaderBoxBorderTable.getHeaderCell("Est. Income");
        estimatedIncomeCell.setRowspan(3);
        table.addCell(estimatedIncomeCell);

        PdfPCell blankCell = ReportTemplate.BoldHeaderBoxBorderTable.getHeaderCell(" ");
        blankCell.setRowspan(3);
        /**
         * @Added - Abhishek
         * @date - 12-1-2016
         * @desc - Blank cell for table
         */
        blankCell.setBackgroundColor(BaseColor.LIGHT_GRAY);

        table.addCell(blankCell);


        // Add Resource Usage, colspan will be dynamic depending upon the number of resources
        PdfPCell cropAcreage = ReportTemplate.BoldHeaderBoxBorderTable.getHeaderCell("Resource Use");
        cropAcreage.setColspan(resourceUsageForFarm.size());
        table.addCell(cropAcreage);

        table.completeRow();



        //  Adding header for resources that are in use
        for (CropResourceUsageView cropResourceUsageView : resourceUsageForFarm) {
            PdfPCell resourceHeaderCell;
            if(cropResourceUsageView.getCropResourceUse().equalsIgnoreCase("capital"))
                resourceHeaderCell = ReportTemplate.BoldHeaderBoxBorderTable.getDataCell("Working\n" + cropResourceUsageView.getCropResourceUse());
            else
                resourceHeaderCell = ReportTemplate.BoldHeaderBoxBorderTable.getDataCell(cropResourceUsageView.getCropResourceUse());
            resourceHeaderCell.setRowspan(2);
//            resourceHeaderCell.setRotation(90);
//            resourceHeaderCell.rotate();
            table.addCell(resourceHeaderCell);
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
            table.addCell(ReportTemplate.BoldHeaderBoxBorderTable.getDataCell(((FarmCustomStrategyView)strategyDataJsonObject.get("farmCustomStrategy")).getStrategyName()));
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
            table.addCell(ReportTemplate.BoldHeaderBoxBorderTable.getBlankCell());
            List<CropResourceUsageView> resourceList = (List<CropResourceUsageView>) strategyDataJsonObject.get("resourceList");
            Map<String, String> cropResourceUsed = (Map<String, String>) reportDataPage2.getBaseSelectedStrategyOutputDetails().get("cropResourceUsed");
            for (CropResourceUsageView cropResourceUsageView : resourceList) {
                /**
                 * @changed - Abhishek
                 * @date - 11-02-2016
                 * @desc - Applied format of $xxx,xxx
                 */
                if(cropResourceUsageView.getCropResourceUse().equalsIgnoreCase("capital")){
                    table.addCell(ReportTemplate.BoldHeaderBoxBorderTable.getDataCell("$" + cropResourceUsed.get(cropResourceUsageView.getCropResourceUse())));
                } else {
                    table.addCell(ReportTemplate.BoldHeaderBoxBorderTable.getDataCell(cropResourceUsed.get(cropResourceUsageView.getCropResourceUse())));
                }
            }

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
        PdfPTable table = ReportTemplate.getFullWidthTable((reportDataPage2.getTotalScenarioCount() == 0 ? 1 : reportDataPage2.getTotalScenarioCount()) + 5);

        PdfPCell strategyCell = ReportTemplate.BoldHeaderBoxBorderTable.getHeaderCell("Strategy");
        if (reportDataPage2.getTotalScenarioCount() !=0 ) {
            strategyCell.setRowspan(4);
        } else {
            strategyCell.setRowspan(2);
        }
        table.addCell(strategyCell);

        PdfPCell incomeCell = ReportTemplate.BoldHeaderBoxBorderTable.getHeaderCell("Est Income");
        if (reportDataPage2.getTotalScenarioCount() !=0 ) {
            incomeCell.setRowspan(4);
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
        PdfPCell estimateIncomeContractedCell = ReportTemplate.BoldHeaderBoxBorderTable.getHeaderCell("% Est Income Forward Sold");
        if (reportDataPage2.getTotalScenarioCount() !=0 ) {
            estimateIncomeContractedCell.setRowspan(3);
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
        PdfPCell coveredCropInsCell = ReportTemplate.BoldHeaderBoxBorderTable.getHeaderCell("% of Est Income in \n Hi-Risk Crops");
        if (reportDataPage2.getTotalScenarioCount() !=0 ) {
            coveredCropInsCell.setRowspan(3);
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
            acresCoveredCell.setRowspan(3);
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
            PdfPCell scenarioHeaderCell = ReportTemplate.BoldHeaderBoxBorderTable.getHeaderCell(farmStrategyScenarioView.getScenarioName());
            if (reportDataPage2.getTotalScenarioCount() !=0 ) {
                scenarioHeaderCell.setRowspan(2);
            }
            table.addCell(scenarioHeaderCell);
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


            Map<String, String> mapDifferentValues = (Map<String, String>) reportDataPage2.getBaseSelectedStrategyOutputDetails().get("mapDifferentValues");
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

        PdfPCell estimatedIncomeCell = ReportTemplate.BoldHeaderBoxBorderTable.getHeaderCell("Est Income");
        estimatedIncomeCell.setRowspan(3);
        table.addCell(estimatedIncomeCell);


        PdfPCell conservationGoals = ReportTemplate.BoldHeaderBoxBorderTable.getHeaderCell("Conservation Goals");
        conservationGoals.setColspan(2);
        table.addCell(conservationGoals);

        table.completeRow();

        PdfPCell incomeCell = ReportTemplate.BoldHeaderBoxBorderTable.getHeaderCell("% Est Acreage Under\nConservation Practices");
        table.addCell(incomeCell);

        PdfPCell acreageCell = ReportTemplate.BoldHeaderBoxBorderTable.getHeaderCell("% Est Income Under\nConservation Practices");
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

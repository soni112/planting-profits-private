package com.decipher.agriculture.farmreport;

import com.decipher.agriculture.data.farm.PlanByStrategy;
import com.decipher.util.AgricultureStandardUtils;
import com.decipher.util.PlantingProfitLogger;
import com.decipher.view.form.account.AccountView;
import com.decipher.view.form.farmDetails.CropTypeView;
import com.decipher.view.form.farmDetails.FarmInfoView;
import com.decipher.view.form.farmDetails.FarmOutputDetailsView;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import org.codehaus.jettison.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;

/**
 * Created by Satan on 11/30/2015.
 */
public class SectionOnePDFGenerator {

    private AccountView accountView;
    private FarmInfoView farmInfoView;

    /**
     * @chanege - Abhishek
     * @date - 09-12-2015
     */
    private static String selectedStrategy;
    private static String estimatedIncome;
//	public static int index=0;

    public static Double getEstimateIncomeInDouble() {
        return estimateIncomeInDouble;
    }

    public static void setEstimateIncomeInDouble(Double estimateIncomeInDouble) {
        SectionOnePDFGenerator.estimateIncomeInDouble = estimateIncomeInDouble;
    }

    Double acrease = 0.0;
    Double totalProfit = 0.0;

    private static Double estimateIncomeInDouble;
    private ReportDataPage1 reportDataPage1;
    private SectionTwoPDFGenerator sectionTwoPDFGenerator;
    private List <String> profitList;

    public SectionOnePDFGenerator(SectionTwoPDFGenerator sectionTwoPDFGenerator, AccountView accountView, FarmInfoView farmInfoView, ReportDataPage1 reportDataPage1, String selectedStrategy, String estimatedIncome) {
        this.accountView = accountView;
        this.farmInfoView = farmInfoView;
        this.reportDataPage1 = reportDataPage1;
        this.selectedStrategy = selectedStrategy;
        this.estimatedIncome = estimatedIncome;
        this.sectionTwoPDFGenerator = sectionTwoPDFGenerator;
    }

    /**
     * @chanege - Abhishek
     * @date - 09-12-2015
     */
    public static String getSelectedStrategy() {
        return selectedStrategy;
    }

    public void setSelectedStrategy(String selectedStrategy) {
        this.selectedStrategy = selectedStrategy;
    }

    /**
     * @chanege - Abhishek
     * @date - 09-12-2015
     */
    public static String getEstimatedIncome() {
        return estimatedIncome;
    }

    public void setEstimatedIncome(String estimatedIncome) {
        this.estimatedIncome = estimatedIncome;
    }

    public ReportDataPage1 getReportDataPage1() {
        return reportDataPage1;
    }

    public FarmInfoView getFarmInfoView() {
        return farmInfoView;
    }

    public AccountView getAccountView() {
        return accountView;
    }

    public void buildSectionOne(Document document) throws DocumentException, IOException, JSONException {
        // Create Strategy & Estimate Income
        PdfPTable titleTable = new PdfPTable ( 1 );
        titleTable.setWidthPercentage ( 100 );

        // Strategy
        titleTable.addCell ( ReportTemplate.getSectionHeaderCell ( "Featured Strategy:" + selectedStrategy ) );

        /**
         * @changed - Abhishek
         * @date - 11-02-2016
         * @desc - Applied format of $xxx,xxx
         */
        // Estimate Income
        titleTable.addCell ( ReportTemplate.getSectionSubHeaderCellWithBoxBorder ( "Estimated Income: $" + estimatedIncome ) );

        document.add ( titleTable );

        // Add Charts Now
        PdfPTable contentTable = new PdfPTable ( 2 );
        contentTable.setWidthPercentage ( 100 );

        // Create 3d Pie Chart Section
        PdfPCell pieChartSectionCell = ReportTemplate.getBoxBorderCell ();
        pieChartSectionCell.addElement ( getPieChartSection () );
        contentTable.addCell ( pieChartSectionCell );

        // Create Bar Chart Section
        PdfPCell barChartSectionCell = ReportTemplate.getBoxBorderCell ();
        barChartSectionCell.addElement ( getResourceManagementTable () );
        contentTable.addCell ( barChartSectionCell );
        document.add ( contentTable );
        //document.newPage();

        PdfPTable contentTable2 = new PdfPTable ( 2 );
        contentTable2.setWidthPercentage ( 100 );

        // Add Risk Management Section
        PdfPCell riskManagementSectionCell = ReportTemplate.getBoxBorderWithoutLeftPaddingCell ();
        riskManagementSectionCell.addElement ( getRiskManagementTable () );
        contentTable2.addCell ( riskManagementSectionCell );

        // Add Conservation Management Section

        Paragraph conservationParagraph = new Paragraph ();

        conservationParagraph.add ( new Chunk ( "Conservation Management\n\n", ReportTemplate.TIMESROMAN_12_BOLD ) );
        conservationParagraph.add ( new Chunk ( "Conservation Goals\n", ReportTemplate.TIMESROMAN_10_BOLD ) );
        /**
         * @changed - Abhishek
         * @date - 12-12-2015
         * @updated - 11-01-2016
         */
        ReportDataPage1.ConservationPracticeBean conservationBean = reportDataPage1.getLandUnderConservationPractice ();

        /**
         * @changed - Abhishek
         * @updated - 11-01-2016
         */
        conservationParagraph.add ( new Chunk ( conservationBean.getProfitFromConservation () + " % Est. Income under conservation practices\n" +
                conservationBean.getLandUnderConservation () + " % Acreage under conservation practices", ReportTemplate.TIMESROMAN_10_NORMAL ) );

        PdfPCell conservationManagementSectionCell = ReportTemplate.getBoxBorderWithoutLeftPaddingCell ();
        conservationManagementSectionCell.addElement ( conservationParagraph );
        contentTable2.addCell ( conservationManagementSectionCell );

        document.add ( contentTable2 );


        /*getIncomeUnderConservationPractice(farmInfoView);*/

    }

    private PdfPTable getResourceManagementTable() throws IOException, BadElementException {
        PdfPTable resourceManagementTable = new PdfPTable ( 1 );
        resourceManagementTable.setWidthPercentage ( 100 );

        PdfPCell resoucekManagementCell = new PdfPCell ( new Phrase ( "Resource Management", ReportTemplate.TIMESROMAN_12_NORMAL ) );
        resoucekManagementCell.setUseBorderPadding ( true );
        resoucekManagementCell.setBorderWidth ( 0 );
        //cropsAcreageCell.setPaddingLeft(10);
        resourceManagementTable.addCell ( resoucekManagementCell );

        /**
         * @changed - Abhishek
         * @date - 09-01-2016
         * According to the updated syntax of ReportDataPage1
         */
        PdfPCell resourceManagementGraphCell = new PdfPCell ( reportDataPage1.getResourceManagementHorizontalBarChart (), false );
        resourceManagementGraphCell.setUseBorderPadding ( true );
        resourceManagementGraphCell.setBorderWidth ( 0 );
        resourceManagementGraphCell.setMinimumHeight ( 150 );
        //cropsAcreageCell.setPaddingLeft(10);
        resourceManagementTable.addCell ( resourceManagementGraphCell );

        // Resource Table
        PdfPTable resourceTable = new PdfPTable ( 4 );
        resourceTable.setWidthPercentage ( 100 );

        // Create header Resource , Used, Unused
        PdfPCell resourceCell = new PdfPCell ( new Phrase ( "Resource", ReportTemplate.TIMESROMAN_10_NORMAL ) );
        resourceCell.setBorderWidth ( 0.5f );
        resourceCell.setBorderColor ( new BaseColor ( 131, 154, 103 ) );
        resourceCell.setBorder ( Rectangle.BOX );
        resourceCell.setUseBorderPadding ( true );
        resourceCell.setBackgroundColor ( new BaseColor ( 247, 205, 114 ) );
        resourceTable.addCell ( resourceCell );

        PdfPCell totalAmountCell = new PdfPCell ( new Phrase ( "Amount Available", ReportTemplate.TIMESROMAN_10_NORMAL ) );
        totalAmountCell.setBorderWidth ( 0.5f );
        totalAmountCell.setBorderColor ( new BaseColor ( 131, 154, 103 ) );
        totalAmountCell.setBorder ( Rectangle.BOX );
        totalAmountCell.setUseBorderPadding ( true );
        totalAmountCell.setBackgroundColor ( new BaseColor ( 247, 205, 114 ) );
        resourceTable.addCell ( totalAmountCell );

        PdfPCell usedCell = new PdfPCell ( new Phrase ( "Used", ReportTemplate.TIMESROMAN_10_NORMAL ) );
        usedCell.setBorderWidth ( 0.5f );
        usedCell.setBorderColor ( new BaseColor ( 131, 154, 103 ) );
        usedCell.setBorder ( Rectangle.BOX );
        usedCell.setUseBorderPadding ( true );
        usedCell.setBackgroundColor ( new BaseColor ( 247, 205, 114 ) );
        resourceTable.addCell ( usedCell );

        PdfPCell unusedCell = new PdfPCell ( new Phrase ( "Unused", ReportTemplate.TIMESROMAN_10_NORMAL ) );
        unusedCell.setBorderWidth ( 0.5f );
        unusedCell.setBorderColor ( new BaseColor ( 131, 154, 103 ) );
        unusedCell.setBorder ( Rectangle.BOX );
        unusedCell.setUseBorderPadding ( true );
        unusedCell.setBackgroundColor ( new BaseColor ( 247, 205, 114 ) );
        resourceTable.addCell ( unusedCell );

        resourceTable.completeRow ();

        Map <String, Object> resourceTotalList = new HashMap <String, Object> ();

        String estimatedIncommeFormatted = estimatedIncome.replaceAll ( "\\$", "" );
        estimatedIncommeFormatted = estimatedIncommeFormatted.replaceAll ( "\\,", "" );

        /**
         * @changed - Abhishek
         * @date - 09-01-2016
         * According to the updated syntax of ReportDataPage1
         */
        // Create table data dynamically
        List <HashMap <String, String>> resourceUsages = reportDataPage1.getResourceUsageViewByFarm ();
        for (int i = 0; i < resourceUsages.size (); i++) {

            /**
             * @changed - Abhishek
             * @date - 12-12-2015
             */

            PdfPCell resourceDataCell = null;
            if (resourceUsages.get ( i ).get ( "resource" ).equalsIgnoreCase ( "capital ($)" )) {
                resourceDataCell = new PdfPCell ( new Phrase ( "Working " + resourceUsages.get ( i ).get ( "resource" ), ReportTemplate.TIMESROMAN_10_NORMAL ) );
            } else {
                resourceDataCell = new PdfPCell ( new Phrase ( resourceUsages.get ( i ).get ( "resource" ), ReportTemplate.TIMESROMAN_10_NORMAL ) );
            }
            resourceDataCell.setBorderWidth ( 0.5f );
            resourceDataCell.setBorderColor ( new BaseColor ( 131, 154, 103 ) );
            resourceDataCell.setBorder ( Rectangle.BOX );
            resourceDataCell.setUseBorderPadding ( true );
            resourceDataCell.setBackgroundColor ( new BaseColor ( 223, 240, 216 ) );
            resourceTable.addCell ( resourceDataCell );

            PdfPCell totalAmountDataCell = new PdfPCell ( new Phrase ( resourceUsages.get ( i ).get ( "totalAmount" ), ReportTemplate.TIMESROMAN_10_NORMAL ) );
            totalAmountDataCell.setBorderWidth ( 0.5f );
            totalAmountDataCell.setBorderColor ( new BaseColor ( 131, 154, 103 ) );
            totalAmountDataCell.setBorder ( Rectangle.BOX );
            totalAmountDataCell.setUseBorderPadding ( true );
            totalAmountDataCell.setBackgroundColor ( new BaseColor ( 223, 240, 216 ) );
            resourceTable.addCell ( totalAmountDataCell );

            PdfPCell amountDataCell = new PdfPCell ( new Phrase ( resourceUsages.get ( i ).get ( "used" ), ReportTemplate.TIMESROMAN_10_NORMAL ) );
            amountDataCell.setBorderWidth ( 0.5f );
            amountDataCell.setBorderColor ( new BaseColor ( 131, 154, 103 ) );
            amountDataCell.setBorder ( Rectangle.BOX );
            amountDataCell.setUseBorderPadding ( true );
            amountDataCell.setBackgroundColor ( new BaseColor ( 223, 240, 216 ) );
            resourceTable.addCell ( amountDataCell );

            PdfPCell filledDataCell = new PdfPCell ( new Phrase ( resourceUsages.get ( i ).get ( "unused" ), ReportTemplate.TIMESROMAN_10_NORMAL ) );
            filledDataCell.setBorderWidth ( 0.5f );
            filledDataCell.setBorderColor ( new BaseColor ( 131, 154, 103 ) );
            filledDataCell.setBorder ( Rectangle.BOX );
            filledDataCell.setUseBorderPadding ( true );
            filledDataCell.setBackgroundColor ( new BaseColor ( 223, 240, 216 ) );
            resourceTable.addCell ( filledDataCell );

            resourceTable.completeRow ();
        }

        PdfPCell resourceTableCell = new PdfPCell ( resourceTable );
        resourceTableCell.setUseBorderPadding ( true );
        resourceTableCell.setBorderWidth ( 0 );
        //cropsAcreageCell.setPaddingLeft(10);
        resourceManagementTable.addCell ( resourceTableCell );


        Paragraph resourceParagraph = new Paragraph ();
        resourceParagraph.add ( new Chunk ( "Return on Resources\n\n", ReportTemplate.TIMESROMAN_10_BOLD ) );

        /**
         * @changed - Abhishek
         * @date - 09-01-2016
         * According to the updated syntax of ReportDataPage1
         * @decs - Added decimal formatter for decimal values upto two decimal values
         */
        DecimalFormat formatter = new DecimalFormat ( "#.00" );
        for (HashMap <String, String> resourceUsage : resourceUsages) {
            Double total = Double.parseDouble ( resourceUsage.get ( "used" ).replaceAll ( "\\,", "" ) )
                    + Double.parseDouble ( resourceUsage.get ( "unused" ).replaceAll ( "\\,", "" ) );

            if (resourceUsage.get ( "resource" ).equalsIgnoreCase ( "land (Acres)" )) {
                resourceParagraph.add ( new Chunk ( "Return on " + resourceUsage.get ( "resource" )
                        + ": $" + formatter.format ( Double.parseDouble  ( estimatedIncommeFormatted ) / total )
                        + " of gross income per one acre\n", ReportTemplate.TIMESROMAN_10_NORMAL ) );
            } else if (resourceUsage.get ( "resource" ).equalsIgnoreCase ( "capital ($)" )) {

                resourceParagraph.add ( new Chunk ( "Return on Working " + resourceUsage.get ( "resource" )
                        + ": $" + formatter.format ( Double.parseDouble ( estimatedIncommeFormatted ) / total )
                        + " of gross income per one $ of working capital\n", ReportTemplate.TIMESROMAN_10_NORMAL ) );
            } else {
                /*resourceParagraph.add(new Chunk("Return on " + resourceUsage.get("resource")
						+ ": $" + formatter.format(Double.parseDouble(estimatedIncommeFormatted)/ total)
						+ " of gross income per " + resourceUsage.get("uom") + "\n", ReportTemplate.TIMESROMAN_10_NORMAL));*/
            }

            resourceParagraph.add ( ReportTemplate.getNewLineChunk () );

        }


		/*for (Map.Entry<String, Object> returnList : resourceTotalList.entrySet()) {
			if(returnList.getKey().equalsIgnoreCase("land (Acres)")){
				resourceParagraph.add(new Chunk("Return on " + returnList.getKey() + ": $"+ formatter.format(Double.parseDouble(returnList.getValue().toString())) +" of gross income per acre\n", ReportTemplate.TIMESROMAN_10_NORMAL));
			} else if (returnList.getKey().equalsIgnoreCase("capital ($)")) {
//				resourceParagraph.add(new Chunk("Return on " + returnList.getKey() + ": $"+ formatter.format(Double.parseDouble(returnList.getValue().toString())) +" earned per $ borrowed\n", ReportTemplate.TIMESROMAN_10_NORMAL));
				resourceParagraph.add(new Chunk("Return on Working " + returnList.getKey() + ": $"+ formatter.format(Double.parseDouble(returnList.getValue().toString())) +" of gross income per $ of working capital\n", ReportTemplate.TIMESROMAN_10_NORMAL));
			} else {
				resourceParagraph.add(new Chunk("Return on " + returnList.getKey() + ": $" + formatter.format(Double.parseDouble(returnList.getValue().toString())) +"\n", ReportTemplate.TIMESROMAN_10_NORMAL));
			}

			resourceParagraph.add(ReportTemplate.getNewLineChunk());
		}*/

        PdfPCell resourceManagementSectionCell = new PdfPCell ( resourceParagraph );
        resourceManagementSectionCell.setUseBorderPadding ( true );
        //riskManagementSectionCell.setPaddingLeft(10);
        resourceManagementSectionCell.setBorderWidth ( 0 );
        resourceManagementSectionCell.setBorder ( Rectangle.BOX );
        resourceManagementTable.addCell ( resourceManagementSectionCell );

        return resourceManagementTable;
    }

    private PdfPTable getRiskManagementTable() {
        PdfPTable riskManagementTable = new PdfPTable ( 1 );
        riskManagementTable.setWidthPercentage ( 100 );

        PdfPCell riskManagementCell = new PdfPCell ( new Phrase ( "Risk Management", ReportTemplate.TIMESROMAN_12_BOLD ) );
        riskManagementCell.setUseBorderPadding ( true );
        riskManagementCell.setBorderWidth ( 0 );
        //cropsAcreageCell.setPaddingLeft(10);
        riskManagementTable.addCell ( riskManagementCell );

        PdfPCell forwardSalesCell = new PdfPCell ( new Phrase ( "Forward Sales & Opportunities", ReportTemplate.TIMESROMAN_10_NORMAL ) );
        forwardSalesCell.setUseBorderPadding ( true );
        forwardSalesCell.setBorderWidth ( 0 );
        //cropsAcreageCell.setPaddingLeft(10);
        riskManagementTable.addCell ( forwardSalesCell );


        PdfPTable forwardSalesTable = new PdfPTable ( 3 );
        forwardSalesTable.setWidthPercentage ( 100 );

        // Create header Crop , Amount, Filled
        PdfPCell cropNameCell = new PdfPCell ( new Phrase ( "Crop", ReportTemplate.TIMESROMAN_10_NORMAL ) );
        cropNameCell.setBorderWidth ( 0.5f );
        cropNameCell.setBorderColor ( new BaseColor ( 131, 154, 103 ) );
        cropNameCell.setBorder ( Rectangle.BOX );
        cropNameCell.setUseBorderPadding ( true );
        cropNameCell.setBackgroundColor ( new BaseColor ( 247, 205, 114 ) );
        forwardSalesTable.addCell ( cropNameCell );

        PdfPCell amountCell = new PdfPCell ( new Phrase ( "Amount", ReportTemplate.TIMESROMAN_10_NORMAL ) );
        amountCell.setBorderWidth ( 0.5f );
        amountCell.setBorderColor ( new BaseColor ( 131, 154, 103 ) );
        amountCell.setBorder ( Rectangle.BOX );
        amountCell.setUseBorderPadding ( true );
        amountCell.setBackgroundColor ( new BaseColor ( 247, 205, 114 ) );
        forwardSalesTable.addCell ( amountCell );

        PdfPCell filledCell = new PdfPCell ( new Phrase ( "Filled", ReportTemplate.TIMESROMAN_10_NORMAL ) );
        filledCell.setBorderWidth ( 0.5f );
        filledCell.setBorderColor ( new BaseColor ( 131, 154, 103 ) );
        filledCell.setBorder ( Rectangle.BOX );
        filledCell.setUseBorderPadding ( true );
        filledCell.setBackgroundColor ( new BaseColor ( 247, 205, 114 ) );
        forwardSalesTable.addCell ( filledCell );

        forwardSalesTable.completeRow ();

        /**
         * @changed - Abhishek
         * @date - 09-12-2015
         */
        // Create table data dynamically
        List <CropTypeView> forwardSalesList = reportDataPage1.getForwardSales ();
		/*if(farmInfoView.getStrategy().equals(PlanByStrategy.PLAN_BY_ACRES)){

			forwardSalesList = reportDataPage1.getForwardSales();

		} else if(farmInfoView.getStrategy().equals(PlanByStrategy.PLAN_BY_FIELDS)){

			forwardSalesList = reportDataPage1.getForwardSalesByField();

		}*/

        /**
         * @changed - Abhishek
         * @date - 16-01-2016
         * @desc - added condition to check for data in Forward sales
         */
        int forwardCropCount = 0;
        for (CropTypeView cropDetails : forwardSalesList) {

            if (cropDetails.getSelected ()
                    && (cropDetails.getFirmchecked ().equalsIgnoreCase ( "true" ) || cropDetails.getProposedchecked ())
                    && !cropDetails.getAcresStr ().equalsIgnoreCase ( "" )) {

                forwardCropCount++;
                /**
                 * @changed - Abhishek
                 * @date - 09-12-2015
                 */
                String cropName;
                if (cropDetails.getFirmchecked ().equalsIgnoreCase ( "true" ))
                    cropName = cropDetails.getCropName () + " (Firm)";
                else if (cropDetails.getProposedchecked ())
                    cropName = cropDetails.getCropName () + " (Proposed)";
                else
                    cropName = cropDetails.getCropName ();


                PdfPCell cropNameDataCell = new PdfPCell ( new Phrase ( cropName, ReportTemplate.TIMESROMAN_10_NORMAL ) );
                cropNameDataCell.setBorderWidth ( 0.5f );
                cropNameDataCell.setBorderColor ( new BaseColor ( 131, 154, 103 ) );
                cropNameDataCell.setBorder ( Rectangle.BOX );
                cropNameDataCell.setUseBorderPadding ( true );
                cropNameDataCell.setBackgroundColor ( new BaseColor ( 223, 240, 216 ) );
                forwardSalesTable.addCell ( cropNameDataCell );

                String amountStr = "";
                if (cropDetails.getFirmchecked ().equalsIgnoreCase ( "true" ) || cropDetails.getProposedchecked ()) {
                    amountStr = cropDetails.getAcresStr () + " acres at " + cropDetails.getForwardPrice ();
                }

                PdfPCell amountDataCell = new PdfPCell ( new Phrase ( amountStr, ReportTemplate.TIMESROMAN_10_NORMAL ) );
                amountDataCell.setBorderWidth ( 0.5f );
                amountDataCell.setBorderColor ( new BaseColor ( 131, 154, 103 ) );
                amountDataCell.setBorder ( Rectangle.BOX );
                amountDataCell.setUseBorderPadding ( true );
                amountDataCell.setBackgroundColor ( new BaseColor ( 223, 240, 216 ) );
                forwardSalesTable.addCell ( amountDataCell );


                String filledStatus = reportDataPage1.getForwardSalesStatus ( cropDetails );
					/*if(cropDetails.getFirmchecked().equalsIgnoreCase("true")){
						filledStatus = "Yes";
					} else if(cropDetails.getProposedchecked()){
						filledStatus = "Yes";
					} else {
						filledStatus = "No";
					}*/

                PdfPCell filledDataCell = new PdfPCell ( new Phrase ( filledStatus, ReportTemplate.TIMESROMAN_10_NORMAL ) );
                filledDataCell.setBorderWidth ( 0.5f );
                filledDataCell.setBorderColor ( new BaseColor ( 131, 154, 103 ) );
                filledDataCell.setBorder ( Rectangle.BOX );
                filledDataCell.setUseBorderPadding ( true );
                filledDataCell.setBackgroundColor ( new BaseColor ( 223, 240, 216 ) );
                forwardSalesTable.addCell ( filledDataCell );
                forwardSalesTable.completeRow ();
            }
        }

        /**
         * @added - Abhishek
         * @date - 16-01-2016
         * @desc - If no data for forward sales then display message
         */
        if (forwardCropCount == 0) {
            PdfPCell filledDataCell = new PdfPCell ( new Phrase ( "No Forward Sales", ReportTemplate.TIMESROMAN_10_NORMAL ) );
            filledDataCell.setHorizontalAlignment ( Element.ALIGN_CENTER );
            filledDataCell.setBorderWidth ( 0.5f );
            filledDataCell.setColspan ( 3 );
            filledDataCell.setBorderColor ( new BaseColor ( 131, 154, 103 ) );
            filledDataCell.setBorder ( Rectangle.BOX );
            filledDataCell.setUseBorderPadding ( true );
            filledDataCell.setBackgroundColor ( new BaseColor ( 223, 240, 216 ) );
            forwardSalesTable.addCell ( filledDataCell );
            forwardSalesTable.completeRow ();
        }

        PdfPCell forwardSalesTableCell = new PdfPCell ( forwardSalesTable );
        forwardSalesTableCell.setUseBorderPadding ( true );
        forwardSalesTableCell.setBorderWidth ( 0 );
        //cropsAcreageCell.setPaddingLeft(10);
        riskManagementTable.addCell ( forwardSalesTableCell );


        /**
         * @changed - Abhishek
         * @date - 09-02-2015
         */
		/*ReportDataPage1.ForwardSales forwardSales = reportDataPage1.getTotalForwardSales(farmInfoView);

		int incomeForwardSalesPercentage = 0;

		if(forwardSales.getLandUsage() != 0 && forwardSales.getTotalForwardSales() != 0 ){
			incomeForwardSalesPercentage = (forwardSales.getTotalForwardSales() * 100) / forwardSales.getLandUsage();
		}*/


        DecimalFormat formatter = new DecimalFormat ( "#.0" );

        // Add Crop Contribution Margin Table Footer

        int firstCropProfit = Integer.MIN_VALUE;
        int secondCropProfit = Integer.MIN_VALUE;

        for (String profit : profitList) {
            int profitInInteger = Integer.parseInt ( profit.replaceAll ( ",", "" ) );
            if (profitInInteger > firstCropProfit) {
                secondCropProfit = firstCropProfit;
                firstCropProfit = profitInInteger;
            } else if (profitInInteger > secondCropProfit)
                secondCropProfit = profitInInteger;
        }

        /**
         * @changed - Abhishek
         * @date - 12-12-2015
         */

        String estimatedIncomeFormatted = estimatedIncome.replaceAll ( "\\$", "" );
        estimatedIncomeFormatted = estimatedIncomeFormatted.replaceAll ( "\\,", "" );

        Double singleProfit = ((100 * firstCropProfit) / Double.parseDouble ( estimatedIncomeFormatted ));

        Double twoCropProfit = ((100 * (firstCropProfit + secondCropProfit)) / Double.parseDouble ( estimatedIncomeFormatted ));
        /**
         * @chanegd - Abhishek
         * @date - 12-01-2016
         * @desc - Applied formatter for decimal output
         */
//		PdfPCell cropContributionMarginTableFooterCell = new PdfPCell(new Phrase("% Est Income in single crop : " + formatter.format(singleProfit) +
//				"\n% Est Income in two crops : " + formatter.format(twoCropProfit), ReportTemplate.TIMESROMAN_10_NORMAL));
//		cropContributionMarginTableFooterCell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
//		cropContributionMarginTableFooterCell.setUseBorderPadding(true);
//		cropContributionMarginTableFooterCell.setBorderWidth(0);
//		//cropTableCell.setPaddingLeft(10);
//		riskManagementTable.addCell(cropContributionMarginTableFooterCell);


        String incomeForwardSalesPercentage = reportDataPage1.getTotalForwardSales ( farmInfoView );
        /**
         * @changed - Abhishek
         * @date - 12-12-2015
         */
        Paragraph forwardSalesTableFotterParagraph = new Paragraph ();
        forwardSalesTableFotterParagraph.add ( new Chunk ( incomeForwardSalesPercentage + "% Est. Income in forward sales \n", ReportTemplate.TIMESROMAN_10_NORMAL ) );
        forwardSalesTableFotterParagraph.add ( new Chunk ( formatter.format ( singleProfit ) + "% Est. Income in single crop" +
                "\n" + formatter.format ( twoCropProfit ) + "% Est. Income in two crops", ReportTemplate.TIMESROMAN_10_NORMAL ) );
        forwardSalesTableFotterParagraph.add ( ReportTemplate.getNewLineChunk () );


        /**
         * @changed - Abhishek
         * @date - 11-02-2016
         * @desc - changed according to slide#2 of 02102016
         */
		/*forwardSalesTableFotterParagraph.add(new Chunk("Crop Insurance\n", ReportTemplate.TIMESROMAN_10_BOLD));*/
        forwardSalesTableFotterParagraph.add ( new Chunk ( "\nHi-Risk Crops\n", ReportTemplate.TIMESROMAN_10_BOLD ) );
        /**
         * @changed - Abhishek
         * @date - 25-01-2016
         * @desc - changed according to slide#
         */
		/*forwardSalesTableFotterParagraph.add(new Chunk("% of Acres covered w/ xx crop insurance\n", ReportTemplate.TIMESROMAN_10_NORMAL));
		forwardSalesTableFotterParagraph.add(new Chunk("% of Acres covered with yy  crop insurance:", ReportTemplate.TIMESROMAN_10_NORMAL));*/
        ReportDataPage1.ConservationPracticeBean riskManagementLandProfit = reportDataPage1.getRiskManagementLandProfit ();
        forwardSalesTableFotterParagraph.add ( new Chunk ( riskManagementLandProfit.getProfitFromConservation () + "% Est. Income in one or more Hi-Risk Crops\n", ReportTemplate.TIMESROMAN_10_NORMAL ) );
        forwardSalesTableFotterParagraph.add ( new Chunk ( riskManagementLandProfit.getLandUnderConservation () + "% Acreage in one or more Hi-Risk Crops", ReportTemplate.TIMESROMAN_10_NORMAL ) );

        PdfPCell forwardSalesTableFooterCell = new PdfPCell ( forwardSalesTableFotterParagraph );
        forwardSalesTableFooterCell.setUseBorderPadding ( true );
        forwardSalesTableFooterCell.setBorderWidth ( 0 );
        //cropsAcreageCell.setPaddingLeft(10);
        riskManagementTable.addCell ( forwardSalesTableFooterCell );

        PlantingProfitLogger.info ( "riskManagementTable = " + riskManagementTable );

        return riskManagementTable;
    }

    private PdfPTable getPieChartSection() throws IOException, BadElementException {
        PdfPTable pieChartTable = new PdfPTable ( 1 );
        pieChartTable.setWidthPercentage ( 100 );

        DecimalFormat formatter = new DecimalFormat ( "#.00" );

        PdfPCell cropsAcreageCell = new PdfPCell ( new Phrase ( "Crop Acreage", ReportTemplate.TIMESROMAN_12_NORMAL ) );
        cropsAcreageCell.setUseBorderPadding ( true );
        cropsAcreageCell.setBorderWidth ( 0 );
        //cropsAcreageCell.setPaddingLeft(10);
        pieChartTable.addCell ( cropsAcreageCell );

        // Pie Chart Cell & Content
        /**
         * @Chanegd - Abhishek
         * @date - 11-01-2016
         * @desc - According to the updated syntax of ReportDataPage1
         */
        PdfPCell pieChartCell = new PdfPCell ( reportDataPage1.getCropsAndAcreagePieChart (), false );
        pieChartCell.setUseBorderPadding ( true );
        pieChartCell.setBorderWidth ( 0 );
        //pieChartCell.setPaddingLeft(10);
        pieChartTable.addCell ( pieChartCell );

        // Create CropAcreageProfit Table
        PdfPCell cropTableCell = new PdfPCell ();
        cropTableCell.addElement ( getCropAcreageProfitTable () );
        cropTableCell.setUseBorderPadding ( true );
        cropTableCell.setBorderWidth ( 0 );
        //cropTableCell.setPaddingLeft(10);
        pieChartTable.addCell ( cropTableCell );

        /**
         * @Added - Abhishek
         * @date - 25-01-2016
         * @desc - Added according to slide#3 of 12282015
         */
//		ReportDataPage1.ConservationPracticeBean landUnderConservationPractice = reportDataPage1.getLandUnderConservationPractice();
//		String profitFromConservation = landUnderConservationPractice.getProfitFromConservation();
//		String landUnderConservation = landUnderConservationPractice.getLandUnderConservation();

//		PdfPCell cropConservationDetailsCell = new PdfPCell(new Phrase("% of profit in Hi-risk Crops : " + (profitFromConservation.equals(".00") ? "0.0" : profitFromConservation) + "\n" +
//								"% of acreage in Hi-risk Crops : " + (landUnderConservation.equals(".00") ? "0.0" : landUnderConservation), ReportTemplate.TIMESROMAN_10_NORMAL));
//		cropConservationDetailsCell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
//		cropConservationDetailsCell.setUseBorderPadding(true);
//		cropConservationDetailsCell.setBorderWidth(0);
//		pieChartTable.addCell(cropConservationDetailsCell);


        // Add Crop Field Assignment
        PdfPCell cropFieldAssignment = new PdfPCell ( new Phrase ( "Crop/Field Assignments - See Exhibit 1", ReportTemplate.TIMESROMAN_12_BOLD ) );
        cropFieldAssignment.setUseBorderPadding ( true );
        cropFieldAssignment.setBorderWidth ( 0 );
        cropTableCell.setPaddingTop ( 5 );
        pieChartTable.addCell ( cropFieldAssignment );

        // Add Crop Contribution Margin
        /**
         * @changed - Abhishek
         * @date - 25-01-2016
         * @desc - changed according to slide#4 of 12282015
         */
		/*PdfPCell cropContributionMargin = new PdfPCell(new Phrase("Crop Contribution Margin", ReportTemplate.TIMESROMAN_12_BOLD));*/
        PdfPCell cropContributionMargin = new PdfPCell ( new Phrase ( " Profitability Measures ", ReportTemplate.TIMESROMAN_12_NORMAL ) );
        cropContributionMargin.setUseBorderPadding ( true );
        cropContributionMargin.setBorderWidth ( 0 );
        cropContributionMargin.setPaddingTop ( 10 );
        pieChartTable.addCell ( cropContributionMargin );

        // Add Crop Contribution Margin Table
        PdfPCell cropContributionMarginTableCell = new PdfPCell ();
        cropContributionMarginTableCell.setHorizontalAlignment ( PdfPCell.ALIGN_LEFT );
        cropContributionMarginTableCell.addElement ( getCropContributionMarginTable () );
        cropContributionMarginTableCell.setUseBorderPadding ( true );
        cropContributionMarginTableCell.setBorderWidth ( 0 );
        //cropTableCell.setPaddingLeft(10);
        pieChartTable.addCell ( cropContributionMarginTableCell );

//		// Add Crop Contribution Margin Table Footer
//
//		int firstCropProfit = int.MIN_VALUE;
//		int secondCropProfit = int.MIN_VALUE;
//
//		for (String profit : profitList) {
//			int profitInInteger =  Integer.parseInt(profit.replaceAll("\\,", ""));
//			if (profitInInteger > firstCropProfit) {
//				secondCropProfit = firstCropProfit;
//				firstCropProfit = profitInInteger;
//			} else if (profitInInteger > secondCropProfit)
//				secondCropProfit = profitInInteger;
//		}
//
//		/**
//		 * @changed - Abhishek
//		 * @date - 12-12-2015
//		 */
//
//		String estimatedIncommeFormatted = estimatedIncome.replaceAll("\\$", "");
//		estimatedIncommeFormatted = estimatedIncommeFormatted.replaceAll("\\,", "");
//
//		Double singleProfit = (firstCropProfit / Double.parseDouble(estimatedIncommeFormatted)) * 100;
//
//		Double twoCropProfit = ((firstCropProfit + secondCropProfit)/ Double.parseDouble(estimatedIncommeFormatted)) * 100;
//		/**
//		 * @chanegd - Abhishek
//		 * @date - 12-01-2016
//		 * @desc - Applied formatter for decimal output
//		 */
//		PdfPCell cropContributionMarginTableFooterCell = new PdfPCell(new Phrase("% Est Income in single crop : " + formatter.format(singleProfit) + "\n% Est Income in two crops : " + formatter.format(twoCropProfit), ReportTemplate.TIMESROMAN_10_NORMAL));
//		cropContributionMarginTableFooterCell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
//		cropContributionMarginTableFooterCell.setUseBorderPadding(true);
//		cropContributionMarginTableFooterCell.setBorderWidth(0);
//		//cropTableCell.setPaddingLeft(10);
//		pieChartTable.addCell(cropContributionMarginTableFooterCell);
        return pieChartTable;

    }

    private PdfPTable getCropContributionMarginTable() {
        PdfPTable cropContributionMarginTable = new PdfPTable ( 6 );
        cropContributionMarginTable.setWidthPercentage ( 100 );

        // Create Table Header
        PdfPCell cropHead = new PdfPCell ( new Phrase ( "Crop", ReportTemplate.TIMESROMAN_10_NORMAL ) );
        cropHead.setUseBorderPadding ( true );
        cropHead.setBorderWidth ( 0.5f );
        cropHead.setBorder ( Rectangle.BOTTOM );
        cropContributionMarginTable.addCell ( cropHead );

        PdfPCell cmHead = new PdfPCell ( new Phrase ( "% Profit / <br> % of Land", ReportTemplate.TIMESROMAN_10_NORMAL ) );
        cmHead.setUseBorderPadding ( true );
        cmHead.setBorderWidth ( 0.5f );
        cmHead.setBorder ( Rectangle.BOTTOM );
        cropContributionMarginTable.addCell ( cmHead );

        PdfPCell ratingHead = new PdfPCell ( new Phrase ( " ", ReportTemplate.TIMESROMAN_10_NORMAL ) );
        ratingHead.setUseBorderPadding ( true );
        ratingHead.setBorderWidth ( 0.5f );
        ratingHead.setBorder ( Rectangle.BOTTOM );
        cropContributionMarginTable.addCell ( ratingHead );

        PdfPCell estimateHead = new PdfPCell ( new Phrase ( "Estimated Income per Acre", ReportTemplate.TIMESROMAN_10_NORMAL ) );
        estimateHead.setUseBorderPadding ( true );
        estimateHead.setBorderWidth ( 0.5f );
        estimateHead.setBorder ( Rectangle.BOTTOM );
        cropContributionMarginTable.addCell ( estimateHead );

        PdfPCell returnWorkingCapitalHead = new PdfPCell ( new Phrase ( "Return Working Capital", ReportTemplate.TIMESROMAN_10_NORMAL ) );
        returnWorkingCapitalHead.setUseBorderPadding ( true );
        returnWorkingCapitalHead.setBorderWidth ( 0.5f );
        returnWorkingCapitalHead.setBorder ( Rectangle.BOTTOM );
        cropContributionMarginTable.addCell ( returnWorkingCapitalHead );

        PdfPCell ratingforWorkingCapitalHead = new PdfPCell ( new Phrase ( "Rating", ReportTemplate.TIMESROMAN_10_NORMAL ) );
        ratingforWorkingCapitalHead.setUseBorderPadding ( true );
        ratingforWorkingCapitalHead.setBorderWidth ( 0.5f );
        ratingforWorkingCapitalHead.setBorder ( Rectangle.BOTTOM );
        cropContributionMarginTable.addCell ( ratingforWorkingCapitalHead );

        cropContributionMarginTable.completeRow ();

        // Add Table Data
        /**
         * @changed - Abhishek
         * @date - 09-12-2015
         */
        if (farmInfoView.getStrategy ().equals ( PlanByStrategy.PLAN_BY_ACRES )) {
            /**
             * @changed - Abhishek
             * @date - 09-01-2016
             * According to the updated syntax of ReportDataPage1
             */
            List <FarmOutputDetailsView> outputDetailsForFarm = reportDataPage1.getOutputDetailsForFarm ();
            for (FarmOutputDetailsView farmOutputDetails : outputDetailsForFarm) {
                /**
                 * @changed - Abhishek
                 * @date - 09-12-2015
                 */
                String cropNameStr;
                if (farmOutputDetails.getForFirm ())
                    cropNameStr = farmOutputDetails.getCropTypeView ().getCropName () + " (Firm)";
                else if (farmOutputDetails.getForProposed ())
                    cropNameStr = farmOutputDetails.getCropTypeView ().getCropName () + " (Proposed)";
                else
                    cropNameStr = farmOutputDetails.getCropTypeView ().getCropName ();

                PdfPCell cropName = new PdfPCell ( new Phrase ( cropNameStr, ReportTemplate.TIMESROMAN_10_NORMAL ) );
                cropName.setUseBorderPadding ( true );
                cropName.setBorderWidth ( 0 );
                cropName.setBorder ( Rectangle.NO_BORDER );
                cropContributionMarginTable.addCell ( cropName );

                /**
                 * @chanegd - Abhishek
                 * @date - 09-02-2016
                 */
//				Double cropContriM = Double.parseDouble(AgricultureStandardUtils.removeAllCommas(farmOutputDetails.getProfit())) / Double.parseDouble(AgricultureStandardUtils.removeAllCommas(farmOutputDetails.getUsedAcres()));

                /**
                 * @chanegd - Abhishek
                 * @date - 12-01-2016
                 * @desc - Applied formatter for decimal output
                 */
                DecimalFormat formatter = new DecimalFormat ( "#.00" );
                /**
                 * @chanegd - Abhishek
                 * @date - 09-02-2016
                 */
//				PdfPCell cropContriMargin = new PdfPCell(new Phrase("" + formatter.format(cropContriM), ReportTemplate.TIMESROMAN_10_NORMAL));
                PdfPCell cropContriMargin = new PdfPCell ( new Phrase ( "" + farmOutputDetails.getProfitIndex ().toString (), ReportTemplate.TIMESROMAN_10_NORMAL ) );
                cropContriMargin.setUseBorderPadding ( true );
                cropContriMargin.setBorderWidth ( 0 );
                cropContriMargin.setBorder ( Rectangle.NO_BORDER );
                cropContributionMarginTable.addCell ( cropContriMargin );

                PdfPCell rating = new PdfPCell ( new Phrase ( "", ReportTemplate.TIMESROMAN_10_NORMAL ) );
                rating.setUseBorderPadding ( true );
                rating.setBorderWidth ( 0 );
                rating.setBorder ( Rectangle.NO_BORDER );
                if (farmOutputDetails.getRating ().equalsIgnoreCase ( "green" )) {
                    rating.setBackgroundColor ( BaseColor.GREEN );
                } else if (farmOutputDetails.getRating ().equalsIgnoreCase ( "red" )) {
                    rating.setBackgroundColor ( BaseColor.RED );
                } else if (farmOutputDetails.getRating ().equalsIgnoreCase ( "yellow" )) {
                    rating.setBackgroundColor ( BaseColor.YELLOW );
                } else if (farmOutputDetails.getRating ().equalsIgnoreCase ( "NA" )) {
                    rating.setBackgroundColor ( BaseColor.LIGHT_GRAY );
                } else {
                    rating.setBackgroundColor ( BaseColor.LIGHT_GRAY );
                }

                cropContributionMarginTable.addCell ( rating );
                String estimateIncomePerAce = null;
                String returnWorking = null;
                if (farmOutputDetails.getRatio () == 0.0) {
                    estimateIncomePerAce = "NA";
                } else {
                    estimateIncomePerAce = String.valueOf ( farmOutputDetails.getRatio () );

                }
                PdfPCell estimate = new PdfPCell ( new Phrase ( " " + estimateIncomePerAce, ReportTemplate.TIMESROMAN_10_NORMAL ) );
                estimate.setUseBorderPadding ( true );
                estimate.setBorderWidth ( 0 );
                estimate.setBorder ( Rectangle.NO_BORDER );
                cropContributionMarginTable.addCell ( estimate );
                CropTypeView cropTypeView = farmOutputDetails.getCropTypeView ();

                Double workReturn = 0.0;
                String workReturnInString = null;
                if (farmOutputDetails.getProfitDouble () == 0.0) {
                    workReturnInString = "NA";
                } else {
                    workReturn = farmOutputDetails.getProfitDouble () / cropTypeView.getCalculatedVariableProductionCost ().doubleValue ();
                    workReturnInString = String.valueOf ( workReturn );
                }
                PdfPCell returnWorkingCapital = new PdfPCell ( new Phrase ( " " + workReturnInString, ReportTemplate.TIMESROMAN_10_NORMAL ) );
                returnWorkingCapital.setUseBorderPadding ( true );
                returnWorkingCapital.setBorderWidth ( 0 );
                returnWorkingCapital.setBorder ( Rectangle.NO_BORDER );
                cropContributionMarginTable.addCell ( returnWorkingCapital );


                PdfPCell ratingforWorkingCapital = new PdfPCell ( new Phrase ( "Rating", ReportTemplate.TIMESROMAN_10_NORMAL ) );
                ratingforWorkingCapital.setUseBorderPadding ( true );
                ratingforWorkingCapital.setBorderWidth ( 0 );
                ratingforWorkingCapital.setBorder ( Rectangle.NO_BORDER );
                if (farmOutputDetails.getProfitDouble () == 0.0) {
                    ratingforWorkingCapital.setBackgroundColor ( BaseColor.GRAY );
                } else {
                    if (workReturn < 0.5) {
                        ratingforWorkingCapital.setBackgroundColor ( BaseColor.RED );
                    } else if ((0.51 < workReturn)) {
                        if (workReturn <= 0.9) {
                            ratingforWorkingCapital.setBackgroundColor ( BaseColor.YELLOW );
                        }
                    } else if (workReturn > 0.9) {
                        ratingforWorkingCapital.setBackgroundColor ( BaseColor.GREEN );
                    } else {
                        ratingforWorkingCapital.setBackgroundColor ( BaseColor.GRAY );
                    }
                }
                cropContributionMarginTable.completeRow ();
            }
        } else if (farmInfoView.getStrategy ().equals ( PlanByStrategy.PLAN_BY_FIELDS )) {
            /**
             * @changed - Abhishek
             * @date - 09-01-2016
             * @updated - 09-0-2016
             * According to the updated syntax of ReportDataPage1
             */
            JSONObject baseSelectedOutpuDetailsJsonObject = reportDataPage1.getBaseSelectedDetailsOutputDetails ();

            List <CropTypeView> cropTypeViewList = (List <CropTypeView>) baseSelectedOutpuDetailsJsonObject.get ( "cropTypeView" );
            Map <String, String> hashMapForProfitIndex = (Map <String, String>) baseSelectedOutpuDetailsJsonObject.get ( "hashMapForProfitIndex" );
            Map <String, String> hashMapForRating = (Map <String, String>) baseSelectedOutpuDetailsJsonObject.get ( "hashMapForRating" );
            Map <String, String> hashMapForProfit = (Map <String, String>) baseSelectedOutpuDetailsJsonObject.get ( "hashMapForProfit" );
            List <String> ratioList = new ArrayList <String> ();

            Map <String, String> map = new LinkedHashMap <String, String> ();
            JSONArray cropAcreageJsonArray = (JSONArray) baseSelectedOutpuDetailsJsonObject.get ( "cropAcreageJsonArray" );
            for (int i = 0; i < cropAcreageJsonArray.size (); i++) {
                Map <String, String> hashMapForAcreage = (Map <String, String>) cropAcreageJsonArray.get ( i );
                String value = hashMapForAcreage.get ( "ratio" );
                String key = hashMapForAcreage.get ( "cropName" );
                ratioList.add ( value );
                map.put ( hashMapForAcreage.get ( "cropName" ), hashMapForAcreage.get ( "ratio" ) );

            }

            Set <String> keySet = hashMapForRating.keySet ();
            int index = 0;
            for (String cropKey : keySet) {

                PdfPCell cropName = new PdfPCell ( new Phrase ( cropKey, ReportTemplate.TIMESROMAN_10_NORMAL ) );
                cropName.setUseBorderPadding ( true );
                cropName.setBorderWidth ( 0 );
                cropName.setBorder ( Rectangle.NO_BORDER );
                cropContributionMarginTable.addCell ( cropName );

//				Double cropContriM =  Double.parseDouble(AgricultureStandardUtils.removeAllCommas(farmOutputDetailsForField.getProfit())) / Double.parseDouble(AgricultureStandardUtils.removeAllCommas(farmOutputDetailsForField.getUsedAcres()));

                /**
                 * @chanegd - Abhishek
                 * @date - 12-01-2016
                 * @desc - Applied formatter for decimal output
                 */
                DecimalFormat formatter = new DecimalFormat ( "#.00" );
                /**
                 * @chanegd - Abhishek
                 * @date - 11-02-2016
                 * @desc - replaced % with space for land profitability index
                 */
//				PdfPCell cropContriMargin = new PdfPCell(new Phrase("" + formatter.format(cropContriM), ReportTemplate.TIMESROMAN_10_NORMAL));
                PdfPCell cropContriMargin = new PdfPCell ( new Phrase ( "" + hashMapForProfitIndex.get ( cropKey ).replaceAll ( "%", "" ), ReportTemplate.TIMESROMAN_10_NORMAL ) );
                cropContriMargin.setUseBorderPadding ( true );
                cropContriMargin.setBorderWidth ( 0 );
                cropContriMargin.setBorder ( Rectangle.NO_BORDER );
                cropContributionMarginTable.addCell ( cropContriMargin );


                PdfPCell rating = new PdfPCell ( new Phrase ( "   ", ReportTemplate.TIMESROMAN_10_NORMAL ) );
                rating.setUseBorderPadding ( true );
                rating.setBorderWidth ( 0 );
                rating.setBorder ( Rectangle.NO_BORDER );
                if (hashMapForRating.get ( cropKey ).equalsIgnoreCase ( "green" )) {
                    rating.setBackgroundColor ( BaseColor.GREEN );
                } else if (hashMapForRating.get ( cropKey ).equalsIgnoreCase ( "red" )) {
                    rating.setBackgroundColor ( BaseColor.RED );
                } else if (hashMapForRating.get ( cropKey ).equalsIgnoreCase ( "yellow" )) {
                    rating.setBackgroundColor ( BaseColor.YELLOW );
                } else {
                    rating.setBackgroundColor ( BaseColor.GRAY );
                }
                cropContributionMarginTable.addCell ( rating );

                PdfPCell estimate = new PdfPCell ( new Phrase ( " " + map.get ( cropKey ), ReportTemplate.TIMESROMAN_10_NORMAL ) );
                index += 1;
                estimate.setUseBorderPadding ( true );
                estimate.setBorderWidth ( 0 );
                estimate.setBorder ( Rectangle.NO_BORDER );

                cropContributionMarginTable.addCell ( estimate );
                Double workReturn = 0.0;
                String workReturnInString = null;
                String profitStr = hashMapForProfit.get ( cropKey );

                if (profitStr.equalsIgnoreCase ( "0 (0.0%)" )
                        || profitStr.equalsIgnoreCase ( "0 (-0.0%)" )) {
                    workReturnInString = "NA";
                } else {
                    Double profitDouble = new Double ( AgricultureStandardUtils.removeAllCommas ( profitStr.split ( " \\(" )[0] ) );
                    String cropName1 = cropKey;
                    if (cropKey.contains ( " (Firm)" )) {
                        cropName1 = cropKey.split ( " \\(Firm\\)" )[0];
                    } else if (cropKey.contains ( " (Proposed)" )) {
                        cropName1 = cropKey.split ( " \\(Proposed\\)" )[0];
                    }
                    List <CropTypeView> cropTypeViews = (List <CropTypeView>) baseSelectedOutpuDetailsJsonObject.get ( "cropTypeView" );
                    for (CropTypeView cropTypeView : cropTypeViews) {
                        if (cropTypeView.getSelected () && cropTypeView.getCropName ().equalsIgnoreCase ( cropName1 )) {
                            workReturn = profitDouble / cropTypeView.getCalculatedVariableProductionCost ().doubleValue ();
                            workReturnInString = String.valueOf ( workReturn );
                            break;
                        }
                    }
                }
                PdfPCell returnWorkingCapital = new PdfPCell ( new Phrase ( " " + workReturnInString, ReportTemplate.TIMESROMAN_10_NORMAL ) );
                returnWorkingCapital.setUseBorderPadding ( true );
                returnWorkingCapital.setBorderWidth ( 0 );
                returnWorkingCapital.setBorder ( Rectangle.NO_BORDER );
                cropContributionMarginTable.addCell ( returnWorkingCapital );

                PdfPCell ratingforWorkingCapital = new PdfPCell ( new Phrase ( "Rating", ReportTemplate.TIMESROMAN_10_NORMAL ) );
                ratingforWorkingCapital.setUseBorderPadding ( true );
                ratingforWorkingCapital.setBorderWidth ( 0 );
                ratingforWorkingCapital.setBorder ( Rectangle.NO_BORDER );

                if (profitStr.equalsIgnoreCase ( "0 (0.0%)" )
                        || profitStr.equalsIgnoreCase ( "0 (-0.0%)" )) {
                    ratingforWorkingCapital.setBackgroundColor ( BaseColor.GRAY );
                } else {
                    if (workReturn < 0.5) {
                        ratingforWorkingCapital.setBackgroundColor ( BaseColor.RED );
                    } else if ((0.51 < workReturn)) {
                        if (workReturn <= 0.9) {
                            ratingforWorkingCapital.setBackgroundColor ( BaseColor.YELLOW );
                        }
                    } else if (workReturn > 0.9) {
                        ratingforWorkingCapital.setBackgroundColor ( BaseColor.GREEN );
                    } else {
                        ratingforWorkingCapital.setBackgroundColor ( BaseColor.GRAY );

                    }
                }

                cropContributionMarginTable.completeRow ();
            }

		/*for(CropTypeView cropTypeView : cropTypeViewList){
                if (cropTypeView.getSelected()) {
					*//**
             * @changed - Abhishek
             * @date - 09-12-2015
             *//*
					String cropNameStr;
					if(cropTypeView.getFirmchecked().equalsIgnoreCase("true"))
                        cropNameStr = cropTypeView.getCropName() + " (Firm)";
                    else if(cropTypeView.getProposedchecked())
                        cropNameStr = cropTypeView.getCropName() + " (Proposed)";
                    else
                        cropNameStr = cropTypeView.getCropName();

					PdfPCell cropName = new PdfPCell(new Phrase(cropNameStr, ReportTemplate.TIMESROMAN_10_NORMAL));
					cropName.setUseBorderPadding(true);
					cropName.setBorderWidth(0);
					cropName.setBorder(Rectangle.NO_BORDER);
					cropContributionMarginTable.addCell(cropName);

//				Double cropContriM =  Double.parseDouble(AgricultureStandardUtils.removeAllCommas(farmOutputDetailsForField.getProfit())) / Double.parseDouble(AgricultureStandardUtils.removeAllCommas(farmOutputDetailsForField.getUsedAcres()));

					*//**
             * @chanegd - Abhishek
             * @date - 12-01-2016
             * @desc - Applied formatter for decimal output
             *//*
					DecimalFormat formatter = new DecimalFormat("#.00");
					*//**
             * @chanegd - Abhishek
             * @date - 11-02-2016
             * @desc - replaced % with space for land profitability index
             *//*
//				PdfPCell cropContriMargin = new PdfPCell(new Phrase("" + formatter.format(cropContriM), ReportTemplate.TIMESROMAN_10_NORMAL));
					PdfPCell cropContriMargin = new PdfPCell(new Phrase("" + hashMapForProfitIndex.get(cropTypeView.getCropName()).replaceAll("%",""), ReportTemplate.TIMESROMAN_10_NORMAL));
					cropContriMargin.setUseBorderPadding(true);
					cropContriMargin.setBorderWidth(0);
					cropContriMargin.setBorder(Rectangle.NO_BORDER);
					cropContributionMarginTable.addCell(cropContriMargin);

					PdfPCell rating = new PdfPCell(new Phrase("   ", ReportTemplate.TIMESROMAN_10_NORMAL));
					rating.setUseBorderPadding(true);
					rating.setBorderWidth(0);
					rating.setBorder(Rectangle.NO_BORDER);
					if(hashMapForRating.get(cropTypeView.getCropName()).equalsIgnoreCase("green")){
						rating.setBackgroundColor(BaseColor.GREEN);
					} else if (hashMapForRating.get(cropTypeView.getCropName()).equalsIgnoreCase("red")){
						rating.setBackgroundColor(BaseColor.RED);
					} else if (hashMapForRating.get(cropTypeView.getCropName()).equalsIgnoreCase("yellow")){
						rating.setBackgroundColor(BaseColor.YELLOW);
					} else {
						rating.setBackgroundColor(BaseColor.GRAY);
					}
					cropContributionMarginTable.addCell(rating);

					cropContributionMarginTable.completeRow();
				}

			}*/
        }


        return cropContributionMarginTable;

    }

    private PdfPTable getCropAcreageProfitTable() {
        PdfPTable table = new PdfPTable ( 3 );
        table.setWidthPercentage ( 100 );

        // Create header Crop , Acreage, Profit
        PdfPCell cropNameCell = new PdfPCell ( new Phrase ( "Crop", ReportTemplate.TIMESROMAN_10_NORMAL ) );
        cropNameCell.setBorderWidth ( 0.5f );
        cropNameCell.setBorderColor ( new BaseColor ( 131, 154, 103 ) );
        cropNameCell.setBorder ( Rectangle.BOX );
        cropNameCell.setUseBorderPadding ( true );
        cropNameCell.setBackgroundColor ( new BaseColor ( 247, 205, 114 ) );
        table.addCell ( cropNameCell );

        PdfPCell acreageCell = new PdfPCell ( new Phrase ( "Acreage", ReportTemplate.TIMESROMAN_10_NORMAL ) );
        acreageCell.setBorderWidth ( 0.5f );
        acreageCell.setBorderColor ( new BaseColor ( 131, 154, 103 ) );
        acreageCell.setBorder ( Rectangle.BOX );
        acreageCell.setUseBorderPadding ( true );
        acreageCell.setBackgroundColor ( new BaseColor ( 247, 205, 114 ) );
        table.addCell ( acreageCell );

        PdfPCell profitCell = new PdfPCell ( new Phrase ( "Est. Income", ReportTemplate.TIMESROMAN_10_NORMAL ) );
        profitCell.setBorderWidth ( 0.5f );
        profitCell.setBorderColor ( new BaseColor ( 131, 154, 103 ) );
        profitCell.setBorder ( Rectangle.BOX );
        profitCell.setUseBorderPadding ( true );
        profitCell.setBackgroundColor ( new BaseColor ( 247, 205, 114 ) );
        table.addCell ( profitCell );

        table.completeRow ();

        profitList = new ArrayList <String> ();


        // Create table data dynamically
        /**
         * @changed - Abhishek
         * @date - 09-12-2015
         */
        if (farmInfoView.getStrategy ().equals ( PlanByStrategy.PLAN_BY_ACRES )) {

            /**
             * @changed - Abhishek
             * @date - 09-01-2016
             * According to the updated syntax of ReportDataPage1
             */
            List <FarmOutputDetailsView> outputDetailsForFarm = reportDataPage1.getOutputDetailsForFarm ();
            for (FarmOutputDetailsView detailsForAcreageView : outputDetailsForFarm) {
                /**
                 * @changed - Abhishek
                 * @date - 09-12-2015
                 */
                String cropName;
                if (detailsForAcreageView.getForFirm ())
                    cropName = detailsForAcreageView.getCropTypeView ().getCropName () + " (Firm)";
                else if (detailsForAcreageView.getForProposed ())
                    cropName = detailsForAcreageView.getCropTypeView ().getCropName () + " (Proposed)";
                else
                    cropName = detailsForAcreageView.getCropTypeView ().getCropName ();

                PdfPCell cropNameDataCell = new PdfPCell ( new Phrase ( cropName, ReportTemplate.TIMESROMAN_10_NORMAL ) );
                cropNameDataCell.setBorderWidth ( 0.5f );
                cropNameDataCell.setBorderColor ( new BaseColor ( 131, 154, 103 ) );
                cropNameDataCell.setBorder ( Rectangle.BOX );
                cropNameDataCell.setUseBorderPadding ( true );
                cropNameDataCell.setBackgroundColor ( new BaseColor ( 223, 240, 216 ) );
                table.addCell ( cropNameDataCell );

                /**
                 * @changed - Abhishek
                 * @date - 25-01-2016
                 * @desc - changed according to slide#4 of 12282015
                 */
				/*PdfPCell acreageDataCell = new PdfPCell(new Phrase(detailsForAcreageView.getUsedAcresAsInteger() + "(" + detailsForAcreageView.getUsedAcresPercentage() + "%)", ReportTemplate.TIMESROMAN_10_NORMAL));*/
                PdfPCell acreageDataCell = new PdfPCell ( new Phrase ( AgricultureStandardUtils.commaSeparaterForLong ( detailsForAcreageView.getUsedAcresAsInteger () ) + " (" + detailsForAcreageView.getUsedAcresPercentage () + "%)", ReportTemplate.TIMESROMAN_10_NORMAL ) );
                acreageDataCell.setBorderWidth ( 0.5f );
                acreageDataCell.setBorderColor ( new BaseColor ( 131, 154, 103 ) );
                acreageDataCell.setBorder ( Rectangle.BOX );
                acreageDataCell.setUseBorderPadding ( true );
                acreageDataCell.setBackgroundColor ( new BaseColor ( 223, 240, 216 ) );
                table.addCell ( acreageDataCell );

                /**
                 * @changed - Abhishek
                 * @date - 12-12-2015
                 */
                profitList.add ( detailsForAcreageView.getProfit () );
                /**
                 * @changed - Abhishek
                 * @date - 11-02-2016
                 * @desc - formatted profit with $xxx,xxx format
                 */
                PdfPCell profitDataCell = new PdfPCell ( new Phrase ( "$" + detailsForAcreageView.getProfit () + " (" + detailsForAcreageView.getUsedCapitalPercentage () + "%)", ReportTemplate.TIMESROMAN_10_NORMAL ) );
                profitDataCell.setBorderWidth ( 0.5f );
                profitDataCell.setBorderColor ( new BaseColor ( 131, 154, 103 ) );
                profitDataCell.setBorder ( Rectangle.BOX );
                profitDataCell.setUseBorderPadding ( true );
                profitDataCell.setBackgroundColor ( new BaseColor ( 223, 240, 216 ) );
                table.addCell ( profitDataCell );

                table.completeRow ();
            }

        } else if (farmInfoView.getStrategy ().equals ( PlanByStrategy.PLAN_BY_FIELDS )) {
            /**
             * @changed - Abhishek
             * @date - 09-01-2016
             * @Updated - 09-02-2016
             * According to the updated syntax of ReportDataPage1
             */
            JSONObject baseSelectedOutpuDetailsJsonObject = reportDataPage1.getBaseSelectedDetailsOutputDetails ();
            Map <String, String> hashMapForAcre = (Map <String, String>) baseSelectedOutpuDetailsJsonObject.get ( "hashMapForAcre" );
            Map <String, String> hashMapForProfit = (Map <String, String>) baseSelectedOutpuDetailsJsonObject.get ( "hashMapForProfit" );

            List <CropTypeView> cropTypeViewList = (List <CropTypeView>) baseSelectedOutpuDetailsJsonObject.get ( "cropTypeView" );

            Set <String> keySet = hashMapForAcre.keySet ();

            for (String cropKey : keySet) {
                PdfPCell cropNameDataCell = new PdfPCell ( new Phrase ( cropKey, ReportTemplate.TIMESROMAN_10_NORMAL ) );
                cropNameDataCell.setBorderWidth ( 0.5f );
                cropNameDataCell.setBorderColor ( new BaseColor ( 131, 154, 103 ) );
                cropNameDataCell.setBorder ( Rectangle.BOX );
                cropNameDataCell.setUseBorderPadding ( true );
                cropNameDataCell.setBackgroundColor ( new BaseColor ( 223, 240, 216 ) );
                table.addCell ( cropNameDataCell );

                PdfPCell acreageDataCell = new PdfPCell ( new Phrase ( hashMapForAcre.get ( cropKey ), ReportTemplate.TIMESROMAN_10_NORMAL ) );
                acreageDataCell.setBorderWidth ( 0.5f );
                acreageDataCell.setBorderColor ( new BaseColor ( 131, 154, 103 ) );
                acreageDataCell.setBorder ( Rectangle.BOX );
                acreageDataCell.setUseBorderPadding ( true );
                acreageDataCell.setBackgroundColor ( new BaseColor ( 223, 240, 216 ) );
                table.addCell ( acreageDataCell );

                /**
                 * @changed - Abhishek
                 * @date - 12-12-2015
                 * @updated - 09-02-2016
                 */
                String profit = hashMapForProfit.get ( cropKey );

                PdfPCell profitDataCell = new PdfPCell ( new Phrase ( "$" + profit, ReportTemplate.TIMESROMAN_10_NORMAL ) );
                profitDataCell.setBorderWidth ( 0.5f );
                profitDataCell.setBorderColor ( new BaseColor ( 131, 154, 103 ) );
                profitDataCell.setBorder ( Rectangle.BOX );
                profitDataCell.setUseBorderPadding ( true );
                profitDataCell.setBackgroundColor ( new BaseColor ( 223, 240, 216 ) );
                table.addCell ( profitDataCell );

                /**
                 * @changed - Abhishek
                 * @date - 09-02-2016
                 */
                profit = profit.substring ( 0, profit.indexOf ( '(' ) - 1 );
                profitList.add ( profit );

                table.completeRow ();
            }


			/*for (CropTypeView cropTypeView : cropTypeViewList) {
				if (cropTypeView.getSelected()) {
					*//**
             * @changed - Abhishek
             * @date - 09-12-2015
             *//*
					*//*String cropName = null;
					if(detailsForFieldView.isForFirm())
                        cropName = detailsForFieldView.getCropTypeViewList().getCropName() + " (Contract)";
                    else if(detailsForFieldView.isForProposed())
                        cropName = detailsForFieldView.getCropTypeViewList().getCropName() + " (Proposed)";
                    else
                        cropName = detailsForFieldView.getCropTypeViewList().getCropName();*//*



				}
			}*/

        }


        return table;

    }


}

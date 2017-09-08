package com.decipher.agriculture.farmreport;

import java.awt.*;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;


import com.decipher.agriculture.data.farm.PlanByStrategy;
import com.decipher.util.AgricultureStandardUtils;
import com.decipher.view.form.account.AccountView;
import com.decipher.view.form.farmDetails.CropResourceUsageView;
import com.decipher.view.form.farmDetails.CropTypeView;
import com.decipher.view.form.farmDetails.FarmInfoView;
import com.decipher.view.form.farmDetails.FarmOutputDetailsForFieldView;
import com.decipher.view.form.farmDetails.FarmOutputDetailsView;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Image;
import org.codehaus.jettison.json.JSONException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.util.Rotation;
import org.json.simple.JSONObject;

/**
 * @author - Abhishek
 * @date - 29-11-2015
 */

@SuppressWarnings("unchecked")
public class ReportDataPage1 {

    /**
     * @chanegd - Abhishek
     * @date - 09-01-2016
     * @desc - Only data objects to be used are used not using any service to populate data objects
     */
    private AccountView accountView;
    private FarmInfoView farmInfoView;
    private JSONObject baseSelectedOutpuDetailsJsonObject;

    public ReportDataPage1(AccountView accountView, FarmInfoView farmInfoView, JSONObject baseSelectedOutputDetailsJsonObject) {
        /**
         * @chanegd - Abhishek
         * @date - 09-01-2016
         * @desc - Only data objects to be used are used not using any service to populate data objects
         */
        this.accountView = accountView;
        this.farmInfoView = farmInfoView;
        this.baseSelectedOutpuDetailsJsonObject = baseSelectedOutputDetailsJsonObject;
    }

    /**
     * @return Account information of the loggedIn User
     * @changed - Abhishek
     * @date - 09-01-2016
     */
    public AccountView getAccountInforamtion() {
        return accountView;
    }

    /**
     * @return Farm information
     * @changed - Abhishek
     * @date - 09-01-2016
     */
    public FarmInfoView getFarmInfoView() {

        return farmInfoView;

    }

    /**
     * @return List with all crops for the farm
     * @changed - Abhishek
     * @date - 09-01-2016
     */
    public List<CropTypeView> getCropsDetailsForFarm() {

        List<CropTypeView> cropTypeViewList = (List<CropTypeView>) baseSelectedOutpuDetailsJsonObject.get("cropTypeView");

        if (cropTypeViewList.size() == 0)
            return cropTypeViewList;
        else
            return null;

    }

    /**
     * @return List with all forward-sales for the farm buy acreage
     * @changed - Abhishek
     * @date - 09-01-2016
     * @logic- if crop is of firm type or of proposed type then only the crop is applicable or forward sales
     */
    public List<CropTypeView> getForwardSales() {
        /*List<FarmOutputDetailsView> farmOutputDetails = (List<FarmOutputDetailsView>) baseSelectedOutpuDetailsJsonObject.get("farmOutputDetails");

        List<CropTypeView> forwardSales = new ArrayList<CropTypeView>();

        /*for (FarmOutputDetailsView farmOutputDetailsView : farmOutputDetails) {
            CropTypeView crop = farmOutputDetailsView.getCropTypeViewList();
            if (crop.getFirmchecked().equalsIgnoreCase("true") || crop.getProposedchecked()) {
                forwardSales.add(crop);
            }
        }*/
        List<CropTypeView> forwardSales = (List<CropTypeView>) baseSelectedOutpuDetailsJsonObject.get("cropTypeView");


        return forwardSales;

    }

    /**
     * @added - Abhishek
     * @date - 09-12-2015
     * @updated - 09-02-2016
     * @return List with all forward-sales for the farm buy field
     * @logic- if crop is of firm type or of proposed type then only the crop is applicable or forward sales
     */
    /*public List<CropTypeView> getForwardSalesByField() {
        List<FarmOutputDetailsForFieldView> farmOutputDetails = (List<FarmOutputDetailsForFieldView>) baseSelectedOutpuDetailsJsonObject.get("farmOutputDetails");

        List<CropTypeView> forwardSales = new ArrayList<CropTypeView>();

        for (FarmOutputDetailsForFieldView farmOutputDetailsView : farmOutputDetails) {
            CropTypeView crop = farmOutputDetailsView.getCropTypeViewList();
            if (crop.getFirmchecked().equalsIgnoreCase("true") || crop.getProposedchecked()) {
                forwardSales.add(crop);
            }
        }

        List<CropTypeView> forwardSales = (List<CropTypeView>) baseSelectedOutpuDetailsJsonObject.get("cropTypeView");

        return forwardSales;

    }*/

    /**
     * @return List with all output details farm by acreage(Baseline Strategy) for the farm
     * @chanegd - Abhishek
     * @date - 09-01-2016
     * @updated - 09-01-2016
     */
    public List<FarmOutputDetailsView> getOutputDetailsForFarm() {

        List<FarmOutputDetailsView> farmOutputDetails = (List<FarmOutputDetailsView>) baseSelectedOutpuDetailsJsonObject.get("farmOutputDetails");

        /*if(farmInfoView != null){*/
        return farmOutputDetails;
        /*} else {
            return null;
        }*/

    }

    /**
     * @return List with all output details farm by field(Baseline Strategy) for the farm
     * @changed - Abhishek
     * @date - 09-12-2015
     * @updated - 09-02-2016
     */
    public JSONObject getBaseSelectedDetailsOutputDetails() {

        /*List<FarmOutputDetailsForFieldView> farmOutputDetails = (List<FarmOutputDetailsForFieldView>) baseSelectedOutpuDetailsJsonObject.get("farmOutputDetails");

        if(farmInfoView != null){
            return farmOutputDetails;
        } else {
            return null;
        }*/
        return baseSelectedOutpuDetailsJsonObject;

    }

    /**
     * @changed - Abhishek
     * @updated - 09-01-2016
     * @desc - used data used for generation of pie chart from JsonObject instead of services
     */
    public Image getCropsAndAcreagePieChart() throws IOException, BadElementException {
        DefaultPieDataset dataset = new DefaultPieDataset();
        FarmInfoView farmInfoView = getFarmInfoView();
        if (farmInfoView.getStrategy().equals(PlanByStrategy.PLAN_BY_ACRES)) {
            /**
             * @changed - Abhishek
             * @date - 09-01-2016
             */
            List<FarmOutputDetailsView> cropsDetailsForFarm = getOutputDetailsForFarm();
            for (FarmOutputDetailsView farmOutputDetailsView : cropsDetailsForFarm) {
                /**
                 * @changed - Abhishek
                 * @date - 11-02-2016
                 * @desc - applied % symbol for acreage on graph by slide#1 of 02102016
                 */
                if (farmOutputDetailsView.getUsedAcresPercentage() == 0) {
                    continue;
                } else {
                    dataset.setValue(farmOutputDetailsView.getCropTypeView().getCropName() + ": " + farmOutputDetailsView.getUsedAcres(), farmOutputDetailsView.getUsedAcresAsDouble());
                }
            }
        } else if (farmInfoView.getStrategy().equals(PlanByStrategy.PLAN_BY_FIELDS)) {
            /**
             * @changed - Abhishek
             * @date - 09-01-2016
             */
            JSONObject baseSelectedOutpuDetailsJsonObject = getBaseSelectedDetailsOutputDetails();
            Map<String, String> hashMapForAcre = (Map<String, String>) baseSelectedOutpuDetailsJsonObject.get("hashMapForAcre");

            Set<String> keySet = hashMapForAcre.keySet();

            for (String cropKey : keySet) {
                String acreagePercentage = hashMapForAcre.get(cropKey);
                acreagePercentage = acreagePercentage.substring(0, acreagePercentage.indexOf('(')).trim();
                /**
                 * @changed - Abhishek
                 * @date - 11-02-2016
                 * @desc - applied % symbol for acreage on graph by slide#1 of 02102016
                 * @update - 29-02-2016
                 * @desc - removed crop whose profit is 0
                 */
                if (acreagePercentage.equalsIgnoreCase("0")) {
                    continue;
                } else {
                    dataset.setValue(cropKey + ": " + acreagePercentage, Double.parseDouble(AgricultureStandardUtils.removeAllCommas(acreagePercentage)));
                }
            }
        }
        /**
         * @changed - Abhishek
         * @date - 25-01-2016
         * @desc - changed according to slide#4 of 12282015
         */
        /*JFreeChart pieChart = ChartFactory.createPieChart("", dataset, false, false, false);*/
        JFreeChart pieChart = ChartFactory.createPieChart("", dataset, false, false, false);
        pieChart.setBackgroundPaint(new Color(192, 202, 144));
        PiePlot plot = (PiePlot) pieChart.getPlot();
        plot.setBackgroundPaint(new Color(192, 202, 144));
        plot.setLabelOutlinePaint(null);
        plot.setLabelBackgroundPaint(null);
        /**
         * @changed - Abhishek
         * @date - 11-02-2016
         * @desc - removed shadow from charts according to slide#1 of 02102016
         */
        plot.setShadowPaint(null);
        plot.setLabelShadowPaint(null);

        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.7f);
        plot.setLabelFont(new Font("Arial Unicode MS", 0, 10));
        plot.setSimpleLabels(false);
        plot.setSectionOutlinesVisible(false);
        //ChartUtilities.saveChartAsPNG(new File("E:\\Idea Workspace\\a.png"), pieChart, 250, 150);
        return Image.getInstance(pieChart.createBufferedImage(260, 150), null);

    }

    /**
     * @changed - Abhishek
     * @updated - 09-01-2016
     * @date - 09-12-2015
     * @desc - used data used for generation of pie chart from JsonObject instead of services
     */
    public Image getResourceManagementHorizontalBarChart() throws IOException, BadElementException {
        List<CropResourceUsageView> resourceList = (List<CropResourceUsageView>) baseSelectedOutpuDetailsJsonObject.get("resourceList");

        /**
         * @changed - Abhishek
         * @date - 25-01-2016
         * @desc - changed according to slide#3 of 12282015
         */
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        /**
         * @changed - Abhishek
         * @date - 09-01-2016
         * @updated - 25-01-2016
         * @desc - changed according to slide#3 of 12282015
         */
//        Double[][] cropUsedAndUnusedArray = new Double[((List<CropResourceUsageView>)baseSelectedOutpuDetailsJsonObject.get("resourceList")).size()][2];


        /**
         * @changed - Abhishek
         * @date - 09-01-2016
         * @updated - 25-01-2016
         * @desc - changed according to slide#3 of 12282015
         */
        Map<String, String> cropResourceUsed = ((Map<String, String>) baseSelectedOutpuDetailsJsonObject.get("cropResourceUsed"));
//            cropUsedAndUnusedArray[0][k] = Double.parseDouble(AgricultureStandardUtils.removeAllCommas(cropResourceUsed.getValue()));
            Double totalResource = 0.0;
            if(cropResourceUsed.containsKey("Land")){

                for (CropResourceUsageView cropResourceUsageView : resourceList) {
                    if (cropResourceUsageView.getCropResourceUse().equalsIgnoreCase("Land")) {
                        totalResource = Double.parseDouble(AgricultureStandardUtils.removeAllCommas(cropResourceUsageView.getCropResourceUseAmount().equalsIgnoreCase("") ? "0": cropResourceUsageView.getCropResourceUseAmount()));
                    }
                }
                totalResource = (Double.parseDouble(AgricultureStandardUtils.removeAllCommas(cropResourceUsed.get("Land"))) * 100) / totalResource;
                totalResource = AgricultureStandardUtils.doubleUptoSingleDecimalPoint(totalResource);

                dataset.addValue(totalResource, "Used", "% " + "Land");
            }if(cropResourceUsed.containsKey("Capital")){

                for (CropResourceUsageView cropResourceUsageView : resourceList) {
                    if (cropResourceUsageView.getCropResourceUse().equalsIgnoreCase("Capital")) {
                        totalResource = Double.parseDouble(AgricultureStandardUtils.removeAllCommas(cropResourceUsageView.getCropResourceUseAmount().equalsIgnoreCase("") ? "0": cropResourceUsageView.getCropResourceUseAmount()));
                    }
                }
                totalResource = (Double.parseDouble(AgricultureStandardUtils.removeAllCommas(cropResourceUsed.get("Capital"))) * 100) / totalResource;
                totalResource = AgricultureStandardUtils.doubleUptoSingleDecimalPoint(totalResource);

                dataset.addValue(totalResource, "Used", "% " + "Capital");
            }

                for (Map.Entry<String, String> cropResources : ((Map<String, String>) baseSelectedOutpuDetailsJsonObject.get("cropResourceUsed")).entrySet()) {
                    if (!cropResources.getKey().equalsIgnoreCase("Land") && !cropResources.getKey().equalsIgnoreCase("Capital")) {

                        for (CropResourceUsageView cropResourceUsageView : resourceList) {
                            if (cropResourceUsageView.getCropResourceUse().equalsIgnoreCase(cropResources.getKey())) {
                                totalResource = Double.parseDouble(AgricultureStandardUtils.removeAllCommas(cropResourceUsageView.getCropResourceUseAmount().equalsIgnoreCase("") ? "0" : cropResourceUsageView.getCropResourceUseAmount()));
                            }
                        }
                        totalResource = (Double.parseDouble(AgricultureStandardUtils.removeAllCommas(cropResources.getValue())) * 100) / totalResource;
                        totalResource = AgricultureStandardUtils.doubleUptoSingleDecimalPoint(totalResource);
                        dataset.addValue(totalResource, "Used", "% " + cropResources.getKey().substring(0, 5) + "...");
                    }
                }





        /**
         * @changed - Abhishek
         * @date - 09-01-2016
         * @updated - 25-01-2016
         * @desc - changed according to slide#3 of 12282015
         */

        Map<String, String> cropResourceUnused1 = ((Map<String, String>) baseSelectedOutpuDetailsJsonObject.get("cropResourceUnused"));
//            cropUsedAndUnusedArray[0][k] = Double.parseDouble(AgricultureStandardUtils.removeAllCommas(cropResourceUsed.getValue()));
        //Double totalResource = 0.0;
        if(cropResourceUnused1.containsKey("Land")){
            for (CropResourceUsageView cropResourceUsageView : resourceList) {
                if (cropResourceUsageView.getCropResourceUse().equalsIgnoreCase("Land")) {
                    totalResource = Double.parseDouble(AgricultureStandardUtils.removeAllCommas(cropResourceUsageView.getCropResourceUseAmount().equalsIgnoreCase("") ? "0": cropResourceUsageView.getCropResourceUseAmount()));
                }
            }
            totalResource = (Double.parseDouble(AgricultureStandardUtils.removeAllCommas(cropResourceUnused1.get("Land"))) * 100) / totalResource;
            totalResource = AgricultureStandardUtils.doubleUptoSingleDecimalPoint(totalResource);
            dataset.addValue(totalResource, "Unused", "% " + "Land");
        } if(cropResourceUnused1.containsKey("Capital")){
            for (CropResourceUsageView cropResourceUsageView : resourceList) {
                if (cropResourceUsageView.getCropResourceUse().equalsIgnoreCase("Capital")) {
                    totalResource = Double.parseDouble(AgricultureStandardUtils.removeAllCommas(cropResourceUsageView.getCropResourceUseAmount().equalsIgnoreCase("") ? "0": cropResourceUsageView.getCropResourceUseAmount()));
                }
            }
            totalResource = (Double.parseDouble(AgricultureStandardUtils.removeAllCommas(cropResourceUnused1.get("Capital"))) * 100) / totalResource;
            totalResource = AgricultureStandardUtils.doubleUptoSingleDecimalPoint(totalResource);
            dataset.addValue(totalResource, "Unused", "% " + "Capital");
        }

            for (Map.Entry<String, String> cropResourceUnused : ((Map<String, String>) baseSelectedOutpuDetailsJsonObject.get("cropResourceUnused")).entrySet()) {
//            cropUsedAndUnusedArray[1][k] = Double.parseDouble(AgricultureStandardUtils.removeAllCommas(cropResourceUnused.getValue()));
                //Double totalResource = 0.0;
                if (!cropResourceUnused.getKey().equalsIgnoreCase("Land") && !cropResourceUnused.getKey().equalsIgnoreCase("Capital")) {
                    for (CropResourceUsageView cropResourceUsageView : resourceList) {
                        if (cropResourceUsageView.getCropResourceUse().equalsIgnoreCase(cropResourceUnused.getKey())) {
                            totalResource = Double.parseDouble(AgricultureStandardUtils.removeAllCommas(cropResourceUsageView.getCropResourceUseAmount().equalsIgnoreCase("") ? "0" : cropResourceUsageView.getCropResourceUseAmount()));
                        }
                    }
                    totalResource = (Double.parseDouble(AgricultureStandardUtils.removeAllCommas(cropResourceUnused.getValue())) * 100) / totalResource;
                    totalResource = AgricultureStandardUtils.doubleUptoSingleDecimalPoint(totalResource);
                    dataset.addValue(totalResource, "Unused", "% " + cropResourceUnused.getKey().substring(0, 5) + "...");

                }
            }


        /**
         * @changed - Abhishek
         * @date - 25-01-2016
         * @desc - changed according to slide#3 of 12282015
         */
//        CategoryDataset categoryDataset = DatasetUtilities.createCategoryDataset(" ", " ", cropUsedAndUnusedArray);

        /**
         * @changed - Abhishek
         * @date - 25-01-2016
         * @desc - changed according to slide#4 of 12282015
         */
        /*final JFreeChart chart = ChartFactory.createStackedBarChart(*/
        JFreeChart chart = ChartFactory.createStackedBarChart(
                "",                             // chart title
                "",                             // domain axis label
                "",                             // range axis label
                dataset,                        // data
                PlotOrientation.HORIZONTAL,     // the plot orientation
                true,                          // include legend
                false,                          // tooltips
                false                           // urls
        );

        CategoryPlot plot = (CategoryPlot) chart.getPlot();

        plot.setBackgroundPaint(new Color(192, 202, 144));

        plot.getRenderer().setSeriesPaint(0, new Color(30, 100, 175));
        plot.getRenderer().setSeriesPaint(1, new Color(90, 190, 110));

        plot.clearDomainMarkers();
        plot.clearRangeMarkers();


        return Image.getInstance(chart.createBufferedImage(250, 150), null);

    }

    /**
     * @changed - Abhishek
     * @date - 09-01-2016
     * @desc - used data used for generation of pie chart from JsonObject instead of services
     */
    public List<HashMap<String, String>> getResourceUsageViewByFarm() {
        List<CropResourceUsageView> resourceUsageViews = (List<CropResourceUsageView>) baseSelectedOutpuDetailsJsonObject.get("resourceList");

        List<HashMap<String, String>> finalData = new ArrayList<HashMap<String, String>>();
        for (CropResourceUsageView resource : resourceUsageViews) {
            if (resource.isActive()) {
                HashMap<String, String> data = new HashMap<String, String>();
                if (resource.getCropResourceUse().equalsIgnoreCase("capital"))
                    data.put("resource", resource.getCropResourceUse() + " ($)");
                else
                    data.put("resource", resource.getCropResourceUse() + " (" + resource.getUoMResource() + ")");

                data.put("uom", resource.getUoMResource());
                data.put("totalAmount", resource.getCropResourceUseAmount());
                data.put("used", ((Map<String, String>) baseSelectedOutpuDetailsJsonObject.get("cropResourceUsed")).get(resource.getCropResourceUse()));
                data.put("unused", ((Map<String, String>) baseSelectedOutpuDetailsJsonObject.get("cropResourceUnused")).get(resource.getCropResourceUse()));
                finalData.add(data);
            }
        }
        return finalData;
    }

    /**
     * @param farmInfoView
     * @return - total amount of forward sales
     * @changed - Abhishek
     * @date - 12-12-2015
     * @updated - 09-02-2015
     */
    public String getTotalForwardSales(FarmInfoView farmInfoView) {
        Map<String, String> mapDifferentValues = (Map<String, String>) baseSelectedOutpuDetailsJsonObject.get("mapDifferentValues");

        return mapDifferentValues.get("usedForwardAcresP");

        /*int totalForwardSales = 0;
        int landUsage = 0;

        if(farmInfoView.getStrategy().equals(PlanByStrategy.PLAN_BY_ACRES)){
            for (FarmOutputDetailsView farmOutputDetailsView : (List<FarmOutputDetailsView>)baseSelectedOutpuDetailsJsonObject.get("farmOutputDetails")) {

                if(farmOutputDetailsView.getForProposed() || farmOutputDetailsView.getForFirm()){
                    totalForwardSales += farmOutputDetailsView.getProfitAsInteger();
                }

                landUsage += Integer.parseInt(farmOutputDetailsView.getUsedAcres().replaceAll("\\,",""));
            }
        } else if(farmInfoView.getStrategy().equals(PlanByStrategy.PLAN_BY_FIELDS)){
            for (FarmOutputDetailsForFieldView farmOutputDetailsForFieldView : (List<FarmOutputDetailsForFieldView>)baseSelectedOutpuDetailsJsonObject.get("farmOutputDetails")) {

                if(farmOutputDetailsForFieldView.getCropTypeViewList().getProposedchecked() || farmOutputDetailsForFieldView.getCropTypeViewList().getFirmchecked().equalsIgnoreCase("true")){
                    totalForwardSales += farmOutputDetailsForFieldView.getProfitAsInteger();
                }

                landUsage += Integer.parseInt(farmOutputDetailsForFieldView.getUsedAcres().replaceAll("\\,",""));
            }
        }

        ForwardSales f = new ForwardSales();

        f.setTotalForwardSales(totalForwardSales);
        f.setLandUsage(landUsage);
        return f;*/
    }

    /**
     * @changed - Abhishek
     * @date - 12-12-2015
     * @updated - 09-02-2015
     */
    /*public class ForwardSales{

        private int totalForwardSales;
        private int landUsage;

        public int getTotalForwardSales() {
            return totalForwardSales;
        }

        public void setTotalForwardSales(int totalForwardSales) {
            this.totalForwardSales = totalForwardSales;
        }

        public int getLandUsage() {
            return landUsage;
        }

        public void setLandUsage(int landUsage) {
            this.landUsage = landUsage;
        }
    }*/

    /**
     * @return Percentage Conservation of land
     * @throws JSONException
     * @added - Abhishek
     * @date - 12-12-2015
     */
    public ConservationPracticeBean getLandUnderConservationPractice() {

        double landUnderConservation = 0.0;
        double incomeUnderConservation = 0.0;

        DecimalFormat formatter = new DecimalFormat("#.00");

        if (farmInfoView.getStrategy().equals(PlanByStrategy.PLAN_BY_ACRES)) {

            for (FarmOutputDetailsView farmOutputDetails : (List<FarmOutputDetailsView>) baseSelectedOutpuDetailsJsonObject.get("farmOutputDetails")) {
                if (farmOutputDetails.getCropTypeView().getConservation_Crop().equalsIgnoreCase("true")) {
                    landUnderConservation += farmOutputDetails.getUsedAcresPercentage();
                    /**
                     * @chanegd - Abhishek
                     * @date - 09-02-2016
                     */
                    /*incomeUnderConservation += Double.parseDouble(formatter.format((farmOutputDetails.getProfitDouble() / Double.parseDouble(SectionOnePDFGenerator.getEstimatedIncome().replaceAll("\\,", ""))) * 100));*/
                    incomeUnderConservation += farmOutputDetails.getUsedCapitalPercentage();
                }
            }


        } else if (farmInfoView.getStrategy().equals(PlanByStrategy.PLAN_BY_FIELDS)) {
            Map<String, String> hashMapForAcre = (Map<String, String>) baseSelectedOutpuDetailsJsonObject.get("hashMapForAcre");
            Map<String, String> hashMapForProfit = (Map<String, String>) baseSelectedOutpuDetailsJsonObject.get("hashMapForProfit");
            List<CropTypeView> cropTypeViewList = (List<CropTypeView>) baseSelectedOutpuDetailsJsonObject.get("cropTypeView");

            Set<String> keySet = hashMapForAcre.keySet();

            for (String cropKey : keySet) {
                for (CropTypeView cropTypeView : cropTypeViewList) {
                    if (cropTypeView.getSelected()
                            && cropTypeView.getConservation_Crop().equalsIgnoreCase("true")
                            && cropKey.contains(cropTypeView.getCropName())) {
                        String land = hashMapForAcre.get(cropKey);
                        landUnderConservation += Double.parseDouble(land.substring(land.indexOf('(') + 1, land.indexOf('%')).replaceAll("\\,", ""));
                        String income = hashMapForProfit.get(cropKey);
                        incomeUnderConservation += Double.parseDouble(income.substring(income.indexOf('(') + 1, income.indexOf('%')).replaceAll("\\,", ""));
                    }
                }
            }

        }

        ConservationPracticeBean conservationBean = new ConservationPracticeBean();

        conservationBean.setLandUnderConservation(formatter.format(landUnderConservation));
        conservationBean.setProfitFromConservation(formatter.format(incomeUnderConservation));

        return conservationBean;
    }

    /**
     * @added - Abhishek
     * @date - 25-01-2016
     * @desc - Added according to slide#3 of 12282015
     */
    public ConservationPracticeBean getRiskManagementLandProfit() {
        Double landUnderConservation = 0.0;
        Double incomeUnderConservation = 0.0;

        DecimalFormat formatter = new DecimalFormat("#.0");

        if (farmInfoView.getStrategy().equals(PlanByStrategy.PLAN_BY_ACRES)) {

            for (FarmOutputDetailsView farmOutputDetails : (List<FarmOutputDetailsView>) baseSelectedOutpuDetailsJsonObject.get("farmOutputDetails")) {
                if (farmOutputDetails.getCropTypeView().getHiRiskCrop().equalsIgnoreCase("true")) {
                    landUnderConservation += farmOutputDetails.getUsedAcresPercentage();
                    incomeUnderConservation += Double.parseDouble(formatter.format((farmOutputDetails.getProfitDouble() / Double.parseDouble(SectionOnePDFGenerator.getEstimatedIncome().replaceAll("\\,", ""))) * 100));
                }
            }


        } else if (farmInfoView.getStrategy().equals(PlanByStrategy.PLAN_BY_FIELDS)) {
            Map<String, String> hashMapForAcre = (Map<String, String>) baseSelectedOutpuDetailsJsonObject.get("hashMapForAcre");
            Map<String, String> hashMapForProfit = (Map<String, String>) baseSelectedOutpuDetailsJsonObject.get("hashMapForProfit");
            List<CropTypeView> cropTypeViewList = (List<CropTypeView>) baseSelectedOutpuDetailsJsonObject.get("cropTypeView");

            Set<String> keySet = hashMapForAcre.keySet();

            for (String cropKey : keySet) {
                for (CropTypeView cropTypeView : cropTypeViewList) {
                    if (cropTypeView.getSelected()
                            && cropTypeView.getHiRiskCrop().equalsIgnoreCase("true")
                            && cropKey.contains(cropTypeView.getCropName())) {
                        String land = hashMapForAcre.get(cropKey);
                        landUnderConservation += Double.parseDouble(land.substring(land.indexOf('(') + 1, land.indexOf('%')).replaceAll("\\,", ""));
                        String income = hashMapForProfit.get(cropKey);
                        incomeUnderConservation += Double.parseDouble(income.substring(income.indexOf('(') + 1, income.indexOf('%')).replaceAll("\\,", ""));
                    }
                }
            }
            /*for (CropTypeView cropTypeView : (List<CropTypeView>) baseSelectedOutpuDetailsJsonObject.get("cropTypeView")) {
                if (cropTypeView.getSelected() && cropTypeView.getHiRiskCrop().equalsIgnoreCase("true")) {
                    String land = ((Map<String, String>)baseSelectedOutpuDetailsJsonObject.get("hashMapForAcre")).get(cropTypeView.getCropName());
                    landUnderConservation += Double.parseDouble(land.substring(land.indexOf('(') + 1, land.indexOf('%')).replaceAll("\\,", ""));
                    String income = ((Map<String, String>) baseSelectedOutpuDetailsJsonObject.get("hashMapForProfit")).get(cropTypeView.getCropName());
                    incomeUnderConservation += Double.parseDouble(income.substring(income.indexOf('(') + 1, income.indexOf('%')).replaceAll("\\,", ""));
                }
            }*/

        }

        ConservationPracticeBean conservationBean = new ConservationPracticeBean();

        conservationBean.setLandUnderConservation(formatter.format(landUnderConservation));
        conservationBean.setProfitFromConservation(formatter.format(incomeUnderConservation));

        return conservationBean;
    }

    public class ConservationPracticeBean {
        String landUnderConservation;
        String profitFromConservation;

        public String getLandUnderConservation() {
            if (landUnderConservation.equalsIgnoreCase(".0"))
                return "0.0";
            else if (landUnderConservation.charAt(0) == '.')
                return "0" + landUnderConservation;
            else
                return landUnderConservation;
        }

        public void setLandUnderConservation(String landUnderConservation) {
            this.landUnderConservation = landUnderConservation;
        }

        public String getProfitFromConservation() {
            if (profitFromConservation.equalsIgnoreCase(".0"))
                return "0.0";
            else if (profitFromConservation.charAt(0) == '.')
                return "0" + profitFromConservation;
            else
                return profitFromConservation;
        }

        public void setProfitFromConservation(String profitFromConservation) {
            this.profitFromConservation = profitFromConservation;
        }
    }


    public String getForwardSalesStatus(CropTypeView cropTypeView) {
        boolean flag = false;
        if (Objects.equals(farmInfoView.getStrategy(), PlanByStrategy.PLAN_BY_ACRES)) {

            List<FarmOutputDetailsView> farmOutputDetailsViewList = (List<FarmOutputDetailsView>) baseSelectedOutpuDetailsJsonObject.get("farmOutputDetails");
            for (FarmOutputDetailsView farmOutputDetailsView : farmOutputDetailsViewList) {

                if (Objects.equals(cropTypeView.getId(), farmOutputDetailsView.getCropTypeView().getId())) {
                    if (farmOutputDetailsView.getForProposed() && !farmOutputDetailsView.getUsedAcres().equalsIgnoreCase("0")) {
                        flag = true;
                    }
                }
            }

        } else if (Objects.equals(farmInfoView.getStrategy(), PlanByStrategy.PLAN_BY_FIELDS)) {
            List<FarmOutputDetailsForFieldView> farmOutputDetailsForFieldViewList = (List<FarmOutputDetailsForFieldView>) baseSelectedOutpuDetailsJsonObject.get("farmOutputDetails");

            for (FarmOutputDetailsForFieldView farmOutputDetailsForFieldView : farmOutputDetailsForFieldViewList) {

                if (Objects.equals(cropTypeView.getId(), farmOutputDetailsForFieldView.getCropTypeView().getId())) {
                    if (farmOutputDetailsForFieldView.isForProposed() && !farmOutputDetailsForFieldView.getUsedAcres().equalsIgnoreCase("0")) {
                        flag = true;
                    }
                }
            }

        }

        if (cropTypeView.getFirmchecked().equalsIgnoreCase("true")) {
            return "Yes";
        } else if (cropTypeView.getProposedchecked() && flag) {
            return "Yes";
        } else {
            return "No";
        }
    }


}

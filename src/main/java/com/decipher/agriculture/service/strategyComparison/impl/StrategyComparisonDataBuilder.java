package com.decipher.agriculture.service.strategyComparison.impl;

import com.decipher.agriculture.data.farm.PlanByStrategy;
import com.decipher.agriculture.service.farmDetails.FarmOutputCalculationService;
import com.decipher.util.AgricultureStandard;
import com.decipher.util.AgricultureStandardUtils;
import com.decipher.util.PlantingProfitLogger;
import com.decipher.view.form.farmDetails.CropResourceUsageView;
import com.decipher.view.form.farmDetails.CropTypeView;
import com.decipher.view.form.farmDetails.FarmInfoView;
import com.decipher.view.form.farmDetails.FarmOutputDetailsForFieldView;
import com.decipher.view.form.farmDetails.FarmOutputDetailsView;
import com.decipher.view.form.strategy.FarmCustomStrategyView;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Created by abhishek on 20/4/16.
 */
@SuppressWarnings("unchecked")
class StrategyComparisonDataBuilder {

    private FarmOutputCalculationService farmOutputCalculationService;

    private Map<FarmCustomStrategyView, JSONObject> strategyDetailsForFarm;

    private int[] strategyIdArray;

    public FarmOutputCalculationService getFarmOutputCalculationService() {
        return farmOutputCalculationService;
    }

    public void setFarmOutputCalculationService(FarmOutputCalculationService farmOutputCalculationService) {
        this.farmOutputCalculationService = farmOutputCalculationService;
    }

    StrategyComparisonDataBuilder(Map<FarmCustomStrategyView, JSONObject> strategyDetailsForFarm, int[] strategyIdArray) {

        this.strategyDetailsForFarm = strategyDetailsForFarm;
        this.strategyIdArray = strategyIdArray;

    }

    /*public JSONArray getGraphObject(StrategyComparisonType xAxisType, StrategyComparisonType yAxisType){
        Set<Map.Entry<FarmCustomStrategyView, JSONObject>> entries = strategyDetailsForFarm.entrySet();
        JSONArray jsonArray = new JSONArray();
        int count = 0;
        for (Map.Entry<FarmCustomStrategyView, JSONObject> entry : entries) {
            JSONObject graphJsonObject = new JSONObject();
            FarmCustomStrategyView farmCustomStrategyView = entry.getKey();

            graphJsonObject.put("balloonText", xAxisType + " : <b>[[x" + count + "]]</b><br>" + yAxisType + " : <b>[[y" + count + "]]</b><br>Strategy Name : <b>[[value" + count + "]]</b>");
            graphJsonObject.put("bullet", "circle");
            graphJsonObject.put("bulletBorderAlpha", 0.2);
            graphJsonObject.put("bulletAlpha", 0.8);
            graphJsonObject.put("lineAlpha", 0);
            graphJsonObject.put("fillAlphas", 0);
            graphJsonObject.put("valueField", "value" + count);
            graphJsonObject.put("xField", "x" + count);
            graphJsonObject.put("yField", "y" + count);
            graphJsonObject.put("title", farmCustomStrategyView.getStrategyName());
            graphJsonObject.put("bulletSize", 20);

            count++;

            jsonArray.add(graphJsonObject);
        }

        return jsonArray;
    }*/

    JSONArray getGraphObject(String xAxisText, String yAxisText) {
        Set<Map.Entry<FarmCustomStrategyView, JSONObject>> entries = strategyDetailsForFarm.entrySet();
        JSONArray jsonArray = new JSONArray();
        int count = 0;
        for (Map.Entry<FarmCustomStrategyView, JSONObject> entry : entries) {
            JSONObject graphJsonObject = new JSONObject();
            FarmCustomStrategyView farmCustomStrategyView = entry.getKey();

            graphJsonObject.put("balloonText", xAxisText + " : <b>[[x]]</b><br>" + yAxisText + " : <b>[[y]]</b><br>Strategy Name : <b>[[value" + count + "]]</b>");
            graphJsonObject.put("bullet", "circle");
//            graphJsonObject.put("bulletBorderAlpha", 0.2);
//            graphJsonObject.put("bulletAlpha", 0.8);
            graphJsonObject.put("lineAlpha", 0);
//            graphJsonObject.put("fillAlphas", 0);
            graphJsonObject.put("valueField", "value" + count);
            graphJsonObject.put("xField", "x" + count);
            graphJsonObject.put("yField", "y" + count);
            graphJsonObject.put("title", farmCustomStrategyView.getStrategyName());
            graphJsonObject.put("bulletSize", 20);
            /*graphJsonObject.put("balloonFunction", "function(item, graph) {" +
                    "   console.log('item : ' + item)" +
                    "   console.log('graph : ' + graph)" +
                    "      var result = graph.balloonText;" +
                    "      for (var key in item.dataContext) {" +
                    "        if (item.dataContext.hasOwnProperty(key) && !isNaN(item.dataContext[key])) {" +
                    "          var formatted = AmCharts.formatNumber(item.dataContext[key], {" +
                    "            precision: chart.precision," +
                    "            decimalSeparator: chart.decimalSeparator," +
                    "            thousandsSeparator: chart.thousandsSeparator" +
                    "          }, 2);" +
                    "          result = result.replace('[[' + key + ']]', formatted);" +
                    "        }" +
                    "      }" +
                    "      return result;" +
                    "    }");*/

            count++;

            jsonArray.add(graphJsonObject);
        }

        return jsonArray;
    }

    JSONObject getPotentialProfitComparisonDetails(String axis) {

        Set<Map.Entry<FarmCustomStrategyView, JSONObject>> entries = strategyDetailsForFarm.entrySet();

        JSONObject graphDataJsonObject = new JSONObject();

        int count = 0;
        for (Map.Entry<FarmCustomStrategyView, JSONObject> entry : entries) {
            FarmCustomStrategyView farmCustomStrategyView = entry.getKey();
            JSONObject strategyOutput = entry.getValue();

            graphDataJsonObject.put(axis + count, AgricultureStandardUtils.removeAllCommas(strategyOutput.get("potentialProfit").toString()));
            graphDataJsonObject.put("value" + count, farmCustomStrategyView.getStrategyName());

            count++;
        }

        return graphDataJsonObject;

    }

    JSONObject getLandUsageComparisonDetails(String axis) {
        Set<Map.Entry<FarmCustomStrategyView, JSONObject>> entries = strategyDetailsForFarm.entrySet();

        JSONObject graphDataJsonObject = new JSONObject();

        int count = 0;
        for (Map.Entry<FarmCustomStrategyView, JSONObject> entry : entries) {
            FarmCustomStrategyView farmCustomStrategyView = entry.getKey();

            List<CropResourceUsageView> resourceUsageViewList = (List<CropResourceUsageView>) entry.getValue().get("resourceList");

            for (CropResourceUsageView cropResourceUsageView : resourceUsageViewList) {

                if (cropResourceUsageView.getCropResourceUse().equalsIgnoreCase("land")) {
                    String resourceUsed = ((HashMap<String, String>) entry.getValue().get("cropResourceUsed")).get(cropResourceUsageView.getCropResourceUse());

                    graphDataJsonObject.put(axis + count, Double.parseDouble(AgricultureStandardUtils.removeAllCommas(resourceUsed)));
                    graphDataJsonObject.put("value" + count, farmCustomStrategyView.getStrategyName());
                }

            }

            count++;
        }

        return graphDataJsonObject;
    }

    JSONObject getCapitalUsageComparisonDetails(String axis) {
        Set<Map.Entry<FarmCustomStrategyView, JSONObject>> entries = strategyDetailsForFarm.entrySet();

        JSONObject graphDataJsonObject = new JSONObject();

        int count = 0;
        for (Map.Entry<FarmCustomStrategyView, JSONObject> entry : entries) {
            FarmCustomStrategyView farmCustomStrategyView = entry.getKey();

            List<CropResourceUsageView> resourceUsageViewList = (List<CropResourceUsageView>) entry.getValue().get("resourceList");

            for (CropResourceUsageView cropResourceUsageView : resourceUsageViewList) {

                if (cropResourceUsageView.getCropResourceUse().equalsIgnoreCase("capital")) {
                    String resourceUsed = ((HashMap<String, String>) entry.getValue().get("cropResourceUsed")).get(cropResourceUsageView.getCropResourceUse());

                    graphDataJsonObject.put(axis + count, Double.parseDouble(AgricultureStandardUtils.removeAllCommas(resourceUsed)));
                    graphDataJsonObject.put("value" + count, farmCustomStrategyView.getStrategyName());
                }

            }

            count++;
        }

        return graphDataJsonObject;
    }

    JSONObject getCropAcreagePerCropComparisonDetails(String axis, FarmInfoView farmInfoView) {
        JSONObject graphDataJsonObject = new JSONObject();

        Set<Map.Entry<FarmCustomStrategyView, JSONObject>> entries = strategyDetailsForFarm.entrySet();

        int count = 0;
        for (Map.Entry<FarmCustomStrategyView, JSONObject> entry : entries) {
            FarmCustomStrategyView farmCustomStrategyView = entry.getKey();
            int cropAcreagePerCrop = 0, totalCropAcreage = 0, cropTotal = 0;

            if (Objects.equals(farmCustomStrategyView.getFarmCustomStrategy().getFarmInfo().getStrategy(), PlanByStrategy.PLAN_BY_ACRES)) {
                List<FarmOutputDetailsView> farmOutputDetailsViewList = (List<FarmOutputDetailsView>) entry.getValue().get("farmOutputDetails");


                for (FarmOutputDetailsView farmOutputDetailsView : farmOutputDetailsViewList) {
                    cropTotal++;
                    totalCropAcreage += farmOutputDetailsView.getUsedAcresAsInteger();

                }

            } else if (Objects.equals(farmCustomStrategyView.getFarmCustomStrategy().getFarmInfo().getStrategy(), PlanByStrategy.PLAN_BY_FIELDS)) {

                Map<String, String> mapForAcreage = (Map<String, String>) entry.getValue().get("hashMapForAcre");

                Set<Map.Entry<String, String>> entrySetForAcreage = mapForAcreage.entrySet();
                for (Map.Entry<String, String> cropAcreage : entrySetForAcreage) {

                    String acreage = cropAcreage.getValue().split(" ")[0];
                    cropTotal++;
                    totalCropAcreage += Integer.parseInt(acreage);
                }

            }

            try {
                cropAcreagePerCrop = totalCropAcreage / cropTotal;
            } catch (Exception e) {
                PlantingProfitLogger.error(e);
            }

            graphDataJsonObject.put(axis + count, cropAcreagePerCrop);
            graphDataJsonObject.put("value" + count, entry.getKey().getStrategyName());

            count++;
        }


        return graphDataJsonObject;
    }

    JSONObject getPpFromOneOrTwoProfitCropComparisonDetails(String axis, FarmInfoView farmInfoView, String key) {
        JSONObject graphDataJsonObject = new JSONObject();

        Set<Map.Entry<FarmCustomStrategyView, JSONObject>> entries = strategyDetailsForFarm.entrySet();

        int count = 0;
        for (Map.Entry<FarmCustomStrategyView, JSONObject> entry : entries) {
            FarmCustomStrategyView farmCustomStrategyView = entry.getKey();
            List<String> profitList = new ArrayList<>();

            if (Objects.equals(farmCustomStrategyView.getFarmCustomStrategy().getFarmInfo().getStrategy(), PlanByStrategy.PLAN_BY_ACRES)) {

                List<FarmOutputDetailsView> farmOutputDetailsViewList = (List<FarmOutputDetailsView>) entry.getValue().get("farmOutputDetails");

                for (FarmOutputDetailsView farmOutputDetailsView : farmOutputDetailsViewList) {
                    profitList.add(farmOutputDetailsView.getProfit());
                }


            } else if (Objects.equals(farmCustomStrategyView.getFarmCustomStrategy().getFarmInfo().getStrategy(), PlanByStrategy.PLAN_BY_FIELDS)) {

                Map<String, String> mapForProfit = (Map<String, String>) entry.getValue().get("hashMapForProfit");

                Set<Map.Entry<String, String>> entrySetForProfit = mapForProfit.entrySet();

                for (Map.Entry<String, String> profitEntry : entrySetForProfit) {

                    profitList.add(profitEntry.getValue().split(" ")[0]);

                }

            }
            JSONObject strategyOutput = entry.getValue();

            graphDataJsonObject.put(axis + count, getProfitFromOneOrTwoProfitableCrops(profitList, key, Double.parseDouble(AgricultureStandardUtils.removeAllCommas(strategyOutput.get("potentialProfit").toString()))));
            graphDataJsonObject.put("value" + count, entry.getKey().getStrategyName());

            count++;
        }

        return graphDataJsonObject;
    }

    private double getProfitFromOneOrTwoProfitableCrops(List<String> profitList, String key, Double potentialProfit) {
        int firstCropProfit = Integer.MIN_VALUE;
        int secondCropProfit = Integer.MIN_VALUE;

        for (String profit : profitList) {
            int profitInInteger = Integer.parseInt(profit.replaceAll("\\,", ""));
            if (profitInInteger > firstCropProfit) {
                secondCropProfit = firstCropProfit;
                firstCropProfit = profitInInteger;
            } else if (profitInInteger > secondCropProfit)
                secondCropProfit = profitInInteger;
        }

        DecimalFormat decimalFormat = new DecimalFormat("#.00");

        if (key.equalsIgnoreCase("one") && potentialProfit != null) {

            double singleCropProfitPercentage = (100 * firstCropProfit) / potentialProfit;
            return Double.parseDouble(decimalFormat.format(singleCropProfitPercentage));
        } else if (key.equalsIgnoreCase("two") && potentialProfit != null) {

            double twoCropProfitPercentage = (100 * (firstCropProfit + secondCropProfit)) / potentialProfit;
            return Double.parseDouble(decimalFormat.format(twoCropProfitPercentage));
        } else {
            return 0;
        }

    }

    JSONObject getPpFromForwardSalesComparisonDetails(String axis) {
        Set<Map.Entry<FarmCustomStrategyView, JSONObject>> entries = strategyDetailsForFarm.entrySet();

        JSONObject graphDataJsonObject = new JSONObject();

        int count = 0;
        for (Map.Entry<FarmCustomStrategyView, JSONObject> entry : entries) {
            FarmCustomStrategyView farmCustomStrategyView = entry.getKey();

            Map<String, String> mapDifferentValues = (Map<String, String>) entry.getValue().get("mapDifferentValues");

            String usedForwardAcresP = mapDifferentValues.get("usedForwardAcresP");

            graphDataJsonObject.put(axis + count, Double.parseDouble(usedForwardAcresP));
            graphDataJsonObject.put("value" + count, farmCustomStrategyView.getStrategyName());

            count++;
        }

        return graphDataJsonObject;
    }

    JSONObject getAcreageOrProfitHighRiskCropComparisonDetails(String axis, FarmInfoView farmInfoView, String key) {
        Set<Map.Entry<FarmCustomStrategyView, JSONObject>> entries = strategyDetailsForFarm.entrySet();

        JSONObject graphDataJsonObject = new JSONObject();

        DecimalFormat formatter = new DecimalFormat("#.00");

        int count = 0;
        for (Map.Entry<FarmCustomStrategyView, JSONObject> entry : entries) {
            FarmCustomStrategyView farmCustomStrategyView = entry.getKey();
            JSONObject strategyOutput = entry.getValue();
            Double strategyProfit = Double.parseDouble(AgricultureStandardUtils.removeAllCommas(strategyOutput.get("potentialProfit").toString()));

            Double landUnderHighRisk = 0.0, incomeUnderHighRisk = 0.0;

            if (Objects.equals(farmCustomStrategyView.getFarmCustomStrategy().getFarmInfo().getStrategy(), PlanByStrategy.PLAN_BY_ACRES)) {

                List<FarmOutputDetailsView> farmOutputDetailsViewList = (List<FarmOutputDetailsView>) entry.getValue().get("farmOutputDetails");

                for (FarmOutputDetailsView farmOutputDetailsView : farmOutputDetailsViewList) {

                    if (farmOutputDetailsView.getCropTypeView().getHiRiskCrop().equalsIgnoreCase("true")) {
                        landUnderHighRisk += farmOutputDetailsView.getUsedAcresPercentage();
//                        incomeUnderHighRisk += Double.parseDouble(formatter.format((farmOutputDetailsView.getProfitDouble() / strategyProfit) * 100));
                        incomeUnderHighRisk += Double.parseDouble(formatter.format(farmOutputDetailsView.getUsedCapitalPercentage()));
                    }

                }


            } else if (Objects.equals(farmCustomStrategyView.getFarmCustomStrategy().getFarmInfo().getStrategy(), PlanByStrategy.PLAN_BY_FIELDS)) {

                List<CropTypeView> cropTypeViewList = (List<CropTypeView>) entry.getValue().get("cropTypeView");

                for (CropTypeView cropTypeView : cropTypeViewList) {
                    if (cropTypeView.getSelected() && cropTypeView.getHiRiskCrop().equalsIgnoreCase("true")) {
                        String land = ((Map<String, String>) entry.getValue().get("hashMapForAcre")).get(cropTypeView.getCropName());
                        landUnderHighRisk += Double.parseDouble(land.substring(land.indexOf('(') + 1, land.indexOf('%')).replaceAll("\\,", ""));
                        String income = ((Map<String, String>) entry.getValue().get("hashMapForProfit")).get(cropTypeView.getCropName());
                        incomeUnderHighRisk += Double.parseDouble(income.substring(income.indexOf('(') + 1, income.indexOf('%')).replaceAll("\\,", ""));
                    }
                }

            }

            if (key.equalsIgnoreCase("acreage")) {
                graphDataJsonObject.put(axis + count, Double.parseDouble(AgricultureStandardUtils.commaSeparaterForPriceWithOneDecimal(landUnderHighRisk.toString())));

            } else if (key.equalsIgnoreCase("profit")) {
                graphDataJsonObject.put(axis + count, Double.parseDouble(AgricultureStandardUtils.commaSeparaterForPriceWithOneDecimal(incomeUnderHighRisk.toString())));

            }

            graphDataJsonObject.put("value" + count, farmCustomStrategyView.getStrategyName());

            count++;
        }

        return graphDataJsonObject;
    }

    JSONObject getAcreageOrProfitConservationCropComparisonDetails(String axis, FarmInfoView farmInfoView, String key) {

        Set<Map.Entry<FarmCustomStrategyView, JSONObject>> entries = strategyDetailsForFarm.entrySet();

        JSONObject graphDataJsonObject = new JSONObject();

        DecimalFormat formatter = new DecimalFormat("#.00");

        int count = 0;
        for (Map.Entry<FarmCustomStrategyView, JSONObject> entry : entries) {
            FarmCustomStrategyView farmCustomStrategyView = entry.getKey();
            JSONObject strategyOutput = entry.getValue();
            Double strategyProfit = Double.parseDouble(AgricultureStandardUtils.removeAllCommas(strategyOutput.get("potentialProfit").toString()));


            Double landUnderConservation = 0.0, incomeUnderConservation = 0.0;

            if (Objects.equals(farmCustomStrategyView.getFarmCustomStrategy().getFarmInfo().getStrategy(), PlanByStrategy.PLAN_BY_ACRES)) {

                List<FarmOutputDetailsView> farmOutputDetailsViewList = (List<FarmOutputDetailsView>) entry.getValue().get("farmOutputDetails");

                for (FarmOutputDetailsView farmOutputDetailsView : farmOutputDetailsViewList) {
                    if (farmOutputDetailsView.getCropTypeView().getConservation_Crop().equalsIgnoreCase("true")) {
                        landUnderConservation += farmOutputDetailsView.getUsedAcresPercentage();
                        incomeUnderConservation += Double.parseDouble(formatter.format((farmOutputDetailsView.getProfitDouble() / strategyProfit) * 100));
                    }
                }

            } else if (Objects.equals(farmCustomStrategyView.getFarmCustomStrategy().getFarmInfo().getStrategy(), PlanByStrategy.PLAN_BY_FIELDS)) {

                List<CropTypeView> cropTypeViewList = (List<CropTypeView>) entry.getValue().get("cropTypeView");

                for (CropTypeView cropTypeView : cropTypeViewList) {
                    if (cropTypeView.getSelected()) {
                        if (/*cropTypeView.getSelected() &&*/ cropTypeView.getConservation_Crop().equalsIgnoreCase("true")) {
                            String land = ((Map<String, String>) entry.getValue().get("hashMapForAcre")).get(cropTypeView.getCropName());
                            landUnderConservation += Double.parseDouble(land.substring(land.indexOf('(') + 1, land.indexOf('%')).replaceAll("\\,", ""));
                            String income = ((Map<String, String>) entry.getValue().get("hashMapForProfit")).get(cropTypeView.getCropName());
                            incomeUnderConservation += Double.parseDouble(income.substring(income.indexOf('(') + 1, income.indexOf('%')).replaceAll("\\,", ""));
                        }
                    }

                }

            }

            if (key.equalsIgnoreCase("acreage")) {
                graphDataJsonObject.put(axis + count, landUnderConservation);

            } else if (key.equalsIgnoreCase("profit")) {
                graphDataJsonObject.put(axis + count, incomeUnderConservation);

            }

            graphDataJsonObject.put("value" + count, farmCustomStrategyView.getStrategyName());

            count++;
        }

        return graphDataJsonObject;

    }

    JSONObject getAcreageForCrop(String axis, FarmInfoView farmInfoView, String cropName) {
        JSONObject graphDataJsonObject = new JSONObject();
        int count = 0;

        Set<FarmCustomStrategyView> farmCustomStrategyViewSet = strategyDetailsForFarm.keySet();

        for (FarmCustomStrategyView farmCustomStrategyView : farmCustomStrategyViewSet) {

            JSONObject strategyDetails = strategyDetailsForFarm.get(farmCustomStrategyView);
            int totalAcreage = 0;
            if (Objects.equals(farmCustomStrategyView.getFarmCustomStrategy().getFarmInfo().getStrategy(), PlanByStrategy.PLAN_BY_ACRES)) {

                List<FarmOutputDetailsView> farmOutputDetailsViewList = (List<FarmOutputDetailsView>) strategyDetails.get("farmOutputDetails");

                for (FarmOutputDetailsView farmOutputDetailsView : farmOutputDetailsViewList) {

                    if (cropName.contains("Firm") && farmOutputDetailsView.getCropTypeView().getCropName().contains(cropName.split("()")[0])) {
                        totalAcreage += farmOutputDetailsView.getUsedAcresAsInteger();
                    } else if (cropName.contains("Proposed") && farmOutputDetailsView.getCropTypeView().getCropName().contains(cropName.split("()")[0])) {
                        totalAcreage += farmOutputDetailsView.getUsedAcresAsInteger();
                    } else if (farmOutputDetailsView.getCropTypeView().getCropName().equalsIgnoreCase(cropName)) {
                        totalAcreage += farmOutputDetailsView.getUsedAcresAsInteger();
                    }

                }
            } else if (Objects.equals(farmCustomStrategyView.getFarmCustomStrategy().getFarmInfo().getStrategy(), PlanByStrategy.PLAN_BY_FIELDS)) {
                Map<String, String> hashMapForAcre = (Map<String, String>) strategyDetails.get("hashMapForAcre");
                String acreage = hashMapForAcre.get(cropName);
                if (acreage != null) {
                    acreage = acreage.split(" ")[0];
                    totalAcreage += Integer.parseInt(acreage);
                }

            }

            graphDataJsonObject.put(axis + count, totalAcreage);
            graphDataJsonObject.put("value" + count, farmCustomStrategyView.getStrategyName());

            count++;
        }


        /*Set<Map.Entry<FarmCustomStrategyView, JSONObject>> entries = strategyDetailsForFarm.entrySet();

        for (Map.Entry<FarmCustomStrategyView, JSONObject> entry : entries) {

            int totalCropAcreage = 0;

            if (Objects.equals(farmInfoView.getStrategy(), PlanByStrategy.PLAN_BY_ACRES)) {
                List<FarmOutputDetailsView> farmOutputDetailsViewList = (List<FarmOutputDetailsView>)entry.getValue().get("farmOutputDetails");

                for (FarmOutputDetailsView farmOutputDetailsView : farmOutputDetailsViewList) {

                    if(farmOutputDetailsView.getCropTypeView().getCropName().equalsIgnoreCase(cropTypeView.getCropName())){
                        totalCropAcreage += farmOutputDetailsView.getUsedAcresAsInteger();
                    }

                }

            } else if(Objects.equals(farmInfoView.getStrategy(), PlanByStrategy.PLAN_BY_FIELDS)){

                Map<String, String> mapForAcreage = (Map<String, String>)entry.getValue().get("hashMapForAcre");

                Set<Map.Entry<String, String>> entrySetForAcreage = mapForAcreage.entrySet();
                for (Map.Entry<String, String> cropAcreage : entrySetForAcreage) {

                    if (cropAcreage.getKey().equalsIgnoreCase(cropTypeView.getCropName())) {
                        String acreage = cropAcreage.getValue().split(" ")[0];

                        totalCropAcreage += Integer.parseInt(acreage);
                    }
                }

            }


            graphDataJsonObject.put(axis + count, totalCropAcreage);
            graphDataJsonObject.put("value" + count, entry.getKey().getStrategyName());

            count++;
        }*/


        return graphDataJsonObject;
    }

    JSONObject getPpForMinGivenPriceAndYield(String axis, FarmInfoView farmInfoView) {

        JSONObject graphDataJsonObject = new JSONObject();

        Set<Map.Entry<FarmCustomStrategyView, JSONObject>> entries = strategyDetailsForFarm.entrySet();

        int count = 0;
        for (Map.Entry<FarmCustomStrategyView, JSONObject> entry : entries) {
            FarmCustomStrategyView farmCustomStrategyView = entry.getKey();
            Double profitGained = 0.0;
            if (Objects.equals(farmCustomStrategyView.getFarmCustomStrategy().getFarmInfo().getStrategy(), PlanByStrategy.PLAN_BY_ACRES)) {

                List<FarmOutputDetailsView> farmOutputDetailsViewList = (List<FarmOutputDetailsView>) entry.getValue().get("farmOutputDetails");
                for (FarmOutputDetailsView farmOutputDetailsView : farmOutputDetailsViewList) {


                    if (farmOutputDetailsView.getUsedAcresAsDouble() > 0.0 &&
                            farmOutputDetailsView.getCropTypeView().getIntMinCropYield() != "0" &&
                            farmOutputDetailsView.getCropTypeView().getIntMinCropPrice().longValue() > 0.0) {
                        profitGained += farmOutputDetailsView.getProfitAsDouble();
                    }
                }
            } else if (Objects.equals(farmCustomStrategyView.getFarmCustomStrategy().getFarmInfo().getStrategy(), PlanByStrategy.PLAN_BY_FIELDS)) {

                List<FarmOutputDetailsForFieldView> farmOutputDetailsForFieldViewList = (List<FarmOutputDetailsForFieldView>) entry.getValue().get("farmOutputDetails");
                for (FarmOutputDetailsForFieldView farmOutputDetailsForFieldView : farmOutputDetailsForFieldViewList) {

                    if (farmOutputDetailsForFieldView.getUsedAcresAsDouble() > 0.0
                            && farmOutputDetailsForFieldView.getCropTypeView().getIntMinCropYield() != "0"
                            && farmOutputDetailsForFieldView.getCropTypeView().getIntMinCropPrice().longValue() > 0.0) {

                        profitGained += farmOutputDetailsForFieldView.getProfitAsDouble();

                    }

                }

            }


            graphDataJsonObject.put(axis + count, profitGained);
            graphDataJsonObject.put("value" + count, entry.getKey().getStrategyName());

            count++;
        }

        return graphDataJsonObject;

    }

    JSONArray getVarianceGraphDetails(FarmInfoView farmInfoView) {
        JSONArray jsonArray = new JSONArray();

        Set<FarmCustomStrategyView> farmCustomStrategyViewSet = this.strategyDetailsForFarm.keySet();

        for (FarmCustomStrategyView farmCustomStrategyView : farmCustomStrategyViewSet) {


            Double averageProfit = 0.0;
            Double minimumProfit = 0.0;
            Double maximumProfit = 0.0;
            for (int strategyId : this.strategyIdArray) {

                if (strategyId == farmCustomStrategyView.getId()) {

                    JSONObject strategyDetails = this.strategyDetailsForFarm.get(farmCustomStrategyView);

                    Double potentialProfit = Double.parseDouble(AgricultureStandardUtils.removeAllCommas(strategyDetails.get("potentialProfit").toString()));
                    JSONObject jsonObject = new JSONObject();

                    if (Objects.equals(farmCustomStrategyView.getFarmCustomStrategy().getFarmInfo().getStrategy(), PlanByStrategy.PLAN_BY_ACRES)) {

                        List<FarmOutputDetailsView> farmOutputDetailsViewList = (List<FarmOutputDetailsView>) strategyDetails.get("farmOutputDetails");
                        for (FarmOutputDetailsView farmOutputDetailsView : farmOutputDetailsViewList) {

                            CropTypeView cropTypeView = farmOutputDetailsView.getCropTypeView();

                            Double minCropYield = Double.parseDouble(AgricultureStandardUtils.removeAllCommas(!cropTypeView.getIntMinCropYield().equalsIgnoreCase("0") ? cropTypeView.getIntMinCropYield() : cropTypeView.getIntExpCropYield()));
                            Double maxCropYield = Double.parseDouble(AgricultureStandardUtils.removeAllCommas(!cropTypeView.getIntMaxCropYield().equalsIgnoreCase("0") ? cropTypeView.getIntMaxCropYield() : cropTypeView.getIntExpCropYield()));
                            Double minCropPrice = cropTypeView.getIntMinCropPrice().doubleValue() != 0.0 ? cropTypeView.getIntMinCropPrice().doubleValue() : cropTypeView.getIntExpCropPrice().doubleValue();
                            Double maxCropPrice = cropTypeView.getIntMaxCropPrice().doubleValue() != 0.0 ? cropTypeView.getIntMaxCropPrice().doubleValue() : cropTypeView.getIntExpCropPrice().doubleValue();

                            averageProfit += getVarianceProfit(minCropYield, maxCropYield, minCropPrice, maxCropPrice,
                                    farmOutputDetailsView.getUsedAcresDouble(), cropTypeView.getCalculatedVariableProductionCost().doubleValue());

                            minimumProfit += farmOutputCalculationService.calculateProfit(minCropYield, minCropPrice,
                                    farmOutputDetailsView.getUsedAcresDouble(), cropTypeView.getCalculatedVariableProductionCost().doubleValue());

                            maximumProfit += farmOutputCalculationService.calculateProfit(maxCropYield, maxCropPrice,
                                    farmOutputDetailsView.getUsedAcresDouble(), cropTypeView.getCalculatedVariableProductionCost().doubleValue());

                        }
                    } else if (Objects.equals(farmCustomStrategyView.getFarmCustomStrategy().getFarmInfo().getStrategy(), PlanByStrategy.PLAN_BY_FIELDS)) {
                        List<FarmOutputDetailsForFieldView> farmOutputDetailsForFieldViewList = (List<FarmOutputDetailsForFieldView>) strategyDetails.get("farmOutputDetails");
                        for (FarmOutputDetailsForFieldView farmOutputDetailsForFieldView : farmOutputDetailsForFieldViewList) {

                            CropTypeView cropTypeView = farmOutputDetailsForFieldView.getCropTypeView();

                            Double minCropYield = Double.parseDouble(AgricultureStandardUtils.removeAllCommas(!cropTypeView.getIntMinCropYield().equalsIgnoreCase("0") ? cropTypeView.getIntMinCropYield() : cropTypeView.getIntExpCropYield()));
                            Double maxCropYield = Double.parseDouble(AgricultureStandardUtils.removeAllCommas(!cropTypeView.getIntMaxCropYield().equalsIgnoreCase("0") ? cropTypeView.getIntMaxCropYield() : cropTypeView.getIntExpCropYield()));
                            Double minCropPrice = cropTypeView.getIntMinCropPrice().doubleValue() != 0.0 ? cropTypeView.getIntMinCropPrice().doubleValue() : cropTypeView.getIntExpCropPrice().doubleValue();
                            Double maxCropPrice = cropTypeView.getIntMaxCropPrice().doubleValue() != 0.0 ? cropTypeView.getIntMaxCropPrice().doubleValue() : cropTypeView.getIntExpCropPrice().doubleValue();

                            averageProfit += getVarianceProfit(minCropYield, maxCropYield, minCropPrice, maxCropPrice,
                                    farmOutputDetailsForFieldView.getUsedAcresDouble(), cropTypeView.getCalculatedVariableProductionCost().doubleValue());

                            minimumProfit += farmOutputCalculationService.calculateProfit(minCropYield, minCropPrice,
                                    farmOutputDetailsForFieldView.getUsedAcresDouble(), cropTypeView.getCalculatedVariableProductionCost().doubleValue());

                            maximumProfit += farmOutputCalculationService.calculateProfit(maxCropYield, maxCropPrice,
                                    farmOutputDetailsForFieldView.getUsedAcresDouble(), cropTypeView.getCalculatedVariableProductionCost().doubleValue());

                        }
                    }

                    minimumProfit = minimumProfit == 0.0 ? potentialProfit : minimumProfit;
                    maximumProfit = maximumProfit == 0.0 ? potentialProfit : maximumProfit;

                    jsonObject.put("strategyName", farmCustomStrategyView.getStrategyName());
//                        jsonObject.put("Average Profit", averageProfit);
//                        jsonObject.put("Minimum Profit", minimumProfit == 0 ? AgricultureStandardUtils.doubleUptoSingleDecimalPoint(potentialProfit) : AgricultureStandardUtils.doubleUptoSingleDecimalPoint(minimumProfit));
//                        jsonObject.put("Maximum Profit", maximumProfit == 0 ? AgricultureStandardUtils.doubleUptoSingleDecimalPoint(potentialProfit) : AgricultureStandardUtils.doubleUptoSingleDecimalPoint(maximumProfit));

                    jsonObject.put("Average Profit", AgricultureStandardUtils.doubleUptoSingleDecimalPoint(averageProfit));
                    jsonObject.put("Minimum Profit", AgricultureStandardUtils.doubleUptoSingleDecimalPoint(minimumProfit));
                    jsonObject.put("Maximum Profit", AgricultureStandardUtils.doubleUptoSingleDecimalPoint(maximumProfit));

                    jsonArray.add(jsonObject);
                }


            }


        }
//        } else if (Objects.equals(farmInfoView.getStrategy(), PlanByStrategy.PLAN_BY_FIELDS)) {
//            Set<Map.Entry<FarmCustomStrategyView, JSONObject>> entries = this.strategyDetailsForFarm.entrySet();
//            for (Map.Entry<FarmCustomStrategyView, JSONObject> entry : entries) {
//                FarmCustomStrategyView farmCustomStrategyView = entry.getKey();
//                Double averageProfit = 0.0;
//                Double minimumProfit = 0.0;
//                Double maximumProfit = 0.0;
//                for (int strategyId : this.strategyIdArray) {
//
//                    if(strategyId == farmCustomStrategyView.getId()){
//                        JSONObject strategyDetails = entry.getValue();
//
//                        Double potentialProfit = Double.parseDouble(AgricultureStandardUtils.removeAllCommas(strategyDetails.get("potentialProfit").toString()));
//                        JSONObject jsonObject = new JSONObject();
//
//                        List<FarmOutputDetailsForFieldView> farmOutputDetailsForFieldViewList = (List<FarmOutputDetailsForFieldView>)strategyDetails.get("farmOutputDetails");
//                        for (FarmOutputDetailsForFieldView farmOutputDetailsForFieldView : farmOutputDetailsForFieldViewList) {
//
//                            CropTypeView cropTypeView = farmOutputDetailsForFieldView.getCropTypeView();
//
//                            Double minCropYield = Double.parseDouble(!cropTypeView.getIntMinCropYield().equalsIgnoreCase("0") ? cropTypeView.getIntMinCropYield() : cropTypeView.getIntExpCropYield());
//                            Double maxCropYield = Double.parseDouble(!cropTypeView.getIntMaxCropYield().equalsIgnoreCase("0") ? cropTypeView.getIntMaxCropYield() : cropTypeView.getIntExpCropYield());
//                            Double minCropPrice = cropTypeView.getIntMinCropPrice().doubleValue() != 0.0 ? cropTypeView.getIntMinCropPrice().doubleValue() : cropTypeView.getIntExpCropPrice().doubleValue();
//                            Double maxCropPrice = cropTypeView.getIntMaxCropPrice().doubleValue() != 0.0 ? cropTypeView.getIntMaxCropPrice().doubleValue() : cropTypeView.getIntExpCropPrice().doubleValue();
//
//                            averageProfit += getVarianceProfit(minCropYield, maxCropYield, minCropPrice, maxCropPrice,
//                                    farmOutputDetailsForFieldView.getUsedAcresDouble(), cropTypeView.getCalculatedVariableProductionCost().doubleValue());
//
//                            minimumProfit += farmOutputCalculationService.calculateProfit(minCropYield, minCropPrice,
//                                    farmOutputDetailsForFieldView.getUsedAcresDouble(),  cropTypeView.getCalculatedVariableProductionCost().doubleValue());
//
//                            maximumProfit += farmOutputCalculationService.calculateProfit(maxCropYield, maxCropPrice,
//                                    farmOutputDetailsForFieldView.getUsedAcresDouble(), cropTypeView.getCalculatedVariableProductionCost().doubleValue());
//
//
////                            minimumProfit += farmOutputDetailsForFieldView.getMinimumProfit();
////                            maximumProfit += farmOutputDetailsForFieldView.getMaximumProfit();
//                        }
//
//                        minimumProfit = minimumProfit == 0.0 ? potentialProfit : minimumProfit;
//                        maximumProfit = maximumProfit == 0.0 ? potentialProfit : maximumProfit;
//
//                        jsonObject.put("strategyName", farmCustomStrategyView.getStrategyName());
////                        jsonObject.put("Average Profit", averageProfit);
////                        jsonObject.put("Minimum Profit", minimumProfit == 0 ? AgricultureStandardUtils.doubleUptoSingleDecimalPoint(potentialProfit) : AgricultureStandardUtils.doubleUptoSingleDecimalPoint(minimumProfit));
////                        jsonObject.put("Maximum Profit", maximumProfit == 0 ? AgricultureStandardUtils.doubleUptoSingleDecimalPoint(potentialProfit) : AgricultureStandardUtils.doubleUptoSingleDecimalPoint(maximumProfit));
//
//                        jsonObject.put("Average Profit", AgricultureStandardUtils.doubleUptoSingleDecimalPoint(averageProfit));
//                        jsonObject.put("Minimum Profit", AgricultureStandardUtils.doubleUptoSingleDecimalPoint(minimumProfit));
//                        jsonObject.put("Maximum Profit", AgricultureStandardUtils.doubleUptoSingleDecimalPoint(maximumProfit));
//
//                        jsonArray.add(jsonObject);
//                    }
//
//
//                }
//            }
//        }

        return jsonArray;
    }


    JSONArray getVarianceGraphCustomizedDetails(FarmInfoView farmInfoView, int[] cropPriceSelection, int[] cropYieldSelection,
                                                int[] cropProductionCostSelection, JSONObject rangeValuesObject) {
        JSONArray jsonArray = new JSONArray();

        PlantingProfitLogger.info("Calculating variance profit for farm named : " + farmInfoView.getFarmName());

        Set<FarmCustomStrategyView> farmCustomStrategyViewSet = this.strategyDetailsForFarm.keySet();
        for (FarmCustomStrategyView farmCustomStrategyView : farmCustomStrategyViewSet) {

            Double minimumProfit = 0.0;
            Double maximumProfit = 0.0;
            Double potentialProfit = 0.0;

            for (int strategyId : this.strategyIdArray) {

                if (strategyId == farmCustomStrategyView.getId()) {
                    JSONObject strategyDetails = this.strategyDetailsForFarm.get(farmCustomStrategyView);

//                        int potentialProfit = Integer.parseInt(AgricultureStandardUtils.removeAllCommas(strategyDetails.get("potentialProfit").toString()));
                    JSONObject jsonObject = new JSONObject();
                    if (Objects.equals(farmCustomStrategyView.getFarmCustomStrategy().getFarmInfo().getStrategy(), PlanByStrategy.PLAN_BY_ACRES)) {
                        List<FarmOutputDetailsView> farmOutputDetailsViewList = (List<FarmOutputDetailsView>) strategyDetails.get("farmOutputDetails");
                        for (FarmOutputDetailsView farmOutputDetailsView : farmOutputDetailsViewList) {
                            CropTypeView cropTypeView = farmOutputDetailsView.getCropTypeView();
                            JSONObject rangeDetails = (JSONObject) rangeValuesObject.get(cropTypeView.getId().toString());

                            /*potentialProfit += getVariancePotentialProfit(cropPriceSelection, cropYieldSelection, cropProductionCostSelection, cropTypeView, rangeDetails, farmOutputDetailsView.getUsedAcresAsDouble());
                            minimumProfit += farmOutputDetailsView.getMinimumProfit();
                            maximumProfit += farmOutputDetails View.getMaximumProfit();*/

                            Map<String, Double> variancePotentialProfit = getVariancePotentialProfit(cropPriceSelection, cropYieldSelection, cropProductionCostSelection, cropTypeView, rangeDetails, farmOutputDetailsView.getUsedAcresAsDouble());
                            potentialProfit += variancePotentialProfit.get("avgProfit");
                            minimumProfit += variancePotentialProfit.get("minProfit");
                            maximumProfit += variancePotentialProfit.get("maxProfit");


                        }
                    } else if (Objects.equals(farmCustomStrategyView.getFarmCustomStrategy().getFarmInfo().getStrategy(), PlanByStrategy.PLAN_BY_FIELDS)) {
                        List<FarmOutputDetailsForFieldView> farmOutputDetailsForFieldViewList = (List<FarmOutputDetailsForFieldView>) strategyDetails.get("farmOutputDetails");
                        for (FarmOutputDetailsForFieldView farmOutputDetailsForFieldView : farmOutputDetailsForFieldViewList) {
                            CropTypeView cropTypeView = farmOutputDetailsForFieldView.getCropTypeView();
                            JSONObject rangeDetails = (JSONObject) rangeValuesObject.get((cropTypeView.getId()).toString());

                            /*potentialProfit += getVariancePotentialProfit(cropPriceSelection, cropYieldSelection, cropProductionCostSelection, cropTypeView, rangeDetails, farmOutputDetailsForFieldView.getUsedAcresAsDouble());
                            minimumProfit += farmOutputDetailsForFieldView.getMinimumProfit();
                            maximumProfit += farmOutputDetailsForFieldView.getMaximumProfit();*/

                            Map<String, Double> variancePotentialProfit = getVariancePotentialProfit(cropPriceSelection, cropYieldSelection, cropProductionCostSelection, cropTypeView, rangeDetails, farmOutputDetailsForFieldView.getUsedAcresAsDouble());
                            potentialProfit += variancePotentialProfit.get("avgProfit");
                            minimumProfit += variancePotentialProfit.get("minProfit");
                            maximumProfit += variancePotentialProfit.get("maxProfit");

                        }
                    }
                    minimumProfit = minimumProfit == 0.0 ? potentialProfit : minimumProfit;
                    maximumProfit = maximumProfit == 0.0 ? potentialProfit : maximumProfit;

                    jsonObject.put("strategyName", farmCustomStrategyView.getStrategyName());
                    jsonObject.put("Average Profit", AgricultureStandardUtils.doubleUptoSingleDecimalPoint(potentialProfit));
                    jsonObject.put("Minimum Profit", AgricultureStandardUtils.doubleUptoSingleDecimalPoint(minimumProfit));
                    jsonObject.put("Maximum Profit", AgricultureStandardUtils.doubleUptoSingleDecimalPoint(maximumProfit));

                    jsonArray.add(jsonObject);
                }


            }


        }
//        } else if (Objects.equals(farmInfoView.getStrategy(), PlanByStrategy.PLAN_BY_FIELDS)) {
//            Set<Map.Entry<FarmCustomStrategyView, JSONObject>> entries = this.strategyDetailsForFarm.entrySet();
//            for (Map.Entry<FarmCustomStrategyView, JSONObject> entry : entries) {
//                FarmCustomStrategyView farmCustomStrategyView = entry.getKey();
//                Double minimumProfit = 0.0;
//                Double maximumProfit = 0.0;
//                Double potentialProfit = 0.0;
//
//                for (int strategyId : this.strategyIdArray) {
//
//                    if (strategyId == farmCustomStrategyView.getId()) {
//                        JSONObject strategyDetails = entry.getValue();
//
////                        int potentialProfit = Integer.parseInt(AgricultureStandardUtils.removeAllCommas(strategyDetails.get("potentialProfit").toString()));
//                        JSONObject jsonObject = new JSONObject();
//
//                        List<FarmOutputDetailsForFieldView> farmOutputDetailsForFieldViewList = (List<FarmOutputDetailsForFieldView>) strategyDetails.get("farmOutputDetails");
//                        for (FarmOutputDetailsForFieldView farmOutputDetailsForFieldView : farmOutputDetailsForFieldViewList) {
//                            CropTypeView cropTypeView = farmOutputDetailsForFieldView.getCropTypeView();
//                            JSONObject rangeDetails = (JSONObject) rangeValuesObject.get((cropTypeView.getId()).toString());
//
//                            potentialProfit += getVariancePotentialProfit(cropPriceSelection, cropYieldSelection, cropProductionCostSelection, cropTypeView, rangeDetails, farmOutputDetailsForFieldView.getUsedAcresAsDouble());
//                            minimumProfit += farmOutputDetailsForFieldView.getMinimumProfit();
//                            maximumProfit += farmOutputDetailsForFieldView.getMaximumProfit();
//
//                            Map<String, Double> variancePotentialProfit = getVariancePotentialProfit(cropPriceSelection, cropYieldSelection, cropProductionCostSelection, cropTypeView, rangeDetails, farmOutputDetailsForFieldView.getUsedAcresAsDouble());
//                            potentialProfit += variancePotentialProfit.get("avgProfit");
//                            minimumProfit += variancePotentialProfit.get("minProfit");
//                            maximumProfit += variancePotentialProfit.get("maxProfit");
//
//                        }
//
//
//                        jsonObject.put("strategyName", farmCustomStrategyView.getStrategyName());
//                        jsonObject.put("Average Profit", AgricultureStandardUtils.doubleUptoSingleDecimalPoint(potentialProfit));
//                        jsonObject.put("Minimum Profit", AgricultureStandardUtils.doubleUptoSingleDecimalPoint(minimumProfit == 0 ? potentialProfit.intValue() : minimumProfit));
//                        jsonObject.put("Maximum Profit", AgricultureStandardUtils.doubleUptoSingleDecimalPoint(maximumProfit == 0 ? potentialProfit.intValue() : maximumProfit));
//
//                        jsonArray.add(jsonObject);
//                    }
//
//
//                }
//            }
//        }

        return jsonArray;
    }

    private Map<String, Double> getVariancePotentialProfit(int[] cropPriceSelection, int[] cropYieldSelection, int[] cropProductionCostSelection,
                                                           CropTypeView cropTypeView, JSONObject rangeDetails, Double usedAcres) {

        Map<String, Double> varianceOutputDetails = new HashMap<>();

        Double minYield = 0.0, maxYield = 0.0, minPrice = 0.0, maxPrice = 0.0, productionCost = 0.0;
        boolean yieldFlag = true, priceFlag = true;
        if (rangeDetails != null) {

            if (cropPriceSelection != null) {
                for (int cropId : cropPriceSelection) {
                    if (cropTypeView.getId().equals(cropId)) {
                        priceFlag = false;
//                        if ((!rangeDetails.get("minPrice").toString().equalsIgnoreCase("0") && !rangeDetails.get("minPrice").toString().equalsIgnoreCase(""))
//                                && (!rangeDetails.get("maxPrice").toString().equalsIgnoreCase("0") && !rangeDetails.get("maxPrice").toString().equalsIgnoreCase(""))) {
//                            minPrice = Double.parseDouble(AgricultureStandardUtils.removeAllCommas(rangeDetails.get("minPrice").toString()));
//                            maxPrice = Double.parseDouble(AgricultureStandardUtils.removeAllCommas(rangeDetails.get("maxPrice").toString()));
//                        } else {
                        minPrice = cropTypeView.getIntMinCropPrice().doubleValue();
                        maxPrice = cropTypeView.getIntMaxCropPrice().doubleValue();
//                        }
                    }
                }
            }
            if (cropYieldSelection != null) {
                for (int cropId : cropYieldSelection) {
                    if (cropTypeView.getId().equals(cropId)) {
                        yieldFlag = false;
//                        if ((!rangeDetails.get("minYield").toString().equalsIgnoreCase("0") && !rangeDetails.get("minYield").toString().equalsIgnoreCase(""))
//                                && (!rangeDetails.get("maxYield").toString().equalsIgnoreCase("0") && !rangeDetails.get("maxYield").toString().equalsIgnoreCase(""))) {
//                            minYield = Double.parseDouble(AgricultureStandardUtils.removeAllCommas(rangeDetails.get("minYield").toString()));
//                            maxYield = Double.parseDouble(AgricultureStandardUtils.removeAllCommas(rangeDetails.get("maxYield").toString()));
//                        } else {
                        minYield = Double.parseDouble(AgricultureStandardUtils.removeAllCommas(cropTypeView.getIntMinCropYield()));
                        maxYield = Double.parseDouble(AgricultureStandardUtils.removeAllCommas(cropTypeView.getIntMaxCropYield()));
//                        }

                    }
                }
            }
            if (cropProductionCostSelection != null) {
                for (int cropId : cropProductionCostSelection) {
                    if (Objects.equals(cropId, cropTypeView.getId())) {
                        productionCost = cropTypeView.getCalculatedVariableProductionCost().doubleValue();
                    }
                }
            }

            if (priceFlag) {
                if (cropTypeView.getIntMaxCropPrice().doubleValue() == 0.0 && cropTypeView.getIntMinCropPrice().doubleValue() == 0.0) {
                    minPrice = cropTypeView.getIntExpCropPrice().doubleValue();
                    maxPrice = cropTypeView.getIntExpCropPrice().doubleValue();
                } else {
                    minPrice = cropTypeView.getIntMinCropPrice().doubleValue();
                    maxPrice = cropTypeView.getIntMaxCropPrice().doubleValue();
                }

            }

            if (yieldFlag) {
                if (cropTypeView.getIntMaxCropYield().equalsIgnoreCase("0.0") && cropTypeView.getIntMinCropYield().equalsIgnoreCase("0.0")) {
                    minYield = Double.parseDouble(AgricultureStandardUtils.removeAllCommas(cropTypeView.getIntExpCropYield()));
                    maxYield = Double.parseDouble(AgricultureStandardUtils.removeAllCommas(cropTypeView.getIntExpCropYield()));
                } else {
                    minYield = Double.parseDouble(AgricultureStandardUtils.removeAllCommas(cropTypeView.getIntMinCropYield()));
                    maxYield = Double.parseDouble(AgricultureStandardUtils.removeAllCommas(cropTypeView.getIntMaxCropYield()));
                }
//                minYield = Double.parseDouble(rangeDetails.get("minYield").toString().equalsIgnoreCase("") ? "0" : rangeDetails.get("minYield").toString());
//                maxYield = Double.parseDouble(rangeDetails.get("maxYield").toString().equalsIgnoreCase("") ? "0" : rangeDetails.get("maxYield").toString());
            }

        }

        varianceOutputDetails.put("minProfit", farmOutputCalculationService.calculateProfit(minYield, minPrice, usedAcres, productionCost));
        varianceOutputDetails.put("avgProfit", getVarianceProfit(minYield, maxYield, minPrice, maxPrice, usedAcres, productionCost));
        varianceOutputDetails.put("maxProfit", farmOutputCalculationService.calculateProfit(maxYield, maxPrice, usedAcres, productionCost));


        return varianceOutputDetails;
//        return getVarianceProfit(minYield,maxYield, minPrice, maxPrice, usedAcres, productionCost);


    }

    private Double getVarianceProfit(Double minYield, Double maxYield, Double minPrice, Double maxPrice, Double minAcres, Double variableProductionCost) {

        Double profit = 0.0;
        int iterationLimit = 10000;

//        if ((minYield != 0 && maxYield != 0) && (minPrice != 0 && maxPrice != 0)) {
        for (int i = 0; i < iterationLimit; i++) {
            profit += (getRandomNumberDouble(minYield, maxYield) * minAcres * getRandomNumberDouble(minPrice, maxPrice)) - (minAcres * variableProductionCost);
        }
//        }

        profit = profit / iterationLimit;

        return AgricultureStandardUtils.doubleUptoSingleDecimalPoint(profit);

    }

    private Double getRandomNumberDouble(Double minimum, Double maximum) {
        return Math.floor(Math.random() * (maximum - minimum + 1)) + minimum;
    }
}

package com.decipher.agriculture.service.strategy.impl;

import com.decipher.agriculture.data.farm.PlanByStrategy;
import com.decipher.agriculture.service.farmDetails.FarmOutputCalculationService;
import com.decipher.util.AgricultureStandardUtils;
import com.decipher.view.form.farmDetails.CropResourceUsageView;
import com.decipher.view.form.farmDetails.CropTypeView;
import com.decipher.view.form.farmDetails.FarmInfoView;
import com.decipher.view.form.farmDetails.FarmOutputDetailsForFieldView;
import com.decipher.view.form.farmDetails.FarmOutputDetailsView;
import org.json.simple.JSONObject;

import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by abhishek on 17/5/16.
 */
@SuppressWarnings("unchecked")
public class RiskAndConservationMgmtDataBuilder {

    private FarmOutputCalculationService farmOutputCalculationService;

    public RiskAndConservationMgmtDataBuilder() {
    }

    public RiskAndConservationMgmtDataBuilder(FarmOutputCalculationService farmOutputCalculationService) {
        this.farmOutputCalculationService = farmOutputCalculationService;
    }

    public JSONObject getCapitalUsed(JSONObject outputDetails){
        JSONObject jsonObject = new JSONObject();

        List<CropResourceUsageView> resourceUsageViewList = (List<CropResourceUsageView>)outputDetails.get("resourceList");
        String resourceUsed = "";

        for (CropResourceUsageView cropResourceUsageView : resourceUsageViewList) {

            if (cropResourceUsageView.getCropResourceUse().equalsIgnoreCase("capital")){
                resourceUsed = ((HashMap<String, String>)outputDetails.get("cropResourceUsed")).get(cropResourceUsageView.getCropResourceUse());
                break;
            }
        }

        jsonObject.put("name", "capitalUsed");
        jsonObject.put("amount", "$" + resourceUsed);


        return jsonObject;
    }

    public JSONObject getReturnOnLand(FarmInfoView farmInfoView, JSONObject outputDetails){
        JSONObject jsonObject = new JSONObject();
        Double potentialProfit = Double.parseDouble(AgricultureStandardUtils.removeAllCommas(outputDetails.get("potentialProfit").toString()));

        Double totalAcreage = 0.0;

        if(Objects.equals(farmInfoView.getStrategy(), PlanByStrategy.PLAN_BY_ACRES)){

            List<FarmOutputDetailsView> farmOutputDetailsViewList = (List<FarmOutputDetailsView>)outputDetails.get("farmOutputDetails");

            for (FarmOutputDetailsView farmOutputDetailsView : farmOutputDetailsViewList) {

                totalAcreage += farmOutputDetailsView.getUsedAcresDouble();

            }


        } else if(Objects.equals(farmInfoView.getStrategy(), PlanByStrategy.PLAN_BY_FIELDS)){

            Map<String, String> hashMapForAcre = (Map<String, String>) outputDetails.get("hashMapForAcre");

            Set<Map.Entry<String, String>> entries = hashMapForAcre.entrySet();

            for (Map.Entry<String, String> entry : entries) {

                Double acreage = Double.parseDouble(AgricultureStandardUtils.removeAllCommas(entry.getValue().split(" ")[0]));

                totalAcreage += acreage;

            }

        }
        double amount = potentialProfit / totalAcreage;
        jsonObject.put("name", "returnOnLand");
        jsonObject.put("amount", Double.isNaN(amount) ? "0.0" : AgricultureStandardUtils.commaSeparatedUptoOneDecimalForDoublePrice(amount));

        return jsonObject;


    }

    public JSONObject getPpFromSingleProfitCrop(FarmInfoView farmInfoView, JSONObject outputDetails){
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("name", "PP From single crop");
        jsonObject.put("amount", getProfitFromOneOrTwoProfitableCrops(farmInfoView, "one", outputDetails));

        return jsonObject;
    }

    public JSONObject getPpFromTwoProfitCrop(FarmInfoView farmInfoView, JSONObject outputDetails){

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("name", "PP From two crop");
        jsonObject.put("amount", getProfitFromOneOrTwoProfitableCrops(farmInfoView, "two", outputDetails));
        return jsonObject;
    }

    private String getProfitFromOneOrTwoProfitableCrops(FarmInfoView farmInfoView, String key, JSONObject outputDetails){
        List<String> profitList = new ArrayList<>();
        Double potentialProfit = Double.parseDouble(AgricultureStandardUtils.removeAllCommas(outputDetails.get("potentialProfit").toString()));
        if(Objects.equals(farmInfoView.getStrategy(), PlanByStrategy.PLAN_BY_ACRES)){

            List<FarmOutputDetailsView> farmOutputDetailsViewList = (List<FarmOutputDetailsView>)outputDetails.get("farmOutputDetails");

            for (FarmOutputDetailsView farmOutputDetailsView : farmOutputDetailsViewList) {
                profitList.add(farmOutputDetailsView.getProfit());
            }


        } else if(Objects.equals(farmInfoView.getStrategy(), PlanByStrategy.PLAN_BY_FIELDS)) {

            Map<String, String> mapForProfit = (Map<String, String>)outputDetails.get("hashMapForProfit");

            Set<Map.Entry<String, String>> entrySetForProfit = mapForProfit.entrySet();

            for (Map.Entry<String, String> profitEntry : entrySetForProfit) {

                profitList.add(profitEntry.getValue().split(" ")[0]);

            }

        }


        int firstCropProfit = Integer.MIN_VALUE;
        int secondCropProfit = Integer.MIN_VALUE;

        for (String profit : profitList) {
            int profitInInteger =  Integer.parseInt(profit.replaceAll("\\,", ""));
            if (profitInInteger > firstCropProfit) {
                secondCropProfit = firstCropProfit;
                firstCropProfit = profitInInteger;
            } else if (profitInInteger > secondCropProfit)
                secondCropProfit = profitInInteger;
        }

        DecimalFormat decimalFormat = new DecimalFormat("#.0");

        String profit = "";

        if(key.equalsIgnoreCase("one") && potentialProfit != null){

            double singleCropProfitPercentage = (100 * firstCropProfit) / potentialProfit;
            if(Double.isNaN(singleCropProfitPercentage)){
                singleCropProfitPercentage = 0.0;
            }
            profit = decimalFormat.format(singleCropProfitPercentage);
        } else if (key.equalsIgnoreCase("two") && potentialProfit != null) {

            double twoCropProfitPercentage = (100 * (firstCropProfit + secondCropProfit)) / potentialProfit;
            if(Double.isNaN(twoCropProfitPercentage)){
                twoCropProfitPercentage = 0.0;
            }
            profit = decimalFormat.format(twoCropProfitPercentage);
        }

        return profit;

    }

    public JSONObject getPpFromHighRiskCrop(FarmInfoView farmInfoView, JSONObject outputDetails){
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("name", "PP From high risk crop");
        jsonObject.put("amount", getHighRiskDetails(farmInfoView, outputDetails, "income", "risk"));
        return jsonObject;
    }

    public JSONObject getAcreageHighRiskCrop(FarmInfoView farmInfoView, JSONObject outputDetails){
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("name", "Acreage From high crop");
        jsonObject.put("amount", getHighRiskDetails(farmInfoView, outputDetails, "acreage", "risk"));

        return jsonObject;
    }

    public JSONObject getPpFromConservationRiskCrop(FarmInfoView farmInfoView, JSONObject outputDetails){

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("name", "PP From conservation crop");
        jsonObject.put("amount", getHighRiskDetails(farmInfoView, outputDetails, "income", "conservation"));

        return jsonObject;
    }

    public JSONObject getAcreageConservationRiskCrop(FarmInfoView farmInfoView, JSONObject outputDetails){

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("name", "Acreage From conservation crop");
        jsonObject.put("amount", getHighRiskDetails(farmInfoView, outputDetails, "acreage", "conservation"));

        return jsonObject;
    }

    private String getHighRiskDetails(FarmInfoView farmInfoView, JSONObject outputDetails, String key, String cnsrvatnOrRisk){
        DecimalFormat formatter = new DecimalFormat("#.0");
        Double landUnderHighRisk = 0.0, incomeUnderHighRisk = 0.0;

        if(Objects.equals(farmInfoView.getStrategy(), PlanByStrategy.PLAN_BY_ACRES)){

            List<FarmOutputDetailsView> farmOutputDetailsViewList = (List<FarmOutputDetailsView>) outputDetails.get("farmOutputDetails");

            for (FarmOutputDetailsView farmOutputDetailsView : farmOutputDetailsViewList) {

                if(cnsrvatnOrRisk.equalsIgnoreCase("conservation")){
                    if (farmOutputDetailsView.getCropTypeView().getConservation_Crop().equalsIgnoreCase("true")) {
                        landUnderHighRisk += farmOutputDetailsView.getUsedAcresPercentage();
                        incomeUnderHighRisk += Double.parseDouble(formatter.format(farmOutputDetailsView.getUsedCapitalPercentage()));
                    }
                } else if(cnsrvatnOrRisk.equalsIgnoreCase("risk")){
                    if (farmOutputDetailsView.getCropTypeView().getHiRiskCrop().equalsIgnoreCase("true")) {
                        landUnderHighRisk += farmOutputDetailsView.getUsedAcresPercentage();
                        incomeUnderHighRisk += Double.parseDouble(formatter.format(farmOutputDetailsView.getUsedCapitalPercentage()));
                    }
                }
            }


        } else if(Objects.equals(farmInfoView.getStrategy(), PlanByStrategy.PLAN_BY_FIELDS)){

            List<CropTypeView> cropTypeViewList = (List<CropTypeView>) outputDetails.get("cropTypeView");

            for (CropTypeView cropTypeView : cropTypeViewList) {

                if(cnsrvatnOrRisk.equalsIgnoreCase("conservation")){
                    if (cropTypeView.getSelected() && cropTypeView.getHiRiskCrop().equalsIgnoreCase("true")) {
                        String land = ((Map<String, String>)outputDetails.get("hashMapForAcre")).get(cropTypeView.getCropName());
                        landUnderHighRisk += Double.parseDouble(land.substring(land.indexOf('(') + 1, land.indexOf('%')).replaceAll("\\,", ""));
                        String income = ((Map<String, String>)outputDetails.get("hashMapForProfit")).get(cropTypeView.getCropName());
                        incomeUnderHighRisk += Double.parseDouble(income.substring(income.indexOf('(') + 1, income.indexOf('%')).replaceAll("\\,", ""));
                    }
                } else if(cnsrvatnOrRisk.equalsIgnoreCase("risk")){
                    if (cropTypeView.getSelected() && cropTypeView.getConservation_Crop().equalsIgnoreCase("true")) {
                        String land = ((Map<String, String>)outputDetails.get("hashMapForAcre")).get(cropTypeView.getCropName());
                        landUnderHighRisk += Double.parseDouble(land.substring(land.indexOf('(') + 1, land.indexOf('%')).replaceAll("\\,", ""));
                        String income = ((Map<String, String>)outputDetails.get("hashMapForProfit")).get(cropTypeView.getCropName());
                        incomeUnderHighRisk += Double.parseDouble(income.substring(income.indexOf('(') + 1, income.indexOf('%')).replaceAll("\\,", ""));
                    }
                }


            }

        }

        if(key.equalsIgnoreCase("income")){
//            return incomeUnderHighRisk == 0.0 ? "0.0" : formatter.format(incomeUnderHighRisk);
            return incomeUnderHighRisk == 0.0 ? "0.0" : AgricultureStandardUtils.commaSeparaterForPriceWithOneDecimal(incomeUnderHighRisk.toString());
        } else if(key.equalsIgnoreCase("acreage")){
//            return landUnderHighRisk == 0.0 ? "0.0" : formatter.format(landUnderHighRisk);
            return landUnderHighRisk == 0.0 ? "0.0" : AgricultureStandardUtils.commaSeparaterForPriceWithOneDecimal(landUnderHighRisk.toString());
        }


        return formatter.format(incomeUnderHighRisk);
    }

    public JSONObject getForwardSoldProfit(FarmInfoView farmInfoView, JSONObject outputDetails){
        JSONObject jsonObject = new JSONObject();

        /*Double profitGained = 0.0;

        if(Objects.equals(farmInfoView.getStrategy(), PlanByStrategy.PLAN_BY_ACRES)){

            List<FarmOutputDetailsView> farmOutputDetailsViewList = (List<FarmOutputDetailsView>) outputDetails.get("farmOutputDetails");

            for (FarmOutputDetailsView farmOutputDetailsView : farmOutputDetailsViewList) {
                if (farmOutputDetailsView.getCropTypeView().getFirmchecked().equalsIgnoreCase("true") || farmOutputDetailsView.getCropTypeView().getProposedchecked()) {
                    profitGained += farmOutputDetailsView.getProfitAsDouble();
                }
            }

        } else if(Objects.equals(farmInfoView.getStrategy(), PlanByStrategy.PLAN_BY_FIELDS)){

            Map<String, String> mapForProfit = (Map<String, String>)outputDetails.get("hashMapForProfit");
            List<CropTypeView> cropTypeViewList = (List<CropTypeView>)outputDetails.get("cropTypeView");

            for (CropTypeView cropTypeView : cropTypeViewList) {

                if(cropTypeView.getSelected() && (cropTypeView.getProposedchecked() || cropTypeView.getFirmchecked().equalsIgnoreCase("true"))){

                    profitGained += Double.parseDouble(AgricultureStandardUtils.removeAllCommas(mapForProfit.get(cropTypeView.getCropName()).toString().split(" ")[0]));

                }

            }

        }

        Double totalProfit = Double.parseDouble(AgricultureStandardUtils.removeAllCommas(outputDetails.get("potentialProfit").toString()));

        profitGained = (profitGained *100) / totalProfit;*/


        jsonObject.put("name", "ForwardSoldProfit");
//        jsonObject.put("amount", AgricultureStandardUtils.doubleUptoSingleDecimalPoint(profitGained));
        Map<String, String> mapDifferentValues = (Map<String, String>) outputDetails.get("mapDifferentValues");
        jsonObject.put("amount", mapDifferentValues.get("usedForwardAcresP"));

        return jsonObject;

    }

    public JSONObject getProfitFromMinPriceAndYield(FarmInfoView farmInfoView, JSONObject outputDetails){
        JSONObject jsonObject = new JSONObject();

        Double profitGained = 0.0;

        if(Objects.equals(farmInfoView.getStrategy(), PlanByStrategy.PLAN_BY_ACRES)){
            List<FarmOutputDetailsView> farmOutputDetailsViewList = (List<FarmOutputDetailsView>)outputDetails.get("farmOutputDetails");
            for (FarmOutputDetailsView farmOutputDetailsView : farmOutputDetailsViewList) {
                CropTypeView cropTypeView = farmOutputDetailsView.getCropTypeView();
                if (farmOutputDetailsView.getUsedAcresAsDouble() > 0.0
                        && !cropTypeView.getIntMinCropYield().equals("0")
                        && farmOutputDetailsView.getCropTypeView().getIntMinCropPrice().longValue() > 0.0) {

                    profitGained += farmOutputCalculationService.calculateProfit(Double.parseDouble(AgricultureStandardUtils.removeAllCommas(cropTypeView.getIntMinCropYield())),
                            cropTypeView.getIntMinCropPrice().doubleValue(),
                            farmOutputDetailsView.getUsedAcresDouble(),
                            cropTypeView.getCalculatedVariableProductionCost().doubleValue());

//                    profitGained += farmOutputDetailsView.getProfitAsDouble();

                }

            }


        } else if(Objects.equals(farmInfoView.getStrategy(), PlanByStrategy.PLAN_BY_FIELDS)){
            List<FarmOutputDetailsForFieldView> farmOutputDetailsViewList = (List<FarmOutputDetailsForFieldView>)outputDetails.get("farmOutputDetails");
            for (FarmOutputDetailsForFieldView farmOutputDetailsView : farmOutputDetailsViewList) {
                CropTypeView cropTypeView = farmOutputDetailsView.getCropTypeView();
                if (farmOutputDetailsView.getUsedAcresAsDouble() > 0.0
                        && !cropTypeView.getIntMinCropYield().equals("0")
                        && cropTypeView.getIntMinCropPrice().longValue() > 0.0) {

                    profitGained += farmOutputCalculationService.calculateProfit(Double.parseDouble(AgricultureStandardUtils.removeAllCommas(cropTypeView.getIntMinCropYield())),
                                    cropTypeView.getIntMinCropPrice().doubleValue(),
                                    farmOutputDetailsView.getUsedAcresDouble(),
                                    cropTypeView.getCalculatedVariableProductionCost().doubleValue());

//                    profitGained += farmOutputDetailsView.getProfitAsDouble();

                }

            }

        }


        jsonObject.put("name", "ProfitFromMinPriceAndYield");
        jsonObject.put("amount", (AgricultureStandardUtils.commaSeparaterForDoublePrice(profitGained).equalsIgnoreCase(".00") ? "N/A" : "$" + AgricultureStandardUtils.commaSeparaterForInteger(profitGained)));

        return jsonObject;
    }


}

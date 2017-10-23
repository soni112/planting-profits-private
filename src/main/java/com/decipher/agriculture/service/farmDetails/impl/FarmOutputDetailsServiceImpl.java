package com.decipher.agriculture.service.farmDetails.impl;

import com.decipher.AppConstants;
import com.decipher.agriculture.data.farm.CropType;
import com.decipher.agriculture.data.farm.PlanByStrategy;
import com.decipher.agriculture.service.farmDetails.FarmOutputDetailsService;
import com.decipher.util.AgricultureStandardUtils;
import com.decipher.view.form.farmDetails.CropResourceUsageView;
import com.decipher.view.form.farmDetails.CropTypeView;
import com.decipher.view.form.farmDetails.CropsGroupView;
import com.decipher.view.form.farmDetails.FarmInfoView;
import com.decipher.view.form.farmDetails.FarmOutputDetailsForFieldView;
import com.decipher.view.form.farmDetails.FarmOutputDetailsView;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Created on 3/2/17 2:38 PM by Abhishek Samuel
 * Software Engineer
 * abhisheks@decipherzone.com
 * Decipher Zone Softwares LLP
 * www.decipherzone.com
 */
@Service
@SuppressWarnings("unchecked")
public class FarmOutputDetailsServiceImpl implements FarmOutputDetailsService {

    private static final String CROP_NAME = "cropName";
    private static final String ACREAGE = "acreage";
    private static final String PROFIT = "profit";
    private static final String RATIO = "ratio";
    private static final String INDEX = "index";
    private static final String RATING = "rating";

    private static final String YES = "Yes";
    private static final String NO = "No";

    private static final String RESOURCE_STATUS = "status";
    private static final String RESOURCE_NAME = "resourceName";
    private static final String RESOURCE_TOTAL_AVAILABLE = "totalAvailable";
    private static final String RESOURCE_USED = "used";
    private static final String RESOURCE_UNUSED = "unused";
    private static final String RESOURCE_IMPACTING_PROFIT = "impactingProfit";

    private static final String MIN_LIMIT = "minLimit";
    private static final String MAX_LIMIT = "maxLimit";
    private static final String IMPACTING_INCOME = "impactingIncome";
    private static final String INC_DEC_INCOME = "incDecIncome";
    private static final String MESSAGE = "message";


    @Override
    public JSONArray buildForwardSalesContent(JSONObject outputDetails) {
        JSONArray jsonArray = new JSONArray();
        FarmInfoView farmInfoView = (FarmInfoView) outputDetails.get("farmInfoView");
        List<CropTypeView> cropTypeViewList = (List<CropTypeView>) outputDetails.get("cropTypeView");

        for (CropTypeView cropTypeView : cropTypeViewList) {
            if (cropTypeView.getSelected()) {
                JSONObject jsonObject = new JSONObject();
                boolean flag = false;
                if (Objects.equals(farmInfoView.getStrategy(), PlanByStrategy.PLAN_BY_ACRES)) {

                    List<FarmOutputDetailsView> farmOutputDetailsViewList = (List<FarmOutputDetailsView>) outputDetails.get("farmOutputDetails");
                    for (FarmOutputDetailsView farmOutputDetailsView : farmOutputDetailsViewList) {

                        if (Objects.equals(cropTypeView.getId(), farmOutputDetailsView.getCropTypeView().getId())) {
                            if (farmOutputDetailsView.getForProposed() && !farmOutputDetailsView.getUsedAcres().equalsIgnoreCase("0")) {
                                flag = true;
                            }
                        }
                    }

                } else if (Objects.equals(farmInfoView.getStrategy(), PlanByStrategy.PLAN_BY_FIELDS)) {
                    List<FarmOutputDetailsForFieldView> farmOutputDetailsForFieldViewList = (List<FarmOutputDetailsForFieldView>) outputDetails.get("farmOutputDetails");

                    for (FarmOutputDetailsForFieldView farmOutputDetailsForFieldView : farmOutputDetailsForFieldViewList) {

                        if (Objects.equals(cropTypeView.getId(), farmOutputDetailsForFieldView.getCropTypeView().getId())) {
                            if (farmOutputDetailsForFieldView.isForProposed() && !farmOutputDetailsForFieldView.getUsedAcres().equalsIgnoreCase("0")) {
                                flag = true;
                            }
                        }
                    }

                }


                jsonObject.put("cropName", cropTypeView.getCropName());

                if (cropTypeView.getProposedchecked() || cropTypeView.getFirmchecked().equalsIgnoreCase("true")) {
                    jsonObject.put("forwardSalesAmount", cropTypeView.getAcresStr() + " acres at " + cropTypeView.getForwardPrice() + "/" + cropTypeView.getCropUOM());
                } else {
                    jsonObject.put("forwardSalesAmount", "");
                }

                if (cropTypeView.getFirmchecked().equalsIgnoreCase("true")) {
                    jsonObject.put("firmProposedCheck", "Firm");
                } else if (cropTypeView.getProposedchecked()) {
                    jsonObject.put("firmProposedCheck", "Proposed");
                } else {
                    jsonObject.put("firmProposedCheck", "N/A");
                }

                if (cropTypeView.getFirmchecked().equalsIgnoreCase("true")) {
                    jsonObject.put("status", YES);
                } else if (cropTypeView.getProposedchecked() && flag) {
                    jsonObject.put("status", YES);
                } else {
                    jsonObject.put("status", NO);
                }

                jsonArray.add(jsonObject);
            }
        }

        return jsonArray;
    }

    @Override
    public JSONArray buildCropLimitContent(JSONObject outputDetails) {
        JSONArray jsonArray = new JSONArray();

        List<CropTypeView> cropTypeViewList = (List<CropTypeView>) outputDetails.get("cropTypeView");
        FarmInfoView farmInfoView = (FarmInfoView) outputDetails.get("farmInfoView");
        for (CropTypeView cropTypeView : cropTypeViewList) {
            if (cropTypeView.getSelected()) {
                JSONObject jsonObject = getCropLimit(farmInfoView, cropTypeView.getMinimumAcres(), cropTypeView.getMaximumAcres(), cropTypeView, null, outputDetails);
                jsonObject.put("cropName", cropTypeView.getCropName());
                jsonObject.put("acreagePlanted", getCropAcreage(cropTypeView, outputDetails));
                jsonArray.add(jsonObject);

                if (cropTypeView.getFirmchecked().equalsIgnoreCase("true")){

                    JSONObject jsonObjectForFirm = getCropLimit(farmInfoView, AgricultureStandardUtils.withoutDecimalAndComma(cropTypeView.getForwardAcres()), "", cropTypeView, null, outputDetails);
                    jsonObjectForFirm.put("cropName", cropTypeView.getCropName() + " (Firm)");
                    jsonObject.put("acreagePlanted", getCropAcreage(cropTypeView, outputDetails));
                    jsonArray.add(jsonObjectForFirm);
                }
            }
        }

        List<CropsGroupView> cropsGroupViewList = (List<CropsGroupView>) outputDetails.get("cropsGroupViews");

        for (CropsGroupView cropsGroupView : cropsGroupViewList) {
            JSONObject jsonObject = getCropLimit(farmInfoView, cropsGroupView.getMinimumAcres(), cropsGroupView.getMaximumAcres(), null, cropsGroupView, outputDetails);
            jsonObject.put("cropName", cropsGroupView.getCropsGroupName());
            int totalAcreage = 0;
            Set<CropType> cropSet = cropsGroupView.getCropSet();
            for (CropType cropType : cropSet) {
                totalAcreage += Integer.parseInt(AgricultureStandardUtils.removeAllCommas(getCropAcreage(new CropTypeView(cropType), outputDetails)));
            }
            jsonObject.put("acreagePlanted", totalAcreage);
            jsonArray.add(jsonObject);
        }

        return jsonArray;
    }

    private String getCropAcreage(CropTypeView cropTypeView, JSONObject outputDetails){
        FarmInfoView farmInfoView = (FarmInfoView) outputDetails.get("farmInfoView");

        if (PlanByStrategy.PLAN_BY_ACRES.equals(farmInfoView.getStrategy())) {
            List<FarmOutputDetailsView> farmOutputDetailsViewList = (List<FarmOutputDetailsView>) outputDetails.get("farmOutputDetails");

            for (FarmOutputDetailsView farmOutputDetailsView : farmOutputDetailsViewList) {

                if (farmOutputDetailsView.getCropTypeView().getId().equals(cropTypeView.getId())) {
                    return farmOutputDetailsView.getUsedAcres();
                }
            }

        } else if (PlanByStrategy.PLAN_BY_FIELDS.equals(farmInfoView.getStrategy())){
            Map<String, String> hashMapForAcre = (Map<String, String>) outputDetails.get("hashMapForAcre");

            String cropName = cropTypeView.getCropName();
            if (cropTypeView.getFirmchecked().equalsIgnoreCase("true")) {
                cropName += " (Firm)";
            } /*else if (cropTypeView.getProposedchecked()) {
                cropName += " (Proposed)";
            }*/

            return hashMapForAcre.get(cropName) != null ? hashMapForAcre.get(cropName).split(" ")[0] : "--";
        }

        return "";
    }

    private JSONObject getCropLimit(FarmInfoView farmInfoView, String minAcres, String maxAcres, CropTypeView cropTypeView, CropsGroupView cropsGroupView, JSONObject outputDetails) {

        JSONObject jsonObject = new JSONObject();
        /* no max and no min acres */ if (minAcres.equalsIgnoreCase("") && maxAcres.equalsIgnoreCase("")) {
            jsonObject.put(MIN_LIMIT, "--");
            jsonObject.put(MAX_LIMIT, "--");
            jsonObject.put(IMPACTING_INCOME, "--");
            jsonObject.put(INC_DEC_INCOME, "--");
            jsonObject.put(MESSAGE, "No Limit Specified");

        }/* max acres */ else if (minAcres.equalsIgnoreCase("") && !maxAcres.equalsIgnoreCase("")) {
            jsonObject.put(MIN_LIMIT, "--");
            jsonObject.put(MAX_LIMIT, "No more than " + maxAcres + " acres");
            String max = isIncomeImpactedForCropLimit(cropTypeView, cropsGroupView, outputDetails, "max");
            if(farmInfoView.getStrategy().equals(PlanByStrategy.PLAN_BY_FIELDS) && max.equals(NO)){
                jsonObject.put(IMPACTING_INCOME, "May be");
                jsonObject.put(INC_DEC_INCOME, "Increase");
                jsonObject.put(MESSAGE, "Maximum crop limit may be impacting Estimated Income");
            } else {
                jsonObject.put(IMPACTING_INCOME, max);
                jsonObject.put(INC_DEC_INCOME, max.equalsIgnoreCase(YES) ? "Increase" : "--");
                if (max.equalsIgnoreCase(YES)) {
                    jsonObject.put(MESSAGE, "Maximum crop limit is impacting Estimated Income");
                } else {
                    jsonObject.put(MESSAGE, "Maximum crop limit is not impacting Estimated Income");
                }
            }


        }/* min acres */ else if (!minAcres.equalsIgnoreCase("") && maxAcres.equalsIgnoreCase("")) {
            jsonObject.put(MIN_LIMIT, "At least " + minAcres + " acres");
            jsonObject.put(MAX_LIMIT, "--");
            String min = isIncomeImpactedForCropLimit(cropTypeView, cropsGroupView, outputDetails, "min");
            jsonObject.put(IMPACTING_INCOME, min);
            jsonObject.put(INC_DEC_INCOME, min.equalsIgnoreCase(YES) ? "Decrease" : "--");

            if (min.equalsIgnoreCase(YES)) {
                jsonObject.put(MESSAGE, "Minimum crop limit is impacting Estimated Income");
            } else {
                jsonObject.put(MESSAGE, "Minimum crop limit is not impacting Estimated Income");
            }

        }/* min and max acres */ else if (!minAcres.equalsIgnoreCase("") && !maxAcres.equalsIgnoreCase("")) {
            jsonObject.put(MIN_LIMIT, "At least " + minAcres + " acres");
            jsonObject.put(MAX_LIMIT, "No more than " + maxAcres + " acres");
            String both = isIncomeImpactedForCropLimit(cropTypeView, cropsGroupView, outputDetails, "both");
            jsonObject.put(IMPACTING_INCOME, both);
            jsonObject.put(INC_DEC_INCOME, "--");
            if (both.equalsIgnoreCase(YES)) {
                jsonObject.put(MESSAGE, "Minimum and maximum crop limit is impacting Estimated Income");
            } else {
                jsonObject.put(MESSAGE, "Minimum and maximum crop limit is not impacting Estimated Income");
            }

        } else {
            jsonObject.put(MIN_LIMIT, "--");
            jsonObject.put(MAX_LIMIT, "--");
            jsonObject.put(IMPACTING_INCOME, "--");
            jsonObject.put(INC_DEC_INCOME, "--");
            jsonObject.put(MESSAGE, "--");
        }


        return jsonObject;
    }

    private String isIncomeImpactedForCropLimit(CropTypeView cropTypeView, CropsGroupView cropsGroupView,
                                                JSONObject outputDetails, String minOrMax) {

        FarmInfoView farmInfoView = (FarmInfoView) outputDetails.get("farmInfoView");

        PlanByStrategy strategy = farmInfoView.getStrategy();
        if (Objects.equals(strategy, PlanByStrategy.PLAN_BY_ACRES)) {
            List<FarmOutputDetailsView> farmOutputDetailsViewList = (List<FarmOutputDetailsView>) outputDetails.get("farmOutputDetails");
            for (FarmOutputDetailsView farmOutputDetailsView : farmOutputDetailsViewList) {
                if (cropTypeView != null) {
                    int usedAcres, minimumAcres, maximumAcres;
                    if (farmOutputDetailsView.getCropTypeView().getId().equals(cropTypeView.getId()) && farmOutputDetailsView.getForFirm()){
                        usedAcres = farmOutputDetailsView.getUsedAcresAsInteger();
                        minimumAcres = cropTypeView.getForwardAcres().intValue();
                        maximumAcres = 0;
                        return getYesNo(usedAcres, minimumAcres, maximumAcres, minOrMax);
                    } else if (farmOutputDetailsView.getCropTypeView().getId().equals(cropTypeView.getId())) {
                        usedAcres = farmOutputDetailsView.getUsedAcresAsInteger();
                        minimumAcres = Integer.parseInt(cropTypeView.getMinimumAcresWithoutComma().equalsIgnoreCase("") ? "0" : cropTypeView.getMinimumAcresWithoutComma());
                        maximumAcres = Integer.parseInt(cropTypeView.getMaximumAcresWithoutComma().equalsIgnoreCase("") ? "0" : cropTypeView.getMaximumAcresWithoutComma());
                        return getYesNo(usedAcres, minimumAcres, maximumAcres, minOrMax);
                    }
                } else if (cropsGroupView != null) {
                    Set<CropType> cropSet = cropsGroupView.getCropSet();
                    int usedAcres = 0;
                    for (CropType cropType : cropSet) {
                        if (farmOutputDetailsView.getCropTypeView().getId().equals(cropType.getId())) {
                            usedAcres += farmOutputDetailsView.getUsedAcresAsInteger();
                        }
                    }
                    int minimumAcres = Integer.parseInt(cropsGroupView.getMinimumAcresWithoutComma().equalsIgnoreCase("") ? "0" : cropsGroupView.getMinimumAcresWithoutComma());
                    int maximumAcres = Integer.parseInt(cropsGroupView.getMaximumAcresWithoutComma().equalsIgnoreCase("") ? "0" : cropsGroupView.getMaximumAcresWithoutComma());

                    return getYesNo(usedAcres, minimumAcres, maximumAcres, minOrMax);
                }

            }

        } else if (Objects.equals(strategy, PlanByStrategy.PLAN_BY_FIELDS)) {
            Map<String, String> hashMapForAcre = (Map<String, String>) outputDetails.get("hashMapForAcre");

            if (cropTypeView != null) {

                int usedAcres, minimumAcres, maximumAcres;
                if(cropTypeView.getFirmchecked().equalsIgnoreCase("true") && hashMapForAcre.containsKey(cropTypeView.getCropName() + " (Firm)")){
                    usedAcres = Integer.parseInt(AgricultureStandardUtils.removeAllCommas(hashMapForAcre.get(cropTypeView.getCropName() + " (Firm)").split(" ")[0]));
                    minimumAcres = cropTypeView.getForwardAcres().intValue();
                    maximumAcres = 0;
                } else {
                    usedAcres = Integer.parseInt(AgricultureStandardUtils.removeAllCommas(hashMapForAcre.get(cropTypeView.getCropName()).split(" ")[0]));
                    minimumAcres = Integer.parseInt(cropTypeView.getMinimumAcresWithoutComma().equalsIgnoreCase("") ? "0" : cropTypeView.getMinimumAcresWithoutComma());
                    maximumAcres = Integer.parseInt(cropTypeView.getMaximumAcresWithoutComma().equalsIgnoreCase("") ? "0" : cropTypeView.getMaximumAcresWithoutComma());
                }


                return getYesNo(usedAcres, minimumAcres, maximumAcres, minOrMax);

            } else if (cropsGroupView != null) {
                Set<CropType> cropSet = cropsGroupView.getCropSet();
                int usedAcres = 0;
                for (CropType cropType : cropSet) {
                    usedAcres += Integer.parseInt(AgricultureStandardUtils.removeAllCommas(hashMapForAcre.get(cropType.getCropName()).split(" ")[0]));
                }
                int minimumAcres = Integer.parseInt(cropsGroupView.getMinimumAcresWithoutComma().equalsIgnoreCase("") ? "0" : cropsGroupView.getMinimumAcresWithoutComma());
                int maximumAcres = Integer.parseInt(cropsGroupView.getMaximumAcresWithoutComma().equalsIgnoreCase("") ? "0" : cropsGroupView.getMaximumAcresWithoutComma());

                return getYesNo(usedAcres, minimumAcres, maximumAcres, minOrMax);
            }

        }

        return "";
    }
    @Override
    public String getYesNo(int usedAcres, int minimumAcres, int maximumAcres, String minOrMax) {
        if (minOrMax.equalsIgnoreCase("min")) {
            if (usedAcres == minimumAcres)
                return YES;
            else if (usedAcres > minimumAcres)
                return NO;
            else
                return YES;
        } else if (minOrMax.equalsIgnoreCase("max")) {
            return maximumAcres > usedAcres ? NO : YES;
        } else if (minOrMax.equalsIgnoreCase("both")) {
//            if (usedAcres > minimumAcres && maximumAcres < usedAcres) {
                return NO;
//            } else {
//                return YES;
//            }
        } else {
            return "";
        }
    }

    @Override
    public JSONArray buildCropAcreageContent(JSONObject outputDetails) {
        JSONArray jsonArray = new JSONArray();

        FarmInfoView farmInfoView = (FarmInfoView) outputDetails.get("farmInfoView");

        if (PlanByStrategy.PLAN_BY_ACRES.equals(farmInfoView.getStrategy())) {
            List<FarmOutputDetailsView> farmOutputDetailsViewList = (List<FarmOutputDetailsView>) outputDetails.get("farmOutputDetails");
            for (FarmOutputDetailsView farmOutputDetailsView : farmOutputDetailsViewList) {
                JSONObject jsonObject = new JSONObject();

                String cropName = farmOutputDetailsView.getCropTypeView().getCropName();
                if (farmOutputDetailsView.getForFirm()) {
                    cropName += " (Firm)";
                } else if (farmOutputDetailsView.getForProposed()) {
                    cropName += " (Proposed)";
                }

                jsonObject.put(CROP_NAME, cropName);


                jsonObject.put(ACREAGE, farmOutputDetailsView.getUsedAcres() + "<br>" + farmOutputDetailsView.getUsedAcresPercentage() + "%");

                if (!farmOutputDetailsView.getProfit().equalsIgnoreCase("0")) {
                    jsonObject.put(PROFIT, farmOutputDetailsView.getProfit() + "<br>" + farmOutputDetailsView.getUsedCapitalPercentage() + "%");
                } else {
                    jsonObject.put(PROFIT, "NA");
                }

                if (farmOutputDetailsView.getRatio() == 0.0) {
                    jsonObject.put(RATIO, "NA");
                } else {
                    jsonObject.put(RATIO, farmOutputDetailsView.getRatio());
                }

                if (farmOutputDetailsView.getProfitIndex() == 0.0) {
                    jsonObject.put(INDEX, "NA");
                } else {
                    jsonObject.put(INDEX, farmOutputDetailsView.getProfitIndex());
                }

                if (farmOutputDetailsView.getProfit().equalsIgnoreCase("0")) {
                    jsonObject.put(RATING, "NA");
                } else {
                    jsonObject.put(RATING, farmOutputDetailsView.getRating());
                }

                jsonArray.add(jsonObject);

            }


        } else if (PlanByStrategy.PLAN_BY_FIELDS.equals(farmInfoView.getStrategy())) {
            Map<String, String> hashMapForAcre = (Map<String, String>) outputDetails.get("hashMapForAcre");
            Map<String, String> hashMapForProfit = (Map<String, String>) outputDetails.get("hashMapForProfit");
            Map<String, String> hashMapForRatio = (Map<String, String>) outputDetails.get("hashMapForRatio");
            Map<String, String> hashMapForProfitIndex = (Map<String, String>) outputDetails.get("hashMapForProfitIndex");
            Map<String, String> hashMapForRating = (Map<String, String>) outputDetails.get("hashMapForRating");

            Set<String> cropTypeKeySet = hashMapForAcre.keySet();
            for (String cropTypeKey : cropTypeKeySet) {

                JSONObject jsonObject = new JSONObject();

                jsonObject.put(CROP_NAME, cropTypeKey);

                String acreage = hashMapForAcre.get(cropTypeKey);
                jsonObject.put(ACREAGE, acreage.split(" ")[0] + " <br> " + acreage.split(" ")[1].replaceAll("[\\(\\)]", ""));

                String profit = hashMapForProfit.get(cropTypeKey);
                if (profit.equalsIgnoreCase("0 (0.0%)")
                        || profit.equalsIgnoreCase("0 (-0.0%)")) {
                    jsonObject.put(PROFIT, "NA");
                } else {

                    jsonObject.put(PROFIT, profit.split(" ")[0] + " <br> " + profit.split(" ")[1].replaceAll("[\\(\\)]", ""));
                }

                if (hashMapForRatio.get(cropTypeKey).equalsIgnoreCase("0")) {
                    jsonObject.put(RATIO, "NA");
                } else {
                    jsonObject.put(RATIO, hashMapForRatio.get(cropTypeKey));
                }

                if (hashMapForProfitIndex.get(cropTypeKey).equalsIgnoreCase("0.0")
                        || hashMapForProfitIndex.get(cropTypeKey).equalsIgnoreCase("0")) {
                    jsonObject.put(INDEX, "NA");
                } else {
                    jsonObject.put(INDEX, hashMapForProfitIndex.get(cropTypeKey).replace("%", ""));
                }

                jsonObject.put(RATING, hashMapForRating.get(cropTypeKey));


                jsonArray.add(jsonObject);

            }
        }


        return jsonArray;
    }

    @Override
    public JSONObject buildResourcesContent(JSONObject outputDetails) {
        List<CropResourceUsageView> resourceList = (List<CropResourceUsageView>) outputDetails.get("resourceList");
        Map<String, String> cropResourceUsed = (Map<String, String>) outputDetails.get("cropResourceUsed");
        Map<String, String> cropResourceUnused = (Map<String, String>) outputDetails.get("cropResourceUnused");

        JSONObject flagStatusObj = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (CropResourceUsageView cropResourceUsageView : resourceList) {
            JSONObject jsonObject = null;
            if (cropResourceUsageView.isActive()) {
                jsonObject = new JSONObject();
                if(cropResourceUsageView.getCropResourceUse().equalsIgnoreCase("capital")){
                    jsonObject.put(RESOURCE_NAME, AppConstants.WORKING_CAPITAL);
                } else if(cropResourceUsageView.getCropResourceUse().equalsIgnoreCase("land")){
                    String used = cropResourceUsed.get(cropResourceUsageView.getCropResourceUse());
                    String unused = cropResourceUnused.get(cropResourceUsageView.getCropResourceUse());
                    String totalAvailable = cropResourceUsageView.getCropResourceUseAmount();
                    long total = null == totalAvailable ? 0L : AgricultureStandardUtils.stringToLong(totalAvailable);
                    long usedLong = null == used ? 0L : AgricultureStandardUtils.stringToLong(used);
                    long unusedLong = null == unused ? 0L : AgricultureStandardUtils.stringToLong(unused);

                    jsonObject.put(RESOURCE_NAME, cropResourceUsageView.getCropResourceUse());
                    flagStatusObj.put(cropResourceUsageView.getCropResourceUse(), (usedLong != total && usedLong < total));
                } else {
                    jsonObject.put(RESOURCE_NAME, cropResourceUsageView.getCropResourceUse());
                    flagStatusObj.put(cropResourceUsageView.getCropResourceUse(), false);
                }

                jsonObject.put(RESOURCE_TOTAL_AVAILABLE, cropResourceUsageView.getCropResourceUseAmount());
                jsonObject.put(RESOURCE_USED, cropResourceUsed.get(cropResourceUsageView.getCropResourceUse()));
                jsonObject.put(RESOURCE_UNUSED, cropResourceUnused.get(cropResourceUsageView.getCropResourceUse()));

                if(cropResourceUnused.get(cropResourceUsageView.getCropResourceUse()).equalsIgnoreCase("0")){
                    jsonObject.put(RESOURCE_IMPACTING_PROFIT, "Yes");
                } else {
                    jsonObject.put(RESOURCE_IMPACTING_PROFIT, "No");
                }
                jsonArray.add(jsonObject);
            }
        }

        JSONObject data = new JSONObject();
        data.put("resourceDetails", jsonArray);
        data.put("resourceFlags", flagStatusObj);

        return data;
    }

}

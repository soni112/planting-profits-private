package com.decipher.agriculture.service.farmDetails.impl;

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
                jsonArray.add(jsonObject);

                if (cropTypeView.getFirmchecked().equalsIgnoreCase("true")){

                    JSONObject jsonObjectForFirm = getCropLimit(farmInfoView, AgricultureStandardUtils.withoutDecimalAndComma(cropTypeView.getForwardAcres()), "", cropTypeView, null, outputDetails);
                    jsonObjectForFirm.put("cropName", cropTypeView.getCropName() + " (Firm)");
                    jsonArray.add(jsonObjectForFirm);
                }
            }
        }

        List<CropsGroupView> cropsGroupViewList = (List<CropsGroupView>) outputDetails.get("cropsGroupViews");

        for (CropsGroupView cropsGroupView : cropsGroupViewList) {
            JSONObject jsonObject = getCropLimit(farmInfoView, cropsGroupView.getMinimumAcres(), cropsGroupView.getMaximumAcres(), null, cropsGroupView, outputDetails);
            jsonObject.put("cropName", cropsGroupView.getCropsGroupName());
            jsonArray.add(jsonObject);
        }

        return jsonArray;
    }

    private JSONObject getCropLimit(FarmInfoView farmInfoView, String minAcres, String maxAcres, CropTypeView cropTypeView, CropsGroupView cropsGroupView, JSONObject outputDetails) {

        JSONObject jsonObject = new JSONObject();
        /* no max and no min acres */ if (minAcres.equalsIgnoreCase("") && maxAcres.equalsIgnoreCase("")) {
            jsonObject.put("minLimit", "--");
            jsonObject.put("maxLimit", "--");
            jsonObject.put("impactingIncome", "--");
            jsonObject.put("incDecIncome", "--");
            jsonObject.put("message", "No Limit Specified");

        }/* max acres */ else if (minAcres.equalsIgnoreCase("") && !maxAcres.equalsIgnoreCase("")) {
            jsonObject.put("minLimit", "--");
            jsonObject.put("maxLimit", "No more than " + maxAcres + " acres");
            String max = isIncomeImpactedForCropLimit(cropTypeView != null ? cropTypeView : null, cropsGroupView != null ? cropsGroupView : null, outputDetails, "max");
            if(farmInfoView.getStrategy().equals(PlanByStrategy.PLAN_BY_FIELDS) && max.equals(NO)){
                jsonObject.put("impactingIncome", "May be");
                jsonObject.put("incDecIncome", "Increase");
                jsonObject.put("message", "Maximum crop limit may be impacting Estimated Income");
            } else {
                jsonObject.put("impactingIncome", max);
                jsonObject.put("incDecIncome", max.equalsIgnoreCase(YES) ? "Increase" : "--");
                if (max.equalsIgnoreCase(YES)) {
                    jsonObject.put("message", "Maximum crop limit is impacting Estimated Income");
                } else {
                    jsonObject.put("message", "Maximum crop limit is not impacting Estimated Income");
                }
            }


        }/* min acres */ else if (!minAcres.equalsIgnoreCase("") && maxAcres.equalsIgnoreCase("")) {
            jsonObject.put("minLimit", "At least " + minAcres + " acres");
            jsonObject.put("maxLimit", "--");
            String min = isIncomeImpactedForCropLimit(cropTypeView != null ? cropTypeView : null, cropsGroupView != null ? cropsGroupView : null, outputDetails, "min");
            jsonObject.put("impactingIncome", min);
            jsonObject.put("incDecIncome", min.equalsIgnoreCase(YES) ? "Decrease" : "--");

            if (min.equalsIgnoreCase(YES)) {
                jsonObject.put("message", "Minimum crop limit is impacting Estimated Income");
            } else {
                jsonObject.put("message", "Minimum crop limit is not impacting Estimated Income");
            }

        }/* min and max acres */ else if (!minAcres.equalsIgnoreCase("") && !maxAcres.equalsIgnoreCase("")) {
            jsonObject.put("minLimit", "At least " + minAcres + " acres");
            jsonObject.put("maxLimit", "No more than " + maxAcres + " acres");
            String both = isIncomeImpactedForCropLimit(cropTypeView != null ? cropTypeView : null, cropsGroupView != null ? cropsGroupView : null, outputDetails, "both");
            jsonObject.put("impactingIncome", both);
//            jsonObject.put("impactingIncome", "No");
            jsonObject.put("incDecIncome", "--");
            if (both.equalsIgnoreCase(YES)) {
                jsonObject.put("message", "Minimum and maximum crop limit is impacting Estimated Income");
            } else {
                jsonObject.put("message", "Minimum and maximum crop limit is not impacting Estimated Income");
            }

        } else {
            jsonObject.put("minLimit", "--");
            jsonObject.put("maxLimit", "--");
            jsonObject.put("impactingIncome", "--");
            jsonObject.put("incDecIncome", "--");
            jsonObject.put("message", "--");
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
                        minimumAcres = Integer.parseInt(cropTypeView.getMinimumAcres().equalsIgnoreCase("") ? "0" : cropTypeView.getMinimumAcres());
                        maximumAcres = Integer.parseInt(cropTypeView.getMaximumAcres().equalsIgnoreCase("") ? "0" : cropTypeView.getMaximumAcres());
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
                    int minimumAcres = Integer.parseInt(cropsGroupView.getMinimumAcres().equalsIgnoreCase("") ? "0" : cropsGroupView.getMinimumAcres());
                    int maximumAcres = Integer.parseInt(cropsGroupView.getMaximumAcres().equalsIgnoreCase("") ? "0" : cropsGroupView.getMaximumAcres());

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
                    minimumAcres = Integer.parseInt(cropTypeView.getMinimumAcres().equalsIgnoreCase("") ? "0" : cropTypeView.getMinimumAcresWithoutComma());
                    maximumAcres = Integer.parseInt(cropTypeView.getMaximumAcres().equalsIgnoreCase("") ? "0" : cropTypeView.getMaximumAcresWithoutComma());
                }


                return getYesNo(usedAcres, minimumAcres, maximumAcres, minOrMax);

            } else if (cropsGroupView != null) {
                Set<CropType> cropSet = cropsGroupView.getCropSet();
                int usedAcres = 0;
                for (CropType cropType : cropSet) {
                    usedAcres += Integer.parseInt(AgricultureStandardUtils.removeAllCommas(hashMapForAcre.get(cropType.getCropName()).split(" ")[0]));
                }
                int minimumAcres = Integer.parseInt(cropsGroupView.getMinimumAcres().equalsIgnoreCase("") ? "0" : cropsGroupView.getMinimumAcres());
                int maximumAcres = Integer.parseInt(cropsGroupView.getMaximumAcres().equalsIgnoreCase("") ? "0" : cropsGroupView.getMaximumAcres());

                return getYesNo(usedAcres, minimumAcres, maximumAcres, minOrMax);
            }

        }

        return "";
    }
    @Override
    public String getYesNo(int usedAcres, int minimumAcres, int maximumAcres, String minOrMax) {
        if (minOrMax.equalsIgnoreCase("min")) {
            if (usedAcres > minimumAcres) {
                return NO;
            } else {
                return YES;
            }
        } else if (minOrMax.equalsIgnoreCase("max")) {
            if (maximumAcres > usedAcres) {
                return NO;
            } else {
                return YES;
            }
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

        if (Objects.equals(farmInfoView.getStrategy(), PlanByStrategy.PLAN_BY_ACRES)) {
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


        } else if (Objects.equals(farmInfoView.getStrategy(), PlanByStrategy.PLAN_BY_FIELDS)) {
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
    public JSONArray buildResourcesContent(JSONObject outputDetails) {
        List<CropResourceUsageView> resourceList = (List<CropResourceUsageView>) outputDetails.get("resourceList");
        Map<String, String> cropResourceUsed = (Map<String, String>) outputDetails.get("cropResourceUsed");
        Map<String, String> cropResourceUnused = (Map<String, String>) outputDetails.get("cropResourceUnused");

        JSONArray jsonArray = new JSONArray();
        for (CropResourceUsageView cropResourceUsageView : resourceList) {
            JSONObject jsonObject = null;
            if (cropResourceUsageView.isActive()) {
                jsonObject = new JSONObject();
                if(cropResourceUsageView.getCropResourceUse().equalsIgnoreCase("capital")){
                    jsonObject.put("resourceName", "Working Capital");
                } else {
                    jsonObject.put("resourceName", cropResourceUsageView.getCropResourceUse());
                }

                jsonObject.put("totalAvailable", cropResourceUsageView.getCropResourceUseAmount());
                jsonObject.put("used", cropResourceUsed.get(cropResourceUsageView.getCropResourceUse()));
                jsonObject.put("unused", cropResourceUnused.get(cropResourceUsageView.getCropResourceUse()));

                if(cropResourceUnused.get(cropResourceUsageView.getCropResourceUse()).equalsIgnoreCase("0")){
                    jsonObject.put("impactingProfit", "Yes");
                } else {
                    jsonObject.put("impactingProfit", "No");
                }
                jsonArray.add(jsonObject);
            }


        }


        return jsonArray;
    }


}

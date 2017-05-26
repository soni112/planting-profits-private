package com.decipher.agriculture.farmreport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.decipher.agriculture.data.farm.CropType;
import com.decipher.agriculture.data.farm.PlanByStrategy;
import com.decipher.util.AgricultureStandardUtils;
import com.decipher.view.form.farmDetails.CropFieldChociesView;
import com.decipher.view.form.farmDetails.CropTypeView;
import com.decipher.view.form.farmDetails.CropsGroupView;
import com.decipher.view.form.farmDetails.FarmInfoView;
import com.decipher.view.form.farmDetails.FarmOutputDetailsForFieldView;
import com.decipher.view.form.farmDetails.FarmOutputDetailsView;
import com.decipher.view.form.farmDetails.FieldInfoView;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * @author abhishek
 * @dateCreated - 29-11-2015
 */

public class ReportDataPage3 {

    /**
     * @chanegd - Abhishek
     * @date - 11-01-2016
     * @desc - Only data objects to be used are used not using any service to populate data objects
     */
    private JSONObject baseSelectedStrategyOutputDetailsJsonObj;

    private static final String YES = "Yes";
    private static final String NO = "No";

    public ReportDataPage3(JSONObject baseSelectedStrategyOutputDetailsJsonObj) {
        /**
         * @chanegd - Abhishek
         * @date - 11-01-2016
         * @desc - Only data objects to be used are used not using any service to populate data objects
         */

        this.baseSelectedStrategyOutputDetailsJsonObj = baseSelectedStrategyOutputDetailsJsonObj;
    }

    public Object[] getCropFieldChoice() {
        /*List<CropTypeView> cropTypeView = cropTypeService.getAllCropByFarm(farmInfoView.getId());*/
        List<CropTypeView> cropTypeView = (List<CropTypeView>)baseSelectedStrategyOutputDetailsJsonObj.get("cropTypeView");
        int numberOfCrops = 0;
        List<CropTypeView> cropsHeaderList = new ArrayList<CropTypeView>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        int[] cropIdArray = new int[cropTypeView.size()];
        for (CropTypeView view : cropTypeView) {
            if (view.getSelected()) {
                cropsHeaderList.add(view);
                cropIdArray[numberOfCrops] = view.getId();
                numberOfCrops++;
            }
        }
        /**
         * @chanegd - Abhishek
         * @date - 11-01-2016
         * @desc - Only data objects to be used are used not using any service to populate data objects
         */
        /*List<FieldInfoView> fieldInfoViews = fieldInfoService.getAllFieldsByFarmId(farmInfoView.getId());*/
        List<FieldInfoView> fieldInfoViews = (List<FieldInfoView>)baseSelectedStrategyOutputDetailsJsonObj.get("fieldInfoList");
        /**
         * @chanegd - Abhishek
         * @date - 11-01-2016
         * @desc - Only data objects to be used are used not using any service to populate data objects
         */
        /*List<CropFieldChociesView> cropFieldsDetails = cropFieldChociesService.getAllCropFiledsCropIds(cropIdArray);*/
        List<CropFieldChociesView> cropFieldsDetails = (List<CropFieldChociesView>)baseSelectedStrategyOutputDetailsJsonObj.get("cropFieldsDetails");
        for (FieldInfoView f : fieldInfoViews) {
            List<CropFieldChociesView> cropsDataList = new ArrayList<CropFieldChociesView>();
            for (CropFieldChociesView cropFieldChociesView : cropFieldsDetails) {
                if (cropFieldChociesView.getFieldName().getFieldName().equalsIgnoreCase(f.getFieldName())) {
                    cropsDataList.add(cropFieldChociesView);
                }
            }
            dataMap.put(f.getFieldName(), cropsDataList);
        }

        return new Object[]{cropsHeaderList, dataMap};

    }

    public List<Map<String, Object>> getCropFieldAssignments() {
        /**
         * @chanegd - Abhishek
         * @date - 11-01-2016
         * @desc - Only data objects to be used are used not using any service to populate data objects
         */
        /*List<FieldInfoView> fieldsByFarmId = fieldInfoService.getAllFieldsByFarmId(farmInfoView.getId());*/
        List<FieldInfoView> fieldsByFarmId = (List<FieldInfoView>)baseSelectedStrategyOutputDetailsJsonObj.get("fieldInfoList");
        /**
         * @chanegd - Abhishek
         * @date - 11-01-2016
         * @desc - Only data objects to be used are used not using any service to populate data objects
         */
        /*List<FarmOutputDetailsForFieldView> farmOutputDetailsViewList = farmOutputCalculationService.getAllFarmOutputDetailsForFieldByFarm(farmInfoView.getId());*/
        List<FarmOutputDetailsForFieldView> farmOutputDetailsViewList = (List<FarmOutputDetailsForFieldView>)baseSelectedStrategyOutputDetailsJsonObj.get("farmOutputDetails");

        List<Map<String, Object>> finalData = new ArrayList<Map<String, Object>>();
        for (FieldInfoView fieldInfoView : fieldsByFarmId) {
            if (fieldInfoView.getFallow().equalsIgnoreCase("true")) {
                Map<String, Object> data = new HashMap<String, Object>();
                data.put("field", fieldInfoView.getFieldName());
                data.put("size", fieldInfoView.getFieldSize());
                data.put("crop", "Fallow");
                finalData.add(data);
            } else {
                boolean planted = false;
                for (FarmOutputDetailsForFieldView farmOutputDetailsForFieldView : farmOutputDetailsViewList) {
                    if (farmOutputDetailsForFieldView.getFieldInfoView().getFieldName().equalsIgnoreCase(fieldInfoView.getFieldName()) && farmOutputDetailsForFieldView.getUsedAcres() != "0") {
                        planted = true;
                        Map<String, Object> data = new HashMap<String, Object>();
                        data.put("field", fieldInfoView.getFieldName());
                        data.put("size", farmOutputDetailsForFieldView.getUsedAcres());
                        String forStr = "";
                        if (farmOutputDetailsForFieldView.isForFirm()) {
                            forStr = " (Firm)";
                        } else if (farmOutputDetailsForFieldView.isForProposed()) {
                            forStr = " (Proposed)";
                        }
                        data.put("crop", farmOutputDetailsForFieldView.getCropTypeView().getCropName() + forStr);
                        finalData.add(data);
                    }
                }

                if (!planted) {
                    Map<String, Object> data = new HashMap<String, Object>();
                    data.put("field", fieldInfoView.getFieldName());
                    data.put("size", fieldInfoView.getFieldSize());
                    data.put("crop", "Not Planted");
                    finalData.add(data);
                }
            }
        }

        return finalData;
    }

    /**
     * @added - Abhishek
     * @date - 16-01-2016
     */
    public HashMap<String, Object> getCropAcreageLimts() {

        List<CropTypeView> cropTypeViewList = (List<CropTypeView>)baseSelectedStrategyOutputDetailsJsonObj.get("cropTypeView");
        List<CropsGroupView> cropsGroupViewsList = (List<CropsGroupView>)baseSelectedStrategyOutputDetailsJsonObj.get("cropsGroupViews");

        HashMap<String, Object> cropTypeAndGroupsHashMap = new HashMap<String, Object>();

        cropTypeAndGroupsHashMap.put("cropTypeViewList", cropTypeViewList);
        cropTypeAndGroupsHashMap.put("cropsGroupViewsList", cropsGroupViewsList);

        return cropTypeAndGroupsHashMap;

    }

    public JSONArray getCropLimitContent() {

        JSONObject outputDetails = baseSelectedStrategyOutputDetailsJsonObj;
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
}
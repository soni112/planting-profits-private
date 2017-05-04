package com.decipher.agriculture.farmreport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.decipher.view.form.farmDetails.CropFieldChociesView;
import com.decipher.view.form.farmDetails.CropTypeView;
import com.decipher.view.form.farmDetails.CropsGroupView;
import com.decipher.view.form.farmDetails.FarmOutputDetailsForFieldView;
import com.decipher.view.form.farmDetails.FieldInfoView;
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
}
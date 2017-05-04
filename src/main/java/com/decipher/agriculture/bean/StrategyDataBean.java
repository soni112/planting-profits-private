package com.decipher.agriculture.bean;

import com.decipher.agriculture.data.farm.CropType;
import com.decipher.view.form.farmDetails.CropFieldChociesView;
import com.decipher.view.form.farmDetails.CropResourceUsageFieldVariancesView;
import com.decipher.view.form.farmDetails.CropResourceUsageView;
import com.decipher.view.form.farmDetails.CropTypeView;
import com.decipher.view.form.farmDetails.FarmInfoView;
import com.decipher.view.form.farmDetails.FieldInfoView;
import com.decipher.view.form.strategy.FarmCustomStrategyView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by abhishek on 5/1/16.
 */
public class StrategyDataBean implements Cloneable {

    private List<CropTypeView> cropTypeViewList;
    private Integer[] cropIdArray;
    private List<CropResourceUsageView> resourceUsageViewsList;
    private List<CropResourceUsageFieldVariancesView> cropResourceUsageFieldVariancesList;
    private List<FarmCustomStrategyView> farmCustomStrategyViewList;
    private List<FieldInfoView> fieldInfoViewList;
    private List<CropFieldChociesView> cropFieldChoicesViewList;
    private FarmInfoView farmInfoView;
    /**
     * @changed - Abhishek
     * @date - 11-01-2016
     * @desc - used FarmCustomStrategyView instead of strategyID
     */
    /*private int strategyId;*/
    private FarmCustomStrategyView farmCustomStrategyView;


    /**
     * @changed - Abhishek
     * @date - 08-02-2016
     * @desc - added cropTypeList for output calculation
     */
    private List<CropType> cropTypeList;


    public List<CropType> getCropTypeList() {
        return cropTypeList;
    }

    public void setCropTypeList(List<CropType> cropTypeList) {
        this.cropTypeList = cropTypeList;
    }

//    public StrategyDataBean(){
//
//    }

//    public StrategyDataBean(FarmInfoView farmInfoView, List<CropTypeView> cropTypeViewList, int[] cropIdArray, List<CropResourceUsageView> resourceUsageViewsList, List<CropResourceUsageFieldVariancesView> cropResourceUsageFieldVariancesList, List<FarmCustomStrategyView> farmCustomStrategyViewList, List<FieldInfoView> fieldInfoViewList, List<CropFieldChociesView> cropFieldChoicesViewList, FarmCustomStrategyView farmCustomStrategyView) {
//        this.cropTypeViewList = cropTypeViewList;
//        this.cropIdArray = cropIdArray;
//        this.resourceUsageViewsList = resourceUsageViewsList;
//        this.cropResourceUsageFieldVariancesList = cropResourceUsageFieldVariancesList;
//        this.farmCustomStrategyViewList = farmCustomStrategyViewList;
//        this.fieldInfoViewList = fieldInfoViewList;
//        this.cropFieldChoicesViewList = cropFieldChoicesViewList;
//        this.farmInfoView = farmInfoView;
//        /**
//         * @changed - Abhishek
//         * @date - 11-01-2016
//         * @desc - used FarmCustomStrategyView instead of strategyID
//         */
//        this.farmCustomStrategyView = farmCustomStrategyView;
//    }

    public List<CropTypeView> getCropTypeViewList() {
        return cropTypeViewList;
    }

    public void setCropTypeViewList(List<CropTypeView> cropTypeViewList) {
        this.cropTypeViewList = cropTypeViewList;
    }

    public Integer[] getCropIdArray() {
        return cropIdArray;
    }

    public void setCropIdArray(Integer[] cropIdArray) {
        this.cropIdArray = cropIdArray;
    }

    public List<CropResourceUsageView> getResourceUsageViewsList() {
        return resourceUsageViewsList;
    }

    public void setResourceUsageViewsList(List<CropResourceUsageView> resourceUsageViewsList) {
        this.resourceUsageViewsList = resourceUsageViewsList;
    }

    public List<CropResourceUsageFieldVariancesView> getCropResourceUsageFieldVariancesList() {
        return cropResourceUsageFieldVariancesList;
    }

    public void setCropResourceUsageFieldVariancesList(List<CropResourceUsageFieldVariancesView> cropResourceUsageFieldVariancesList) {
        this.cropResourceUsageFieldVariancesList = cropResourceUsageFieldVariancesList;
    }

    public List<FarmCustomStrategyView> getFarmCustomStrategyViewList() {
        return farmCustomStrategyViewList;
    }

    public void setFarmCustomStrategyViewList(List<FarmCustomStrategyView> farmCustomStrategyViewList) {
        this.farmCustomStrategyViewList = farmCustomStrategyViewList;
    }

    public List<FieldInfoView> getFieldInfoViewList() {
        return fieldInfoViewList;
    }

    public void setFieldInfoViewList(List<FieldInfoView> fieldInfoViewList) {
        this.fieldInfoViewList = fieldInfoViewList;
    }

    public List<CropFieldChociesView> getCropFieldChoicesViewList() {
        return cropFieldChoicesViewList;
    }

    public void setCropFieldChoicesViewList(List<CropFieldChociesView> cropFieldChoicesViewList) {
        this.cropFieldChoicesViewList = cropFieldChoicesViewList;
    }

    public FarmInfoView getFarmInfoView() {
        return farmInfoView;
    }

    public void setFarmInfoView(FarmInfoView farmInfoView) {
        this.farmInfoView = farmInfoView;
    }

    /*public int getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(int strategyId) {
        this.strategyId = strategyId;
    }*/

    /**
     * @Added - Abhishek
     * @date - 11-01-2016
     * @desc - getter and setter for FarmCustomStrategyView
     */
    public FarmCustomStrategyView getFarmCustomStrategyView() {
        return farmCustomStrategyView;
    }

    public void setFarmCustomStrategyView(FarmCustomStrategyView farmCustomStrategyView) {
        this.farmCustomStrategyView = farmCustomStrategyView;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        StrategyDataBean clonedObject = (StrategyDataBean)super.clone();

        List<CropTypeView> cropTypeViewList = new ArrayList<>();
        for (CropTypeView typeView : this.cropTypeViewList) {
            cropTypeViewList.add(typeView.clone());
        }
        clonedObject.setCropTypeViewList(cropTypeViewList);

        Integer[] cropIdArray = this.getCropIdArray();
        clonedObject.setCropIdArray(Arrays.copyOf(cropIdArray, cropIdArray.length));

        List<CropResourceUsageView> resourceUsageViewList = new ArrayList<>();
        for (CropResourceUsageView resourceUsageView : this.resourceUsageViewsList) {
            resourceUsageViewList.add(resourceUsageView.clone());
        }
        clonedObject.setResourceUsageViewsList(resourceUsageViewList);

        List<CropResourceUsageFieldVariancesView> resourceUsageVariancesList = new ArrayList<>();
        for (CropResourceUsageFieldVariancesView resourceUsageVariance : this.cropResourceUsageFieldVariancesList) {
            resourceUsageVariancesList.add(resourceUsageVariance.clone());
        }
        clonedObject.setCropResourceUsageFieldVariancesList(resourceUsageVariancesList);

        List<FarmCustomStrategyView> farmCustomStrategyViewList = new ArrayList<>();
        for (FarmCustomStrategyView farmCustomStrategyView : this.farmCustomStrategyViewList) {
            farmCustomStrategyViewList.add(farmCustomStrategyView.clone());
        }
        clonedObject.setFarmCustomStrategyViewList(farmCustomStrategyViewList);

        List<FieldInfoView> fieldInfoViewList = new ArrayList<>();
        for (FieldInfoView fieldInfoView : this.fieldInfoViewList) {
            fieldInfoViewList.add(fieldInfoView.clone());
        }
        clonedObject.setFieldInfoViewList(fieldInfoViewList);

        List<CropFieldChociesView> cropFieldChociesViewList = new ArrayList<>();
        for (CropFieldChociesView cropFieldsDetail : this.cropFieldChoicesViewList) {
            cropFieldChociesViewList.add(cropFieldsDetail.clone());
        }
        clonedObject.setCropFieldChoicesViewList(cropFieldChociesViewList);

        clonedObject.setFarmInfoView(this.getFarmInfoView().clone());

        return clonedObject;
    }

}

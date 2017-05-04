package com.decipher.agriculture.bean;

import com.decipher.agriculture.data.farm.CropType;
import com.decipher.agriculture.data.farm.FarmInfo;
import com.decipher.view.form.farmDetails.CropResourceUsageView;

import java.util.List;

/**
 * Created by abhishek on 21/12/15.
 */
public class OutputBeanForStrategy {

    private FarmInfo farmInfo;
    private boolean saveFlag = false;
    private boolean baselineFlag = false;
    private int strategyID;
    private int farmID;
    private boolean sesntivityFlag = false;
    private List<CropResourceUsageView> resourceUsageViews;

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

    public OutputBeanForStrategy() {
    }

    public OutputBeanForStrategy(int farmID, int strategyID, boolean baselineFlag, boolean saveFlag, FarmInfo farmInfo) {
        this.farmID = farmID;
        this.strategyID = strategyID;
        this.baselineFlag = baselineFlag;
        this.saveFlag = saveFlag;
        this.farmInfo = farmInfo;
    }

    public FarmInfo getFarmInfo() {
        return farmInfo;
    }

    public void setFarmInfo(FarmInfo farmInfo) {
        this.farmInfo = farmInfo;
    }

    public boolean getSaveFlag() {
        return saveFlag;
    }

    public void setSaveFlag(boolean saveFlag) {
        this.saveFlag = saveFlag;
    }

    public boolean getBaselineFlag() {
        return baselineFlag;
    }

    public void setBaselineFlag(boolean baselineFlag) {
        this.baselineFlag = baselineFlag;
    }

    public int getStrategyID() {
        return strategyID;
    }

    public void setStrategyID(int strategyID) {
        this.strategyID = strategyID;
    }

    public int getFarmID() {
        return farmID;
    }

    public void setFarmID(int farmID) {
        this.farmID = farmID;
    }

    public boolean getSesntivityFlag() {
        return sesntivityFlag;
    }

    public void setSesntivityFlag(boolean sesntivityFlag) {
        this.sesntivityFlag = sesntivityFlag;
    }

    public List<CropResourceUsageView> getResourceUsageViews() {
        return resourceUsageViews;
    }

    public void setResourceUsageViews(List<CropResourceUsageView> resourceUsageViews) {
        this.resourceUsageViews = resourceUsageViews;
    }
}

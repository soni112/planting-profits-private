package com.decipher.agriculture.bean;

import com.decipher.agriculture.data.farm.CropsGroup;
import com.decipher.view.form.farmDetails.CropResourceUsageView;
import com.decipher.view.form.farmDetails.FieldInfoView;
import net.sf.javailp.Result;

import java.util.List;
import java.util.Set;

/**
 * Created by abhishek on 18/10/16.
 */
public class LinearProgrammingResultBean {

    private Result bigResult = null;
    private Result smallResult = null;
    private List<CropBeanForOutput> cropBeanForOutputList;
    private List<CropResourceUsageView> resourceUsageViews;
    private Set<CropsGroup> cropsGroups;
    private List<FieldInfoView> fieldInfoViews;
    private List<String[]> combination;

    public List<String[]> getCombination() {
        return combination;
    }

    public void setCombination(List<String[]> combination) {
        this.combination = combination;
    }

    public Result getBigResult() {
        return bigResult;
    }

    public void setBigResult(Result bigResult) {
        this.bigResult = bigResult;
    }

    public Result getSmallResult() {
        return smallResult;
    }

    public void setSmallResult(Result smallResult) {
        this.smallResult = smallResult;
    }

    public List<CropBeanForOutput> getCropBeanForOutputList() {
        return cropBeanForOutputList;
    }

    public void setCropBeanForOutputList(List<CropBeanForOutput> cropBeanForOutputList) {
        this.cropBeanForOutputList = cropBeanForOutputList;
    }

    public List<CropResourceUsageView> getResourceUsageViews() {
        return resourceUsageViews;
    }

    public void setResourceUsageViews(List<CropResourceUsageView> resourceUsageViews) {
        this.resourceUsageViews = resourceUsageViews;
    }

    public Set<CropsGroup> getCropsGroups() {
        return cropsGroups;
    }

    public void setCropsGroups(Set<CropsGroup> cropsGroups) {
        this.cropsGroups = cropsGroups;
    }

    public List<FieldInfoView> getFieldInfoViews() {
        return fieldInfoViews;
    }

    public void setFieldInfoViews(List<FieldInfoView> fieldInfoViews) {
        this.fieldInfoViews = fieldInfoViews;
    }
}

package com.decipher.agriculture.data.strategy;

import java.io.Serializable;

/**
 * Created by abhishek on 20/4/16.
 *
 * If enum declared outside of  the class public,default,strictfp
 *                  inside          public,default,strictfp,private,protected,
 */
public enum StrategyComparisonType implements Serializable {

    Potential_Profit(0, "Estimated Income"),
    Land_Used(1, "Land Used"),
    Capital_Used(2, "Working Capital Used"),
    Crop_Acreage_Per_Crop(3, "Crop Acreage Per Crop"),
    Percentage_PP_from_Single_Profitable_Crop(4, "% Estimated Income from Single Most Profitable Crop"),
    Percentage_PP_from_Two_Profitable_Crops(5, "% Estimated Income from Two Most Profitable Crops"),
    Percentage_PP_from_Forward_Sales(6, "% Estimated Income Forward Sold"),
    Percentage_PP_from_High_Risk_Crops(7, "% Estimated Income in High Risk Crops"),
    Percentage_Acreage_High_Risk_Crops(8, "% Acreage in High Risk Crops"),
    Percentage_PP_Conservation_Crops(9, "% Estimated Income Assigned to Conservation Crops"),
    Percentage_Acreage_Conservation_Crops(10, "% Acreage Assigned to Conservation Crops"),
    PP_Given_Min_Price_And_Yield(11, "Estimated Income Given Min Prices and Yields"),
    Return_on_Working_Capital(12, "Return on Working Capital");



    Integer bit;
    String comparisonStr;

    StrategyComparisonType(Integer bit, String comparisonStr){
        this.bit = bit;
        this.comparisonStr = comparisonStr;
    }

    public String getComparisonStr() {
        return comparisonStr;
    }

    public void setComparisonStr(String comparisonStr) {
        this.comparisonStr = comparisonStr;
    }

    public Integer getBit() {
        return bit;
    }

    public void setBit(Integer bit) {
        this.bit = bit;
    }

    public StrategyComparisonType getStrategyComparisonType(Integer ordinalValue){
        return StrategyComparisonType.values()[ordinalValue];
    }

    public String getStrategyComparisonType(){
        return toString();
    }

    @Override
    public String toString() {
        return super.toString().replaceAll("_", " ");
    }
}

package com.decipher.agriculture.service.strategy;

import java.util.List;
import java.util.Map;


import com.decipher.agriculture.bean.StrategyDataBean;
import com.decipher.agriculture.data.farm.FarmInfo;
import com.decipher.agriculture.data.strategy.FarmCustomStrategy;
import com.decipher.view.form.strategy.FarmCustomStrategyView;
import com.decipher.view.form.farmDetails.FarmInfoView;
import org.json.simple.JSONObject;

public interface FarmCustomStrategyService {

    /*boolean saveFarmCustomStrategy(int farmId,String[] resourceArray,String strategyName, Double potentialProfit, Double totalAcreage);*/
    int saveFarmCustomStrategy(int farmId, String[] resourceArray, String strategyName);

    /*boolean  saveFarmCustomStrategyForMultipalCrop(int farmId,String[] cropsArray,String[] cropContractArray,
                                                   String[] cropProposedArray,String[] cropsGroupArray,String strategyName,
                                                   Double potentialProfit, Double totalAcreage);*/
    int saveFarmCustomStrategyForMultipalCrop(int farmId, String[] cropsArray, String[] cropContractArray,
                                                  String[] cropProposedArray, String[] cropsGroupArray, String strategyName);

    boolean isFarmStrategyExitsWithName(String strategyName,int farmId);

    boolean isFarmStrategyExitsWithId(int strategyId,int farmId);

    boolean deleteStrategy(int strategyId);

    List<FarmCustomStrategyView> getDataForCustomStrategy(int farmId);

    /**
     * @added - Abhishek
     * @date - 21-12-2015
     */
    FarmCustomStrategyView getDataForCustomStrategy(int farmId, int strategyID);

    /**
     * @added - Abhishek
     * @date - 05-01-2016
     */
    StrategyDataBean getStrategyBaseValuesForStrategy(int strategyId, FarmInfoView farmInfoView);

    int saveAsBaseLineStrategy(FarmInfo farmInfo/*, Double potentialProfit, Double totalAcreage*/);

    int updateFarmStrategy(FarmCustomStrategy farmCustomStrategy);

    FarmCustomStrategyView getBaseLineStrategyForFarm(FarmInfo farminfo);

    int saveFarmCustomStrategy(FarmCustomStrategy farmCustomStrategy);

    Map<Integer, String> getCropDetailsForSelection(FarmInfoView farmInfoView, JSONObject baseLineOutputDetails);
}

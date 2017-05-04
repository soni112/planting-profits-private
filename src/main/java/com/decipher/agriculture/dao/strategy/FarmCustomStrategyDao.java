package com.decipher.agriculture.dao.strategy;

import java.util.List;

import com.decipher.agriculture.data.strategy.FarmCustomStrategy;
import com.decipher.agriculture.data.farm.FarmInfo;

public interface FarmCustomStrategyDao {

    /*boolean  saveFarmCustomStrategy(int farmId,String[] resourceArray,String strategyName, Double potentialProfit, Double totalAcreage);*/

    boolean isFarmStrategyExitsWithName(String strategyName, int farmId);

    boolean isFarmStrategyExitsWithId(int strategyId, int farmId);

    /*boolean saveFarmCustomStrategyForMultipleCrop(int farmId, String[] cropsArray, String[] cropContractArray, String[] cropProposedArray,
                                                  String[] cropsGroupArray, String strategyName, Double potentialProfit, Double totalAcreage);*/

    int saveFarmCustomStrategy(FarmCustomStrategy customStrategy);

    boolean deleteStrategy(int strategyId);

    FarmCustomStrategy getStrategyByNameByFarm(String strategyName, FarmInfo farmInfo);

    List<FarmCustomStrategy> getDataForCustomStrategy(int farmId);

    /**
     * @author - Abhishek
     * @date - 21-12-2015
     */
    FarmCustomStrategy getDataForCustomStrategy(int farmId, int strategyId);

//    boolean saveAsBaseLineStrategy(FarmInfo farmInfo, Double potentialProfit, Double totalAcreage);

    int updateFarmStrategy(FarmCustomStrategy farmCustomStrategy);

}

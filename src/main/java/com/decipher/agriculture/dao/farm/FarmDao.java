package com.decipher.agriculture.dao.farm;

import com.decipher.agriculture.data.farm.Farm;
import com.decipher.agriculture.data.farm.FarmInfo;

import java.util.List;

/**
 * Created by abhishek on 30/11/16.
 */
public interface FarmDao {

    boolean isFarmExitsWithNameAndUserId(String farmName, int accountId);

    Farm getFarmById(int farmId);

    int saveFarm(Farm farm);

    void deleteFarm(Farm farm);

    boolean deleteAllFarmsForUser(int accountId);

    boolean deleteFarmByIds(String farmIdsString);

    boolean updateFarm(Farm farm);

    FarmInfo getBaselineFarmDetails(int farmId);

    List<Farm> getAllFarmsForUser(int userId);


}

package com.decipher.agriculture.service.farm;

import com.decipher.agriculture.data.account.Account;
import com.decipher.agriculture.data.farm.Farm;
import com.decipher.view.form.farmDetails.FarmInfoView;

import java.util.List;

/**
 * Created by abhishek on 30/11/16.
 */
public interface FarmService {
    boolean isFarmExitsWithNameAndUserId(String farmName, int accountId);

    Farm getFarmById(int farmId);

    int saveFarm(Farm farm);

    void deleteFarm(Farm farm);

    boolean deleteAllFarmsForUser(Account account);

    boolean deleteFarmByIds(int[] farmIdArray);

    boolean updateFarm(Farm farm);

    FarmInfoView getBaselineFarmDetails(int farmId);

    List<Farm> getAllFarmsForUser(int userId);


}

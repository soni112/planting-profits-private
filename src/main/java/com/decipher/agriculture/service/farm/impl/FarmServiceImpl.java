package com.decipher.agriculture.service.farm.impl;

import com.decipher.agriculture.dao.farm.FarmDao;
import com.decipher.agriculture.data.account.Account;
import com.decipher.agriculture.data.farm.Farm;
import com.decipher.agriculture.data.farm.FarmInfo;
import com.decipher.agriculture.service.farm.FarmService;
import com.decipher.view.form.farmDetails.FarmInfoView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by abhishek on 30/11/16.
 */
@Service
public class FarmServiceImpl implements FarmService {
    @Autowired
    private FarmDao farmDao;

    @Override
    public boolean isFarmExitsWithNameAndUserId(String farmName, int accountId) {
        return farmDao.isFarmExitsWithNameAndUserId(farmName, accountId);
    }

    @Override
    public Farm getFarmById(int farmId) {
        return farmDao.getFarmById(farmId);
    }

    @Override
    public int saveFarm(Farm farm) {
        return farmDao.saveFarm(farm);
    }

    @Override
    public void deleteFarm(Farm farm) {
        Set<Farm> farmSet = new HashSet<>();
        farmSet.add(farm);
        String farmIdStringForFarm = getFarmIdStringForFarm(farmSet);
        farmDao.deleteFarmByIds(farmIdStringForFarm);
    }

    @Override
    public boolean deleteAllFarmsForUser(Account account) {
        String farmIdStringForFarm = getFarmIdStringForFarm(account.getFarmList());
        return farmDao.deleteFarmByIds(farmIdStringForFarm);
    }

    @Override
    public boolean deleteFarmByIds(int[] farmIdArray) {

        String farmIdsString = Arrays.toString(farmIdArray);
        farmIdsString = farmIdsString.substring(1, farmIdsString.length() - 1);

        return farmDao.deleteFarmByIds(farmIdsString);
    }

    @Override
    public boolean updateFarm(Farm farm) {
        return farmDao.updateFarm(farm);
    }

    @Override
    public FarmInfoView getBaselineFarmDetails(int farmId) {
        FarmInfo baselineFarmDetails = farmDao.getBaselineFarmDetails(farmId);
        FarmInfoView farmInfoView = null;
        if(baselineFarmDetails != null){
            farmInfoView = new FarmInfoView(baselineFarmDetails);
        }
        return farmInfoView;
    }

    @Override
    public List<Farm> getAllFarmsForUser(int userId) {
        return farmDao.getAllFarmsForUser(userId);
    }

    private String getFarmIdStringForFarm(Set<Farm> farmList){
        String farmIdsString = "";
        for (Farm farm : farmList) {
            farmIdsString += farm.getFarmId() + ", ";
        }
        farmIdsString = farmIdsString.trim();
        farmIdsString = farmIdsString.substring(0, farmIdsString.length() - 1);

        return farmIdsString;

    }

}

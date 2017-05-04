package com.decipher.agriculture.service.farm.impl;

import java.util.Arrays;
import java.util.List;

import com.decipher.agriculture.service.farm.FarmInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.dao.farm.FarmInfoDao;
import com.decipher.agriculture.data.farm.FarmInfo;
import com.decipher.util.PlantingProfitLogger;
import com.decipher.view.form.farmDetails.FarmInfoView;

@Repository
@Transactional
public class FarmInfoServiceImpl implements FarmInfoService {

    @Autowired
    private FarmInfoDao farmInfoDAO;

    @Override
    public boolean updateFarmInfo(FarmInfo farmInfo) {
        return farmInfoDAO.updateFarmInfo(farmInfo);
    }

    @Override
    public FarmInfoView getFarmInfoById(int id) {
        return new FarmInfoView(farmInfoDAO.getFarmInfoById(id));
    }

    @Override
    public FarmInfoView getFarmByIdAfterCheckForAccount(int id, int accountId) {
        return new FarmInfoView(farmInfoDAO.getFarmByIdAfterCheckForAccount(id, accountId));
    }

    @Override
    public FarmInfo getFarmInfoByIdForUpdate(int id) {
        return farmInfoDAO.getFarmInfoById(id);
    }

    @Override
    public FarmInfo getFarmoldById(int id, String CropTypes,
                                   String CropResourceUsage, String FieldInfos, String CropsGroup,
                                   String cropDualValues, String resourceDual, String groupDualValue) {
        return farmInfoDAO.getFarmInfoOldById(id, CropTypes, CropResourceUsage,
                FieldInfos, CropsGroup, cropDualValues, resourceDual,
                groupDualValue);
    }

    @Override
    public FarmInfo updateFarmInfo(int farmId, String irrigate_val,
                                   boolean evaluate_forward_sales_val,
                                   boolean evaluate_storage_sales_val,
                                   boolean evaluate_crop_insurance_val, String strategy,
                                   String total_land, String[] crop_str,
                                   String[] crop_information_detail, String[] cal_var_cost_crops,
                                   String[] option_crop_info_array,
                                   String[] optional_planting_date_array,
                                   String[] manage_resource_tbody_array,
                                   String[] crop_resource_usage_tbody_array,
                                   String[] crop_limits_table_tbody_array,
                                   String[] forward_sales_information_tbody_array,
                                   String[] plan_by_field_tbody_array,
                                   String[] field_choice_crop_tbody_row_array,
                                   String[] crop_resources_usages_difference_tbody_array,
                                   String field_difference_str, String[] crop_group_array,
                                   FarmInfo farmInfo, String additionalCropCostObj) {

        return farmInfoDAO.updateFarmInfo(farmInfo, farmId, irrigate_val,
                evaluate_forward_sales_val, evaluate_storage_sales_val,
                evaluate_crop_insurance_val, strategy, total_land, crop_str,
                crop_information_detail, cal_var_cost_crops,
                option_crop_info_array, optional_planting_date_array,
                manage_resource_tbody_array, crop_resource_usage_tbody_array,
                crop_limits_table_tbody_array,
                forward_sales_information_tbody_array,
                plan_by_field_tbody_array, field_choice_crop_tbody_row_array,
                crop_resources_usages_difference_tbody_array,
                field_difference_str, crop_group_array, additionalCropCostObj);

    }

    @Override
    public boolean deleteAllFarmRecords(int farmId) {
        return farmInfoDAO.deleteAllFarmRecords(farmId);
    }

    @Override
    public boolean deleteAllSelectedFarms(int[] farmIdsArray) {
        String farmIdsString = Arrays.toString(farmIdsArray);
        farmIdsString = farmIdsString.substring(1, farmIdsString.length() - 1);

        return farmInfoDAO.deleteAllSelectedFarms(farmIdsString);
    }

    @Override
    public boolean deleteAllFarms(int acountId) {
        List<Integer> farmIds = farmInfoDAO.getAllFarmIds(acountId);
        String farmIdsString = "";
        PlantingProfitLogger.info("farmIds------------->>>>>>>>>>" + farmIds.size());

        for (int i : farmIds) {
            farmIdsString += i + ",";
        }
        farmIdsString = farmIdsString.substring(0, farmIdsString.length() - 1);
        return farmInfoDAO.deleteAllSelectedFarms(farmIdsString);
    }

    @Override
    public FarmInfoView getFarmByFarmIdAndUserId(int farmId, int userId) {
        FarmInfo farmInfo = farmInfoDAO.getFarmByFarmIdAndUserId(farmId, userId);
        FarmInfoView farmInfoView = null;
        if (farmInfo != null) {
            farmInfoView = new FarmInfoView(farmInfo);
        }
        return farmInfoView;
    }

    @Override
    public boolean updateFarmDetails(int userId, int farmId,
                                     String farmName) {

        return farmInfoDAO.updateFarmDetails(userId, farmId, farmName);
    }

    @Override
    public FarmInfo saveFarmInfo(int farmId, String irrigate_val,
                                 boolean evaluate_forward_sales_val,
                                 boolean evaluate_storage_sales_val,
                                 boolean evaluate_crop_insurance_val, String strategy,
                                 String total_land, FarmInfo farmInfo, String[] crop_str,
                                 String[] crop_information_detail, String[] cal_var_cost_crops,
                                 String[] option_crop_info_array,
                                 String[] optional_planting_date_array,
                                 String[] manage_resource_tbody_array,
                                 String[] crop_resource_usage_tbody_array,
                                 String[] crop_limits_table_tbody_array,
                                 String[] forward_sales_information_tbody_array,
                                 String[] plan_by_field_tbody_array,
                                 String[] field_choice_crop_tbody_row_array,
                                 String[] crop_resources_usages_difference_tbody_array,
                                 String field_difference_str, String[] crop_group_array, String additionalCropCostObj) {

        return farmInfoDAO.saveFarm(farmId, irrigate_val,
                evaluate_forward_sales_val, evaluate_storage_sales_val,
                evaluate_crop_insurance_val, strategy, total_land, farmInfo,
                crop_str, crop_information_detail, cal_var_cost_crops,
                option_crop_info_array, optional_planting_date_array,
                manage_resource_tbody_array, crop_resource_usage_tbody_array,
                crop_limits_table_tbody_array,
                forward_sales_information_tbody_array,
                plan_by_field_tbody_array, field_choice_crop_tbody_row_array,
                crop_resources_usages_difference_tbody_array,
                field_difference_str, crop_group_array, additionalCropCostObj);
    }

    @Override
    public void initializeLazy(FarmInfo farmInfo) {
        farmInfoDAO.initializeLazy(farmInfo);
    }
}

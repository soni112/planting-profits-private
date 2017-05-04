package com.decipher.agriculture.service.farm;

import java.util.List;

import com.decipher.agriculture.data.farm.FarmInfo;
import com.decipher.view.form.farmDetails.FarmInfoView;

public interface FarmInfoService {


    boolean updateFarmInfo(FarmInfo farmInfo);

    FarmInfoView getFarmInfoById(int id);

    FarmInfo getFarmInfoByIdForUpdate(int id);

    boolean deleteAllFarmRecords(int farmId);

    boolean deleteAllSelectedFarms(int[] farmIdsArray);

    boolean deleteAllFarms(int accountId);

    FarmInfoView getFarmByFarmIdAndUserId(int farmId, int userId);

    boolean updateFarmDetails(int id, int farmId, String farmName);

    FarmInfoView getFarmByIdAfterCheckForAccount(int farmId, int id);

    FarmInfo getFarmoldById(int id, String CropTypes,
                            String CropResourceUsage, String FieldInfos, String CropsGroup,
                            String cropDualValues, String resourceDual, String groupDualValue);

    FarmInfo updateFarmInfo(int farmId, String irrigate_val,
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
                            FarmInfo farmInfo, String additionalCropCostObj);

    FarmInfo saveFarmInfo(int farmId, String irrigate_val,
                          boolean evaluate_forward_sales_val,
                          boolean evaluate_storage_sales_val,
                          boolean evaluate_crop_insurance_val, String strategy,
                          String total_land, FarmInfo farmInfoold, String[] crop_str,
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
                          String field_difference_str, String[] crop_group_array, String additionalCropCostObj);

    void initializeLazy(FarmInfo farmInfo);
}

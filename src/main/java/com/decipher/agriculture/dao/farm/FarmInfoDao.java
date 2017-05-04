package com.decipher.agriculture.dao.farm;

import java.util.List;

import com.decipher.agriculture.data.farm.FarmInfo;

public interface FarmInfoDao {

	boolean updateFarmInfo(FarmInfo farmInfo);

	FarmInfo getFarmInfoById(int id);

//	List<Integer> getAllFarmIds(int userId, int[] farmIds);

	List<Integer> getAllFarmIds(int userId);

	FarmInfo getFarmByFarmIdAndUserId(int farmId, int userId);

	boolean updateFarmDetails(int userId, int farmId, String farmName);

	FarmInfo getFarmByIdAfterCheckForAccount(int id, int accountId);

	boolean deleteAllFarmRecords(int farmId);

	boolean deleteAllSelectedFarms(String farmIdsString);

	FarmInfo getFarmInfoOldById(int id, String CropTypes,
								String CropResourceUsage, String FieldInfos, String CropsGroup,
								String cropDualValues, String resourceDual, String groupDualValue);

	FarmInfo updateFarmInfo(FarmInfo farmInfo, int farmId, String irrigate_val,
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
							String field_difference_str, String[] crop_group_array, String additionalCropCostObj);

	FarmInfo saveFarm(int farmId, String irrigate_val,
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
					  String field_difference_str, String[] crop_group_array, String additionalCropCostObj);

	void initializeLazy(FarmInfo farmInfo);

}

package com.decipher.agriculture.dao.farmOutput;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.decipher.agriculture.bean.OutputBeanForStrategy;

import com.decipher.agriculture.bean.CropBeanForOutput;
import com.decipher.agriculture.data.farm.CropType;
import com.decipher.agriculture.data.farm.CropsGroup;
import com.decipher.agriculture.data.farm.FarmInfo;
import com.decipher.agriculture.data.farmOutput.FarmOutputDetails;
import com.decipher.agriculture.data.farmOutput.FarmOutputDetailsForField;
import com.decipher.view.form.farmDetails.CropResourceUsageFieldVariancesView;
import com.decipher.view.form.farmDetails.CropResourceUsageView;
import com.decipher.view.form.farmDetails.CropTypeView;
import com.decipher.view.form.farmDetails.FarmOutputDetailsForFieldView;
import com.decipher.view.form.farmDetails.FarmOutputDetailsView;
import com.decipher.view.form.farmDetails.FieldInfoView;

public interface FarmOutputCalculationDao {

	/**
	 * @description - calculation of output stats for farm
	 */
	void calculateFarmOutputStatistics(FarmInfo farmInfo);

	void calculateFarmOutputStatisticsForField(FarmInfo farmInfo);

	void calculateFarmOutputStatisticsForAcres(FarmInfo farmInfo);

	/**
	 * @desciption - getting all output details for farm
	 */
	List<FarmOutputDetails> getAllFarmOutputDetailsByFarm(int farmId);

	List<FarmOutputDetailsForField> getAllFarmOutputDetailsForFieldByFarm(int farmId);

	/**
	 * @author - Abhishek
	 * @date - 21-12-2015
	 * @description - Overloaded Functions for getting output data
	 */
	/* ********************** Start ********************** */

	List<Object> calculateFarmOutputStatistics(OutputBeanForStrategy outputBeanForStrategy);

	List<Object> calculateFarmOutputStatisticsForField(OutputBeanForStrategy outputBeanForStrategy);

	List<Object> calculateFarmOutputStatisticsForAcres(OutputBeanForStrategy outputBeanForStrategy);

	List<FarmOutputDetails> getAllFarmOutputDetailsByFarm(OutputBeanForStrategy outputBeanForStrategy);

	List<FarmOutputDetailsForField> getAllFarmOutputDetailsForFieldByFarm(OutputBeanForStrategy outputBeanForStrategy);

	/* ********************** End ********************** */

	void saveFarmOutputDetails(FarmOutputDetails farmOutputDetails);

	int saveFarmOutputDetailsForField(FarmOutputDetailsForField farmOutputDetailsForField);



	List<FarmOutputDetails> calculateAcresForEachCropForAcres(
            List<CropBeanForOutput> cropBeanForOutputList, FarmInfo farmInfo,
            List<CropResourceUsageView> resourceUsageViews,
            Set<CropsGroup> cropsGroups, boolean updateFlag);

	Map<String, HashMap<String, String>> calculateUsedAndUnusedResourcesForAcre(
            List<CropResourceUsageView> resourceUsageViews,
            List<FarmOutputDetailsView> farmOutputDetailsViewList,
            List<CropResourceUsageFieldVariancesView> resourceUsageVariances);

	int calculatePotentialProfitForAcre(
            List<FarmOutputDetailsView> farmOutputDetails);

	org.json.simple.JSONObject createJSONObjectForGraphForAcre(List<FarmOutputDetailsView> farmOutputDetails, String unusedLand);



	List<FarmOutputDetailsForField> calculateAcresForEachCropForField(
            List<CropBeanForOutput> cropBeanForOutputList,
            List<CropResourceUsageView> resourceUsageViews,
            List<FieldInfoView> fieldInfoViews, Set<CropsGroup> cropsGroups);

	Map<String, HashMap<String, String>> calculateUsedAndUnusedResourcesForField(
            List<CropResourceUsageView> resourceUsageViews,
            List<FarmOutputDetailsForFieldView> farmOutputDetailsForFieldViews,
            List<CropResourceUsageFieldVariancesView> resourceUsageVariances);

	int calculatePotentialProfitForField(
            List<FarmOutputDetailsForFieldView> farmOutputDetailsForFieldViews);

	Map<String, Object> createJSONObjectAndMapObjectForGraphForField(
            List<CropTypeView> cropTypeView,
            List<FarmOutputDetailsForFieldView> farmOutputDetailsForFieldViews,
            String unusedLand);




	List<CropBeanForOutput> getCropBeanForCalculation(
            List<CropType> cropTypeList,
            List<CropResourceUsageView> resourceUsageViews);


	Double calculateProfit(double expectedYield, double expectedprice, double minAcres, double variableProductionCost);
}
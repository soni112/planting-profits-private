package com.decipher.agriculture.farmreport;

/**
 * @author Abhishek
 * @date 29-11-2015
 */
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.decipher.agriculture.data.farm.PlanByStrategy;
import com.decipher.agriculture.service.farmDetails.FarmDetailsContainerService;
import com.decipher.agriculture.service.scenario.ScenarioService;
import com.decipher.agriculture.service.strategy.FarmCustomStrategyService;
import com.decipher.config.ApplicationConfig;
import com.decipher.view.form.farmDetails.CropResourceUsageView;
import com.decipher.view.form.farmDetails.CropTypeView;
import com.decipher.view.form.farmDetails.FarmOutputDetailsView;
import com.decipher.view.form.scenario.FarmStrategyScenarioView;
import com.decipher.view.form.strategy.FarmCustomStrategyView;
import com.decipher.view.form.farmDetails.FarmInfoView;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.poi.util.ArrayUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@SuppressWarnings("unchecked")
public class ReportDataPage2 {

	/**
	 * @chanegd - Abhishek
	 * @date - 11-01-2016
	 * @desc - Only data objects to be used are used not using any service to populate data objects
	 */
	private JSONObject baseSelectedStrategyOutputDetails;
	private Map<FarmCustomStrategyView, JSONObject> selectedStrategiesOutputDetailsJsonObjectList;
	private int totalScenarioCount;

	public ReportDataPage2(JSONObject baseSelectedStrategyOutputDetails, Map<FarmCustomStrategyView, JSONObject> selectedStrategiesOutputDetailsJsonObjectList, int totalScenarioCount){
		/**
		 * @chanegd - Abhishek
		 * @date - 11-01-2016
		 * @desc - Only data objects to be used are used not using any service to populate data objects
		 */
		this.baseSelectedStrategyOutputDetails = baseSelectedStrategyOutputDetails;
		this.selectedStrategiesOutputDetailsJsonObjectList = selectedStrategiesOutputDetailsJsonObjectList;
		this.totalScenarioCount = totalScenarioCount;

	}

	public JSONObject getBaseSelectedStrategyOutputDetails() {
		return baseSelectedStrategyOutputDetails;
	}

	/**
	 *@return List with all details of the crops for the farm
	 */
	public List<CropTypeView> getCropDetails(){
		/**
		 * @chanegd - Abhishek
		 * @date - 11-01-2016
		 * @desc - Getting from bean instead of getting from service which require a lot of resource and server processing
		 */
		return (List<CropTypeView>)baseSelectedStrategyOutputDetails.get("cropTypeView");
		/*return cropTypeService.getAllCropByFarm(farmInfoView.getId());*/

	}

	/**
	 * @return List with all data for all selected strategies
	 */
	public List<JSONObject> getStrategiesDataForFarm() {
		/**
		 * @chanegd - Abhishek
		 * @date - 11-01-2016
		 * @desc - Using data supplied form controller instead of getting from service which require a lot of resource and server processing
		 */
		List<JSONObject> outputList = new ArrayList<>();
		Set<Map.Entry<FarmCustomStrategyView, JSONObject>> entries = selectedStrategiesOutputDetailsJsonObjectList.entrySet();
		for (Map.Entry<FarmCustomStrategyView, JSONObject> entry : entries) {
			JSONObject outputDetails = entry.getValue();

			outputList.add(outputDetails);
		}


		return outputList;

	}

	/**
	 * @return List with all resource usage details
	 */
	public List<CropResourceUsageView> getResourceUsageForFarm(){
		/**
		 * @chanegd - Abhishek
		 * @date - 11-01-2016
		 * @desc - Getting from bean instead of getting from service which require a lot of resource and server processing
		 */
		return (List<CropResourceUsageView>)baseSelectedStrategyOutputDetails.get("resourceList");
		/*return cropResourceUsageService.getAllCropResourceUsageByFarmId(farmInfoView.getId());*/
	}

	public FarmInfoView getFarmInfoView(){
		return (FarmInfoView)baseSelectedStrategyOutputDetails.get("farmInfoView");
	}


	public int getTotalScenarioCount() {
		return totalScenarioCount;
	}

	public List<FarmStrategyScenarioView> getAllScenarios(){
		FarmDetailsContainerService farmDetailsContainerService = ApplicationConfig.getApplicationContext().getBean(FarmDetailsContainerService.class);
		Set<FarmCustomStrategyView> farmCustomStrategyViewSet = farmDetailsContainerService.getAllStrategiesForFarm(getFarmInfoView().getFarmInfo().getFarm());
		List<FarmStrategyScenarioView> farmStrategyScenarioViewList = new ArrayList<>();
		for (FarmCustomStrategyView customStrategyView : farmCustomStrategyViewSet) {
			farmStrategyScenarioViewList.addAll(farmDetailsContainerService.getAllScenarioList(getFarmInfoView(), customStrategyView));
		}

		return farmStrategyScenarioViewList;
	}

	public Map<String, String> getRiskAndConservationAnalysis(JSONObject baseSelectedOutpuDetailsJsonObject) {

		FarmInfoView farmInfoView = getFarmInfoView();

		Map<String, String> riskAnalysisMap = new HashMap<>();


		double landUnderConservation = 0.0, incomeUnderConservation = 0.0, landUnderRisk = 0.0, incomeUnderRisk = 0.0;

		DecimalFormat formatter = new DecimalFormat("#.00");

		if (farmInfoView.getStrategy().equals(PlanByStrategy.PLAN_BY_ACRES)) {

			for (FarmOutputDetailsView farmOutputDetails : (List<FarmOutputDetailsView>) baseSelectedOutpuDetailsJsonObject.get("farmOutputDetails")) {
				if(farmOutputDetails.getCropTypeView().getConservation_Crop().equalsIgnoreCase("true")
						&& farmOutputDetails.getCropTypeView().getHiRiskCrop().equalsIgnoreCase("true")){
					landUnderConservation += farmOutputDetails.getUsedAcresPercentage();
					incomeUnderConservation += farmOutputDetails.getUsedCapitalPercentage();
					landUnderRisk += farmOutputDetails.getUsedAcresPercentage();
					incomeUnderRisk += Double.parseDouble(formatter.format((farmOutputDetails.getProfitDouble() / Double.parseDouble(SectionOnePDFGenerator.getEstimatedIncome().replaceAll("\\,", ""))) * 100));
				} else if (farmOutputDetails.getCropTypeView().getConservation_Crop().equalsIgnoreCase("true")) {
					landUnderConservation += farmOutputDetails.getUsedAcresPercentage();
					incomeUnderConservation += farmOutputDetails.getUsedCapitalPercentage();
				} else if (farmOutputDetails.getCropTypeView().getHiRiskCrop().equalsIgnoreCase("true")) {
					landUnderRisk += farmOutputDetails.getUsedAcresPercentage();
					incomeUnderRisk += Double.parseDouble(formatter.format((farmOutputDetails.getProfitDouble() / Double.parseDouble(SectionOnePDFGenerator.getEstimatedIncome().replaceAll("\\,", ""))) * 100));
				}
			}


		} else if (farmInfoView.getStrategy().equals(PlanByStrategy.PLAN_BY_FIELDS)) {
			Map<String, String> hashMapForAcre = (Map<String, String>) baseSelectedOutpuDetailsJsonObject.get("hashMapForAcre");
			Map<String, String> hashMapForProfit = (Map<String, String>) baseSelectedOutpuDetailsJsonObject.get("hashMapForProfit");
			List<CropTypeView> cropTypeViewList = (List<CropTypeView>) baseSelectedOutpuDetailsJsonObject.get("cropTypeView");

			Set<String> keySet = hashMapForAcre.keySet();

			for (String cropKey : keySet) {
				for (CropTypeView cropTypeView : cropTypeViewList) {
					if(cropTypeView.getSelected()
							&& cropTypeView.getConservation_Crop().equalsIgnoreCase("true")
							&& cropTypeView.getHiRiskCrop().equalsIgnoreCase("true")
							&& cropKey.contains(cropTypeView.getCropName())){
						String land, income;
						land = hashMapForAcre.get(cropKey);
						landUnderConservation += Double.parseDouble(land.substring(land.indexOf('(') + 1, land.indexOf('%')).replaceAll("\\,", ""));
						income = hashMapForProfit.get(cropKey);
						incomeUnderConservation += Double.parseDouble(income.substring(income.indexOf('(') + 1, income.indexOf('%')).replaceAll("\\,", ""));

						land = hashMapForAcre.get(cropKey);
						landUnderRisk += Double.parseDouble(land.substring(land.indexOf('(') + 1, land.indexOf('%')).replaceAll("\\,", ""));
						income = hashMapForProfit.get(cropKey);
						incomeUnderRisk += Double.parseDouble(income.substring(income.indexOf('(') + 1, income.indexOf('%')).replaceAll("\\,", ""));
					} else if (cropTypeView.getSelected()
							&& cropTypeView.getConservation_Crop().equalsIgnoreCase("true")
							&& cropKey.contains(cropTypeView.getCropName())) {
						String land = hashMapForAcre.get(cropKey);
						landUnderConservation += Double.parseDouble(land.substring(land.indexOf('(') + 1, land.indexOf('%')).replaceAll("\\,", ""));
						String income = hashMapForProfit.get(cropKey);
						incomeUnderConservation += Double.parseDouble(income.substring(income.indexOf('(') + 1, income.indexOf('%')).replaceAll("\\,", ""));
					} else if (cropTypeView.getSelected()
							&& cropTypeView.getHiRiskCrop().equalsIgnoreCase("true")
							&& cropKey.contains(cropTypeView.getCropName())) {
						String land = hashMapForAcre.get(cropKey);
						landUnderRisk += Double.parseDouble(land.substring(land.indexOf('(') + 1, land.indexOf('%')).replaceAll("\\,", ""));
						String income = hashMapForProfit.get(cropKey);
						incomeUnderRisk += Double.parseDouble(income.substring(income.indexOf('(') + 1, income.indexOf('%')).replaceAll("\\,", ""));
					}
				}
			}

		}

		riskAnalysisMap.put("landUnderConservation", formatter.format(landUnderConservation));
		riskAnalysisMap.put("incomeUnderConservation", formatter.format(incomeUnderConservation));
		riskAnalysisMap.put("landUnderRisk", formatter.format(landUnderRisk));
		riskAnalysisMap.put("incomeUnderRisk", formatter.format(incomeUnderRisk));

		return riskAnalysisMap;
	}

	public JSONArray getScenarioDetails(FarmCustomStrategyView farmCustomStrategyView){
		ScenarioService scenarioService = ApplicationConfig.getApplicationContext().getBean(ScenarioService.class);

		List<FarmStrategyScenarioView> farmStrategyScenarioViewList = getAllScenarios();

		JSONArray outputDetails = new JSONArray();
		for (FarmStrategyScenarioView farmStrategyScenarioView : farmStrategyScenarioViewList) {
			int[] ar = {farmCustomStrategyView.getId()};
			JSONObject scenarioComparisonDetails = scenarioService.getScenarioComparisonDetails(getFarmInfoView(), farmStrategyScenarioView.getScenarioId(), ar);
			JSONArray details = (JSONArray) scenarioComparisonDetails.get("outputDetails");
			outputDetails.addAll(details);
		}

		return outputDetails;
	}

}
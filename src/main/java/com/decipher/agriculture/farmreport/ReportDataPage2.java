package com.decipher.agriculture.farmreport;

/**
 * @author Abhishek
 * @date 29-11-2015
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.decipher.view.form.farmDetails.CropResourceUsageView;
import com.decipher.view.form.farmDetails.CropTypeView;
import com.decipher.view.form.strategy.FarmCustomStrategyView;
import com.decipher.view.form.farmDetails.FarmInfoView;
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
}

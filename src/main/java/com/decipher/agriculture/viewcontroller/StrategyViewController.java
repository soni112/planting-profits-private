package com.decipher.agriculture.viewcontroller;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.decipher.agriculture.data.farm.Farm;
import com.decipher.agriculture.data.farm.FarmInfo;
import com.decipher.agriculture.data.farm.PlanByStrategy;
import com.decipher.agriculture.data.strategy.StrategyComparisonType;
import com.decipher.agriculture.service.farm.FarmInfoService;
import com.decipher.agriculture.service.farm.FarmService;
import com.decipher.agriculture.service.farmDetails.FarmDetailsContainerService;
import com.decipher.agriculture.service.strategyComparison.StrategyComparisonService;
import com.decipher.util.AgricultureStandardUtils;
import com.decipher.util.JsonResponse;
import com.decipher.util.PlantingProfitLogger;

import com.decipher.view.form.farmDetails.CropTypeView;
import com.decipher.view.form.strategy.FarmCustomStrategyView;
import com.decipher.view.form.farmDetails.FarmOutputDetailsView;
import org.codehaus.jettison.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.decipher.agriculture.service.strategy.FarmCustomStrategyService;
import com.decipher.view.form.farmDetails.FarmInfoView;

import javax.servlet.http.HttpSession;

@Controller
@SuppressWarnings("unchecked")
public class StrategyViewController {

	@Autowired
	private HttpSession httpSessionService;
	@Autowired
	private StrategyComparisonService strategyComparisonService;
	@Autowired
	private FarmCustomStrategyService farmCustomStrategyService;
	@Autowired
	private FarmDetailsContainerService farmDetailsContainerService;
	@Autowired
	private FarmService farmService;
	@Autowired
	private FarmOutPutViewController farmOutPutViewController;

	@Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN", "ROLE_PROFESSIONAL", "ROLE_GROWER", "ROLE_STUDENT"})
	@RequestMapping(value = "/view-farm-strategy.htm")
	public Object viewFarmStrategy(@RequestParam(value = "farmId", required = false) int farmId) throws JSONException {

		httpSessionService.removeAttribute("cropDescription");
		httpSessionService.removeAttribute("strategyContainer");
		httpSessionService.removeAttribute("allStrategyOutputDetailsContainer");
		httpSessionService.removeAttribute("strategyIdArray");
       int strategyIdArray[]=null;



        Map<String, Object> model = new HashMap<String, Object>();
		PlantingProfitLogger.info("User requesting for view-farm-strategy.htm page .... ");

		Farm farm = farmService.getFarmById(farmId);

		if(farm != null && !farm.getSaveFlag()){
			return "redirect:farm-info.htm?farmId=" + farm.getFarmId();
		}


		FarmInfoView farmInfoView = farmService.getBaselineFarmDetails(farmId);
		String page;
		if (farmInfoView != null) {
			model.put("farmInfoView", farmInfoView);
			model.put("farm", farm);

			JSONObject baseLineOutputDetails = farmDetailsContainerService.getBaseLineDetails(farm);

			model.put("cropTypeViewList", baseLineOutputDetails.get("cropTypeView"));
			List<FarmCustomStrategyView> farmCustomStrategyViewListView = farmCustomStrategyService.getDataForCustomStrategy(farm.getFarmId());
				if (farmCustomStrategyViewListView.size() > 0) {
				model.put("farmCustomStrategyList", farmCustomStrategyViewListView);
				model.put("strategyComparisonType", StrategyComparisonType.values());
				model.put("cropDetailsForSelection", farmCustomStrategyService.getCropDetailsForSelection(farmInfoView, baseLineOutputDetails));
			}

			page = "view-farm-strategy";
		} else {
			PlantingProfitLogger.info("User requesting for farm.htm page .... farmId " + farmId);
			page = "home";
		}


        return new ModelAndView(page, "model", model);

	}

	/**
	 * @added - Abhishek
	 * @date - 27-11-2015
	 * @return - status for custom strategy present for loggedIn user 
	 */
	@Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN", "ROLE_PROFESSIONAL", "ROLE_GROWER", "ROLE_STUDENT"})
	@RequestMapping(value = "/checkCustomStrategyForUser", method = RequestMethod.GET)
	public @ResponseBody JsonResponse check_custom_Strategy_For_User(@RequestParam(value = "farmId", required = false) int farmId){

		List<FarmCustomStrategyView> farmCustomStrategyViewList = farmCustomStrategyService.getDataForCustomStrategy(farmId);

		JsonResponse jsonResponse = new JsonResponse();
		
		if(farmCustomStrategyViewList.isEmpty()){
			jsonResponse.setStatus(JsonResponse.RESULT_FAILED);
		} else {
			jsonResponse.setStatus(JsonResponse.RESULT_SUCCESS);
		}
		
		return jsonResponse;
		
	}

	@RequestMapping(value = "getStrategyForFarm", method = RequestMethod.POST)
	public @ResponseBody JsonResponse getStrategyForFarm(@RequestParam(value = "farmId", required = false) int farmId,
														 @RequestParam(value = "strategyIdArray[]", required = false) int[] strategyIdArray){

        httpSessionService.removeAttribute ( "strategyIdArray" );
        JsonResponse jsonResponse = new JsonResponse ();

        FarmInfoView farmInfoView = farmService.getBaselineFarmDetails ( farmId );

        JSONObject strategyComparisonDetails;
        ArrayList list = new ArrayList ();

        try {
            strategyComparisonDetails = strategyComparisonService.getStrategyComparisonDetails ( farmInfoView, strategyIdArray );
            Map <String, JSONArray> granularComparisonResult = strategyComparisonService.getGranularComparisonResult ( farmInfoView, strategyIdArray );
            JSONArray jsonArrayForHighRisk = granularComparisonResult.get ( "jsonArrayForHighRiskCropForGranular" );
            JSONArray jsonArrayForConservationCrop = granularComparisonResult.get ( "jsonArrayForConservationCropForGranular" );
            JSONArray jsonArrayStrategyData = granularComparisonResult.get ( "DataForStrategy" );
            JSONArray jsonArrayForStrategy = granularComparisonResult.get ( "jsonArrayForStrategy" );

            HashMap <String, String> detailsDataForGauge;
            String[] returnWorkingCapitalArray = new String[jsonArrayForStrategy.size ()];
			Integer[] EstimateIncomeArray = new Integer[jsonArrayForStrategy.size ()];

			for (int i = 0; i < jsonArrayForStrategy.size (); i++) {
                JSONObject jsonObjectStrategyData = (JSONObject) jsonArrayStrategyData.get ( i );
                returnWorkingCapitalArray[i] = (String) jsonObjectStrategyData.get ( "returnWorkingCapital" );
				EstimateIncomeArray[i] = (Integer) jsonObjectStrategyData.get ( "EstimateIncome" );
            }
            for (int i = 0; i < returnWorkingCapitalArray.length; i++) {
                for (int l = i + 1; l < returnWorkingCapitalArray.length; l++) {
                    if (Double.valueOf ( AgricultureStandardUtils.removeAllCommas ( returnWorkingCapitalArray[i] ) ) < Double.valueOf ( AgricultureStandardUtils.removeAllCommas ( returnWorkingCapitalArray[l] ) )) {
                        String temp = returnWorkingCapitalArray[i];
                        returnWorkingCapitalArray[i] = returnWorkingCapitalArray[l];
                        returnWorkingCapitalArray[l] = temp;
                    }

                }
            }
			for (int i = 0; i < EstimateIncomeArray.length; i++) {
				for (int l = i + 1; l < EstimateIncomeArray.length; l++) {
					if ( EstimateIncomeArray[i]  < EstimateIncomeArray[l]) {
						int temp = EstimateIncomeArray[i];
						EstimateIncomeArray[i] = EstimateIncomeArray[l];
						EstimateIncomeArray[l] = temp;
					}

				}
			}
            for (int i = 0; i < jsonArrayForStrategy.size (); i++) {
                detailsDataForGauge = new HashMap <> ();
                FarmCustomStrategyView farmCustomStrategyView = (FarmCustomStrategyView) jsonArrayForStrategy.get ( i );
                JSONObject jsonObjectForHighRisk = (JSONObject) jsonArrayForHighRisk.get ( i );
                JSONObject jsonObjectConservationCrop = (JSONObject) jsonArrayForConservationCrop.get ( i );
                JSONObject jsonObjectStrategyData = (JSONObject) jsonArrayStrategyData.get ( i );

                detailsDataForGauge.put ( "id", farmCustomStrategyView.getId ().toString () );
                detailsDataForGauge.put ( "strategyName", farmCustomStrategyView.getStrategyName () );
                detailsDataForGauge.put ( "EstimateIncome", jsonObjectStrategyData.get ( "EstimateIncome" ).toString () );
                detailsDataForGauge.put ( "ReturnWorkingCapital", jsonObjectStrategyData.get ( "returnWorkingCapital" ).toString () );
                String returnWorking = (String) jsonObjectStrategyData.get ( "returnWorkingCapital" );
				int EstimateIncome= (Integer) jsonObjectStrategyData.get ( "EstimateIncome" );

				int countReturnWorking = 0;
                for (String returnWorkingCapital :
                        returnWorkingCapitalArray) {
					countReturnWorking++;
                    if (returnWorkingCapital.equals ( returnWorking )) {
                        detailsDataForGauge.put ( "countReturnWorking", "" + countReturnWorking );
                        break;
                    }
                }
				int countEstimateIncome = 0;
				for (int EstimateIncomeArrayValue :
						EstimateIncomeArray) {
					countEstimateIncome++;
					if (EstimateIncomeArrayValue== EstimateIncome) {
						detailsDataForGauge.put ( "countEstimateIncome", "" + countEstimateIncome );
						break;
					}
				}
                JSONArray jsonArrayDetails = (JSONArray) jsonObjectForHighRisk.get ( "details" );
                JSONArray jsonArrayConservationDetails = (JSONArray) jsonObjectConservationCrop.get ( "details" );
                Map <String, String> hashMapForAverageInConservationCrop = (Map <String, String>) jsonArrayConservationDetails.get ( 1 );
                Map <String, String> hashMapForEstIncomeInOneCrop = (Map <String, String>) jsonArrayDetails.get ( 2 );
                detailsDataForGauge.put ( "EstIncomeInOneCrop", hashMapForEstIncomeInOneCrop.get ( "amount" ) );
                Map <String, String> hashMapForEstIncomeInForwardSale = (Map <String, String>) jsonArrayDetails.get ( 4 );
                detailsDataForGauge.put ( "EstIncomeInForwardSale", hashMapForEstIncomeInForwardSale.get ( "amount" ) );
                detailsDataForGauge.put ( "AverageInConservationCrop", hashMapForAverageInConservationCrop.get ( "amount" ) );
                list.add ( detailsDataForGauge );
                strategyComparisonDetails.put ( "jsonArrayForGaugeChart", list );

            }
        } catch (JSONException e) {
            strategyComparisonDetails = new JSONObject ();
            PlantingProfitLogger.error ( e );
        }
        jsonResponse.setResult ( strategyComparisonDetails );
        jsonResponse.setStatus ( JsonResponse.RESULT_SUCCESS );

        return jsonResponse;
    }

    @RequestMapping(value = "getVarianceGraphDetails", method = RequestMethod.POST)
    public @ResponseBody
    JsonResponse getVarianceGraphDetails(@RequestParam(value = "farmId", required = false) int farmId,
                                         @RequestParam(value = "strategyIdArray[]", required = false) int[] strategyIdArray,
                                         @RequestParam(value = "cropPriceSelection[]", required = false) int[] cropPriceSelection,
                                         @RequestParam(value = "cropYieldSelection[]", required = false) int[] cropYieldSelection,
                                         @RequestParam(value = "cropProductionCostSelection[]", required = false) int[] cropProductionCostSelection,
                                         @RequestParam(value = "rangeValues", required = false) String rangeValues) throws JSONException {
        JsonResponse response = new JsonResponse ();

		JSONParser jsonParser = new JSONParser();
		JSONObject rangeValuesObject = new JSONObject();
		try {
			rangeValuesObject = (JSONObject)jsonParser.parse(rangeValues);
		} catch (ParseException e) {
			PlantingProfitLogger.error(e);
		}

		FarmInfoView farmInfoView = farmService.getBaselineFarmDetails(farmId);


		JSONArray varianceGraphCustomisedDetails = strategyComparisonService.getVarianceGraphCustomisedDetails(farmInfoView, strategyIdArray, cropPriceSelection, cropYieldSelection, cropProductionCostSelection, rangeValuesObject);

		if(varianceGraphCustomisedDetails.size() > 0){
			response.setStatus(JsonResponse.RESULT_SUCCESS);
			response.setResult(varianceGraphCustomisedDetails);
		} else {
			response.setStatus(JsonResponse.RESULT_FAILED);
		}

		return response;
	}



	@RequestMapping(value = "getStrategyComparisonChartData", method = RequestMethod.POST)
	public @ResponseBody JsonResponse getStrategyComparisonChartData(@RequestParam(value="xAxisValue", required = false) int xAxisValue,
																	 @RequestParam(value="yAxisValue", required = false) int yAxisValue,
																	 @RequestParam(value="farmId", required = false) int farmId,
																	 @RequestParam(value="strategyArray", required = false) int[] strategyIdArray) throws JSONException {

		httpSessionService.removeAttribute("strategyIdArray");
		httpSessionService.setAttribute("strategyIdArray", strategyIdArray);

		JsonResponse response = new JsonResponse();

		FarmInfoView farmInfoView = farmService.getBaselineFarmDetails(farmId);

		JSONObject graphComparisonResult = strategyComparisonService.getGraphComparisonResult(xAxisValue, yAxisValue, farmInfoView, strategyIdArray);

//		JSONObject graphComparisonResult = strategyComparisonService.getGraphComparisonResult(StrategyComparisonType.values()[xAxisValue], StrategyComparisonType.values()[yAxisValue], farmInfoView);

		response.setStatus(JsonResponse.RESULT_SUCCESS);
		response.setResult(graphComparisonResult);

		return response;
	}

	@RequestMapping(value = "deleteStrategy", method = RequestMethod.POST)
	public @ResponseBody JsonResponse deleteStrategy(@RequestParam(value = "strategyIdArray[]", required = false) int[] strategyIdArray,
													 @RequestParam(value="farmId", required = false) Integer farmId) {

		JsonResponse jsonResponse = new JsonResponse();
		PlantingProfitLogger.info("inside deleteStrategy");
		boolean result;
		try {
			Farm farm = farmService.getFarmById(farmId);
			for (int strategyId : strategyIdArray) {
				farmCustomStrategyService.deleteStrategy(strategyId);
			}
			farmDetailsContainerService.deleteStrategies(farm, strategyIdArray);

			result = true;
		} catch (Exception e) {
			PlantingProfitLogger.error(e);
			result = false;
		}

		if (result) {
			jsonResponse.setStatus(JsonResponse.RESULT_SUCCESS);
		} else {
			jsonResponse.setStatus(JsonResponse.RESULT_FAILED);
		}
		return jsonResponse;
	}

}

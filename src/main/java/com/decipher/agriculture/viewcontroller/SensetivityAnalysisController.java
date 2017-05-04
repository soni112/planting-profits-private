package com.decipher.agriculture.viewcontroller;

import javax.servlet.http.HttpServletRequest;

import com.decipher.agriculture.data.farm.FarmInfo;
import com.decipher.agriculture.service.account.impl.SessionService;
import com.decipher.agriculture.service.farm.FarmInfoService;
import com.decipher.agriculture.service.farmDetails.FarmDetailsContainerService;
import com.decipher.view.form.farmDetails.FarmInfoView;
import com.decipher.view.form.strategy.FarmCustomStrategyView;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.decipher.agriculture.data.account.Account;
import com.decipher.agriculture.service.strategy.FarmCustomStrategyService;
import com.decipher.agriculture.service.farmDetails.SensetivityAnalysisService;
import com.decipher.util.JsonResponse;
import com.decipher.util.PlantingProfitLogger;

@Controller
@RequestMapping("/SensetivityAnalysisController")
public class SensetivityAnalysisController {

	@Autowired
	private FarmCustomStrategyService farmCustomStrategyService;
	@Autowired
	private SessionService sessionService;
	@Autowired
	private SensetivityAnalysisService sensetivityAnalysisService;
	@Autowired
	private FarmDetailsContainerService farmDetailsContainerService;


	@RequestMapping(value = "getSAForStrategyByMultipleResource", method = RequestMethod.POST)
	public @ResponseBody JsonResponse getSAForStrategyByMultipleResource(
			HttpServletRequest request,
			@RequestParam(value = "farmInfoId", required = false) int farmInfoId,
			@RequestParam(value = "resourceArray[]", required = true) String[] resourceArray) {
		JsonResponse jsonResponse = new JsonResponse();
		org.json.simple.JSONObject jsonObject = null;
		Account account = sessionService.getLoggedInUser();
		if (account != null) {
			jsonObject = sensetivityAnalysisService.getSAStrategyByMultipleResource(farmInfoId, resourceArray, null, null, null, null);
			if (jsonObject != null) {
				jsonResponse.setStatus(JsonResponse.RESULT_SUCCESS);
				jsonResponse.setResult(jsonObject);
				PlantingProfitLogger.info(jsonResponse);
			} else {
				jsonResponse.setStatus(JsonResponse.RESULT_FAILED);
			}
		} else {
			jsonResponse.setStatus(JsonResponse.RESULT_INVALID_USER_NOT_EXISTS);
		}
		return jsonResponse;
	}

	@RequestMapping(value = "getSAForStrategyByMultipleCrops", method = RequestMethod.POST)
	public @ResponseBody JsonResponse getSAForStrategyByMultipleCrops(
			HttpServletRequest request,
			@RequestParam(value = "farmInfoId", required = false) int farmInfoId,
			@RequestParam(value = "cropsArray[]", required = false) String[] cropsArray,
			@RequestParam(value = "cropContractArray[]", required = false) String[] cropContractArray,
			@RequestParam(value = "cropProposedArray[]", required = false) String[] cropProposedArray,
			@RequestParam(value = "cropsGroupArray[]", required = false) String[] cropsGroupArray) {
		JsonResponse jsonResponse = new JsonResponse();
		org.json.simple.JSONObject jsonObject = null;
		Account account = sessionService.getLoggedInUser();
		if (account != null) {
			jsonObject = sensetivityAnalysisService.getSAStrategyByMultipleResource(farmInfoId, null, cropsArray, cropContractArray, cropsGroupArray, cropProposedArray);
			if (jsonObject != null) {
				jsonResponse.setStatus(JsonResponse.RESULT_SUCCESS);
				jsonResponse.setResult(jsonObject);
				PlantingProfitLogger.info(jsonResponse);
			} else {
				jsonResponse.setStatus(JsonResponse.RESULT_FAILED);
			}
		} else {
			jsonResponse.setStatus(JsonResponse.RESULT_INVALID_USER_NOT_EXISTS);
		}
		return jsonResponse;
	}

	@RequestMapping(value = "getSAForCastGraphForSingleResource", method = RequestMethod.POST)
	public @ResponseBody JsonResponse getSAForCastGraphForSingleResource(
			HttpServletRequest request,
			@RequestParam(value = "farmInfoId", required = false) int farmInfoId,
			@RequestParam(value = "resourceName", required = false) String resourceName,
			@RequestParam(value = "currentPotentialProfit", required = false) Long currentPotentialProfit,
			@RequestParam(value = "differenceValue", required = false) String differenceValue){
		JsonResponse jsonResponse = new JsonResponse();
		JSONObject jsonObject = null;
		Account account = sessionService.getLoggedInUser();
		if (account != null) {
			jsonObject = sensetivityAnalysisService.getSAForCastGraphForSingleResource(farmInfoId, resourceName, Long.parseLong(differenceValue), null, null, null, currentPotentialProfit);
			if (jsonObject != null) {
				jsonResponse.setStatus(JsonResponse.RESULT_SUCCESS);
				jsonResponse.setResult(jsonObject);
				PlantingProfitLogger.info(jsonResponse);
			} else {
				jsonResponse.setStatus(JsonResponse.RESULT_FAILED);
			}
		} else {
			jsonResponse.setStatus(JsonResponse.RESULT_INVALID_USER_NOT_EXISTS);
		}
		return jsonResponse;
	}

	@RequestMapping(value = "SAForCastGraphForSingleCrop", method = RequestMethod.POST)
	public @ResponseBody JsonResponse SAForCastGraphForSingleCrop(
			HttpServletRequest request,
			@RequestParam(value = "farmInfoId", required = false) int farmInfoId,
			@RequestParam(value = "cropName", required = false) String cropName,
			@RequestParam(value = "currentPotentialProfit", required = false) Long currentPotentialProfit,
			@RequestParam(value = "selectionType", required = false) String selectionType,
			@RequestParam(value = "rangeType", required = false) String rangeType,
			@RequestParam(value = "differenceValue", required = false) String differenceValue){
		JsonResponse jsonResponse = new JsonResponse();
		JSONObject jsonObject = null;
		Account account = sessionService.getLoggedInUser();
		if (account != null) {
			jsonObject = sensetivityAnalysisService.getSAForCastGraphForSingleResource(farmInfoId, null, Long.parseLong(differenceValue), cropName, selectionType, rangeType, currentPotentialProfit);
			if (jsonObject != null) {
				jsonResponse.setStatus(JsonResponse.RESULT_SUCCESS);
				jsonResponse.setResult(jsonObject);
				PlantingProfitLogger.info(jsonResponse);
			} else {
				jsonResponse.setStatus(JsonResponse.RESULT_FAILED);
			}
		} else {
			jsonResponse.setStatus(JsonResponse.RESULT_INVALID_USER_NOT_EXISTS);
		}
		return jsonResponse;
	}

	@RequestMapping(value = "SaveStrategyForMultipleResources", method = RequestMethod.POST)
	public @ResponseBody JsonResponse SaveStrategyForMultipleResources(
			HttpServletRequest request,
			@RequestParam(value = "farmId", required = true) int farmId,
			@RequestParam(value = "farmInfoId", required = true) int farmInfoId,
			@RequestParam(value = "resourceArray[]", required = true) String[] resourceArray,
			@RequestParam(value = "strategyName", required = true) String strategyName/*,
			@RequestParam(value = "potentialProfit", required = true) Double potentialProfit,
			@RequestParam(value = "totalAcreage", required = true) Double totalAcreage*/) {
		JsonResponse jsonResponse = new JsonResponse();
		Account account = sessionService.getLoggedInUser();
		int strategyId;
		if (account != null) {
			boolean exists = farmCustomStrategyService.isFarmStrategyExitsWithName(strategyName.trim(), farmId);
			if (!exists) {
				strategyId = farmCustomStrategyService.saveFarmCustomStrategy(farmInfoId, resourceArray, strategyName);

				if (strategyId != 0) {
					/**
					 * @added - Abhishek
					 * @date - 12-05-2016
					 * @desc - for updating strategy details for farm in container
					 */
					FarmCustomStrategyView farmCustomStrategyView = farmCustomStrategyService.getDataForCustomStrategy(farmId, strategyId);
					FarmInfo farmInfo = farmCustomStrategyView.getFarmCustomStrategy().getFarmInfo();
					farmDetailsContainerService.updateStrategyDetails(new FarmInfoView(farmInfo), farmCustomStrategyView);

					jsonResponse.setStatus(JsonResponse.RESULT_SUCCESS);
				} else {
					jsonResponse.setStatus(JsonResponse.RESULT_FAILED);
				}
			} else {
				jsonResponse.setStatus(JsonResponse.RESULT_ALREADY_EXISTS);
			}
		} else {
			jsonResponse.setStatus(JsonResponse.RESULT_INVALID_USER_NOT_EXISTS);
		}
		return jsonResponse;

	}

	@RequestMapping(value = "SaveStrategyForMultipleCrop", method = RequestMethod.POST)
	public @ResponseBody JsonResponse SaveStrategyForMultipleCrop(
			@RequestParam(value = "farmInfoId") int farmInfoId,
			@RequestParam(value = "farmId") int farmId,
			@RequestParam(value = "cropsArray[]", required = false) String[] cropsArray,
			@RequestParam(value = "cropContractArray[]", required = false) String[] cropContractArray,
			@RequestParam(value = "cropProposedArray[]", required = false) String[] cropProposedArray,
			@RequestParam(value = "cropsGroupArray[]", required = false) String[] cropsGroupArray,
			@RequestParam(value = "strategyName") String strategyName) {
		JsonResponse jsonResponse = new JsonResponse();
		Account account = sessionService.getLoggedInUser();
		PlantingProfitLogger.info("In SaveStrategyForMultipleCrop...");
		int strategyId;
		if (account != null) {
			boolean exists = farmCustomStrategyService.isFarmStrategyExitsWithName(strategyName.trim(), farmId);
			if (!exists) {
				strategyId = farmCustomStrategyService.saveFarmCustomStrategyForMultipalCrop(farmInfoId, cropsArray, cropContractArray,
										cropProposedArray, cropsGroupArray, strategyName);

				if (strategyId != 0) {

					/**
					 * @added - Abhishek
					 * @date - 12-05-2016
					 * @desc - for updating strategy details for farm in container
					 */
					FarmCustomStrategyView farmCustomStrategyView = farmCustomStrategyService.getDataForCustomStrategy(farmId, strategyId);
					FarmInfo farmInfo = farmCustomStrategyView.getFarmCustomStrategy().getFarmInfo();
					farmDetailsContainerService.updateStrategyDetails(new FarmInfoView(farmInfo), farmCustomStrategyView);


					jsonResponse.setStatus(JsonResponse.RESULT_SUCCESS);
				} else {
					jsonResponse.setStatus(JsonResponse.RESULT_FAILED);
				}
			} else {
				jsonResponse.setStatus(JsonResponse.RESULT_ALREADY_EXISTS);
			}
		} else {
			jsonResponse.setStatus(JsonResponse.RESULT_INVALID_USER_NOT_EXISTS);
		}
		return jsonResponse;

	}


}

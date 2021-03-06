package com.decipher.agriculture.service.strategyComparison.impl;

import com.decipher.agriculture.data.farm.PlanByStrategy;
import com.decipher.agriculture.data.strategy.StrategyComparisonType;
import com.decipher.agriculture.service.farm.FarmService;
import com.decipher.agriculture.service.farmDetails.FarmDetailsContainerService;
import com.decipher.agriculture.service.farmDetails.FarmOutputCalculationService;
import com.decipher.agriculture.service.strategy.impl.RiskAndConservationMgmtDataBuilder;
import com.decipher.agriculture.service.strategyComparison.StrategyComparisonService;
import com.decipher.util.AgricultureStandardUtils;
import com.decipher.util.PlantingProfitLogger;
import com.decipher.view.form.farmDetails.CropResourceUsageView;
import com.decipher.view.form.farmDetails.CropTypeView;
import com.decipher.view.form.farmDetails.FarmInfoView;
import com.decipher.view.form.farmDetails.FarmOutputDetailsForFieldView;
import com.decipher.view.form.farmDetails.FarmOutputDetailsView;
import com.decipher.view.form.farmDetails.FieldInfoView;
import com.decipher.view.form.scenario.FarmStrategyScenarioView;
import com.decipher.view.form.strategy.FarmCustomStrategyView;
import org.codehaus.jettison.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by abhishek on 20/4/16.
 */
@Service
@SuppressWarnings("unchecked")
public class StrategyComparisonServiceImpl implements StrategyComparisonService {

    @Autowired
    private HttpSession httpSessionService;
    @Autowired
    private FarmDetailsContainerService farmDetailsContainerService;
    @Autowired
    private FarmOutputCalculationService farmOutputCalculationService;
    @Autowired
    private FarmService farmService;

    private StrategyComparisonDataBuilder strategyComparisonDataBuilder;


    @Override
    public JSONObject getGraphComparisonResult(StrategyComparisonType xAxisType,
                                               StrategyComparisonType yAxisType,
                                               FarmInfoView farmInfoView,
                                               int[] strategyIdArray) throws JSONException {

        Map<FarmCustomStrategyView, JSONObject> strategyDetailsForFarm = getDetailsFromDatabase(farmInfoView);
        strategyComparisonDataBuilder = new StrategyComparisonDataBuilder(strategyDetailsForFarm, strategyIdArray);

        JSONObject jsonObject = new JSONObject();

        JSONObject xAxisData = getDetailsForStrategyType(xAxisType, "x", farmInfoView);
        JSONObject yAxisData = getDetailsForStrategyType(yAxisType, "y", farmInfoView);

        xAxisData.putAll(yAxisData);

        JSONArray graphObject = strategyComparisonDataBuilder.getGraphObject(xAxisType.toString(), yAxisType.toString());

        JSONArray jsonArrayForGraphData = new JSONArray();
        jsonArrayForGraphData.add(xAxisData);

        jsonObject.put("graphDataJsonObject", jsonArrayForGraphData);
        jsonObject.put("graphJsonObject", graphObject);

        return jsonObject;
    }


    private String getAxisText(int axisIndex){

        Map<Integer, String> cropDescription = (Map<Integer, String>)httpSessionService.getAttribute("cropDescription");

        return cropDescription.get(axisIndex);
    }

    @Override
    public Map<FarmCustomStrategyView, JSONObject> getStrategyDetailsForFarm(FarmInfoView farmInfoView) throws JSONException {
//        Map<FarmCustomStrategyView, JSONObject> strategyDetails = new TreeMap<>();
//        Map<FarmInfoView, Map<FarmCustomStrategyView, JSONObject>> allStrategyOutputDetailsContainer = new TreeMap<FarmInfoView, Map<FarmCustomStrategyView, JSONObject>>();
//
//        if (httpSessionService.getAttribute("allStrategyOutputDetailsContainer") != null) {
//            allStrategyOutputDetailsContainer = (Map<FarmInfoView, Map<FarmCustomStrategyView, JSONObject>>) httpSessionService.getAttribute("allStrategyOutputDetailsContainer");
//
//            if(!allStrategyOutputDetailsContainer.containsKey(farmInfoView)){
//                updateSessionWithStrategyOutput(farmInfoView);
//            }
//
//        } else {
//            updateSessionWithStrategyOutput(farmInfoView);
//        }
//
//        allStrategyOutputDetailsContainer = (Map<FarmInfoView, Map<FarmCustomStrategyView, JSONObject>>) httpSessionService.getAttribute("allStrategyOutputDetailsContainer");
//        strategyDetails = allStrategyOutputDetailsContainer.get(farmInfoView);
//
//        return strategyDetails;

        return getDetailsFromDatabase(farmInfoView);

    }

    @Override
    public boolean updateSessionWithStrategyOutput(FarmInfoView farmInfoView){

        boolean status = false;

        Map<FarmInfoView, Map<FarmCustomStrategyView, JSONObject>> allStrategyOutputDetailsContainer = new TreeMap<FarmInfoView, Map<FarmCustomStrategyView, JSONObject>>();
        PlantingProfitLogger.info("Trying to find strategy details in session");
        if (httpSessionService.getAttribute("allStrategyOutputDetailsContainer") != null) {
            PlantingProfitLogger.info("Strategy details found in session. Now finding details for farm : " + farmInfoView.getFarmName());
            allStrategyOutputDetailsContainer = (Map<FarmInfoView, Map<FarmCustomStrategyView, JSONObject>>) httpSessionService.getAttribute("allStrategyOutputDetailsContainer");

            if (!allStrategyOutputDetailsContainer.containsKey(farmInfoView)) {
                PlantingProfitLogger.info("Strategy details not found. Now finding details from database for farm : " + farmInfoView.getFarmName());
                try {
//                    PlantingProfitLogger.info("Started getting latest strategy output details for farm named " + farmInfoView.getFarmName() + " from database : ");
                    allStrategyOutputDetailsContainer.put(farmInfoView, getDetailsFromDatabase(farmInfoView));
                    PlantingProfitLogger.info("Completed getting latest strategy output details for farm named " + farmInfoView.getFarmName() + " from database : ");


                    PlantingProfitLogger.info("Updating session with latest strategy output details for farm : " + farmInfoView.getFarmName());
                    httpSessionService.removeAttribute("allStrategyOutputDetailsContainer");
                    httpSessionService.setAttribute("allStrategyOutputDetailsContainer", allStrategyOutputDetailsContainer);
                    PlantingProfitLogger.info("Session update complete with latest strategy output details for farm : " + farmInfoView.getFarmName());

                    status = true;

                } catch (JSONException e) {
                    PlantingProfitLogger.info("Error occurred while getting latest strategy output details for farm named " + farmInfoView.getFarmName() + " from database : ");
                    PlantingProfitLogger.error(e);

                    status = false;
                }
            }

        } else {
            PlantingProfitLogger.info("No strategy details found in session");
            try {
                PlantingProfitLogger.info("Started getting latest strategy output details for farm named " + farmInfoView.getFarmName() + " from database : ");
                allStrategyOutputDetailsContainer.put(farmInfoView, getDetailsFromDatabase(farmInfoView));
                PlantingProfitLogger.info("Completed getting latest strategy output details for farm named " + farmInfoView.getFarmName() + " from database : ");

                PlantingProfitLogger.info("Updating session with latest strategy output details for farm : " + farmInfoView.getFarmName());
                httpSessionService.removeAttribute("allStrategyOutputDetailsContainer");
                httpSessionService.setAttribute("allStrategyOutputDetailsContainer", allStrategyOutputDetailsContainer);
                PlantingProfitLogger.info("Session update complete with latest strategy output details for farm : " + farmInfoView.getFarmName());

                status = true;

            } catch (JSONException e) {
                PlantingProfitLogger.info("Error occurred while getting latest strategy output details for farm named " + farmInfoView.getFarmName() + " from database : ");
                PlantingProfitLogger.error(e);

                status = false;
            }
        }

        return status;
    }

    private Map<FarmCustomStrategyView, JSONObject> getDetailsFromDatabase(FarmInfoView farmInfoView) throws JSONException {

        //  Getting all strategies for farm
        /*List<FarmCustomStrategyView> farmCustomStrategyViewList = farmCustomStrategyService.getFarmCustomStrategyViewList(farmInfoView.getId());*/
//        Map<FarmCustomStrategyView, JSONObject> strategyContainer = new TreeMap<FarmCustomStrategyView, JSONObject>();

        /* //  Iterating all strategies for getting corresponding details
        for (FarmCustomStrategyView farmCustomStrategyView : farmCustomStrategyViewList) {

            //  getting base values of strategy
            StrategyDataBean strategyDataBean = farmCustomStrategyService.getStrategyBaseValuesForStrategy(farmCustomStrategyView.getId(), farmInfoView);

            //  getting all details for strategy
            JSONObject strategyOutputDetails = scenarioService.getFarmOutputDetails(strategyDataBean);

            //  adding all details for strategy
            strategyContainer.put(farmCustomStrategyView, strategyOutputDetails);

        }*/
        int[] strategyIdArray = (int[])httpSessionService.getAttribute("strategyIdArray");

        if(strategyIdArray != null){
            return farmDetailsContainerService.getSpecificStrategiesDetails(farmInfoView, strategyIdArray);
        } else {
            return farmDetailsContainerService.getAllStrategiesDetails(farmInfoView);
        }

    }

    private JSONObject getDetailsForStrategyType(StrategyComparisonType strategyComparisonType, String axis, FarmInfoView farmInfoView){
        JSONObject jsonObject = new JSONObject();

        if (StrategyComparisonType.Potential_Profit.equals(strategyComparisonType)) {

            jsonObject = strategyComparisonDataBuilder.getPotentialProfitComparisonDetails(axis);

        } else if (StrategyComparisonType.Land_Used.equals(strategyComparisonType)){

            jsonObject = strategyComparisonDataBuilder.getLandUsageComparisonDetails(axis);

        } else if (StrategyComparisonType.Capital_Used.equals(strategyComparisonType)){

            jsonObject = strategyComparisonDataBuilder.getCapitalUsageComparisonDetails(axis);

        } else if (StrategyComparisonType.Crop_Acreage_Per_Crop.equals(strategyComparisonType)){

            jsonObject = strategyComparisonDataBuilder.getCropAcreagePerCropComparisonDetails(axis, farmInfoView);

        } else if (StrategyComparisonType.Percentage_PP_from_Single_Profitable_Crop.equals(strategyComparisonType)){

            jsonObject = strategyComparisonDataBuilder.getPpFromOneOrTwoProfitCropComparisonDetails(axis, farmInfoView, "one");

        } else if (StrategyComparisonType.Percentage_PP_from_Two_Profitable_Crops.equals(strategyComparisonType)){

            jsonObject = strategyComparisonDataBuilder.getPpFromOneOrTwoProfitCropComparisonDetails(axis, farmInfoView, "two");

        } else if (StrategyComparisonType.Percentage_PP_from_Forward_Sales.equals(strategyComparisonType)){

            jsonObject = strategyComparisonDataBuilder.getPpFromForwardSalesComparisonDetails(axis);

        } else if (StrategyComparisonType.Percentage_PP_from_High_Risk_Crops.equals(strategyComparisonType)){

            jsonObject = strategyComparisonDataBuilder.getAcreageOrProfitHighRiskCropComparisonDetails(axis, farmInfoView, "profit");

        } else if (StrategyComparisonType.Percentage_Acreage_High_Risk_Crops.equals(strategyComparisonType)){

            jsonObject = strategyComparisonDataBuilder.getAcreageOrProfitHighRiskCropComparisonDetails(axis, farmInfoView, "acreage");

        } else if (StrategyComparisonType.Percentage_PP_Conservation_Crops.equals(strategyComparisonType)){

            jsonObject = strategyComparisonDataBuilder.getAcreageOrProfitConservationCropComparisonDetails(axis, farmInfoView, "profit");

        } else if (StrategyComparisonType.Percentage_Acreage_Conservation_Crops.equals(strategyComparisonType)){

            jsonObject = strategyComparisonDataBuilder.getAcreageOrProfitConservationCropComparisonDetails(axis, farmInfoView, "acreage");

        } else if(StrategyComparisonType.PP_Given_Min_Price_And_Yield.equals(strategyComparisonType)){

            jsonObject = strategyComparisonDataBuilder.getPpForMinGivenPriceAndYield(axis, farmInfoView);

        } else if (StrategyComparisonType.Return_on_Working_Capital.equals ( strategyComparisonType )) {
            jsonObject = strategyComparisonDataBuilder.getReturnonWorkingCapitalComparisonDetails ( axis );
        }
        return jsonObject;
    }

    private JSONObject getDetailsForStrategyType(int axisIndex, String axis, FarmInfoView farmInfoView){

        Map<Integer, String> cropDescription = (Map<Integer, String>)httpSessionService.getAttribute("cropDescription");

        String cropName = cropDescription.get(axisIndex);


        cropName = cropName.substring(cropName.lastIndexOf("of") + 2, cropName.length()).trim();


        /*Set<Map.Entry<Integer, Object>> entries = cropDescription.entrySet();

        CropTypeView cropTypeView = null;

        for (Map.Entry<Integer, Object> entry : entries) {

            if (entry.getKey() == axisIndex) {
                Map<String, Object> value = (Map<String, Object>)entry.getValue();
                cropTypeView = (CropTypeView)value.get("cropType");
            }

        }*/

        return strategyComparisonDataBuilder.getAcreageForCrop(axis, farmInfoView, cropName);

    }
    @Override
    public JSONObject getGraphComparisonResult(int xAxisIndex,
                                               int yAxisIndex,
                                               FarmInfoView farmInfoView,
                                               int[] strategyIdArray) throws JSONException {

        Map<FarmCustomStrategyView, JSONObject> strategyDetailsForFarm = getDetailsFromDatabase(farmInfoView);
        strategyComparisonDataBuilder = new StrategyComparisonDataBuilder(strategyDetailsForFarm, strategyIdArray);

        JSONObject jsonObject = new JSONObject();
        JSONObject xAxisData, yAxisData;

        String xAxisText, yAxisText;

        if(xAxisIndex > 12){
            xAxisData = getDetailsForStrategyType(xAxisIndex, "x", farmInfoView);
            xAxisText = getAxisText(xAxisIndex);
        } else {
            xAxisData = getDetailsForStrategyType(StrategyComparisonType.values()[xAxisIndex], "x", farmInfoView);
            xAxisText = StrategyComparisonType.values()[xAxisIndex].getComparisonStr();
        }

        if(yAxisIndex > 12){
            yAxisData = getDetailsForStrategyType(yAxisIndex, "y", farmInfoView);
            yAxisText = getAxisText(yAxisIndex);
        } else {
            yAxisData = getDetailsForStrategyType(StrategyComparisonType.values()[yAxisIndex], "y", farmInfoView);
            yAxisText = StrategyComparisonType.values()[yAxisIndex].getComparisonStr();
        }


        xAxisData.putAll(yAxisData);

        JSONArray graphObject = strategyComparisonDataBuilder.getGraphObject(xAxisText, yAxisText);

        JSONArray jsonArrayForGraphData = new JSONArray();
        jsonArrayForGraphData.add(xAxisData);

        jsonObject.put("graphDataJsonObject", jsonArrayForGraphData);
        jsonObject.put("graphJsonObject", graphObject);
        jsonObject.put("xAxisText", xAxisText);
        jsonObject.put("yAxisText", yAxisText);

        return jsonObject;
    }
    @Override
    public JSONObject getGranularComparisonResult(FarmInfoView farmInfoView, int[] strategyIdArray) throws JSONException {
        int strategyID;
        Map <FarmCustomStrategyView, JSONObject> strategyDetailsForFarm = getStrategyDetailsForFarm ( farmInfoView );
        Map <String, JSONArray> strategyOutputDetails = getStrategyOutputDetails ( strategyDetailsForFarm, strategyIdArray );
        Map <String, JSONArray> highRiskAngConservationForStrategy = getHighRiskAndConservationForStrategy ( strategyDetailsForFarm, strategyIdArray);

        Set <Map.Entry <FarmCustomStrategyView, JSONObject>> entries = strategyDetailsForFarm.entrySet ();
        JSONArray jsonArrayStrategyData = new JSONArray ();
        for (Map.Entry <FarmCustomStrategyView, JSONObject> entry : entries) {
            JSONObject jsonObjectStrategyData = new JSONObject ();
            FarmCustomStrategyView farmCustomStrategyView = entry.getKey ();
            int strategyId = farmCustomStrategyView.getId();
            JSONObject strategyDetails = entry.getValue ();
            int estimateIncome = 0;
            String returnWorkingCapital = null;
            double variableCostProduction = 0.0;
            double workingCapitalUsed = 0.0;

            for (int i:strategyIdArray) {
                strategyID = i;
                if (strategyID == strategyId) {
                    List<CropTypeView> cropTypeViews = (List<CropTypeView>) strategyDetails.get("cropTypeView");

                    if (Objects.equals(farmCustomStrategyView.getFarmCustomStrategy().getFarmInfo().getStrategy(), PlanByStrategy.PLAN_BY_ACRES)) {
                        List<FarmOutputDetailsView> farmOutputDetailsViewList = (List<FarmOutputDetailsView>) strategyDetails.get("farmOutputDetails");
                        for (FarmOutputDetailsView farmOutputDetailsView : farmOutputDetailsViewList) {
                            estimateIncome += Integer.parseInt(AgricultureStandardUtils.removeAllCommas(farmOutputDetailsView.getProfit()));
//                            farmOutputDetailsView.getProfitAsDouble();
                        }
                    } else if (Objects.equals(farmCustomStrategyView.getFarmCustomStrategy().getFarmInfo().getStrategy(), PlanByStrategy.PLAN_BY_FIELDS)) {
                        List<FarmOutputDetailsForFieldView> farmOutputDetailsForFieldViewList = (List<FarmOutputDetailsForFieldView>) strategyDetails.get("farmOutputDetails");
                        for (FarmOutputDetailsForFieldView farmOutputDetailsForFieldView : farmOutputDetailsForFieldViewList) {
                            estimateIncome += Integer.parseInt(AgricultureStandardUtils.removeAllCommas(farmOutputDetailsForFieldView.getProfit()));
                        }
                    }
                    int index = 0;
                    Map<String, String> cropResourceUsed = (Map<String, String>) strategyDetails.get("cropResourceUsed");
                    int sizeOfList = ((List<CropResourceUsageView>) strategyDetails.get("resourceList")).size();
                    for (CropResourceUsageView cropResourceUsageView : (List<CropResourceUsageView>) strategyDetails.get("resourceList")) {
                        index++;

                        if (cropResourceUsageView.getCropResourceUse().equalsIgnoreCase("capital")) {
                            workingCapitalUsed = Double.parseDouble(AgricultureStandardUtils.removeAllCommas(cropResourceUsed.get(cropResourceUsageView.getCropResourceUse())));
                        } else if (cropResourceUsageView.getCropResourceUse().equalsIgnoreCase("land")) {
                            workingCapitalUsed = Double.parseDouble(AgricultureStandardUtils.removeAllCommas(cropResourceUsed.get(cropResourceUsageView.getCropResourceUse())));
                        }/*else {
                    workingCapitalUsed = Double.parseDouble ( AgricultureStandardUtils.removeAllCommas ( cropResourceUsed.get ( cropResourceUsageView.getCropResourceUse () ) ) );
                }*/


                        if (index == sizeOfList) {
                            if (workingCapitalUsed != 0 || estimateIncome != 0) {
                                returnWorkingCapital = String.valueOf((AgricultureStandardUtils.doubleWithOneDecimal(estimateIncome / workingCapitalUsed)));
                            } else {
                                returnWorkingCapital = "0.0";
                            }
                        }
                    }
                    jsonObjectStrategyData.put("EstimateIncome", estimateIncome);
                    jsonObjectStrategyData.put("returnWorkingCapital", "" + returnWorkingCapital);
                    jsonArrayStrategyData.add(jsonObjectStrategyData);
                }
            }
        }

        JSONObject jsonObject = new JSONObject ();
        jsonObject.put ( "DataForStrategy", jsonArrayStrategyData );
        jsonObject.put ( "jsonArrayForStrategy", strategyOutputDetails.get ( "jsonArrayForStrategy" ) );
        jsonObject.put ( "jsonArrayForHighRiskCropForGranular", highRiskAngConservationForStrategy.get ( "jsonArrayForHighRiskCrop" ) );
        jsonObject.put ( "jsonArrayForConservationCropForGranular", highRiskAngConservationForStrategy.get ( "jsonArrayForConservationCrop" ) );
        jsonObject.put ( "jsonArrayForConversion", highRiskAngConservationForStrategy.get ( "jsonArrayForConversion" ) );


        return jsonObject;

    }

    @Override
    public JSONObject getStrategyComparisonDetails(FarmInfoView farmInfoView, int[] strategyIdArray) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        Set <String> setForCropHeader = new HashSet <String> ();

        int strategyId = 0;
        for(int i:strategyIdArray) {
            strategyId = i;
            Map<FarmCustomStrategyView, JSONObject> strategyDetailsForFarm = getStrategyDetailsForFarm(farmInfoView);

            Map<String, JSONArray> strategyOutputDetails = getStrategyOutputDetails(strategyDetailsForFarm, strategyIdArray);
            Map<String, JSONArray> cropDetailsForStrategy = getCropDetailsForStrategy(strategyDetailsForFarm);
            Map<String, JSONArray> resourceDetailsForStrategy = getResourceDetailsForStrategy(strategyDetailsForFarm);
            Map<String, JSONArray> highRiskAngConservationForStrategy = getHighRiskAndConservationForStrategy(strategyDetailsForFarm, strategyIdArray);
            JSONArray jsonArrayForHeader = new JSONArray();

            JSONArray jsonArrayForCropHeader = cropDetailsForStrategy.get("jsonArrayForCropHeader");
            for (Object object : jsonArrayForCropHeader) {
                JSONObject headerDetails = (JSONObject) object;
                JSONArray jsonArrayForHeaderDetails = (JSONArray) headerDetails.get("cropKey");
                if (headerDetails.get("strategyId").equals(strategyId)) {
                    for (Object jsonArrayForHeaderDetail : jsonArrayForHeaderDetails) {
                        JSONObject jsonObjectForHeader = (JSONObject) jsonArrayForHeaderDetail;
                        String cropName = (String) jsonObjectForHeader.get("name");
                        setForCropHeader.add(cropName);
                    }
                }
            }
            jsonArrayForHeader.addAll(setForCropHeader);

            Map<String, JSONArray> cropDetailsForHeader = new HashMap<>();
            cropDetailsForHeader.put("jsonArrayForCropHeader", jsonArrayForHeader);


            //  Strategy Comparison Details
            jsonObject.put("strategyDetails", strategyOutputDetails.get("jsonArrayForStrategy"));
            jsonObject.put("strategyOutput", strategyOutputDetails.get("jsonArrayForStrategyOutput"));
            jsonObject.put("strategy", farmInfoView.getStrategy());

            jsonObject.put("jsonArrayForCrop", cropDetailsForStrategy.get("jsonArrayForCrop"));
            jsonObject.put("jsonArrayForCropHeader", cropDetailsForHeader.get("jsonArrayForCropHeader"));

            jsonObject.put("jsonArrayForResource", resourceDetailsForStrategy.get("jsonArrayForResource"));
            jsonObject.put("jsonArrayForResourceHeader", resourceDetailsForStrategy.get("jsonArrayForResourceHeader"));

            jsonObject.put("jsonArrayForConservationCrop", highRiskAngConservationForStrategy.get("jsonArrayForConservationCrop"));
            jsonObject.put("jsonArrayForConservationCropHeader", highRiskAngConservationForStrategy.get("jsonArrayForConservationCropHeader"));

            jsonObject.put("jsonArrayForHighRiskCrop", highRiskAngConservationForStrategy.get("jsonArrayForHighRiskCrop"));
            jsonObject.put("jsonArrayForHighRiskCropHeader", highRiskAngConservationForStrategy.get("jsonArrayForHighRiskCropHeader"));


            //  Variance Graph Details
            jsonObject.put("jsonArrayForVarianceGraphData", getVarianceGraphDetails(strategyDetailsForFarm, farmInfoView, strategyIdArray));
        }

        return jsonObject;

    }

    private Map<String, JSONArray> getStrategyOutputDetails(Map<FarmCustomStrategyView, JSONObject> strategyDetailsForFarm, int[] strategyIdArray){

        Map<String, JSONArray> strategyDetails = new HashMap<>();

        JSONArray jsonArrayForStrategy = new JSONArray();

        JSONArray jsonArrayForStrategyOutput = new JSONArray();

        Double totalAcreage;
        Set<FarmCustomStrategyView> farmCustomStrategyViews = strategyDetailsForFarm.keySet();
        for (FarmCustomStrategyView farmCustomStrategyView : farmCustomStrategyViews) {

            for (int strategyId : strategyIdArray) {

                if(farmCustomStrategyView.getId().equals(strategyId)){

                    JSONObject jsonObjectForStrategyDetails = strategyDetailsForFarm.get(farmCustomStrategyView);

                    totalAcreage = 0.0;

                    double potentialProfit = Double.parseDouble(AgricultureStandardUtils.removeAllCommas(jsonObjectForStrategyDetails.get("potentialProfit").toString()));
                    farmCustomStrategyView.setPotentialProfit(potentialProfit);
                    List<CropTypeView> cropTypeViewList = (List<CropTypeView>) jsonObjectForStrategyDetails.get("cropTypeView");

                    if(PlanByStrategy.PLAN_BY_ACRES.equals(farmCustomStrategyView.getFarmCustomStrategy().getFarmInfo().getStrategy())){

                        List<FarmOutputDetailsView> farmOutputDetailsViewList = (List<FarmOutputDetailsView>) jsonObjectForStrategyDetails.get("farmOutputDetails");
                        JSONArray jsonArray = new JSONArray();
                        JSONObject jsonObject = new JSONObject();
                        for (FarmOutputDetailsView farmOutputDetailsView : farmOutputDetailsViewList) {
                            JSONObject jsonObjectDetails = new JSONObject();
                            String cropName = null;
                            String conservation = " (conservation)";
                            String highRisk = " (highRisk)";
                            String conservationHighRisk = " (conservation) (highRisk)";

                            String forStr = "";
                            if (farmOutputDetailsView.getForFirm()) {
                                forStr = " (Firm)";
                            } else if (farmOutputDetailsView.getForProposed()) {
                                forStr = " (Proposed)";
                            }

                            for (CropTypeView cropTypeView : cropTypeViewList) {
                                if (farmOutputDetailsView.getCropTypeView().getCropName().equals(cropTypeView.getCropName()) && cropTypeView.getHiRiskCrop().equals("true") && cropTypeView.getConservation_Crop().equals("true")) {
                                    cropName = farmOutputDetailsView.getCropTypeView().getCropName() + forStr + ""+ conservationHighRisk;
                                }else if (farmOutputDetailsView.getCropTypeView().getCropName().equals(cropTypeView.getCropName()) && cropTypeView.getHiRiskCrop().equals("true")){
                                    cropName = farmOutputDetailsView.getCropTypeView().getCropName() + forStr + ""+highRisk;
                                }else if (farmOutputDetailsView.getCropTypeView().getCropName().equals(cropTypeView.getCropName()) && cropTypeView.getConservation_Crop().equals("true")) {
                                    cropName = farmOutputDetailsView.getCropTypeView().getCropName() + forStr + ""+conservation;
                                }else if (farmOutputDetailsView.getCropTypeView().getCropName().equals(cropTypeView.getCropName())){
                                    cropName = farmOutputDetailsView.getCropTypeView().getCropName()+ forStr;
                                }
                            }

                            jsonObjectDetails.put("cropName", cropName);
                            jsonObjectDetails.put("acreage", AgricultureStandardUtils.commaSeparaterForLong(farmOutputDetailsView.getUsedAcresAsInteger()));

                            totalAcreage += farmOutputDetailsView.getUsedAcresAsDouble();

                            jsonArray.add(jsonObjectDetails);
                        }
                        jsonObject.put("outputDetails", jsonArray);
                        jsonObject.put("farmCustomStrategyId", farmCustomStrategyView.getId());
                        jsonObject.put("strategy", farmCustomStrategyView.getFarmCustomStrategy().getFarmInfo().getStrategy());

                        jsonArrayForStrategyOutput.add(jsonObject);


                    } else if(PlanByStrategy.PLAN_BY_FIELDS.equals(farmCustomStrategyView.getFarmCustomStrategy().getFarmInfo().getStrategy())){

                        List<FieldInfoView> fieldInfoViewList = (List<FieldInfoView>)jsonObjectForStrategyDetails.get("fieldInfoList");
                        List<FarmOutputDetailsForFieldView> farmOutputDetailsViewList = (List<FarmOutputDetailsForFieldView>)jsonObjectForStrategyDetails.get("farmOutputDetails");

                        JSONArray jsonArray = new JSONArray();
                        JSONObject jsonObject = new JSONObject();

                        for (FieldInfoView fieldInfoView : fieldInfoViewList) {

                            if (fieldInfoView.getFallow().equalsIgnoreCase("true")) {
                                JSONObject jsonObjectDetails = new JSONObject();
                                jsonObjectDetails.put("field", fieldInfoView.getFieldName());
                                jsonObjectDetails.put("size", fieldInfoView.getFieldSize());
                                jsonObjectDetails.put("crop", "Fallow");
                                jsonArray.add(jsonObjectDetails);
                            } else {
                                boolean planted = false;
                                for (FarmOutputDetailsForFieldView farmOutputDetailsForFieldView : farmOutputDetailsViewList) {
                                    if (farmOutputDetailsForFieldView.getFieldInfoView().getFieldName().equalsIgnoreCase(fieldInfoView.getFieldName()) && !farmOutputDetailsForFieldView.getUsedAcres().equals("0")) {
                                        planted = true;
                                        JSONObject jsonObjectDetails = new JSONObject();
                                        jsonObjectDetails.put("field", fieldInfoView.getFieldName());
                                        jsonObjectDetails.put("size", farmOutputDetailsForFieldView.getUsedAcres());

                                        totalAcreage += farmOutputDetailsForFieldView.getUsedAcresAsDouble();

                                        String forStr = "";
                                        if (farmOutputDetailsForFieldView.isForFirm()) {
                                            forStr = " (Firm)";
                                        } else if (farmOutputDetailsForFieldView.isForProposed()) {
                                            forStr = " (Proposed)";
                                        }

                                        String cropNameDetail = null;
                                        String conservation = " (conservation)";
                                        String highRisk = " (highRisk)";
                                        String conservationHighRisk = " (conservation) (highRisk)";

                                        for (CropTypeView cropTypeView : cropTypeViewList) {
                                            if (farmOutputDetailsForFieldView.getCropTypeView().getCropName().equals(cropTypeView.getCropName()) && cropTypeView.getHiRiskCrop().equals("true") && cropTypeView.getConservation_Crop().equals("true")) {
                                                cropNameDetail = farmOutputDetailsForFieldView.getCropTypeView().getCropName() + forStr + "" + conservationHighRisk;
                                            }else if (farmOutputDetailsForFieldView.getCropTypeView().getCropName().equals(cropTypeView.getCropName()) && cropTypeView.getHiRiskCrop().equals("true")){
                                                cropNameDetail = farmOutputDetailsForFieldView.getCropTypeView().getCropName() +forStr+""+highRisk;
                                            }else if (farmOutputDetailsForFieldView.getCropTypeView().getCropName().equals(cropTypeView.getCropName()) && cropTypeView.getConservation_Crop().equals("true")) {
                                                cropNameDetail = farmOutputDetailsForFieldView.getCropTypeView().getCropName() +forStr+""+conservation;
                                            }else if (farmOutputDetailsForFieldView.getCropTypeView().getCropName().equals(cropTypeView.getCropName())){
                                                cropNameDetail = farmOutputDetailsForFieldView.getCropTypeView().getCropName() +forStr;
                                            }
                                        }
                                        jsonObjectDetails.put("crop",  cropNameDetail);
                                        jsonArray.add(jsonObjectDetails);
                                    }
                                }

                                if (!planted) {
                                    JSONObject jsonObjectDetails = new JSONObject();
                                    jsonObjectDetails.put("field", fieldInfoView.getFieldName());
                                    jsonObjectDetails.put("size", fieldInfoView.getFieldSize());
                                    jsonObjectDetails.put("crop", "Not Assigned");
                                    jsonArray.add(jsonObjectDetails);
                                }
                            }
                        }


                        jsonObject.put("outputDetails", jsonArray);
                        jsonObject.put("farmCustomStrategyId", farmCustomStrategyView.getId());
                        jsonObject.put("strategy", farmCustomStrategyView.getFarmCustomStrategy().getFarmInfo().getStrategy());
                        jsonArrayForStrategyOutput.add(jsonObject);
                    }

                    farmCustomStrategyView.setTotalAcreage(totalAcreage);

                    jsonArrayForStrategy.add(farmCustomStrategyView);
                }

            }
        }

        strategyDetails.put("jsonArrayForStrategy", jsonArrayForStrategy);
        strategyDetails.put("jsonArrayForStrategyOutput", jsonArrayForStrategyOutput);

        return strategyDetails;
    }

    private Map<String, JSONArray> getCropDetailsForStrategy(Map<FarmCustomStrategyView, JSONObject> strategyDetailsForFarm){

        JSONArray jsonArrayForCrop = new JSONArray();
        JSONArray jsonArrayForCropHeader = new JSONArray();

        Set<Map.Entry<FarmCustomStrategyView, JSONObject>> entries = strategyDetailsForFarm.entrySet();

        int counter = 0;
        for (Map.Entry<FarmCustomStrategyView, JSONObject> entry : entries) {
            FarmCustomStrategyView farmCustomStrategyView = entry.getKey();
            JSONObject strategyDetails = entry.getValue();

            FarmInfoView farmInfoView = (FarmInfoView)strategyDetails.get("farmInfoView");

            JSONObject jsonObjectForStrategy = new JSONObject();
            JSONArray cropArray = new JSONArray();
            JSONArray jsonArrayForCropHeaderDetails = new JSONArray();

            jsonObjectForStrategy.put("strategyName", farmCustomStrategyView.getStrategyName());
            jsonObjectForStrategy.put("strategyId", farmCustomStrategyView.getId());

            if(Objects.equals(farmInfoView.getStrategy(), PlanByStrategy.PLAN_BY_ACRES)){
                for (FarmOutputDetailsView farmOutputDetailsView : (List<FarmOutputDetailsView>) strategyDetails.get("farmOutputDetails")) {
                    if (farmOutputDetailsView.getCropTypeView().getSelected()) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("name", farmOutputDetailsView.getCropTypeView().getCropName());
                        jsonObject.put("amount", farmOutputDetailsView.getUsedAcresAsDouble());
                        cropArray.add(jsonObject);
                        System.out.println(jsonObject);
                        if(!jsonArrayForCropHeaderDetails.contains(jsonObject)){
                            jsonArrayForCropHeaderDetails.add(jsonObject);
                        }
                    }
                }
            } else if(Objects.equals(farmInfoView.getStrategy(), PlanByStrategy.PLAN_BY_FIELDS)){

                Map<String, String> mapForAcre = (Map<String, String>)strategyDetails.get("hashMapForAcre");

                Set<Map.Entry<String, String>> mapForAcreEntries = mapForAcre.entrySet();

                for (Map.Entry<String, String> mapForAcreEntry : mapForAcreEntries) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("amount", mapForAcreEntry.getValue().split(" ")[0]);
                    jsonObject.put("name", mapForAcreEntry.getKey());
                    cropArray.add(jsonObject);

                    if (!jsonArrayForCropHeaderDetails.contains(jsonObject)) {
                        jsonArrayForCropHeaderDetails.add(jsonObject);
                    }
                }
            }
            jsonObjectForStrategy.put("cropKey", jsonArrayForCropHeaderDetails);
            jsonObjectForStrategy.put("details", cropArray);

            jsonArrayForCropHeader.add(jsonObjectForStrategy);
            jsonArrayForCrop.add(jsonObjectForStrategy);
        }

        for (Object o : jsonArrayForCrop) {

            JSONObject jsonObject = (JSONObject)o;

            JSONArray cropDetailsObject = (JSONArray) jsonObject.get("details");

            for(Object object :jsonArrayForCropHeader) {
                JSONObject headerForJsonObject = (JSONObject) object;
                JSONArray headerCrop = (JSONArray) headerForJsonObject.get("cropKey");
                for (Object headerCropDetail : headerCrop) {
                    JSONObject jsonObjectForHeader = (JSONObject) headerCropDetail;
                    Object cropHeaderName = jsonObjectForHeader.get("name");

                    Boolean flag = false;
                    String headerCropName = cropHeaderName.toString();

                    for (Object s : cropDetailsObject) {
                        JSONObject cropNameInDetails = (JSONObject) s;
                        String cropName = cropNameInDetails.get("name").toString();
                        if (cropName.equals(headerCropName)) {
                            flag = true;
                            break;
                        }
                    }

                    if (!flag) {
                        JSONObject jsonObjectAdd = new JSONObject();
                        jsonObjectAdd.put("amount", 0);
                        jsonObjectAdd.put("name", headerCropName);
                        cropDetailsObject.add(jsonObjectAdd);
                    }
                }
            }

        }

        Map<String, JSONArray> cropDetails = new HashMap<>();

        cropDetails.put("jsonArrayForCropHeader", jsonArrayForCropHeader);
        cropDetails.put("jsonArrayForCrop", jsonArrayForCrop);
        return cropDetails;

    }

    private Map <String, JSONArray> getResourceDetailsForStrategy(Map <FarmCustomStrategyView, JSONObject> strategyDetailsForFarm) {

        JSONArray jsonArrayForResource = new JSONArray ();
        JSONArray jsonArrayForResourceHeader = new JSONArray ();

        Set <Map.Entry <FarmCustomStrategyView, JSONObject>> entries = strategyDetailsForFarm.entrySet ();

        int counter = 0;
        for (Map.Entry <FarmCustomStrategyView, JSONObject> entry : entries) {

            FarmCustomStrategyView farmCustomStrategyView = entry.getKey ();
            JSONObject strategyDetails = entry.getValue ();

            JSONObject jsonObjectForStrategy = new JSONObject ();
            JSONArray jsonArray = new JSONArray ();

            jsonObjectForStrategy.put ( "strategyName", farmCustomStrategyView.getStrategyName () );
            jsonObjectForStrategy.put ( "strategyId", farmCustomStrategyView.getId () );

            Map <String, String> cropResourceUsed = (Map <String, String>) strategyDetails.get ( "cropResourceUsed" );
            double workingCapitalUsed = 0.0, estimateIncome = 0.0;
           String returnWorkingCapital="null";

            if (Objects.equals ( farmCustomStrategyView.getFarmCustomStrategy ().getFarmInfo ().getStrategy (), PlanByStrategy.PLAN_BY_ACRES )) {
                List <FarmOutputDetailsView> farmOutputDetailsViewList = (List <FarmOutputDetailsView>) strategyDetails.get ( "farmOutputDetails" );
                for (FarmOutputDetailsView farmOutputDetailsView : farmOutputDetailsViewList) {
                    estimateIncome += farmOutputDetailsView.getProfitDouble ();
                }
            } else if (Objects.equals ( farmCustomStrategyView.getFarmCustomStrategy ().getFarmInfo ().getStrategy (), PlanByStrategy.PLAN_BY_FIELDS )) {
                List <FarmOutputDetailsForFieldView> farmOutputDetailsForFieldViewList = (List <FarmOutputDetailsForFieldView>) strategyDetails.get ( "farmOutputDetails" );
                for (FarmOutputDetailsForFieldView farmOutputDetailsForFieldView : farmOutputDetailsForFieldViewList) {
                    estimateIncome += farmOutputDetailsForFieldView.getProfitAsDouble ();
                }
            }
            int index=0;
            int sizeOfList=((List<CropResourceUsageView>) strategyDetails.get ( "resourceList"  )).size ();
            for (CropResourceUsageView cropResourceUsageView : (List <CropResourceUsageView>) strategyDetails.get ( "resourceList" )) {
                index++;
                JSONObject jsonObjectForWorkReturn = new JSONObject ();
                JSONObject jsonObject = new JSONObject ();
                jsonObject.put ( "strategyName", farmCustomStrategyView.getStrategyName () );
                if (cropResourceUsageView.getCropResourceUse ().equalsIgnoreCase ( "capital" )) {
                    jsonObject.put ( "name", "Working Capital" );
                    workingCapitalUsed = Double.parseDouble ( AgricultureStandardUtils.removeAllCommas ( cropResourceUsed.get ( cropResourceUsageView.getCropResourceUse () ) ) );
                    jsonObject.put ( "amount", "$" + cropResourceUsed.get ( cropResourceUsageView.getCropResourceUse () ) );
                } else if (cropResourceUsageView.getCropResourceUse ().equalsIgnoreCase ( "land" )) {
                    jsonObject.put ( "name", "Acreage Assigned" );
                    workingCapitalUsed = Double.parseDouble ( AgricultureStandardUtils.removeAllCommas ( cropResourceUsed.get ( cropResourceUsageView.getCropResourceUse () ) ) );
                    jsonObject.put ( "amount", cropResourceUsed.get ( cropResourceUsageView.getCropResourceUse () ) );
                } else {
                    String usedResources;
                    jsonObject.put ( "name", cropResourceUsageView.getCropResourceUse () );
                    String resourcesUsed =  cropResourceUsed.get ( cropResourceUsageView.getCropResourceUse () ) ;
                    if (resourcesUsed!=null){
                        usedResources = resourcesUsed;
                    }else {
                        usedResources = "N/A";
                    }
                    jsonObject.put ( "amount", usedResources);
                }
                    /*if (cropResourceUsageView.getCropResourceUse ().equalsIgnoreCase ( "capital" ) || cropResourceUsageView.getCropResourceUse ().equalsIgnoreCase ( "Land" )) {
                        jsonObject.put ( "amount", "N/A" );
                    } else {
//                        workingCapitalUsed = Double.parseDouble ( AgricultureStandardUtils.removeAllCommas ( cropResourceUsed.get ( cropResourceUsageView.getCropResourceUse () ) ) );
                        jsonObject.put ( "amount", cropResourceUsageView.getCropResourceUseAmount () );
                    }
                }*/
                if (index == sizeOfList) {
                    if (workingCapitalUsed != 0 && estimateIncome!=0) {
                        returnWorkingCapital = String.valueOf ( (AgricultureStandardUtils.doubleWithOneDecimal  ( estimateIncome / workingCapitalUsed ) ) );
                    }else {
                        returnWorkingCapital="NA";
                    }
                    jsonObjectForWorkReturn.put ( "strategyName", farmCustomStrategyView.getStrategyName () );
                    jsonObjectForWorkReturn.put ( "name", "Return on Working Capital" );
                    jsonObjectForWorkReturn.put ( "amount", "" + returnWorkingCapital );
                }
                jsonArray.add ( jsonObject );
                if (!(jsonObjectForWorkReturn.isEmpty ())) {
                    jsonArray.add ( jsonObjectForWorkReturn );
                }
                if (counter == 0) {
                    if(cropResourceUsageView.isActive()) {
                        if (cropResourceUsageView.getCropResourceUse().equalsIgnoreCase("land")) {
//                        jsonArrayForResourceHeader.add(cropResourceUsageView.getCropResourceUse());
                            jsonArrayForResourceHeader.add("Acreage Assigned");
                        } else if (cropResourceUsageView.getCropResourceUse().equalsIgnoreCase("capital")) {
                            jsonArrayForResourceHeader.add("Working Capital");
                        } else {
                            jsonArrayForResourceHeader.add(cropResourceUsageView.getCropResourceUse());
                        }
                    }
                }
            }
            jsonObjectForStrategy.put ( "details", jsonArray );
            jsonArrayForResource.add ( jsonObjectForStrategy );
            counter++;
        }

        Map <String, JSONArray> resourceDetails = new HashMap <> ();

        for (Object o : jsonArrayForResource) {

            JSONObject jsonObject = (JSONObject) o;

            String strategyNameObject = jsonObject.get ( "strategyName" ).toString ();
            JSONArray resourceDetailsObject = (JSONArray) jsonObject.get ( "details" );

            for (Object o1 : jsonArrayForResourceHeader) {
                Boolean flag = false;
                String headerResourceName = o1.toString ();

                for (Object s : resourceDetailsObject) {
                            JSONObject resourceNameInDetails = (JSONObject) s;
                    String resourceName = resourceNameInDetails.get ( "name" ).toString ();
                    if (resourceName.equals ( headerResourceName )) {
                        flag = true;
                        break;
                    }
                }

                if (!flag) {
                    JSONObject jsonObjectAdd = new JSONObject ();
                    jsonObjectAdd.put ( "amount", "N/A");
                    jsonObjectAdd.put ( "strategyName", strategyNameObject );
                    jsonObjectAdd.put ( "name", headerResourceName );
                    resourceDetailsObject.add ( jsonObjectAdd );
                }
            }
        }

        jsonArrayForResourceHeader.add ( "Return on Working Capital" );
        resourceDetails.put ( "jsonArrayForResource", jsonArrayForResource );
        resourceDetails.put ( "jsonArrayForResourceHeader", jsonArrayForResourceHeader );

        return resourceDetails;

    }

    private Map<String, JSONArray> getHighRiskAndConservationForStrategy(Map<FarmCustomStrategyView, JSONObject> strategyDetailsForFarm, int[] strategyIdArray){

        JSONArray jsonArrayForConservationCrop = new JSONArray();
        JSONArray jsonArrayForConversion = new JSONArray();
        JSONArray jsonArrayForHighRiskCrop = new JSONArray();


        Set<Map.Entry<FarmCustomStrategyView, JSONObject>> entries = strategyDetailsForFarm.entrySet();

        RiskAndConservationMgmtDataBuilder dataBuilder = new RiskAndConservationMgmtDataBuilder(farmOutputCalculationService);


        for (Map.Entry<FarmCustomStrategyView, JSONObject> entry : entries) {
            FarmCustomStrategyView farmCustomStrategyView = entry.getKey();
            JSONObject strategyDetails = entry.getValue();

            for (int strategyId : strategyIdArray) {

                if (farmCustomStrategyView.getId().equals(strategyId)) {

                    FarmInfoView farmInfoView = (FarmInfoView) strategyDetails.get("farmInfoView");

                    JSONObject conservationObject = new JSONObject();
                    conservationObject.put("strategyName", farmCustomStrategyView.getStrategyName());
                    conservationObject.put("strategyId", farmCustomStrategyView.getId());

                    JSONArray conservationArray = new JSONArray();
                    conservationArray.add(dataBuilder.getPpFromConservationRiskCrop(farmInfoView, strategyDetails));
                    conservationArray.add(dataBuilder.getAcreageConservationRiskCrop(farmInfoView, strategyDetails));
                    conservationObject.put("details", conservationArray);

                    jsonArrayForConservationCrop.add(conservationObject);


                    JSONObject conversionObject = new JSONObject();
                    conversionObject.put("strategyName", farmCustomStrategyView.getStrategyName());
                    conversionObject.put("strategyId", farmCustomStrategyView.getId());

                    JSONArray conversionArray = new JSONArray();
                    conversionArray.add(dataBuilder.getAcreageConservation(farmInfoView, strategyDetails));
                    conversionObject.put("details", conversionArray);
                    jsonArrayForConversion.add(conversionObject);


                    JSONObject highRiskObject = new JSONObject();
                    highRiskObject.put("strategyName", farmCustomStrategyView.getStrategyName());
                    highRiskObject.put("strategyId", farmCustomStrategyView.getId());

                    JSONArray highRiskArray = new JSONArray();
                    highRiskArray.add(dataBuilder.getCapitalUsed(strategyDetails));
                    highRiskArray.add(dataBuilder.getReturnOnLand(farmInfoView, strategyDetails));
                    highRiskArray.add(dataBuilder.getPpFromSingleProfitCrop(farmInfoView, strategyDetails));
                    highRiskArray.add(dataBuilder.getPpFromTwoProfitCrop(farmInfoView, strategyDetails));
                    highRiskArray.add(dataBuilder.getForwardSoldProfit(farmInfoView, strategyDetails));
                    highRiskArray.add(dataBuilder.getProfitFromMinPriceAndYield(farmInfoView, strategyDetails));
                    highRiskArray.add(dataBuilder.getPpFromHighRiskCrop(farmInfoView, strategyDetails));
                    highRiskArray.add(dataBuilder.getAcreageHighRiskCrop(farmInfoView, strategyDetails));
                    highRiskObject.put("details", highRiskArray);

                    jsonArrayForHighRiskCrop.add(highRiskObject);

                }
            }
        }

        Map<String, JSONArray> output = new HashMap<>();

        output.put("jsonArrayForHighRiskCropHeader", getHiRiskCropHeader());
        output.put("jsonArrayForHighRiskCrop", jsonArrayForHighRiskCrop);

        output.put("jsonArrayForConservationCropHeader", getConservationCropHeader());
        output.put("jsonArrayForConservationCrop", jsonArrayForConservationCrop);
        output.put("jsonArrayForConversion", jsonArrayForConversion);

        return output;

    }

    private JSONArray getConservationCropHeader(){
        JSONArray conservationCropHeader = new JSONArray();

        conservationCropHeader.add("% Est. Income in Conservation Crops");
        conservationCropHeader.add("% Acreage in Conservation Crops");

        return conservationCropHeader;
    }

    private JSONArray getHiRiskCropHeader(){
        JSONArray highRiskCropHeader = new JSONArray();

        highRiskCropHeader.add("Working Capital Used");
        highRiskCropHeader.add("Return on Land $/acre");
        highRiskCropHeader.add("% Est. Income in Top One Crop");
        highRiskCropHeader.add("% Est. Income in Top Two Crops");
        highRiskCropHeader.add("% Est. Income Forward Sold");
        highRiskCropHeader.add("Est. Income Given Min Prices and Min Yields");
        highRiskCropHeader.add("% Est. Income in Hi-Risk Crops");
        highRiskCropHeader.add("% Acreage in Hi-Risk Crops");
        return highRiskCropHeader;
    }

    private JSONArray getVarianceGraphDetails(Map<FarmCustomStrategyView, JSONObject> strategyDetailsForFarm, FarmInfoView farmInfoView, int[] strategyIdArray){

//        Set<FarmCustomStrategyView> farmCustomStrategyViewSet = strategyDetailsForFarm.keySet();

//        int[] strategyIdArray = new Integer[farmCustomStrategyViewSet.size()];

//        Iterator<FarmCustomStrategyView> iterator = farmCustomStrategyViewSet.iterator();
//        int index = 0;
//        while (iterator.hasNext()){
//
//            FarmCustomStrategyView farmCustomStrategyView = iterator.next();
//            strategyIdArray[index] = farmCustomStrategyView.getId();
//            index++;
//        }
        strategyComparisonDataBuilder = new StrategyComparisonDataBuilder(strategyDetailsForFarm, strategyIdArray);
        strategyComparisonDataBuilder.setFarmOutputCalculationService(farmOutputCalculationService);

        return strategyComparisonDataBuilder.getVarianceGraphDetails(farmInfoView);
    }

    @Override
    public JSONArray getVarianceGraphCustomisedDetails(FarmInfoView farmInfoView,
                                                       int[] strategyIdArray,
                                                       int[] cropPriceSelection,
                                                       int[] cropYieldSelection,
                                                       int[] cropProductionCostSelection,
                                                       JSONObject rangeValuesObject) throws JSONException {
        Map<FarmCustomStrategyView, JSONObject> strategyDetailsForFarm = getStrategyDetailsForFarm(farmInfoView);

        strategyComparisonDataBuilder = new StrategyComparisonDataBuilder(strategyDetailsForFarm, strategyIdArray);

        strategyComparisonDataBuilder.setFarmOutputCalculationService(farmOutputCalculationService);

        return strategyComparisonDataBuilder.getVarianceGraphCustomizedDetails(farmInfoView,cropPriceSelection,cropYieldSelection,cropProductionCostSelection, rangeValuesObject);

    }

    public JSONObject getAllScenarioAnalysisDetails(FarmInfoView farmInfoView, int farmId, int scenarioId) {

        farmInfoView = farmService.getBaselineFarmDetails(farmId);

        JSONObject outputDetails = new JSONObject();
        JSONArray jsonArrayForScenario = new JSONArray();

        Map<FarmStrategyScenarioView, Map<FarmCustomStrategyView, JSONObject>> allScenarioDetails = farmDetailsContainerService.getAllScenarioDetails(farmInfoView);
        Set<Map.Entry<FarmStrategyScenarioView, Map<FarmCustomStrategyView, JSONObject>>> farmStrategyScenarioViewSet = allScenarioDetails.entrySet();

        for (Map.Entry<FarmStrategyScenarioView, Map<FarmCustomStrategyView, JSONObject>> strategyScenario : farmStrategyScenarioViewSet) {
            FarmStrategyScenarioView scenarioKey = strategyScenario.getKey();
            Map<FarmCustomStrategyView, JSONObject> scenarioValue = strategyScenario.getValue();

          int id = scenarioKey.getScenarioId();
            String scenarioName = scenarioKey.getScenarioName();

            if (id == scenarioId) {

                for (Map.Entry<FarmCustomStrategyView, JSONObject> strategyScenarioJSONArray : scenarioValue.entrySet()) {
                    FarmCustomStrategyView strategyViewKey = strategyScenarioJSONArray.getKey();
                    JSONObject scenarioDataValue = strategyScenarioJSONArray.getValue();

                    farmInfoView = (FarmInfoView) scenarioDataValue.get("farmInfoView");

                    JSONObject jsonObjectForStrategy = new JSONObject();

                    jsonObjectForStrategy.put("strategyName", strategyViewKey.getStrategyName());
                    jsonObjectForStrategy.put("strategyId", strategyViewKey.getId());

                    JSONArray cropArray = new JSONArray();

                    if (Objects.equals(farmInfoView.getStrategy(), PlanByStrategy.PLAN_BY_ACRES)) {
                        Object valueForScenarios = scenarioDataValue.get("potentialProfit");
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("potentialProfit", valueForScenarios);
                        cropArray.add(jsonObject);
                    } else if (Objects.equals(farmInfoView.getStrategy(), PlanByStrategy.PLAN_BY_FIELDS)) {
                        Object valueForScenarios = scenarioDataValue.get("potentialProfit");
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("potentialProfit", valueForScenarios);
                        cropArray.add(jsonObject);
                    }

                    jsonObjectForStrategy.put("scenarioId", id);
                    jsonObjectForStrategy.put("scenarioName", scenarioName);
                    jsonObjectForStrategy.put("details", cropArray);

                    jsonArrayForScenario.add(jsonObjectForStrategy);
                }
            }
            outputDetails.put("scenarioValue", jsonArrayForScenario);
        }

        return outputDetails;
    }
}
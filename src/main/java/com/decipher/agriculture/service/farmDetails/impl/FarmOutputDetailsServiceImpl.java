package com.decipher.agriculture.service.farmDetails.impl;

import com.decipher.AppConstants;
import com.decipher.agriculture.data.farm.CropType;
import com.decipher.agriculture.data.farm.PlanByStrategy;
import com.decipher.agriculture.service.farmDetails.FarmOutputDetailsService;
import com.decipher.util.AgricultureStandardUtils;
import com.decipher.util.PlantingProfitLogger;
import com.decipher.view.form.farmDetails.CropResourceUsageView;
import com.decipher.view.form.farmDetails.CropTypeView;
import com.decipher.view.form.farmDetails.CropsGroupView;
import com.decipher.view.form.farmDetails.FarmInfoView;
import com.decipher.view.form.farmDetails.FarmOutputDetailsForFieldView;
import com.decipher.view.form.farmDetails.FarmOutputDetailsView;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

import static java.lang.Double.parseDouble;

/**
 * Created on 3/2/17 2:38 PM by Abhishek Samuel
 * Software Engineer
 * abhisheks@decipherzone.com
 * Decipher Zone Softwares LLP
 * www.decipherzone.com
 */
@Service
@SuppressWarnings("unchecked")
public class FarmOutputDetailsServiceImpl implements FarmOutputDetailsService {

    private static final String CROP_NAME = "cropName";
    private static final String ACREAGE = "acreage";
    private static final String PROFIT = "profit";
    private static final String RATIO = "ratio";
    private static final String INDEX = "index";
    private static final String WORKRETURN = "workreturn";
    private static final String RATING = "rating";
    private static final String RATINGFORWORKRETURN = "ratingforworkreturn";

    private static final String YES = "Yes";
    private static final String NO = "No";
    private static final String Likely = "Likely";
    private static final String PARTIAL = "Partial";


    private static final String RESOURCE_STATUS = "status";
    private static final String RESOURCE_NAME = "resourceName";
    private static final String UOM_RESOURCE = "uoMResource";
    private static final String RESOURCE_TOTAL_AVAILABLE = "totalAvailable";
    private static final String RESOURCE_USED = "used";
    private static final String RESOURCE_UNUSED = "unused";
    private static final String RESOURCE_IMPACTING_PROFIT = "impactingProfit";

    private static final String MIN_LIMIT = "minLimit";
    private static final String MAX_LIMIT = "maxLimit";
    private static final String IMPACTING_INCOME = "impactingIncome";
    private static final String INC_DEC_INCOME = "incDecIncome";
    private static final String MESSAGE = "message";


    @Override
    public JSONArray buildForwardSalesContent(JSONObject outputDetails) {
        JSONArray jsonArray = new JSONArray();
        FarmInfoView farmInfoView = (FarmInfoView) outputDetails.get("farmInfoView");
        List<CropTypeView> cropTypeViewList = (List<CropTypeView>) outputDetails.get("cropTypeView");

        for (CropTypeView cropTypeView : cropTypeViewList) {
            if (cropTypeView.getSelected()) {
                JSONObject jsonObject = new JSONObject();
                boolean flag = false;
                if (Objects.equals(farmInfoView.getStrategy(), PlanByStrategy.PLAN_BY_ACRES)) {

                    List<FarmOutputDetailsView> farmOutputDetailsViewList = (List<FarmOutputDetailsView>) outputDetails.get("farmOutputDetails");
                    for (FarmOutputDetailsView farmOutputDetailsView : farmOutputDetailsViewList) {

                        if (Objects.equals(cropTypeView.getId(), farmOutputDetailsView.getCropTypeView().getId())) {
                            if (farmOutputDetailsView.getForProposed() && !farmOutputDetailsView.getUsedAcres().equalsIgnoreCase("0")) {
                                flag = true;
                            }
                        }
                    }

                } else if (Objects.equals(farmInfoView.getStrategy(), PlanByStrategy.PLAN_BY_FIELDS)) {
                    List<FarmOutputDetailsForFieldView> farmOutputDetailsForFieldViewList = (List<FarmOutputDetailsForFieldView>) outputDetails.get("farmOutputDetails");

                    for (FarmOutputDetailsForFieldView farmOutputDetailsForFieldView : farmOutputDetailsForFieldViewList) {

                        if (Objects.equals(cropTypeView.getId(), farmOutputDetailsForFieldView.getCropTypeView().getId())) {
                            if (farmOutputDetailsForFieldView.isForProposed() && !farmOutputDetailsForFieldView.getUsedAcres().equalsIgnoreCase("0")) {
                                flag = true;
                            }
                        }
                    }

                }


                jsonObject.put("cropName", cropTypeView.getCropName());

                if (cropTypeView.getProposedchecked() || cropTypeView.getFirmchecked().equalsIgnoreCase("true")) {
//                    jsonObject.put("forwardSalesAmount", cropTypeView.getAcresStr() + " acres at " + cropTypeView.getForwardPrice() + "/" + cropTypeView.getCropUOM());
                    jsonObject.put("forwardSalesAmount", cropTypeView.getAcresStr() +" "+"acres");
                    jsonObject.put("forwardSalesPrice", cropTypeView.getForwardPrice() + "/" + cropTypeView.getCropUOM());
                } else {
                    jsonObject.put("forwardSalesAmount", "");
                }

                if (cropTypeView.getFirmchecked().equalsIgnoreCase("true")) {
                    jsonObject.put("firmProposedCheck", "Firm");
                } else if (cropTypeView.getProposedchecked()) {
                    jsonObject.put("firmProposedCheck", "Proposed");
                } else {
                    jsonObject.put("firmProposedCheck", "N/A");
                }
                if(Objects.equals(farmInfoView.getStrategy(), PlanByStrategy.PLAN_BY_FIELDS)) {
                    List<FarmOutputDetailsForFieldView> farmOutputDetailsForFieldViewList = (List<FarmOutputDetailsForFieldView>) outputDetails.get("farmOutputDetails");
                    Double usedAcres = 0.0;
                    for (FarmOutputDetailsForFieldView farmOutputDetailsForFieldView : farmOutputDetailsForFieldViewList) {
                        if (Objects.equals(cropTypeView.getId(), farmOutputDetailsForFieldView.getCropTypeView().getId()) && (farmOutputDetailsForFieldView.isForProposed() || farmOutputDetailsForFieldView.isForFirm())) {
                            if (farmOutputDetailsForFieldView.isForFirm() && farmOutputDetailsForFieldView.getCropTypeView().getFirmchecked().equalsIgnoreCase("true"))
                                usedAcres = farmOutputDetailsForFieldView.getCropTypeView().getForwardAcres();
                            else if (farmOutputDetailsForFieldView.isForProposed() && farmOutputDetailsForFieldView.getCropTypeView().getProposedchecked()){
                                usedAcres += farmOutputDetailsForFieldView.getUsedAcresAsDouble();
                            }

                            String contractAmount = cropTypeView.getAcresStr().equalsIgnoreCase("") ? "0" : cropTypeView.getAcresStr();
                            double contractAmountAsDouble = Double.parseDouble(contractAmount);

                            jsonObject.put("amountFilled", AgricultureStandardUtils.withoutDecimalAndComma(usedAcres));

                            double amountUnfilled = contractAmountAsDouble-usedAcres;
                            Integer amountUnfilledAsDouble = AgricultureStandardUtils.doubleToInteger(amountUnfilled);

                            jsonObject.put("amountUnfilled", amountUnfilledAsDouble);

                            /*Amt Filled and Unfilled Column in Forward Sales*/
                            if (farmOutputDetailsForFieldView.getCropTypeView().getFirmchecked().equalsIgnoreCase("true")) {
                                jsonObject.put("status", YES);
                            } else if (farmOutputDetailsForFieldView.getCropTypeView().getProposedchecked()) {
                                if (usedAcres == 0) {
                                    jsonObject.put("status", NO);
                                } else if (usedAcres == contractAmountAsDouble) {
                                    jsonObject.put("status", YES);
                                } else {
                                    jsonObject.put("status", PARTIAL);
                                }
                            }
                        }
                    }
                }
                if(Objects.equals(farmInfoView.getStrategy(), PlanByStrategy.PLAN_BY_ACRES)) {
                    List<FarmOutputDetailsView> farmOutputDetailsViewList = (List<FarmOutputDetailsView>) outputDetails.get("farmOutputDetails");
                    Double usedAcres = 0.0;
                    for (FarmOutputDetailsView farmOutputDetailsView : farmOutputDetailsViewList) {
                        if (Objects.equals(cropTypeView.getId(), farmOutputDetailsView.getCropTypeView().getId()) && (farmOutputDetailsView.getForFirm().equals(true) || farmOutputDetailsView.getForProposed().equals(true))) {
                            if (farmOutputDetailsView.getForFirm() && farmOutputDetailsView.getCropTypeView().getFirmchecked().equalsIgnoreCase("true"))
                                usedAcres = farmOutputDetailsView.getCropTypeView().getForwardAcres();
                            else if (farmOutputDetailsView.getForProposed() && farmOutputDetailsView.getCropTypeView().getProposedchecked()){
                                usedAcres += farmOutputDetailsView.getUsedAcresAsDouble();
                            }
                            String contractAmount = cropTypeView.getAcresStr().equalsIgnoreCase("") ? "0" : cropTypeView.getAcresStr();
                            double contractAmountAsDouble = Double.parseDouble(contractAmount);

                            jsonObject.put("amountFilled", farmOutputDetailsView.getUsedAcres());

                            double amountUnfilled = contractAmountAsDouble - usedAcres;
                            Integer amountUnfilled1 = AgricultureStandardUtils.doubleToInteger(amountUnfilled);
                            jsonObject.put("amountUnfilled", amountUnfilled1);

                            if (farmOutputDetailsView.getCropTypeView().getFirmchecked().equalsIgnoreCase("true")) {
                                jsonObject.put("status", YES);
                            } else if (farmOutputDetailsView.getCropTypeView().getProposedchecked()) {
                                if (usedAcres == 0) {
                                    jsonObject.put("status", NO);
                                } else if (usedAcres == contractAmountAsDouble) {
                                    jsonObject.put("status", YES);
                                } else {
                                    jsonObject.put("status", PARTIAL);
                                }
                            }
                        }
                    }
                }
                jsonArray.add(jsonObject);
            }
        }

            return jsonArray;
    }

    @Override
    public JSONArray buildCropLimitContent(JSONObject outputDetails) {
        JSONArray jsonArray = new JSONArray();

        List<CropTypeView> cropTypeViewList = (List<CropTypeView>) outputDetails.get("cropTypeView");
        FarmInfoView farmInfoView = (FarmInfoView) outputDetails.get("farmInfoView");
        for (CropTypeView cropTypeView : cropTypeViewList) {
            if (cropTypeView.getSelected ()) {
                JSONArray jsonArray1 = getCropLimit ( farmInfoView, cropTypeView.getMinimumAcres (), cropTypeView.getMaximumAcres (), cropTypeView, null, outputDetails );
                JSONObject jsonObject = new JSONObject ();

                for (int i = 0; i < jsonArray1.size (); i++) {
                    jsonObject = (JSONObject) jsonArray1.get ( i );
                    jsonObject.put ( "cropName", cropTypeView.getCropName () );
                    if(jsonObject.get(IMPACTING_INCOME).toString().equalsIgnoreCase("No")){
                        jsonObject.put("incDecIncome","--");
                    }
                    if (!jsonObject.get ( IMPACTING_INCOME ).toString ().equalsIgnoreCase ( "--" )) {
                        jsonObject.put ( "acreagePlanted", getCropAcreage ( cropTypeView, outputDetails, false ) );
                    } else {
                        jsonObject.put ( "acreagePlanted", "--" );
                    }
                    jsonArray.add ( jsonObject );
                }
                if (cropTypeView.getFirmchecked ().equalsIgnoreCase ( "true" )) {

                    JSONArray jsonArrayForFirm = getCropLimit ( farmInfoView, AgricultureStandardUtils.withoutDecimalAndComma ( cropTypeView.getForwardAcres () ), "", cropTypeView, null, outputDetails );
                    JSONObject jsonObjectForFirm;
                    for (int i = 0; i < jsonArrayForFirm.size (); i++) {
                        jsonObjectForFirm = (JSONObject) jsonArrayForFirm.get ( i );
                        jsonObjectForFirm.put ( "cropName", cropTypeView.getCropName () + " (Firm)" );

                        /* checking Firm Condition for Increase Decrease w.r.t Profit Index */
                        if (farmInfoView.getStrategy().equals(PlanByStrategy.PLAN_BY_ACRES)) {
                            List<FarmOutputDetailsView> farmOutputDetailsViewList = (List<FarmOutputDetailsView>) outputDetails.get("farmOutputDetails");
                            for (FarmOutputDetailsView farmOutputDetailsView : farmOutputDetailsViewList) {
                                if (cropTypeView != null) {
                                    if (cropTypeView.getFirmchecked().equalsIgnoreCase("true") && farmOutputDetailsView.getCropTypeView().getId().equals(cropTypeView.getId())) {
                                        if (farmOutputDetailsView.getForFirm().equals(true)) {
                                            double profitIndex = farmOutputDetailsView.getProfitIndex();
                                            if (profitIndex >= 0.8) {
                                                jsonObjectForFirm.put(INC_DEC_INCOME, "Increase");
                                                jsonObjectForFirm.put(IMPACTING_INCOME, NO);
                                                jsonObjectForFirm.put(MESSAGE, "Minimum crop limit to meet the forward contract is not impacting Estimated Income. It is likely that Estimated Income will increase if the amount of acreage under this contract can be increased.");
                                            } else if (profitIndex < 0.8) {
                                                jsonObjectForFirm.put(INC_DEC_INCOME, "Decrease");
                                                jsonObjectForFirm.put(IMPACTING_INCOME, YES);
                                                jsonObjectForFirm.put ( MESSAGE, "Minimum crop limit is impacting Estimated Income." );

                                            }
                                        }
                                    }
                                }
                            }
                        }

                        if (!jsonObjectForFirm.get ( IMPACTING_INCOME ).toString ().equalsIgnoreCase ( "--" )) {
                            jsonObjectForFirm.put ( "acreagePlanted", getCropAcreage ( cropTypeView, outputDetails, true ) );
                        } else if (jsonObjectForFirm.get ( IMPACTING_INCOME ).toString ().equalsIgnoreCase ( "--" )) {
                            jsonObjectForFirm.put ( "acreagePlanted", getCropAcreage ( cropTypeView, outputDetails, true ) );
                        } else {
                            jsonObjectForFirm.put ( "acreagePlanted", "--" );
                        }
                        jsonArray.add ( jsonObjectForFirm );
                    }
                }
            }
        }

        List<CropsGroupView> cropsGroupViewList = (List<CropsGroupView>) outputDetails.get("cropsGroupViews");

        for (CropsGroupView cropsGroupView : cropsGroupViewList) {
            JSONArray jsonArray1 = getCropLimit(farmInfoView, cropsGroupView.getMinimumAcres(), cropsGroupView.getMaximumAcres(), null, cropsGroupView, outputDetails);
            JSONObject jsonObject;

            for (int i = 1; i < jsonArray.size(); i++) {
                jsonObject = (JSONObject) jsonArray1.get(i);
                jsonObject.put("cropName", cropsGroupView.getCropsGroupName());

                if (!jsonObject.get(IMPACTING_INCOME).toString().equalsIgnoreCase("--")) {
                    int totalAcreage = 0;
                    Set<CropType> cropSet = cropsGroupView.getCropSet();
                    for (CropType cropType : cropSet) {
                        CropTypeView cropTypeView = new CropTypeView(cropType);
                        totalAcreage += Integer.parseInt(AgricultureStandardUtils.removeAllCommas(getCropAcreage(cropTypeView, outputDetails, cropTypeView.getFirmchecked().equalsIgnoreCase("true"))));
                    }
                    jsonObject.put("acreagePlanted", totalAcreage);
                } else {
                    jsonObject.put("acreagePlanted", "--");
                }
                jsonArray.add(jsonObject);
            }
        }

        return jsonArray;
    }



    private String getCropAcreage(CropTypeView cropTypeView, JSONObject outputDetails, boolean isFirm){
        FarmInfoView farmInfoView = (FarmInfoView) outputDetails.get("farmInfoView");

        if (PlanByStrategy.PLAN_BY_ACRES.equals(farmInfoView.getStrategy())) {
            List<FarmOutputDetailsView> farmOutputDetailsViewList = (List<FarmOutputDetailsView>) outputDetails.get("farmOutputDetails");

            for (FarmOutputDetailsView farmOutputDetailsView : farmOutputDetailsViewList) {

                if(farmOutputDetailsView.getForFirm() && isFirm && farmOutputDetailsView.getCropTypeView().getId().equals(cropTypeView.getId())){
                    return farmOutputDetailsView.getUsedAcres();
                }

                if(!farmOutputDetailsView.getForProposed() && !isFirm && cropTypeView.getProposedchecked() && farmOutputDetailsView.getCropTypeView().getId().equals(cropTypeView.getId())){
                    return farmOutputDetailsView.getUsedAcres();
                }

                if(!farmOutputDetailsView.getForFirm() && !farmOutputDetailsView.getForProposed() && !isFirm && !cropTypeView.getFirmchecked().equalsIgnoreCase("true") && farmOutputDetailsView.getCropTypeView().getId().equals(cropTypeView.getId())){
                    return farmOutputDetailsView.getUsedAcres();
                }
            }

        } else if (PlanByStrategy.PLAN_BY_FIELDS.equals(farmInfoView.getStrategy())){
            Map<String, String> hashMapForAcre = (Map<String, String>) outputDetails.get("hashMapForAcre");

            String cropName = cropTypeView.getCropName();
            if (isFirm && cropTypeView.getFirmchecked().equalsIgnoreCase("true")) {
                cropName += " (Firm)";
            } /*else if (cropTypeView.getProposedchecked()) {
                cropName += " (Proposed)";
            }*/

            return hashMapForAcre.get(cropName) != null ? hashMapForAcre.get(cropName).split(" ")[0] : "--";
        }

        return "";
    }

    private JSONArray getCropLimit(FarmInfoView farmInfoView, String minAcres, String maxAcres, CropTypeView cropTypeView, CropsGroupView cropsGroupView, JSONObject outputDetails) {

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray ();
        /* no max and no min acres */ if (minAcres.equalsIgnoreCase("") && maxAcres.equalsIgnoreCase("")) {
            jsonObject.put(MIN_LIMIT, "--");
            jsonObject.put(MAX_LIMIT, "--");
            jsonObject.put(IMPACTING_INCOME, "--");
            jsonObject.put(INC_DEC_INCOME, "--");
            jsonObject.put(MESSAGE, "No Limit Specified");
            jsonArray.add ( jsonObject );

        }/* max acres */ else if (minAcres.equalsIgnoreCase("") && !maxAcres.equalsIgnoreCase("")) {
            jsonObject.put(MIN_LIMIT, "--");
            jsonObject.put(MAX_LIMIT, "No more than " + maxAcres + " acres");
            String max = isIncomeImpactedForCropLimit(cropTypeView, cropsGroupView, outputDetails, "max");
            if(farmInfoView.getStrategy().equals(PlanByStrategy.PLAN_BY_FIELDS) && max.equals(NO)){
                jsonObject.put(IMPACTING_INCOME, "May be");
                jsonObject.put(INC_DEC_INCOME, "Increase");
                jsonObject.put(MESSAGE, "Maximum crop limit may be impacting Estimated Income.");
            } else {
                double totalLand = 0;
                List <CropResourceUsageView> resourceList = (List <CropResourceUsageView>) outputDetails.get ( "resourceList" );
                for (CropResourceUsageView cropResourceUsageView : resourceList) {
                    if(cropResourceUsageView.getCropResourceUse ().equalsIgnoreCase ( "land" )){
                        totalLand = Double.parseDouble ( AgricultureStandardUtils.removeAllCommas ( cropResourceUsageView.getCropResourceUseAmount ()));
                    }
                }

                String cropAcreage = getCropAcreage ( cropTypeView, outputDetails, cropTypeView.getFirmchecked ().equalsIgnoreCase ( "true" ) );
                if (!cropAcreage.equalsIgnoreCase("")  && cropAcreage.equalsIgnoreCase("_") ) {
                    if(totalLand == Double.parseDouble ( AgricultureStandardUtils.removeAllCommas ( getCropAcreage ( cropTypeView, outputDetails, cropTypeView.getFirmchecked ().equalsIgnoreCase ( "true" ) )))){
                        max = NO;
                    }
                }
                else
                    totalLand = 0.0;


                jsonObject.put(IMPACTING_INCOME, max);

                farmInfoView = (FarmInfoView) outputDetails.get("farmInfoView");

                PlanByStrategy strategy = farmInfoView.getStrategy();
                if (Objects.equals(strategy, PlanByStrategy.PLAN_BY_ACRES)) {

                    List<FarmOutputDetailsView> farmOutputDetailsViewList = (List<FarmOutputDetailsView>) outputDetails.get("farmOutputDetails");
                    for (FarmOutputDetailsView farmOutputDetailsView : farmOutputDetailsViewList) {
                        if (cropTypeView != null ) {
                            if (cropTypeView.getFirmchecked().equalsIgnoreCase("true") && farmOutputDetailsView.getCropTypeView().getId().equals(cropTypeView.getId()) ) {
                                if (farmOutputDetailsView.getForFirm() == true ) {
                                    jsonObject.put(INC_DEC_INCOME, max.equalsIgnoreCase(YES)? "Decrease" : "Increase");
                                    break;
                                } else {
                                    jsonObject.put(INC_DEC_INCOME, max.equalsIgnoreCase(YES) || max.equalsIgnoreCase ( Likely ) ? "Increase" : "--");
                                    break;
                                }

                            } else
                                jsonObject.put(INC_DEC_INCOME, max.equalsIgnoreCase(YES) || max.equalsIgnoreCase ( Likely ) ? "Increase" : "--");
                            break;
                        }
                    }
                }

//                jsonObject.put(INC_DEC_INCOME, max.equalsIgnoreCase(YES) || max.equalsIgnoreCase ( Likely ) ? "Increase" : "--");
                if (max.equalsIgnoreCase(YES)) {
                    jsonObject.put(MESSAGE, "Maximum crop limit is impacting Estimated Income.");
                } else if (max.equalsIgnoreCase ( NO )) {
                    jsonObject.put ( MESSAGE, "Maximum crop limit is not impacting Estimated Income." );
                } else {
                    jsonObject.put ( MESSAGE, "Maximum crop limit is likely impacting  Estimated Income." );
                }
            }
        jsonArray.add ( jsonObject );

        }/* min acres */ else if (!minAcres.equalsIgnoreCase("") && maxAcres.equalsIgnoreCase("")) {
            jsonObject.put(MIN_LIMIT, "At least " + minAcres + " acres");
            jsonObject.put(MAX_LIMIT, "--");
            String min = isIncomeImpactedForCropLimit(cropTypeView, cropsGroupView, outputDetails, "min");
            jsonObject.put(IMPACTING_INCOME, min);

         /*   farmInfoView = (FarmInfoView) outputDetails.get("farmInfoView");

            PlanByStrategy strategy = farmInfoView.getStrategy();
            if (Objects.equals(strategy, PlanByStrategy.PLAN_BY_ACRES)) {

                List<FarmOutputDetailsView> farmOutputDetailsViewList = (List<FarmOutputDetailsView>) outputDetails.get("farmOutputDetails");
                Double profitIndex = 0.0;
                for (FarmOutputDetailsView farmOutputDetailsView : farmOutputDetailsViewList) {
                    if(cropTypeView != null ) {
//                        if (farmOutputDetailsView.getForFirm().equals("true") && farmOutputDetailsView.getCropTypeView().getId().equals(cropTypeView.getId())) {
                            profitIndex = farmOutputDetailsView.getProfitIndex();
//                        }
                        if (profitIndex >= 0.8)
                            jsonObject.put(INC_DEC_INCOME, "Increase");
                        else if (profitIndex <0.8 )
                            jsonObject.put(INC_DEC_INCOME, "Decrease");
                    }
                }
            }*/
//
//            if (Objects.equals(strategy, PlanByStrategy.PLAN_BY_FIELDS)) {
//
//                Map<String, String> hashMapForAcre = (Map<String, String>) outputDetails.get("hashMapForAcre");
//                Map<String, String> hashMapForProfit = (Map<String, String>) outputDetails.get("hashMapForProfit");
//                if (cropTypeView != null) {
//                    Double profitIndex = 0.0;
//                    if(cropTypeView.getFirmchecked().equalsIgnoreCase("true") || hashMapForAcre.containsKey(cropTypeView.getCropName() + " (Firm)")){
////                        profitIndex = Double.valueOf(hashMapForProfit.get("profitIndex"));
//                    }
//                    if (profitIndex >= 0.8)
//                        jsonObject.put(INC_DEC_INCOME, "Increase");
//                    else if (profitIndex <0.8 )
//                        jsonObject.put(INC_DEC_INCOME, "Decrease");
//                }
//            }*/

            farmInfoView = (FarmInfoView) outputDetails.get("farmInfoView");

            PlanByStrategy strategy = farmInfoView.getStrategy();
            if (Objects.equals(strategy, PlanByStrategy.PLAN_BY_ACRES)) {

                List<FarmOutputDetailsView> farmOutputDetailsViewList = (List<FarmOutputDetailsView>) outputDetails.get("farmOutputDetails");
                for (FarmOutputDetailsView farmOutputDetailsView : farmOutputDetailsViewList) {
                    if (cropTypeView != null ) {
                       if (cropTypeView.getFirmchecked().equalsIgnoreCase("true") && farmOutputDetailsView.getCropTypeView().getId().equals(cropTypeView.getId()) ) {
                           if (farmOutputDetailsView.getForFirm().equals(true) ) {
                               jsonObject.put(INC_DEC_INCOME, min.equalsIgnoreCase(YES)? "Decrease" : "Increase");
                               break;
                           } else {
                               jsonObject.put(INC_DEC_INCOME, min.equalsIgnoreCase(YES) || min.equalsIgnoreCase ( Likely ) ? "Decrease" : "--");

                           }

                       } else
                           jsonObject.put(INC_DEC_INCOME, min.equalsIgnoreCase(YES) || min.equalsIgnoreCase ( Likely ) ? "Decrease" : "--");
                    }
                }
            }

            /*if(cropTypeView.getFirmchecked ().equalsIgnoreCase ( "true" ) ){
                jsonObject.put(INC_DEC_INCOME, min.equalsIgnoreCase(YES)? "Decrease" : "Increase");
            }else{
                jsonObject.put(INC_DEC_INCOME, min.equalsIgnoreCase(YES) || min.equalsIgnoreCase ( Likely ) ? "Decrease" : "--");
            }*/
//            jsonObject.put(INC_DEC_INCOME, min.equalsIgnoreCase(YES) || min.equalsIgnoreCase ( Likely ) ? "Increase" : "--");

            if (min.equalsIgnoreCase ( YES )) {
                jsonObject.put ( MESSAGE, "Minimum crop limit is impacting Estimated Income." );
            } else if (min.equalsIgnoreCase ( NO )) {
                jsonObject.put ( MESSAGE, "Minimum crop limit is not impacting Estimated Income." );
            } else {
                jsonObject.put ( MESSAGE, "Minimum crop limit is likely impacting Est  Income." );
            }
            jsonArray.add ( jsonObject );
        }/* min and max acres */ else if (!minAcres.equalsIgnoreCase("") && !maxAcres.equalsIgnoreCase("")) {

            jsonObject.put(MIN_LIMIT, "At least " + minAcres + " acres");
            jsonObject.put(MAX_LIMIT, "--");
            String min = isIncomeImpactedForCropLimit(cropTypeView, cropsGroupView, outputDetails, "min");
            jsonObject.put(IMPACTING_INCOME, min);
            jsonObject.put(INC_DEC_INCOME, min.equalsIgnoreCase(YES) || min.equalsIgnoreCase ( Likely ) ? "Decrease" : "--");
//            jsonObject.put(INC_DEC_INCOME, min.equalsIgnoreCase(YES) ? "Increase" : "--");

            if (min.equalsIgnoreCase(YES)) {
                jsonObject.put(MESSAGE, "Minimum crop limit is impacting Estimated Income.");
            } else if (min.equalsIgnoreCase ( NO )) {
                jsonObject.put ( MESSAGE, "Minimum crop limit is not impacting Estimated Income." );
            }else {
                jsonObject.put(MESSAGE, "Minimum crop limit is likely impacting Est  Income");
            }
            jsonArray.add ( jsonObject );

            JSONObject jsonObject1= new JSONObject ();
            jsonObject1.put(MIN_LIMIT, "--");
            jsonObject1.put(MAX_LIMIT, "No more than " + maxAcres + " acres");
            String max = isIncomeImpactedForCropLimit(cropTypeView, cropsGroupView, outputDetails, "max");
            if(farmInfoView.getStrategy().equals(PlanByStrategy.PLAN_BY_FIELDS) && max.equals(NO)){
                jsonObject1.put(IMPACTING_INCOME, "May be");
                jsonObject1.put(INC_DEC_INCOME, "Increase");
                jsonObject1.put(MESSAGE, "Maximum crop limit may be impacting Estimated Income.");
            } else {
                double totalLand = 0;
                List <CropResourceUsageView> resourceList = (List <CropResourceUsageView>) outputDetails.get ( "resourceList" );
                for (CropResourceUsageView cropResourceUsageView : resourceList) {
                    if(cropResourceUsageView.getCropResourceUse ().equalsIgnoreCase ( "land" )){
                        totalLand = Double.parseDouble ( AgricultureStandardUtils.removeAllCommas ( cropResourceUsageView.getCropResourceUseAmount ()));
                    }
                }

                String cropAcreage = getCropAcreage ( cropTypeView, outputDetails, cropTypeView.getFirmchecked ().equalsIgnoreCase ( "true" ) );
                if (!cropAcreage.equalsIgnoreCase("") && cropAcreage.equalsIgnoreCase("_") ) {
                    if (totalLand == Double.parseDouble(cropAcreage.replaceAll("\\,",""))) {
                        max = NO;
                    }
                }
                else
                    totalLand = 0.0;

//                String minImpactingIncome = min;
                if (cropTypeView.getMaximumAcres() != null && cropTypeView.getMinimumAcres() != null) {
                    String max1 = min.equalsIgnoreCase("NO") ? YES : NO;
                    jsonObject1.put(IMPACTING_INCOME, max1);
                    jsonObject1.put(INC_DEC_INCOME, max1.equalsIgnoreCase(YES) || max.equalsIgnoreCase ( Likely ) ? "Increase" : "--");
                    if (max1.equalsIgnoreCase(YES)) {
                        jsonObject1.put ( MESSAGE, "Maximum crop limit is impacting Estimated Income." );
                    } else if (max1.equalsIgnoreCase ( NO )) {
                        jsonObject1.put ( MESSAGE, "Maximum crop limit is not impacting Estimated Income." );
                    } else {
                        jsonObject1.put ( MESSAGE, "Maximum crop limit is likely impacting  Estimated Income" );
                    }
                }
                else if (cropTypeView.getMaximumAcres() != null || cropTypeView.getMinimumAcres() != null) {
                    jsonObject1.put(IMPACTING_INCOME, max);
                    jsonObject1.put(INC_DEC_INCOME, max.equalsIgnoreCase(YES) || max.equalsIgnoreCase ( Likely ) ? "Increase" : "--");
                    if (max.equalsIgnoreCase(YES)) {
                        jsonObject1.put ( MESSAGE, "Maximum crop limit is impacting Estimated Income." );
                    } else if (max.equalsIgnoreCase ( NO )) {
                        jsonObject1.put ( MESSAGE, "Maximum crop limit is not impacting Estimated Income." );
                    } else {
                        jsonObject1.put ( MESSAGE, "Maximum crop limit is likely impacting  Estimated Income" );
                    }
                }
            }
            jsonArray.add ( jsonObject1 );


           /* jsonObject.put(MIN_LIMIT, "At least " + minAcres + " acres");
            jsonObject.put(MAX_LIMIT, "No more than " + maxAcres + " acres");
                String both = isIncomeImpactedForCropLimit(cropTypeView, cropsGroupView, outputDetails, "both");
            /*if(599 == Double.parseDouble ( AgricultureStandardUtils.removeAllCommas ( getCropAcreage ( cropTypeView, outputDetails, cropTypeView.getFirmchecked ().equalsIgnoreCase ( "true" ) )))){
                both = NO;
            }*//*
            jsonObject.put(IMPACTING_INCOME, both);
            jsonObject.put(INC_DEC_INCOME, "--");
            if (both.equalsIgnoreCase(YES)) {
                jsonObject.put(MESSAGE, "Minimum crop limit is impacting  Estimated Income.");
            } else {
                jsonObject.put(MESSAGE, "Maximum crop limit is impacting  Estimated Income.");
            }*/

        } else {
            jsonObject.put(MIN_LIMIT, "--");
            jsonObject.put(MAX_LIMIT, "--");
            jsonObject.put(IMPACTING_INCOME, "--");
            jsonObject.put(INC_DEC_INCOME, "--");
            jsonObject.put(MESSAGE, "--");
            jsonArray.add ( jsonObject );
        }

        return jsonArray;
//        return jsonObject;
    }

    private String isIncomeImpactedForCropLimit(CropTypeView cropTypeView, CropsGroupView cropsGroupView,
                                                JSONObject outputDetails, String minOrMax) {

        FarmInfoView farmInfoView = (FarmInfoView) outputDetails.get("farmInfoView");

        PlanByStrategy strategy = farmInfoView.getStrategy();
        if (Objects.equals(strategy, PlanByStrategy.PLAN_BY_ACRES)) {
            List<FarmOutputDetailsView> farmOutputDetailsViewList = (List<FarmOutputDetailsView>) outputDetails.get("farmOutputDetails");
            for (FarmOutputDetailsView farmOutputDetailsView : farmOutputDetailsViewList) {
                if (cropTypeView != null) {
                    int usedAcres, minimumAcres, maximumAcres;
                 /*   Double profitIndex;
                        if (cropTypeView.getFirmchecked().equalsIgnoreCase("true") && farmOutputDetailsView.getCropTypeView().getId().equals(cropTypeView.getId())) {
//                            if (farmOutputDetailsView.getForFirm().equals(true)) {
//                                profitIndex = farmOutputDetailsView.getProfitIndex();
//                                return getYesNoForFirmChecked(profitIndex);
//                            } else {
                                usedAcres = farmOutputDetailsView.getUsedAcresAsInteger();
                                String maxAcresValue = cropTypeView.getMaximumAcres().equalsIgnoreCase("") ? "0" : cropTypeView.getMaximumAcres();
                                maximumAcres = Integer.parseInt(maxAcresValue.replace(",",""));
                                String minimumAcresValue = cropTypeView.getMinimumAcres().equals("") ? "0" : cropTypeView.getMinimumAcres() ;
                                minimumAcres = Integer.parseInt(minimumAcresValue.replace(",",""));

                                return getYesNo(usedAcres, minimumAcres, maximumAcres, minOrMax);
//                            }
                        }
                    else {*/
                        if (farmOutputDetailsView.getCropTypeView().getId().equals(cropTypeView.getId())){
                            usedAcres = farmOutputDetailsView.getUsedAcresAsInteger();
                            String maxAcresValue = cropTypeView.getMaximumAcres().equalsIgnoreCase("") ? "0" : cropTypeView.getMaximumAcres();
                            maximumAcres = Integer.parseInt(maxAcresValue.replace(",",""));
                            String minimumAcresValue = cropTypeView.getMinimumAcres().equals("") ? "0" : cropTypeView.getMinimumAcres() ;
                            minimumAcres = Integer.parseInt(minimumAcresValue.replace(",",""));
                            /*if(minimumAcres != 0 && maximumAcres != 0) {
                                return getYesNoForMax(usedAcres, minimumAcres, maximumAcres, minOrMax);
                            }
                            else if(minimumAcres != 0 || maximumAcres !=0 ) {
                                return getYesNo(usedAcres, minimumAcres, maximumAcres, minOrMax);
                            }*/

                            return getYesNo(usedAcres, minimumAcres, maximumAcres, minOrMax);
                        }

//                    }
                } else if (cropsGroupView != null) {
                    Set<CropType> cropSet = cropsGroupView.getCropSet();
                    int usedAcres = 0;
                    for (CropType cropType : cropSet) {
                        if (farmOutputDetailsView.getCropTypeView().getId().equals(cropType.getId())) {
                            usedAcres += farmOutputDetailsView.getUsedAcresAsInteger();
                        }
                    }
                    int minimumAcres = Integer.parseInt(cropsGroupView.getMinimumAcresWithoutComma().equalsIgnoreCase("") ? "0" : cropsGroupView.getMinimumAcresWithoutComma());
                    int maximumAcres = Integer.parseInt(cropsGroupView.getMaximumAcresWithoutComma().equalsIgnoreCase("") ? "0" : cropsGroupView.getMaximumAcresWithoutComma());

                    return getYesNo(usedAcres, minimumAcres, maximumAcres, minOrMax);
                }

            }

        } else if (Objects.equals(strategy, PlanByStrategy.PLAN_BY_FIELDS)) {
            Map<String, String> hashMapForAcre = (Map<String, String>) outputDetails.get("hashMapForAcre");
            Map<String, String> hashMapForProfit = (Map<String, String>) outputDetails.get("hashMapForProfit");
            if (cropTypeView != null) {

                int usedAcres, minimumAcres, maximumAcres;
                Double profitIndex;
                if(cropTypeView.getFirmchecked().equalsIgnoreCase("true") && hashMapForAcre.containsKey(cropTypeView.getCropName() + " (Firm)")){
                    usedAcres = Integer.parseInt(AgricultureStandardUtils.removeAllCommas(hashMapForAcre.get(cropTypeView.getCropName() + " (Firm)").split(" ")[0]));
                    minimumAcres = cropTypeView.getForwardAcres().intValue();
                    maximumAcres = 0;
//                    profitIndex = hashMapForProfit.get("");
                    profitIndex = 0.0;
                    return getYesNoForFirmChecked(profitIndex);
                } else {
                    usedAcres = Integer.parseInt(AgricultureStandardUtils.removeAllCommas(hashMapForAcre.get(cropTypeView.getCropName()).split(" ")[0]));
                    minimumAcres = Integer.parseInt(cropTypeView.getMinimumAcresWithoutComma().equalsIgnoreCase("") ? "0" : cropTypeView.getMinimumAcresWithoutComma());
                    maximumAcres = Integer.parseInt(cropTypeView.getMaximumAcresWithoutComma().equalsIgnoreCase("") ? "0" : cropTypeView.getMaximumAcresWithoutComma());
//                    return getYesNoForField(usedAcres, minimumAcres, maximumAcres, minOrMax);
                }
            } else if (cropsGroupView != null) {
                Set<CropType> cropSet = cropsGroupView.getCropSet();
                int usedAcres = 0;
                for (CropType cropType : cropSet) {
                    usedAcres += Integer.parseInt(AgricultureStandardUtils.removeAllCommas(hashMapForAcre.get(cropType.getCropName()).split(" ")[0]));
                }
                int minimumAcres = Integer.parseInt(cropsGroupView.getMinimumAcresWithoutComma().equalsIgnoreCase("") ? "0" : cropsGroupView.getMinimumAcresWithoutComma());
                int maximumAcres = Integer.parseInt(cropsGroupView.getMaximumAcresWithoutComma().equalsIgnoreCase("") ? "0" : cropsGroupView.getMaximumAcresWithoutComma());

//                return getYesNoForField(usedAcres, minimumAcres, maximumAcres, minOrMax);
            }

        }

        return "";
    }
    @Override
    public String getYesNoForField(int usedAcres, int minimumAcres, int maximumAcres, String minOrMax) {
        if (minOrMax.equalsIgnoreCase("min")) {
            int value = usedAcres - minimumAcres;
            double values=  value/minimumAcres;
            if(minimumAcres <= 0){
                return NO;
            } else if (value == 0) {
                return YES;
            } else if (values <= 0.25) {
                return Likely;
            } else if (values > 0.25) {
                return NO;
            }
            /*if (usedAcres == minimumAcres)
                    return YES;
            else if (usedAcres > minimumAcres)
                return NO;
            else
                return YES;*/
        } else if (minOrMax.equalsIgnoreCase("max")) {
            int value = maximumAcres - usedAcres;
            double values=  value/minimumAcres;
            if(maximumAcres <= 0){
                return NO;
            } else if (value == 0) {
                return YES;
            } else if (values <= 0.25) {
                return Likely;
            } else if (values > 0.25) {
                return NO;
            }
//            return maximumAcres > usedAcres ? NO : YES;
        }/* else if (minOrMax.equalsIgnoreCase("both")) {
            if(usedAcres == minimumAcres || usedAcres>minimumAcres) {
                 if(usedAcres>=(.85*maximumAcres)){return Likely;}
                else{     return NO; }}
                else {return NO;}

        } */else {
            return "";
        }
        return "";    }

    public String getYesNoForFirmChecked(Double profitIndex){
//        double value = usedAcres - minimumAcres;
//        double values=  value/minimumAcres;
        if (profitIndex >= 0.8) {
            return NO;
        } else if (profitIndex < 0.8) {
            return YES;
        }
        return "";
    }

    @Override
    public String getYesNoForMax(int usedAcres, int minimumAcres, int maximumAcres, String minOrMax){
        if (minOrMax.equalsIgnoreCase("max")){
            return "No";
        }else if (minOrMax.equalsIgnoreCase("min")){
            return getYesNo(usedAcres, minimumAcres, maximumAcres, minOrMax);
        }

        return "";
    }
    @Override
    public String getYesNo(int usedAcres, int minimumAcres, int maximumAcres, String minOrMax) {
        if (minOrMax.equalsIgnoreCase("min")) {

            double value = usedAcres - minimumAcres;
            double values=  value / minimumAcres;
            if(minimumAcres <= 0){
                return NO;
            } else if (value == 0) {
                return YES;
            } else if (values <= 0.15) {
                return Likely;
            } else if (values > 0.15) {
                return NO;
            }
            /*if (usedAcres == minimumAcres)
                    return YES;
            else if (usedAcres > minimumAcres)
                return NO;
            else
                return YES;*/
        } else if (minOrMax.equalsIgnoreCase("max")) {
            double value = maximumAcres - usedAcres;
            double values = value / maximumAcres;

            if(maximumAcres <= 0){
                return NO;
            } else if (value == 0) {
                return YES;
            } else if ( values<= 0.20) {
                return Likely;
            } else if (values > 0.20) {
                return NO;
            }
//            return maximumAcres > usedAcres ? NO : YES;
        }/* else if (minOrMax.equalsIgnoreCase("both")) {
            if(usedAcres == minimumAcres || usedAcres>minimumAcres) {
                 if(usedAcres>=(.85*maximumAcres)){return Likely;}
                else{     return NO; }}
                else {return NO;}

        } */else {
            return "";
        }
        return "";}

    @Override

    public JSONArray buildCropAcreageContent(JSONObject outputDetails) {
        JSONArray jsonArray = new JSONArray();

        FarmInfoView farmInfoView = (FarmInfoView) outputDetails.get("farmInfoView");
        List<CropTypeView> cropTypeViewList = (List<CropTypeView>)outputDetails.get("cropTypeView");

        double workReturn=0.0;

        if (PlanByStrategy.PLAN_BY_ACRES.equals(farmInfoView.getStrategy())) {
            List<FarmOutputDetailsView> farmOutputDetailsViewList = (List<FarmOutputDetailsView>) outputDetails.get("farmOutputDetails");
            for (FarmOutputDetailsView farmOutputDetailsView : farmOutputDetailsViewList) {
                Map<String,String> hashMapForWorkReturn=new HashMap <> (  );
                Map<String,String> hashMapForWorkReturnRating=new HashMap <> (  );
                JSONObject jsonObject = new JSONObject();

                String cropName = farmOutputDetailsView.getCropTypeView().getCropName();
                if (farmOutputDetailsView.getForFirm()) {
                    cropName += " (Firm)";
                } else if (farmOutputDetailsView.getForProposed()) {
                    cropName += " (Proposed)";
                }

                jsonObject.put(CROP_NAME, cropName);


                jsonObject.put(ACREAGE, farmOutputDetailsView.getUsedAcres() + "<br>" + farmOutputDetailsView.getUsedAcresPercentage() + "%");

                if (!farmOutputDetailsView.getProfit().equalsIgnoreCase("0")) {
                    jsonObject.put(PROFIT, farmOutputDetailsView.getProfit() + "<br>" + farmOutputDetailsView.getUsedCapitalPercentage() + "%");
                } else {
                    jsonObject.put(PROFIT, "NA");
                }

                if (farmOutputDetailsView.getRatio() == 0.0) {
                        Double ratio = 0.0;
                            if(farmOutputDetailsView.getCropTypeView ().getCropName ().equals ( cropName )) {
//                                ratio= (parseDouble (AgricultureStandardUtils.removeAllCommas(farmOutputDetailsView.getCropTypeView ().getIntExpCropYield ())) * farmOutputDetailsView.getCropTypeView ().getIntExpCropPrice ().doubleValue ()) -( farmOutputDetailsView.getCropTypeView ().getCalculatedVariableProductionCost ().doubleValue () );
                            }
                        jsonObject.put(RATIO, String.valueOf ( AgricultureStandardUtils.withoutDecimalAndComma ( ratio )).split ( "//." )[0]);
                } else {
                    String ratioInString = String.valueOf(farmOutputDetailsView.getRatio());
                    jsonObject.put(RATIO, AgricultureStandardUtils.commaSeparaterForPriceWithOneDecimal(ratioInString ).split("\\.")[0] );
                }

                if (farmOutputDetailsView.getProfitIndex() == 0.0) {
                    jsonObject.put(INDEX, "NA");
                } else {
                    jsonObject.put(INDEX, farmOutputDetailsView.getProfitIndex());
                }

                if (farmOutputDetailsView.getProfit().equalsIgnoreCase("0")) {
                    jsonObject.put(RATING, "Grey");
                } else {
                    jsonObject.put(RATING, farmOutputDetailsView.getRating());
                }
                if(farmOutputDetailsView.getRatio () == 0){
                    Double ratio = 0.0;
                    if(farmOutputDetailsView.getCropTypeView ().getCropName ().equals ( cropName )) {
                        ratio= (parseDouble (AgricultureStandardUtils.removeAllCommas(farmOutputDetailsView.getCropTypeView ().getIntExpCropYield ())) * farmOutputDetailsView.getCropTypeView ().getIntExpCropPrice ().doubleValue ()) -( farmOutputDetailsView.getCropTypeView ().getCalculatedVariableProductionCost ().doubleValue () );

                        workReturn = ratio / farmOutputDetailsView.getCropTypeView().getCalculatedVariableProductionCost ().doubleValue ();
                    }
//                    jsonObject.put(WORKRETURN, AgricultureStandardUtils.doubleWithOneDecimal(workReturn) );
                    jsonObject.put ( WORKRETURN,"0.0" );
                } else {
                    CropTypeView cropTypeView = farmOutputDetailsView.getCropTypeView ();

                    workReturn=  farmOutputDetailsView.getRatio () / cropTypeView.getCalculatedVariableProductionCost ().doubleValue ();

                    jsonObject.put ( WORKRETURN, AgricultureStandardUtils.doubleWithOneDecimal (farmOutputDetailsView.getRatio () / cropTypeView.getCalculatedVariableProductionCost ().doubleValue ()) );
                }
                if (farmOutputDetailsView.getProfit ().equalsIgnoreCase ( "0" )) {
                    jsonObject.put ( RATINGFORWORKRETURN, "Grey" );
                } else {
                    if (workReturn < 0.5) {
                        jsonObject.put ( RATINGFORWORKRETURN,"Red");

                    } else if (0.50 <= workReturn && workReturn < 0.9) {
                        jsonObject.put ( RATINGFORWORKRETURN,"Yellow" );

                    } else if (workReturn >= 0.9) {
                        jsonObject.put ( RATINGFORWORKRETURN,"Green" );
                    }
                }
                hashMapForWorkReturn.put ( "count","1" );
                hashMapForWorkReturnRating.put ("con","1");
                jsonArray.add(jsonObject);

            }


        } else if (PlanByStrategy.PLAN_BY_FIELDS.equals(farmInfoView.getStrategy())) {
            List<FarmOutputDetailsForFieldView> farmOutputDetailsViewList = (List<FarmOutputDetailsForFieldView>) outputDetails.get("farmOutputDetails");
            Map<String, String> hashMapForAcre = (Map<String, String>) outputDetails.get("hashMapForAcre");
            Map<String, String> hashMapForProfit = (Map<String, String>) outputDetails.get("hashMapForProfit");
            Map<String, String> hashMapForRatio = (Map<String, String>) outputDetails.get("hashMapForRatio");
            Map<String, String> hashMapForProfitIndex = (Map<String, String>) outputDetails.get("hashMapForProfitIndex");
            Map<String, String> hashMapForRating = (Map<String, String>) outputDetails.get("hashMapForRating");
            Map<String,String> hashMapForWorkReturn=(Map<String,String>) outputDetails.get ( "hashMapForWorkReturn" );
            Map<String,String> hashMapForWorkReturnRating= (Map<String, String>) outputDetails.get ( "hashMapForWorkReturnForRating" );

            Set<String> cropTypeKeySet = hashMapForAcre.keySet();
            for (String cropTypeKey : cropTypeKeySet) {
                Double workreturn=0.0;

                JSONObject jsonObject = new JSONObject();


                jsonObject.put(CROP_NAME, cropTypeKey);

                String acreage = hashMapForAcre.get(cropTypeKey);
                jsonObject.put(ACREAGE, acreage.split(" ")[0] + " <br> " + acreage.split(" ")[1].replaceAll("[\\(\\)]", ""));

                String profit = hashMapForProfit.get(cropTypeKey);
                if (profit.equalsIgnoreCase("0 (0.0%)")
                        || profit.equalsIgnoreCase("0 (-0.0%)")) {
                    jsonObject.put(PROFIT, "NA");
                } else {

                    jsonObject.put(PROFIT, profit.split(" ")[0] + " <br> " + profit.split(" ")[1].replaceAll("[\\(\\)]", ""));
                }

                if (hashMapForRatio.get(cropTypeKey).equalsIgnoreCase("0")) {
                    Double ratio = 0.0;
                    for (FarmOutputDetailsForFieldView farmOutputDetailsView : farmOutputDetailsViewList) {
                        Double expCropYield = parseDouble (AgricultureStandardUtils.removeAllCommas ( farmOutputDetailsView.getCropTypeView().getIntExpCropYield()));
                        Double expCropPrice = parseDouble(AgricultureStandardUtils.removeAllCommas ( String.valueOf(farmOutputDetailsView.getCropTypeView().getIntExpCropPrice())));
                        Double calculatedVariableProductionCost = parseDouble(AgricultureStandardUtils.removeAllCommas ( String.valueOf(farmOutputDetailsView.getCropTypeView().getCalculatedVariableProductionCost ())));

//                        ratio = (expCropYield * expCropPrice) - (calculatedVariableProductionCost);


                    }
                    /*for (CropTypeView cropTypeView : cropTypeViewList) {
                        if(cropTypeView.getCropName ().equals ( cropTypeKey )) {
                            Double expCropYield = parseDouble (AgricultureStandardUtils.removeAllCommas ( cropTypeView.getIntExpCropYield()));
                            Double expCropPrice = parseDouble(AgricultureStandardUtils.removeAllCommas ( String.valueOf(cropTypeView.getIntExpCropPrice())));
                            Double calculatedVariableProductionCost = parseDouble(AgricultureStandardUtils.removeAllCommas ( String.valueOf(cropTypeView.getCalculatedVariableProductionCost ())));

                            ratio = (expCropYield * expCropPrice) - (calculatedVariableProductionCost);

//                             ratio= (Double.parseDouble (cropTypeView.getIntExpCropYield ()) * cropTypeView.getIntExpCropPrice ().doubleValue ()) -( cropTypeView.getCalculatedVariableProductionCost ().doubleValue () );
                         }
                    }*/
                    jsonObject.put(RATIO, ( AgricultureStandardUtils.withoutDecimalAndComma ( ratio ) ) );
                } else {
                    jsonObject.put(RATIO, (hashMapForRatio.get(cropTypeKey) ).split("\\.")[0] );
                }

                if (hashMapForProfitIndex.get(cropTypeKey).equalsIgnoreCase("0.0%")
                         ) {
                    jsonObject.put(INDEX, "NA");
                } else {
                    jsonObject.put(INDEX, hashMapForProfitIndex.get(cropTypeKey).replace("%", ""));
                }

                jsonObject.put(RATING, hashMapForRating.get(cropTypeKey));

                if (hashMapForWorkReturn.get ( cropTypeKey ).equalsIgnoreCase ( "0.0" ) ){
                    Double ratio = null;
                    for (FarmOutputDetailsForFieldView farmOutputDetailsView : farmOutputDetailsViewList) {
                        Double expCropYield = parseDouble (AgricultureStandardUtils.removeAllCommas ( farmOutputDetailsView.getCropTypeView().getIntExpCropYield()));
                        Double expCropPrice = parseDouble(AgricultureStandardUtils.removeAllCommas ( String.valueOf(farmOutputDetailsView.getCropTypeView().getIntExpCropPrice())));
                        Double calculatedVariableProductionCost = parseDouble(AgricultureStandardUtils.removeAllCommas ( String.valueOf(farmOutputDetailsView.getCropTypeView().getCalculatedVariableProductionCost ())));

                        ratio = (expCropYield * expCropPrice) - (calculatedVariableProductionCost);

//                        workReturn = ratio / calculatedVariableProductionCost;
                    }
                    /*for (CropTypeView cropTypeView : cropTypeViewList) {
                        if(cropTypeView.getCropName ().equals ( cropTypeKey )) {
                            Double expCropYield = parseDouble (AgricultureStandardUtils.removeAllCommas ( cropTypeView.getIntExpCropYield()));
                            Double expCropPrice = parseDouble(AgricultureStandardUtils.removeAllCommas ( String.valueOf(cropTypeView.getIntExpCropPrice())));
                            Double calculatedVariableProductionCost = parseDouble(AgricultureStandardUtils.removeAllCommas ( String.valueOf(cropTypeView.getCalculatedVariableProductionCost ())));

                            ratio = (expCropYield * expCropPrice) - (calculatedVariableProductionCost);

                            workReturn = ratio / calculatedVariableProductionCost;
                        }
                    }*/
                    jsonObject.put(WORKRETURN, AgricultureStandardUtils.doubleWithOneDecimal(workReturn) );

//                  jsonObject.put ( WORKRETURN,"NA" );
                } else {
                    jsonObject.put ( WORKRETURN, hashMapForWorkReturn.get ( cropTypeKey ));}
                 /*   Double ratio= new Double ( AgricultureStandardUtils.removeAllCommas ( hashMapForRatio.get(cropTypeKey)));
                    String cropName = cropTypeKey;
                    if (cropTypeKey.contains (" (Firm)")) {
                        cropName = cropTypeKey.split ( " \\(Firm\\)" )[0];
                    } else if (cropTypeKey.contains (" (Proposed)")) {
                        cropName = cropTypeKey.split ( " \\(Proposed\\)" )[0];
                    }

                    List <CropTypeView> cropTypeViews = (List <CropTypeView>) outputDetails.get ( "cropTypeView" );
                    for (CropTypeView cropTypeView : cropTypeViews) {
                        if (cropTypeView.getSelected () && cropTypeView.getCropName ().equalsIgnoreCase ( cropName )){
                            if(ratio!=0)
                            jsonObject.put ( WORKRETURN, AgricultureStandardUtils.doubleWithOneDecimal (  ratio / cropTypeView.getCalculatedVariableProductionCost ().doubleValue ()));

                            workreturn=   ratio / cropTypeView.getCalculatedVariableProductionCost ().doubleValue ();
                            break;
                        }
                    }
                }*/
                /*if (profit.equalsIgnoreCase("0 (0.0%)")
                        || profit.equalsIgnoreCase("0 (-0.0%)")){
                    jsonObject.put ( RATINGFORWORKRETURN,"Grey" );}
                else {
                    if (workreturn < 0.5) {
                        jsonObject.put ( RATINGFORWORKRETURN,"Red");

                    } else if (0.50 <= workreturn && workreturn < 0.9) {
                        jsonObject.put ( RATINGFORWORKRETURN,"Yellow" );
                    } else if (workreturn >= 0.9) {
                        jsonObject.put ( RATINGFORWORKRETURN,"Green" );
                    }
                }*/
                    jsonObject.put ( RATINGFORWORKRETURN,hashMapForWorkReturnRating.get ( cropTypeKey ));
                    jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }



    @Override
    public JSONObject buildResourcesContent(JSONObject outputDetails) {
        List<CropResourceUsageView> resourceList = (List<CropResourceUsageView>) outputDetails.get("resourceList");
        Map<String, String> cropResourceUsed = (Map<String, String>) outputDetails.get("cropResourceUsed");
        Map<String, String> cropResourceUnused = (Map<String, String>) outputDetails.get("cropResourceUnused");

        JSONObject flagStatusObj = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (CropResourceUsageView cropResourceUsageView : resourceList) {
            JSONObject jsonObject = null;
            if (cropResourceUsageView.isActive()) {
                jsonObject = new JSONObject();
                if(cropResourceUsageView.getCropResourceUse().equalsIgnoreCase("capital")){
                    jsonObject.put(RESOURCE_NAME, AppConstants.WORKING_CAPITAL);
                } else if(cropResourceUsageView.getCropResourceUse().equalsIgnoreCase("land")){
                    String used = cropResourceUsed.get(cropResourceUsageView.getCropResourceUse());
                    String unused = cropResourceUnused.get(cropResourceUsageView.getCropResourceUse());
                    String totalAvailable = cropResourceUsageView.getCropResourceUseAmount();
                    long total = null == totalAvailable ? 0L : AgricultureStandardUtils.stringToLong(totalAvailable);
                    long usedLong = null == used ? 0L : AgricultureStandardUtils.stringToLong(used);
                    long unusedLong = null == unused ? 0L : AgricultureStandardUtils.stringToLong(unused);

                    jsonObject.put(RESOURCE_NAME, cropResourceUsageView.getCropResourceUse());
                    flagStatusObj.put(cropResourceUsageView.getCropResourceUse(), (usedLong != total && usedLong < total));
                } else {
                    jsonObject.put(RESOURCE_NAME, cropResourceUsageView.getCropResourceUse());
                    flagStatusObj.put(cropResourceUsageView.getCropResourceUse(), false);
                }
                jsonObject.put(UOM_RESOURCE, cropResourceUsageView.getUoMResource());
                jsonObject.put(RESOURCE_TOTAL_AVAILABLE, cropResourceUsageView.getCropResourceUseAmount());
                jsonObject.put(RESOURCE_USED, cropResourceUsed.get(cropResourceUsageView.getCropResourceUse()));
                jsonObject.put(RESOURCE_UNUSED, cropResourceUnused.get(cropResourceUsageView.getCropResourceUse()));

                Double usedCrop= Double.valueOf ( AgricultureStandardUtils.removeAllCommas ( cropResourceUnused.get ( cropResourceUsageView.getCropResourceUse() )) );
                Double totalAvalable= Double.valueOf ( AgricultureStandardUtils.removeAllCommas ( cropResourceUsageView.getCropResourceUseAmount ()) );
               Double impactingEstIncome=0.0;
                if(usedCrop!=0 || totalAvalable!=0){
                    impactingEstIncome=   usedCrop/totalAvalable;
               }
                if(impactingEstIncome<=0.05){
                    jsonObject.put(RESOURCE_IMPACTING_PROFIT, "Yes");
                } else if(impactingEstIncome>0.05 && impactingEstIncome<=0.15){
                    jsonObject.put(RESOURCE_IMPACTING_PROFIT, "Likely");
                }else if(impactingEstIncome>0.15){
                    jsonObject.put(RESOURCE_IMPACTING_PROFIT, "No ");
                }else{
                    jsonObject.put(RESOURCE_IMPACTING_PROFIT, "NA");

                }
                jsonArray.add(jsonObject);
            }
        }

        JSONObject data = new JSONObject();
        data.put("resourceDetails", jsonArray);
        data.put("resourceFlags", flagStatusObj);

        return data;
    }

}

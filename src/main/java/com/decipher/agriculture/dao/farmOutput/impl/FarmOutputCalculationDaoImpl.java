package com.decipher.agriculture.dao.farmOutput.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import com.decipher.agriculture.bean.OutputBeanForStrategy;
import com.decipher.agriculture.dao.farm.CropResourceUsageFieldVariancesDao;
import com.decipher.agriculture.dao.farmOutput.FarmOutputCalculationDao;
import com.decipher.agriculture.dao.farmOutput.LinearProgramingSolveDao;
import com.decipher.agriculture.service.farm.CropResourceUsageService;
import com.decipher.agriculture.service.farm.CropTypeService;
import com.decipher.agriculture.service.farm.FarmInfoService;
import com.decipher.agriculture.service.farm.FieldInfoService;
import com.decipher.agriculture.service.strategy.FarmCustomStrategyService;
import com.decipher.view.form.farmDetails.CropResourceUsageFieldVariancesView;
import com.decipher.view.form.farmDetails.CropResourceUsageView;
import com.decipher.view.form.farmDetails.CropTypeView;
import com.decipher.view.form.farmDetails.FarmOutputDetailsForFieldView;
import com.decipher.view.form.farmDetails.FarmOutputDetailsView;
import com.decipher.view.form.farmDetails.FieldInfoView;
import com.decipher.view.form.strategy.FarmCustomStrategyForGroupView;
import com.decipher.view.form.strategy.FarmCustomStrategyView;
import net.sf.javailp.Result;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.bean.CropBeanForOutput;
import com.decipher.agriculture.data.farm.CropFieldChocies;
import com.decipher.agriculture.data.farmOutput.CropLimitDualValue;
import com.decipher.agriculture.data.farm.CropResourceUsage;
import com.decipher.agriculture.data.farm.CropResourceUsageFieldVariances;
import com.decipher.agriculture.data.farm.CropType;
import com.decipher.agriculture.data.farm.CropsGroup;
import com.decipher.agriculture.data.farm.FarmInfo;
import com.decipher.agriculture.data.farmOutput.FarmOutputDetails;
import com.decipher.agriculture.data.farmOutput.FarmOutputDetailsForField;
import com.decipher.agriculture.data.farmOutput.GroupLimitDualValue;
import com.decipher.agriculture.data.farm.PlanByStrategy;
import com.decipher.agriculture.data.farmOutput.ResourceDualValue;
import com.decipher.util.AgricultureStandardUtils;
import com.decipher.util.PlantingProfitLogger;

@Repository
@Transactional
public class FarmOutputCalculationDaoImpl implements FarmOutputCalculationDao {

    @Autowired
    private CropTypeService cropTypeService;
    @Autowired
    private FieldInfoService fieldInfoService;
    @Autowired
    private CropResourceUsageService cropResourceUsageService;
    @Autowired
    private CropResourceUsageFieldVariancesDao cropResourceUsageFieldVariancesDao;
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private LinearProgramingSolveDao linearProgramingSolveDao;
    @Autowired
    private FarmCustomStrategyService farmCustomStrategyService;
    @Autowired
    private FarmInfoService farmInfoService;

    private static final Double zeroDouble = 0.0;

    @Override
    public void calculateFarmOutputStatistics(FarmInfo farmInfo) {
        /**
         * @changed - Abhishek
         * @date - 21-12-2015
         */

        OutputBeanForStrategy outputBeanForStrategy = new OutputBeanForStrategy();
        outputBeanForStrategy.setFarmInfo(farmInfo);
        outputBeanForStrategy.setBaselineFlag(true);
        outputBeanForStrategy.setSaveFlag(true);

        calculateFarmOutputStatistics(outputBeanForStrategy);
    }

    @Override
    public void calculateFarmOutputStatisticsForField(FarmInfo farmInfo) {
        /**
         * @changed - Abhishek
         * @date - 21-12-2015
         */
        OutputBeanForStrategy outputBeanForStrategy = new OutputBeanForStrategy();
        outputBeanForStrategy.setFarmInfo(farmInfo);
        outputBeanForStrategy.setBaselineFlag(true);
        outputBeanForStrategy.setSaveFlag(true);

        calculateFarmOutputStatisticsForField(outputBeanForStrategy);
    }

    @Override
    public void calculateFarmOutputStatisticsForAcres(FarmInfo farmInfo) {
        /**
         * @changed - Abhishek
         * @date - 21-12-2015
         */
        OutputBeanForStrategy outputBeanForStrategy = new OutputBeanForStrategy();
        outputBeanForStrategy.setFarmInfo(farmInfo);
        outputBeanForStrategy.setBaselineFlag(true);
        outputBeanForStrategy.setSaveFlag(true);

        calculateFarmOutputStatisticsForAcres(outputBeanForStrategy);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<FarmOutputDetails> getAllFarmOutputDetailsByFarm(int farmId) {
        /**
         * @changed - Abhishek
         * @date - 21-12-2015
         */
        /*PlantingProfitLogger.debug("get All FarmOutputDetails By farm Id .. " + farmId);
        List<FarmOutputDetails> farmOutputDetailsList = new ArrayList<FarmOutputDetails>();

		Session session = sessionFactory.openSession();
		try {
			Query query = session.createQuery("FROM FarmOutputDetails where farmInfo.id=:farmId ORDER BY cropType.cropName ASC");
			query.setParameter("farmId", farmId);
			farmOutputDetailsList = query.list();
			if (farmOutputDetailsList != null) {
				PlantingProfitLogger.info("size -->>"+ farmOutputDetailsList.size());
			}

		} catch (Exception e) {
			PlantingProfitLogger.error(e);
			farmOutputDetailsList = null;
		} finally {
			session.close();
		}*/

        OutputBeanForStrategy outputBeanForStrategy = new OutputBeanForStrategy();
        outputBeanForStrategy.setFarmID(farmId);
        outputBeanForStrategy.setBaselineFlag(true);

        return getAllFarmOutputDetailsByFarm(outputBeanForStrategy);
    }

    @Override
    public List<FarmOutputDetailsForField> getAllFarmOutputDetailsForFieldByFarm(int farmId) {
        /**
         * @changed - Abhishek
         * @date - 21-12-2015
         */
		/*PlantingProfitLogger.debug("get All FarmOutputDetailsForField By farm Id .. " + farmId);
		List<FarmOutputDetailsForField> farmOutputDetailsForField = new ArrayList<FarmOutputDetailsForField>();

		Session session = sessionFactory.openSession();
		try {
			Query query = session.createQuery("FROM FarmOutputDetailsForField where farmInfo.id=:farmId ORDER BY cropType.cropName ASC");
			query.setParameter("farmId", farmId);
			farmOutputDetailsForField = query.list();
			if (farmOutputDetailsForField != null) {
				PlantingProfitLogger.info("size -->>"+ farmOutputDetailsForField.size());
			}
		} catch (Exception e) {
			PlantingProfitLogger.error(e);
			farmOutputDetailsForField = null;
		} finally {
			session.close();
		}*/
        OutputBeanForStrategy outputBeanForStrategy = new OutputBeanForStrategy();
        outputBeanForStrategy.setFarmID(farmId);
        outputBeanForStrategy.setBaselineFlag(true);

        return getAllFarmOutputDetailsForFieldByFarm(outputBeanForStrategy);
    }

    /**
     * @author - Abhishek
     * @date - 21-12-2015
     * @description - Overloaded Functions for getting output data
     */

	/* ********************** Start ********************** */

    /**
     * @author - Abhishek
     * @#date - 21-12-2015
     */
    @Override
    public List<Object> calculateFarmOutputStatistics(OutputBeanForStrategy outputBeanForStrategy) {
//		PlantingProfitLogger.info("" + PlanByStrategy.PLAN_BY_ACRES + "------------" + outputBeanForStrategy.getFarmInfo().getStrategy() + "---------");
//		PlantingProfitLogger.info(PlanByStrategy.PLAN_BY_ACRES == outputBeanForStrategy.getFarmInfo().getStrategy());
        if (PlanByStrategy.PLAN_BY_ACRES == outputBeanForStrategy.getFarmInfo().getStrategy()) {
            return calculateFarmOutputStatisticsForAcres(outputBeanForStrategy);
        } else if (PlanByStrategy.PLAN_BY_FIELDS == outputBeanForStrategy.getFarmInfo().getStrategy()) {
            return calculateFarmOutputStatisticsForField(outputBeanForStrategy);
        }
        return null;
    }

    /**
     * @author - Abhishek
     * @#date - 21-12-2015
     */
    @Override
    public List<Object> calculateFarmOutputStatisticsForField(OutputBeanForStrategy outputBeanForStrategy) {
        /**
         * @changed - abhishek
         * @date - 21-12-2015
         */
		/*List<CropType> cropTypeList = cropTypeService.getAllCropByFarmId(outputBeanForStrategy.getFarmInfo().getId());
		List<CropResourceUsageView> resourceUsageViews = cropResourceUsageService.getAllCropResourceUsageByFarmId(outputBeanForStrategy.getFarmInfo().getId());
		List<CropBeanForOutput> cropBeanForOutput = getCropBeanForCalculation(cropTypeList, resourceUsageViews);
		List<FieldInfoView> fieldInfoViews = fieldInfoService.getAllFieldsByFarmId(outputBeanForStrategy.getFarmInfo().getId());
		Set<CropsGroup> cropsGroups = outputBeanForStrategy.getFarmInfo().getCropsGroup();*/

        Double potentialProfit = 0.0, totalAcreage = 0.0;

        List<FarmOutputDetailsForField> farmOutputDetails = null;
        List<Object> farmOutputDetailsList = new ArrayList<Object>();
        try {
            /**
             * @changed - abhishek
             * @date - 21-12-2015
             */
			/*farmOutputDetails = calculateAcresForEachCropForField(cropBeanForOutput, resourceUsageViews, fieldInfoViews, cropsGroups);*/
            farmOutputDetails = getOutputDetailsForFarmByField(outputBeanForStrategy);
            for (FarmOutputDetailsForField outputDetails : farmOutputDetails) {
                outputDetails.setFarmInfo(outputBeanForStrategy.getFarmInfo());
                CropTypeView cropTypeView = new CropTypeView(outputDetails.getCropType());
                double expectedYield = Double.parseDouble(AgricultureStandardUtils.removeAllCommas(cropTypeView.getIntExpCropYield()));
                double expectedPrice = cropTypeView.getIntExpCropPrice().doubleValue();
                double productionCost = Double.parseDouble("" + cropTypeView.getCalculatedVariableProductionCost());

                double minYield = Double.parseDouble(AgricultureStandardUtils.removeAllCommas(cropTypeView.getIntMinCropYield()));
                double minPrice = cropTypeView.getIntMinCropPrice().doubleValue();

                double maxYield = Double.parseDouble(AgricultureStandardUtils.removeAllCommas(cropTypeView.getIntMaxCropYield()));
                double maxPrice = cropTypeView.getIntMaxCropPrice().doubleValue();

                if (outputDetails.getCropType().getCropYieldFieldVariances() != null && outputDetails.getCropType().getCropYieldFieldVariances().getFieldInfo().getId().equals(outputDetails.getFieldInfo().getId())) {

                    expectedYield = cropTypeView.getExpCropYieldFieldStr().equals("0") || cropTypeView.getExpCropYieldFieldStr().equals("") ? expectedYield : Double.parseDouble(cropTypeView.getExpCropYieldFieldStrWithOneDecimal());
                    productionCost = cropTypeView.getVarProductionCost().equals(zeroDouble) ? productionCost : cropTypeView.getVarProductionCost();
                }
                outputDetails.setUsedCapital(outputDetails.getUsedAcres() * productionCost);
                if (outputDetails.getForFirm() || outputDetails.getForProposed()) {
                    double forwardAcres = cropTypeView.getForwardAcres();
                    /**
                     * @changed - abhishek
                     * @date - 03-12-2015
                     */
                    double forwardQuantity = Double.parseDouble(AgricultureStandardUtils.removeAllCommas(cropTypeView.getForwardQuantity().replaceAll("\\$", "")));
//					double forwardQuantity = Double.parseDouble(AgricultureStandardUtils.removeAllCommas(cropTypeView.getQuantity()));
                    double forwardPrice = Double.parseDouble(cropTypeView.getPriceStr());
                    PlantingProfitLogger.info(cropTypeView.getCropName() + "------------------" + forwardPrice + "----------------" + forwardAcres + "----------------" + expectedYield + "----------------" + expectedPrice + "----------------" + outputDetails.getUsedAcres() + "----------------" + productionCost);
                    outputDetails.setProfit(calculatePofitWithFirmSelected(forwardPrice, forwardAcres, expectedYield, expectedPrice, outputDetails.getUsedAcres(), productionCost, forwardQuantity));
                    PlantingProfitLogger.info("Profit------------>>>>>>>>>>>>" + outputDetails.getProfit());
                } else {
                    outputDetails.setProfit(calculateProfit(expectedYield, expectedPrice, outputDetails.getUsedAcres(), productionCost));
//					outputDetails.setMinimumProfit(calculateProfit(minYield,minPrice,outputDetails.getUsedAcres(), productionCost));
//					outputDetails.setMaximumProfit(calculateProfit(maxYield,maxPrice,outputDetails.getUsedAcres(), productionCost));
                    if ((maxPrice != 0 && maxYield != 0) && (minPrice != 0 && minYield != 0)) {

                        double minProfit = calculateProfit(minYield, minPrice, outputDetails.getUsedAcres(), productionCost);
                        minProfit += cropTypeView.getAdditionalIncome();

                        outputDetails.setMinimumProfit(minProfit);

                        double maxProfit = calculateProfit(maxYield, maxPrice, outputDetails.getUsedAcres(), productionCost);
                        maxProfit += cropTypeView.getAdditionalIncome();

                        outputDetails.setMaximumProfit(maxProfit);
                    } else {
                        double profit = calculateProfit(expectedYield, expectedPrice, outputDetails.getUsedAcres(), productionCost);
                        profit += cropTypeView.getAdditionalIncome();
                        outputDetails.setMinimumProfit(profit);
                        outputDetails.setMaximumProfit(profit);
                    }
                }

                if (outputDetails.getForProposed()) {
//					outputDetails.setMinimumProfit(calculateProfit(minYield,minPrice,outputDetails.getUsedAcres(), productionCost));
//					outputDetails.setMaximumProfit(calculateProfit(maxYield,maxPrice,outputDetails.getUsedAcres(), productionCost));
                    if ((maxPrice != 0 && maxYield != 0) && (minPrice != 0 && minYield != 0)) {
//						outputDetails.setMinimumProfit(calculateProfit(minYield,minPrice,outputDetails.getUsedAcres(), productionCost));
//						outputDetails.setMaximumProfit(calculateProfit(maxYield,maxPrice,outputDetails.getUsedAcres(), productionCost));
                        double minProfit = calculateProfit(minYield, minPrice, outputDetails.getUsedAcres(), productionCost);
                        minProfit += cropTypeView.getAdditionalIncome();

                        outputDetails.setMinimumProfit(minProfit);

                        double maxProfit = calculateProfit(maxYield, maxPrice, outputDetails.getUsedAcres(), productionCost);
                        maxProfit += cropTypeView.getAdditionalIncome();

                        outputDetails.setMaximumProfit(maxProfit);
                    } else {
//						outputDetails.setMinimumProfit(calculateProfit(expectedYield,expectedprice,outputDetails.getUsedAcres(), productionCost));
//						outputDetails.setMaximumProfit(calculateProfit(expectedYield,expectedprice,outputDetails.getUsedAcres(), productionCost));
                        double profit = calculateProfit(expectedYield, expectedPrice, outputDetails.getUsedAcres(), productionCost);
                        profit += cropTypeView.getAdditionalIncome();
                        outputDetails.setMinimumProfit(profit);
                        outputDetails.setMaximumProfit(profit);
                    }
                } else if (outputDetails.getForFirm()) {
                    outputDetails.setMinimumProfit(zeroDouble);
                    outputDetails.setMaximumProfit(zeroDouble);
                }

                if (outputBeanForStrategy.getSaveFlag()) {

					/*totalAcreage += outputDetails.getUsedAcres();
					potentialProfit += outputDetails.getProfit();*/

                    saveFarmOutputDetailsForField(outputDetails);
                } else {
                    farmOutputDetailsList.add(outputDetails);
                }
            }

            /*if (outputBeanForStrategy.getBaselineFlag() && outputBeanForStrategy.getSaveFlag()) {
                PlantingProfitLogger.info("Started saving as baseline strategy for farm by field....");
                farmCustomStrategyService.saveAsBaseLineStrategy(outputBeanForStrategy.getFarmInfo()*//*, potentialProfit, totalAcreage*//*);
                PlantingProfitLogger.info("Completed saving as baseline strategy for farm by field....");
            }*/


        } catch (Exception exception) {
            PlantingProfitLogger.error(exception);
        }

        return farmOutputDetailsList;
    }

    /**
     * @author - Abhishek
     * @#date - 21-12-2015
     */
    @Override
    public List<Object> calculateFarmOutputStatisticsForAcres(OutputBeanForStrategy outputBeanForStrategy) {

        /**
         * @changed - Abhishek
         * @date - 21-12-2015
         */
		/*List<CropType> cropTypeList = cropTypeService.getAllCropByFarmId(farmInfo.getId());
		List<CropResourceUsageView> resourceUsageViews = cropResourceUsageService.getAllCropResourceUsageByFarmId(farmInfo.getId());
		List<CropBeanForOutput> cropBeanForOutput = getCropBeanForCalculation(cropTypeList, resourceUsageViews);
		Set<CropsGroup> cropsGroups = farmInfo.getCropsGroup();*/

        Double potentialProfit = 0.0, totalAcreage = 0.0;

        List<Object> farmOutputDetailsList = new ArrayList<Object>();
        List<FarmOutputDetails> farmOutputDetails = getOutputDetailsForFarmByAcres(outputBeanForStrategy);

        try {
            /**
             * @changed - Abhishek
             * @date - 21-12-2015
             */
			/*farmOutputDetails = calculateAcresForEachCropForAcres(cropBeanForOutput, farmInfo, resourceUsageViews, cropsGroups);*/
            for (FarmOutputDetails outputDetails : farmOutputDetails) {
                outputDetails.setFarmInfo(outputBeanForStrategy.getFarmInfo());
                CropTypeView cropTypeView = new CropTypeView(outputDetails.getCropType());

                outputDetails.setUsedCapital(outputDetails.getUsedAcres() * Double.parseDouble("" + cropTypeView.getCalculatedVariableProductionCost()));
//				boolean firmSelected = cropTypeView.getFirmchecked().equals("true") ? true : false;
                double expectedYield = Double.parseDouble(AgricultureStandardUtils.removeAllCommas(cropTypeView.getIntExpCropYield()));
                double expectedprice = cropTypeView.getIntExpCropPrice().doubleValue();

                double minYield = Double.parseDouble(AgricultureStandardUtils.removeAllCommas(cropTypeView.getIntMinCropYield()));
                double minPrice = cropTypeView.getIntMinCropPrice().doubleValue();

                double maxYield = Double.parseDouble(AgricultureStandardUtils.removeAllCommas(cropTypeView.getIntMaxCropYield()));
                double maxPrice = cropTypeView.getIntMaxCropPrice().doubleValue();

                if (outputDetails.getUsedAcres() != zeroDouble) {

                    if (outputDetails.getForContract() || outputDetails.getForProposed()) {
                        double forwardAcres = cropTypeView.getForwardAcres();

                        /**
                         * @changed - Abhishek
                         * @Date - 03-2-2015
                         */
                        double forwardQuantity = Double.parseDouble(AgricultureStandardUtils.removeAllCommas(cropTypeView.getForwardQuantity().replaceAll("\\$", "")));
//					double forwardQuantity = Double.parseDouble(AgricultureStandardUtils.removeAllCommas(cropTypeView.getQuantity()));

                        double forwardPrice = Double.parseDouble(AgricultureStandardUtils.removeAllCommas(cropTypeView.getPriceStr()));

                        PlantingProfitLogger.info(forwardPrice + "----------------" + forwardQuantity + "----------------" + forwardAcres + "----------------" + expectedYield + "----------------" + expectedprice + "----------------"
                                + outputDetails.getUsedAcres() + "----------------" + Double.parseDouble("" + cropTypeView.getCalculatedVariableProductionCost()));


                        double profit = calculatePofitWithFirmSelected(forwardPrice, forwardAcres, expectedYield, expectedprice, outputDetails.getUsedAcres(),
                                Double.parseDouble("" + cropTypeView.getCalculatedVariableProductionCost()), forwardQuantity);

                        profit = profit + cropTypeView.getAdditionalIncome();

                        outputDetails.setProfit(profit);
                    } else {
                        outputDetails.setProfit(calculateProfit(expectedYield, expectedprice, outputDetails.getUsedAcres(), Double.parseDouble("" + cropTypeView.getCalculatedVariableProductionCost())));
                        if ((maxPrice != 0 && maxYield != 0) && (minPrice != 0 && minYield != 0)) {
                            double minProfit = calculateProfit(minYield, minPrice, outputDetails.getUsedAcres(), Double.parseDouble("" + cropTypeView.getCalculatedVariableProductionCost()));
                            minProfit = minProfit + cropTypeView.getAdditionalIncome();
                            outputDetails.setMinimumProfit(minProfit);

                            double maxProfit = calculateProfit(maxYield, maxPrice, outputDetails.getUsedAcres(), Double.parseDouble("" + cropTypeView.getCalculatedVariableProductionCost()));
                            maxProfit = maxProfit + cropTypeView.getAdditionalIncome();
                            outputDetails.setMaximumProfit(maxProfit);
                        } else {
                            double profit = calculateProfit(expectedYield, expectedprice, outputDetails.getUsedAcres(), Double.parseDouble("" + cropTypeView.getCalculatedVariableProductionCost()));
                            profit = profit + cropTypeView.getAdditionalIncome();

                            outputDetails.setMinimumProfit(profit);
                            outputDetails.setMaximumProfit(profit);
                        }

                    }

                    if (outputDetails.getForProposed()) {
                        if ((maxPrice != 0 && maxYield != 0) && (minPrice != 0 && minYield != 0)) {
//							outputDetails.setMinimumProfit(calculateProfit(minYield,minPrice,outputDetails.getUsedAcres(), Double.parseDouble(""+ cropTypeView.getCalculatedVariableProductionCost())));
//							outputDetails.setMaximumProfit(calculateProfit(maxYield,maxPrice,outputDetails.getUsedAcres(), Double.parseDouble(""+ cropTypeView.getCalculatedVariableProductionCost())));
                            double minProfit = calculateProfit(minYield, minPrice, outputDetails.getUsedAcres(), Double.parseDouble("" + cropTypeView.getCalculatedVariableProductionCost()));
                            minProfit = minProfit + cropTypeView.getAdditionalIncome();
                            outputDetails.setMinimumProfit(minProfit);

                            double maxProfit = calculateProfit(maxYield, maxPrice, outputDetails.getUsedAcres(), Double.parseDouble("" + cropTypeView.getCalculatedVariableProductionCost()));
                            maxProfit = maxProfit + cropTypeView.getAdditionalIncome();
                            outputDetails.setMaximumProfit(maxProfit);
                        } else {
//							outputDetails.setMinimumProfit(calculateProfit(expectedYield,expectedprice,outputDetails.getUsedAcres(), Double.parseDouble(""+ cropTypeView.getCalculatedVariableProductionCost())));
//							outputDetails.setMaximumProfit(calculateProfit(expectedYield,expectedprice,outputDetails.getUsedAcres(), Double.parseDouble(""+ cropTypeView.getCalculatedVariableProductionCost())));
                            double profit = calculateProfit(expectedYield, expectedprice, outputDetails.getUsedAcres(), Double.parseDouble("" + cropTypeView.getCalculatedVariableProductionCost()));
                            profit = profit + cropTypeView.getAdditionalIncome();

                            outputDetails.setMinimumProfit(profit);
                            outputDetails.setMaximumProfit(profit);
                        }
                    } else if (outputDetails.getForContract()) {
                        outputDetails.setMinimumProfit(zeroDouble);
                        outputDetails.setMaximumProfit(zeroDouble);
                    }

                } else if (outputDetails.getUsedAcres() == zeroDouble) {
                    outputDetails.setProfit(zeroDouble);
                    outputDetails.setMinimumProfit(zeroDouble);
                    outputDetails.setMaximumProfit(zeroDouble);
                }

                if (outputBeanForStrategy.getSaveFlag()) {

					/*totalAcreage += outputDetails.getUsedAcres();
					potentialProfit += outputDetails.getProfit();*/

                    saveFarmOutputDetails(outputDetails);
                } else {
                    farmOutputDetailsList.add(outputDetails);
                }
            }

            /*if (outputBeanForStrategy.getBaselineFlag() && outputBeanForStrategy.getSaveFlag()) {
                PlantingProfitLogger.info("Started saving as baseline strategy for farm by acreage....");
                farmCustomStrategyService.saveAsBaseLineStrategy(outputBeanForStrategy.getFarmInfo()*//*, potentialProfit, totalAcreage*//*);
                PlantingProfitLogger.info("Completed saving as baseline strategy for farm by acreage....");
            }*/


        } catch (Exception exception) {
            PlantingProfitLogger.error(exception);
        }

        return farmOutputDetailsList;
    }

	/*private void calculateMinMaxProfit(Object outputDetails, Double yield, Double price, Double usedAcres, Double productionCost, String key){

		if(key.equalsIgnoreCase("min") && outputDetails instanceof FarmOutputDetails){
			outputDetails = (FarmOutputDetails)outputDetails;

			if(yield != 0 && price != 0){
				outputDetails.setMinimumProfit(calculateProfit(yield, price, usedAcres, productionCost));
			} else {
				outputDetails.setMinimumProfit(zeroDouble);
			}
		} else if(key.equalsIgnoreCase("max") && outputDetails instanceof FarmOutputDetails){
			if(yield != 0 && price != 0){
				outputDetails.setMaximumProfit(calculateProfit(maxYield,maxPrice,outputDetails.getUsedAcres(), Double.parseDouble(""+ cropTypeView.getCalculatedVariableProductionCost())));
			} else {
				outputDetails.setMaximumProfit(zeroDouble);
			}
		}


	}*/


    /**
     * @author - Abhishek
     * @#date - 21-12-2015
     */
    @Override
    public List<FarmOutputDetails> getAllFarmOutputDetailsByFarm(OutputBeanForStrategy outputBeanForStrategy) {
        PlantingProfitLogger.info("get All FarmOutputDetails By farm Id .. " + outputBeanForStrategy.getFarmID());
        List<FarmOutputDetails> farmOutputDetailsList = new ArrayList<FarmOutputDetails>();

        if (outputBeanForStrategy.getBaselineFlag()) {
            Session session = sessionFactory.openSession();
            try {
                Query query = session.createQuery("FROM FarmOutputDetails where farmInfo.id=:farmId ORDER BY cropType.cropName ASC");
                query.setParameter("farmId", outputBeanForStrategy.getFarmID());
                farmOutputDetailsList = query.list();
                if (farmOutputDetailsList != null) {
//                    PlantingProfitLogger.info("size -->>" + farmOutputDetailsList.size());
                }

            } catch (Exception e) {
                PlantingProfitLogger.error(e);
                farmOutputDetailsList = null;
            } finally {
                session.close();
            }
        } else {
            List<Object> objectList = calculateFarmOutputStatistics(outputBeanForStrategy);
            for (Object objects : objectList) {
                farmOutputDetailsList.add((FarmOutputDetails) objects);
            }

        }

        return farmOutputDetailsList;
    }

    /**
     * @author - Abhishek
     * @#date - 21-12-2015
     */
    @Override
    public List<FarmOutputDetailsForField> getAllFarmOutputDetailsForFieldByFarm(OutputBeanForStrategy outputBeanForStrategy) {
        PlantingProfitLogger.debug("get All FarmOutputDetailsForField By farm Id .. " + outputBeanForStrategy.getFarmID());
        List<FarmOutputDetailsForField> farmOutputDetailsForField = new ArrayList<FarmOutputDetailsForField>();

        if (outputBeanForStrategy.getBaselineFlag()) {
            Session session = sessionFactory.openSession();
            try {
                Query query = session.createQuery("FROM FarmOutputDetailsForField where farmInfo.id=:farmId ORDER BY cropType.cropName ASC");
                query.setParameter("farmId", outputBeanForStrategy.getFarmID());
                farmOutputDetailsForField = query.list();
                if (farmOutputDetailsForField != null) {
//                    PlantingProfitLogger.info("size -->>" + farmOutputDetailsForField.size());
                }
            } catch (Exception e) {
                PlantingProfitLogger.error(e);
                farmOutputDetailsForField = null;
            } finally {
                session.close();
            }
        } else {
            List<Object> objects = calculateFarmOutputStatistics(outputBeanForStrategy);
            for (Object objectList : objects) {
                farmOutputDetailsForField.add((FarmOutputDetailsForField) objectList);
            }

        }

        return farmOutputDetailsForField;
    }

	/* ********************** End ********************** */

    /**
     * @author - Abhishek
     * @#date - 21-12-2015
     */
    private List<FarmOutputDetails> getOutputDetailsForFarmByAcres(OutputBeanForStrategy outputBeanForStrategy) {

        List<FarmOutputDetails> farmOutputDetails;

        if (outputBeanForStrategy.getBaselineFlag()) {
            List<CropType> cropTypeList = cropTypeService.getAllCropByFarmId(outputBeanForStrategy.getFarmInfo().getId());
            List<CropResourceUsageView> resourceUsageViews = cropResourceUsageService.getAllCropResourceUsageByFarmId(outputBeanForStrategy.getFarmInfo().getId());
            List<CropBeanForOutput> cropBeanForOutput = getCropBeanForCalculation(cropTypeList, resourceUsageViews);
            Set<CropsGroup> cropsGroups = outputBeanForStrategy.getFarmInfo().getCropsGroup();

            farmOutputDetails = calculateAcresForEachCropForAcres(cropBeanForOutput, outputBeanForStrategy.getFarmInfo(),
                    resourceUsageViews, cropsGroups, true);


        } else {

            FarmCustomStrategyView farmCustomStrategyView = farmCustomStrategyService.getDataForCustomStrategy(outputBeanForStrategy.getFarmInfo().getId(), outputBeanForStrategy.getStrategyID());

			/*List<CropType> cropTypeList = cropTypeService.getAllCropByFarmId(outputBeanForStrategy.getFarmInfo().getId());*/
            List<CropType> cropTypeList = outputBeanForStrategy.getCropTypeList();

            List<CropResourceUsageView> resourceUsageViews = null;
            if (outputBeanForStrategy.getSensitivityFlag()) {
                resourceUsageViews = outputBeanForStrategy.getResourceUsageViews();
            } else {
                resourceUsageViews = cropResourceUsageService.getAllCropResourceUsageByFarmId(outputBeanForStrategy.getFarmInfo().getId());
            }

            Set<CropsGroup> cropsGroups = outputBeanForStrategy.getFarmInfo().getCropsGroup();

            //	updating values for crops maximum or minimum limits
            /*if (farmCustomStrategyView.getCustomStrategyForCropsViews() != null) {
                for (FarmCustomStrategyForCropView farmCustomStrategyForCropView : farmCustomStrategyView.getCustomStrategyForCropsViews()) {
                    for (CropType cropType : cropTypeList) {
                        if (cropType.getSelected()) {
                            if (cropType.getCropName().equalsIgnoreCase(farmCustomStrategyForCropView.getCropname())) {
                                if (farmCustomStrategyForCropView.getMinimum() != null) {
                                    cropType.getCropLimit().setMinimumAcres(farmCustomStrategyForCropView.getMinimum());
                                }
                                if (farmCustomStrategyForCropView.getMaximum() != null) {
                                    cropType.getCropLimit().setMaximumAcres(farmCustomStrategyForCropView.getMaximum());
                                }
                            }
                        }
                    }
                }
            }

            //	updating values for resources used
            if (farmCustomStrategyView.getCustomStrategyForResourcesView() != null) {
                for (FarmCustomStrategyForResourceView farmCustomStrategyForResourceView : farmCustomStrategyView.getCustomStrategyForResourcesView()) {
                    for (CropResourceUsageView resourceUsageView : resourceUsageViews) {
                        if (resourceUsageView.getCropResourceUse().equalsIgnoreCase(farmCustomStrategyForResourceView.getResourseName())) {
                            resourceUsageView.setCropResourceUseAmount(Long.toString(farmCustomStrategyForResourceView.getResourseValue()));
                        }
                    }

                }
            }*/

            List<CropBeanForOutput> cropBeanForOutput = getCropBeanForCalculation(cropTypeList, resourceUsageViews);

            //	updating values for groupTypes
            if (farmCustomStrategyView.getCustomStrategyForGroupsView() != null) {
                for (FarmCustomStrategyForGroupView farmCustomStrategyForGroupView : farmCustomStrategyView.getCustomStrategyForGroupsView()) {
                    for (CropsGroup cropsGroup : cropsGroups) {
                        if (cropsGroup.getCropsGroupName().equalsIgnoreCase(farmCustomStrategyForGroupView.getGroupname())) {
                            if (farmCustomStrategyForGroupView.getMinimum() != null) {
                                cropsGroup.setMinimumAcres(farmCustomStrategyForGroupView.getMinimum());
                            }
                            if (farmCustomStrategyForGroupView.getMaximum() != null) {
                                cropsGroup.setMaximumAcres(farmCustomStrategyForGroupView.getMaximum());
                            }
                        }
                    }

                }
            }

            farmOutputDetails = calculateAcresForEachCropForAcres(cropBeanForOutput, outputBeanForStrategy.getFarmInfo(),
                    resourceUsageViews, cropsGroups, false);
        }

        return farmOutputDetails;
    }

    /**
     * @author - Abhishek
     * @#date - 21-12-2015
     */
    private List<FarmOutputDetailsForField> getOutputDetailsForFarmByField(OutputBeanForStrategy outputBeanForStrategy) {
        List<FarmOutputDetailsForField> farmOutputDetails = null;

        if (outputBeanForStrategy.getBaselineFlag()) {
            List<CropResourceUsageView> resourceUsageViews = cropResourceUsageService.getAllCropResourceUsageByFarmId(outputBeanForStrategy.getFarmInfo().getId());
            List<CropType> cropTypeList = cropTypeService.getAllCropByFarmId(outputBeanForStrategy.getFarmInfo().getId());
            List<CropBeanForOutput> cropBeanForOutput = getCropBeanForCalculation(cropTypeList, resourceUsageViews);
            List<FieldInfoView> fieldInfoViews = fieldInfoService.getAllFieldsByFarmId(outputBeanForStrategy.getFarmInfo().getId());
            Set<CropsGroup> cropsGroups = outputBeanForStrategy.getFarmInfo().getCropsGroup();

            farmOutputDetails = calculateAcresForEachCropForField(cropBeanForOutput, resourceUsageViews, fieldInfoViews, cropsGroups);
        } else {

            FarmCustomStrategyView dataForCustomStrategy = farmCustomStrategyService.getDataForCustomStrategy(outputBeanForStrategy.getFarmInfo().getId(), outputBeanForStrategy.getStrategyID());

            List<CropResourceUsageView> resourceUsageViews;
            List<CropType> cropTypeList;
            if (outputBeanForStrategy.getSensitivityFlag()) {
                resourceUsageViews = outputBeanForStrategy.getResourceUsageViews();
                cropTypeList = outputBeanForStrategy.getCropTypeList();
            } else {
                cropTypeList = cropTypeService.getAllCropByFarmId(outputBeanForStrategy.getFarmInfo().getId());
                resourceUsageViews = cropResourceUsageService.getAllCropResourceUsageByFarmId(outputBeanForStrategy.getFarmInfo().getId());
            }

            Set<CropsGroup> cropsGroups = outputBeanForStrategy.getFarmInfo().getCropsGroup();

            /**
             * @changed - Abhishek
             * @date - 09-01-2016
             * @desc - applied null pointer check
             */
            //	updating values for crops maximum or minimum limits
            /*if (dataForCustomStrategy.getCustomStrategyForCropsViews() != null) {
                for (FarmCustomStrategyForCropView farmCustomStrategyForCropView : dataForCustomStrategy.getCustomStrategyForCropsViews()) {
                    for (CropType cropType : cropTypeList) {
                        if (cropType.getCropName().equalsIgnoreCase(farmCustomStrategyForCropView.getCropname())) {
                            cropType.getCropLimit().setMinimumAcres(farmCustomStrategyForCropView.getMinimum());
                            cropType.getCropLimit().setMaximumAcres(farmCustomStrategyForCropView.getMaximum());
                        }
                    }
                }
            }*/

            /**
             * @changed - Abhishek
             * @date - 09-01-2016
             * @desc - applied null pointer check
             */
            //	updating values for resources used
            /*if (dataForCustomStrategy.getCustomStrategyForResourcesView() != null) {
                for (FarmCustomStrategyForResourceView farmCustomStrategyForResourceView : dataForCustomStrategy.getCustomStrategyForResourcesView()) {
                    for (CropResourceUsageView resourceUsageView : resourceUsageViews) {
                        if (resourceUsageView.getCropResourceUse().equalsIgnoreCase(farmCustomStrategyForResourceView.getResourseName())) {
                            resourceUsageView.setCropResourceUseAmount(Long.toString(farmCustomStrategyForResourceView.getResourseValue()));
                            resourceUsageView.setCropResourceUseAmount(Long.toString(farmCustomStrategyForResourceView.getResourseValue()));
                        }
                    }

                }
            }*/

            List<CropBeanForOutput> cropBeanForOutput = getCropBeanForCalculation(cropTypeList, resourceUsageViews);

            /**
             * @changed - Abhishek
             * @date - 09-01-2016
             * @desc - applied null pointer check
             */
            //	updating values for groupTypes
            if (dataForCustomStrategy.getCustomStrategyForGroupsView() != null) {
                for (FarmCustomStrategyForGroupView farmCustomStrategyForGroupView : dataForCustomStrategy.getCustomStrategyForGroupsView()) {
                    for (CropsGroup cropsGroup : cropsGroups) {
                        if (cropsGroup.getCropsGroupName().equalsIgnoreCase(farmCustomStrategyForGroupView.getGroupname())) {
                            cropsGroup.setMinimumAcres(farmCustomStrategyForGroupView.getMinimum());
                            cropsGroup.setMaximumAcres(farmCustomStrategyForGroupView.getMaximum());
                        }
                    }

                }
            }

            List<FieldInfoView> fieldInfoViews = fieldInfoService.getAllFieldsByFarmId(outputBeanForStrategy.getFarmInfo().getId());


            farmOutputDetails = calculateAcresForEachCropForField(cropBeanForOutput, resourceUsageViews, fieldInfoViews, cropsGroups);
        }

        return farmOutputDetails;
    }

    @Override
    public List<CropBeanForOutput> getCropBeanForCalculation(List<CropType> cropTypeList, List<CropResourceUsageView> resourceUsageViews) {
        List<CropBeanForOutput> cropBeanForOutputList = new ArrayList<CropBeanForOutput>();
        PlantingProfitLogger.info("************************************getCropBeanForCalculation()************************************\n");
        for (CropType cropType : cropTypeList) {
            if (cropType.getSelected()) {
                CropBeanForOutput cropBeanForOutput = new CropBeanForOutput();
                cropBeanForOutput.setCropType(cropType);
                try {
                    CropTypeView cropTypeView = new CropTypeView(cropType);

                    /**
                     * @added - Abhishek
                     * @date - 06-02-2016
                     * @desc - monty carlo analysis for crop profit
                     * @update - 18-05-2016
                     * @desc - according to monty carlo status for farm
                     */
                    Double profit;
                    PlantingProfitLogger.info("Monty Carlo Status : " + cropType.getFarmInfo().getMontyCarloStatus());
                    if (cropType.getFarmInfo().getMontyCarloStatus()) {
                        profit = cropTypeView.getMontyCarloAnalysisProfit();
                    } else {
                        profit = ((Double.parseDouble(AgricultureStandardUtils.removeAllCommas(cropTypeView.getIntExpCropYield())) *
                                Double.parseDouble(AgricultureStandardUtils.removeAllCommas(cropTypeView.getIntExpCropPrice().toString())))
                                - Double.parseDouble(cropTypeView.getCalculatedVariableProductionCost().toString()));
                    }

                    PlantingProfitLogger.info("CropBeanForOutput profit : " + profit);


                    Double forwardProfit = ((Double.parseDouble(AgricultureStandardUtils.removeAllCommas(cropTypeView.getIntExpCropYield())) *
                            Double.parseDouble(AgricultureStandardUtils.removeAllCommas(cropTypeView.getPriceStr())))
                            - Double.parseDouble(cropTypeView.getCalculatedVariableProductionCost().toString()));

                    Double acre = 1.0;
                    Double variableProductionCost = Double.parseDouble(cropTypeView.getCalculatedVariableProductionCost().toString());
                    Double minAcre = Double.parseDouble((cropTypeView.getMinimumAcresWithoutComma().equalsIgnoreCase("")) ? "0" : cropTypeView.getMinimumAcresWithoutComma());
                    Double maxAcre = Double.parseDouble((cropTypeView.getMaximumAcresWithoutComma().equalsIgnoreCase("")) ? "0" : cropTypeView.getMaximumAcresWithoutComma());
                    if (cropType.getCropForwardSales() != null
                            && cropType.getCropForwardSales().getFirmchecked().equals("true")) {
//						minAcre += cropType.getCropForwardSales().getForwardAcres();
//						if (maxAcre > new Double(0)) {
//							maxAcre += cropType.getCropForwardSales().getForwardAcres();
//						}
                        cropBeanForOutput.setFirmAcres(cropType.getCropForwardSales().getAcres());
                        cropBeanForOutput.setProposedAcres(zeroDouble);
                        cropBeanForOutput.setForwardprofit(forwardProfit);
                    } else if (cropType.getCropForwardSales() != null
                            && cropType.getCropForwardSales().getProposedchecked()) {
                        cropBeanForOutput.setFirmAcres(zeroDouble);
                        cropBeanForOutput.setProposedAcres(cropType.getCropForwardSales().getAcres());
                        cropBeanForOutput.setForwardprofit(forwardProfit);
                    } else {
                        cropBeanForOutput.setFirmAcres(zeroDouble);
                        cropBeanForOutput.setProposedAcres(zeroDouble);
                        cropBeanForOutput.setForwardprofit(zeroDouble);
                    }
                    cropBeanForOutput.setVariableProductionCost(variableProductionCost);
                    List<CropResourceUsageFieldVariances> cropResourceUsageFieldVariances = cropResourceUsageFieldVariancesDao.getAllResourceByCrop(cropType.getId());
                    Map<String, Double> resourceMap = new HashMap<String, Double>();
                    for (CropResourceUsageView cropResourceUsageView : resourceUsageViews) {
                        if (cropResourceUsageView.isActive()) {
                            for (CropResourceUsageFieldVariances fieldVariances : cropResourceUsageFieldVariances) {
                                if (!(cropResourceUsageView.getCropResourceUse().equals("Land") || cropResourceUsageView.getCropResourceUse().equals("Capital"))
                                        && cropResourceUsageView.getCropResourceUse().equals(fieldVariances.getCropFieldResourceUse())) {
                                    resourceMap.put(cropResourceUsageView.getCropResourceUse(), Double.parseDouble(AgricultureStandardUtils.removeAllCommas(fieldVariances.getCropResourceAmount())));
                                    break;
                                }
                            }
                            if (resourceMap.get(cropResourceUsageView.getCropResourceUse()) == null) {
                                if (cropResourceUsageView.getCropResourceUse().equals("Land")) {
                                    resourceMap.put(cropResourceUsageView.getCropResourceUse(), new Double(1));
                                } else if (cropResourceUsageView.getCropResourceUse().equals("Capital")) {
                                    resourceMap.put(cropResourceUsageView.getCropResourceUse(), cropBeanForOutput.getVariableProductionCost());
                                } else {
                                    resourceMap.put(cropResourceUsageView.getCropResourceUse(), zeroDouble);
                                }
                            }
                        }

                    }
                    cropBeanForOutput.setResourceList(resourceMap);
                    cropBeanForOutput.setProfit(profit);
                    cropBeanForOutput.setAcres(acre);
                    cropBeanForOutput.setMinAcre(minAcre);
                    cropBeanForOutput.setMaxAcre(maxAcre);
                    PlantingProfitLogger.info(" cropBeanForOutput.getProfit() : " + cropBeanForOutput.getProfit() + " cropBeanForOutput.getAcres() : " + cropBeanForOutput.getAcres()
                            + " cropBeanForOutput.getVariableProductionCost() : " + cropBeanForOutput.getVariableProductionCost() + " cropBeanForOutput.getMinAcre() : "
                            + cropBeanForOutput.getMinAcre() + " cropBeanForOutput.getMaxAcre() : " + cropBeanForOutput.getMaxAcre());
                    PlantingProfitLogger.info(cropBeanForOutput.getResourceList());

                } catch (Exception exception) {
                    PlantingProfitLogger.error(exception);
                }
                cropBeanForOutputList.add(cropBeanForOutput);
            }
        }
        PlantingProfitLogger.info("\n************************************getCropBeanForCalculation()************************************");
        return cropBeanForOutputList;
    }

    @Override
    public List<FarmOutputDetails> calculateAcresForEachCropForAcres(List<CropBeanForOutput> cropBeanForOutputList, FarmInfo farmInfo,
                                                                     List<CropResourceUsageView> resourceUsageViews, Set<CropsGroup> cropsGroups, boolean updateFlag) {
        Result result = linearProgramingSolveDao.getLinearProgramingResultForAcerage(cropBeanForOutputList, farmInfo.getLand(), resourceUsageViews, cropsGroups);
        List<FarmOutputDetails> farmOutputDetails = new ArrayList<FarmOutputDetails>();
        if (result != null) {
            PlantingProfitLogger.info("Result for calculation is " + result);
            for (CropBeanForOutput beanForOutput : cropBeanForOutputList) {

                if (beanForOutput.getCropType().getCropForwardSales() != null
                        && (beanForOutput.getCropType().getCropForwardSales().getFirmchecked().equals("true")
                        || beanForOutput.getCropType().getCropForwardSales().getProposedchecked())) {

                    FarmOutputDetails outputDetails = new FarmOutputDetails();
                    FarmOutputDetails outputDetailsForForward = null;
                    outputDetails.setCropType(beanForOutput.getCropType());
                    outputDetails.setForContract(false);
                    outputDetails.setForProposed(false);
                    try {
                        Number number = result.get(beanForOutput.getCropType().getCropName());
                        if(number == null){
                            outputDetails.setUsedAcres(zeroDouble);
                        } else {
                            outputDetails.setUsedAcres(AgricultureStandardUtils.withoutDecimalAndCommaToLong(number.doubleValue()).doubleValue());
                        }

                    } catch (Exception e) {
                        PlantingProfitLogger.error(e.getMessage(), e);
                    }
                    try {
                        if (beanForOutput.getFirmAcres() > zeroDouble) {
                            outputDetailsForForward = new FarmOutputDetails();
                            outputDetailsForForward.setCropType(beanForOutput.getCropType());
                            outputDetailsForForward.setForContract(true);
                            outputDetailsForForward.setForProposed(false);
                            outputDetailsForForward.setUsedAcres(AgricultureStandardUtils.withoutDecimalAndCommaToLong(result.get(beanForOutput.getCropType().getCropName() + " (Contract)").doubleValue()).doubleValue());
                        } else if (beanForOutput.getProposedAcres() > zeroDouble
                                && result.get(beanForOutput.getCropType().getCropName() + " (Proposed)").doubleValue() > zeroDouble) {
                            outputDetailsForForward = new FarmOutputDetails();
                            outputDetailsForForward.setCropType(beanForOutput.getCropType());
                            outputDetailsForForward.setForContract(false);
                            outputDetailsForForward.setForProposed(true);
                            outputDetailsForForward.setUsedAcres(AgricultureStandardUtils.withoutDecimalAndCommaToLong(result.get(beanForOutput.getCropType().getCropName() + " (Proposed)").doubleValue()).doubleValue());
                        }
                    }
                    catch (Exception ex){
                        PlantingProfitLogger.error(ex);
                    }
                    PlantingProfitLogger.info(beanForOutput.getCropType().getCropName() + "------------" + outputDetails.getUsedAcres());
                    farmOutputDetails.add(outputDetails);
                    if (outputDetailsForForward != null)
                        farmOutputDetails.add(outputDetailsForForward);
                } else {
                    FarmOutputDetails outputDetails = new FarmOutputDetails();
                    outputDetails.setCropType(beanForOutput.getCropType());
                    outputDetails.setForContract(false);
                    outputDetails.setForProposed(false);
                    try {
                        outputDetails.setUsedAcres(result.get(beanForOutput.getCropType().getCropName()).doubleValue());
                    } catch (Exception exception) {
                        outputDetails.setUsedAcres(zeroDouble);
                        //					PlantingProfitLogger.error(exception);
                    }
                    PlantingProfitLogger.info(beanForOutput.getCropType().getCropName() + "------------" + outputDetails.getUsedAcres());
                    farmOutputDetails.add(outputDetails);
                }

            }
        } else {
            PlantingProfitLogger.info("Result for calculations is null");
            for (CropBeanForOutput beanForOutput : cropBeanForOutputList) {
                FarmOutputDetails outputDetails = new FarmOutputDetails();
                outputDetails.setCropType(beanForOutput.getCropType());
                outputDetails.setUsedAcres(zeroDouble);
                outputDetails.setForContract(false);
                outputDetails.setForProposed(false);
                PlantingProfitLogger.info(beanForOutput.getCropType().getCropName() + "------------" + outputDetails.getUsedAcres());
                farmOutputDetails.add(outputDetails);
            }
        }

        if (result != null) {
            for (CropResourceUsage cropResourceUsage : farmInfo.getCropResourceUsage()) {
                if (result.getDualValue(cropResourceUsage.getCropResourceUse()) != null && result.getDualValue(cropResourceUsage.getCropResourceUse()).doubleValue() > zeroDouble) {
                    ResourceDualValue resourceDualValue = null;
                    for (ResourceDualValue dualValue : farmInfo.getResourceDualValues()) {
                        if (dualValue.getCropResourceUsage().getCropResourceUse().equals(cropResourceUsage.getCropResourceUse())) {
                            resourceDualValue = dualValue;
                            break;
                        }
                    }
                    if (resourceDualValue == null) {
                        resourceDualValue = new ResourceDualValue();
                        resourceDualValue.setCropResourceUsage(cropResourceUsage);
                        resourceDualValue.setFarmInfo(farmInfo);
                        resourceDualValue.setDualValue(result.getDualValue(cropResourceUsage.getCropResourceUse()).doubleValue());
                        cropResourceUsage.setResourceDualValue(resourceDualValue);
                        farmInfo.getResourceDualValues().add(resourceDualValue);
                    } else {
                        resourceDualValue.setDualValue(result.getDualValue(cropResourceUsage.getCropResourceUse()).doubleValue());
                    }
                    if (result.getPrimalValue(cropResourceUsage.getCropResourceUse()) != null && result.getPrimalValue(cropResourceUsage.getCropResourceUse()).doubleValue() > zeroDouble) {
                        resourceDualValue.setPrimalValue(result.getPrimalValue(cropResourceUsage.getCropResourceUse()).doubleValue());
                    }
                } else {
                    for (ResourceDualValue dualValue : farmInfo.getResourceDualValues()) {
                        if (dualValue.getCropResourceUsage().getCropResourceUse().equals(cropResourceUsage.getCropResourceUse())) {
                            dualValue.setFarmInfo(null);
                            dualValue.setCropResourceUsage(null);
                            cropResourceUsage.setResourceDualValue(null);
                            farmInfo.getResourceDualValues().remove(dualValue);
                            break;
                        }
                    }
                }
            }
            for (CropBeanForOutput beanForOutput : cropBeanForOutputList) {
                if ((beanForOutput.getMinAcre() > 0 && result.getDualValue("Minimum " + beanForOutput.getCropType().getCropName()) != null && result.getDualValue("Minimum " + beanForOutput.getCropType().getCropName()).doubleValue() > zeroDouble)
                        || (beanForOutput.getMaxAcre() > 0 && result.getDualValue("Maximum " + beanForOutput.getCropType().getCropName()) != null && result.getDualValue("Maximum " + beanForOutput.getCropType().getCropName()).doubleValue() > zeroDouble)) {
                    if (beanForOutput.getMinAcre() > 0 && result.getDualValue("Minimum " + beanForOutput.getCropType().getCropName()) != null && result.getDualValue("Minimum " + beanForOutput.getCropType().getCropName()).doubleValue() > zeroDouble) {
                        CropLimitDualValue cropLimitDualValue = null;
                        for (CropLimitDualValue dualValue : farmInfo.getCropLimitDualValues()) {
                            if (dualValue.getCropType().getCropName().equals(beanForOutput.getCropType().getCropName())) {
                                cropLimitDualValue = dualValue;
                                break;
                            }
                        }
                        if (cropLimitDualValue == null) {
                            cropLimitDualValue = new CropLimitDualValue();
                            cropLimitDualValue.setCropType(beanForOutput.getCropType());
                            cropLimitDualValue.setFarmInfo(farmInfo);
                            cropLimitDualValue.setMinimumDualValue(result.getDualValue("Minimum " + beanForOutput.getCropType().getCropName()).doubleValue());
                            farmInfo.getCropLimitDualValues().add(cropLimitDualValue);
                        } else {
                            cropLimitDualValue.setMinimumDualValue(result.getDualValue("Minimum " + beanForOutput.getCropType().getCropName()).doubleValue());
                        }
                        if (result.getPrimalValue("Minimum " + beanForOutput.getCropType().getCropName()) != null && result.getPrimalValue("Minimum " + beanForOutput.getCropType().getCropName()).doubleValue() > zeroDouble) {
                            cropLimitDualValue.setMinimumPrimalValue(result.getPrimalValue("Minimum " + beanForOutput.getCropType().getCropName()).doubleValue());
                        }
                    }
                    if (beanForOutput.getMaxAcre() > 0 && result.getDualValue("Maximum " + beanForOutput.getCropType().getCropName()) != null && result.getDualValue("Maximum " + beanForOutput.getCropType().getCropName()).doubleValue() > zeroDouble) {
                        CropLimitDualValue cropLimitDualValue = null;
                        for (CropLimitDualValue dualValue : farmInfo.getCropLimitDualValues()) {
                            if (dualValue.getCropType().getCropName().equals(beanForOutput.getCropType().getCropName())) {
                                cropLimitDualValue = dualValue;
                                break;
                            }
                        }
                        if (cropLimitDualValue == null) {
                            cropLimitDualValue = new CropLimitDualValue();
                            cropLimitDualValue.setCropType(beanForOutput.getCropType());
                            cropLimitDualValue.setFarmInfo(farmInfo);
                            cropLimitDualValue.setMaximumDualValue(result.getDualValue("Maximum " + beanForOutput.getCropType().getCropName()).doubleValue());
                            farmInfo.getCropLimitDualValues().add(cropLimitDualValue);
                        } else {
                            cropLimitDualValue.setMaximumDualValue(result.getDualValue("Maximum " + beanForOutput.getCropType().getCropName()).doubleValue());
                        }
                        if (result.getPrimalValue("Maximum " + beanForOutput.getCropType().getCropName()) != null && result.getPrimalValue("Maximum " + beanForOutput.getCropType().getCropName()).doubleValue() > zeroDouble) {
                            cropLimitDualValue.setMaximumPrimalValue(result.getPrimalValue("Maximum " + beanForOutput.getCropType().getCropName()).doubleValue());
                        }
                    }
                } else {
                    for (CropLimitDualValue cropLimitDualValue : farmInfo.getCropLimitDualValues()) {
                        if (cropLimitDualValue.getCropType().getCropName().equals(beanForOutput.getCropType().getCropName())) {
                            cropLimitDualValue.setFarmInfo(null);
                            farmInfo.getCropLimitDualValues().remove(cropLimitDualValue);
                            //break;
                        }
                    }
                }
            }
            for (CropsGroup cropsGroup : cropsGroups) {
                if ((AgricultureStandardUtils.stringToLong(cropsGroup.getMinimumAcres()) > 0 && result.getDualValue("Minimum " + cropsGroup.getCropsGroupName()) != null && result.getDualValue("Minimum " + cropsGroup.getCropsGroupName()).doubleValue() > zeroDouble)
                        || (AgricultureStandardUtils.stringToLong(cropsGroup.getMaximumAcres()) > 0 && result.getDualValue("Maximum " + cropsGroup.getCropsGroupName()) != null && result.getDualValue("Maximum " + cropsGroup.getCropsGroupName()).doubleValue() > zeroDouble)) {
                    if (AgricultureStandardUtils.stringToLong(cropsGroup.getMinimumAcres()) > 0 && result.getDualValue("Minimum " + cropsGroup.getCropsGroupName()) != null && result.getDualValue("Minimum " + cropsGroup.getCropsGroupName()).doubleValue() > zeroDouble) {
                        GroupLimitDualValue groupLimitDualValue = null;
                        for (GroupLimitDualValue dualValue : farmInfo.getGroupLimitDualValues()) {
                            if (dualValue.getCropsGroup().getCropsGroupName().equals(cropsGroup.getCropsGroupName())) {
                                groupLimitDualValue = dualValue;
                                break;
                            }
                        }
                        if (groupLimitDualValue == null) {
                            groupLimitDualValue = new GroupLimitDualValue();
                            groupLimitDualValue.setCropsGroup(cropsGroup);
                            groupLimitDualValue.setFarmInfo(farmInfo);
                            groupLimitDualValue.setMinimumDualValue(result.getDualValue("Minimum " + cropsGroup.getCropsGroupName()).doubleValue());
                            farmInfo.getGroupLimitDualValues().add(groupLimitDualValue);
                        } else {
                            groupLimitDualValue.setMinimumDualValue(result.getDualValue("Minimum " + cropsGroup.getCropsGroupName()).doubleValue());
                        }
                        if (result.getPrimalValue("Minimum " + cropsGroup.getCropsGroupName()) != null && result.getPrimalValue("Minimum " + cropsGroup.getCropsGroupName()).doubleValue() > zeroDouble) {
                            groupLimitDualValue.setMinimumPrimalValue(result.getPrimalValue("Minimum " + cropsGroup.getCropsGroupName()).doubleValue());
                        }
                    }
                    if (AgricultureStandardUtils.stringToLong(cropsGroup.getMaximumAcres()) > 0 && result.getDualValue("Maximum " + cropsGroup.getCropsGroupName()) != null && result.getDualValue("Maximum " + cropsGroup.getCropsGroupName()).doubleValue() > zeroDouble) {
                        GroupLimitDualValue groupLimitDualValue = null;
                        for (GroupLimitDualValue dualValue : farmInfo.getGroupLimitDualValues()) {
                            if (dualValue.getCropsGroup().getCropsGroupName().equals(cropsGroup.getCropsGroupName())) {
                                groupLimitDualValue = dualValue;
                                break;
                            }
                        }
                        if (groupLimitDualValue == null) {
                            groupLimitDualValue = new GroupLimitDualValue();
                            groupLimitDualValue.setCropsGroup(cropsGroup);
                            groupLimitDualValue.setFarmInfo(farmInfo);
                            groupLimitDualValue.setMaximumDualValue(result.getDualValue("Maximum " + cropsGroup.getCropsGroupName()).doubleValue());
                            farmInfo.getGroupLimitDualValues().add(groupLimitDualValue);
                        } else {
                            groupLimitDualValue.setMaximumDualValue(result.getDualValue("Maximum " + cropsGroup.getCropsGroupName()).doubleValue());
                        }
                        if (result.getPrimalValue("Maximum " + cropsGroup.getCropsGroupName()) != null && result.getPrimalValue("Maximum " + cropsGroup.getCropsGroupName()).doubleValue() > zeroDouble) {
                            groupLimitDualValue.setMaximumPrimalValue(result.getPrimalValue("Maximum " + cropsGroup.getCropsGroupName()).doubleValue());
                        }
                    }
                } else {
                    for (GroupLimitDualValue groupLimitDualValue : farmInfo.getGroupLimitDualValues()) {
                        if (groupLimitDualValue.getCropsGroup().getCropsGroupName().equals(cropsGroup.getCropsGroupName())) {
                            groupLimitDualValue.setFarmInfo(null);
                            farmInfo.getGroupLimitDualValues().remove(groupLimitDualValue);
                            break;
                        }
                    }
                }
            }

            if (updateFlag) {
                updateFarmInfo(farmInfo);
            }
        }


        return farmOutputDetails;
    }

    public Double calculatePofitWithFirmSelected(double forwardPrice, double forwardAcres, double expectedYield, double expectedprice,
                                                 double minAcres, double varibleProductionCost, double forwardQuantity) {
        double profit = ((forwardPrice * forwardQuantity) + (expectedprice * (minAcres - forwardAcres) * expectedYield)) - (minAcres * varibleProductionCost);
        PlantingProfitLogger.info("Profit : " + profit);
        return profit;
    }

    @Override
    public Double calculateProfit(double expectedYield, double expectedprice, double minAcres, double varibleProductionCost) {
        double profit = (expectedprice * minAcres * expectedYield) - (minAcres * varibleProductionCost);
        PlantingProfitLogger.info("Profit : " + profit);
        return profit;
    }

    /**
     * @changed - Abhishek
     * @date - 07-04-2016
     * @desc - for overcoming NonUniqueObjectException: a different object with the same identifier value was already associated with the session:
     */
	/*@Override
	public int saveFarmOutputDetails(FarmOutputDetails farmOutputDetails) {
		PlantingProfitLogger.info("inside saveFarmOutputDetails .. ");
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		int id = 0;
		try {
			tx = session.beginTransaction();
			id = (int) session.save(farmOutputDetails);
			tx.commit();
			return id;
		} catch (Exception e) {
			id = 0;
			tx.rollback();
			PlantingProfitLogger.error(e);
			return id;
		} finally {
			session.close();
		}
	}*/
    @Override
    public void saveFarmOutputDetails(FarmOutputDetails farmOutputDetails) {

        PlantingProfitLogger.info("inside saveFarmOutputDetails .. ");
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        int id = 0;
        try {
            tx = session.beginTransaction();
            session.merge(farmOutputDetails);
            session.flush();
            tx.commit();

        } catch (Exception e) {
            tx.rollback();
            PlantingProfitLogger.error(e);

        } finally {

            session.close();
        }
    }

    public int updateFarmInfo(FarmInfo farmInfo) {
        PlantingProfitLogger.info("inside updateFarmInfo .. ");
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        int id = 0;
        try {
            tx = session.beginTransaction();
            session.saveOrUpdate(farmInfo);
            session.flush();
            tx.commit();
            return id;
        } catch (Exception e) {
            id = 0;
            tx.rollback();
            PlantingProfitLogger.error(e);
            return id;
        } finally {
            session.close();
        }
    }

    @Override
    public int saveFarmOutputDetailsForField(FarmOutputDetailsForField farmOutputDetailsForField) {
        PlantingProfitLogger.info("inside saveFarmOutputDetails .. ");
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        int id = 0;
        try {
            tx = session.beginTransaction();
            id = (int) session.save(farmOutputDetailsForField);
            session.flush();
            tx.commit();
            return id;
        } catch (Exception e) {
            id = 0;
            tx.rollback();
            PlantingProfitLogger.error(e);
            return id;
        } finally {
            session.close();
        }
    }

    @Override
    public int calculatePotentialProfitForAcre(List<FarmOutputDetailsView> farmOutputDetails) {
        PlantingProfitLogger.debug(" inside calculatePotentialProfitForAcre .. ");
        int potentialProfit = 0;
        for (FarmOutputDetailsView details : farmOutputDetails) {
            potentialProfit += details.getProfitAsDouble().intValue();
        }
        return potentialProfit;
    }

    @Override
    public Map<String, HashMap<String, String>> calculateUsedAndUnusedResourcesForAcre(List<CropResourceUsageView> resourceUsageViews, List<FarmOutputDetailsView> farmOutputDetailsViewList,
                                                                                       List<CropResourceUsageFieldVariancesView> resourceUsageVariances) {
        PlantingProfitLogger.debug(" inside calculateUsedAndUnusedResourcesForAcre .. ");
        Map<String, HashMap<String, String>> map = new HashMap<String, HashMap<String, String>>();
        HashMap<String, String> cropResourceUsed = new HashMap<String, String>();
        HashMap<String, String> cropResourceUnused = new HashMap<String, String>();
        for (CropResourceUsageView cropResourceUsageView : resourceUsageViews) {
            if (cropResourceUsageView.isActive()) {
                Double usedAmount = zeroDouble;
                for (FarmOutputDetailsView farmOutputDetailsView : farmOutputDetailsViewList) {
                    if (cropResourceUsageView.getCropResourceUse().equals("Land")) {
                        usedAmount += farmOutputDetailsView.getUsedAcresDouble();
                    } else if (cropResourceUsageView.getCropResourceUse().equals("Capital")) {
                        usedAmount += farmOutputDetailsView.getUsedAcresDouble() * Double.parseDouble(farmOutputDetailsView.getCropTypeView().getCalculatedVariableProductionCost().toString());
                    } else {
                        for (CropResourceUsageFieldVariancesView fieldVariances : resourceUsageVariances) {
                            if (farmOutputDetailsView.getCropTypeView().getCropName().equals(fieldVariances.getCropName()) && cropResourceUsageView.getCropResourceUse().equals(fieldVariances.getCropFieldResourceUse())) {
                                usedAmount += farmOutputDetailsView.getUsedAcresDouble() * Double.parseDouble(AgricultureStandardUtils.removeAllCommas(fieldVariances.getCropResourceAmount().equals("") ? "0" : fieldVariances.getCropResourceAmount()));
                            }
                        }
                    }
                }
                cropResourceUsed.put(cropResourceUsageView.getCropResourceUse(), AgricultureStandardUtils.commaSeparaterForInteger(usedAmount));
                /**
                 * @author Raja
                 * @since 21 Dec, 2015
                 * @summary Fixation of -0 bug
                 */
                //cropResourceUnused.put(cropResourceUsageView.getCropResourceUse(), AgricultureStandardUtils.commaSeparaterForInteger((Double.parseDouble(cropResourceUsageView.getCropResourceUseAmountWithoutComma()) - usedAmount)));
                cropResourceUnused.put(cropResourceUsageView.getCropResourceUse(), AgricultureStandardUtils.commaSeparaterForInteger((Double.parseDouble(cropResourceUsageView.getCropResourceUseAmountZeroIfBlank()) - usedAmount > 0 ? Double.parseDouble(cropResourceUsageView.getCropResourceUseAmountZeroIfBlank()) - usedAmount : 0)));
            }
        }
        map.put("cropResourceUsed", cropResourceUsed);
        map.put("cropResourceUnused", cropResourceUnused);
        return map;
    }

    /**
     * @changed - Abhishek
     * @date - 08-01-2016
     * @desc - Used org.json.simple.JSONObject and JSONArray instead of org.codehaus.jettison.json.JSONObject and JSONArray for removing "no serializer found for class and no properties discovered to create beanserializer" exception
     */
    @Override
    public JSONObject createJSONObjectForGraphForAcre(List<FarmOutputDetailsView> farmOutputDetailsViewList, String unusedLand) {
        PlantingProfitLogger.debug(" inside createJSONObjectForGraphForAcre .. ");

        /**
         * @changed - Abhishek
         * @date - 08-01-2016
         * @desc - Used org.json.simple.JSONObject and JSONArray instead of org.codehaus.jettison.json.JSONObject and JSONArray for removing "no serializer found for class and no properties discovered to create beanserializer" exception
         */
        JSONObject jsonObjectForGraphs = new JSONObject();
        JSONArray jsonArrayForProfit = new JSONArray();
        JSONArray jsonArrayForAcre = new JSONArray();
        for (FarmOutputDetailsView farmOutputDetailsView : farmOutputDetailsViewList) {
            JSONObject jsonObjectForProfit = new JSONObject();
            String cropName = farmOutputDetailsView.getCropTypeView().getCropName();
            if (farmOutputDetailsView.getForFirm()) {
                cropName += " (Contract)";
            } else if (farmOutputDetailsView.getForProposed()) {
                cropName += " (Proposed)";
            }

            /**
             * @changed - Abhishek
             * @date - 02-04-2016
             * @desc - according to slide#5 of 03232016
             */
            DecimalFormat decimalFormatter = new DecimalFormat("#.0");
            jsonObjectForProfit.put("Crop", cropName);
//			jsonObjectForProfit.put("Profit",farmOutputDetailsView.getProfitDouble());
            jsonObjectForProfit.put("Profit", decimalFormatter.format(farmOutputDetailsView.getProfitDouble()));
            jsonArrayForProfit.add(jsonObjectForProfit);

            JSONObject jsonObjectForAcre = new JSONObject();

            jsonObjectForAcre.put("Crop", cropName);
//			jsonObjectForAcre.put("Acre", farmOutputDetailsView.getUsedAcresDouble());
            jsonObjectForAcre.put("Acre", farmOutputDetailsView.getUsedAcresAsInteger());
            jsonArrayForAcre.add(jsonObjectForAcre);

        }
		/*
		 * Total Acreage would be by Used Acres
		 * 
		 * JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("Crop", "Unused Land");
			jsonObject.put("Acre", AgricultureStandardUtils.removeAllCommas(unusedLand));
		} catch (JSONException e1) {
			PlantingProfitLogger.error(e1);
		}
		jsonArrayForAcre.put(jsonObject);*/

        try {
            jsonObjectForGraphs.put("jsonArrayForProfit", jsonArrayForProfit);
            jsonObjectForGraphs.put("jsonArrayForAcre", jsonArrayForAcre);
        } catch (Exception e) {
            PlantingProfitLogger.error(e);
        }
        return jsonObjectForGraphs;
    }

    @Override
    public List<FarmOutputDetailsForField> calculateAcresForEachCropForField(List<CropBeanForOutput> cropBeanForOutputList, List<CropResourceUsageView> resourceUsageViews, List<FieldInfoView> fieldInfoViews, Set<CropsGroup> cropsGroups) {
        List<FarmOutputDetailsForField> detailsForFields = new ArrayList<>();
        List<String[]> array = linearProgramingSolveDao.generateCombination(cropBeanForOutputList, cropsGroups, fieldInfoViews);
        Map<String, Object> map = linearProgramingSolveDao.getBestResultFromLinearProgramingForField(cropBeanForOutputList, resourceUsageViews, cropsGroups, fieldInfoViews, array);
        String[] bestCase = (String[]) map.get("Best_Case");
        Result bestResult = (Result) map.get("Best_Result");
        if (bestCase != null) {
            for (String str : bestCase) {
                try {
                    FarmOutputDetailsForField farmOutputDetailsForField = new FarmOutputDetailsForField();
                    FarmOutputDetailsForField farmOutputDetailsForFieldForContract = null;
                    farmOutputDetailsForField.setUsedAcres(bestResult.get(str).doubleValue());
                    for (CropBeanForOutput cropBeanForOutput : cropBeanForOutputList) {
                        if (cropBeanForOutput.getCropType().getCropName().equals(str.split("###")[1])) {
                            if (cropBeanForOutput.getFirmAcres() > zeroDouble) {
                                farmOutputDetailsForFieldForContract = new FarmOutputDetailsForField();
                                farmOutputDetailsForFieldForContract.setUsedAcres(bestResult.get(str + " (Contract)").doubleValue());
                                farmOutputDetailsForFieldForContract.setCropType(cropBeanForOutput.getCropType());
                                farmOutputDetailsForFieldForContract.setForFirm(true);
                                farmOutputDetailsForFieldForContract.setForProposed(false);
                            } else if (cropBeanForOutput.getProposedAcres() > zeroDouble && bestResult.get(str + " (Proposed)").doubleValue() > zeroDouble) {
                                farmOutputDetailsForFieldForContract = new FarmOutputDetailsForField();
                                farmOutputDetailsForFieldForContract.setUsedAcres(bestResult.get(str + " (Proposed)").doubleValue());
                                farmOutputDetailsForFieldForContract.setCropType(cropBeanForOutput.getCropType());
                                farmOutputDetailsForFieldForContract.setForFirm(false);
                                farmOutputDetailsForFieldForContract.setForProposed(true);
                            }
                            farmOutputDetailsForField.setForFirm(false);
                            farmOutputDetailsForField.setForProposed(false);
                            farmOutputDetailsForField.setCropType(cropBeanForOutput.getCropType());
//							farmOutputDetailsForField.setProfit(bestResult.get(str).doubleValue()*cropBeanForOutput.getProfit());
                            break;
                        }
                    }
                    Set<CropFieldChocies> allChoices = farmOutputDetailsForField.getCropType().getChocies();
                    for (CropFieldChocies choices : allChoices) {
                        if (choices.getCropFieldChoice().equals("true") && choices.getName().getFieldName().equals(str.split("###")[0])) {
                            farmOutputDetailsForField.setFieldInfo(choices.getName());
                            if (farmOutputDetailsForFieldForContract != null) {
                                farmOutputDetailsForFieldForContract.setFieldInfo(choices.getName());
                            }
                        }
                    }
                    detailsForFields.add(farmOutputDetailsForField);
                    if (farmOutputDetailsForFieldForContract != null) {
                        detailsForFields.add(farmOutputDetailsForFieldForContract);
                    }
                } catch (Exception exception) {
                    PlantingProfitLogger.error(exception);
                }
            }
        }
//		else if (array.size() == 0 && errorFlag) {
//			detailsForFields = calculateAcresForEachCropForFieldByEachField(cropBeanForOutputList, resourceUsageViews, fieldInfoViews, cropsGroups);
//		}
        return detailsForFields;
    }

    @Override
    public Map<String, HashMap<String, String>> calculateUsedAndUnusedResourcesForField(
            List<CropResourceUsageView> resourceUsageViews,
            List<FarmOutputDetailsForFieldView> farmOutputDetailsForFieldViews,
            List<CropResourceUsageFieldVariancesView> resourceUsageVariances) {
        PlantingProfitLogger.debug("inside calculateUsedAndUnusedResourcesForField..");
        Map<String, HashMap<String, String>> map = new HashMap<String, HashMap<String, String>>();
        HashMap<String, String> cropResourceUsed = new HashMap<String, String>();
        HashMap<String, String> cropResourceUnused = new HashMap<String, String>();
        for (CropResourceUsageView cropResourceUsageView : resourceUsageViews) {
            if (cropResourceUsageView.isActive()) {
                Double usedAmount = zeroDouble;
                for (FarmOutputDetailsForFieldView farmOutputDetailsForFieldView : farmOutputDetailsForFieldViews) {
                    if (cropResourceUsageView.getCropResourceUse().equals("Land")) {
                        usedAmount += farmOutputDetailsForFieldView.getUsedAcresDouble();
                    } else if (cropResourceUsageView.getCropResourceUse().equals("Capital")) {
                        usedAmount += farmOutputDetailsForFieldView.getUsedAcresDouble() * Double.parseDouble(farmOutputDetailsForFieldView.getCropTypeView().getCalculatedVariableProductionCost().toString());
                    } else if (farmOutputDetailsForFieldView.getCropTypeView().getFieldIdForVariances().equals(farmOutputDetailsForFieldView.getFieldInfoView().getId())
                            && cropResourceUsageView.getResourseOverrideAmount() != null && (!cropResourceUsageView.getResourseOverrideAmount().equals(""))) {
                        //					PlantingProfitLogger.info("In Field Difference for resource---------------->>>>>>>>>>>>>>>>>>>"+cropResourceUsageView.getCropResourceUse());
                        usedAmount += farmOutputDetailsForFieldView.getUsedAcresDouble() * Double.parseDouble(AgricultureStandardUtils.removeAllCommas(cropResourceUsageView.getResourseOverrideAmount()));
                        //					PlantingProfitLogger.info(farmOutputDetailsForFieldView.getUsedAcresAsDouble()* Double.parseDouble(AgricultureStandardUtils.removeAllCommas(cropResourceUsageView.getResourseOverrideAmount()!=null && cropResourceUsageView.getResourseOverrideAmount().equals("")?"0":(fieldVariances.getCropResourceAmount().equals("")?"0":fieldVariances.getCropResourceAmount())))+
                        //							"--------"+AgricultureStandardUtils.removeAllCommas(cropResourceUsageView.getResourseOverrideAmount() != null && cropResourceUsageView.getResourseOverrideAmount().equals("")?"0":(fieldVariances.getCropResourceAmount().equals("")?"0":fieldVariances.getCropResourceAmount())));
                    } else {
                        for (CropResourceUsageFieldVariancesView fieldVariances : resourceUsageVariances) {
                            if (farmOutputDetailsForFieldView.getCropTypeView().getCropName().equals(fieldVariances.getCropName())
                                    && cropResourceUsageView.getCropResourceUse().equals(fieldVariances.getCropFieldResourceUse())) {
                                usedAmount += farmOutputDetailsForFieldView.getUsedAcresDouble() * Double.parseDouble(AgricultureStandardUtils.removeAllCommas(fieldVariances.getCropResourceAmount().equals("") ? "0" : fieldVariances.getCropResourceAmount()));
                            }
                        }
                    }
                }
                cropResourceUsed.put(cropResourceUsageView.getCropResourceUse(), AgricultureStandardUtils.commaSeparaterForInteger(usedAmount));
                Double cropUnusedAmount = Double.parseDouble(cropResourceUsageView.getCropResourceUseAmountZeroIfBlank()) - usedAmount;
                cropResourceUnused.put(cropResourceUsageView.getCropResourceUse(), AgricultureStandardUtils.commaSeparaterForInteger(cropUnusedAmount > 0 ? cropUnusedAmount : 0));
            }
        }
        map.put("cropResourceUsed", cropResourceUsed);
        map.put("cropResourceUnused", cropResourceUnused);
        return map;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Object> createJSONObjectAndMapObjectForGraphForField(
            List<CropTypeView> cropTypeView, List<FarmOutputDetailsForFieldView> farmOutputDetailsForFieldViews,
            String unusedLand) {
        PlantingProfitLogger.debug(" inside createJSONObjectForGraphForField .. ");
        /**
         * @changed - Abhishek
         * @date - 08-01-2016
         * @desc - Used org.json.simple.JSONObject and JSONArray instead of org.codehaus.jettison.json.JSONObject and JSONArray for removing "no serializer found for class and no properties discovered to create beanserializer" exception
         */
        JSONObject jsonObjectForGraphs = new JSONObject();
        JSONArray jsonArrayForProfit = new JSONArray();
        JSONArray jsonArrayForAcre = new JSONArray();
        Map<String, Object> mapCompleteObject = new HashMap<>();
        Map<String, Map<String, String>> mapForCropsForField = new HashMap<>();
        Map<String, String> hashMapForAcre = new TreeMap<>();
        Map<String, String> hashMapForProfit = new TreeMap<>();
        Map<String, String> hashMapForRatio = new TreeMap<>();
        Map<String, String> hashMapForProfitIndex = new TreeMap<>();
        Map<String, String> hashMapForRating = new TreeMap<>();
        long profitTotalOfAllCrops = 0;
        long acreTotalOfAllCrops = 0;
        for (CropTypeView typeView : cropTypeView) {
            if (typeView.getSelected()) {
                long profitTotal = 0, acreTotal = 0, firmProfitTotal = 0, firmAcreTotal = 0, proposedProfitTotal = 0, proposedAcreTotal = 0;

                for (FarmOutputDetailsForFieldView farmOutputDetailsForFieldView : farmOutputDetailsForFieldViews) {
                    if (typeView.getCropName().equalsIgnoreCase(farmOutputDetailsForFieldView.getCropTypeView().getCropName())
                            && farmOutputDetailsForFieldView.isForFirm()) {

                        firmProfitTotal += farmOutputDetailsForFieldView.getProfitAsDouble();
                        firmAcreTotal += farmOutputDetailsForFieldView.getUsedAcresAsDouble();

                    } else if (typeView.getCropName().equalsIgnoreCase(farmOutputDetailsForFieldView.getCropTypeView().getCropName())
                            && farmOutputDetailsForFieldView.isForProposed()) {

                        proposedProfitTotal += farmOutputDetailsForFieldView.getProfitAsDouble();
                        proposedAcreTotal += farmOutputDetailsForFieldView.getUsedAcresAsDouble();

                    } else if (typeView.getCropName().equalsIgnoreCase(farmOutputDetailsForFieldView.getCropTypeView().getCropName())) {
                        profitTotal += farmOutputDetailsForFieldView.getProfitAsDouble();
                        acreTotal += farmOutputDetailsForFieldView.getUsedAcresAsDouble();
                    }
                }

                profitTotalOfAllCrops += profitTotal + firmProfitTotal + proposedProfitTotal;
                acreTotalOfAllCrops += acreTotal + firmAcreTotal + proposedAcreTotal;
                boolean cropFlag = true;
                for (FarmOutputDetailsForFieldView farmOutputDetailsForFieldView : farmOutputDetailsForFieldViews) {

                    if (typeView.getCropName().equalsIgnoreCase(farmOutputDetailsForFieldView.getCropTypeView().getCropName())
                            && farmOutputDetailsForFieldView.isForFirm()) {
                        cropFlag = false;
                        try {
                            hashMapForAcre.put(typeView.getCropName() + " (Firm)", "" + firmAcreTotal);
                            hashMapForProfit.put(typeView.getCropName() + " (Firm)", "" + firmProfitTotal);
                            hashMapForRatio.put(typeView.getCropName() + " (Firm)", ("" + firmProfitTotal / (firmAcreTotal >= 1 ? firmAcreTotal : 1)));
//                          hashMapForRatio.put(typeView.getCropName(), (typeView.getCalculatedProfitFloat()));
                        } catch (Exception e) {
                            PlantingProfitLogger.error(e);
                        }


                    } else if (typeView.getCropName().equalsIgnoreCase(farmOutputDetailsForFieldView.getCropTypeView().getCropName())
                            && farmOutputDetailsForFieldView.isForProposed()) {
                        cropFlag = false;
                        try {
                            hashMapForAcre.put(typeView.getCropName() + " (Proposed)", "" + proposedAcreTotal);
                            hashMapForProfit.put(typeView.getCropName() + " (Proposed)", "" + proposedProfitTotal);
                            hashMapForRatio.put(typeView.getCropName() + " (Proposed)", ("" + proposedProfitTotal / (proposedAcreTotal >= 1 ? proposedAcreTotal : 1)));
//                          hashMapForRatio.put(typeView.getCropName(), (typeView.getCalculatedProfitFloat()));
                        } catch (Exception e) {
                            PlantingProfitLogger.error(e);
                        }


                    } else if (typeView.getCropName().equalsIgnoreCase(farmOutputDetailsForFieldView.getCropTypeView().getCropName())) {
                        cropFlag = false;
                        try {
                            hashMapForAcre.put(typeView.getCropName(), "" + acreTotal);
                            hashMapForProfit.put(typeView.getCropName(), "" + profitTotal);
                            hashMapForRatio.put(typeView.getCropName(), ("" + profitTotal / (acreTotal >= 1 ? acreTotal : 1)));
//                          hashMapForRatio.put(typeView.getCropName(), (typeView.getCalculatedProfitFloat()));
                        } catch (Exception e) {
                            PlantingProfitLogger.error(e);
                        }

                    }

                }
                if (cropFlag) {
                    hashMapForAcre.put(typeView.getCropName(), "0");
                    hashMapForProfit.put(typeView.getCropName(), "0");
                    hashMapForRatio.put(typeView.getCropName(), "0");
                }

            }
        }
        for (Entry<String, String> entry : hashMapForAcre.entrySet()) {
            long profit = 0;
            long acre = 0;
            acre = Integer.parseInt(hashMapForAcre.get(entry.getKey()));
            profit = Integer.parseInt(hashMapForProfit.get(entry.getKey()));
            String profitString = "";
            String acreString = "";
            /**
             * @changed - Abhishek
             * @Date - 26-11-2015
             * */
//			Double profitIndexString = null;
            Double profitIndexString = 0.0;
            try {
                /**
                 * @changed - Abhishek
                 * @date - 03-03-2016
                 * @desc - replaced integer conversion to double upto single decimal point
                 */
				/*profitString = AgricultureStandardUtils.commaSeparaterForField(hashMapForProfit.get(entry.getKey()))+" ("+(AgricultureStandardUtils.doubleToInteger(new Double(new Double(profit*100)/profitTotalOfAllCrops)))+"%)";
				acreString = AgricultureStandardUtils.commaSeparaterForField(hashMapForAcre.get(entry.getKey()))+" ("+(AgricultureStandardUtils.doubleToInteger(new Double(new Double(acre*100))/acreTotalOfAllCrops))+"%)";*/
                profitString = AgricultureStandardUtils.commaSeparaterForField(hashMapForProfit.get(entry.getKey())) + " (" + (AgricultureStandardUtils.doubleUptoSingleDecimalPoint(new Double(new Double(profit * 100) / profitTotalOfAllCrops))) + "%)";
                acreString = AgricultureStandardUtils.commaSeparaterForField(hashMapForAcre.get(entry.getKey())) + " (" + (AgricultureStandardUtils.doubleUptoSingleDecimalPoint(new Double(new Double(acre * 100)) / acreTotalOfAllCrops)) + "%)";
                profitIndexString = ((Double.parseDouble(hashMapForProfit.get(entry.getKey())) * 100) / profitTotalOfAllCrops) / ((Long.parseLong(hashMapForAcre.get(entry.getKey())) * 100) / acreTotalOfAllCrops);
            } catch (Exception exception) {
//				PlantingProfitLogger.error(exception);
            }

            JSONObject jsonObjectForProfit = new JSONObject();
            JSONObject jsonObjectForAcre = new JSONObject();

            jsonObjectForProfit.put("Crop", entry.getKey());
            jsonObjectForProfit.put("Profit", profit);

            jsonObjectForAcre.put("Crop", entry.getKey());
            jsonObjectForAcre.put("Acre", acre);

            jsonArrayForProfit.add(jsonObjectForProfit);
            jsonArrayForAcre.add(jsonObjectForAcre);
            hashMapForAcre.put(entry.getKey(), acreString);
            hashMapForProfit.put(entry.getKey(), profitString);
            hashMapForProfitIndex.put(entry.getKey(), "" + AgricultureStandardUtils.doubleWithOneDecimal(profitIndexString).toString() + "%");
            hashMapForRating.put(entry.getKey(), (profitIndexString >= 1) ? "Green" : (profitIndexString < 1 && profitIndexString >= 0.6) ? "Yellow" : (profitIndexString < 0.6 && profitIndexString > 0) ? "Red" : "Grey");
        }
		/*
		 * Total Acreage would be by Used Acres
		 * 
		 * JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("Crop", "Unused Land");
			jsonObject.put("Acre", AgricultureStandardUtils.removeAllCommas(unusedLand));
		} catch (JSONException e1) {
			PlantingProfitLogger.error(e1);
		}
		jsonArrayForAcre.put(jsonObject);*/

        try {
            jsonObjectForGraphs.put("jsonArrayForProfit", jsonArrayForProfit);
            jsonObjectForGraphs.put("jsonArrayForAcre", jsonArrayForAcre);
        } catch (Exception e) {
            PlantingProfitLogger.error(e);
        }
        mapForCropsForField.put("hashMapForAcre", AgricultureStandardUtils.sortUsingComparatorByValue(hashMapForAcre));
        mapForCropsForField.put("hashMapForProfit", hashMapForProfit);
        mapForCropsForField.put("hashMapForRatio", hashMapForRatio);
        mapForCropsForField.put("hashMapForProfitIndex", hashMapForProfitIndex);
        mapForCropsForField.put("hashMapForRating", hashMapForRating);
        mapCompleteObject.put("jsonObjectForGraphs", jsonObjectForGraphs);
        mapCompleteObject.put("mapForCropsForField", mapForCropsForField);
        return mapCompleteObject;
    }

    @Override
    public int calculatePotentialProfitForField(List<FarmOutputDetailsForFieldView> farmOutputDetailsForFieldViews) {
        PlantingProfitLogger.debug(" inside calculatePotentialProfitForField .. ");
        int potentialProfit = 0;
        for (FarmOutputDetailsForFieldView details : farmOutputDetailsForFieldViews) {
            potentialProfit += details.getProfitAsDouble().intValue();
        }
        return potentialProfit;
    }
//	@SuppressWarnings("unused")
//	private List<FarmOutputDetailsForField> uniqueFarmOutputDetailsForField(List<FarmOutputDetailsForField> farmOutputDetailsForFields, List<FieldInfoView> fieldInfoViews){
//		List<FarmOutputDetailsForField> farmOutputDetailsForFields2 = new ArrayList<FarmOutputDetailsForField>();
//			for(FieldInfoView fieldInfoView : fieldInfoViews){
//				FarmOutputDetailsForField outputDetailsForField = null;
//				int i=0;
//				for(FarmOutputDetailsForField detailsForField : farmOutputDetailsForFields){
//					if(fieldInfoView.getFieldName().equals(detailsForField.getFieldInfo().getFieldName())){
//						if(i==0){
//							outputDetailsForField = detailsForField;
//						}else if(detailsForField.getUsedAcres()>outputDetailsForField.getUsedAcres()){
//							outputDetailsForField = detailsForField;
//						}
//						i++;
//					}
//				}
//				if(outputDetailsForField != null){
//					farmOutputDetailsForFields2.add(outputDetailsForField);
//				}
//			}
//		return farmOutputDetailsForFields2;
//	}
//	
//	private String[][] generateCombinations(String[]... arrays) throws Throwable {
//	    if (arrays.length == 0) {
//	        return new String[][]{{}};
//	    }
//	    int num = 1;
//	    for (int i = 0; i < arrays.length; i++) {
//	        num *= arrays[i].length;
//	    }
////	    PlantingProfitLogger.info(num+"--------------"+arrays.length);
//	    
//	    String[][] result = new String[num][arrays.length];
//	    // array containing the indices of the Strings
//	    int[] combination = new Integer[arrays.length];
//
//	    for (int i = 0; i < num; i++) {
//	        String[] comb = result[i];
//	        // fill array
//	        for (int j = 0; j < arrays.length; j++) {
//	            comb[j] = arrays[j][combination[j]];
////	            PlantingProfitLogger.info(j+"--------"+Arrays.toString(comb));
//	        }
//
//	        // generate next combination
//	        for (int j = 0; j < arrays.length; j++) {
//	            int n = ++combination[j];
//	            if (n >= arrays[j].length) {
//	                // "digit" exceeded valid range -> back to 0 and continue incrementing
//	                combination[j] = 0;
//	            } else {
//	                // "digit" still in valid range -> stop
//	                break;
//	            }
//	        }
//	    }
//	    return result;
//	}
//	
//	public List<FarmOutputDetailsForField> calculateAcresForEachCropForFieldByEachField(List<CropBeanForOutput> cropBeanForOutputList, List<CropResourceUsageView> resourceUsageViews, List<FieldInfoView> fieldInfoViews, Set<CropsGroup> cropsGroups) {
//		List<FarmOutputDetailsForField> farmOutputDetailsForFieldListByAsc = new ArrayList<FarmOutputDetailsForField>();
//		List<FarmOutputDetailsForField> farmOutputDetailsForFieldListByDesc = new ArrayList<FarmOutputDetailsForField>();
//		Long totalProfitByASCOrder = 0l;
//		Long totalProfitByDESCOrder = 0l;
//		boolean perfactStarategyForAsc = true;
//		boolean perfactStarategyForDESC = true;
//		Double zero = new Double(0);
//		for (int i = 0; i < 2; i++) {
//			
//			Map<CropResourceUsageView, Double> resourceRemaining = new HashMap<CropResourceUsageView, Double>();
//			Map<CropBeanForOutput, Double> minimumAcreConsumed = new HashMap<CropBeanForOutput, Double>();
//			Map<CropBeanForOutput, Double> minimumAcreRequired = new HashMap<CropBeanForOutput, Double>();
//			Map<CropBeanForOutput, boolean> minimumOnlyInSingleField = new HashMap<CropBeanForOutput, boolean>();
//			Map<CropBeanForOutput, Double> maximumAcreConsumed = new HashMap<CropBeanForOutput, Double>();
//			Map<CropBeanForOutput, Double> maximumAcreRequired = new HashMap<CropBeanForOutput, Double>();
//			Map<CropBeanForOutput, Double> minimumGroupsAcreConsumed = new HashMap<CropBeanForOutput, Double>();
//			Map<CropBeanForOutput, Double> minimumGroupsAcreRequired = new HashMap<CropBeanForOutput, Double>();
//			
//			for(CropBeanForOutput cropBeanForOutput: cropBeanForOutputList){
//				PlantingProfitLogger.info(cropBeanForOutput.getCropType().getCropName()+"-----------"+cropBeanForOutput.getMinAcre().longValue()+"--------"+cropBeanForOutput.getMaxAcre().longValue());
//				minimumAcreConsumed.put(cropBeanForOutput, zero);
//				minimumAcreRequired.put(cropBeanForOutput, cropBeanForOutput.getMinAcre());
//				maximumAcreConsumed.put(cropBeanForOutput, zero);
//				maximumAcreRequired.put(cropBeanForOutput, cropBeanForOutput.getMaxAcre());
//				minimumGroupsAcreConsumed.put(cropBeanForOutput, zero);
//				minimumGroupsAcreRequired.put(cropBeanForOutput, zero);
//				int numberOfChoices = 0;
//				for(CropFieldChocies chocies : cropBeanForOutput.getCropType().getChocies()){
//					if(chocies.getCropFieldChoice().equals("true")){
//						numberOfChoices++;
//					}
//				}
//				minimumOnlyInSingleField.put(cropBeanForOutput, numberOfChoices==1?true:false);
//			}
//			
//			for(CropResourceUsageView cropResourceUsageView : resourceUsageViews){
//				resourceRemaining.put(cropResourceUsageView, Double.parseDouble(cropResourceUsageView.getCropResourceUseAmountWithoutComma()));
//			}
//			
//			if (i == 0) {
//				Collections.sort(fieldInfoViews, new FieldInfoViewComperatorDESCOrder());
//			} else if (i == 1) {
//				Collections.sort(fieldInfoViews, new FieldInfoViewComperatorASCOrder());
//			}
//		for(FieldInfoView fieldInfoView : fieldInfoViews){
//			if(fieldInfoView.getFallow().equals("false")){
//			CropFieldChocies cropFieldChoice = null;
//			Long maxProfit = 0l;
//			Map<CropFieldChocies, Result> resultMap = new HashMap<CropFieldChocies, Result>();
//			List<CropFieldChocies> cropFieldChociesList = new ArrayList<CropFieldChocies>(fieldInfoView.getChocies());
//			Collections.sort(cropFieldChociesList, new CropFieldChoicesComperator());
//			outer : for(CropFieldChocies cropFieldChocies : cropFieldChociesList){
//				if(cropFieldChocies.getCropFieldChoice().equals("true")){
//					CropBeanForOutput beanForOutput = null;
//					for(CropBeanForOutput cropBeanForOutput : cropBeanForOutputList){
//						if(cropBeanForOutput.getCropType().getCropName().equals(cropFieldChocies.getCropType().getCropName())){
//							beanForOutput = cropBeanForOutput;
//							break;
//						}
//					}
//					if(beanForOutput.getProfit()<1 && minimumAcreRequired.get(beanForOutput)<1){
//						continue outer;
//					}
//					
////					if(maximumAcreRequired.get(beanForOutput)<=fieldInfoView.getFieldSizeFloat()){
////						break;
////					}
//					SolverFactory factory = new SolverFactoryLpSolve(); // use lp_solve
//					factory.setParameter(Solver.VERBOSE, 0);
//					factory.setParameter(Solver.TIMEOUT, 1000);
//					Problem problem = new Problem();
//					Linear linear = null;
//					
//					/* Profit by crop */
//					linear = new Linear();
//					linear.add(beanForOutput.getProfit(), beanForOutput.getCropType().getCropName()+"-"+fieldInfoView.getFieldName());
//					problem.setObjective(linear, OptType.MAX);
//					
//					/*Resources Calculation*/
//					for(CropResourceUsageView cropResourceUsageView : resourceUsageViews){
//						if(cropResourceUsageView.getCropResourceUse().equals("Land")){
//							linear = new Linear();
//							linear.add(1, beanForOutput.getCropType().getCropName()+"-"+fieldInfoView.getFieldName());
//							problem.add(linear, "=", Integer.parseInt(fieldInfoView.getFieldSize()));
//						}
//						else if(cropResourceUsageView.getCropResourceUse().equals("Capital")){
//							linear = new Linear();
//							linear.add(beanForOutput.getVariableProductionCost(), beanForOutput.getCropType().getCropName()+"-"+fieldInfoView.getFieldName());
//							problem.add(linear, "<=", resourceRemaining.get(cropResourceUsageView));
//						}
//						else{
//							for(CropResourceUsageFieldVariances cropResourceUsageFieldVariances : beanForOutput.getCropType().getCropResourceUsageFieldVariances()){
//							if(cropResourceUsageFieldVariances.getCropFieldResourceUse().equals(cropResourceUsageView.getCropResourceUse())){
//								linear = new Linear();
//								linear.add(Double.parseDouble(AgricultureStandardUtils.removeAllCommas(cropResourceUsageFieldVariances.getCropResourceAmount())), beanForOutput.getCropType().getCropName()+"-"+fieldInfoView.getFieldName());
//								problem.add(linear, "<=", resourceRemaining.get(cropResourceUsageView));
//								}
//							}
//						}
//					}
//					linear = new Linear();
//					linear.add(1, beanForOutput.getCropType().getCropName()+"-"+fieldInfoView.getFieldName());
//					problem.add(linear, ">=", minimumAcreRequired.get(beanForOutput));
//					
//					if(maximumAcreRequired.get(beanForOutput) > 0){
//					linear = new Linear();
//					linear.add(1, beanForOutput.getCropType().getCropName()+"-"+fieldInfoView.getFieldName());
//					problem.add(linear, "<=", maximumAcreRequired.get(beanForOutput));
//					}
//					
//					problem.setVarType(beanForOutput.getCropType().getCropName()+"-"+fieldInfoView.getFieldName(), Long.class);
//					Solver solver = factory.get();
//					Result result = solver.solve(problem);
//					
//					if(result != null && result.getObjective().longValue() > 0){
//						resultMap.put(cropFieldChocies, result);
//					}
//				}
//			}
//			CropBeanForOutput cropBeanForOutputPrevious = null;
//			for(Entry<CropFieldChocies, Result> entry : resultMap.entrySet()){
//				CropBeanForOutput cropBeanForOutput = null;
//				for(CropBeanForOutput beanForOutput : cropBeanForOutputList){
//					if(beanForOutput.getCropType().getCropName().equals(entry.getKey().getCropType().getCropName())){
//						cropBeanForOutput = beanForOutput;
//					}
//				}
//				PlantingProfitLogger.info(entry.getValue());
//				if(cropFieldChoice == null){
//					cropFieldChoice = entry.getKey();
//					maxProfit = entry.getValue().getObjective().longValue();
//					cropBeanForOutputPrevious = cropBeanForOutput;
//				}else if(entry.getValue().getObjective().longValue() > maxProfit && minimumAcreRequired.get(cropBeanForOutputPrevious) == 0){
//					PlantingProfitLogger.info("In else if-----"+entry.getValue().getObjective().longValue() +"-----"+ maxProfit +"-----"+ minimumAcreRequired.get(cropBeanForOutputPrevious));
//					cropFieldChoice = entry.getKey();
//					maxProfit = entry.getValue().getObjective().longValue();
//					cropBeanForOutputPrevious = cropBeanForOutput;
//				}else if(entry.getValue().getObjective().longValue() > maxProfit && minimumAcreRequired.get(cropBeanForOutputPrevious) > 0 && 
//						minimumAcreRequired.get(cropBeanForOutput) > 0 && !minimumOnlyInSingleField.get(cropBeanForOutputPrevious)){
//					PlantingProfitLogger.info("In else if else if-----"+entry.getValue().getObjective().longValue() +"-----"+ maxProfit +"-----"+ minimumAcreRequired.get(cropBeanForOutputPrevious)+"-------"+minimumAcreRequired.get(cropBeanForOutput));
//					cropFieldChoice = entry.getKey();
//					maxProfit = entry.getValue().getObjective().longValue();
//					cropBeanForOutputPrevious = cropBeanForOutput;
//				}else if(entry.getValue().getObjective().longValue() == maxProfit && minimumAcreRequired.get(cropBeanForOutputPrevious) == 0 && 
//						minimumAcreRequired.get(cropBeanForOutput) > 0){
//					PlantingProfitLogger.info("In else if else if else if-----"+entry.getValue().getObjective().longValue() +"-----"+ maxProfit +"-----"+ minimumAcreRequired.get(cropBeanForOutputPrevious)+"-------"+minimumAcreRequired.get(cropBeanForOutput));
//					cropFieldChoice = entry.getKey();
//					maxProfit = entry.getValue().getObjective().longValue();
//					cropBeanForOutputPrevious = cropBeanForOutput;
//				}else if(entry.getValue().getObjective().longValue() < maxProfit && minimumAcreRequired.get(cropBeanForOutputPrevious) == 0 && 
//						minimumAcreRequired.get(cropBeanForOutput) > 0 && minimumOnlyInSingleField.get(cropBeanForOutput)){
//					PlantingProfitLogger.info("In else if else if else if-----"+entry.getValue().getObjective().longValue() +"-----"+ maxProfit +"-----"+ minimumAcreRequired.get(cropBeanForOutputPrevious)+"-------"+minimumAcreRequired.get(cropBeanForOutput));
//					cropFieldChoice = entry.getKey();
//					maxProfit = entry.getValue().getObjective().longValue();
//					cropBeanForOutputPrevious = cropBeanForOutput;
//				}else if(entry.getValue().getObjective().longValue() <= maxProfit && minimumAcreRequired.get(cropBeanForOutputPrevious) > 0 && 
//						minimumAcreRequired.get(cropBeanForOutput) > 0 && minimumOnlyInSingleField.get(entry.getKey())){
//					PlantingProfitLogger.info("In else if else if else if else if-----"+entry.getValue().getObjective().longValue() +"-----"+ maxProfit +"-----"+ minimumAcreRequired.get(cropBeanForOutputPrevious)+"-------"+minimumAcreRequired.get(cropBeanForOutput)+"------"+minimumOnlyInSingleField.get(entry.getKey()));
//					cropFieldChoice = entry.getKey();
//					maxProfit = entry.getValue().getObjective().longValue();
//					cropBeanForOutputPrevious = cropBeanForOutput;
//				}
//			}
//			if(cropFieldChoice != null){
//				Result result = resultMap.get(cropFieldChoice);
//				if (i == 0) {
//					totalProfitByDESCOrder += result.getObjective().longValue();
//				} else if (i == 1) {
//					totalProfitByASCOrder += result.getObjective().longValue();
//				}
//				PlantingProfitLogger.info("To be Used----------->>>>>>>>>>>"+result);
//				Double usedAcres = result.getPrimalValue(cropFieldChoice.getCropType().getCropName()+"-"+fieldInfoView.getFieldName()).doubleValue();
//				for(CropResourceUsageView cropResourceUsageView : resourceUsageViews){
//					Double usedResource = new Double(0);
//					if(cropResourceUsageView.getCropResourceUse().equals("Capital")){
//							usedResource = usedAcres * cropFieldChoice.getCropType().getCostsCropProductionCosts().getCalculatedVariableProductionCost().doubleValue();
//							resourceRemaining.put(cropResourceUsageView, (resourceRemaining.get(cropResourceUsageView)-usedResource));
//						}
//						else{
//							for(CropResourceUsageFieldVariances cropResourceUsageFieldVariances : cropFieldChoice.getCropType().getCropResourceUsageFieldVariances()){
//							if(cropResourceUsageFieldVariances.getCropFieldResourceUse().equals(cropResourceUsageView.getCropResourceUse())){
//								usedResource = usedAcres * Double.parseDouble(AgricultureStandardUtils.removeAllCommas(cropResourceUsageFieldVariances.getCropResourceAmount()));
//								resourceRemaining.put(cropResourceUsageView, (resourceRemaining.get(cropResourceUsageView)-usedResource));
//							}
//						}
//					}
//				}
//				for(CropBeanForOutput beanForOutput : cropBeanForOutputList){
//					if(beanForOutput.getCropType().getCropName().equals(cropFieldChoice.getCropType().getCropName())){
//						minimumAcreConsumed.put(beanForOutput, minimumAcreConsumed.get(beanForOutput)+usedAcres);
//						minimumAcreRequired.put(beanForOutput, ((minimumAcreRequired.get(beanForOutput) > usedAcres)?(minimumAcreRequired.get(beanForOutput)-usedAcres):0l));
//						maximumAcreConsumed.put(beanForOutput, maximumAcreConsumed.get(beanForOutput)+usedAcres);
//						maximumAcreRequired.put(beanForOutput, ((maximumAcreRequired.get(beanForOutput) > usedAcres)?(maximumAcreRequired.get(beanForOutput)-usedAcres):0l));
//						break;
//					}
//				}
//				FarmOutputDetailsForField farmOutputDetailsForField = new FarmOutputDetailsForField();
//				farmOutputDetailsForField.setCropType(cropFieldChoice.getCropType());
//				farmOutputDetailsForField.setFieldInfo(cropFieldChoice.getName());
//				farmOutputDetailsForField.setUsedAcres(usedAcres.doubleValue());
//				if (i == 0) {
//					farmOutputDetailsForFieldListByDesc.add(farmOutputDetailsForField);
//				} else if (i == 1) {
//					farmOutputDetailsForFieldListByAsc.add(farmOutputDetailsForField);
//				}
//			}
//		}
//		}
//		for(CropBeanForOutput cropBeanForOutput: cropBeanForOutputList){
//			PlantingProfitLogger.info(cropBeanForOutput.getCropType().getCropName()+"-----------"+minimumAcreRequired.get(cropBeanForOutput));
//			if(minimumAcreRequired.get(cropBeanForOutput) > 0){
//				if (i == 0) {
//					perfactStarategyForDESC = false;
//				} else if (i == 1) {
//					perfactStarategyForAsc = false;
//				}
//				break;
//			}
//		}
//		}
//		PlantingProfitLogger.info("Profit by ASC = "+totalProfitByASCOrder+" perfactStarategyForDESC = "+perfactStarategyForDESC);
//		PlantingProfitLogger.info("Profit by DESC = "+totalProfitByDESCOrder+" perfactStarategyForAsc = "+perfactStarategyForAsc);
//
//		if (totalProfitByDESCOrder >= totalProfitByASCOrder && perfactStarategyForDESC) {
//				return farmOutputDetailsForFieldListByDesc;
//			}else if(totalProfitByDESCOrder <= totalProfitByASCOrder && perfactStarategyForAsc){
//				return farmOutputDetailsForFieldListByAsc;
//			}else if(perfactStarategyForDESC){
//				return farmOutputDetailsForFieldListByDesc;
//			}else if(perfactStarategyForAsc){
//				return farmOutputDetailsForFieldListByAsc;
//			}else{
//				return new ArrayList<FarmOutputDetailsForField>();
//			}
//	}
//	private void removeAllCombinationThatDoNotSatisfy(Set<List<String>> array, List<CropBeanForOutput> cropBeanForOutputs){
//		List<CropBeanForOutput> beanForOutputsWithMinAcreOnly = new ArrayList<CropBeanForOutput>();
//		for(CropBeanForOutput cropBeanForOutput : cropBeanForOutputs){
//			if(cropBeanForOutput.getMinAcre() > new Double(0)){
//				beanForOutputsWithMinAcreOnly.add(cropBeanForOutput);
//			}
//		}
////		PlantingProfitLogger.info("Initial size of Array = "+array.size()+" Process start time = "+new Date());
//		boolean flag = false;
//		Set<List<String>> set = null;
//		for (CropBeanForOutput cropBeanForOutput : beanForOutputsWithMinAcreOnly) {
//			set = new HashSet<List<String>>();
//			for (List<String> combination : array) {
//				flag = false;
//				for (String string : combination) {
//					if (string.split("###")[1].equals(cropBeanForOutput.getCropType().getCropName())) {
//						flag = true;
//					}
//				}
//				if (!flag) {
//					set.add(combination);
//				}
//			}
//			if (set.size() > 0) {
////				PlantingProfitLogger.info("Size of Set to be removed = " + set.size());
//				array.removeAll(set);
//			}
//		}
////		PlantingProfitLogger.info("New size of Array = "+array.size()+" Process end time = "+new Date());
//	}
}
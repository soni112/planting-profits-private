package com.decipher.agriculture.service.farmDetails.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.decipher.agriculture.bean.OutputBeanForStrategy;
import com.decipher.agriculture.service.farmDetails.FarmOutputCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.dao.farmOutput.FarmOutputCalculationDao;
import com.decipher.agriculture.data.farm.FarmInfo;
import com.decipher.agriculture.data.farmOutput.FarmOutputDetails;
import com.decipher.agriculture.data.farmOutput.FarmOutputDetailsForField;
import com.decipher.util.AgricultureStandardUtils;
import com.decipher.view.form.farmDetails.FarmOutputDetailsForFieldView;
import com.decipher.view.form.farmDetails.FarmOutputDetailsView;

@Repository
@Transactional
public class FarmOutputCalculationServiceImpl implements FarmOutputCalculationService {

    @Autowired
    private FarmOutputCalculationDao farmOutputCalculationDao;

    @Override
    public boolean checkIfFarmHasValidStrategy(int farmId) {
        List<FarmOutputDetails> farmOutputDetails = farmOutputCalculationDao.getAllFarmOutputDetailsByFarm(farmId);
        boolean validStrategy = false;
        if (farmOutputDetails != null) {
            for (FarmOutputDetails farmOutputDetail : farmOutputDetails) {
                if (farmOutputDetail.getUsedAcres() != 0) {
                    validStrategy = true;
                }
            }
        }
        return validStrategy;
    }

    /**
     * @changed - Abhishek
     * @date - 21-12-2015
     */
    @Override
    public void calculateFarmOutputStatistics(FarmInfo farmInfo) {
        //farmOutputCalculationDao.calculateFarmOutputStatistics(farmInfo);

        OutputBeanForStrategy outputBeanForStrategy = new OutputBeanForStrategy();
        outputBeanForStrategy.setFarmInfo(farmInfo);
        outputBeanForStrategy.setSaveFlag(true);
        calculateFarmOutputStatistics(outputBeanForStrategy);
    }

    @Override
    public List<FarmOutputDetailsView> getAllFarmOutputDetailsByFarm(int farmId) {
        /**
         * @changed - Abhishek
         * @date - 21-12-2015
         */
        /*Double totalUsedAcre = new Double(0);
        Double totalProfit = new Double(0);
		List<FarmOutputDetailsView> farmOutputDetailView = new ArrayList<FarmOutputDetailsView>();
		List<FarmOutputDetails> farmOutputDetails = farmOutputCalculationDao.getAllFarmOutputDetailsByFarm(farmId);

		if (farmOutputDetails != null) {
			for (FarmOutputDetails farmOutputDetail : farmOutputDetails) {
				farmOutputDetailView.add(new FarmOutputDetailsView(farmOutputDetail));
				totalUsedAcre += farmOutputDetail.getUsedAcres();
				totalProfit += farmOutputDetail.getProfit();
			}
			
			if(totalUsedAcre > 0 && totalProfit > 0){
				for (FarmOutputDetailsView farmOutputDetailsView : farmOutputDetailView) {
					farmOutputDetailsView.setUsedAcresPercentage(AgricultureStandardUtils.doubleToInteger((farmOutputDetailsView.getUsedAcresDouble() * 100) / totalUsedAcre));
					farmOutputDetailsView.setUsedCapitalPercentage(AgricultureStandardUtils.doubleToInteger((farmOutputDetailsView.getProfitDouble() * 100) / totalProfit));
					farmOutputDetailsView.setProfitIndex(AgricultureStandardUtils.doubleWithOneDecimal(((farmOutputDetailsView.getProfitDouble() * 100) / totalProfit)/((farmOutputDetailsView.getUsedAcresAsDouble()*100)/totalUsedAcre)));
					farmOutputDetailsView.setRatio(AgricultureStandardUtils.doubleWithOneDecimal(farmOutputDetailsView.getProfitDouble()/farmOutputDetailsView.getUsedAcresAsDouble()));
					farmOutputDetailsView.setRating((farmOutputDetailsView.getProfitIndex() >= 1)?"Green":(farmOutputDetailsView.getProfitIndex() < 1 && farmOutputDetailsView.getProfitIndex() >= 0.6)?"Yellow":"Red");
				}
			}
		}*/

        OutputBeanForStrategy outputBeanForStrategy = new OutputBeanForStrategy();

        outputBeanForStrategy.setFarmID(farmId);
        outputBeanForStrategy.setBaselineFlag(true);

        return getAllFarmOutputDetailsByFarm(outputBeanForStrategy);
    }

    @Override
    public List<FarmOutputDetailsForFieldView> getAllFarmOutputDetailsForFieldByFarm(int farmId) {
		/*Double totalUsedAcre = new Double(0);
		Double totalProfit = new Double(0);
		List<FarmOutputDetailsForFieldView> farmOutputDetailsForFieldViews = new ArrayList<FarmOutputDetailsForFieldView>();
		List<FarmOutputDetailsForField> farmOutputDetailsForFields = farmOutputCalculationDao.getAllFarmOutputDetailsForFieldByFarm(farmId);

		if (farmOutputDetailsForFields != null) {
			for (FarmOutputDetailsForField farmOutputDetailsForField : farmOutputDetailsForFields) {
				farmOutputDetailsForFieldViews.add(new FarmOutputDetailsForFieldView(farmOutputDetailsForField));
				totalUsedAcre += farmOutputDetailsForField.getUsedAcres();
				totalProfit += farmOutputDetailsForField.getProfit();
			}
			
			if(totalUsedAcre > 0 && totalProfit > 0){
                for (FarmOutputDetailsForFieldView farmOutputDetailsForFieldView : farmOutputDetailsForFieldViews) {
                    farmOutputDetailsForFieldView.setUsedAcresPercentage(AgricultureStandardUtils.doubleToInteger((farmOutputDetailsForFieldView.getUsedAcresAsDouble() * 100) / totalUsedAcre));
                    farmOutputDetailsForFieldView.setUsedCapitalPercentage(AgricultureStandardUtils.doubleToInteger((farmOutputDetailsForFieldView.getProfitAsDouble() * 100) / totalProfit));
                }
		    }
		}*/

        OutputBeanForStrategy outputBeanForStrategy = new OutputBeanForStrategy();
        outputBeanForStrategy.setBaselineFlag(true);
        outputBeanForStrategy.setFarmID(farmId);
        return getAllFarmOutputDetailsForFieldByFarm(outputBeanForStrategy);
    }

    /**
     * @return - list of output details for farm
     * @author - Abhishek
     * @date - 21-12-2015
     */
    @Override
    public List<Object> calculateFarmOutputStatistics(OutputBeanForStrategy outputBeanForStrategy) {

        if (outputBeanForStrategy.getSaveFlag()) {
            farmOutputCalculationDao.calculateFarmOutputStatistics(outputBeanForStrategy.getFarmInfo());
            return null;
        } else {
            return farmOutputCalculationDao.calculateFarmOutputStatistics(outputBeanForStrategy);
        }

    }

    /**
     * @return - list of output details for farm by Acres
     * @author - Abhishek
     * @date - 21-12-2015
     */
    @Override
    public List<FarmOutputDetailsView> getAllFarmOutputDetailsByFarm(OutputBeanForStrategy outputBeanForStrategy) {
        Double totalUsedAcre = 0.0;
        Double totalProfit = 0.0;
        List<FarmOutputDetailsView> farmOutputDetailsViewList = new ArrayList<FarmOutputDetailsView>();
        List<FarmOutputDetails> farmOutputDetails = farmOutputCalculationDao.getAllFarmOutputDetailsByFarm(outputBeanForStrategy);

        if (farmOutputDetails != null) {
            for (FarmOutputDetails farmOutputDetail : farmOutputDetails) {
                farmOutputDetailsViewList.add ( new FarmOutputDetailsView ( farmOutputDetail ) );
                totalUsedAcre += farmOutputDetail.getUsedAcres ();
                totalProfit += farmOutputDetail.getProfit ();
            }

//            if (totalUsedAcre > 0 && totalProfit > 0) {
            for (FarmOutputDetailsView farmOutputDetailsView : farmOutputDetailsViewList) {
                farmOutputDetailsView.setUsedAcresPercentage ( AgricultureStandardUtils.doubleToInteger ( (farmOutputDetailsView.getUsedAcresDouble () * 100) / totalUsedAcre ) );
                farmOutputDetailsView.setUsedCapitalPercentage ( AgricultureStandardUtils.doubleToInteger ( (farmOutputDetailsView.getProfitDouble () * 100) / totalProfit ) );
                if (!farmOutputDetailsView.getProfit ().equalsIgnoreCase ( "0" ) || !farmOutputDetailsView.getUsedAcres ().equalsIgnoreCase ( "0" )) {
                    int acreage = Integer.parseInt ( AgricultureStandardUtils.removeAllCommas ( farmOutputDetailsView.getProfit ().split ( "//." )[0] ) );
                    int totalProfitInInteger = Integer.parseInt ( AgricultureStandardUtils.removeAllCommas ( String.valueOf ( AgricultureStandardUtils.withoutDecimalAndComma ( totalProfit ) ).split ( "//." )[0] ) );
                    int acreageInPer = Integer.parseInt ( AgricultureStandardUtils.removeAllCommas ( String.valueOf ( (acreage * 100) / totalProfitInInteger ).split ( "//." )[0] ) );
                    int estimateIncome = Integer.parseInt ( AgricultureStandardUtils.removeAllCommas ( farmOutputDetailsView.getUsedAcres ().split ( "//." )[0] ) );
                    int totalEstimateIncome = Integer.parseInt ( AgricultureStandardUtils.removeAllCommas ( String.valueOf ( AgricultureStandardUtils.withoutDecimalAndComma ( totalUsedAcre ).split ( "//." )[0] ) ) );
                    int estimateIncomeInPer = Integer.parseInt ( AgricultureStandardUtils.removeAllCommas ( String.valueOf ( (estimateIncome * 100) / totalEstimateIncome ).split ( "//." )[0] ) );
                    farmOutputDetailsView.setProfitIndex ( AgricultureStandardUtils.doubleWithOneDecimal ( ((farmOutputDetailsView.getProfitDouble () * 100) / totalProfit) / ((farmOutputDetailsView.getUsedAcresAsDouble () * 100) / totalUsedAcre) ) );
                    farmOutputDetailsView.setRatio ( AgricultureStandardUtils.doubleWithOneDecimal ( farmOutputDetailsView.getProfitDouble () / farmOutputDetailsView.getUsedAcresAsDouble () ) );
                    farmOutputDetailsView.setRating ( (farmOutputDetailsView.getProfitIndex () >= 1) ? "Green" : (farmOutputDetailsView.getProfitIndex () < 1 && farmOutputDetailsView.getProfitIndex () >= 0.6) ? "Yellow" : (farmOutputDetailsView.getProfitIndex () < 0.6 /*&& farmOutputDetailsView.getProfitIndex() > 0*/) ? "Red" : "Grey" );
                }else{
                    farmOutputDetailsView.setProfitIndex ( AgricultureStandardUtils.doubleWithOneDecimal ( 0.0 ) );
                    farmOutputDetailsView.setRatio ( AgricultureStandardUtils.doubleWithOneDecimal ( 0.0 ) );
                    farmOutputDetailsView.setRating (  "Grey" );
                }
            }
//            }
        }

        /**
         * for sorting list of FarmOutputDetails
         */
        Collections.sort(farmOutputDetailsViewList, new FarmOutputDetailsView());

        return farmOutputDetailsViewList;
    }

    /**
     * @return - list of output details for farm
     * @author - Abhishek
     * @date - 21-12-2015
     */
    @Override
    public List<FarmOutputDetailsForFieldView> getAllFarmOutputDetailsForFieldByFarm(OutputBeanForStrategy outputBeanForStrategy) {
        Double totalUsedAcre = new Double(0);
        Double totalProfit = new Double(0);
        List<FarmOutputDetailsForFieldView> farmOutputDetailsForFieldViews = new ArrayList<FarmOutputDetailsForFieldView>();
        List<FarmOutputDetailsForField> farmOutputDetailsForFields = farmOutputCalculationDao.getAllFarmOutputDetailsForFieldByFarm(outputBeanForStrategy);

        if (farmOutputDetailsForFields != null) {
            for (FarmOutputDetailsForField farmOutputDetailsForField : farmOutputDetailsForFields) {
                farmOutputDetailsForFieldViews.add ( new FarmOutputDetailsForFieldView ( farmOutputDetailsForField ) );
                totalUsedAcre += farmOutputDetailsForField.getUsedAcres ();
                totalProfit += farmOutputDetailsForField.getProfit ();
            }

            if (totalUsedAcre > 0 && totalProfit > 0) {
                for (FarmOutputDetailsForFieldView farmOutputDetailsForFieldView : farmOutputDetailsForFieldViews) {
                    if (farmOutputDetailsForFieldView.getProfitAsDouble () != 0.0 || farmOutputDetailsForFieldView.getUsedAcresAsDouble () != 0.0) {

                        farmOutputDetailsForFieldView.setUsedAcresPercentage ( AgricultureStandardUtils.doubleToInteger ( (farmOutputDetailsForFieldView.getUsedAcresAsDouble () * 100) / totalUsedAcre ) );
                        farmOutputDetailsForFieldView.setUsedCapitalPercentage ( AgricultureStandardUtils.doubleToInteger ( (farmOutputDetailsForFieldView.getProfitAsDouble () * 100) / totalProfit ) );
                        int acreage = Integer.parseInt ( AgricultureStandardUtils.removeAllCommas ( farmOutputDetailsForFieldView.getProfit ().split ( "//." )[0] ) );
                        int totalProfitInInteger = Integer.parseInt ( AgricultureStandardUtils.removeAllCommas ( String.valueOf ( AgricultureStandardUtils.withoutDecimalAndComma ( totalProfit ) ).split ( "//." )[0] ) );
                        int acreageInPer = Integer.parseInt ( AgricultureStandardUtils.removeAllCommas ( String.valueOf ( (acreage * 100) / totalProfitInInteger ).split ( "//." )[0] ) );
                        int estimateIncome = Integer.parseInt ( AgricultureStandardUtils.removeAllCommas ( farmOutputDetailsForFieldView.getUsedAcres ().split ( "//." )[0] ) );
                        int totalEstimateIncome = Integer.parseInt ( AgricultureStandardUtils.removeAllCommas ( String.valueOf ( AgricultureStandardUtils.withoutDecimalAndComma ( totalUsedAcre ).split ( "//." )[0] ) ) );
                        int estimateIncomeInPer = Integer.parseInt ( AgricultureStandardUtils.removeAllCommas ( String.valueOf ( (estimateIncome * 100) / totalEstimateIncome ).split ( "//." )[0] ) );
                        farmOutputDetailsForFieldView.setProfitIndex ( AgricultureStandardUtils.doubleWithOneDecimal ( ((farmOutputDetailsForFieldView.getProfitDouble () * 100) / totalProfit) / ((farmOutputDetailsForFieldView.getUsedAcresAsDouble () * 100) / totalUsedAcre) ) );
                        farmOutputDetailsForFieldView.setRatio ( AgricultureStandardUtils.doubleWithOneDecimal ( farmOutputDetailsForFieldView.getProfitDouble () / farmOutputDetailsForFieldView.getUsedAcresAsDouble () ) );
                        farmOutputDetailsForFieldView.setRating ( (farmOutputDetailsForFieldView.getProfitIndex () >= 1) ? "Green" : (farmOutputDetailsForFieldView.getProfitIndex () < 1 && farmOutputDetailsForFieldView.getProfitIndex () >= 0.6) ? "Yellow" : (farmOutputDetailsForFieldView.getProfitIndex () < 0.6 /*&& farmOutputDetailsForFieldView.getProfitIndex() > 0.0*/) ? "Red" : "Grey" );
                    }
                }
            }
        }

        return farmOutputDetailsForFieldViews;
    }

    @Override
    public Double calculateProfit(double expectedYield, double expectedprice, double minAcres, double variableProductionCost) {
        return farmOutputCalculationDao.calculateProfit(expectedYield, expectedprice, minAcres, variableProductionCost);
    }


}

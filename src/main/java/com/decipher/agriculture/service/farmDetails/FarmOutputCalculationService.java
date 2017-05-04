package com.decipher.agriculture.service.farmDetails;

import java.util.List;

import com.decipher.agriculture.bean.OutputBeanForStrategy;
import com.decipher.agriculture.data.farm.FarmInfo;
import com.decipher.view.form.farmDetails.FarmOutputDetailsForFieldView;
import com.decipher.view.form.farmDetails.FarmOutputDetailsView;

public interface FarmOutputCalculationService {

	boolean checkIfFarmHasValidStrategy(int farmId);

	void calculateFarmOutputStatistics(FarmInfo farmInfo);

	List<FarmOutputDetailsView> getAllFarmOutputDetailsByFarm(int farmId);

	List<FarmOutputDetailsForFieldView> getAllFarmOutputDetailsForFieldByFarm(int farmId);

	/**
	 * @author - Abhishek
	 * @date - 21-12-2015
	 * @return - list of output details for farm
	 */
	List<Object> calculateFarmOutputStatistics(OutputBeanForStrategy outputBeanForStrategy);

	/**
	 * @author - Abhishek
	 * @date - 21-12-2015
	 * @return - list of output details for farm
	 */
	List<FarmOutputDetailsView> getAllFarmOutputDetailsByFarm(OutputBeanForStrategy outputBeanForStrategy);

	/**
	 * @author - Abhishek
	 * @date - 21-12-2015
	 * @return - list of output details for farm
	 */
	List<FarmOutputDetailsForFieldView> getAllFarmOutputDetailsForFieldByFarm(OutputBeanForStrategy outputBeanForStrategy);


	Double calculateProfit(double expectedYield, double expectedprice, double minAcres, double variableProductionCost);
}

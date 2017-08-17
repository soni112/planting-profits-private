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
     * @return - list of output details for farm
     * @author - Abhishek
     * @date - 21-12-2015
     */
    List<Object> calculateFarmOutputStatistics(OutputBeanForStrategy outputBeanForStrategy);

    /**
     * @return - list of output details for farm
     * @author - Abhishek
     * @date - 21-12-2015
     */
    List<FarmOutputDetailsView> getAllFarmOutputDetailsByFarm(OutputBeanForStrategy outputBeanForStrategy);

    /**
     * @return - list of output details for farm
     * @author - Abhishek
     * @date - 21-12-2015
     */
    List<FarmOutputDetailsForFieldView> getAllFarmOutputDetailsForFieldByFarm(OutputBeanForStrategy outputBeanForStrategy);


    Double calculateProfit(double expectedYield, double expectedprice, double minAcres, double variableProductionCost);
}

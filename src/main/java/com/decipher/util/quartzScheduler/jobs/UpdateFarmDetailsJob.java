package com.decipher.util.quartzScheduler.jobs;

import com.decipher.agriculture.data.account.Account;
import com.decipher.agriculture.data.farm.Farm;
import com.decipher.agriculture.service.farmDetails.FarmDetailsContainerService;
import com.decipher.util.PlantingProfitLogger;
import org.codehaus.jettison.json.JSONException;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by abhishek on 10/5/16.
 */
public class UpdateFarmDetailsJob implements Job {

    private static Account currentUser;
    private static Farm farm;
    private static FarmDetailsContainerService farmDetailsContainerService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        PlantingProfitLogger.warn("Commencing update of farm details");

        try {
            farmDetailsContainerService.updateFarmDetails(farm, currentUser);
            PlantingProfitLogger.warn("Update of farm details complete");
        } catch (JSONException e) {
            PlantingProfitLogger.warn("Error while updating farm details");
            PlantingProfitLogger.error(e);
        }

    }

    public static void setCurrentUser(Account currentUser) {
        UpdateFarmDetailsJob.currentUser = currentUser;
    }

    public static void setFarm(Farm farm) {
        UpdateFarmDetailsJob.farm = farm;
    }

    public static void setFarmDetailsContainerService(FarmDetailsContainerService farmDetailsContainerService) {
        UpdateFarmDetailsJob.farmDetailsContainerService = farmDetailsContainerService;
    }
}

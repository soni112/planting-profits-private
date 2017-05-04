package com.decipher.util.quartzScheduler.jobs;

import com.decipher.agriculture.service.farmDetails.FarmDetailsContainerService;
import com.decipher.util.PlantingProfitLogger;
import com.decipher.util.listner.SpringApplicationContextListener;
import org.codehaus.jettison.json.JSONException;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

/**
 * Created by abhishek on 5/5/16.
 */
public class FarmDetailsContainerJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        PlantingProfitLogger.warn("Farm details container job process started");

        ApplicationContext applicationContext = SpringApplicationContextListener.getApplicationContext();

        FarmDetailsContainerService farmDetailsContainerService = applicationContext.getBean(FarmDetailsContainerService.class);

        try {
            farmDetailsContainerService.updateAllDetails();
        } catch (JSONException e) {

            PlantingProfitLogger.error(e);
        }

        PlantingProfitLogger.warn("Farm details container job process complete");
    }
}

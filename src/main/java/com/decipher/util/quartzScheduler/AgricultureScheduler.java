package com.decipher.util.quartzScheduler;

import com.decipher.agriculture.data.account.Account;
import com.decipher.agriculture.data.farm.Farm;
import com.decipher.agriculture.service.farmDetails.FarmDetailsContainerService;
import com.decipher.util.PlantingProfitLogger;
import com.decipher.util.listner.SpringApplicationContextListener;
import com.decipher.util.quartzScheduler.jobs.FarmDetailsContainerJob;
import com.decipher.util.quartzScheduler.jobs.StudentExpirationJob;
import com.decipher.util.quartzScheduler.jobs.UpdateFarmDetailsJob;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.ApplicationContext;

import static org.quartz.CronScheduleBuilder.*;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created by abhishek on 2/4/16.
 */
public class AgricultureScheduler {

    private static Scheduler scheduler = null;

    public static void startQuartsSchedulerJobs() throws SchedulerException {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        scheduler = schedulerFactory.getScheduler();

        PlantingProfitLogger.info("Starting building jobs");

        buildStudentValidationJob();
        buildFarmDetailsContainerJob();

        PlantingProfitLogger.info("Completed building jobs");


        PlantingProfitLogger.warn("Initializing Scheduler");

        scheduler.start();

        PlantingProfitLogger.warn("Scheduler Started");
    }

    public static boolean stopQuartzSchedulerJobs() throws SchedulerException {

        if(scheduler != null){
            PlantingProfitLogger.warn("Preparing To Shutdown Scheduler");
            scheduler.shutdown(true);
            PlantingProfitLogger.warn("Scheduler Shutdown Process Complete");
            return true;
        } else {
            return false;
        }

    }

    public static void startQuartsSchedulerForFarmUpdate(Farm farm, Account account) throws SchedulerException {
        PlantingProfitLogger.warn("Initializing Farm Update Scheduler");

        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();

        buildFarmUpdateSchedulerJob(scheduler);

        ApplicationContext applicationContext = SpringApplicationContextListener.getApplicationContext();

        UpdateFarmDetailsJob.setFarm(farm);
        UpdateFarmDetailsJob.setFarmDetailsContainerService(applicationContext.getBean(FarmDetailsContainerService.class));
        UpdateFarmDetailsJob.setCurrentUser(account);

        PlantingProfitLogger.warn("Farm Update Scheduler Started");
        scheduler.start();

    }

    /**
     * building students validation scheduler job
     * @throws SchedulerException
     */
    private static void buildStudentValidationJob() throws SchedulerException {

        PlantingProfitLogger.warn("Starting Building Student Validation job");

        // define the Student validation job and tie it to our StudentExpirationJob class
        JobDetail StudentValidationJob = newJob(StudentExpirationJob.class)
                .withIdentity("StudentValidationJob", "AgricultureSchedulerGroup")
                .build();

        // CronTrigger the job to run daily at 23:59 PM
        CronTrigger StudentValidationJobTrigger = newTrigger()
                .withIdentity("StudentValidationJobTrigger", "AgricultureSchedulerGroup")
                .withSchedule(dailyAtHourAndMinute(23, 59))
                .build();

        // Tell quartz to schedule the job using our CronTrigger
        scheduler.scheduleJob(StudentValidationJob, StudentValidationJobTrigger);

        PlantingProfitLogger.warn("Completed Building Student Validation job");

    }

    /**
     * building farm details container(Jugad) job
     * @throws SchedulerException
     */
    private static void buildFarmDetailsContainerJob() throws SchedulerException {
        PlantingProfitLogger.warn("Starting Building FarmDetailsContainer job");

        // define the farmDetailsContainer job and tie it to our FarmDetailsContainerJob class
        JobDetail farmDetailsContainerJob = newJob(FarmDetailsContainerJob.class)
                .withIdentity("FarmDetailsContainerJob", "AgricultureSchedulerGroup")
                .build();

        Trigger farmDetailsContainerJobTrigger = newTrigger()
                .withIdentity("FarmDetailsContainerJobTrigger", "AgricultureSchedulerGroup")
                .startNow().build();

        // Tell quartz to schedule the job using trigger for farmDetailsContainer job
        scheduler.scheduleJob(farmDetailsContainerJob, farmDetailsContainerJobTrigger);

        PlantingProfitLogger.warn("Completed Building FarmDetailsContainer job");
    }

    /**
     * building update of farm details in farm details container job
     * @param scheduler - scheduler context
     * @throws SchedulerException
     */
    private static void buildFarmUpdateSchedulerJob(Scheduler scheduler) throws SchedulerException {
        PlantingProfitLogger.info("Building Student Validation job");

        // define the Farm Update job and tie it to our UpdateFarmDetailsJob class
        JobDetail updateFarmDetailsJobDetail = newJob(UpdateFarmDetailsJob.class)
                .withIdentity("UpdateFarmDetailsJob", "AgricultureSchedulerGroup")
                .build();

        // CronTrigger the job to run immediately
        Trigger updateFarmDetailsJobTrigger = newTrigger()
                .withIdentity("UpdateFarmDetailsJob", "AgricultureSchedulerGroup")
                .startNow().build();

        // Tell quartz to schedule the job using our CronTrigger
        scheduler.scheduleJob(updateFarmDetailsJobDetail, updateFarmDetailsJobTrigger);

        PlantingProfitLogger.info("Completed Building Student Validation job");
    }

}

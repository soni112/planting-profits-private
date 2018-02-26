package com.decipher.util.quartzScheduler.jobs;

import com.decipher.agriculture.data.account.Account;
import com.decipher.agriculture.service.account.AccountService;
import com.decipher.util.PlantingProfitLogger;
import com.decipher.util.listener.SpringApplicationContextListener;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by abhishek on 2/4/16.
 */
public class StudentExpirationJob implements Job {


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        PlantingProfitLogger.info("Student Expiration Process Started");

        ApplicationContext applicationContext = SpringApplicationContextListener.getApplicationContext();

        AccountService accountService = applicationContext.getBean(AccountService.class);

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Date date = null;
        try {
            PlantingProfitLogger.info("date : " + new Date());
            date = formatter.parse(formatter.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<Account> allUsersToDisable = accountService.getAllUsersToDisable(date);

        int count = 0;
        for (Account accountToDisable : allUsersToDisable) {
            accountToDisable.setEnabled(false);
            accountService.updateUser(accountToDisable);
            count++;
        }

        PlantingProfitLogger.info("Student Expiration Process Finished With " + count + " Account(s) Disabled");
    }

}

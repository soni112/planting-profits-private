package com.decipher.util.listener;

/**
 * @author abhishek
 * @date 27-11-2015 
 */


import com.decipher.config.ApplicationConfig;
import com.decipher.util.PlantingProfitLogger;
import com.decipher.config.ApplicationMode;
import com.decipher.util.quartzScheduler.AgricultureScheduler;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ApplicationContextListener implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		/**
		 * @added - Abhishek
		 * @date - 02-04-2016
		 * @desc - Destroying scheduler
		 */
	/*	try {
			AgricultureScheduler.stopQuartzSchedulerJobs();
		} catch (SchedulerException e) {
			PlantingProfitLogger.error(e);
		}*/
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {

		/*String pathToAdd = "/usr/local/lib/jni";

		try {
			final Field usrPathsField = ClassLoader.class.getDeclaredField("usr_paths");
			usrPathsField.setAccessible(true);

			//get array of paths
			final String[] paths = (String[])usrPathsField.get(null);

			//check if the path to add is already present
			for(String path : paths) {
			    if(path.equals(pathToAdd)) {
			        return;
			    }
			}

			//add the new path
			final String[] newPaths = Arrays.copyOf(paths, paths.length + 1);
			newPaths[newPaths.length-1] = pathToAdd;
			usrPathsField.set(null, newPaths);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			PlantingProfitLogger.error(e);
		}*/

		/**
		 * @added - Abhishek
		 * @date - 02-04-2016
		 * @desc - Initializing scheduler
		 */
/*		try {
			ApplicationContext applicationContext = SpringApplicationContextListener.getApplicationContext();
			ApplicationMode applicationMode = applicationContext.getBean(ApplicationConfig.class).getApplicationMode();
			if (applicationMode.equals(ApplicationMode.UAT) || applicationMode.equals(ApplicationMode.PRODUCTION)) {
				PlantingProfitLogger.info("Data container initialization started");
				AgricultureScheduler.startQuartsSchedulerJobs();
			}
		} catch (Exception e) {
			PlantingProfitLogger.error(e);
		}*/

	}

}

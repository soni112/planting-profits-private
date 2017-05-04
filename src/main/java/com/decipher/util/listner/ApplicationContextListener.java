package com.decipher.util.listner;

/**
 * @author abhishek
 * @date 27-11-2015 
 */

import com.decipher.util.PlantingProfitLogger;
import com.decipher.util.appStandard.ApplicationMode;
import com.decipher.util.appStandard.ApplicationStandard;
import com.decipher.util.quartzScheduler.AgricultureScheduler;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;

public class ApplicationContextListener implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		/**
		 * @added - Abhishek
		 * @date - 02-04-2016
		 * @desc - Destroying scheduler
		 */
		try {
			AgricultureScheduler.stopQuartzSchedulerJobs();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}

//		System.gc();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		PlantingProfitLogger.info("Context initialization started");
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
		try {
//			ApplicationContext applicationContext = SpringApplicationContextListener.getApplicationContext();
//			ApplicationStandard bean = applicationContext.getBean(ApplicationStandard.class);
//			ApplicationMode applicationMode = bean.getApplicationMode();
//			if (Objects.equals(applicationMode, ApplicationMode.UAT) || Objects.equals(applicationMode, ApplicationMode.PRODUCTION)) {
				AgricultureScheduler.startQuartsSchedulerJobs();
//			}
		} catch (Exception e) {
			PlantingProfitLogger.error(e);
		}

	}

}

package com.decipher.util.appStandard;

import com.decipher.util.PlantingProfitLogger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by abhishek on 9/9/16.
 */
@Component
public class ApplicationStandard {

    private static ApplicationMode applicationMode;

    public static ApplicationMode getApplicationMode() {
        return applicationMode;
    }

    @Value("${agriculture.application.mode}")
    private  void setApplicationMode(String applicationMode) {
        try {
            switch (Integer.parseInt(applicationMode)){
                case 0:
                    ApplicationStandard.applicationMode = ApplicationMode.DEVELOPMENT;
                    break;
                case 1:
                    ApplicationStandard.applicationMode = ApplicationMode.UAT;
                    break;
                case 2:
                    ApplicationStandard.applicationMode = ApplicationMode.PRODUCTION;
                    break;
                default:
                    PlantingProfitLogger.warn("Current application mode is " + ApplicationStandard.applicationMode);
                    break;

            }
        } catch (NumberFormatException e) {
            PlantingProfitLogger.error(e);
            throw new RuntimeException("Application mode must be provided in application properties");
        }
    }
}

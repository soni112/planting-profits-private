package com.decipher.config;

import com.decipher.util.PlantingProfitLogger;
import com.decipher.util.listner.SpringApplicationContextListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;

/**
 * Created on 26/7/17 5:33 PM by Abhishek Samuel
 * Software Engineer
 * abhisheks@decipherzone.com
 * Decipher Zone Softwares LLP
 * www.decipherzone.com
 */
public class ApplicationConfig {

    private static String dbPoolName;
    //	private static String driverType;
    private static String dataSourceClassName;
    private static String driverClassName;
    private static String maximumPoolSize;
    private static String minimumIdleTime;
    private static String jdbcUrl;
    // Local
    private static String dbUserLocal;
    private static String dbdbpasswordLocal;
    private static String dbdatabaseNameLocal;
    private static String dbserverNameLocal;
    private static String dbportNumberLocal;
    // UAT
    private static String dbUserUAT;
    private static String dbdbpasswordUAT;
    private static String dbdatabaseNameUAT;
    private static String dbserverNameUAT;
    private static String dbportNumberUAT;
    // Production
    private static String dbUserProduction;
    private static String dbdbpasswordProduction;
    private static String dbdatabaseNameProduction;
    private static String dbserverNameProduction;
    private static String dbportNumberProduction;

    private static ApplicationMode APPLICATION_MODE;

    private static String APP_URL_LOCAL;
    private static String APP_URL_UAT;
    private static String APP_URL_PRODUCTION;

    private static String APP_EMAIL_INFO_LOCAL;
    private static String APP_EMAIL_INFO_UAT;
    private static String APP_EMAIL_INFO_PRODUCTION;

    public static String getAppEmail() {
        switch (getApplicationMode()){
            case DEVELOPMENT:
                return ApplicationConfig.APP_EMAIL_INFO_LOCAL;
            case UAT:
                return ApplicationConfig.APP_EMAIL_INFO_UAT;
            case PRODUCTION:
                return ApplicationConfig.APP_EMAIL_INFO_PRODUCTION;
            default:
                PlantingProfitLogger.warn("Current application mode is " + ApplicationConfig.APPLICATION_MODE);
                break;
        }
        return "";
    }

    @Value("${app.email.info.dev}")
    public void setAppEmailLocal(String appEmailLocal) {
        ApplicationConfig.APP_EMAIL_INFO_LOCAL = appEmailLocal;
    }

    @Value("${app.email.info.uat}")
    public void setAppEmailUat(String appEmailUat) {
        ApplicationConfig.APP_EMAIL_INFO_UAT = appEmailUat;
    }

    @Value("${app.email.info.prod}")
    public void setAppEmailProduction(String appEmailProduction) {
        ApplicationConfig.APP_EMAIL_INFO_PRODUCTION = appEmailProduction;
    }

    public static String getAppUrl() {
        switch (getApplicationMode()){
            case DEVELOPMENT:
                return ApplicationConfig.APP_URL_LOCAL;
            case UAT:
                return ApplicationConfig.APP_URL_UAT;
            case PRODUCTION:
                return ApplicationConfig.APP_URL_PRODUCTION;
            default:
                PlantingProfitLogger.warn("Current application mode is " + ApplicationConfig.APPLICATION_MODE);
                break;
        }
        return "";
    }

    @Value("${app.url.dev}")
    public void setAppUrlLocal(String appUrlLocal) {
        ApplicationConfig.APP_URL_LOCAL = appUrlLocal;
    }

    @Value("${app.url.uat}")
    public void setAppUrlUat(String appUrlUat) {
        ApplicationConfig.APP_URL_UAT = appUrlUat;
    }

    @Value("${app.url.prod}")
    public void setAppUrlProduction(String appUrlProduction) {
        ApplicationConfig.APP_URL_PRODUCTION = appUrlProduction;
    }

    public static ApplicationMode getApplicationMode() {
        return ApplicationConfig.APPLICATION_MODE;
    }

    @Value("${application.mode}")
    private void setApplicationMode(String applicationMode) {
        try {
            switch (Integer.parseInt(applicationMode)){
                case 0:
                    ApplicationConfig.APPLICATION_MODE = ApplicationMode.DEVELOPMENT;
                    break;
                case 1:
                    ApplicationConfig.APPLICATION_MODE = ApplicationMode.UAT;
                    break;
                case 2:
                    ApplicationConfig.APPLICATION_MODE = ApplicationMode.PRODUCTION;
                    break;
                default:
                    PlantingProfitLogger.info("Current application mode is " + ApplicationConfig.APPLICATION_MODE);
                    break;

            }
        } catch (NumberFormatException e) {
            PlantingProfitLogger.error(e);
            throw new RuntimeException("Application mode must be provided in application properties");
        }
    }

    public static String getDriverClassName() {
        return driverClassName;
    }

    @Value("${driverClassName}")
    public void setDriverClassName(String driverClassName) {
        ApplicationConfig.driverClassName = driverClassName;
    }

    public static String getJdbcUrl() {
        return jdbcUrl;
    }

    @Value("${jdbcUrl}")
    public void setJdbcUrl(String jdbcUrl) {
        ApplicationConfig.jdbcUrl = jdbcUrl;
    }

    public static String getDbPoolName() {
        return dbPoolName;
    }

    @Value("${dbPoolName}")
    public void setDbPoolName(String dbPoolName) {
        ApplicationConfig.dbPoolName = dbPoolName;
    }

    public static String getDbUserLocal() {
        return dbUserLocal;
    }

    @Value("${dbuser.dev}")
    public void setDbUserLocal(String dbUserLocal) {
        ApplicationConfig.dbUserLocal = dbUserLocal;
    }

    public static String getDbdbpasswordLocal() {
        return ApplicationConfig.dbdbpasswordLocal;
    }

    @Value("${dbpassword.dev}")
    public void setDbdbpasswordLocal(String dbdbpasswordLocal) {
        ApplicationConfig.dbdbpasswordLocal = dbdbpasswordLocal;
    }

    public static String getDbdatabaseNameLocal() {
        return ApplicationConfig.dbdatabaseNameLocal;
    }

    @Value("${dbdatabaseName.dev}")
    public void setDbdatabaseNameLocal(String dbdatabaseNameLocal) {
        ApplicationConfig.dbdatabaseNameLocal = dbdatabaseNameLocal;
    }

    public static String getDbserverNameLocal() {
        return dbserverNameLocal;
    }

    @Value("${dbserverName.dev}")
    public void setDbserverNameLocal(String dbserverNameLocal) {
        ApplicationConfig.dbserverNameLocal = dbserverNameLocal;
    }

    public static String getDbportNumberLocal() {
        return dbportNumberLocal;
    }

    @Value("${dbportNumber.dev}")
    public void setDbportNumberLocal(String dbportNumberLocal) {
        ApplicationConfig.dbportNumberLocal = dbportNumberLocal;
    }

    public static String getDbUserUAT() {
        return dbUserUAT;
    }

    @Value("${dbuser.uat}")
    public void setDbUserUAT(String dbUserUAT) {
        ApplicationConfig.dbUserUAT = dbUserUAT;
    }

    public static String getDbdbpasswordUAT() {
        return dbdbpasswordUAT;
    }

    @Value("${dbpassword.uat}")
    public void setDbdbpasswordUAT(String dbdbpasswordUAT) {
        ApplicationConfig.dbdbpasswordUAT = dbdbpasswordUAT;
    }

    public static String getDbdatabaseNameUAT() {
        return dbdatabaseNameUAT;
    }

    @Value("${dbdatabaseName.uat}")
    public void setDbdatabaseNameUAT(String dbdatabaseNameUAT) {
        ApplicationConfig.dbdatabaseNameUAT = dbdatabaseNameUAT;
    }

    public static String getDbserverNameUAT() {
        return dbserverNameUAT;
    }

    @Value("${dbserverName.uat}")
    public void setDbserverNameUAT(String dbserverNameUAT) {
        ApplicationConfig.dbserverNameUAT = dbserverNameUAT;
    }

    public static String getDbportNumberUAT() {
        return dbportNumberUAT;
    }

    @Value("${dbportNumber.uat}")
    public void setDbportNumberUAT(String dbportNumberUAT) {
        ApplicationConfig.dbportNumberUAT = dbportNumberUAT;
    }

    public static String getDbUserProduction() {
        return dbUserProduction;
    }

    @Value("${dbuser.prod}")
    public void setDbUserProduction(String dbUserProduction) {
        ApplicationConfig.dbUserProduction = dbUserProduction;
    }

    public static String getDbdbpasswordProduction() {
        return dbdbpasswordProduction;
    }

    @Value("${dbpassword.prod}")
    public void setDbdbpasswordProduction(String dbdbpasswordProduction) {
        ApplicationConfig.dbdbpasswordProduction = dbdbpasswordProduction;
    }

    public static String getDbdatabaseNameProduction() {
        return dbdatabaseNameProduction;
    }

    @Value("${dbdatabaseName.prod}")
    public void setDbdatabaseNameProduction(String dbdatabaseNameProduction) {
        ApplicationConfig.dbdatabaseNameProduction = dbdatabaseNameProduction;
    }

    public static String getDbserverNameProduction() {
        return dbserverNameProduction;
    }

    @Value("${dbserverName.prod}")
    public void setDbserverNameProduction(String dbserverNameProduction) {
        ApplicationConfig.dbserverNameProduction = dbserverNameProduction;
    }

    public static String getDbportNumberProduction() {
        return dbportNumberProduction;
    }

    @Value("${dbportNumber.prod}")
    public void setDbportNumberProduction(String dbportNumberProduction) {
        ApplicationConfig.dbportNumberProduction = dbportNumberProduction;
    }

    /*public static String getDriverType() {
        return driverType;
    }

    @Value("${driverType}")
    public void setDriverType(String driverType) {
        ApplicationConfig.driverType = driverType;
    }
*/
    public static String getDataSourceClassName() {
        return dataSourceClassName;
    }

    @Value("${dataSourceClassName}")
    public void setDataSourceClassName(String dataSourceClassName) {
        ApplicationConfig.dataSourceClassName = dataSourceClassName;
    }

    public static int getMaximumPoolSize() {
        try {
            return Integer.parseInt(maximumPoolSize);
        } catch (NumberFormatException nfe) {
            PlantingProfitLogger.error("Error : ", nfe);
            return 100;
        }
    }

    @Value("${maximumPoolSize}")
    public void setMaximumPoolSize(String maximumPoolSize) {
        ApplicationConfig.maximumPoolSize = maximumPoolSize;
    }

    public static int getMinimumIdleTime() {
        try {
            return Integer.parseInt(minimumIdleTime);
        } catch (NumberFormatException nfe) {
            PlantingProfitLogger.error("Error : ", nfe);
            return 100;
        }
    }

    @Value("${minimumIdleTime}")
    public void setMinimumIdleTime(String minimumIdleTime) {
        ApplicationConfig.minimumIdleTime = minimumIdleTime;
    }


    public static ApplicationContext getApplicationContext(){
        return SpringApplicationContextListener.getApplicationContext();
    }
}

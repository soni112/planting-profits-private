package com.decipher.config;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.SQLException;
import java.util.Properties;

/**
 * Created on 26/7/17 5:33 PM by Abhishek Samuel
 * Software Engineer
 * abhisheks@decipherzone.com
 * Decipher Zone Softwares LLP
 * www.decipherzone.com
 */
public class HikariDataSourceConfiguration extends HikariDataSource{
    private final Properties properties = new Properties();
    private String dbUser;
    private String dbPassword;
    private String dbName;
    private String dbServer;
    private String dbPort;

    public HikariDataSourceConfiguration() throws SQLException {
        super();
        if (ApplicationConfig.getApplicationMode() == ApplicationMode.DEVELOPMENT) {
            loadLocalDatabaseSettings();
        } else if (ApplicationConfig.getApplicationMode() == ApplicationMode.UAT) {
            loadUATDatabaseSettings();
        } else if (ApplicationConfig.getApplicationMode() == ApplicationMode.PRODUCTION) {
            loadProductionDatabaseSettings();
        } else {
            throw new RuntimeException("Invalid Application mode found while configuring database with app mode : " + ApplicationConfig.getApplicationMode());
        }
        configureDatabaseSetting();
        setMaximumPoolSize(ApplicationConfig.getMaximumPoolSize());
        setMinimumIdle(ApplicationConfig.getMinimumIdleTime());
        setDataSourceClassName(ApplicationConfig.getDataSourceClassName());
        setPoolName(ApplicationConfig.getDbPoolName());
        setDataSourceProperties(properties);
        setConnectionTestQuery("select 1");
    }

    private void configureDatabaseSetting() {
        properties.setProperty("user", dbUser);
        properties.setProperty("password", dbPassword);
        properties.setProperty("databaseName", dbName);
        properties.setProperty("serverName", dbServer);
        properties.setProperty("portNumber", dbPort);
        //properties.setProperty("driverType", ApplicationConfig.getDriverType());
    }

    private void loadLocalDatabaseSettings() {
        dbUser = ApplicationConfig.getDbUserLocal();
        dbPassword = ApplicationConfig.getDbdbpasswordLocal();
        dbName = ApplicationConfig.getDbdatabaseNameLocal();
        dbServer = ApplicationConfig.getDbserverNameLocal();
        dbPort = ApplicationConfig.getDbportNumberLocal();
    }

    private void loadUATDatabaseSettings() {
        dbUser = ApplicationConfig.getDbUserUAT();
        dbPassword = ApplicationConfig.getDbdbpasswordUAT();
        dbName = ApplicationConfig.getDbdatabaseNameUAT();
        dbServer = ApplicationConfig.getDbserverNameUAT();
        dbPort = ApplicationConfig.getDbportNumberUAT();
    }

    private void loadProductionDatabaseSettings() {
        dbUser = ApplicationConfig.getDbUserProduction();
        dbPassword = ApplicationConfig.getDbdbpasswordProduction();
        dbName = ApplicationConfig.getDbdatabaseNameProduction();
        dbServer = ApplicationConfig.getDbserverNameProduction();
        dbPort = ApplicationConfig.getDbportNumberProduction();
    }
}

package com.decipher.util;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public final class PlantingProfitLogger {
    private static final Map<String, Logger> loggers;

    static {
        loggers = new HashMap<String, Logger>();
    }

    private PlantingProfitLogger() {

    }

    public static void debug(Object message) {
        getLogger(getCallerClass()).debug(message);
    }

    public static void error(Throwable e) {
        getLogger(getCallerClass()).error(e.getMessage(), e);
//        e.printStackTrace();
    }

    public static void error(String message){
        getLogger(getCallerClass()).error(message);
    }

    public static void error(String message, Throwable throwable){
        getLogger(getCallerClass()).error(message, throwable);
    }

    public static void info(Object message) {
        getLogger(getCallerClass()).info(message);
    }

    public static void warn(Object message){
        getLogger(getCallerClass()).warn(message);
    }

    private static String getCallerClass() {
        return Thread.currentThread().getStackTrace()[3].getClassName();
    }

    private static Logger getLogger(String className) {
        if (!loggers.containsKey(className))
            loggers.put(className, Logger.getLogger(className));

        return loggers.get(className);
    }
}
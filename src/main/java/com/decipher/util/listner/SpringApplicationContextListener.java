package com.decipher.util.listner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * Created by abhishek on 2/4/16.
 */
public class SpringApplicationContextListener implements ApplicationListener<ContextRefreshedEvent> {

    private static ApplicationContext applicationContext;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent applicationEvent) {

        this.applicationContext = applicationEvent.getApplicationContext();

    }

    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }

}

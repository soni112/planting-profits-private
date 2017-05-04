package com.decipher.test;

import com.decipher.util.PlantingProfitLogger;
import com.itextpdf.text.DocumentException;
import org.codehaus.jettison.json.JSONException;
import org.springframework.context.support.AbstractApplicationContext;

import java.io.IOException;

/**
 * Created by Satan on 12/2/2015.
 */
public class StandaloneApplication {
    private static AbstractApplicationContext ctx;
    public static void main(String arg[]) throws DocumentException, IOException, JSONException {
        //ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

        /*ScenarioService scenarioService = ctx.getBean(ScenarioService.class);

        System.out.println("scenarioService.getAllScenarioByStrategy(17) = " + scenarioService.getAllScenarioByStrategy(17));*/

       /* FarmCustomStrategyService farmCustomStrategyService = ctx.getBean(FarmCustomStrategyService.class);

        System.out.println("farmCustomStrategyService.getFarmCustomStrategyViewList(4) = " + farmCustomStrategyService.getFarmCustomStrategyViewList(4));*/


        PlantingProfitLogger.info("hello");

    }
}

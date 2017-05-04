package com.decipher.agriculture.controller;

import com.decipher.agriculture.data.account.AppRole;
import com.decipher.agriculture.data.account.UserCountry;
import com.decipher.agriculture.service.account.AccountService;
import com.decipher.util.PlantingProfitLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by abhishek on 22/3/16.
 */
@Controller
public class UserRedirectController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private HttpSession httpSession;

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN", "ROLE_PROFESSIONAL", "ROLE_GROWER", "ROLE_STUDENT"})
    @RequestMapping(value = "/redirectUser", method = RequestMethod.GET)
    public String redirectUser(){
        httpSession.removeAttribute("growerId");
        AppRole appRole = accountService.getCurrentUser().getRole();
        PlantingProfitLogger.info("Current User Role : " + appRole);
        if(appRole.equals(AppRole.ROLE_STUDENT) || appRole.equals(AppRole.ROLE_GROWER)){
            PlantingProfitLogger.info("User is " + appRole + " now redirecting to farm.htm");
            return "redirect:/farm.htm";
        } else if(appRole.equals(AppRole.ROLE_SUPER_ADMIN) || appRole.equals(AppRole.ROLE_ADMIN) || appRole.equals(AppRole.ROLE_PROFESSIONAL)){
            PlantingProfitLogger.info("User is " + appRole + " now redirecting to management.htm");
            httpSession.removeAttribute("growerId");
            return "redirect:/management.htm";
        } else {
            return "redirect:/home.htm";
        }

    }

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN", "ROLE_PROFESSIONAL", "ROLE_GROWER"})
    @RequestMapping(value = "/management.htm", method = RequestMethod.GET)
    public ModelAndView userManagement(){

        httpSession.removeAttribute("growerId");

        Map<String, Object> model = new HashMap<String, Object>();

//        Locale[] locales = Locale.getAvailableLocales();
//        Map<String, String> countryAndCodes = new TreeMap<String, String>();
//        for (Locale locale : locales){
//            countryAndCodes.put(locale.getDisplayCountry(), locale.getCountry());
//        }
//        model.put("countryAndCodes", countryAndCodes);

        List<UserCountry> allCountriesList = accountService.getAllCountriesList();
        model.put("countryAndCodes", allCountriesList);

        model.put("currentUser", accountService.getCurrentUser());
//        model.put("userRole", accountService.getCurrentUser().getRole());

        return new ModelAndView("user-management", "model", model);
    }


    /*@Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN", "ROLE_PROFESSIONAL", "ROLE_GROWER", "ROLE_STUDENT"})
    @RequestMapping(value = "/farmInterceptor", method = RequestMethod.GET)
    public String farmInterceptor(@RequestParam(value = "growerId", required = true) String growerId){

        AppRole appRole = accountService.getCurrentUser().getRole();
        PlantingProfitLogger.info("Current User Role : " + appRole);

        if(growerId != null){
            httpSession.removeAttribute("growerId");
            httpSession.setAttribute("growerId", growerId);
        } else {
            httpSession.removeAttribute("growerId");
        }

        return "redirect:/farm.htm";


    }*/

}

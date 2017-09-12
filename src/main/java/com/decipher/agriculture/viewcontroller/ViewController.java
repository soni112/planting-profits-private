package com.decipher.agriculture.viewcontroller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.decipher.agriculture.data.account.UserCountry;
import com.decipher.config.StripeUtils;
import com.decipher.util.PlantingProfitLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.decipher.agriculture.data.account.Account;
import com.decipher.agriculture.service.account.AccountService;
import com.decipher.agriculture.service.account.impl.SessionService;
import com.decipher.util.CryptographyUtils;

@Controller
public class ViewController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private SessionService sessionService;

	@RequestMapping(value = "/home.htm", headers = "Accept=*/*")
	public ModelAndView homePage(@RequestParam(value = "err", required = false) String error,
			@RequestParam(value = "success", required = false) boolean success) {
		Map<String, Object> model = new HashMap<String, Object>();
		    if(error != null){
				model.put("error", true);	
			}else{
				model.put("error", false);
			}
		    if(success)
		    {
		    	model.put("success", true);
		    }

		/**
		 * @changed - Abhishek
		 * @date - 08-12-2015
		 */
//		Locale[] locales = Locale.getAvailableLocales();
//		Map<String, String> countryAndCodes = new TreeMap<String, String>();
//		for (Locale locale : locales){
//			countryAndCodes.put(locale.getDisplayCountry(), locale.getCountry());
//		}
//		model.put("countryAndCodes", countryAndCodes);

		List<UserCountry> allCountriesList = accountService.getAllCountriesList();
		model.put("countryAndCodes", allCountriesList);

		PlantingProfitLogger.info("User requesting for home.htm page ....");
		return new ModelAndView("home", "model", model);
	}

	@RequestMapping("/login.htm")
	public ModelAndView loginPage(@RequestParam(value = "err", required = false) String error,
			HttpServletRequest request) {
		PlantingProfitLogger.info("User requesting for login.htm page ....");
		PlantingProfitLogger.info("Error:" + error);
		Map<String, Object> model = new HashMap<String, Object>();
		PlantingProfitLogger.info("inside User login  ....");
		if (error != null) {
			model.put("error", true);
		} else {
			model.put("error", false);
		}
		List<UserCountry> allCountriesList = accountService.getAllCountriesList();

		model.put("countryAndCodes", allCountriesList);
		return new ModelAndView("home", "model", model);
	}

	@RequestMapping("/access-denied.htm")
	public ModelAndView accessdenied(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		PlantingProfitLogger.info("inside accessdenied  ....");
		return new ModelAndView("home", "model", model);

	}

	@RequestMapping(value = "/change-password.htm", headers = "Accept=*/*")
	public ModelAndView changePasswordPage() {
		Map<String, Object> model = new HashMap<String, Object>();
		PlantingProfitLogger.info("User requesting for home.htm page ....");
		model.put("userName", sessionService.getUserName());
		List<UserCountry> allCountriesList = accountService.getAllCountriesList();

		model.put("countryAndCodes", allCountriesList);
		return new ModelAndView("change-password", "model", model);
	}

	@RequestMapping(value = "/accountRecovery.htm", headers = "Accept=*/*")
	public ModelAndView accountRecoveryPage(@RequestParam String uid) {
		PlantingProfitLogger.info("request for accountRecovery.htm   uid  : " + uid);
		Map<String, Object> myModel = new HashMap<String, Object>();
		String decodedEmailId = CryptographyUtils.decryptData(uid);
		PlantingProfitLogger.info("decodedEmailId : " + decodedEmailId);
		Account user = accountService.getEnabledUserByEmail(decodedEmailId);
		List<UserCountry> allCountriesList = accountService.getAllCountriesList();

		myModel.put("countryAndCodes", allCountriesList);
		if (user != null) {
			myModel.put("userEmailAddress", decodedEmailId);
			myModel.put("userEncodeduserEmailAddress", uid);
			return new ModelAndView("home", "model", myModel);
		} else {
			return new ModelAndView("home", "model", myModel);
		}

	}

	@RequestMapping(value = "/verifyAccount.htm", headers = "Accept=*/*")
	public String verifyAccountPage(@RequestParam String uid) {
		PlantingProfitLogger.info("request for verifyAccountPage.htm   uid  : " + uid);
		Map<String, Object> myModel = new HashMap<String, Object>();
		String decodedEmailId = CryptographyUtils.decryptData(uid);
		PlantingProfitLogger.info("decodedEmailId : " + decodedEmailId);
		Account user = accountService.getUserByEmail(decodedEmailId);
		if (user != null) {
			/*
			 * @changed - Abhishek
			 * @date - 02-04-2016
			 * @desc - for enable and disable feature of account
			 */
			user.setEnabled(true);
			accountService.UpdateUser(user);

			return "redirect:/home.htm?accountVerified=true";
		} else {
			return "redirect:/home.htm?accountVerified=false";
		}

	}

	@RequestMapping(value = "/privacy-policy.htm", method = {RequestMethod.GET})
	public ModelAndView getPrivacyPolicyPage(){
		ModelAndView modelAndView = new ModelAndView("privacy-policy");
		modelAndView.addObject("stripePublishKey", StripeUtils.getStripePaymentPublishKey());
		return modelAndView;
	}

	@RequestMapping(value = "/license-agreement.htm", method = {RequestMethod.GET})
	public String getLicenseAgreementPage(){

		return "license-agreement";
	}

	@RequestMapping(value = "/learning-center.htm", method = {RequestMethod.GET})
	public ModelAndView getLearningCenter(){
		ModelAndView modelAndView = new ModelAndView("learning-center");
		modelAndView.addObject("stripePublishKey", StripeUtils.getStripePaymentPublishKey());
		return modelAndView;
	}

	@RequestMapping(value = "/consultant-corner.htm", method = {RequestMethod.GET})
	public ModelAndView getConsultantCorner(){
		ModelAndView modelAndView = new ModelAndView("consultant-corner");
		modelAndView.addObject("stripePublishKey", StripeUtils.getStripePaymentPublishKey());
		return modelAndView;
	}

	@RequestMapping(value = "/farm-data.htm", method = {RequestMethod.GET})
	public ModelAndView getFarmData(){
		ModelAndView modelAndView = new ModelAndView("farm-data");
		modelAndView.addObject("stripePublishKey", StripeUtils.getStripePaymentPublishKey());
		return modelAndView;
	}

	@RequestMapping(value = "/contact.htm", method = {RequestMethod.GET})
	public ModelAndView getContact(){
		ModelAndView modelAndView = new ModelAndView("contact");
		modelAndView.addObject("stripePublishKey", StripeUtils.getStripePaymentPublishKey());
		return modelAndView;
	}

	@RequestMapping(value = "/error.htm", method = {RequestMethod.GET})
	public ModelAndView getErrorpage(){
		ModelAndView modelAndView = new ModelAndView("error");
		modelAndView.addObject("stripePublishKey", StripeUtils.getStripePaymentPublishKey());
		return modelAndView;
	}

	@RequestMapping(value = "/report-issue.htm", method = {RequestMethod.GET})
	public ModelAndView getReview(){
		ModelAndView modelAndView = new ModelAndView("report-issue");

		List<UserCountry> allCountriesList = accountService.getAllCountriesList();
		modelAndView.addObject("countryAndCodes", allCountriesList);

		return modelAndView;
	}

}

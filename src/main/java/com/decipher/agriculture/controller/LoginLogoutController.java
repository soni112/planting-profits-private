package com.decipher.agriculture.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.decipher.util.PlantingProfitLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.decipher.agriculture.service.account.AccountService;

/**
 * @author Manoj
 *
 */
@Controller
@RequestMapping("/authentication")
public class LoginLogoutController {

	 @Autowired
	 private AccountService accountService;
	 
	 
	/**
	 * Handles and retrieves the login JSP page
	 * 
	 * @return the name of the JSP page
	 */
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String getLoginPage(@RequestParam(value = "error", required = false) boolean error,
			HttpServletRequest request, ModelMap model) {
		PlantingProfitLogger.info("Inside Login and error:" + error);
		// checking the call is a Ajax request or normal request ...
		if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
			PlantingProfitLogger.info("THis is an ajax lgoin request ...");
			return "redirect:/asa/auth/ajaxInvalidLogin";
		} else {
		 
			// this is a URL based login // non ajax login request
			  if (error) {
				 
					
				PlantingProfitLogger.info("Received request to show login page true login");
				return "redirect:/home.htm?err='true'";
			} else {
				PlantingProfitLogger.info("Received request to show login page false ");
				return "redirect:/home.htm";

			}
		}

	}

	@RequestMapping(value = "/ajaxValidLogin", headers = "Accept=*/*")
	public @ResponseBody String ajaxValidLogin(@RequestParam(value = "error", required = false) boolean error,
			HttpServletRequest request, ModelMap model) {
		PlantingProfitLogger.info("Inside ajaxValidLogin:" );
		// return valid if ajax login request is success
		return "valid";
	}

	@RequestMapping(value = "/ajaxInvalidLogin", headers = "Accept=*/*")
	public @ResponseBody String ajaxInvalidLogin(@RequestParam(value = "error", required = false) boolean error,
			HttpServletRequest request, ModelMap model) {
		PlantingProfitLogger.info("Inside ajaxInvalidLogin:" );
		// return in-valid if ajax login request is failed
		if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With")))
		{
			return "invalid";
		}
		else
		{
			return "<script type='text/javascript'>window.location.replace('home.htm');</script>";
			
		}
		
	}

	/**
	 * Handles and retrieves the login JSP page
	 * 
	 * @return the json Object
	 */
	@RequestMapping(value = "/home", method = RequestMethod.POST)
	public @ResponseBody String getLoginPagePost(@RequestParam(value = "error", required = false) boolean error,
			HttpServletRequest request, ModelMap model) {
		PlantingProfitLogger.info("Received request to show login page Post ");

 		if ("true".equals(request.getHeader("X-Ajax-call"))) {
 			PlantingProfitLogger.info("Ajax Call True ..");
			return "Ok";

		} else {
			 if (error) {
				PlantingProfitLogger.info("Received request to show login page true ");
				return "redirect:/home.htm?err='true'";
			 
			} else {
				PlantingProfitLogger.info("Received request to show login page false ");
				return "redirect:/home.htm";
				 
			}

		}

	}

	/**
	 * Handle request if user loged in the application
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/logedin", method = RequestMethod.GET)
	public String getLogedinPage(HttpServletRequest request,HttpSession session) {

		 
		PlantingProfitLogger.info("User Successfully loged in ");
//		SecurityUtil.changeloggedInUserRole(AppRole.ROLE_USER);
		// if Login Success and request is ajax request ...
		if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
			PlantingProfitLogger.info("Ajax call true ..");
	 	 
			return "redirect:/asa/auth/ajaxValidLogin";

		} else {
			PlantingProfitLogger.info("SecurityContextHolder.getInitializeCount() : " + SecurityContextHolder.getInitializeCount());
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();

			String userRole=auth.getAuthorities().toString();
			PlantingProfitLogger.info("userRole : " + userRole);
//			Account account = accountService.getCurrentUser();

			 if(userRole.equals("[ROLE_SUPER_ADMIN]") ||
					 userRole.equals("[ROLE_ADMIN]") ||
					 userRole.equals("[ROLE_PROFESSIONAL]") ||
					 userRole.equals("[ROLE_GROWER]") ||
					 userRole.equals("[ROLE_STUDENT]")){
//				return "redirect:/home.htm?success=true";
				return "redirect:/farm.htm";
			}
			else  {
				return "redirect:/home.htm";	
			}
		}
	}

	/**
	 * Handles and retrieves the denied JSP page. This is shown whenever a
	 * regular user tries to access an admin only page.
	 * 
	 * @return the name of the JSP page
	 */
	@RequestMapping(value = "/denied", method = RequestMethod.GET)
	public   String getDeniedPage() {
		PlantingProfitLogger.info("Received request to show denied page");
		return "redirect:/access-denied.htm";
	}

}
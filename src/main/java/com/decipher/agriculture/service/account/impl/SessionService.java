package com.decipher.agriculture.service.account.impl;

import com.decipher.agriculture.service.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.decipher.agriculture.data.account.Account;
import com.decipher.util.PlantingProfitLogger;

import javax.servlet.http.HttpSession;

@Component
@Scope("session")
public class SessionService {
	@Autowired
	private AccountService accountService;
	@Autowired
	private HttpSession httpSession;

	
	private Account loggedInUser;

	public Account getLoggedInUser() {

		final String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
		PlantingProfitLogger.info("currentUserName--->>>" + currentUserName);

		if (currentUserName == null || currentUserName.trim().equals("") || currentUserName.trim().equals("anonymousUser")) {
			return null;
		}
//		if (loggedInUser == null) {
			/**
			 * @changed - Abhishek
			 * @date - 29-03-2016
			 * @desc - for getting user if super user requesting for grower data
			 */
			loggedInUser = accountService.getCurrentUser();
			/*Account currentUser = accountService.getCurrentUser();

			Object growerId = httpSession.getAttribute("growerId");
			if(growerId != null && Integer.parseInt(growerId.toString()) != currentUser.getId()){

				loggedInUser = accountService.getUserById(Integer.parseInt(growerId.toString()));

			} else {
				loggedInUser = currentUser;
			}*/


//		}
		return loggedInUser;
	}

	public void setLoggedInUser(Account user) {
		this.loggedInUser = user;

	}

	public String getUserName() {
		Account loggedInUser = getLoggedInUser();
		String userName;
		if (loggedInUser != null) {
			userName = loggedInUser.getFirstName();
		} else {
			userName = "Anonymous User";
		}
		return userName;
	}
	public int getUserId() {
		Account loggedInUser = getLoggedInUser();
		int userId;
		if (loggedInUser != null) {
			userId = loggedInUser.getId();
		} else {
			userId = 0;
		}
		return userId;
	}

}
package com.decipher.security;

import com.decipher.util.PlantingProfitLogger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.decipher.agriculture.data.account.Account;
import com.decipher.agriculture.data.account.AppRole;

public class SecurityUtil {
	

	public static void logInUser(Account account, AppRole appRole) {
		PlantingProfitLogger.info("Logging in user: {}" + account.getId());
		CustomUserDetails userDetails = CustomUserDetails.getBuilder()
				.id(account.getId()).name(account.getFirstName())
				.password(account.getPassword()).role(appRole)
				.username(account.getEmail_Address()).build();
		PlantingProfitLogger.info("Logging in principal: {}" + userDetails);

		Authentication authentication = new UsernamePasswordAuthenticationToken(
				userDetails, null, userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);

		PlantingProfitLogger.info("User: {} has been logged in." + userDetails);
	}

	public static void changeloggedInUserRole(AppRole appRole) {
		PlantingProfitLogger.info("changeloggedInUserRole in user role : {}" + appRole.getBit());
		CustomUserDetails user = (CustomUserDetails) SecurityContextHolder .getContext().getAuthentication().getPrincipal();
		CustomUserDetails userDetails = CustomUserDetails.getBuilder()
				.id(user.getId()).name(user.getName())
				.password(user.getPassword())
				.role(appRole)
				.username(user.getUsername())
				.build();
		PlantingProfitLogger.info("Logging in principal: {}" + userDetails);

		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		PlantingProfitLogger.info("User: {} has been logged in." + userDetails);
	}
	
	public static void changeloggedInUserName(String username) {
		PlantingProfitLogger.info(" In changeloggedInUserName with name : {}"
				+ username);
		CustomUserDetails user = (CustomUserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		CustomUserDetails userDetails = CustomUserDetails.getBuilder()
				.id(user.getId()).name(username)
				.password(user.getPassword()).role(user.getRole())
				.username(user.getUsername()).build();
		PlantingProfitLogger.info("In changeloggedInUserName Logging in principal: {}" + userDetails);

		Authentication authentication = new UsernamePasswordAuthenticationToken(
				userDetails, null, userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		PlantingProfitLogger.info("User: {} has been logged in." + userDetails);
	}
}

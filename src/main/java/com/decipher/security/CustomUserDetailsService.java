package com.decipher.security;

import com.decipher.agriculture.data.account.Account;
import com.decipher.util.PlantingProfitLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.dao.account.AccountDao;
import com.decipher.agriculture.data.account.AppRole;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A custom service for retrieving users from a custom datasource, such as a
 * database.
 * <p>
 * This custom service must implement Spring's {@link UserDetailsService}
 */
@Transactional(readOnly = true)
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	AccountDao accountDAO;
	
	/**
	 * Retrieves a user record containing the user's credentials and access.
	 */
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		PlantingProfitLogger.info("Inside loadUserByUsername ...........");

		Account dbUser = accountDAO.getEnabledUserByEmail(username);

		// Search database for a user that matches the specified username
		// You can provide a custom DAO to access your persistence layer
		// Or use JDBC to access your database
		// DbUser is our custom domain user. This is not the same as
		// Spring's User

		// Populate the Spring User object with details from the dbUser
		// Here we just pass the username, password, and access level
		// getAuthorities() will translate the access level to the correct
		// role type
		if (dbUser == null) {
			throw new UsernameNotFoundException("No user found with username: " + username);
		}
		PlantingProfitLogger.info("User :-->>> " + dbUser.getEmail_Address() + " : ");

		CustomUserDetails principal = CustomUserDetails.getBuilder()
				.id(dbUser.getId())
				.name(dbUser.getFirstName())
				.password(dbUser.getPassword())
				.role(dbUser.getRole())
				.username(dbUser.getEmail_Address())
				.enabled(dbUser.getEnabled())
				.build();
		// Return user to Spring for processing.
		// Take note we're not the one evaluating whether this user is
		// authenticated or valid
		// We just merely retrieve a user that matches the specified username
		return principal;

//		return buildUserForAuthentication(dbUser, buildUserGrantedAuthority(dbUser.getRole()));

	}

	private User buildUserForAuthentication(Account user, List<GrantedAuthority> authorities)
	{
		PlantingProfitLogger.debug("Email ID : "+user.getEmail_Address()+"\tAccount Status : "+user.getEnabled());
		return new User(user.getEmail_Address(), user.getPassword(), user.getEnabled(), true, true, true, authorities);
	}

	private List<GrantedAuthority> buildUserGrantedAuthority(AppRole roles) {
		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

		// Build user's authorities
		//for (AccountRole accountRole : roles) {
		setAuths.add(roles);
		//}

		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);

		return Result;
	}



}
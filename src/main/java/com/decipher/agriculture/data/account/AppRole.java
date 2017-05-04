package com.decipher.agriculture.data.account;

import org.springframework.security.core.GrantedAuthority;

/**
 * @changed - Abhishek
 * @date - 22-03-2016
 * @desc - Added new roles for the application
 */
public enum AppRole implements GrantedAuthority {

	ROLE_SUPER_ADMIN(0),
	ROLE_ADMIN(1),
	ROLE_PROFESSIONAL(2),
	ROLE_GROWER(3),
	ROLE_STUDENT(4);

	private final Integer role;

	AppRole(Integer role) {
		this.role = role;
	}

	public Integer getBit() {
		return role;
	}

	public String getRole() {
		return toString();
	}

	public String getAuthority() {
		return toString();
	}
}
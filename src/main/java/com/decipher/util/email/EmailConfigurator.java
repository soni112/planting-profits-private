package com.decipher.util.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Property reader. Expects config.properties located in WEB-INF.
 *
 */
@Component("emailConfigurator")
public class EmailConfigurator {

	private @Value("${email_from}") String emailFrom;
	private @Value("${email_host}") String emailHost;
	private @Value("${email_userid}") String emailUserid;
	private @Value("${email_password}") String emailPassword;

	public String getEmailFrom() {
		return emailFrom;
	}

	public String getEmailHost() {
		return emailHost;
	}

	public String getEmailUserId() {
		return emailUserid;
	}

	public String getEmailPassword() {

		return emailPassword;
	}
}
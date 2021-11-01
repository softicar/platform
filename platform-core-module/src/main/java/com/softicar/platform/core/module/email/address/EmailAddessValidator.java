package com.softicar.platform.core.module.email.address;

import java.util.regex.Pattern;

/**
 * Functions to send e-mails
 *
 * @author Thees Koester
 */
public class EmailAddessValidator {

	private static final Pattern PATTERN = Pattern.compile("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
	private final String emailAddress;

	public EmailAddessValidator(String emailAddress) {

		this.emailAddress = emailAddress;
	}

	public boolean isValid() {

		if (emailAddress == null) {
			return false;
		}

		return PATTERN.matcher(emailAddress).matches();
	}
}

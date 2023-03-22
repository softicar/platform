package com.softicar.platform.core.module.file.smb;

import java.util.Optional;

/**
 * Holds the credentials used for {@link ISmbClient} methods.
 *
 * @author Daniel Klose
 */
public class SmbCredentials {

	private final String domain;
	private final String username;
	private final String password;

	public SmbCredentials(String domain, String username, String password) {

		this.domain = Optional.ofNullable(domain).orElse("");
		this.username = Optional.ofNullable(username).orElse("");
		this.password = Optional.ofNullable(password).orElse("");
	}

	public String getDomain() {

		return domain;
	}

	public String getUsername() {

		return username;
	}

	public String getPassword() {

		return password;
	}
}

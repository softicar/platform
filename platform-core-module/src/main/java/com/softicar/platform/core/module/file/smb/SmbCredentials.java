package com.softicar.platform.core.module.file.smb;

import java.util.Objects;

/**
 * This is a container class to hold the credentials used for {@link ISmbClient}
 * methods.
 *
 * @author Daniel Klose
 */
public class SmbCredentials {

	private final String domain;
	private final String username;
	private final String password;

	public SmbCredentials(String domain, String username, String password) {

		this.domain = Objects.requireNonNull(domain);
		this.username = Objects.requireNonNull(username);
		this.password = Objects.requireNonNull(password);
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

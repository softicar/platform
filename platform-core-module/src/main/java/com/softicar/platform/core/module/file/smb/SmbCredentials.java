package com.softicar.platform.core.module.file.smb;

public class SmbCredentials implements ISmbCredentials {

	private final String domain;
	private final String username;
	private final String password;

	public SmbCredentials(String domain, String username, String password) {

		this.domain = domain;
		this.username = username;
		this.password = password;
	}

	@Override
	public String getDomain() {

		return domain;
	}

	@Override
	public String getUsername() {

		return username;
	}

	@Override
	public String getPassword() {

		return password;
	}
}

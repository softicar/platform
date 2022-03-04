package com.softicar.platform.core.module.file.smb.testing;

public class SmbTestServerConfiguration {

	private final String testShare;
	private final String testUser;
	private final String testPassword;

	public SmbTestServerConfiguration(String testShare, String testUser, String testPassword) {

		this.testShare = testShare;
		this.testUser = testUser;
		this.testPassword = testPassword;
	}

	public String getTestShare() {

		return testShare;
	}

	public String getTestUser() {

		return testUser;
	}

	public String getTestPassword() {

		return testPassword;
	}
}

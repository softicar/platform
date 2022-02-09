package com.softicar.platform.core.module.file.smb.testing;

public class SmbTestServerConfiguration {

	private final String testPassword;
	private final String testShare;
	private final String testUser;

	public SmbTestServerConfiguration(String testPassword, String testShare, String testUser) {

		this.testPassword = testPassword;
		this.testShare = testShare;
		this.testUser = testUser;
	}

	public String getTestPassword() {

		return testPassword;
	}

	public String getTestShare() {

		return testShare;
	}

	public String getTestUser() {

		return testUser;
	}
}

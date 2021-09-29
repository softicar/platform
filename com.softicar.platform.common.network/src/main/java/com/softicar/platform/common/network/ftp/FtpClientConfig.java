package com.softicar.platform.common.network.ftp;

public class FtpClientConfig implements IFtpClientConfig {

	private final String hostname;
	private final String username;
	private final String password;
	private final boolean passiveMode;

	public FtpClientConfig(String hostname, String username, String password, boolean passiveMode) {

		this.hostname = hostname;
		this.username = username;
		this.password = password;
		this.passiveMode = passiveMode;
	}

	@Override
	public String getHostname() {

		return hostname;
	}

	@Override
	public String getUsername() {

		return username;
	}

	@Override
	public String getPassword() {

		return password;
	}

	@Override
	public boolean isPassiveMode() {

		return passiveMode;
	}
}

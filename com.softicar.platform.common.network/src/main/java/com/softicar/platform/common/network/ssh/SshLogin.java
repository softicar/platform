package com.softicar.platform.common.network.ssh;

public class SshLogin implements ISshLogin {

	private static final int DEFAULT_PORT = 22;
	private final String username;
	private final String host;
	private final int port;

	public SshLogin(String username, String host) {

		this(username, host, DEFAULT_PORT);
	}

	public SshLogin(String username, String host, int port) {

		this.username = username;
		this.host = host;
		this.port = port;
	}

	@Override
	public String getSshUser() {

		return username;
	}

	@Override
	public String getIpAddress() {

		return host;
	}

	@Override
	public Integer getSshPort() {

		return port;
	}
}

package com.softicar.platform.common.network.ssh;

/**
 * A builder to create instances of {@link ISshLocalPortForward}.
 *
 * @author Oliver Richers
 */
public class SshLocalPortForwardBuilder implements ISshLocalPortForwardConfiguration {

	private static final int DEFAULT_SSH_PORT = 22;
	private static final int DEFAULT_TIMEOUT = 30000;
	private String sshUser;
	private String sshHost;
	private int sshPort;
	private int localPort;
	private String remoteHost;
	private int remotePort;
	private int timeout;

	/**
	 * Creates a new builder.
	 * <p>
	 * By default, the SSH port will be 22 and the local port will be 0,
	 * resulting in automatic port allocation. All other attributes must be
	 * defined.
	 */
	public SshLocalPortForwardBuilder() {

		this.sshPort = DEFAULT_SSH_PORT;
		this.localPort = 0;
		this.timeout = DEFAULT_TIMEOUT;
	}

	public SshLocalPortForwardBuilder setSshUser(String sshUser) {

		this.sshUser = sshUser;
		return this;
	}

	public SshLocalPortForwardBuilder setSshHost(String sshHost) {

		this.sshHost = sshHost;
		return this;
	}

	/**
	 * Defines the SSH port to connect to.
	 * <p>
	 * Calling this method is optional, as the default is 22.
	 *
	 * @param sshPort
	 *            the ssh port
	 * @return this builder
	 */
	public SshLocalPortForwardBuilder setSshPort(int sshPort) {

		this.sshPort = sshPort;
		return this;
	}

	/**
	 * Defines the local port to use for the forward.
	 * <p>
	 * Calling this method is optional, as the default is 0, resulting in
	 * automatic allocation of a random empty port. You can get the allocated
	 * port number by calling {@link ISshLocalPortForward#getLocalPort()}.
	 *
	 * @param localPort
	 *            the local port
	 * @return this builder
	 */
	public SshLocalPortForwardBuilder setLocalPort(int localPort) {

		this.localPort = localPort;
		return this;
	}

	public SshLocalPortForwardBuilder setRemoteHost(String remoteHost) {

		this.remoteHost = remoteHost;
		return this;
	}

	public SshLocalPortForwardBuilder setRemotePort(int remotePort) {

		this.remotePort = remotePort;
		return this;
	}

	/**
	 * Defines the connection timeout in milliseconds.
	 *
	 * @param timeout
	 *            the timeout in milliseconds (0 for infinite)
	 */
	public SshLocalPortForwardBuilder setTimeout(int timeout) {

		this.timeout = timeout;
		return this;
	}

	@Override
	public String getSshUser() {

		return sshUser;
	}

	@Override
	public String getSshHost() {

		return sshHost;
	}

	@Override
	public int getSshPort() {

		return sshPort;
	}

	@Override
	public int getLocalPort() {

		return localPort;
	}

	@Override
	public String getRemoteHost() {

		return remoteHost;
	}

	@Override
	public int getRemotePort() {

		return remotePort;
	}

	@Override
	public int getTimeout() {

		return timeout;
	}

	public ISshLocalPortForward build() {

		if (sshUser == null) {
			throw new IllegalStateException("No SSH user defined.");
		}
		if (sshHost == null) {
			throw new IllegalStateException("No SSH host defined.");
		}
		if (remoteHost == null) {
			throw new IllegalStateException("No remote host defined.");
		}
		if (remotePort == 0) {
			throw new IllegalStateException("No remote port defined.");
		}

		return new SshLocalPortForward(this);
	}
}

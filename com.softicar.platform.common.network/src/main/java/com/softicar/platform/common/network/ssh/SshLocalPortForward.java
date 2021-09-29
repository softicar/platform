package com.softicar.platform.common.network.ssh;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.softicar.platform.common.network.sftp.JschRuntimeException;

class SshLocalPortForward implements ISshLocalPortForward {

	private final ISshLocalPortForwardConfiguration configuration;
	private int localPort;
	private Session session;

	public SshLocalPortForward(ISshLocalPortForwardConfiguration configuration) {

		this.configuration = configuration;

		try {
			JSch jsch = new JSch();
			JschUtils.addPublicKeyIdentity(jsch);

			this.session = jsch
				.getSession(//
					configuration.getSshUser(),
					configuration.getSshHost(),
					configuration.getSshPort());
			this.session.setConfig("StrictHostKeyChecking", "no");
			this.session.connect(configuration.getTimeout());
			this.localPort = session
				.setPortForwardingL(//
					configuration.getLocalPort(),
					configuration.getRemoteHost(),
					configuration.getRemotePort());
		} catch (JSchException exception) {
			throw new JschRuntimeException(
				exception,
				"Failed to create local port forward %s:%s:%s with %s@%s:%s",
				configuration.getLocalPort(),
				configuration.getRemoteHost(),
				configuration.getRemotePort(),
				configuration.getSshUser(),
				configuration.getSshHost(),
				configuration.getSshPort());
		}
	}

	@Override
	public ISshLocalPortForwardConfiguration getConfiguration() {

		return configuration;
	}

	@Override
	public int getLocalPort() {

		return localPort;
	}

	@Override
	public void close() {

		session.disconnect();
	}
}

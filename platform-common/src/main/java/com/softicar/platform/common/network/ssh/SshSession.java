package com.softicar.platform.common.network.ssh;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.softicar.platform.common.network.sftp.JschRuntimeException;

public class SshSession implements AutoCloseable {

	private final Session session;

	public SshSession(String hostname, String username) {

		JSch jsch = new JSch();
		JschUtils.addPublicKeyIdentity(jsch);

		try {
			this.session = jsch.getSession(username, hostname, 22);
			this.session.setConfig("StrictHostKeyChecking", "no");
			this.session.connect(10 * 1000);
		} catch (JSchException exception) {
			throw new JschRuntimeException(exception);
		}
	}

	@Override
	public void close() {

		session.disconnect();
	}

	public SshCommandExecution startCommand(String command) {

		return new SshCommandExecution(session, command);
	}
}

package com.softicar.platform.common.network.ssh;

/**
 * Interface of a local port forward using SSH.
 * <p>
 * A local port forward allocates a local port which is then forwarded through
 * an SSH tunnel to another port on a remote host. For more information about
 * this concept, see the manual for the SSH command line client, concerning the
 * <i>-L</i> option.
 *
 * @author Oliver Richers
 */
public interface ISshLocalPortForward extends AutoCloseable {

	ISshLocalPortForwardConfiguration getConfiguration();

	int getLocalPort();

	@Override
	void close();
}

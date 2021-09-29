package com.softicar.platform.common.network.ssh;

/**
 * Configuration of an instance of {@link ISshLocalPortForward}.
 *
 * @author Oliver Richers
 */
public interface ISshLocalPortForwardConfiguration {

	String getSshUser();

	String getSshHost();

	int getSshPort();

	int getLocalPort();

	String getRemoteHost();

	int getRemotePort();

	int getTimeout();
}

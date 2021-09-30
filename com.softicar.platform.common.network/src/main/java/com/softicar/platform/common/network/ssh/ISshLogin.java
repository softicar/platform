package com.softicar.platform.common.network.ssh;

/**
 * Defines an SSH login.
 * 
 * @author Oliver Richers
 */
public interface ISshLogin {

	String getSshUser();

	String getIpAddress();

	Integer getSshPort();
}

package com.softicar.platform.common.network.ftp;

import java.time.Duration;
import java.util.Optional;
import org.apache.commons.net.ftp.FTPClient;

public interface IFtpClientConfig {

	String getHostname();

	String getUsername();

	String getPassword();

	boolean isPassiveMode();

	// ---------------- timeouts ---------------- //

	/**
	 * If present, the value is applied to
	 * {@link FTPClient#setDefaultTimeout(int)}
	 */
	default Optional<Integer> getDefaultTimeout() {

		return Optional.empty();
	}

	/**
	 * If present, the value is applied to {@link FTPClient#setSoTimeout(int)}
	 */
	default Optional<Integer> getSoTimeout() {

		return Optional.empty();
	}

	/**
	 * If present, the value is applied to
	 * {@link FTPClient#setConnectTimeout(int)}
	 */
	default Optional<Integer> getConnectTimeout() {

		return Optional.empty();
	}

	/**
	 * If present, the value is applied to
	 * {@link FTPClient#setDataTimeout(Duration)}
	 */
	default Optional<Duration> getDataTimeout() {

		return Optional.empty();
	}

	/**
	 * If present, the value is applied to
	 * {@link FTPClient#setControlKeepAliveTimeout(Duration)}
	 */
	default Optional<Duration> getControlKeepAliveTimeout() {

		return Optional.empty();
	}

	/**
	 * If present, the value is applied to
	 * {@link FTPClient#setControlKeepAliveReplyTimeout(Duration)}
	 */
	default Optional<Duration> getControlKeepAliveReplyTimeout() {

		return Optional.empty();
	}
}

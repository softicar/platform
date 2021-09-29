package com.softicar.platform.ajax.testing.selenium.grid.configuration.hub;

import com.softicar.platform.common.core.properties.IProperty;

public class SeleniumHubConfiguration implements ISeleniumHubConfiguration {

	private Integer browserTimeout;
	private Integer sessionTimeout;
	private Integer maximumSessionCount;
	private String ip;
	private Integer port;

	@Override
	public Integer getBrowserTimeout() {

		return browserTimeout;
	}

	public SeleniumHubConfiguration setBrowserTimeout(Integer browserTimeout) {

		this.browserTimeout = browserTimeout;
		return this;
	}

	public SeleniumHubConfiguration setBrowserTimeout(IProperty<Integer> browserTimeout) {

		return setBrowserTimeout(browserTimeout.getValue());
	}

	@Override
	public Integer getSessionTimeout() {

		return sessionTimeout;
	}

	public SeleniumHubConfiguration setSessionTimeout(Integer sessionTimeout) {

		this.sessionTimeout = sessionTimeout;
		return this;
	}

	public SeleniumHubConfiguration setSessionTimeout(IProperty<Integer> sessionTimeout) {

		return setSessionTimeout(sessionTimeout.getValue());
	}

	@Override
	public Integer getMaximumSessionCount() {

		return maximumSessionCount;
	}

	public SeleniumHubConfiguration setMaximumSessionCount(Integer maximumSessionCount) {

		this.maximumSessionCount = maximumSessionCount;
		return this;
	}

	public SeleniumHubConfiguration setMaximumSessionCount(IProperty<Integer> maximumSessionCount) {

		return setMaximumSessionCount(maximumSessionCount.getValue());
	}

	@Override
	public String getIp() {

		return ip;
	}

	public SeleniumHubConfiguration setIp(String ip) {

		this.ip = ip;
		return this;
	}

	public SeleniumHubConfiguration setIp(IProperty<String> ip) {

		return setIp(ip.getValue());
	}

	@Override
	public Integer getPort() {

		return port;
	}

	public SeleniumHubConfiguration setPort(Integer port) {

		this.port = port;
		return this;
	}

	public SeleniumHubConfiguration setPort(IProperty<Integer> port) {

		return setPort(port.getValue());
	}
}

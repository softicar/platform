package com.softicar.platform.ajax.server.standalone;

import java.util.Objects;

public class StandAloneServletServerConfiguration {

	public static final int DEFAULT_PORT = 0;
	public static final String DEFAULT_CONTEXT_NAME = "context";
	public static final String DEFAULT_REQUEST_STRING = "";

	private int port;
	private String contextName;
	private String requestString;

	public StandAloneServletServerConfiguration() {

		this.port = DEFAULT_PORT;
		this.contextName = DEFAULT_CONTEXT_NAME;
		this.requestString = DEFAULT_REQUEST_STRING;
	}

	public StandAloneServletServerConfiguration setPort(int port) {

		this.port = port;
		return this;
	}

	public int getPort() {

		return port;
	}

	public StandAloneServletServerConfiguration setContextName(String contextName) {

		this.contextName = Objects.requireNonNull(contextName);
		return this;
	}

	public String getContextName() {

		return contextName;
	}

	public StandAloneServletServerConfiguration setRequestString(String requestString) {

		this.requestString = Objects.requireNonNull(requestString);
		return this;
	}

	public String getRequestString() {

		return requestString;
	}
}

package com.softicar.platform.common.servlet.server;

import com.softicar.platform.common.core.exception.CheckedExceptions;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHolder;

public class HttpServletServerHandle implements AutoCloseable {

	private final Server server;
	private final ServerConnector connector;
	private final ServletHolder servletHolder;

	public HttpServletServerHandle(Server server, ServerConnector connector, ServletHolder servletHolder) {

		this.server = server;
		this.connector = connector;
		this.servletHolder = servletHolder;
	}

	public Server getServer() {

		return server;
	}

	public ServerConnector getConnector() {

		return connector;
	}

	public ServletHolder getServletHolder() {

		return servletHolder;
	}

	/**
	 * Joins the server {@link Thread}.
	 * <p>
	 * This method will only return when the server {@link Thread} terminated.
	 */
	public void join() {

		CheckedExceptions.wrap(() -> server.join());
	}

	@Override
	public void close() {

		connector.close();
		CheckedExceptions.wrap(() -> server.stop());
	}
}

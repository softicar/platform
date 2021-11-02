package com.softicar.platform.ajax.server.standalone;

import com.softicar.platform.common.core.exception.CheckedExceptions;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;

public class StandAloneServletServerHandle implements AutoCloseable {

	private final Server server;
	private final ServerConnector connector;

	public StandAloneServletServerHandle(Server server, ServerConnector connector) {

		this.server = server;
		this.connector = connector;
	}

	public Server getServer() {

		return server;
	}

	public ServerConnector getConnector() {

		return connector;
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

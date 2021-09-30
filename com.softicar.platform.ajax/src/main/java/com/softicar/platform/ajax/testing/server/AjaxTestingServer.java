package com.softicar.platform.ajax.testing.server;

import com.softicar.platform.ajax.testing.AjaxTestingServlet;
import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.core.threading.InterruptedRuntimeException;
import java.util.Optional;
import javax.servlet.ServletException;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.session.DefaultSessionIdManager;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class AjaxTestingServer implements AutoCloseable {

	private final Optional<IAjaxTestingServerEnvironment> environment;
	private final Server server;
	private final ServerConnector connector;
	private final ServletHolder servletHolder;
	private Integer localPort;

	public AjaxTestingServer() {

		this(null);
	}

	public AjaxTestingServer(IAjaxTestingServerEnvironment environment) {

		this.environment = Optional.ofNullable(environment);
		this.server = new Server();
		this.connector = new ServerConnector(server);
		this.servletHolder = new ServletHolder(new AjaxTestingServlet());
		this.localPort = null;
	}

	public AjaxTestingServer setPort(int port) {

		this.connector.setPort(port);
		return this;
	}

	public int getLocalPort() {

		if (localPort != null) {
			return localPort;
		} else {
			throw new IllegalStateException("Server not yet started.");
		}
	}

	public AjaxTestingServer start() {

		if (localPort != null) {
			throw new IllegalStateException(String.format("Server already started at port %s.", localPort));
		}

		environment.ifPresent(IAjaxTestingServerEnvironment::setup);

		ServletContextHandler context = new ServletContextHandler();
		context.setSessionHandler(new SessionHandler());
		context.setContextPath("/");
		context.addServlet(servletHolder, "/");

		HandlerCollection handlers = new HandlerCollection();
		handlers.addHandler(context);
		handlers.addHandler(new DefaultHandler());
		server.setHandler(handlers);
		server.setSessionIdManager(new DefaultSessionIdManager(server));

		try {
			// actually start the server
			server.addConnector(connector);
			server.start();
			this.localPort = connector.getLocalPort();
			return this;
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	public void join() {

		try {
			server.join();
		} catch (InterruptedException exception) {
			throw new InterruptedRuntimeException(exception);
		}
	}

	@Override
	public void close() {

		connector.close();

		try {
			server.stop();
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		} finally {
			environment.ifPresent(IAjaxTestingServerEnvironment::teardown);
		}
	}

	public AjaxTestingServlet getServlet() {

		try {
			return (AjaxTestingServlet) servletHolder.getServlet();
		} catch (ServletException exception) {
			throw new RuntimeException(exception);
		}
	}

	// TODO avoid warning by improving API, e.g. use a configuration object
	@SuppressWarnings("resource")
	public static void main(String[] args) {

		try (AjaxTestingServer server = new AjaxTestingServer()) {
			server.setPort(9000);
			server.start();
			Log.finfo("Server started: http://localhost:%s/", server.getLocalPort());
			server.join();
		}
	}
}

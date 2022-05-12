package com.softicar.platform.ajax.testing.server;

import com.softicar.platform.ajax.testing.AjaxTestingServlet;
import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.core.threading.InterruptedRuntimeException;
import com.softicar.platform.common.servlet.server.AbstractHttpServletServer;
import com.softicar.platform.common.servlet.server.HttpServletServerConfiguration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * Provides an {@link HttpServlet} server for unit test execution.
 *
 * @author Alexander Schmidt
 */
public class AjaxTestingServer extends AbstractHttpServletServer<AjaxTestingServer> implements AutoCloseable {

	public AjaxTestingServer(HttpServletServerConfiguration configuration) {

		super(configuration);
	}

	@Override
	protected ServletHolder createServletHolder() {

		return new ServletHolder(new AjaxTestingServlet());
	}

	@Override
	public void close() {

		if (serverHandle != null) {
			serverHandle.getConnector().close();
			try {
				serverHandle.getServer().stop();
			} catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		}
	}

	@Override
	protected AjaxTestingServer getThis() {

		return this;
	}

	public void join() {

		try {
			serverHandle.getServer().join();
		} catch (InterruptedException exception) {
			throw new InterruptedRuntimeException(exception);
		}
	}

	public AjaxTestingServlet getServlet() {

		try {
			return (AjaxTestingServlet) serverHandle.getServletHolder().getServlet();
		} catch (ServletException exception) {
			throw new RuntimeException(exception);
		}
	}

	public static void main(String[] args) {

		var configuration = new HttpServletServerConfiguration().setPort(9000).setContextName("");
		try (AjaxTestingServer server = new AjaxTestingServer(configuration)) {
			try (var handle = server.start()) {
				Log.finfo("Server started: http://localhost:%s/", server.getLocalPort());
				server.join();
			}
		}
	}
}

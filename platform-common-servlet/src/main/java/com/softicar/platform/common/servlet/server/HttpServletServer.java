package com.softicar.platform.common.servlet.server;

import javax.servlet.http.HttpServlet;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * An HTTP server for a single {@link HttpServlet} instance.
 *
 * @author Oliver Richers
 */
public class HttpServletServer extends AbstractHttpServletServer<HttpServletServer> {

	private final Class<? extends HttpServlet> servletClass;

	/**
	 * Constructs this server for the given servlet class.
	 *
	 * @param servletClass
	 *            the class of the {@link HttpServlet} (never null)
	 */
	public HttpServletServer(Class<? extends HttpServlet> servletClass) {

		this.servletClass = servletClass;
	}

	@Override
	protected HttpServletServer getThis() {

		return this;
	}

	@Override
	protected ServletHolder createServletHolder() {

		return new ServletHolder(servletClass);
	}
}

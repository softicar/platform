package com.softicar.platform.ajax.server.standalone;

import javax.servlet.http.HttpServlet;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * A stand-alone server for a single {@link HttpServlet} instance.
 *
 * @author Oliver Richers
 */
public class StandAloneServletServer extends AbstractStandAloneServletServer<StandAloneServletServer> {

	private final Class<? extends HttpServlet> servletClass;

	/**
	 * Constructs this server for the given servlet class.
	 *
	 * @param servletClass
	 *            the class of the {@link HttpServlet} (never null)
	 */
	public StandAloneServletServer(Class<? extends HttpServlet> servletClass) {

		this.servletClass = servletClass;
	}

	@Override
	protected StandAloneServletServer getThis() {

		return this;
	}

	@Override
	protected ServletHolder createServletHolder() {

		return new ServletHolder(servletClass);
	}
}

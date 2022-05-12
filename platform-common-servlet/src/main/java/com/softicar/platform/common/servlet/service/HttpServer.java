package com.softicar.platform.common.servlet.service;

import com.softicar.platform.common.servlet.server.AbstractHttpServletServer;
import org.eclipse.jetty.servlet.ServletHolder;

public class HttpServer extends AbstractHttpServletServer<HttpServer> {

	private final IHttpService service;

	public HttpServer(IHttpService service) {

		this.service = service;
	}

	@Override
	protected ServletHolder createServletHolder() {

		return new ServletHolder(new HttpServiceServlet(service));
	}

	@Override
	protected HttpServer getThis() {

		return this;
	}
}

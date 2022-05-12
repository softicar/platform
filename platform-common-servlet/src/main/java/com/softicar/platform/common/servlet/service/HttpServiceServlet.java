package com.softicar.platform.common.servlet.service;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HttpServiceServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final IHttpService service;

	public HttpServiceServlet(IHttpService service) {

		this.service = service;
	}

	@Override
	public void init() {

		service.initialize(getServletContext()::addListener);
	}

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) {

		service.service(request, response);
	}

	@Override
	public void destroy() {

		service.destroy();
	}
}

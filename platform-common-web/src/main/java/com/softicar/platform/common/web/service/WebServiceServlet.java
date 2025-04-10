package com.softicar.platform.common.web.service;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

class WebServiceServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final IWebService service;

	public WebServiceServlet(IWebService service) {

		this.service = service;
	}

	@Override
	public void init() {

		service.initialize();
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

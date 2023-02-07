package com.softicar.platform.core.module.web.service;

import com.softicar.platform.core.module.web.service.environment.IWebServiceEnvironment;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Central {@link HttpServlet} for all web-services.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
@WebServlet("/service/*")
@MultipartConfig
public class WebServiceBrokerServlet extends HttpServlet {

	private final WebServiceBrokerService service;

	public WebServiceBrokerServlet() {

		this.service = new WebServiceBrokerService();
	}

	public WebServiceBrokerServlet setEnvironment(IWebServiceEnvironment environment) {

		service.setEnvironment(environment);
		return this;
	}

	@Override
	public void init() {

		service.initialize();
	}

	@Override
	public void destroy() {

		service.destroy();
	}

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) {

		service.service(request, response);
	}
}

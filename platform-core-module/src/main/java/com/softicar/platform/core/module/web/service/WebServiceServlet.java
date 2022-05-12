package com.softicar.platform.core.module.web.service;

import com.softicar.platform.core.module.web.service.environment.IWebServiceEnvironment;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Central {@link HttpServlet} for all web-services.
 * <p>
 * WARNING: DO NOT RENAME OR MOVE THIS CLASS.
 * <p>
 * This class is referenced via canonical name, from the {@code web.xml} files
 * of consuming projects.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
@MultipartConfig
public class WebServiceServlet extends HttpServlet {

	private final WebServiceMasterService compositeService;

	public WebServiceServlet() {

		this.compositeService = new WebServiceMasterService();
	}

	public WebServiceServlet setEnvironment(IWebServiceEnvironment environment) {

		compositeService.setEnvironment(environment);
		return this;
	}

	@Override
	public void init() {

		compositeService.initialize(getServletContext()::addListener);
	}

	@Override
	public void destroy() {

		compositeService.destroy();
	}

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) {

		compositeService.service(request, response);
	}
}

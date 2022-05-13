package com.softicar.platform.common.web.service;

import com.softicar.platform.common.web.servlet.HttpServletServer;

/**
 * A server for {@link IWebService} instances.
 *
 * @author Oliver Richers
 */
public class WebServiceServer extends HttpServletServer {

	public WebServiceServer(IWebService service) {

		super(new WebServiceServlet(service));
	}
}

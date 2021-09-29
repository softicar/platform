package com.softicar.platform.ajax.server.hot;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * This interface is used by {@link HotDeploymentServlet} to load a wrapped
 * {@link HttpServlet}.
 *
 * @author Oliver Richers
 */
public interface IHotDeploymentServletLoader {

	/**
	 * Returns whether a reload of the servlet is necessary for the given HTTP
	 * request.
	 * <p>
	 * One possible implementation is to return <i>true</i> for GET requests and
	 * <i>false</i> for POST request. Another implementation might check if a
	 * session object exists.
	 *
	 * @param request
	 *            the HTTP request that needs to be handled
	 * @return <i>true</i> if the servlet class should be reloaded, <i>false</i>
	 *         otherwise
	 */
	boolean isReloadNecessary(HttpServletRequest request);

	/**
	 * Loads a new servlet instance using the given class loader.
	 *
	 * @param servletContext
	 *            the servlet context (never null)
	 * @param classLoader
	 *            the class loader to use for loading the servlet class
	 * @return a new instance of the servlet
	 */
	HttpServlet loadServlet(ServletContext servletContext, ClassLoader classLoader);
}

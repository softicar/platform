package com.softicar.platform.common.servlet.hot;

import com.softicar.platform.common.servlet.service.IHttpService;
import javax.servlet.http.HttpServletRequest;

/**
 * This interface is used by {@link HotHttpServiceServlet} to load new
 * {@link IHttpService} instances.
 *
 * @author Oliver Richers
 */
public interface IHotHttpServiceLoader {

	/**
	 * Determines whether a reload of the {@link IHttpService} is necessary
	 * for the given HTTP request.
	 * <p>
	 * One possible implementation is to return <i>true</i> for GET requests and
	 * <i>false</i> for POST request. Another implementation might check if a
	 * session object exists.
	 *
	 * @param request
	 *            the HTTP request that needs to be handled (never <i>null</i>)
	 * @return <i>true</i> if the {@link IHttpService} class should be
	 *         reloaded; <i>false</i> otherwise
	 */
	boolean isReloadNecessary(HttpServletRequest request);

	/**
	 * Loads a new {@link IHttpService} instance using the given class
	 * loader.
	 *
	 * @param classLoader
	 *            the {@link ClassLoader} to use for loading the
	 *            {@link IHttpService} class (never <i>null</i>)
	 * @return a new instance of the {@link IHttpService}
	 */
	IHttpService loadService(ClassLoader classLoader);
}

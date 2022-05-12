package com.softicar.platform.common.web.service.hot;

import com.softicar.platform.common.web.service.IWebService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Used by {@link HotWebServiceServer} to load new {@link IWebService}
 * instances.
 *
 * @author Oliver Richers
 */
public interface IHotWebServiceLoader {

	/**
	 * Determines whether a reload of the {@link IWebService} is necessary for
	 * the given {@link HttpServletRequest}.
	 * <p>
	 * One possible implementation is to return <i>true</i> for GET requests and
	 * <i>false</i> for POST request. Another implementation might check if an
	 * {@link HttpSession} object exists.
	 *
	 * @param request
	 *            the {@link HttpServletRequest} that needs to be handled (never
	 *            <i>null</i>)
	 * @return <i>true</i> if the {@link IWebService} instance should be
	 *         reloaded; <i>false</i> otherwise
	 */
	boolean isReloadNecessary(HttpServletRequest request);

	/**
	 * Loads a new {@link IWebService} instance using the given class loader.
	 *
	 * @param classLoader
	 *            the {@link ClassLoader} to use for loading the
	 *            {@link IWebService} class (never <i>null</i>)
	 * @return a new instance of the {@link IWebService}
	 */
	IWebService loadService(ClassLoader classLoader);
}

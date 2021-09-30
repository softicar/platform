package com.softicar.platform.core.module.web.service;

import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.emf.source.code.reference.point.IEmfSourceCodeReferencePoint;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Interface for all web services.
 *
 * @author Oliver Richers
 */
@FunctionalInterface
public interface IWebService extends IEmfSourceCodeReferencePoint {

	/**
	 * Handles a single {@link HttpServletRequest}.
	 * <p>
	 * The implementation of this method must be thread-safe, as it may be
	 * called by different {@link Thread} instances at the same time.
	 *
	 * @param request
	 *            the {@link HttpServletRequest} to handle (never <i>null</i>)
	 * @param response
	 *            the {@link HttpServletResponse} to reply through (never
	 *            <i>null</i>)
	 */
	void service(HttpServletRequest request, HttpServletResponse response);

	/**
	 * Initializes this {@link IWebService} instance.
	 * <p>
	 * This method must be called once for this {@link IWebService} instance
	 * before any call to the {@link #service} method.
	 *
	 * @param servletContext
	 *            the {@link ServletContext} (never <i>null</i>)
	 */
	default void initialize(ServletContext servletContext) {

		DevNull.swallow(servletContext);
	}

	/**
	 * Destroys this {@link IWebService} instance.
	 * <p>
	 * This method releases all resources, potentially allocated by this
	 * {@link IWebService}. It must be called before disposing this class.
	 */
	default void destroy() {

		// nothing to do by default
	}
}

package com.softicar.platform.common.web.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Interface to handle web service requests.
 *
 * @author Oliver Richers
 */
public interface IWebService {

	/**
	 * Initializes this {@link IWebService} instance.
	 * <p>
	 * This method must be called exactly once for this {@link IWebService}
	 * instance before any call to the {@link #service} method.
	 * <p>
	 * The implementation of this method does <b>not</b> have be thread-safe,
	 * since it is guaranteed to be only called once per {@link IWebService}
	 * instance.
	 */
	default void initialize() {

		// nothing to do by default
	}

	/**
	 * Handles a single {@link HttpServletRequest}.
	 * <p>
	 * The implementation of this method must be thread-safe, since it may be
	 * called by different {@link Thread} instances concurrently.
	 *
	 * @param request
	 *            the {@link HttpServletRequest} to handle (never <i>null</i>)
	 * @param response
	 *            the {@link HttpServletResponse} to reply through (never
	 *            <i>null</i>)
	 */
	void service(HttpServletRequest request, HttpServletResponse response);

	/**
	 * Destroys this {@link IWebService} instance.
	 * <p>
	 * This method releases all resources, potentially allocated by this
	 * {@link IWebService}. It must be called exactly once before disposing an
	 * instance of this class.
	 * <p>
	 * The implementation of this method does <b>not</b> have be thread-safe,
	 * since it is guaranteed to be only called once per {@link IWebService}
	 * instance.
	 */
	default void destroy() {

		// nothing to do by default
	}
}

package com.softicar.platform.common.servlet.service;

import com.softicar.platform.common.core.utils.DevNull;
import java.util.EventListener;
import java.util.function.Consumer;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Interface to handle HTTP requests through an {@link HttpServlet}.
 *
 * @author Oliver Richers
 */
public interface IHttpService {

	/**
	 * Initializes this {@link IHttpService} instance.
	 * <p>
	 * This method must be called once for this {@link IHttpService} instance
	 * before any call to the {@link #service} method.
	 * <p>
	 * The implementation of this method need <b>not</b> be thread-safe, as it
	 * is guaranteed to be only called once per {@link IHttpService} instance.
	 *
	 * @param listeners
	 *            a {@link Consumer} to install {@link EventListener} objects
	 *            into the {@link ServletContext} (never <i>null</i>)
	 */
	default void initialize(Consumer<EventListener> listeners) {

		DevNull.swallow(listeners);
	}

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
	 * Destroys this {@link IHttpService} instance.
	 * <p>
	 * This method releases all resources, potentially allocated by this
	 * {@link IHttpService}. It must be called before disposing an instance of
	 * this class.
	 * <p>
	 * The implementation of this method need <b>not</b> be thread-safe, as it
	 * is guaranteed to be only called once per {@link IHttpService} instance.
	 */
	default void destroy() {

		// nothing to do by default
	}
}

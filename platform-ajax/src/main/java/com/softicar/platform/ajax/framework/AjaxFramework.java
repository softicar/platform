package com.softicar.platform.ajax.framework;

import com.softicar.platform.ajax.customization.AjaxSettings;
import com.softicar.platform.ajax.customization.IAjaxSettings;
import com.softicar.platform.ajax.customization.IAjaxStrategy;
import com.softicar.platform.ajax.framework.listener.AjaxSessionListener;
import com.softicar.platform.ajax.request.AjaxRequest;
import com.softicar.platform.ajax.service.AjaxServiceDelegator;
import java.util.EventListener;
import java.util.function.Consumer;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Central controlling point of the AJAX framework.
 *
 * @author Oliver Richers
 */
public class AjaxFramework {

	private final AjaxSettings settings;
	private final IAjaxStrategy strategy;
	private volatile boolean initialized;

	/**
	 * Constructs this instance of {@link AjaxFramework}.
	 * <p>
	 * You must provide a customized implementation of {@link IAjaxStrategy},
	 * which defines the concrete strategy for logging, authentication, exports,
	 * etc.
	 *
	 * @param strategy
	 *            an suitable implementation of the {@link IAjaxStrategy}
	 *            interface (never <i>null</i>)
	 */
	public AjaxFramework(IAjaxStrategy strategy) {

		this.settings = new AjaxSettings();
		this.strategy = strategy;
		this.initialized = false;
	}

	/**
	 * Returns the {@link IAjaxSettings} of this {@link AjaxFramework} instance.
	 *
	 * @return the {@link IAjaxSettings} (never <i>null</i>)
	 */
	public AjaxSettings getSettings() {

		return settings;
	}

	/**
	 * Returns the {@link IAjaxStrategy} employed by this {@link AjaxFramework}
	 * instance.
	 *
	 * @return the {@link IAjaxStrategy} (never <i>null</i>)
	 */
	public IAjaxStrategy getAjaxStrategy() {

		return strategy;
	}

	/**
	 * Initializes this {@link AjaxFramework}.
	 * <p>
	 * This method must be called once for this {@link AjaxFramework} instance
	 * before any call to the {@link #service} method.
	 *
	 * @param contextListeners
	 *            a {@link Consumer} to install {@link EventListener} instances
	 *            into the {@link ServletContext} (never <i>null</i>)
	 * @return this
	 */
	public AjaxFramework initialize(Consumer<EventListener> contextListeners) {

		contextListeners.accept(new AjaxSessionListener());
		initialized = true;
		return this;
	}

	/**
	 * Handles an {@link HttpServletRequest}.
	 * <p>
	 * This method should be called from the service method of an
	 * {@link HttpServlet}.
	 */
	public void service(HttpServletRequest request, HttpServletResponse response) {

		try {
			if (!initialized) {
				throw new IllegalStateException("Framework was not completely initialized.");
			}
			new AjaxServiceDelegator(new AjaxRequest(this, request, response)).service();
		} catch (Throwable throwable) {
			new AjaxServiceExceptionHandler(this, request, response).handleException(throwable);
		}
	}
}

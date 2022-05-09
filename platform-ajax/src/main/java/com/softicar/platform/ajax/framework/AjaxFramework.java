package com.softicar.platform.ajax.framework;

import com.softicar.platform.ajax.customization.AjaxSettings;
import com.softicar.platform.ajax.customization.IAjaxStrategy;
import com.softicar.platform.ajax.framework.listener.AjaxSessionListener;
import com.softicar.platform.ajax.request.AjaxRequest;
import com.softicar.platform.ajax.service.AjaxServiceDelegator;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Central controlling point of the AJAX framework.
 *
 * @author Oliver Richers
 */
public class AjaxFramework implements IAjaxFramework {

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

	@Override
	public AjaxSettings getSettings() {

		return settings;
	}

	@Override
	public IAjaxStrategy getAjaxStrategy() {

		return strategy;
	}

	@Override
	public IAjaxFramework initialize(ServletContext servletContext) {

		servletContext.addListener(AjaxSessionListener.class);
		initialized = true;
		return this;
	}

	@Override
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

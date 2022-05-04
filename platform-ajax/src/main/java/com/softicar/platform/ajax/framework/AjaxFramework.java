package com.softicar.platform.ajax.framework;

import com.softicar.platform.ajax.customization.AjaxSettings;
import com.softicar.platform.ajax.customization.IAjaxStrategy;
import com.softicar.platform.ajax.request.AjaxRequest;
import com.softicar.platform.ajax.service.AjaxServiceDelegator;
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
	public void service(HttpServletRequest request, HttpServletResponse response) {

		try {
			new AjaxServiceDelegator(new AjaxRequest(this, request, response)).service();
		} catch (Throwable throwable) {
			throwable.printStackTrace();
			new AjaxServiceExceptionHandler(this, request, response).handleException(throwable);
		}
	}
}

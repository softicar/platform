package com.softicar.platform.ajax.framework;

import com.softicar.platform.ajax.customization.IAjaxSettings;
import com.softicar.platform.ajax.customization.IAjaxStrategy;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IAjaxFramework {

	/**
	 * Returns the {@link IAjaxSettings} of this {@link IAjaxFramework}
	 * instance.
	 *
	 * @return the {@link IAjaxSettings} (never <i>null</i>)
	 */
	IAjaxSettings getSettings();

	/**
	 * Returns the {@link IAjaxStrategy} employed by this {@link IAjaxFramework}
	 * instance.
	 *
	 * @return the {@link IAjaxStrategy} (never <i>null</i>)
	 */
	IAjaxStrategy getAjaxStrategy();

	/**
	 * Handles an {@link HttpServletRequest}.
	 * <p>
	 * This method should be called from the service method of an
	 * {@link HttpServlet}.
	 */
	void service(HttpServletRequest request, HttpServletResponse response);
}

package com.softicar.platform.ajax.exceptions;

import javax.servlet.http.HttpServletResponse;

/**
 * Represents the HTTP error <i>404 Not Found</i>.
 * <p>
 * <i>The requested resource could not be found but may be available in the
 * future. Subsequent requests by the client are permissible.</i>
 *
 * @author Oliver Richers
 */
public class AjaxHttpNotFoundError extends AjaxHttpError {

	public AjaxHttpNotFoundError() {

		this("Not Found");
	}

	public AjaxHttpNotFoundError(String message, Object...arguments) {

		this(null, message, arguments);
	}

	public AjaxHttpNotFoundError(Throwable cause, String message, Object...arguments) {

		super(HttpServletResponse.SC_NOT_FOUND, cause, message, arguments);
	}
}

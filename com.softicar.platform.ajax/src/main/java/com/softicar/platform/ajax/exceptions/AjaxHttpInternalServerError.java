package com.softicar.platform.ajax.exceptions;

import javax.servlet.http.HttpServletResponse;

/**
 * Represents the HTTP error <i>500 Internal Server Error</i>.
 * <p>
 * <i>A generic error message, given when an unexpected condition was
 * encountered and no more specific message is suitable.</i>
 *
 * @author Oliver Richers
 */
public class AjaxHttpInternalServerError extends AjaxHttpError {

	public AjaxHttpInternalServerError() {

		this("Internal Server Error");
	}

	public AjaxHttpInternalServerError(Throwable cause) {

		this(cause, "Internal Server Error");
	}

	public AjaxHttpInternalServerError(String message, Object...arguments) {

		this(null, message, arguments);
	}

	public AjaxHttpInternalServerError(Throwable cause, String message, Object...arguments) {

		super(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, cause, message, arguments);
	}
}

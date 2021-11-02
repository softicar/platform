package com.softicar.platform.ajax.exceptions;

import javax.servlet.http.HttpServletResponse;

/**
 * Represents the HTTP error <i>400 Bad Request</i>.
 * <p>
 * <i>The server cannot or will not process the request due to an apparent
 * client error (e.g., malformed request syntax, size too large, invalid request
 * message framing, or deceptive request routing).</i>
 *
 * @author Oliver Richers
 */
public class AjaxHttpBadRequestError extends AjaxHttpError {

	public AjaxHttpBadRequestError() {

		this("Bad Request");
	}

	public AjaxHttpBadRequestError(String message, Object...arguments) {

		this(null, message, arguments);
	}

	public AjaxHttpBadRequestError(Throwable cause, String message, Object...arguments) {

		super(HttpServletResponse.SC_BAD_REQUEST, cause, message, arguments);
	}
}

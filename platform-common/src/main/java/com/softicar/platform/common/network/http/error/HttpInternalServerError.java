package com.softicar.platform.common.network.http.error;

import com.softicar.platform.common.network.http.HttpStatusCode;

/**
 * Represents the HTTP error <i>500 Internal Server Error</i>.
 * <p>
 * <i>A generic error message, given when an unexpected condition was
 * encountered and no more specific message is suitable.</i>
 *
 * @author Oliver Richers
 */
public class HttpInternalServerError extends HttpError {

	public HttpInternalServerError() {

		this("Internal Server Error");
	}

	public HttpInternalServerError(Throwable cause) {

		this(cause, "Internal Server Error");
	}

	public HttpInternalServerError(String message, Object...arguments) {

		this(null, message, arguments);
	}

	public HttpInternalServerError(Throwable cause, String message, Object...arguments) {

		super(HttpStatusCode.INTERNAL_SERVER_ERROR, cause, message, arguments);
	}
}

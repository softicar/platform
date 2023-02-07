package com.softicar.platform.common.network.http.error;

import com.softicar.platform.common.network.http.HttpStatusCode;

/**
 * An HTTP error.
 *
 * @author Oliver Richers
 */
public class HttpError extends RuntimeException {

	private final HttpStatusCode statusCode;

	public HttpError(HttpStatusCode statusCode, String message, Object...arguments) {

		this(statusCode, null, message, arguments);
	}

	public HttpError(HttpStatusCode statusCode, Throwable cause, String message, Object...arguments) {

		super(String.format(message, arguments), cause);

		this.statusCode = statusCode;
	}

	/**
	 * Returns the {@link HttpStatusCode}.
	 *
	 * @return the {@link HttpStatusCode} (never <i>null</i>)
	 */
	public HttpStatusCode getStatusCode() {

		return statusCode;
	}
}

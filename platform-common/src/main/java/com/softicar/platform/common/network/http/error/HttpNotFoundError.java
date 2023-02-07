package com.softicar.platform.common.network.http.error;

import com.softicar.platform.common.network.http.HttpStatusCode;

/**
 * Represents the HTTP error <i>404 Not Found</i>.
 * <p>
 * <i>The requested resource could not be found but may be available in the
 * future. Subsequent requests by the client are permissible.</i>
 *
 * @author Oliver Richers
 */
public class HttpNotFoundError extends HttpError {

	public HttpNotFoundError() {

		this("Not Found");
	}

	public HttpNotFoundError(String message, Object...arguments) {

		this(null, message, arguments);
	}

	public HttpNotFoundError(Throwable cause, String message, Object...arguments) {

		super(HttpStatusCode.NOT_FOUND, cause, message, arguments);
	}
}

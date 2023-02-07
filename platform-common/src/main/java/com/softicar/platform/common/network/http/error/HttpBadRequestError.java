package com.softicar.platform.common.network.http.error;

import com.softicar.platform.common.network.http.HttpStatusCode;

/**
 * Represents the HTTP error <i>400 Bad Request</i>.
 * <p>
 * <i>The server cannot or will not process the request due to an apparent
 * client error (e.g., malformed request syntax, size too large, invalid request
 * message framing, or deceptive request routing).</i>
 *
 * @author Oliver Richers
 */
public class HttpBadRequestError extends HttpError {

	public HttpBadRequestError() {

		this("Bad Request");
	}

	public HttpBadRequestError(String message, Object...arguments) {

		this(null, message, arguments);
	}

	public HttpBadRequestError(Throwable cause, String message, Object...arguments) {

		super(HttpStatusCode.BAD_REQUEST, cause, message, arguments);
	}
}

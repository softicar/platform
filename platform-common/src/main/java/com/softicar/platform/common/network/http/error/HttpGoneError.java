package com.softicar.platform.common.network.http.error;

import com.softicar.platform.common.network.http.HttpStatusCode;

/**
 * Represents the HTTP error <i>410 Gone</i>.
 * <p>
 * <i> Indicates that the resource requested is no longer available and will not
 * be available again. This should be used when a resource has been
 * intentionally removed and the resource should be purged. Upon receiving a 410
 * status code, the client should not request the resource in the future.
 * Clients such as search engines should remove the resource from their indices.
 * Most use cases do not require clients and search engines to purge the
 * resource, and a "404 Not Found" may be used instead.</i>
 *
 * @author Oliver Richers
 */
public class HttpGoneError extends HttpError {

	public HttpGoneError() {

		this("Gone");
	}

	public HttpGoneError(String message, Object...arguments) {

		this(null, message, arguments);
	}

	public HttpGoneError(Throwable cause, String message, Object...arguments) {

		super(HttpStatusCode.GONE, cause, message, arguments);
	}
}

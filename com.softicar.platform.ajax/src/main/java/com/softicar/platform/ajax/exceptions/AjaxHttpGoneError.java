package com.softicar.platform.ajax.exceptions;

import javax.servlet.http.HttpServletResponse;

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
public class AjaxHttpGoneError extends AjaxHttpError {

	public AjaxHttpGoneError() {

		this("Gone");
	}

	public AjaxHttpGoneError(String message, Object...arguments) {

		this(null, message, arguments);
	}

	public AjaxHttpGoneError(Throwable cause, String message, Object...arguments) {

		super(HttpServletResponse.SC_GONE, cause, message, arguments);
	}
}

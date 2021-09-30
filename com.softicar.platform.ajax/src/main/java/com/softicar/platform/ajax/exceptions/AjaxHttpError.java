package com.softicar.platform.ajax.exceptions;

/**
 * An HTTP error.
 *
 * @author Oliver Richers
 */
public class AjaxHttpError extends RuntimeException {

	private final int httpStatusCode;

	public AjaxHttpError(int httpStatusCode, String message, Object...arguments) {

		this(httpStatusCode, null, message, arguments);
	}

	public AjaxHttpError(int httpStatusCode, Throwable cause, String message, Object...arguments) {

		super(String.format(message, arguments), cause);

		this.httpStatusCode = httpStatusCode;
	}

	/**
	 * Returns the HTTP error code.
	 *
	 * @return the valid HTTP error code
	 */
	public int getHttpStatusCode() {

		return httpStatusCode;
	}

	/**
	 * Tests whether this is a server error nor not.
	 *
	 * @return <i>true</i> if this is a server error; <i>false</i> otherwise
	 */
	public boolean isServerError() {

		return httpStatusCode >= 500 && httpStatusCode < 600;
	}
}

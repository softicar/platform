package com.softicar.platform.common.core.exceptions;

/**
 * This runtime exception is the base class of all softicar exceptions.
 * 
 * @author Oliver Richers
 */
public class SofticarException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SofticarException() {

		super();
	}

	public SofticarException(String message) {

		super(message);
	}

	public SofticarException(String format, Object...args) {

		super(String.format(format, args));
	}

	public SofticarException(Throwable cause) {

		super(cause);
	}

	public SofticarException(Throwable cause, String message) {

		super(message, cause);
	}

	public SofticarException(Throwable cause, String format, Object...args) {

		super(String.format(format, args), cause);
	}
}

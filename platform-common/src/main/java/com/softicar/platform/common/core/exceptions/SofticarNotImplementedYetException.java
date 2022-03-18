package com.softicar.platform.common.core.exceptions;

/**
 * Throw this exception if a functionality is not implemented, yet.
 * 
 * @author Oliver Richers
 */
public class SofticarNotImplementedYetException extends SofticarException {

	private static final long serialVersionUID = 1L;

	public SofticarNotImplementedYetException() {

		super();
	}

	public SofticarNotImplementedYetException(Exception cause) {

		super(cause);
	}

	public SofticarNotImplementedYetException(String text, Object...args) {

		super(text, args);
	}

	public SofticarNotImplementedYetException(Exception cause, String text, Object...args) {

		super(cause, text, args);
	}
}

package com.softicar.platform.common.core.language;

/**
 * This exception is a wrapper around {@link ClassNotFoundException}.
 * 
 * @author Oliver Richers
 */
public class ClassNotFoundRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ClassNotFoundRuntimeException(ClassNotFoundException exception) {

		super(exception);
	}
}

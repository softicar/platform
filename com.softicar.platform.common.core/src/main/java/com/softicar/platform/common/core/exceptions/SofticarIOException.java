package com.softicar.platform.common.core.exceptions;

import java.io.IOException;

/**
 * This is a {@link RuntimeException} wrapper for {@link IOException}.
 * 
 * @author Oliver Richers
 */
public class SofticarIOException extends SofticarException {

	private static final long serialVersionUID = 1L;
	private final IOException ioException;

	public SofticarIOException(IOException ioException) {

		super(ioException, "An I/O exception occured: %s", ioException);
		this.ioException = ioException;
	}

	public SofticarIOException(IOException ioException, String message) {

		super(ioException, message);
		this.ioException = ioException;
	}

	public SofticarIOException(IOException ioException, String format, Object...args) {

		super(ioException, format, args);
		this.ioException = ioException;
	}

	/**
	 * Returns the wrapped {@link IOException}.
	 */
	public IOException get() {

		return ioException;
	}
}

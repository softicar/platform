package com.softicar.platform.common.core.exception;

import java.util.Collection;

/**
 * A {@link RuntimeException} that aggregates multiple {@link Exception}
 * objects.
 *
 * @author Oliver Richers
 */
public class MultiException extends RuntimeException {

	private final Collection<Throwable> exceptions;

	public MultiException(String message, Collection<Throwable> exceptions) {

		super(message);
		this.exceptions = exceptions;
	}

	public Collection<Throwable> getExceptions() {

		return exceptions;
	}
}

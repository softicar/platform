package com.softicar.platform.dom.exception;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.dom.parent.IDomParentElement;

public interface IDomExceptionDisplayElement extends IDomParentElement {

	/**
	 * Checks the given exception whether it can be displayed or not.
	 * <p>
	 * Per default, this only returns true if the given exception is an instance
	 * of {@link SofticarUserException}.
	 *
	 * @param exception
	 *            the exception to check (never null)
	 * @return true if the exception can be displayed, false otherwise
	 */
	default boolean accepts(Exception exception) {

		return exception instanceof SofticarUserException;
	}

	void display(Exception exception);
}

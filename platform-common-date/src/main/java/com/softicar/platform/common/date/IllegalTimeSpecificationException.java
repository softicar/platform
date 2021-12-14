package com.softicar.platform.common.date;

import com.softicar.platform.common.core.exceptions.SofticarUserException;

/**
 * This {@link Exception} is thrown if an illegal time was specified.
 *
 * @author Oliver Richers
 */
public class IllegalTimeSpecificationException extends SofticarUserException {

	public IllegalTimeSpecificationException(String text) {

		this(null, text);
	}

	public IllegalTimeSpecificationException(Exception cause, String text) {

		super(cause, CommonDateI18n.ILLEGAL_TIME_SPECIFICATION_ARG1.toDisplay(text));
	}
}

package com.softicar.platform.common.date;

import com.softicar.platform.common.core.exceptions.SofticarUserException;

/**
 * Thrown if an illegal date-and-time value was specified.
 *
 * @author Alexander Schmidt
 */
public class IllegalDayTimeSpecificationException extends SofticarUserException {

	public IllegalDayTimeSpecificationException(String text) {

		this(null, text);
	}

	public IllegalDayTimeSpecificationException(Exception cause, String text) {

		super(cause, CommonDateI18n.ILLEGAL_DATE_WITH_TIME_ARG1.toDisplay(text));
	}
}

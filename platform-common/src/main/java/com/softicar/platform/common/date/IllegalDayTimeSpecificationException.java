package com.softicar.platform.common.date;

import com.softicar.platform.common.core.exceptions.SofticarUserException;

/**
 * Thrown if an illegal daytime value was specified.
 *
 * @author Alexander Schmidt
 */
public class IllegalDayTimeSpecificationException extends SofticarUserException {

	public IllegalDayTimeSpecificationException(String text) {

		this(null, text);
	}

	public IllegalDayTimeSpecificationException(Exception cause, String text) {

		super(cause, CommonDateI18n.ILLEGAL_DAYTIME_SPECIFICATION_ARG1.toDisplay(text));
	}
}

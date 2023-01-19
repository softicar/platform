package com.softicar.platform.common.date;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.string.Padding;

/**
 * Thrown if an illegal date was specified.
 *
 * @author Oliver Richers
 */
public class IllegalDateSpecificationException extends SofticarUserException {

	public IllegalDateSpecificationException(int year, int month, int day) {

		this(
			"%s-%s-%s"
				.formatted(//
					Padding.padLeft(year + "", '0', 2),
					Padding.padLeft(month + "", '0', 2),
					Padding.padLeft(day + "", '0', 2)));
	}

	public IllegalDateSpecificationException(String text) {

		this(null, text);
	}

	public IllegalDateSpecificationException(Exception cause, String text) {

		super(cause, CommonDateI18n.ILLEGAL_DATE_ARG1.toDisplay(text));
	}
}

package com.softicar.platform.common.string.parameter;

import com.softicar.platform.common.core.exceptions.SofticarException;

/**
 * This exception is thrown by {@link IParameter} if an invalid parameter value
 * was specified.
 *
 * @author Oliver Richers
 */
public class InvalidParameterValueException extends SofticarException {

	private static final long serialVersionUID = 1L;

	public InvalidParameterValueException(Exception exception, IParameter<?> parameter, String valueString) {

		super(exception, "Invalid value string '%s' for parameter '%s' of type '%s'.", valueString, parameter.getName(), parameter.getClass().getSimpleName());
	}
}

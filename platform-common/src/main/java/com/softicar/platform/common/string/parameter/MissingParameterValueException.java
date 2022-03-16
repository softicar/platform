package com.softicar.platform.common.string.parameter;

import com.softicar.platform.common.core.exceptions.SofticarException;

/**
 * This exception is thrown by {@link IParameter} if a required parameter value
 * missing.
 * 
 * @author Oliver Richers
 */
public class MissingParameterValueException extends SofticarException {

	private static final long serialVersionUID = 1L;

	public MissingParameterValueException(IParameter<?> parameter) {

		super("The required parameter '%s' is missing.", parameter.getName());
	}
}

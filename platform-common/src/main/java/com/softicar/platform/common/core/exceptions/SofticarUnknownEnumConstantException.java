package com.softicar.platform.common.core.exceptions;

/**
 * Throw this exception if you encounter an unexpected enum value.
 * 
 * @author Oliver Richers
 */
public class SofticarUnknownEnumConstantException extends SofticarDeveloperException {

	private static final long serialVersionUID = 1L;

	public SofticarUnknownEnumConstantException(Enum<?> constant) {

		super("The enum constant %s of the type %s is unknown.", constant, constant.getClass().getCanonicalName());
	}
}

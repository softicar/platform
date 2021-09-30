package com.softicar.platform.common.core.java.code.validation;

import com.softicar.platform.common.core.java.code.validator.IJavaCodeValidator;

/**
 * Dedicated exception to be thrown by {@link IJavaCodeValidator}.
 * <p>
 * This exception should be thrown by {@link IJavaCodeValidator} for all
 * well-defined validation failures. Only the exception message is shown to the
 * user, since the stack trace is assumed to be irrelevant.
 *
 * @author Oliver Richers
 */
public class JavaCodeValidationException extends RuntimeException {

	public JavaCodeValidationException(String message, Object...arguments) {

		super(String.format(message, arguments));
	}
}

package com.softicar.platform.common.core.java.code.validator;

import com.softicar.platform.common.core.java.code.validation.JavaCodeValidationEnvironment;

/**
 * Interface for Java code validators.
 * <p>
 * To activate a class for automatic execution, it must be annotated with
 * {@link JavaCodeValidator}.
 *
 * @author Oliver Richers
 */
public interface IJavaCodeValidator {

	/**
	 * Executes this validator.
	 *
	 * @param environment
	 *            the validation environment, providing configuration and
	 *            context to the {@link IJavaCodeValidator} (never null)
	 */
	void validate(JavaCodeValidationEnvironment environment);
}

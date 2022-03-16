package com.softicar.platform.common.core.java.code.validation;

import com.softicar.platform.common.core.java.code.validator.IJavaCodeValidator;

/**
 * Entry point to find and execute all {@link IJavaCodeValidator} classes.
 *
 * @author Oliver Richers
 */
public class JavaCodeValidationEntryPoint {

	public static void main(String[] arguments) {

		try {
			new JavaCodeValidationExecutor(new JavaCodeValidationEnvironment(arguments)).execute();
		} catch (JavaCodeValidationException exception) {
			System.err.println(exception.getMessage());
			System.exit(1);
		}
	}
}

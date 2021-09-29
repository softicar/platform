package com.softicar.platform.common.core.java.code.validation.output;

/**
 * Interface to output Java code violations.
 *
 * @author Oliver Richers
 */
public interface IJavaCodeValidationOuput {

	/**
	 * Adds the given violation message.
	 *
	 * @param message
	 *            the violation message (never <i>null</i>)
	 */
	void addViolation(String message);

	/**
	 * Formats and adds the given violation message.
	 *
	 * @param message
	 *            the violation message (never <i>null</i>)
	 * @param arguments
	 *            the format arguments (never <i>null</i>)
	 */
	void formatViolation(String message, Object...arguments);
}

package com.softicar.platform.dom.input.auto;

import java.util.Optional;

/**
 * Represents the selection and related states of a
 * {@link IDomAutoCompleteInput}, and provides access to the selected value.
 *
 * @author Alexander Schmidt
 */
public interface IDomAutoCompleteInputSelection<T> {

	/**
	 * Validates the selected value. Returns the selected value if validation
	 * passes. Throws if validation fails.
	 *
	 * @return The validated selected value. May be null (if not selecting a
	 *         value is considered valid).
	 * @see #isValid()
	 * @see #assertValid()
	 */
	T getValueOrThrow();

	/**
	 * Validates the selected value. Returns the selected value if validation
	 * passes. Returns null if validation fails.
	 *
	 * @return The validated selected value. May be null (if not selecting a
	 *         value is considered valid). Null if validation fails.
	 * @see #isValid()
	 * @see #assertValid()
	 */
	T getValueOrNull();

	/**
	 * Validates the selected value. Returns an Optional that contains the
	 * selected value if validation passes. Returns an empty Optional if
	 * validation fails.
	 *
	 * @return An Optional that contains the validated selected value. May be
	 *         empty (if not selecting a value is considered valid). Empty if
	 *         validation fails.
	 * @see #isValid()
	 * @see #assertValid()
	 */
	Optional<T> getValue();

	/**
	 * Validates the selected value. Throws an Exception if validation fails
	 * (i.e. if {@link #isValid()} would return false).
	 *
	 * @see #isValid()
	 */
	void assertValid();

	/**
	 * Validates the selected value. Returns a boolean value to indicate the
	 * result of the validation.
	 *
	 * @return true if validation passes. false if validation fails.
	 * @see #assertValid()
	 */
	boolean isValid();

	/**
	 * @return true if the trimmed pattern is empty. false otherwise.
	 */
	boolean isBlankPattern();
}

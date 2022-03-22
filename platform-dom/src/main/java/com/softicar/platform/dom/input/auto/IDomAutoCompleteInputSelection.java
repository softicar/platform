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
	 * Returns the optional value of this input element, as follows:
	 * <ul>
	 * <li>If the entered text can be mapped to a value, that value is
	 * returned.</li>
	 * <li>If the entered text is blank, {@link Optional#empty()} is
	 * returned.</li>
	 * <li>If the entered text cannot be mapped to a value, an exception is
	 * thrown.</li>
	 * </ul>
	 *
	 * @return the value as an {@link Optional} (never <i>null</i>)
	 */
	Optional<T> getValue();

	/**
	 * Returns the value of this input element, as follows:
	 * <ul>
	 * <li>If the entered text can be mapped to a value, that value is
	 * returned.</li>
	 * <li>If the entered text is blank, <i>null</i> is returned.</li>
	 * <li>If the entered text cannot be mapped to a value, an exception is
	 * thrown.</li>
	 * </ul>
	 *
	 * @return the value (may be <i>null</i>)
	 */
	T getValueOrNull();

	/**
	 * Returns the value of this input element, as follows:
	 * <ul>
	 * <li>If the entered text can be mapped to a value, that value is
	 * returned.</li>
	 * <li>If the entered text is blank, or if the entered text cannot be mapped
	 * to a value, an exception is thrown.</li>
	 * </ul>
	 *
	 * @return the value (never <i>null</i>)
	 */
	T getValueOrThrow();

	/**
	 * Determines whether the entered text can be mapped to a value.
	 * <p>
	 * Returns <i>true</i> if the entered text can be mapped to a value, or if
	 * the entered text is blank.
	 *
	 * @return <i>true</i> if the selected value is valid; <i>false</i>
	 *         otherwise
	 */

	/**
	 * Determines the validity of the entered text, as follows:
	 * <ul>
	 * <li>If the entered text can be mapped to a value, <i>true</i> is
	 * returned.</li>
	 * <li>If the entered text is blank, <i>true</i> is returned.</li>
	 * <li>Otherwise, <i>false</i> is returned.</li>
	 * </ul>
	 *
	 * @return <i>true</i> if the entered text is valid; <i>false</i> otherwise
	 */
	boolean isValid();

	/**
	 * Asserts the validity of the entered text.
	 * <p>
	 * If {@link #isValid()} would return <i>false</i>, an exception is thrown.
	 */
	void assertValid();

	/**
	 * @return <i>true</i> if the entered text is blank; <i>false</i> otherwise
	 */
	boolean isBlankPattern();
}

package com.softicar.platform.dom.elements.input.auto.string;

import java.util.Optional;

public interface IDomAutoCompleteLiteralInput {

	/**
	 * @return an Optional of the value string (trimmed; never null;
	 *         {@link Optional#empty()} if the trimmed value string is empty).
	 */
	Optional<String> getValueString();

	/**
	 * @return the value string (trimmed; may be null; never an empty string)
	 */
	String getValueStringOrNull();

	/**
	 * @return the value string (trimmed; never null; may be an empty string)
	 */
	String getValueStringOrEmpty();

	/**
	 * Sets the value for this input element.
	 *
	 * @param string
	 *            the value to set (may be null)
	 */
	void setValueString(String string);

	void clearValueString();

	/**
	 * @return true if the trimmed value string is empty, or if no value string
	 *         is present. false otherwise.
	 */
	boolean isEmptyValueString();
}

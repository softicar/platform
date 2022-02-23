package com.softicar.platform.dom.input;

/**
 * A simple text input element.
 *
 * @author Oliver Richers
 */
public class DomTextInput extends DomInput implements IDomStringInputNode {

	// -------------------------------- CONSTRUCTORS -------------------------------- //

	/**
	 * Creates an empty text input.
	 */
	public DomTextInput() {

		setAttribute("type", "text");
		getAccessor().setAttributeInMap("value", ""); // value is an empty string by default
	}

	/**
	 * Creates the text input, initialized with the specified value.
	 */
	public DomTextInput(String value) {

		setAttribute("type", "text");
		setAttribute("value", value);
	}

	/**
	 * Convenience constructor to initialize the value and the maximum length of
	 * the text input.
	 */
	public DomTextInput(String value, int maxLength) {

		this(value);
		setMaxLength(maxLength);
	}

	// -------------------------------- ATTRIBUTES -------------------------------- //

	/**
	 * Maximum number of characters that can be entered into this input field.
	 *
	 * @param maxLength
	 *            the maximum number of characters for this input
	 */
	public void setMaxLength(int maxLength) {

		setAttribute("maxlength", "" + maxLength);
	}

	// -------------------------------- READONLY -------------------------------- //

	/**
	 * Makes this input element read-only.
	 * <p>
	 * In contrast to {@link #setEnabled(boolean)}, the user can still see and
	 * copy the text of this input element.
	 */
	public void setReadonly(boolean readonly) {

		setAttribute("readonly", readonly);
	}

	// -------------------------------- SPECIAL VALUE METHODS -------------------------------- //

	/**
	 * Returns whether the trimmed value of this input element is empty or not.
	 *
	 * @return true if the value (in trimmed form) is empty or null, false if
	 *         the value is a valid non-empty string
	 */
	public boolean isEmpty() {

		if (getValue() != null && !getValue().trim().isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
}

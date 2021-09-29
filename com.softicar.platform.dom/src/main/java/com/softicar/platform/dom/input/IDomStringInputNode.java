package com.softicar.platform.dom.input;

import java.util.Optional;

public interface IDomStringInputNode extends IDomValueBasedInputNode<String> {

	// -------------------------------- value -------------------------------- //

	@Override
	default void setValue(String value) {

		setAttribute("value", value);
	}

	@Override
	default String getValue() {

		return getAttributeValue("value").orElse(null);
	}

	/**
	 * Resets the value of this input element.
	 */
	default void clearValue() {

		setValue("");
	}

	/**
	 * Trims the value of this input element.
	 */
	default void trimValue() {

		Optional//
			.ofNullable(getValue())
			.map(String::trim)
			.ifPresent(this::setValue);
	}

	/**
	 * Convenience method that trims the value of this input element and returns
	 * it.
	 * <p>
	 * This calls {@link #trimValue()} before returning the value. If the value
	 * string is empty this method will return null instead.
	 *
	 * @return the trimmed value, or null if the value is empty
	 */
	default String getTextOrNull() {

		if (getValue() != null) {
			trimValue();
			if (!getValue().isEmpty()) {
				return getValue();
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	// -------------------------------- caret -------------------------------- //

	/**
	 * Inserts the given text at the current caret position.
	 */
	default void insertAtCaret(String text) {

		getDomEngine().insertAtCaret(this, text);
	}

	default void moveCaretToPosition(int position) {

		getDomEngine().moveCaretToPosition(this, position);
	}

	// -------------------------------- selection -------------------------------- //

	/**
	 * Selects the text in this text input field.
	 * <p>
	 * Important: If you call this method on an input element within a popup
	 * menu, make sure that the popup menu is visible, because only visible
	 * elements can get the focus.
	 */
	default void select() {

		getDomEngine().select(this);
	}
}

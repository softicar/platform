package com.softicar.platform.dom.input;

import com.softicar.platform.dom.elements.popup.DomPopup;

public interface IDomTextualInput extends IDomFocusable {

	// -------------------------------- value -------------------------------- //

	/**
	 * Assigns the input text of this {@link IDomTextualInput}.
	 * <p>
	 * If the provided text is <i>null</i>, an empty {@link String} will be
	 * assigned.
	 *
	 * @param inputText
	 *            the input text or <i>null</i>
	 * @return this
	 */
	IDomTextualInput setInputText(String inputText);

	/**
	 * Returns the current input text of this {@link IDomTextualInput}.
	 *
	 * @return the input text (never <i>null</i>)
	 */
	String getInputText();

	/**
	 * Same as {@link #getInputText()} but trims the returned value.
	 *
	 * @return the trimmed input text (never <i>null</i>)
	 */
	default String getInputTextTrimmed() {

		return getInputText().trim();
	}

	/**
	 * Trims the input text of this {@link IDomTextualInput}.
	 * <p>
	 * Leading and trailing whitespace will be discarded.
	 */
	default void trimInputText() {

		setInputText(getInputTextTrimmed());
	}

	/**
	 * Tests whether the input text is blank, in the sense of
	 * {@link String#isBlank()}.
	 *
	 * @return <i>true</i> if the input text is blank; <i>false</i> otherwise
	 */
	default boolean isBlank() {

		return getInputText().isBlank();
	}

	// -------------------------------- caret & selection -------------------------------- //

	/**
	 * Inserts the given text at the current caret position.
	 */
	default void insertAtCaret(String text) {

		getDomEngine().insertTextAtCaret(this, text);
	}

	/**
	 * Moves the caret to the given position.
	 *
	 * @param position
	 *            the index of the desired position
	 */
	default void moveCaretToPosition(int position) {

		getDomEngine().moveCaretToPosition(this, position);
	}

	/**
	 * Selects the text in this {@link IDomTextualInput}.
	 * <p>
	 * Important: If you call this method on an input element within a
	 * {@link DomPopup}, make sure that the {@link DomPopup} is visible, because
	 * only visible elements can get the focus.
	 */
	default void select() {

		getDomEngine().select(this);
	}

	// -------------------------------- read-only -------------------------------- //

	/**
	 * Makes this {@link IDomTextualInput} read-only.
	 */
	default void setReadonly(boolean readonly) {

		setAttribute("readonly", readonly? "" : null);
	}

	/**
	 * Tests whether this {@link IDomTextualInput} is read-only.
	 *
	 * @return <i>true</i> if this {@link IDomTextualInput} is read-only;
	 *         <i>false</i> otherwise
	 */
	default boolean isReadonly() {

		return getAttributeValue("readonly").isPresent();
	}
}

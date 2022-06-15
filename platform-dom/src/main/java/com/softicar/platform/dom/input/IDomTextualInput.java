package com.softicar.platform.dom.input;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.popup.DomPopup;
import java.util.Optional;

/**
 * A specialization of {@link IDomValueInput} for {@link String}.
 *
 * @author Oliver Richers
 */
public interface IDomTextualInput extends IDomFocusable, IDomValueInput<String> {

	// -------------------------------- value -------------------------------- //

	/**
	 * Sets the value of this {@link IDomTextualInput}.
	 * <p>
	 * If the value is <i>null</i>, the empty string will be assigned.
	 *
	 * @param value
	 *            the value or <i>null</i>
	 */
	@Override
	void setValue(String value);

	/**
	 * Returns the value of this {@link IDomValueInput}.
	 * <p>
	 * The returned {@link Optional} value is never empty.
	 *
	 * @return the value as {@link Optional} (never empty)
	 */
	@Override
	Optional<String> getValue();

	/**
	 * Returns the value text of this {@link IDomTextualInput}.
	 *
	 * @return the value text (never <i>null</i>)
	 */
	String getValueText();

	/**
	 * Same as {@link #getValueText()} but trims the returned value.
	 *
	 * @return the trimmed value text (never <i>null</i>)
	 */
	String getValueTextTrimmed();

	/**
	 * Tests whether the textual value of this input is blank, in the sense of
	 * {@link String#isBlank()}.
	 *
	 * @return <i>true</i> if the value is blank; <i>false</i> otherwise
	 */
	boolean isBlank();

	// -------------------------------- caret & selection -------------------------------- //

	/**
	 * Inserts the given text at the current caret position.
	 */
	void insertTextAtCaret(String text);

	/**
	 * Moves the caret to the given position.
	 *
	 * @param position
	 *            the index of the desired position
	 */
	void moveCaretToPosition(int position);

	/**
	 * Selects the text in this {@link IDomTextualInput}.
	 * <p>
	 * Important: If you call this method on an input element within a
	 * {@link DomPopup}, make sure that the {@link DomPopup} is visible, because
	 * only visible elements can get the focus.
	 */
	void selectText();

	// -------------------------------- placeholder -------------------------------- //

	/**
	 * Defines the HTML placeholder attribute.
	 *
	 * @param placeholder
	 *            the placeholder text to display (never <i>null</i>)
	 * @return this
	 */
	IDomTextualInput setPlaceholder(IDisplayString placeholder);

	// -------------------------------- read-only -------------------------------- //

	/**
	 * Makes this {@link IDomTextualInput} read-only.
	 */
	void setReadonly(boolean readonly);

	/**
	 * Tests whether this {@link IDomTextualInput} is read-only.
	 *
	 * @return <i>true</i> if this {@link IDomTextualInput} is read-only;
	 *         <i>false</i> otherwise
	 */
	boolean isReadonly();
}

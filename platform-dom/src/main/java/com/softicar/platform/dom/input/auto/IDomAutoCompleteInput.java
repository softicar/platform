package com.softicar.platform.dom.input.auto;

import com.softicar.platform.dom.input.IDomTextualInput;
import com.softicar.platform.dom.input.IDomValueInput;
import com.softicar.platform.dom.parent.IDomParentElement;

/**
 * Common interface of all auto-complete input elements.
 *
 * @author Oliver Richers
 * @author Alexander Schmidt
 */
public interface IDomAutoCompleteInput<T> extends IDomParentElement, IDomValueInput<T> {

	/**
	 * Returns the native input field that is contained in the auto-complete
	 * parent element.
	 *
	 * @return the native input field that is contained in the auto-complete
	 *         parent element (never null)
	 */
	IDomTextualInput getInputField();

	/**
	 * Returns the configuration object of this input element.
	 *
	 * @return the configuration object of this input element (never null)
	 */
	IDomAutoCompleteInputConfiguration getConfiguration();

	/**
	 * Returns a limited list of valid items for the given pattern.
	 *
	 * @param pattern
	 *            the normalized piece of text, the user entered (lower-case and
	 *            trimmed)
	 * @return a list of valid items for the given pattern (never null)
	 */
	DomAutoCompleteList getItemList(String pattern);

	/**
	 * Returns an {@link IDomAutoCompleteInputSelection} instance that
	 * represents the current selection, and that provides access to the
	 * selected value.
	 *
	 * @return an {@link IDomAutoCompleteInputSelection} instance that
	 *         represents the current selection (never null)
	 */
	IDomAutoCompleteInputSelection<T> getSelection();
}

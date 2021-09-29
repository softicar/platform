package com.softicar.platform.dom.input.auto;

import com.softicar.platform.dom.input.IDomStringInputNode;
import com.softicar.platform.dom.parent.IDomParentElement;

/**
 * Common interface of all auto-complete input elements.
 *
 * @author Oliver Richers
 * @author Alexander Schmidt
 */
public interface IDomAutoCompleteInput<T> extends IDomParentElement {

	/**
	 * Returns the native input field that is contained in the auto-complete
	 * parent element.
	 *
	 * @return the native input field that is contained in the auto-complete
	 *         parent element (never null)
	 */
	IDomStringInputNode getInputField();

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

	/**
	 * Sets the selected value for this input element.
	 *
	 * @param value
	 *            the value to be set for this input element (may be null)
	 */
	void setValue(T value);

	/**
	 * Sets the selected value for this input element and triggers a
	 * (re-)validation.
	 *
	 * @param value
	 *            the value to be set for this input element (may be null)
	 */
	void setValueAndValidate(T value);
}

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
	 * Returns an {@link IDomAutoCompleteInputSelection} instance that
	 * represents the current selection, and that provides access to the
	 * selected value.
	 *
	 * @return an {@link IDomAutoCompleteInputSelection} instance that
	 *         represents the current selection (never null)
	 */
	IDomAutoCompleteInputSelection<T> getSelection();
}

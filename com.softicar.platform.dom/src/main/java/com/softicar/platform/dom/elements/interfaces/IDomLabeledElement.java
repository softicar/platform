package com.softicar.platform.dom.elements.interfaces;

import com.softicar.platform.common.core.i18n.IDisplayString;

/**
 * Convenience interface for all elements with a label.
 *
 * @author Oliver Richers
 */
public interface IDomLabeledElement<T> {

	/**
	 * Sets the label of this element.
	 *
	 * @param text
	 *            the raw text to be used as label (never <i>null</i>)
	 * @return this
	 */
	default T setLabel(String text) {

		return setLabel(IDisplayString.create(text));
	}

	/**
	 * Sets the label of this element.
	 *
	 * @param text
	 *            the raw format text to be used as label (never <i>null</i>)
	 * @param arguments
	 *            the formatting arguments
	 * @return this
	 */
	default T setLabel(String text, Object...arguments) {

		return setLabel(String.format(text, arguments));
	}

	/**
	 * Sets the label of this element.
	 *
	 * @param label
	 *            the {@link IDisplayString} to be used as label (never
	 *            <i>null</i>)
	 * @return this element
	 */
	T setLabel(IDisplayString label);
}

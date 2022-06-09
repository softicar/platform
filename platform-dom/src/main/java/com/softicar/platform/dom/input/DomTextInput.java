package com.softicar.platform.dom.input;

import com.softicar.platform.dom.element.DomElementTag;

/**
 * A single-line text input.
 *
 * @author Oliver Richers
 */
public class DomTextInput extends AbstractDomTextualInput {

	/**
	 * Creates an empty text input.
	 */
	public DomTextInput() {

		setAttribute("type", "text");
		getAccessor().setAttributeInMap("value", "");
	}

	/**
	 * Creates the text input, initialized with the specified text.
	 *
	 * @param value
	 *            the value to assign (may be <i>null</i>>)
	 */
	public DomTextInput(String value) {

		this();
		setValue(value);
	}

	@Override
	public DomElementTag getTag() {

		return DomElementTag.INPUT;
	}

	/**
	 * Sets the maximum number of characters that can be entered into this
	 * {@link DomTextInput}.
	 *
	 * @param maxLength
	 *            the maximum number of characters (must be zero or greater)
	 */
	public void setMaxLength(int maxLength) {

		setAttribute("maxlength", "" + maxLength);
	}
}

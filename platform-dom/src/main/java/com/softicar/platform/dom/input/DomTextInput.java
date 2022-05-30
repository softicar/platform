package com.softicar.platform.dom.input;

import com.softicar.platform.common.core.i18n.IDisplayString;
import java.util.Optional;

/**
 * A simple text input element.
 *
 * @author Oliver Richers
 */
public class DomTextInput extends AbstractDomInput implements IDomTextualInput {

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
	 * @param inputText
	 *            the input text to assign (may be <i>null</i>>)
	 */
	public DomTextInput(String inputText) {

		this();
		setInputText(inputText);
	}

	@Override
	public DomTextInput setInputText(String inputText) {

		setAttribute("value", Optional.ofNullable(inputText).orElse(""));
		return this;
	}

	@Override
	public String getInputText() {

		return getAttributeValue("value").orElse("");
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

	/**
	 * Defines the HTML placeholder attribute.
	 *
	 * @param placeholder
	 *            the placeholder text to display (never <i>null</i>)
	 * @return this
	 */
	public DomTextInput setPlaceholder(IDisplayString placeholder) {

		setAttribute("placeholder", placeholder.toString());
		return this;
	}
}

package com.softicar.platform.dom.elements;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.input.IDomTextualInput;
import com.softicar.platform.dom.parent.DomParentElement;
import java.util.Optional;

/**
 * Represents an HTML text area element.
 *
 * @author Oliver Richers
 */
public class DomTextArea extends DomParentElement implements IDomTextualInput {

	public DomTextArea() {

		getAccessor().setAttributeInMap("value", "");
	}

	public DomTextArea(int rows, int cols) {

		this("", rows, cols);
	}

	public DomTextArea(String inputText, int rows, int cols) {

		setInputText(inputText);
		setRowCount(rows);
		setColCount(cols);
	}

	@Override
	public DomTextArea setInputText(String inputText) {

		setAttribute("value", Optional.ofNullable(inputText).orElse(""));
		return this;
	}

	@Override
	public String getInputText() {

		return getAttributeValue("value").orElse("");
	}

	@Override
	public DomElementTag getTag() {

		return DomElementTag.TEXTAREA;
	}

	/**
	 * Defines the HTML placeholder attribute.
	 *
	 * @param placeholder
	 *            the placeholder text to display (never <i>null</i>)
	 * @return this
	 */
	public DomTextArea setPlaceholder(IDisplayString placeholder) {

		setAttribute("placeholder", placeholder.toString());
		return this;
	}

	// -------------------------------- configuration -------------------------------- //

	public DomTextArea setRowCount(int row) {

		setAttribute("rows", row);
		return this;
	}

	public DomTextArea setColCount(int col) {

		setAttribute("cols", col);
		return this;
	}

	public DomTextArea setReadonly() {

		setAttribute("readonly", "readonly");
		return this;
	}

	/**
	 * Enables/Disables wrapping behavior of text area fields. WARNING: This is
	 * a non-standard attribute but it works in most browsers.
	 */
	public DomTextArea setWrap(boolean doWrap) {

		setAttribute("wrap", doWrap? "on" : "off");
		return this;
	}
}

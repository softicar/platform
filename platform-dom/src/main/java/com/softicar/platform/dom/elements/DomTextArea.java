package com.softicar.platform.dom.elements;

import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.input.AbstractDomTextualInput;

/**
 * A multi-line text input.
 *
 * @author Oliver Richers
 */
public class DomTextArea extends AbstractDomTextualInput {

	public DomTextArea() {

		getAccessor().setAttributeInMap("value", "");
	}

	public DomTextArea(String value) {

		setValue(value);
	}

	@Override
	public DomElementTag getTag() {

		return DomElementTag.TEXTAREA;
	}

	// -------------------------------- configuration -------------------------------- //

	public DomTextArea setSize(int rows, int columns) {

		setRowCount(rows);
		setColCount(columns);
		return this;
	}

	public DomTextArea setRowCount(int row) {

		setAttribute("rows", row);
		return this;
	}

	public DomTextArea setColCount(int col) {

		setAttribute("cols", col);
		return this;
	}

	/**
	 * Toggles the wrapping behavior of this {@link DomTextArea}.
	 * <p>
	 * WARNING: This is a non-standard attribute but it works in most browsers.
	 */
	public DomTextArea setWrap(boolean doWrap) {

		setAttribute("wrap", doWrap? "on" : "off");
		return this;
	}
}

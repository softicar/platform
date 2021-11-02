package com.softicar.platform.dom.elements;

import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.input.IDomStringInputNode;
import com.softicar.platform.dom.parent.DomParentElement;

/**
 * This class represents an Html text area element.
 *
 * @author Oliver Richers
 */
public class DomTextArea extends DomParentElement implements IDomStringInputNode {

	public DomTextArea() {

		getAccessor().setAttributeInMap("value", "");
	}

	public DomTextArea(String value, int rows, int cols) {

		setAttribute("value", value);
		setAttribute("rows", "" + rows);
		setAttribute("cols", "" + cols);
	}

	public DomTextArea(int rows, int cols) {

		this("", rows, cols);
	}

	@Override
	public DomElementTag getTag() {

		return DomElementTag.TEXTAREA;
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

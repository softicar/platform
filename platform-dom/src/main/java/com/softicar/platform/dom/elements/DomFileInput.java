package com.softicar.platform.dom.elements;

import com.softicar.platform.dom.input.DomInput;

/**
 * This class represents an Html file input element.
 *
 * @author Oliver Richers
 */
public class DomFileInput extends DomInput {

	public DomFileInput() {

		this(false);
	}

	public DomFileInput(boolean multipleFile) {

		setAttribute("type", "file");
		setAttribute("name", "file");
		setMultiple(multipleFile);
	}

	/**
	 * Sets the multiple file select of the input field.
	 *
	 * @param multiple
	 *            the multiple attribute of the input field
	 */
	public void setMultiple(boolean multiple) {

		if (multiple) {
			setAttribute("multiple", "multiple");
		} else {
			setAttribute("multiple", "");
		}
	}
}

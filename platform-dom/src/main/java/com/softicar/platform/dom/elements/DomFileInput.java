package com.softicar.platform.dom.elements;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.dom.event.IDomChangeEventHandler;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.input.AbstractDomInput;

/**
 * This class represents an Html file input element.
 *
 * @author Oliver Richers
 */
public class DomFileInput extends AbstractDomInput implements IDomChangeEventHandler {

	private INullaryVoidFunction onChangeHandler;

	public DomFileInput() {

		this(false);
	}

	public DomFileInput(boolean multipleFile) {

		this.onChangeHandler = INullaryVoidFunction.NO_OPERATION;

		setAttribute("type", "file");
		setAttribute("name", "file");
		setMultiple(multipleFile);
	}

	public DomFileInput setOnChangeHandler(INullaryVoidFunction onChangeHandler) {

		this.onChangeHandler = onChangeHandler;
		return this;
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

	@Override
	public void handleChange(IDomEvent event) {

		onChangeHandler.apply();
	}
}

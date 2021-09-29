package com.softicar.platform.ajax.input.text;

import com.softicar.platform.ajax.utils.TestButton;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.input.DomTextInput;

class AjaxTextInputTestDiv extends DomDiv {

	private final DomTextInput input;
	private final DomButton button;

	public AjaxTextInputTestDiv() {

		this.input = appendChild(new DomTextInput());
		this.button = appendChild(new TestButton());
	}

	public DomTextInput getInput() {

		return input;
	}

	public DomButton getButton() {

		return button;
	}
}

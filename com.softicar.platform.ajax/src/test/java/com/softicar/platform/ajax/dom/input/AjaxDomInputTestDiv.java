package com.softicar.platform.ajax.dom.input;

import com.softicar.platform.ajax.utils.TestButton;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.input.DomTextInput;

class AjaxDomInputTestDiv extends DomDiv {

	private final DomTextInput input;
	private final TestButton button;

	public AjaxDomInputTestDiv() {

		this.input = new DomTextInput();
		this.button = new TestButton();

		appendChild(input);
		appendChild(button);
	}

	public DomTextInput getInput() {

		return input;
	}

	public TestButton getButton() {

		return button;
	}
}

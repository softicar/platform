package com.softicar.platform.ajax.dom.button;

import com.softicar.platform.ajax.utils.TestButton;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.input.DomTextInput;

class AjaxDomButtonTestDiv extends DomDiv {

	private final DomTextInput input;
	private final DomDiv output;
	private final DomButton button;

	public AjaxDomButtonTestDiv() {

		this.input = new DomTextInput();
		this.output = new DomDiv();
		this.button = new TestButton(() -> output.appendChild(input.getValue()));

		appendChild(input);
		appendChild(output);
		appendChild(button);
	}

	public DomTextInput getInput() {

		return input;
	}

	public DomDiv getOutput() {

		return output;
	}

	public DomButton getButton() {

		return button;
	}
}

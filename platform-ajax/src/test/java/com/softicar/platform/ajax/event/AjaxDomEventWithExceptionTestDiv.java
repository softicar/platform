package com.softicar.platform.ajax.event;

import com.softicar.platform.ajax.utils.TestButton;
import com.softicar.platform.dom.elements.DomDiv;

class AjaxDomEventWithExceptionTestDiv extends DomDiv {

	private final TestButton button;
	private final DomDiv output;

	public AjaxDomEventWithExceptionTestDiv() {

		this.button = appendChild(new TestButton(this::trigger));
		this.output = appendChild(new DomDiv());
	}

	public TestButton getButton() {

		return button;
	}

	public DomDiv getOutput() {

		return output;
	}

	private void trigger() {

		output.setAttribute("name", "foo");
		output.appendText("Hello");
		throw new RuntimeException("Exception Text");
	}
}

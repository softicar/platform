package com.softicar.platform.ajax.event;

import com.softicar.platform.ajax.utils.TestButton;
import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.i18n.IDisplayString;
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
		throw new SofticarUserException(IDisplayString.create("Exception Text"));
	}
}

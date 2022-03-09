package com.softicar.platform.ajax.event;

import com.softicar.platform.ajax.utils.TestButton;
import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.DomDiv;

class AjaxDomEventWithExceptionTestDiv extends DomDiv {

	private final TestButton softicarUserExceptionButton;
	private final TestButton nonSofticarUserExceptionButton;
	private final DomDiv output;

	public AjaxDomEventWithExceptionTestDiv() {

		this.softicarUserExceptionButton = appendChild(new TestButton(this::appendTextAndThrowSofticarUserException));
		this.nonSofticarUserExceptionButton = appendChild(new TestButton(this::appendTextAndThrowNonSofticarUserException));
		this.output = appendChild(new DomDiv());
	}

	public TestButton getSofticarUserExceptionButton() {

		return softicarUserExceptionButton;
	}

	public TestButton getNonSofticarUserExceptionButton() {

		return nonSofticarUserExceptionButton;
	}

	public DomDiv getOutput() {

		return output;
	}

	private void appendTextAndThrowSofticarUserException() {

		output.appendText("Hello");
		throw new SofticarUserException(IDisplayString.create("Exception Text"));
	}

	private void appendTextAndThrowNonSofticarUserException() {

		output.appendText("Hello");
		throw new RuntimeException("Exception Text");
	}
}

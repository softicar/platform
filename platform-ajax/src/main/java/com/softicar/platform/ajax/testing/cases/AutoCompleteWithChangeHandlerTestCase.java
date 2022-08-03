package com.softicar.platform.ajax.testing.cases;

import com.softicar.platform.dom.elements.DomDiv;

public class AutoCompleteWithChangeHandlerTestCase extends AbstractTestCaseDiv {

	private final AutoCompleteTestCaseInput input;

	public AutoCompleteWithChangeHandlerTestCase() {

		appendChild(new DomDiv()).appendText("Auto complete with change handler:");

		this.input = new AutoCompleteTestCaseInput(this).listenToChange();
		appendChild(input);
	}
}

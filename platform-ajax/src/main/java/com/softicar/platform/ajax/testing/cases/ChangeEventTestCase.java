package com.softicar.platform.ajax.testing.cases;

import com.softicar.platform.dom.input.DomTextInput;

public class ChangeEventTestCase extends AbstractTestCaseDiv {

	public ChangeEventTestCase() {

		appendChild(new Input());
	}

	private class Input extends DomTextInput {

		public Input() {

			addChangeCallback(() -> {
				log("got %s event, value = '%s'", getCurrentEvent().getType(), getValueText());
			});
		}
	}
}

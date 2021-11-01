package com.softicar.platform.ajax.testing.cases;

import com.softicar.platform.dom.event.IDomChangeEventHandler;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.input.DomTextInput;

public class ChangeEventTestCase extends AbstractTestCaseDiv {

	public ChangeEventTestCase() {

		appendChild(new Input());
	}

	private class Input extends DomTextInput implements IDomChangeEventHandler {

		@Override
		public void handleChange(IDomEvent event) {

			log("got %s event, value = '%s'", event.getType(), getValue());
		}
	}
}

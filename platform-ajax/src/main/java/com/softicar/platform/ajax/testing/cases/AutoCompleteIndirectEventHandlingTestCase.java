package com.softicar.platform.ajax.testing.cases;

import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.event.IDomEnterKeyEventHandler;
import com.softicar.platform.dom.event.IDomEvent;

public class AutoCompleteIndirectEventHandlingTestCase extends AbstractTestCaseDiv {

	public AutoCompleteIndirectEventHandlingTestCase() {

		appendChild(new DomDiv()).appendText("Auto complete with indirect ENTER handler:");
		appendChild(new Wrapper());
	}

	private class Wrapper extends DomDiv implements IDomEnterKeyEventHandler {

		private final AutoCompleteTestCaseInput input;

		public Wrapper() {

			this.input = appendChild(new AutoCompleteTestCaseInput(AutoCompleteIndirectEventHandlingTestCase.this));
		}

		@Override
		public void handleEnterKey(IDomEvent event) {

			log("[" + input.getValue().orElse("") + "]");
		}
	}
}

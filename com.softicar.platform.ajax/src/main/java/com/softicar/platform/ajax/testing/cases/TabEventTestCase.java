package com.softicar.platform.ajax.testing.cases;

import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.event.IDomTabKeyEventHandler;
import com.softicar.platform.dom.input.DomTextInput;

public class TabEventTestCase extends AbstractTestCaseDiv {

	public TabEventTestCase() {

		appendText("Pressing TAB in the first input should focus the second input.");
		appendNewChild(DomElementTag.BR);
		appendText("Pressing TAB in the second input should not change focus.");
		appendNewChild(DomElementTag.BR);
		appendChild(new TestInputWithDefaultBehavior());
		appendChild(new TestInput());
	}

	private class TestInputWithDefaultBehavior extends TestInput {

		public TestInputWithDefaultBehavior() {

			getDomEngine().setPreventDefaultBehavior(this, DomEventType.TAB, false);
		}
	}

	private class TestInput extends DomTextInput implements IDomTabKeyEventHandler {

		@Override
		public void handleTabKey(IDomEvent event) {

			log("pressed TAB");
		}
	}
}

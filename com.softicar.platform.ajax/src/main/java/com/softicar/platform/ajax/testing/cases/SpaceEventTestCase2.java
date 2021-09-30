package com.softicar.platform.ajax.testing.cases;

import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.event.IDomSpaceKeyEventHandler;
import com.softicar.platform.dom.input.DomTextInput;

public class SpaceEventTestCase2 extends AbstractTestCaseDiv {

	public SpaceEventTestCase2() {

		appendText("Pressing SPACE in the following input should input space.");
		appendNewChild(DomElementTag.BR);
		appendChild(new TestInput());
	}

	private class TestInput extends DomTextInput implements IDomSpaceKeyEventHandler {

		public TestInput() {

			getDomEngine().setPreventDefaultBehavior(this, DomEventType.SPACE, false);
		}

		@Override
		public void handleSpaceKey(IDomEvent event) {

			log("pressed SPACE");
		}
	}
}

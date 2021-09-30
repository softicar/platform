package com.softicar.platform.ajax.testing.cases;

import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.event.IDomSpaceKeyEventHandler;
import com.softicar.platform.dom.input.DomTextInput;

public class SpaceEventTestCase1 extends AbstractTestCaseDiv {

	public SpaceEventTestCase1() {

		appendText("Pressing SPACE in the following input should not input any text.");
		appendNewChild(DomElementTag.BR);
		appendChild(new TestInput());
	}

	private class TestInput extends DomTextInput implements IDomSpaceKeyEventHandler {

		@Override
		public void handleSpaceKey(IDomEvent event) {

			log("pressed SPACE");
		}
	}
}

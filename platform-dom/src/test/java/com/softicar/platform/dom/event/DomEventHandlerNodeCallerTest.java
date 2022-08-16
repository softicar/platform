package com.softicar.platform.dom.event;

import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.document.DomDocument;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.testing.engine.document.DomTestEvent;
import org.junit.Test;

public class DomEventHandlerNodeCallerTest extends AbstractTest {

	public DomEventHandlerNodeCallerTest() {

		CurrentDomDocument.set(new DomDocument());
	}

	@Test
	public void testCallSetsCurrentEvent() {

		var button = new DomButton().setClickCallback(this::assertCurrentEventDefined);
		var event = new DomTestEvent(button, DomEventType.CLICK);
		new DomEventHandlerNodeCaller(button, event).call();
	}

	private void assertCurrentEventDefined() {

		assertNotNull(CurrentDomDocument.get().getCurrentEvent());
	}
}

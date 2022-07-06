package com.softicar.platform.dom.event;

import com.softicar.platform.common.core.utils.DevNull;
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

	@Test
	public void testCallUnsetsCurrentEvent() {

		var button = new DomButton();
		var event = new DomTestEvent(button, DomEventType.CLICK);
		new DomEventHandlerNodeCaller(button, event).call();

		assertCurrentEventUndefined();
	}

	@Test
	public void testCallUnsetsCurrentEventWithException() {

		try {
			var button = new DomButton().setClickCallback(() -> {
				throw new RuntimeException();
			});
			var event = new DomTestEvent(button, DomEventType.CLICK);
			new DomEventHandlerNodeCaller(button, event).call();
		} catch (RuntimeException exception) {
			DevNull.swallow(exception);
		}

		assertCurrentEventUndefined();
	}

	private void assertCurrentEventDefined() {

		assertNotNull(CurrentDomDocument.get().getCurrentEvent());
	}

	private void assertCurrentEventUndefined() {

		assertNull(CurrentDomDocument.get().getCurrentEvent());
	}
}

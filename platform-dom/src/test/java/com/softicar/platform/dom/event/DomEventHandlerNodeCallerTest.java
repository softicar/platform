package com.softicar.platform.dom.event;

import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.document.DomDocument;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.testing.engine.document.DomTestEvent;
import com.softicar.platform.dom.utils.DomPayloadCodeExecutor;
import org.junit.Test;

public class DomEventHandlerNodeCallerTest extends AbstractTest {

	private final DomButton button;

	public DomEventHandlerNodeCallerTest() {

		var document = new DomDocument();
		CurrentDomDocument.set(document);

		this.button = document.getBody().appendChild(new DomButton());
	}

	@Test
	public void testCallSetsCurrentEvent() {

		button.setClickCallback(this::assertCurrentEventDefined);

		var event = new DomTestEvent(button, DomEventType.CLICK);
		new DomEventHandlerNodeCaller(new DomPayloadCodeExecutor(), button, event).call();
	}

	@Test
	public void testCallUnsetsCurrentEvent() {

		var event = new DomTestEvent(button, DomEventType.CLICK);
		new DomEventHandlerNodeCaller(new DomPayloadCodeExecutor(), button, event).call();

		assertCurrentEventUndefined();
	}

	@Test
	public void testCallUnsetsCurrentEventWithException() {

		try {
			button.setClickCallback(() -> {
				throw new RuntimeException();
			});

			var event = new DomTestEvent(button, DomEventType.CLICK);
			var executor = new DomPayloadCodeExecutor().setEventNode(button);
			new DomEventHandlerNodeCaller(executor, button, event).call();
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

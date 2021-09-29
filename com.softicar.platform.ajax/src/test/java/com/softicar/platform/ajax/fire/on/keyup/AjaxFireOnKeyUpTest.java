package com.softicar.platform.ajax.fire.on.keyup;

import com.softicar.platform.ajax.dom.event.AbstractAjaxDomEventTestDiv;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.AbstractAjaxSeleniumLowLevelTest;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.event.DomKeyCodes;
import com.softicar.platform.dom.event.IDomEnterKeyEventHandler;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.event.IDomSpaceKeyEventHandler;
import org.junit.Test;

public class AjaxFireOnKeyUpTest extends AbstractAjaxSeleniumLowLevelTest {

	private final TestDiv testDiv;

	public AjaxFireOnKeyUpTest() {

		this.testDiv = openTestNode(TestDiv::new);
	}

	@Test
	public void testWithOnlyKeyDown() {

		simulateKeyDown(testDiv, DomKeyCodes.ENTER);

		assertNoEvents();
	}

	@Test
	public void testWithOnlyKeyUp() {

		simulateKeyUp(testDiv, DomKeyCodes.ENTER);

		assertNoEvents();
	}

	@Test
	public void testWithKeyDownAndKeyUp() {

		simulateKeyDown(testDiv, DomKeyCodes.ENTER);
		simulateKeyUp(testDiv, DomKeyCodes.ENTER);

		assertEventType(DomEventType.ENTER);
	}

	@Test
	public void testWithInterleavedKeyDown() {

		simulateKeyDown(testDiv, DomKeyCodes.ENTER);
		simulateKeyDown(testDiv, DomKeyCodes.ESCAPE);
		simulateKeyUp(testDiv, DomKeyCodes.ENTER);

		assertNoEvents();
	}

	@Test
	public void testWithUnrelatedEvent() {

		simulateKeyDown(testDiv, DomKeyCodes.SPACE);
		simulateKeyUp(testDiv, DomKeyCodes.SPACE);

		assertEventType(DomEventType.SPACE);
	}

	// -------------------- private -------------------- //

	private void assertEventType(DomEventType eventType) {

		waitForServer();
		assertEquals(1, testDiv.getEvents().size());
		assertEquals(eventType, testDiv.getEvents().get(0).getType());
	}

	private void assertNoEvents() {

		waitForServer();
		assertEquals(0, testDiv.getEvents().size());
	}

	private static class TestDiv extends AbstractAjaxDomEventTestDiv implements IDomEnterKeyEventHandler, IDomSpaceKeyEventHandler {

		public TestDiv() {

			getDomEngine().setFireOnKeyUp(this, DomEventType.ENTER, true);
		}

		@Override
		public void handleEnterKey(IDomEvent event) {

			addEvent(event);
		}

		@Override
		public void handleSpaceKey(IDomEvent event) {

			addEvent(event);
		}
	}
}

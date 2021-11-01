package com.softicar.platform.ajax.dom.event.change;

import com.softicar.platform.ajax.dom.event.AbstractAjaxDomEventTestDiv;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.AbstractAjaxSeleniumLowLevelTest;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.event.IDomChangeEventHandler;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.input.DomTextInput;
import org.junit.Test;

public class AjaxDomChangeEventTest extends AbstractAjaxSeleniumLowLevelTest {

	private final TestDiv testDiv;
	private final DomTextInput input;

	public AjaxDomChangeEventTest() {

		this.testDiv = openTestNode(TestDiv::new);
		this.input = testDiv.getInput();
	}

	@Test
	public void test() {

		// input 'hello' and leave input field
		click(input);
		send(input, "hello");
		clickBodyNode();
		waitForServer();
		assertEventAndValue("hello");
		testDiv.clearEvents();

		// clear input
		click(input);
		clear(input);
		waitForServer();
		assertEventAndValue("");
		testDiv.clearEvents();

		// input 'world' and leave input field
		click(input);
		send(input, "world");
		clickBodyNode();
		waitForServer();
		assertEventAndValue("world");
		testDiv.clearEvents();
	}

	@Test
	public void testRedundantChangeEvents() {

		// input 'hello' and leave input field
		click(input);
		send(input, "hello");
		clickBodyNode();
		waitForServer();
		assertEventAndValue("hello");
		testDiv.clearEvents();

		// now simulate CHANGE events
		simulateChange(input);
		simulateChange(input);
		simulateChange(input);
		assertEquals("Expected to receive no redundant events.", 0, testDiv.getEvents().size());
	}

	private void assertEventAndValue(String expectedValue) {

		IDomEvent event = testDiv.getEvent();
		assertEquals(DomEventType.CHANGE, event.getType());
		assertSame(testDiv.getInput(), event.getCurrentTarget());

		assertEquals(expectedValue, testDiv.getInput().getValue());
	}

	private static class TestDiv extends AbstractAjaxDomEventTestDiv {

		private final ChangeListeningInput input;

		public TestDiv() {

			this.input = appendChild(new ChangeListeningInput());
		}

		public ChangeListeningInput getInput() {

			return input;
		}

		private class ChangeListeningInput extends DomTextInput implements IDomChangeEventHandler {

			@Override
			public void handleChange(IDomEvent event) {

				addEvent(event);
			}
		}
	}
}

package com.softicar.platform.ajax.dom.event.change;

import com.softicar.platform.ajax.dom.event.AbstractAjaxDomEventTestDiv;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.AbstractAjaxSeleniumLowLevelTest;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.AjaxSeleniumLowLevelTestEngineInput;
import com.softicar.platform.dom.event.DomEventType;
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

		// remove and add the 'o' again --> no change event should occur
		send(input, AjaxSeleniumLowLevelTestEngineInput.Key.BACK_SPACE);
		send(input, "o");
		clickBodyNode();
		assertEquals("Expected to receive no redundant events.", 0, testDiv.getEvents().size());
	}

	private void assertEventAndValue(String expectedValue) {

		IDomEvent event = testDiv.getEvent();
		assertEquals(DomEventType.CHANGE, event.getType());
		assertSame(testDiv.getInput(), event.getCurrentTarget());

		assertEquals(expectedValue, testDiv.getInput().getValueText());
	}

	private static class TestDiv extends AbstractAjaxDomEventTestDiv {

		private final DomTextInput input;

		public TestDiv() {

			this.input = appendChild(new DomTextInput());
			this.input.addChangeCallback(() -> addEvent(getCurrentEvent()));
		}

		public DomTextInput getInput() {

			return input;
		}
	}
}

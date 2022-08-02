package com.softicar.platform.ajax.dom.event.key;

import com.softicar.platform.ajax.testing.selenium.engine.level.low.AbstractAjaxSeleniumLowLevelTest;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.interfaces.IAjaxSeleniumLowLevelTestEngineInput;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.event.IDomKeyDownEventHandler;
import com.softicar.platform.dom.event.IDomKeyUpEventHandler;
import com.softicar.platform.dom.input.DomTextInput;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class AjaxDomKeyEventTest extends AbstractAjaxSeleniumLowLevelTest {

	private final Deque<IDomEvent> events;
	private TestInput input;

	public AjaxDomKeyEventTest() {

		this.events = new ArrayDeque<>();
	}

	@Test
	public void testWithListeningToLowerCaseLetter() {

		// only listen to lower-case
		this.input = openTestNode(() -> new TestInput("x"));

		// press lower-case key
		sendKeysAndWait("x");
		assertKeyDownAndUpEvents("x");

		// press upper-case key
		sendKeysAndWait("X");
		assertNoFurtherEvents();
	}

	@Test
	public void testWithListeningToUpperCaseLetter() {

		// only listen to upper-case
		this.input = openTestNode(() -> new TestInput("X"));

		// press lower-case key
		sendKeysAndWait("x");
		assertNoFurtherEvents();

		// press upper-case key
		sendKeysAndWait("X");
		assertKeyDownAndUpEvents("X");
		assertNoFurtherEvents();
	}

	@Test
	public void testWithControlKeys() {

		this.input = openTestNode(() -> new TestInput("ArrowDown", "ArrowUp"));

		sendKeysAndWait(IAjaxSeleniumLowLevelTestEngineInput.Key.DOWN);
		assertKeyDownAndUpEvents("ArrowDown");
		assertNoFurtherEvents();

		sendKeysAndWait(IAjaxSeleniumLowLevelTestEngineInput.Key.UP);
		assertKeyDownAndUpEvents("ArrowUp");
		assertNoFurtherEvents();
	}

	@Test
	public void testWithoutListeningToKeys() {

		this.input = openTestNode(() -> new TestInput());

		sendKeysAndWait("x");
		assertNoFurtherEvents();
	}

	@Test
	public void testWithMultiEvent() {

		this.input = openTestNode(() -> new TestInput("a", "B", "c"));

		input.setBlocked(true);
		send(input, "aBBcc");
		input.setBlocked(false);
		waitForServer();

		assertKeyDownAndUpEvents("a");
		assertKeyDownAndUpEvents("B");
		assertKeyDownAndUpEvents("B");
		assertKeyDownAndUpEvents("c");
		assertKeyDownAndUpEvents("c");
		assertNoFurtherEvents();
	}

	@Test
	public void testWithoutListeningToKeyUp() {

		this.input = openTestNode(() -> {
			var input = new TestInput("x");
			// don't listen to KEYUP
			input.unlistenToEvent(DomEventType.KEYUP);
			return input;
		});

		sendKeysAndWait("x");
		assertKeyDownEvent("x");
		assertNoFurtherEvents();
	}

	@Test
	public void testWithoutListeningToKeyDown() {

		this.input = openTestNode(() -> {
			var input = new TestInput("x");
			// don't listen to KEYDOWN
			input.unlistenToEvent(DomEventType.KEYDOWN);
			return input;
		});

		sendKeysAndWait("x");
		assertKeyUpEvent("x");
		assertNoFurtherEvents();
	}

	// ------------------------------ utility ------------------------------ //

	private void sendKeysAndWait(IAjaxSeleniumLowLevelTestEngineInput.Key...keys) {

		send(input, keys);
		waitForServer();
	}

	private void sendKeysAndWait(String keys) {

		send(input, keys);
		waitForServer();
	}

	private void assertKeyDownAndUpEvents(String expectedKey) {

		assertKeyDownEvent(expectedKey);
		assertKeyUpEvent(expectedKey);
	}

	private void assertKeyDownEvent(String expectedKey) {

		assertKeyEvent(DomEventType.KEYDOWN, expectedKey);
	}

	private void assertKeyUpEvent(String expectedKey) {

		assertKeyEvent(DomEventType.KEYUP, expectedKey);
	}

	private void assertKeyEvent(DomEventType expectedType, String expectedKey) {

		var event = assertNextEvent();

		assertEquals(expectedType, event.getType());
		assertEquals(expectedKey, event.getKey());
	}

	public IDomEvent assertNextEvent() {

		var event = events.poll();
		assertNotNull("next event", event);
		return event;
	}

	public void assertNoFurtherEvents() {

		assertEventCount(0);
	}

	public Deque<IDomEvent> assertEventCount(int expectedCount) {

		Assert.assertEquals("event count", expectedCount, events.size());
		return events;
	}

	private class TestInput extends DomTextInput implements IDomKeyDownEventHandler, IDomKeyUpEventHandler {

		private boolean blocked = false;

		public TestInput(String...keys) {

			setListenToKeys(List.of(keys));
		}

		@Override
		public void handleKeyDown(IDomEvent event) {

			waitUntil(() -> !blocked);
			events.add(event);
		}

		@Override
		public void handleKeyUp(IDomEvent event) {

			waitUntil(() -> !blocked);
			events.add(event);
		}

		public void setBlocked(boolean blocked) {

			this.blocked = blocked;
		}
	}
}

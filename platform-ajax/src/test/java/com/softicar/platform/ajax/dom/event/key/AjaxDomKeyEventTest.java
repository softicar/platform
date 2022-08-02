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
		assertKeyDownAndUp("x");

		// press upper-case key
		sendKeysAndWait("X");
		assertNoEvents();
	}

	@Test
	public void testWithListeningToUpperCaseLetter() {

		// only listen to upper-case
		this.input = openTestNode(() -> new TestInput("X"));

		// press lower-case key
		sendKeysAndWait("x");
		assertNoEvents();

		// press upper-case key
		sendKeysAndWait("X");
		assertKeyDownAndUp("X");
		assertNoEvents();
	}

	@Test
	public void testWithControlKeys() {

		this.input = openTestNode(() -> new TestInput("ArrowDown", "ArrowUp"));

		sendKeysAndWait(IAjaxSeleniumLowLevelTestEngineInput.Key.DOWN);
		assertKeyDownAndUp("ArrowDown");
		assertNoEvents();

		sendKeysAndWait(IAjaxSeleniumLowLevelTestEngineInput.Key.UP);
		assertKeyDownAndUp("ArrowUp");
		assertNoEvents();
	}

	@Test
	public void testWithoutListeningToKeys() {

		this.input = openTestNode(() -> new TestInput());

		sendKeysAndWait("x");
		assertNoEvents();
	}

	@Test
	public void testWithMultiEvent() {

		this.input = openTestNode(() -> new TestInput("a", "B", "c"));

		input.setBlocked(true);
		send(input, "aBBcc");
		input.setBlocked(false);
		waitForServer();

		assertKeyDownAndUp("a");
		assertKeyDownAndUp("B");
		assertKeyDownAndUp("B");
		assertKeyDownAndUp("c");
		assertKeyDownAndUp("c");
		assertNoEvents();
	}

	@Test
	public void testWithoutListeningToKeyUp() {

		this.input = openTestNode(() -> {
			var input = new TestInput("x");
			// only listen to KEYDOWN
			input.unlistenToEvent(DomEventType.KEYUP);
			return input;
		});

		sendKeysAndWait("x");
		assertKeyDown("x");
		assertNoEvents();
	}

	@Test
	public void testWithoutListeningToKeyDown() {

		this.input = openTestNode(() -> {
			var input = new TestInput("x");
			// only listen to KEYDOWN
			input.unlistenToEvent(DomEventType.KEYDOWN);
			return input;
		});

		sendKeysAndWait("x");
		assertKeyUp("x");
		assertNoEvents();
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

	private void assertKeyDownAndUp(String expectedKey) {

		assertKeyDown(expectedKey);
		assertKeyUp(expectedKey);
	}

	private void assertKeyDown(String expectedKey) {

		assertKeyEvent(DomEventType.KEYDOWN, expectedKey);
	}

	private void assertKeyUp(String expectedKey) {

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

	public void assertNoEvents() {

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

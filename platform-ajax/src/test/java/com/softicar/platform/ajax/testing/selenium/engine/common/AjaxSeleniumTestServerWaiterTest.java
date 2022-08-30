package com.softicar.platform.ajax.testing.selenium.engine.common;

import com.softicar.platform.ajax.testing.selenium.engine.level.low.AbstractAjaxSeleniumLowLevelTest;
import com.softicar.platform.common.core.thread.sleep.Sleep;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.event.IDomBlurEventHandler;
import com.softicar.platform.dom.event.IDomClickEventHandler;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.event.IDomFocusEventHandler;
import com.softicar.platform.dom.event.IDomInputEventHandler;
import com.softicar.platform.dom.input.DomTextInput;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.openqa.selenium.TimeoutException;

public class AjaxSeleniumTestServerWaiterTest extends AbstractAjaxSeleniumLowLevelTest {

	private static final Duration WAIT_TIMEOUT = Duration.ofSeconds(3);
	private final TestDiv testDiv;
	private final List<String> events;
	private Duration clickDelay;

	public AjaxSeleniumTestServerWaiterTest() {

		this.testDiv = openTestNode(TestDiv::new);
		this.events = new ArrayList<>();
		this.clickDelay = null;
	}

	/**
	 * In this test, we trigger a series of different events:
	 * <ol>
	 * <li>{@link DomEventType#FOCUS} on the input <b>A</b></li>
	 * <li>{@link DomEventType#INPUT} on the input <b>A</b></li>
	 * <li>{@link DomEventType#CHANGE} on the input <b>A</b></li>
	 * <li>{@link DomEventType#BLUR} on the input <b>A</b></li>
	 * <li>{@link DomEventType#FOCUS} on the input <b>B</b></li>
	 * <li>{@link DomEventType#CLICK} on the input <b>B</b></li>
	 * </ol>
	 * To ensure that {@link AjaxSeleniumTestServerWaiter} waits for all these
	 * events to be finished, before continuing, we block the last event, that
	 * is the {@link DomEventType#CLICK} on input <b>B</b> for half of the wait
	 * timeout of the {@link AjaxSeleniumTestServerWaiter}.
	 */
	@Test
	public void testWithoutWaitTimeout() {

		this.clickDelay = WAIT_TIMEOUT.dividedBy(2);

		triggerEvents();
		executeWaiter();

		assertEquals("[focus.A, input.A, change.A, blur.A, focus.B, click.B]", events.toString());
	}

	/**
	 * In this test, we trigger the same series of different events as in
	 * {@link #testWithoutWaitTimeout()}. But this time, we provoke a timeout of the
	 * {@link AjaxSeleniumTestServerWaiter} by blocking the the
	 * {@link DomEventType#CLICK} on input <b>B</b> for double the wait timeout
	 * of the {@link AjaxSeleniumTestServerWaiter}.
	 */
	@Test
	public void testWithWaitTimeout() {

		this.clickDelay = WAIT_TIMEOUT.multipliedBy(2);

		triggerEvents();
		assertException(TimeoutException.class, this::executeWaiter);
	}

	private void triggerEvents() {

		send(testDiv.inputA, "a");
		click(testDiv.inputB);
	}

	private void executeWaiter() {

		new AjaxSeleniumTestServerWaiter(getTestEngine()::getWebDriver)//
			.waitForServer(WAIT_TIMEOUT);
	}

	private class TestDiv extends DomDiv {

		private final TestInput inputA;
		private final TestInput inputB;

		public TestDiv() {

			this.inputA = new TestInput("A");
			this.inputB = new TestInput("B");

			appendChild(inputA);
			appendChild(inputB);
		}
	}

	private class TestInput extends DomTextInput implements IDomInputEventHandler, IDomBlurEventHandler, IDomFocusEventHandler, IDomClickEventHandler {

		private final String name;

		public TestInput(String name) {

			this.name = name;

			addChangeCallback(() -> events.add("change." + name));
		}

		@Override
		public void handleInput(IDomEvent event) {

			events.add("input." + name);
		}

		@Override
		public void handleFocus(IDomEvent event) {

			events.add("focus." + name);
		}

		@Override
		public void handleBlur(IDomEvent event) {

			events.add("blur." + name);
		}

		@Override
		public void handleClick(IDomEvent event) {

			Sleep.sleep(clickDelay);
			events.add("click." + name);
		}
	}
}

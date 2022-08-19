package com.softicar.platform.ajax.testing.selenium.engine.common;

import com.softicar.platform.ajax.testing.selenium.engine.level.low.AbstractAjaxSeleniumLowLevelTest;
import com.softicar.platform.common.core.thread.sleep.Sleep;
import com.softicar.platform.dom.elements.DomDiv;
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

	private static final Duration CLICK_HANDLER_DELAY = Duration.ofSeconds(1);
	private static final Duration WAIT_TIMEOUT = Duration.ofSeconds(10);
	private final TestDiv testDiv;
	private final List<String> events;

	public AjaxSeleniumTestServerWaiterTest() {

		this.testDiv = openTestNode(TestDiv::new);
		this.events = new ArrayList<>();
	}

	@Test
	public void test() {

		send(testDiv.input1, "a");
		click(testDiv.input2);

		new AjaxSeleniumTestServerWaiter(getTestEngine()::getWebDriver)//
			.waitForServer(WAIT_TIMEOUT);

		send(testDiv.input2, "b");

		assertEquals("[FOCUS#1, INPUT#1, BLUR#1, FOCUS#2, CLICK#2, INPUT#2]", events.toString());
	}

	@Test
	public void testWithTimeout() {

		send(testDiv.input1, "a");
		click(testDiv.input2);

		assertException(
			TimeoutException.class,
			() -> new AjaxSeleniumTestServerWaiter(getTestEngine()::getWebDriver)//
				.waitForServer(Duration.ZERO));
	}

	private class TestDiv extends DomDiv {

		private final TestInput input1;
		private final TestInput input2;

		public TestDiv() {

			input1 = new TestInput("1");
			input2 = new TestInput("2");

			appendChild(input1);
			appendChild(input2);
		}
	}

	private class TestInput extends DomTextInput implements IDomInputEventHandler, IDomBlurEventHandler, IDomFocusEventHandler, IDomClickEventHandler {

		private final String name;

		public TestInput(String name) {

			this.name = name;
		}

		@Override
		public void handleInput(IDomEvent event) {

			events.add("INPUT#" + name);
		}

		@Override
		public void handleFocus(IDomEvent event) {

			events.add("FOCUS#" + name);
		}

		@Override
		public void handleBlur(IDomEvent event) {

			events.add("BLUR#" + name);
		}

		@Override
		public void handleClick(IDomEvent event) {

			Sleep.sleep(CLICK_HANDLER_DELAY);
			events.add("CLICK#" + name);
		}
	}
}

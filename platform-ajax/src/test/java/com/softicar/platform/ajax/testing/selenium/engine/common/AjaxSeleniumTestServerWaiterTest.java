package com.softicar.platform.ajax.testing.selenium.engine.common;

import com.softicar.platform.ajax.testing.selenium.engine.level.low.AbstractAjaxSeleniumLowLevelTest;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.event.IDomBlurEventHandler;
import com.softicar.platform.dom.event.IDomClickEventHandler;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.event.IDomFocusEventHandler;
import com.softicar.platform.dom.event.IDomInputEventHandler;
import com.softicar.platform.dom.input.DomTextInput;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class AjaxSeleniumTestServerWaiterTest extends AbstractAjaxSeleniumLowLevelTest {

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
			.waitForServer(IAjaxSeleniumTestEngineWaitMethods.DEFAULT_TIMEOUT);

		assertEquals("[FOCUS#1, INPUT#1, BLUR#1, FOCUS#2, CLICK#2]", events.toString());
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

			events.add("CLICK#" + name);
		}
	}
}

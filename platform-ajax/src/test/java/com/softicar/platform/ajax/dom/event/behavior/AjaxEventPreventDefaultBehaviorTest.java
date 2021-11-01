package com.softicar.platform.ajax.dom.event.behavior;

import com.softicar.platform.ajax.dom.event.AbstractAjaxDomEventTestDiv;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.AbstractAjaxSeleniumLowLevelTest;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.interfaces.IAjaxSeleniumLowLevelTestEngineInput.Key;
import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.event.IDomTabKeyEventHandler;
import com.softicar.platform.dom.input.DomTextInput;
import org.junit.Test;

public class AjaxEventPreventDefaultBehaviorTest extends AbstractAjaxSeleniumLowLevelTest {

	@Test
	public void testWithDefaultPrevention() {

		TestDiv testDiv = openTestNode(TestDiv::new);

		send(testDiv.getInput1(), "foo");
		send(testDiv.getInput1(), Key.TAB);

		assertFocused(testDiv.getInput1());
	}

	@Test
	public void testWithoutDefaultPrevention() {

		TestDiv testDiv = openTestNode(() -> new TestDiv().setPreventDefaultBehavior(false));

		send(testDiv.getInput1(), "foo");
		send(testDiv.getInput1(), Key.TAB);

		assertFocused(testDiv.getInput2());
	}

	private static class TestDiv extends AbstractAjaxDomEventTestDiv {

		private final DomTextInput input1;
		private final DomTextInput input2;

		public TestDiv() {

			this.input1 = new Input();
			this.input2 = new Input();

			appendChild(input1);
			appendChild(input2);
		}

		public TestDiv setPreventDefaultBehavior(boolean prevent) {

			getDomEngine().setPreventDefaultBehavior(input1, DomEventType.TAB, prevent);
			return this;
		}

		public IDomElement getInput1() {

			return input1;
		}

		public IDomElement getInput2() {

			return input2;
		}

		private class Input extends DomTextInput implements IDomTabKeyEventHandler {

			@Override
			public void handleTabKey(IDomEvent event) {

				addEvent(event);
			}
		}
	}
}

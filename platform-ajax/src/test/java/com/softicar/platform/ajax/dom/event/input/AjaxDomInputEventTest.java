package com.softicar.platform.ajax.dom.event.input;

import com.softicar.platform.ajax.testing.selenium.engine.level.low.AbstractAjaxSeleniumLowLevelTest;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.interfaces.IAjaxSeleniumLowLevelTestEngineInput;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.event.IDomInputEventHandler;
import com.softicar.platform.dom.input.DomTextInput;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.Test;

public class AjaxDomInputEventTest extends AbstractAjaxSeleniumLowLevelTest {

	private final Input input;

	public AjaxDomInputEventTest() {

		this.input = openTestNode(() -> new Input());
	}

	@Test
	public void testSingleInputEvent() {

		send(input, "a");

		waitForServer();

		input.assertInputEvents("a");
		input.assertChangeEvents();
	}

	@Test
	public void testMultiInputEvents() {

		send(input, "abc");

		waitForServer();

		input.assertInputEvents("a", "abc");
		input.assertChangeEvents();
	}

	@Test
	public void testMultiInputEventsWithChangeEvent() {

		input.setBlocking(true);
		send(input, "abc");
		send(input, "def");
		send(input, IAjaxSeleniumLowLevelTestEngineInput.Key.TAB);
		send(input, "ghi");
		input.setBlocking(false);

		waitForServer();

		input.assertInputEvents("a", "abcdefghi");
		input.assertChangeEvents("abcdefghi");
	}

	private class Input extends DomTextInput implements IDomInputEventHandler {

		private final Collection<String> changeEventValues;
		private final Collection<String> inputEventValues;
		private volatile boolean blocking;

		public Input() {

			this.changeEventValues = new ArrayList<>();
			this.inputEventValues = new ArrayList<>();
			this.blocking = false;

			addChangeCallback(() -> {
				changeEventValues.add(getValueText());
				waitUntil(() -> !blocking);
			});
		}

		@Override
		public void handleInput(IDomEvent event) {

			inputEventValues.add(getValueText());
			waitUntil(() -> !blocking);
		}

		public void setBlocking(boolean blocking) {

			this.blocking = blocking;
		}

		public void assertChangeEvents(String...expectedValues) {

			assertEquals(List.of(expectedValues).toString(), changeEventValues.toString());
		}

		public void assertInputEvents(String...expectedValues) {

			assertEquals(List.of(expectedValues).toString(), inputEventValues.toString());
		}
	}
}

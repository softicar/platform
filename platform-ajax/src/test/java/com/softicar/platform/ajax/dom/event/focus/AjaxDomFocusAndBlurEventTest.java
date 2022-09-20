package com.softicar.platform.ajax.dom.event.focus;

import com.softicar.platform.ajax.testing.selenium.engine.level.low.AbstractAjaxSeleniumLowLevelTest;
import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.dom.element.DomElement;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.event.IDomBlurEventHandler;
import com.softicar.platform.dom.event.IDomChangeEventHandler;
import com.softicar.platform.dom.event.IDomClickEventHandler;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.event.IDomFocusEventHandler;
import com.softicar.platform.dom.node.IDomNode;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class AjaxDomFocusAndBlurEventTest extends AbstractAjaxSeleniumLowLevelTest {

	private final List<IDomEvent> events;
	private final TestDiv testDiv;

	public AjaxDomFocusAndBlurEventTest() {

		this.events = new ArrayList<>();
		this.testDiv = openTestNode(TestDiv::new);
	}

	@Test
	public void test() {

		clickFirstInputAndEnterTextAndThenClickSecondInput("foo");

		assertEquals("event count", 6, events.size());
		assertEvent(events.get(0), testDiv.input1, DomEventType.FOCUS);
		assertEvent(events.get(1), testDiv.input1, DomEventType.CLICK);
		assertEvent(events.get(2), testDiv.input1, DomEventType.CHANGE);
		assertEvent(events.get(3), testDiv.input1, DomEventType.BLUR);
		assertEvent(events.get(4), testDiv.input2, DomEventType.FOCUS);
		assertEvent(events.get(5), testDiv.input2, DomEventType.CLICK);
	}

	@Test
	public void testWithPreventDefaultOnMouseDown() {

		// prevent default behavior for mouse down on second input
		click(testDiv.preventButton);
		clickFirstInputAndEnterTextAndThenClickSecondInput("foo");

		// assert no focus change happened
		assertEquals("event count", 3, events.size());
		assertEvent(events.get(0), testDiv.input1, DomEventType.FOCUS);
		assertEvent(events.get(1), testDiv.input1, DomEventType.CLICK);
		assertEvent(events.get(2), testDiv.input2, DomEventType.CLICK);

		// reset default behavior for mouse down on second input
		click(testDiv.resetButton);
		events.clear();
		clickFirstInputAndEnterTextAndThenClickSecondInput("bar");

		// assert normal event sequence
		// TODO testing...

		Log.finfo("AjaxDomFocusAndBlurEventTest.testWithPreventDefaultOnMouseDown():");
		Log.finfo("events: %s", events.size());
		events.forEach(Log::finfo);

		assertEvent(events.get(0), testDiv.input1, DomEventType.FOCUS);
		assertEvent(events.get(1), testDiv.input1, DomEventType.CLICK);
		assertEvent(events.get(2), testDiv.input1, DomEventType.CHANGE);
		assertEvent(events.get(3), testDiv.input1, DomEventType.BLUR);
		assertEvent(events.get(4), testDiv.input2, DomEventType.FOCUS);
		assertEvent(events.get(5), testDiv.input2, DomEventType.CLICK);
		assertEquals("event count", 6, events.size());
	}

	private void clickFirstInputAndEnterTextAndThenClickSecondInput(String text) {

		click(testDiv.input1);
		send(testDiv.input1, text);
		click(testDiv.input2);
		waitForServer();
	}

	private void assertEvent(IDomEvent event, IDomNode expectedNode, DomEventType expectedType) {

		assertSame(expectedNode, event.getCurrentTarget());
		assertSame(expectedType, event.getType());
	}

	private class TestDiv extends DomDiv {

		public final TestInput input1;
		public final TestInput input2;
		public final DomButton preventButton;
		public final DomButton resetButton;

		public TestDiv() {

			this.input1 = new TestInput();
			this.input2 = new TestInput();
			this.preventButton = new DomButton()//
				.setLabel("Prevent")
				.setClickCallback(() -> getDomEngine().setPreventDefaultOnMouseDown(input2, true));
			this.resetButton = new DomButton()//
				.setLabel("Reset")
				.setClickCallback(() -> getDomEngine().setPreventDefaultOnMouseDown(input2, false));

			appendChild(input1);
			appendChild(input2);
			appendChild(preventButton);
			appendChild(resetButton);
		}
	}

	private class TestInput extends DomElement implements IDomFocusEventHandler, IDomBlurEventHandler, IDomClickEventHandler, IDomChangeEventHandler {

		public TestInput() {

			setAttribute("type", "text");
		}

		@Override
		public DomElementTag getTag() {

			return DomElementTag.INPUT;
		}

		@Override
		public void handleFocus(IDomEvent event) {

			events.add(event);
		}

		@Override
		public void handleBlur(IDomEvent event) {

			events.add(event);
		}

		@Override
		public void handleChange(IDomEvent event) {

			events.add(event);
		}

		@Override
		public void handleClick(IDomEvent event) {

			events.add(event);
		}
	}
}

package com.softicar.platform.ajax.dom.event.focus;

import com.softicar.platform.ajax.testing.selenium.engine.level.low.AbstractAjaxSeleniumLowLevelTest;
import com.softicar.platform.dom.element.DomElement;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.elements.DomDiv;
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

		click(testDiv.input1);
		send(testDiv.input1, "hello");
		click(testDiv.input2);
		waitForServer();

		assertEquals("event count", 6, events.size());

		assertEvent(events.get(0), testDiv.input1, DomEventType.FOCUS);
		assertEvent(events.get(1), testDiv.input1, DomEventType.CLICK);
		assertEvent(events.get(2), testDiv.input1, DomEventType.CHANGE);
		assertEvent(events.get(3), testDiv.input1, DomEventType.BLUR);

		assertEvent(events.get(4), testDiv.input2, DomEventType.FOCUS);
		assertEvent(events.get(5), testDiv.input2, DomEventType.CLICK);
	}

	private void assertEvent(IDomEvent event, IDomNode expectedNode, DomEventType expectedType) {

		assertSame(expectedNode, event.getCurrentTarget());
		assertSame(expectedType, event.getType());
	}

	private class TestDiv extends DomDiv {

		public final TestInput input1;
		public final TestInput input2;

		public TestDiv() {

			this.input1 = new TestInput();
			this.input2 = new TestInput();

			appendChild(input1);
			appendChild(input2);
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

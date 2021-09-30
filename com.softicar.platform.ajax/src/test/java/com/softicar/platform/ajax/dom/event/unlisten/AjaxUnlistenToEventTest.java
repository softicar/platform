package com.softicar.platform.ajax.dom.event.unlisten;

import com.softicar.platform.ajax.dom.event.AbstractAjaxDomEventTestDiv;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.AbstractAjaxSeleniumLowLevelTest;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.interfaces.IAjaxSeleniumLowLevelTestEngineInput.Key;
import com.softicar.platform.ajax.utils.TestButton;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.event.IDomTabKeyEventHandler;
import org.junit.Test;

public class AjaxUnlistenToEventTest extends AbstractAjaxSeleniumLowLevelTest {

	private final TestDiv testDiv;

	public AjaxUnlistenToEventTest() {

		this.testDiv = openTestNode(TestDiv::new);
	}

	@Test
	public void testUnlisten() {

		click(testDiv.getUnlistenButton());
		send(testDiv, Key.TAB);
		waitForServer();

		assertEquals(0, testDiv.getEvents().size());
	}

	@Test
	public void testUnlistenAndThenListen() {

		click(testDiv.getUnlistenButton());
		click(testDiv.getListenButton());
		send(testDiv, Key.TAB);
		waitForServer();

		assertEquals(1, testDiv.getEvents().size());
	}

	private static class TestDiv extends AbstractAjaxDomEventTestDiv implements IDomTabKeyEventHandler {

		private final TestButton listenButton;
		private final TestButton unlistenButton;

		public TestDiv() {

			this.listenButton = new TestButton(() -> listenToEvent(DomEventType.TAB));
			this.unlistenButton = new TestButton(() -> unlistenToEvent(DomEventType.TAB));

			appendChild(listenButton);
			appendChild(unlistenButton);
		}

		@Override
		public void handleTabKey(IDomEvent event) {

			addEvent(event);
		}

		public TestButton getListenButton() {

			return listenButton;
		}

		public TestButton getUnlistenButton() {

			return unlistenButton;
		}
	}
}

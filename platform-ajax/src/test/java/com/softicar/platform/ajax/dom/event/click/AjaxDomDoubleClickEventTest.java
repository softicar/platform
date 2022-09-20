package com.softicar.platform.ajax.dom.event.click;

import com.softicar.platform.ajax.dom.event.AbstractAjaxDomEventTestDiv;
import com.softicar.platform.ajax.testing.selenium.engine.common.geometry.AjaxSeleniumTestArea;
import com.softicar.platform.ajax.testing.selenium.engine.common.geometry.AjaxSeleniumTestPoint;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.AbstractAjaxSeleniumLowLevelTest;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.event.IDomDoubleClickEventHandler;
import com.softicar.platform.dom.event.IDomEvent;
import org.junit.Test;

public class AjaxDomDoubleClickEventTest extends AbstractAjaxSeleniumLowLevelTest {

	private static final int CLICK_X = 13;
	private static final int CLICK_Y = 17;
	private final TestDiv testDiv;

	public AjaxDomDoubleClickEventTest() {

		this.testDiv = openTestNode(TestDiv::new);
	}

	@Test
	public void test() {

		// click at specific location
		AjaxSeleniumTestArea viewportSize = getViewportSize();
		AjaxSeleniumTestPoint divLocation = getLocation(testDiv);
		doubleClickAt(testDiv, CLICK_X, CLICK_Y);
		waitForServer();

		// assert event and positions
		IDomEvent event = testDiv.getEvent();
		assertEquals(DomEventType.DBLCLICK, event.getType());
		assertSame(testDiv, event.getCurrentTarget());
		assertEquals(divLocation.getX() + CLICK_X, event.getClientX(), 0.1);
		assertEquals(divLocation.getY() + CLICK_Y, event.getClientY(), 0.1);
		assertEquals(CLICK_X, event.getRelativeX(), 0.1);
		assertEquals(CLICK_Y, event.getRelativeY(), 0.1);
		assertEquals(0, event.getScrollX(), 0.1);
		assertEquals(0, event.getScrollY(), 0.1);
		assertEquals(viewportSize.getWidth(), event.getWindowWidth(), 0.1);
		assertEquals(viewportSize.getHeight(), event.getWindowHeight(), 0.1);
	}

	private static class TestDiv extends AbstractAjaxDomEventTestDiv implements IDomDoubleClickEventHandler {

		@Override
		public void handleDoubleClick(IDomEvent event) {

			addEvent(event);
		}
	}
}

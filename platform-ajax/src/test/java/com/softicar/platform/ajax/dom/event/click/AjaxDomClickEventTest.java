package com.softicar.platform.ajax.dom.event.click;

import com.softicar.platform.ajax.dom.event.AbstractAjaxDomEventTestDiv;
import com.softicar.platform.ajax.testing.selenium.engine.common.geometry.AjaxSeleniumTestPoint;
import com.softicar.platform.ajax.testing.selenium.engine.common.geometry.AjaxSeleniumTestRectangle;
import com.softicar.platform.ajax.testing.selenium.engine.common.geometry.AjaxSeleniumTestSegment;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.AbstractAjaxSeleniumLowLevelTest;
import com.softicar.platform.common.core.thread.sleep.Sleep;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.event.DomModifier;
import com.softicar.platform.dom.event.IDomClickEventHandler;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.style.CssPixel;
import com.softicar.platform.dom.style.CssStyle;
import java.time.Duration;
import org.junit.Test;

public class AjaxDomClickEventTest extends AbstractAjaxSeleniumLowLevelTest {

	private static final int CLICK_X = 30;
	private static final int CLICK_Y = 20;
	private static final int SCROLL_X = 20;
	private static final int SCROLL_Y = 10;
	private final TestDiv testDiv;
	private final BackgroundDiv backgroundDiv;

	public AjaxDomClickEventTest() {

		this.backgroundDiv = openTestNode(BackgroundDiv::new);
		this.testDiv = backgroundDiv.getTestDiv();
	}

	@Test
	public void test() {

		// click at specific location
		AjaxSeleniumTestSegment viewportSize = getViewportSize();
		AjaxSeleniumTestPoint divLocation = getLocation(testDiv);
		AjaxSeleniumTestRectangle divRectangle = getRectangle(testDiv);
		clickAt(testDiv, CLICK_X, CLICK_Y);
		waitForServer();

		// assert event and positions
		IDomEvent event = testDiv.getEvent();
		assertEquals(DomEventType.CLICK, event.getType());
		assertSame(testDiv, event.getCurrentTarget());
		assertEquals(divLocation.getX() + CLICK_X, event.getClientX());
		assertEquals(divLocation.getY() + CLICK_Y, event.getClientY());
		assertEquals(CLICK_X, event.getRelativeX(), 0.1);
		assertEquals(CLICK_Y, event.getRelativeY(), 0.1);
		assertEquals(0, event.getScrollX(), 0.1);
		assertEquals(0, event.getScrollY(), 0.1);
		assertEquals(viewportSize.getWidth(), event.getWindowWidth());
		assertEquals(viewportSize.getHeight(), event.getWindowHeight());

		var boundingClientRect = event.getBoundingClientRect();
		assertEquals(divRectangle.getX(), boundingClientRect.getX(), 0.001);
		assertEquals(divRectangle.getY(), boundingClientRect.getY(), 0.001);
		assertEquals(divRectangle.getWidth(), boundingClientRect.getWidth(), 0.001);
		assertEquals(divRectangle.getHeight(), boundingClientRect.getHeight(), 0.001);
	}

	@Test
	public void testWithScrolledWindow() {

		// make viewport small enough for scrolling
		AjaxSeleniumTestSegment backgroundDivSize = getSize(backgroundDiv);
		setViewportSize(backgroundDivSize.getWidth() - SCROLL_X, backgroundDivSize.getHeight() - SCROLL_Y);

		// race condition: resizing apparently takes some time
		Sleep.sleep(Duration.ofMillis(500));

		// scroll the window
		scrollTo(SCROLL_X, SCROLL_Y);

		// click at specific location
		AjaxSeleniumTestSegment viewportSize = getViewportSize();
		AjaxSeleniumTestPoint divLocation = getLocation(testDiv);
		AjaxSeleniumTestRectangle divRectangle = getRectangle(testDiv);
		clickAt(testDiv, CLICK_X, CLICK_Y);
		waitForServer();

		// assert positions
		IDomEvent event = testDiv.getEvent();
		assertEquals(divLocation.getX() - SCROLL_X + CLICK_X, event.getClientX());
		assertEquals(divLocation.getY() - SCROLL_Y + CLICK_Y, event.getClientY());
		assertEquals(CLICK_X, event.getRelativeX(), 0.1);
		assertEquals(CLICK_Y, event.getRelativeY(), 0.1);
		assertEquals(SCROLL_X, event.getScrollX(), 0.1);
		assertEquals(SCROLL_Y, event.getScrollY(), 0.1);
		assertEquals(viewportSize.getWidth(), event.getWindowWidth());
		assertEquals(viewportSize.getHeight(), event.getWindowHeight());

		var boundingClientRect = event.getBoundingClientRect();
		assertEquals(divRectangle.getX() - SCROLL_X, boundingClientRect.getX(), 0.001);
		assertEquals(divRectangle.getY() - SCROLL_Y, boundingClientRect.getY(), 0.001);
		assertEquals(divRectangle.getWidth(), boundingClientRect.getWidth(), 0.001);
		assertEquals(divRectangle.getHeight(), boundingClientRect.getHeight(), 0.001);
	}

	@Test
	public void testWithAltKey() {

		click(testDiv, DomModifier.ALT);
		waitForServer();
		assertModifierKeys(true, false, false, false);
	}

	@Test
	public void testWithCtrlKey() {

		click(testDiv, DomModifier.CONTROL);
		waitForServer();
		assertModifierKeys(false, true, false, false);
	}

	@Test
	public void testWithMetaKey() {

		click(testDiv, DomModifier.META);
		waitForServer();
		assertModifierKeys(false, false, true, false);
	}

	@Test
	public void testWithShiftKey() {

		click(testDiv, DomModifier.SHIFT);
		waitForServer();
		assertModifierKeys(false, false, false, true);
	}

	private void assertModifierKeys(boolean alt, boolean ctrl, boolean meta, boolean shift) {

		IDomEvent event = testDiv.getEvent();
		assertNotNull(event);
		assertEquals(alt, event.isAltKey());
		assertEquals(ctrl, event.isCtrlKey());
		assertEquals(meta, event.isMetaKey());
		assertEquals(shift, event.isShiftKey());
	}

	private static class BackgroundDiv extends AbstractAjaxDomEventTestDiv {

		private final TestDiv testDiv;

		public BackgroundDiv() {

			super(500, 500);
			this.testDiv = new TestDiv();
			setStyle(CssStyle.PADDING, new CssPixel(100));
			appendChild(testDiv);
		}

		public TestDiv getTestDiv() {

			return testDiv;
		}
	}

	private static class TestDiv extends AbstractAjaxDomEventTestDiv implements IDomClickEventHandler {

		public TestDiv() {

			super(200, 100);
		}

		@Override
		public void handleClick(IDomEvent event) {

			addEvent(event);
		}
	}
}

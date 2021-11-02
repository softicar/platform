package com.softicar.platform.ajax.dom.event;

import com.softicar.platform.ajax.testing.selenium.engine.level.low.AbstractAjaxSeleniumLowLevelTest;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.event.IDomEvent;
import java.util.function.Supplier;

public abstract class AbstractAjaxDomEventTest extends AbstractAjaxSeleniumLowLevelTest {

	protected final AbstractAjaxDomEventTestDiv testDiv;

	protected AbstractAjaxDomEventTest(Supplier<? extends AbstractAjaxDomEventTestDiv> factory) {

		this.testDiv = openTestNode(factory);
	}

	protected void assertKeyboardEvent(AbstractAjaxDomEventTestDiv testDiv, DomEventType eventType, int keyCode) {

		IDomEvent event = testDiv.getEvent();

		assertSame(testDiv, event.getCurrentTarget());
		assertEquals(eventType, event.getType());
		assertEquals(Integer.valueOf(keyCode), event.getKeyCode());
		assertTrue(0 < event.getClientX());
		assertTrue(0 < event.getClientY());
		assertEquals(0, event.getScrollX(), 0.1);
		assertEquals(0, event.getScrollY(), 0.1);
		assertNull(event.getRelativeX());
		assertNull(event.getRelativeY());
	}
}

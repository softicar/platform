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

	protected void assertKeyboardEvent(AbstractAjaxDomEventTestDiv testDiv, DomEventType eventType, String key) {

		IDomEvent event = testDiv.getEvent();

		assertSame(testDiv, event.getCurrentTarget());
		assertEquals(eventType, event.getType());
		assertEquals(key, event.getKey());
		assertTrue(0 < event.getClientX());
		assertTrue(0 < event.getClientY());
		assertEquals(0, event.getScrollX(), 0.1);
		assertEquals(0, event.getScrollY(), 0.1);
		assertEquals(0, event.getRelativeX(), 0.1);
		assertEquals(0, event.getRelativeY(), 0.1);
	}
}

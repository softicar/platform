package com.softicar.platform.ajax.dom.event.escape;

import com.softicar.platform.ajax.dom.event.AbstractAjaxDomEventTest;
import com.softicar.platform.ajax.dom.event.AbstractAjaxDomEventTestDiv;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.AjaxSeleniumLowLevelTestEngineInput.Key;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.event.DomKeyCodes;
import com.softicar.platform.dom.event.IDomEscapeKeyEventHandler;
import com.softicar.platform.dom.event.IDomEvent;
import org.junit.Test;

public class AjaxDomEscapeEventTest extends AbstractAjaxDomEventTest {

	public AjaxDomEscapeEventTest() {

		super(TestDiv::new);
	}

	@Test
	public void test() {

		send(testDiv, Key.ESCAPE);
		waitForServer();
		assertKeyboardEvent(testDiv, DomEventType.ESCAPE, DomKeyCodes.ESCAPE);
	}

	private static class TestDiv extends AbstractAjaxDomEventTestDiv implements IDomEscapeKeyEventHandler {

		@Override
		public void handleEscapeKey(IDomEvent event) {

			addEvent(event);
		}
	}
}

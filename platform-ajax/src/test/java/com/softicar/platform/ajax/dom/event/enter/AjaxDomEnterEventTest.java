package com.softicar.platform.ajax.dom.event.enter;

import com.softicar.platform.ajax.dom.event.AbstractAjaxDomEventTest;
import com.softicar.platform.ajax.dom.event.AbstractAjaxDomEventTestDiv;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.interfaces.IAjaxSeleniumLowLevelTestEngineInput.Key;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.event.DomKeyCodes;
import com.softicar.platform.dom.event.IDomEnterKeyEventHandler;
import com.softicar.platform.dom.event.IDomEvent;
import org.junit.Test;

public class AjaxDomEnterEventTest extends AbstractAjaxDomEventTest {

	public AjaxDomEnterEventTest() {

		super(TestDiv::new);
	}

	@Test
	public void test() {

		send(testDiv, Key.ENTER);
		waitForServer();
		assertKeyboardEvent(testDiv, DomEventType.ENTER, DomKeyCodes.ENTER);
	}

	private static class TestDiv extends AbstractAjaxDomEventTestDiv implements IDomEnterKeyEventHandler {

		@Override
		public void handleEnterKey(IDomEvent event) {

			addEvent(event);
		}
	}
}

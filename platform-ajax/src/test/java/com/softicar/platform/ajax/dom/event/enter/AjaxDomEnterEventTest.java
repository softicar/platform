package com.softicar.platform.ajax.dom.event.enter;

import com.softicar.platform.ajax.dom.event.AbstractAjaxDomEventTest;
import com.softicar.platform.ajax.dom.event.AbstractAjaxDomEventTestDiv;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.AjaxSeleniumLowLevelTestEngineInput.Key;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.event.DomKeys;
import com.softicar.platform.dom.event.IDomEnterKeyEventHandler;
import com.softicar.platform.dom.event.IDomEvent;
import org.junit.After;
import org.junit.Test;

public class AjaxDomEnterEventTest extends AbstractAjaxDomEventTest {

	// FIXME temporary
	@After
	public void discardWebDriver() {

		testEngine.discardWebDriver();
	}

	public AjaxDomEnterEventTest() {

		super(TestDiv::new);
	}

	@Test
	public void test() {

		send(testDiv, Key.ENTER);
		waitForServer();
		assertKeyboardEvent(testDiv, DomEventType.ENTER, DomKeys.ENTER);
	}

	private static class TestDiv extends AbstractAjaxDomEventTestDiv implements IDomEnterKeyEventHandler {

		@Override
		public void handleEnterKey(IDomEvent event) {

			addEvent(event);
		}
	}
}

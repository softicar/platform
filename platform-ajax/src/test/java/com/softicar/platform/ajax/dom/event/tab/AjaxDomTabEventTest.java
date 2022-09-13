package com.softicar.platform.ajax.dom.event.tab;

import com.softicar.platform.ajax.dom.event.AbstractAjaxDomEventTest;
import com.softicar.platform.ajax.dom.event.AbstractAjaxDomEventTestDiv;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.AjaxSeleniumLowLevelTestEngineInput.Key;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.event.DomKeys;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.event.IDomTabKeyEventHandler;
import org.junit.Test;

public class AjaxDomTabEventTest extends AbstractAjaxDomEventTest {

	public AjaxDomTabEventTest() {

		super(TestDiv::new);
	}

	@Test
	public void test() {

		send(testDiv, Key.TAB);
		waitForServer();
		assertKeyboardEvent(testDiv, DomEventType.TAB, DomKeys.TAB);
	}

	private static class TestDiv extends AbstractAjaxDomEventTestDiv implements IDomTabKeyEventHandler {

		@Override
		public void handleTabKey(IDomEvent event) {

			addEvent(event);
		}
	}
}

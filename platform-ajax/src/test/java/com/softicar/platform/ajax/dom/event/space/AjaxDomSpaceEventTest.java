package com.softicar.platform.ajax.dom.event.space;

import com.softicar.platform.ajax.dom.event.AbstractAjaxDomEventTest;
import com.softicar.platform.ajax.dom.event.AbstractAjaxDomEventTestDiv;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.interfaces.IAjaxSeleniumLowLevelTestEngineInput.Key;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.event.DomKeyCodes;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.event.IDomSpaceKeyEventHandler;
import org.junit.Test;

public class AjaxDomSpaceEventTest extends AbstractAjaxDomEventTest {

	public AjaxDomSpaceEventTest() {

		super(TestDiv::new);
	}

	@Test
	public void test() {

		send(testDiv, Key.SPACE);
		waitForServer();
		assertKeyboardEvent(testDiv, DomEventType.SPACE, DomKeyCodes.SPACE);
	}

	private static class TestDiv extends AbstractAjaxDomEventTestDiv implements IDomSpaceKeyEventHandler {

		@Override
		public void handleSpaceKey(IDomEvent event) {

			addEvent(event);
		}
	}
}

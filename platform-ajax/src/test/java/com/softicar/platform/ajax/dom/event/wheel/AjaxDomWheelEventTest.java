package com.softicar.platform.ajax.dom.event.wheel;

import com.softicar.platform.ajax.dom.event.AbstractAjaxDomEventTest;
import com.softicar.platform.ajax.dom.event.AbstractAjaxDomEventTestDiv;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.event.IDomWheelEventHandler;
import org.junit.Test;

public class AjaxDomWheelEventTest extends AbstractAjaxDomEventTest {

	public AjaxDomWheelEventTest() {

		super(TestDiv::new);
	}

	@Test
	public void test() {

//		send(testDiv, Key.TAB);
//		waitForServer();
//		assertKeyboardEvent(testDiv, DomEventType.TAB, DomKeyCodes.TAB);
	}

	private static class TestDiv extends AbstractAjaxDomEventTestDiv implements IDomWheelEventHandler {

		@Override
		public void handleWheel(IDomEvent event) {

			addEvent(event);
		}
	}
}

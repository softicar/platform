package com.softicar.platform.ajax.dom.popup.button;

import com.softicar.platform.ajax.testing.selenium.engine.level.low.AbstractAjaxSeleniumLowLevelTest;
import com.softicar.platform.dom.DomTestMarker;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.bar.DomBar;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.button.popup.DomPopupButton;
import com.softicar.platform.dom.elements.popup.DomPopup;
import org.junit.Test;

public class AjaxDomPopupButtonTest extends AbstractAjaxSeleniumLowLevelTest {

	private static final int BODY_OFFSET = 8;

	private final TestDiv testDiv;

	public AjaxDomPopupButtonTest() {

		this.testDiv = openTestNode(TestDiv::new);
	}

	@Test
	public void testPopupIsRepositionedWhenReopened() {

		clickAt(testDiv.getPopupButton(), 20, 5);
		waitForServer();
		var popupLocation = getLocation(findNode(DomTestMarker.POPUP_FRAME).get());
		assertEquals(20 + BODY_OFFSET, popupLocation.getX());
		assertEquals(5 + BODY_OFFSET, popupLocation.getY());

		clickAt(testDiv.getPopupButton(), 10, 10);
		waitForServer();

		popupLocation = getLocation(findNode(DomTestMarker.POPUP_FRAME).get());
		assertEquals(10 + BODY_OFFSET, popupLocation.getX());
		assertEquals(10 + BODY_OFFSET, popupLocation.getY());
	}

	private class TestDiv extends DomDiv {

		private final DomButton popupButton;

		public TestDiv() {

			popupButton = appendChild(new DomBar()).appendChild(new DomPopupButton().setPopupFactory(DomPopup::new).setLabel("open popup"));
		}

		public DomButton getPopupButton() {

			return popupButton;
		}
	}
}

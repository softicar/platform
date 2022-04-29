package com.softicar.platform.dom.elements.dialog;

import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.dialog.testing.DomModalAlertNodes;
import com.softicar.platform.dom.elements.dialog.testing.IDomModalAlertNodes;
import com.softicar.platform.dom.elements.testing.node.tester.DomNodeTester;
import com.softicar.platform.dom.event.DomEventType;
import org.junit.Test;

public class DomModalAlertPopupTest extends AbstractDomModalDialogPopupTest {

	// ---------------- basics ---------------- //

	@Test
	public void testShowMessage() {

		var alert = showAlert();
		alert.getContent().assertContainsText(MESSAGE);
	}

	// ---------------- interaction with "close" button ---------------- //

	@Test
	public void testClickOnCloseButton() {

		var alert = showAlert();
		alert.getCloseButton().click();
		assertNoDisplayedModalDialog();
	}

	@Test
	public void testEnterOnCloseButton() {

		var alert = showAlert();
		alert.getCloseButton().sendEvent(DomEventType.ENTER);
		assertNoDisplayedModalDialog();
	}

	@Test
	public void testSpaceOnCloseButton() {

		var alert = showAlert();
		alert.getCloseButton().sendEvent(DomEventType.SPACE);
		assertNoDisplayedModalDialog();
	}

	// ---------------- private ---------------- //

	private IDomModalAlertNodes<DomNodeTester> showAlert() {

		setNodeSupplier(TestDiv::new);
		findButton(SHOW_BUTTON).click();
		return findDisplayedModalAlertOrFail();
	}

	private IDomModalAlertNodes<DomNodeTester> findDisplayedModalAlertOrFail() {

		return new DomModalAlertNodes<>(this::findNode);
	}

	private static class TestDiv extends DomDiv {

		public TestDiv() {

			appendChild(new DomButton())//
				.setLabel("spawn alert")
				.setClickCallback(new DomModalAlertPopup(MESSAGE)::show)
				.addMarker(SHOW_BUTTON);
		}
	}
}

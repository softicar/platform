package com.softicar.platform.dom.elements.dialog;

import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.dialog.testing.DomModalAlertNodes;
import com.softicar.platform.dom.elements.dialog.testing.IDomModalAlertNodes;
import com.softicar.platform.dom.elements.testing.node.tester.DomNodeTester;
import com.softicar.platform.dom.event.DomEventType;
import org.junit.Test;

public class DomModalAlertDialogTest extends AbstractDomModalDialogTest {

	// ---------------- basics ---------------- //

	@Test
	public void testContainsMessage() {

		var alert = openAlert();
		alert.getContent().assertContainsText(MESSAGE);
	}

	// ---------------- interaction with "close" button ---------------- //

	@Test
	public void testClickOnCloseButton() {

		var alert = openAlert();
		alert.getCloseButton().click();
		assertNoDisplayedModalDialog();
	}

	@Test
	public void testEnterOnCloseButton() {

		var alert = openAlert();
		alert.getCloseButton().sendEvent(DomEventType.ENTER);
		assertNoDisplayedModalDialog();
	}

	@Test
	public void testSpaceOnCloseButton() {

		var alert = openAlert();
		alert.getCloseButton().sendEvent(DomEventType.SPACE);
		assertNoDisplayedModalDialog();
	}

	// ---------------- private ---------------- //

	private IDomModalAlertNodes<DomNodeTester> openAlert() {

		setNodeSupplier(TestDiv::new);
		findButton(OPEN_BUTTON).click();
		return findDisplayedModalAlertOrFail();
	}

	private IDomModalAlertNodes<DomNodeTester> findDisplayedModalAlertOrFail() {

		return new DomModalAlertNodes<>(this::findNode);
	}

	private static class TestDiv extends DomDiv {

		public TestDiv() {

			appendChild(new DomButton())//
				.setLabel("open alert")
				.setClickCallback(new DomModalAlertDialog(MESSAGE)::open)
				.addMarker(OPEN_BUTTON);
		}
	}
}

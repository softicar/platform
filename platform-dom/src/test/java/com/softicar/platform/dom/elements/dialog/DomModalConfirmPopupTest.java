package com.softicar.platform.dom.elements.dialog;

import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.dialog.testing.DomModalConfirmNodes;
import com.softicar.platform.dom.elements.dialog.testing.IDomModalConfirmNodes;
import com.softicar.platform.dom.elements.testing.node.tester.DomNodeTester;
import com.softicar.platform.dom.event.DomEventType;
import org.junit.Test;

public class DomModalConfirmPopupTest extends AbstractDomModalDialogPopupTest {

	private static final String CANCEL_TEXT = "cancelled";
	private static final String CONFIRMATION_TEXT = "confirmed";

	// ---------------- basics ---------------- //

	@Test
	public void testContainsMessage() {

		var confirm = openConfirm(false);
		confirm.getContent().assertContainsText(MESSAGE);
	}

	// ---------------- interaction with "okay" button ---------------- //

	@Test
	public void testClickOnOkayButtonWithoutCancelCallback() {

		var confirm = openConfirm(false);
		confirm.getOkayButton().click();
		assertNoDisplayedModalDialog();
		assertConfirmed();
		assertNotCancelled();
	}

	@Test
	public void testClickOnOkayButtonWithCancelCallback() {

		var confirm = openConfirm(true);
		confirm.getOkayButton().click();
		assertNoDisplayedModalDialog();
		assertConfirmed();
		assertNotCancelled();
	}

	@Test
	public void testEnterOnOkayButtonWithoutCancelCallback() {

		var confirm = openConfirm(false);
		confirm.getOkayButton().sendEvent(DomEventType.ENTER);
		assertNoDisplayedModalDialog();
		assertConfirmed();
		assertNotCancelled();
	}

	@Test
	public void testEnterOnOkayButtonWithCancelCallback() {

		var confirm = openConfirm(true);
		confirm.getOkayButton().sendEvent(DomEventType.ENTER);
		assertNoDisplayedModalDialog();
		assertConfirmed();
		assertNotCancelled();
	}

	@Test
	public void testSpaceOnOkayButtoWithoutCancelCallbackn() {

		var confirm = openConfirm(false);
		confirm.getOkayButton().sendEvent(DomEventType.SPACE);
		assertNoDisplayedModalDialog();
		assertConfirmed();
		assertNotCancelled();
	}

	@Test
	public void testSpaceOnOkayButtoWithCancelCallbackn() {

		var confirm = openConfirm(true);
		confirm.getOkayButton().sendEvent(DomEventType.SPACE);
		assertNoDisplayedModalDialog();
		assertConfirmed();
		assertNotCancelled();
	}

	// ---------------- interaction with "cancel" button ---------------- //

	@Test
	public void testClickOnCancelButtonWithoutCancelCallback() {

		var confirm = openConfirm(false);
		confirm.getCancelButton().click();
		assertNoDisplayedModalDialog();
		assertNotConfirmed();
		assertNotCancelled();
	}

	@Test
	public void testClickOnCancelButtonWithCancelCallback() {

		var confirm = openConfirm(true);
		confirm.getCancelButton().click();
		assertNoDisplayedModalDialog();
		assertNotConfirmed();
		assertCancelled();
	}

	@Test
	public void testEnterOnCancelButtonWithoutCancelCallback() {

		var confirm = openConfirm(false);
		confirm.getCancelButton().sendEvent(DomEventType.ENTER);
		assertNoDisplayedModalDialog();
		assertNotConfirmed();
		assertNotCancelled();
	}

	@Test
	public void testEnterOnCancelButtonWithCancelCallback() {

		var confirm = openConfirm(true);
		confirm.getCancelButton().sendEvent(DomEventType.ENTER);
		assertNoDisplayedModalDialog();
		assertNotConfirmed();
		assertCancelled();
	}

	@Test
	public void testSpaceOnCancelButtonWithoutCancelCallback() {

		var confirm = openConfirm(false);
		confirm.getCancelButton().sendEvent(DomEventType.SPACE);
		assertNoDisplayedModalDialog();
		assertNotConfirmed();
		assertNotCancelled();
	}

	@Test
	public void testSpaceOnCancelButtonWithCancelCallback() {

		var confirm = openConfirm(true);
		confirm.getCancelButton().sendEvent(DomEventType.SPACE);
		assertNoDisplayedModalDialog();
		assertNotConfirmed();
		assertCancelled();
	}

	// ---------------- interaction with frame ---------------- //

	@Test
	public void testEscapeOnFrameWithoutCancelCallback() {

		var confirm = openConfirm(false);
		confirm.getFrame().sendEvent(DomEventType.ESCAPE);
		assertNoDisplayedModalDialog();
		assertNotConfirmed();
		assertNotCancelled();
	}

	@Test
	public void testEscapeOnFrameWithCancelCallback() {

		var confirm = openConfirm(true);
		confirm.getFrame().sendEvent(DomEventType.ESCAPE);
		assertNoDisplayedModalDialog();
		assertNotConfirmed();
		assertCancelled();
	}

	// ---------------- interaction with backdrop ---------------- //

	@Test
	public void testClickOnBackdropWithoutCancelCallback() {

		var confirm = openConfirm(false);
		confirm.getBackdrop().click();
		assertNoDisplayedModalDialog();
		assertNotConfirmed();
		assertNotCancelled();
	}

	@Test
	public void testClickOnBackdropWithCancelCallback() {

		var confirm = openConfirm(true);
		confirm.getBackdrop().click();
		assertNoDisplayedModalDialog();
		assertNotConfirmed();
		assertCancelled();
	}

	// ---------------- private ---------------- //

	private IDomModalConfirmNodes<DomNodeTester> openConfirm(boolean withCancelCallback) {

		setNodeSupplier(() -> new TestDiv(withCancelCallback));
		findButton(OPEN_BUTTON).click();
		return findDisplayedModalConfirmOrFail();
	}

	private IDomModalConfirmNodes<DomNodeTester> findDisplayedModalConfirmOrFail() {

		return new DomModalConfirmNodes<>(this::findNode);
	}

	private void assertConfirmed() {

		findNode(OUTPUT_ELEMENT).assertContainsText(CONFIRMATION_TEXT);
	}

	private void assertNotConfirmed() {

		findNode(OUTPUT_ELEMENT).assertDoesNotContainText(CONFIRMATION_TEXT);
	}

	private void assertCancelled() {

		findNode(OUTPUT_ELEMENT).assertContainsText(CANCEL_TEXT);
	}

	private void assertNotCancelled() {

		findNode(OUTPUT_ELEMENT).assertDoesNotContainText(CANCEL_TEXT);
	}

	private static class TestDiv extends DomDiv {

		private final DomDiv outputElement;

		public TestDiv(boolean withCancelCallback) {

			outputElement = appendChild(new DomDiv());
			outputElement.addMarker(OUTPUT_ELEMENT);

			var confirmPopup = new DomModalConfirmPopup(//
				this::handleConfirm,
				withCancelCallback? this::handleCancel : null,
				MESSAGE);
			appendChild(
				new DomButton()//
					.setLabel("open confirm")
					.setClickCallback(confirmPopup::open)
					.addMarker(OPEN_BUTTON));
		}

		private void handleConfirm() {

			outputElement.appendText(CONFIRMATION_TEXT);
		}

		private void handleCancel() {

			outputElement.appendText(CANCEL_TEXT);
		}
	}
}

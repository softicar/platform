package com.softicar.platform.dom.elements.dialog;

import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.dialog.testing.DomModalConfirmNodes;
import com.softicar.platform.dom.elements.dialog.testing.IDomModalConfirmNodes;
import com.softicar.platform.dom.elements.testing.node.tester.DomNodeTester;
import com.softicar.platform.dom.event.DomEventType;
import org.junit.Test;

public class DomModalConfirmPopupTest extends AbstractDomModalDialogPopupTest {

	private static final String CONFIRMATION_TEXT = "confirmed";

	// ---------------- basics ---------------- //

	@Test
	public void testShowMessage() {

		var confirm = showConfirm();
		confirm.getContent().assertContainsText(MESSAGE);
	}

	// ---------------- interaction with "okay" button ---------------- //

	@Test
	public void testClickOnOkayButton() {

		var confirm = showConfirm();
		confirm.getOkayButton().click();
		assertNoDisplayedModalDialog();
		assertConfirmed();
	}

	@Test
	public void testEnterOnOkayButton() {

		var confirm = showConfirm();
		confirm.getOkayButton().sendEvent(DomEventType.ENTER);
		assertNoDisplayedModalDialog();
		assertConfirmed();
	}

	@Test
	public void testSpaceOnOkayButton() {

		var confirm = showConfirm();
		confirm.getOkayButton().sendEvent(DomEventType.SPACE);
		assertNoDisplayedModalDialog();
		assertConfirmed();
	}

	// ---------------- interaction with "cancel" button ---------------- //

	@Test
	public void testClickOnCancelButton() {

		var confirm = showConfirm();
		confirm.getCancelButton().click();
		assertNoDisplayedModalDialog();
		assertNotConfirmed();
	}

	@Test
	public void testEnterOnCancelButton() {

		var confirm = showConfirm();
		confirm.getCancelButton().sendEvent(DomEventType.ENTER);
		assertNoDisplayedModalDialog();
		assertNotConfirmed();
	}

	@Test
	public void testSpaceOnCancelButton() {

		var confirm = showConfirm();
		confirm.getCancelButton().sendEvent(DomEventType.SPACE);
		assertNoDisplayedModalDialog();
		assertNotConfirmed();
	}

	// ---------------- interaction with frame ---------------- //

	@Test
	public void testEscapeOnFrame() {

		var confirm = showConfirm();
		confirm.getFrame().sendEvent(DomEventType.ESCAPE);
		assertNoDisplayedModalDialog();
		assertNotConfirmed();
	}

	// ---------------- interaction with backdrop ---------------- //

	@Test
	public void testClickOnBackdrop() {

		var confirm = showConfirm();
		confirm.getBackdrop().click();
		assertNoDisplayedModalDialog();
		assertNotConfirmed();
	}

	// ---------------- private ---------------- //

	private IDomModalConfirmNodes<DomNodeTester> showConfirm() {

		setNodeSupplier(TestDiv::new);
		findButton(SHOW_BUTTON).click();
		return findDisplayedModalConfirmOrFail();
	}

	private IDomModalConfirmNodes<DomNodeTester> findDisplayedModalConfirmOrFail() {

		return new DomModalConfirmNodes<>(this::findNode);
	}

	private void assertConfirmed() {

		findNode(OUTPUT_ELEMENT).assertContainsText(CONFIRMATION_TEXT);
	}

	private void assertNotConfirmed() {

		findNode(OUTPUT_ELEMENT).assertContainsNoText();
	}

	private static class TestDiv extends DomDiv {

		public TestDiv() {

			var outputElement = appendChild(new DomDiv());
			outputElement.addMarker(OUTPUT_ELEMENT);

			appendChild(
				new DomButton()//
					.setLabel("spawn confirm")
					.setClickCallback(new DomModalConfirmPopup(() -> outputElement.appendText(CONFIRMATION_TEXT), MESSAGE)::show)
					.addMarker(SHOW_BUTTON));
		}
	}
}

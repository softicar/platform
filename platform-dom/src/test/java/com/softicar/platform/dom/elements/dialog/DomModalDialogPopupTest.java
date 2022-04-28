package com.softicar.platform.dom.elements.dialog;

import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.dialog.testing.DomModalDialogNodes;
import com.softicar.platform.dom.elements.dialog.testing.IDomModalDialogNodes;
import com.softicar.platform.dom.elements.testing.node.tester.DomNodeTester;
import com.softicar.platform.dom.event.DomEventType;
import org.junit.Test;

public class DomModalDialogPopupTest extends AbstractDomModalDialogPopupTest {

	// ---------------- interaction with frame ---------------- //

	@Test
	public void testClickOnFrame() {

		var dialog = showDialog();
		dialog.getFrame().click();
		findDisplayedModalDialogOrFail();
	}

	@Test
	public void testEnterOnFrame() {

		var dialog = showDialog();
		dialog.getFrame().sendEvent(DomEventType.ENTER);
		findDisplayedModalDialogOrFail();
	}

	@Test
	public void testSpaceOnFrame() {

		var dialog = showDialog();
		dialog.getFrame().sendEvent(DomEventType.SPACE);
		findDisplayedModalDialogOrFail();
	}

	@Test
	public void testEscapeOnFrame() {

		var dialog = showDialog();
		dialog.getFrame().sendEvent(DomEventType.ESCAPE);
		assertNoDisplayedModalDialog();
	}

	// ---------------- interaction with backdrop ---------------- //

	@Test
	public void testClickOnBackdrop() {

		var dialog = showDialog();
		dialog.getBackdrop().click();
		assertNoDisplayedModalDialog();
	}

	// ---------------- private ---------------- //

	private IDomModalDialogNodes<DomNodeTester> showDialog() {

		setNodeSupplier(TestDiv::new);
		findButton(SHOW_BUTTON).click();
		return findDisplayedModalDialogOrFail();
	}

	private IDomModalDialogNodes<DomNodeTester> findDisplayedModalDialogOrFail() {

		return new DomModalDialogNodes<>(this::findNode);
	}

	private static class TestDiv extends DomDiv {

		public TestDiv() {

			appendChild(new DomButton())//
				.setLabel("show dialog")
				.setClickCallback(new DomModalDialogPopup()::open)
				.setMarker(SHOW_BUTTON);
		}
	}
}

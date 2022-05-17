package com.softicar.platform.dom.elements.dialog;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.common.core.interfaces.IStaticObject;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomElementsImages;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.dialog.testing.DomModalDialogNodes;
import com.softicar.platform.dom.elements.dialog.testing.IDomModalDialogNodes;
import com.softicar.platform.dom.elements.testing.node.tester.DomNodeTester;
import com.softicar.platform.dom.event.DomEventType;
import org.junit.Test;
import org.mockito.Mockito;

public class DomModalDialogPopupTest extends AbstractDomModalDialogPopupTest {

	private static final IStaticObject CANCEL_BUTTON = Mockito.mock(IStaticObject.class);
	private static final IDisplayString CANCEL_LABEL = IDisplayString.create("cancel");
	private static final IStaticObject OKAY_BUTTON = Mockito.mock(IStaticObject.class);
	private static final IDisplayString OKAY_LABEL = IDisplayString.create("okay");

	private final DomModalDialogPopup dialogPopup;
	private final DomDiv testDiv;

	public DomModalDialogPopupTest() {

		this.dialogPopup = new DomModalDialogPopup();
		this.testDiv = new DomDiv();
		this.testDiv
			.appendChild(new DomButton())//
			.setLabel("open dialog")
			.setClickCallback(dialogPopup::open)
			.addMarker(OPEN_BUTTON);

		setNodeSupplier(() -> testDiv);
	}

	@Test
	public void test() {

		var beforeOpenCallback = new Callback();
		var okayCallback = new Callback();
		var cancelCallback = new Callback();

		dialogPopup.appendContent(MESSAGE);
		dialogPopup.setCallbackBeforeOpen(beforeOpenCallback);
		dialogPopup.addMarker(DomModalDialogMarker.POPUP);
		dialogPopup.appendActionButton(DomElementsImages.DIALOG_OKAY.getResource(), OKAY_LABEL, okayCallback, OKAY_BUTTON);
		dialogPopup.appendActionButton(DomElementsImages.DIALOG_CANCEL.getResource(), CANCEL_LABEL, cancelCallback, CANCEL_BUTTON);

		beforeOpenCallback.assertNotCalled();
		assertNoDisplayedModalDialog();

		findButton(OPEN_BUTTON).click();
		findDisplayedModalDialogOrFail();

		var dialog = findPopup(DomModalDialogMarker.POPUP);
		dialog.assertContainsText(MESSAGE);

		var okayButton = findButton(OKAY_BUTTON);
		okayButton.assertText(OKAY_LABEL);

		var cancelButton = findButton(CANCEL_BUTTON);
		cancelButton.assertText(CANCEL_LABEL);

		okayButton.click();

		assertNoDisplayedModalDialog();
		beforeOpenCallback.assertCalled();
		okayCallback.assertCalled();
		cancelCallback.assertNotCalled();
	}

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

		findButton(OPEN_BUTTON).click();
		return findDisplayedModalDialogOrFail();
	}

	private IDomModalDialogNodes<DomNodeTester> findDisplayedModalDialogOrFail() {

		return new DomModalDialogNodes<>(this::findNode);
	}

	private static class Callback implements INullaryVoidFunction {

		private boolean called;

		public Callback() {

			this.called = false;
		}

		@Override
		public void apply() {

			if (!called) {
				this.called = true;
			} else {
				throw new IllegalStateException();
			}
		}

		public void assertCalled() {

			assertTrue(called);
		}

		public void assertNotCalled() {

			assertFalse(called);
		}
	}
}

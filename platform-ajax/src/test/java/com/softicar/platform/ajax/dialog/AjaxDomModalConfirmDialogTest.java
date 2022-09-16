package com.softicar.platform.ajax.dialog;

import com.softicar.platform.ajax.testing.selenium.engine.level.low.AbstractAjaxSeleniumLowLevelTest;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.AjaxSeleniumLowLevelTestEngineInput.Key;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.dialog.DomModalConfirmDialog;
import com.softicar.platform.dom.elements.dialog.testing.IDomModalConfirmNodes;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.dom.parent.IDomParentElement;
import org.junit.Test;

/**
 * Unit test for {@link DomModalConfirmDialog}, based upon Selenium.
 *
 * @author Alexander Schmidt
 */
public class AjaxDomModalConfirmDialogTest extends AbstractAjaxSeleniumLowLevelTest {

	private static final int BACKDROP_DISMISS_CLICK_OFFSET = 20;
	private static final String CONFIRMATION_TEXT = "confirmed";
	private static final IDisplayString MESSAGE = IDisplayString.create("some message");
	private final TestDiv testDiv;

	public AjaxDomModalConfirmDialogTest() {

		this.testDiv = openTestNode(TestDiv::new);
	}

	// ---------------- basics ---------------- //

	@Test
	public void testShowMessage() {

		var confirm = showConfirm();
		assertEquals(MESSAGE.toString(), getText(confirm.getContent()));
	}

	// ---------------- interaction with "okay" button ---------------- //

	@Test
	public void testClickOnOkayButton() {

		var confirm = showConfirm();
		click(confirm.getOkayButton());
		waitForServer();
		assertNoModalDialog();
		assertConfirmed();
	}

	@Test
	public void testEnterOnOkayButton() {

		var confirm = showConfirm();
		send(confirm.getOkayButton(), Key.ENTER);
		waitForServer();
		assertNoModalDialog();
		assertConfirmed();
	}

	@Test
	public void testSpaceOnOkayButton() {

		var confirm = showConfirm();
		send(confirm.getOkayButton(), Key.SPACE);
		waitForServer();
		assertNoModalDialog();
		assertConfirmed();
	}

	@Test
	public void testEscapeOnOkayButton() {

		var confirm = showConfirm();
		send(confirm.getOkayButton(), Key.ESCAPE);
		waitForServer();
		assertNoModalDialog();
		assertNotConfirmed();
	}

	// ---------------- interaction with "cancel" button ---------------- //

	@Test
	public void testClickOnCancelButton() {

		var confirm = showConfirm();
		click(confirm.getCancelButton());
		waitForServer();
		assertNoModalDialog();
		assertNotConfirmed();
	}

	@Test
	public void testEnterOnCancelButton() {

		var confirm = showConfirm();
		send(confirm.getCancelButton(), Key.ENTER);
		waitForServer();
		assertNoModalDialog();
		assertNotConfirmed();
	}

	@Test
	public void testSpaceOnCancelButton() {

		var confirm = showConfirm();
		send(confirm.getCancelButton(), Key.SPACE);
		waitForServer();
		assertNoModalDialog();
		assertNotConfirmed();
	}

	@Test
	public void testEscapeOnCancelButton() {

		var confirm = showConfirm();
		send(confirm.getCancelButton(), Key.ESCAPE);
		waitForServer();
		assertNoModalDialog();
		assertNotConfirmed();
	}

	// ---------------- interaction with frame ---------------- //

	@Test
	public void testEscapeOnFrame() {

		var confirm = showConfirm();
		send(confirm.getFrame(), Key.ESCAPE);
		waitForServer();
		assertNoModalDialog();
		assertNotConfirmed();
	}

	// ---------------- interaction with backdrop ---------------- //

	@Test
	public void testClickOnBackdrop() {

		int offset = BACKDROP_DISMISS_CLICK_OFFSET;

		var confirm = showConfirm();
		clickAt(confirm.getBackdrop(), offset, offset);
		waitForServer();
		assertNoModalDialog();
		assertNotConfirmed();
	}

	// ---------------- focus ---------------- //

	@Test
	public void testOkayButtonFocusedAfterTabOnFrame() {

		var confirm = showConfirm();
		send(confirm.getFrame(), Key.TAB);
		waitForServer();
		assertFocused(confirm.getOkayButton());
	}

	@Test
	public void testCancelButtonFocusedAfterTabOnOkayButton() {

		var confirm = showConfirm();
		send(confirm.getOkayButton(), Key.TAB);
		waitForServer();
		assertFocused(confirm.getCancelButton());
	}

	@Test
	public void testFrameFocusedAfterTabOnCancelButton() {

		var confirm = showConfirm();
		send(confirm.getCancelButton(), Key.TAB);
		waitForServer();
		assertFocused(confirm.getFrame());
	}

	// ---------------- private ---------------- //

	private IDomModalConfirmNodes<IDomNode> showConfirm() {

		click(testDiv.getButton());
		waitForServer();
		return findModalConfirmOrFail();
	}

	private void assertConfirmed() {

		assertEquals(CONFIRMATION_TEXT, getText(testDiv.getOutputElement()));
	}

	private void assertNotConfirmed() {

		assertEquals("", getText(testDiv.getOutputElement()));
	}

	private static class TestDiv extends DomDiv {

		private final DomButton button;
		private final DomDiv outputElement;

		public TestDiv() {

			this.outputElement = new DomDiv();
			this.button = new DomButton()//
				.setLabel("spawn confirm")
				.setClickCallback(new DomModalConfirmDialog(() -> outputElement.appendText(CONFIRMATION_TEXT), MESSAGE)::open);
			appendChild(button);
			appendChild(outputElement);
		}

		public DomButton getButton() {

			return button;
		}

		public IDomParentElement getOutputElement() {

			return outputElement;
		}
	}
}

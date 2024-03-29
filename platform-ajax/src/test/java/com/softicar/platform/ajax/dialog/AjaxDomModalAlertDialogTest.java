package com.softicar.platform.ajax.dialog;

import com.softicar.platform.ajax.testing.selenium.engine.level.low.AbstractAjaxSeleniumLowLevelTest;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.AjaxSeleniumLowLevelTestEngineInput.Key;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.dialog.DomModalAlertDialog;
import com.softicar.platform.dom.elements.dialog.testing.IDomModalAlertNodes;
import com.softicar.platform.dom.node.IDomNode;
import org.junit.Test;

/**
 * Unit test for {@link DomModalAlertDialog}, based upon Selenium.
 *
 * @author Alexander Schmidt
 */
public class AjaxDomModalAlertDialogTest extends AbstractAjaxSeleniumLowLevelTest {

	private static final IDisplayString MESSAGE = IDisplayString.create("some message");
	private final TestDiv testDiv;

	public AjaxDomModalAlertDialogTest() {

		this.testDiv = openTestNode(TestDiv::new);
	}

	// ---------------- basics ---------------- //

	@Test
	public void testShowMessage() {

		var alert = showAlert();
		assertEquals(MESSAGE.toString(), getText(alert.getContent()));
	}

	// ---------------- interaction with "close" button ---------------- //

	@Test
	public void testClickOnCloseButton() {

		var alert = showAlert();
		click(alert.getCloseButton());
		waitForServer();
		assertNoModalDialog();
	}

	@Test
	public void testEnterOnCloseButton() {

		var alert = showAlert();
		send(alert.getCloseButton(), Key.ENTER);
		waitForServer();
		assertNoModalDialog();
	}

	@Test
	public void testSpaceOnCloseButton() {

		var alert = showAlert();
		send(alert.getCloseButton(), Key.SPACE);
		waitForServer();
		assertNoModalDialog();
	}

	@Test
	public void testEscapeOnCloseButton() {

		var alert = showAlert();
		send(alert.getCloseButton(), Key.ESCAPE);
		waitForServer();
		assertNoModalDialog();
	}

	// ---------------- focus ---------------- //

	@Test
	public void testCloseButtonFocusedAfterTabOnFrame() {

		var alert = showAlert();
		send(alert.getFrame(), Key.TAB);
		waitForServer();
		assertFocused(alert.getCloseButton());
	}

	@Test
	public void testFrameFocusedAfterTabOnCloseButton() {

		var alert = showAlert();
		send(alert.getCloseButton(), Key.TAB);
		waitForServer();
		assertFocused(alert.getFrame());
	}

	// ---------------- private ---------------- //

	private IDomModalAlertNodes<IDomNode> showAlert() {

		click(testDiv.getButton());
		waitForServer();
		return findModalAlertOrFail();
	}

	private static class TestDiv extends DomDiv {

		private final DomButton button;

		public TestDiv() {

			this.button = appendChild(new DomButton())//
				.setLabel("spawn alert")
				.setClickCallback(new DomModalAlertDialog(MESSAGE)::open);
		}

		public DomButton getButton() {

			return button;
		}
	}
}

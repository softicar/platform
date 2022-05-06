package com.softicar.platform.ajax.dialog;

import com.softicar.platform.ajax.testing.selenium.engine.level.low.AbstractAjaxSeleniumLowLevelTest;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.interfaces.IAjaxSeleniumLowLevelTestEngineInput.Key;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.dialog.DomModalAlertPopup;
import com.softicar.platform.dom.elements.dialog.testing.IDomModalAlertNodes;
import com.softicar.platform.dom.node.IDomNode;
import org.junit.Test;

/**
 * Unit test for {@link DomModalAlertPopup}, based upon Selenium.
 *
 * @author Alexander Schmidt
 */
public class AjaxModalAlertPopupTest extends AbstractAjaxSeleniumLowLevelTest {

	private static final IDisplayString MESSAGE = IDisplayString.create("some message");
	private final TestDiv testDiv;

	public AjaxModalAlertPopupTest() {

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
		assertFocused(alert.getCloseButton());
	}

	@Test
	public void testFrameFocusedAfterTabOnCloseButton() {

		var alert = showAlert();
		send(alert.getCloseButton(), Key.TAB);
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
				.setClickCallback(new DomModalAlertPopup(MESSAGE)::open);
		}

		public DomButton getButton() {

			return button;
		}
	}
}

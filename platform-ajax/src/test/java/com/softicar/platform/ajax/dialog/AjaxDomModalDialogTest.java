package com.softicar.platform.ajax.dialog;

import com.softicar.platform.ajax.testing.selenium.engine.level.low.AbstractAjaxSeleniumLowLevelTest;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.interfaces.IAjaxSeleniumLowLevelTestEngineInput.Key;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.dialog.DomModalDialog;
import com.softicar.platform.dom.elements.dialog.testing.IDomModalDialogNodes;
import com.softicar.platform.dom.node.IDomNode;
import org.junit.Test;

/**
 * Unit test for {@link DomModalDialog}, based upon Selenium.
 *
 * @author Alexander Schmidt
 */
public class AjaxDomModalDialogTest extends AbstractAjaxSeleniumLowLevelTest {

	private static final int BACKDROP_DISMISS_CLICK_OFFSET = 20;
	private final TestDiv testDiv;

	public AjaxDomModalDialogTest() {

		this.testDiv = openTestNode(TestDiv::new);
	}

	// ---------------- interaction with frame ---------------- //

	@Test
	public void testClickOnFrame() {

		var dialog = showDialog();
		click(dialog.getFrame());
		waitForServer();
		findModalDialogOrFail();
	}

	@Test
	public void testEnterOnFrame() {

		var dialog = showDialog();
		send(dialog.getFrame(), Key.ENTER);
		waitForServer();
		findModalDialogOrFail();
	}

	@Test
	public void testSpaceOnFrame() {

		var dialog = showDialog();
		send(dialog.getFrame(), Key.SPACE);
		waitForServer();
		findModalDialogOrFail();
	}

	@Test
	public void testEscapeOnFrame() {

		var dialog = showDialog();
		send(dialog.getFrame(), Key.ESCAPE);
		waitForServer();
		assertNoModalDialog();
	}

	// ---------------- interaction with backdrop ---------------- //

	@Test
	public void testClickOnBackdrop() {

		int offset = BACKDROP_DISMISS_CLICK_OFFSET;

		var dialog = showDialog();
		clickAt(dialog.getBackdrop(), offset, offset);
		waitForServer();
		assertNoModalDialog();
	}

	// ---------------- focus ---------------- //

	@Test
	public void testFrameFocusedAfterShow() {

		var dialog = showDialog();
		assertFocused(dialog.getFrame());
	}

	@Test
	public void testFrameStillFocusedAfterTabOnFrame() {

		var dialog = showDialog();
		send(dialog.getFrame(), Key.TAB);
		assertFocused(dialog.getFrame());
	}

	// ---------------- private ---------------- //

	private IDomModalDialogNodes<IDomNode> showDialog() {

		click(testDiv.getButton());
		waitForServer();
		return findModalDialogOrFail();
	}

	private static class TestDiv extends DomDiv {

		private final DomButton button;

		public TestDiv() {

			this.button = appendChild(new DomButton())//
				.setLabel("spawn dialog")
				.setClickCallback(new DomModalDialog()::open);
		}

		public DomButton getButton() {

			return button;
		}
	}
}

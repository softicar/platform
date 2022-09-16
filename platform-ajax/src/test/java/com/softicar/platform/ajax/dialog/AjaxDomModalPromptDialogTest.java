package com.softicar.platform.ajax.dialog;

import com.softicar.platform.ajax.testing.selenium.engine.level.low.AbstractAjaxSeleniumLowLevelTest;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.AjaxSeleniumLowLevelTestEngineInput.Key;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.dialog.DomModalPromptDialog;
import com.softicar.platform.dom.elements.dialog.testing.IDomModalPromptNodes;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.dom.parent.IDomParentElement;
import org.junit.Test;

/**
 * Unit test for {@link DomModalPromptDialog}, based upon Selenium.
 *
 * @author Alexander Schmidt
 */
public class AjaxDomModalPromptDialogTest extends AbstractAjaxSeleniumLowLevelTest {

	private static final int BACKDROP_DISMISS_CLICK_OFFSET = 20;
	private static final IDisplayString MESSAGE = IDisplayString.create("some message");
	private static final String PROMPT_INPUT_DEFAULT_VALUE = "default value";
	private TestDiv testDiv;

	public AjaxDomModalPromptDialogTest() {

		this.testDiv = null;
	}

	// ---------------- basics ---------------- //

	@Test
	public void testShowMessage() {

		var prompt = showPrompt("");
		assertEquals(MESSAGE.toString(), getText(prompt.getContent()));
	}

	// ---------------- interaction with "okay" button ---------------- //

	@Test
	public void testClickOnOkayButton() {

		var prompt = showPrompt();
		click(prompt.getOkayButton());
		waitForServer();
		assertNoModalDialog();
		assertPromptInputApplied();
	}

	@Test
	public void testEnterOnOkayButton() {

		var prompt = showPrompt();
		send(prompt.getOkayButton(), Key.ENTER);
		waitForServer();
		assertNoModalDialog();
		assertPromptInputApplied();
	}

	@Test
	public void testSpaceOnOkayButton() {

		var prompt = showPrompt();
		send(prompt.getOkayButton(), Key.SPACE);
		waitForServer();
		assertNoModalDialog();
		assertPromptInputApplied();
	}

	@Test
	public void testEscapeOnOkayButton() {

		var prompt = showPrompt();
		send(prompt.getOkayButton(), Key.ESCAPE);
		waitForServer();
		assertNoModalDialog();
		assertPromptInputNotApplied();
	}

	// ---------------- interaction with "cancel" button ---------------- //

	@Test
	public void testClickOnCancelButton() {

		var prompt = showPrompt();
		click(prompt.getCancelButton());
		waitForServer();
		assertNoModalDialog();
		assertPromptInputNotApplied();
	}

	@Test
	public void testEnterOnCancelButton() {

		var prompt = showPrompt();
		send(prompt.getCancelButton(), Key.ENTER);
		waitForServer();
		assertNoModalDialog();
		assertPromptInputNotApplied();
	}

	@Test
	public void testSpaceOnCancelButton() {

		var prompt = showPrompt();
		send(prompt.getCancelButton(), Key.SPACE);
		waitForServer();
		assertNoModalDialog();
		assertPromptInputNotApplied();
	}

	@Test
	public void testEscapeOnCancelButton() {

		var prompt = showPrompt();
		send(prompt.getCancelButton(), Key.ESCAPE);
		waitForServer();
		assertNoModalDialog();
		assertPromptInputNotApplied();
	}

	// ---------------- interaction with input element ---------------- //

	@Test
	public void testEnterOnInputElement() {

		var prompt = showPrompt();
		send(prompt.getInputElement(), Key.ENTER);
		waitForServer();
		assertNoModalDialog();
		assertPromptInputApplied();
	}

	@Test
	public void testEnterOnInputElementAfterTabAndTyping() {

		var prompt = showPrompt();

		// Focus the input element via TAB.
		// This should not be necessary because the input is initially focused (see "testInputElementFocusedAfterShow")
		// but Selenium seems to have some quirk here.
		send(prompt.getFrame(), Key.TAB);

		// Type something, and press ENTER.
		send("foo");
		send(Key.ENTER);
		waitForServer();

		assertNoModalDialog();

		// Assert that the entered value was applied.
		// Also, indirectly assert that the default text in the input was initially highlighted.
		assertPromptInputApplied("foo");
	}

	@Test
	public void testEscapeOnInputElement() {

		var prompt = showPrompt();
		send(prompt.getInputElement(), Key.ESCAPE);
		waitForServer();
		assertNoModalDialog();
		assertPromptInputNotApplied();
	}

	// ---------------- input element default value ---------------- //

	@Test
	public void testInputElementWithDefaultValue() {

		var prompt = showPrompt();
		assertEquals(PROMPT_INPUT_DEFAULT_VALUE, getText(prompt.getInputElement()));
	}

	@Test
	public void testInputElementWithDifferentDefaultValue() {

		var prompt = showPrompt("different default value");
		assertEquals("different default value", getText(prompt.getInputElement()));
	}

	@Test
	public void testInputElementWithEmptyDefaultValue() {

		var prompt = showPrompt("");
		assertEquals("", getText(prompt.getInputElement()));
	}

	@Test
	public void testInputElementWithNullDefaultValue() {

		var prompt = showPrompt(null);
		assertEquals("", getText(prompt.getInputElement()));
	}

	// ---------------- interaction with frame ---------------- //

	@Test
	public void testEscapeOnFrame() {

		var prompt = showPrompt();
		send(prompt.getFrame(), Key.ESCAPE);
		waitForServer();
		assertNoModalDialog();
		assertPromptInputNotApplied();
	}

	// ---------------- interaction with backdrop ---------------- //

	@Test
	public void testClickOnBackdrop() {

		int offset = BACKDROP_DISMISS_CLICK_OFFSET;

		var prompt = showPrompt();
		clickAt(prompt.getBackdrop(), offset, offset);
		waitForServer();
		assertNoModalDialog();
		assertPromptInputNotApplied();
	}

	// ---------------- focus ---------------- //

	@Test
	public void testInputElementFocusedAfterShow() {

		var prompt = showPrompt();
		assertFocused(prompt.getInputElement());
	}

	@Test
	public void testInputElementFocusedAfterTabOnFrame() {

		var prompt = showPrompt();
		send(prompt.getFrame(), Key.TAB);
		waitForServer();
		assertFocused(prompt.getInputElement());
	}

	@Test
	public void testOkayButtonFocusedAfterTabOnInputElement() {

		var prompt = showPrompt();
		send(prompt.getInputElement(), Key.TAB);
		waitForServer();
		assertFocused(prompt.getOkayButton());
	}

	@Test
	public void testCancelButtonFocusedAfterTabOnOkayButton() {

		var prompt = showPrompt();
		send(prompt.getOkayButton(), Key.TAB);
		waitForServer();
		assertFocused(prompt.getCancelButton());
	}

	@Test
	public void testFrameFocusedAfterTabOnCancelButton() {

		var prompt = showPrompt();
		send(prompt.getCancelButton(), Key.TAB);
		waitForServer();
		assertFocused(prompt.getFrame());
	}

	// ---------------- private ---------------- //

	private IDomModalPromptNodes<IDomNode> showPrompt() {

		return showPrompt(PROMPT_INPUT_DEFAULT_VALUE);
	}

	private IDomModalPromptNodes<IDomNode> showPrompt(String defaultPromptInputValue) {

		this.testDiv = openTestNode(() -> new TestDiv(defaultPromptInputValue));

		click(testDiv.getButton());
		waitForServer();
		return findModalPromptOrFail();
	}

	private void assertPromptInputApplied() {

		assertPromptInputApplied(PROMPT_INPUT_DEFAULT_VALUE);
	}

	private void assertPromptInputApplied(String expected) {

		assertEquals(expected, getText(testDiv.getOutputElement()));
	}

	private void assertPromptInputNotApplied() {

		assertEquals("", getText(testDiv.getOutputElement()));
	}

	private static class TestDiv extends DomDiv {

		private final DomButton button;
		private final DomDiv outputElement;

		public TestDiv(String promptInputDefaultValue) {

			this.outputElement = new DomDiv();
			this.button = new DomButton()//
				.setLabel("spawn prompt")
				.setClickCallback(new DomModalPromptDialog(outputElement::appendText, MESSAGE, promptInputDefaultValue)::open);
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

package com.softicar.platform.dom.elements.dialog;

import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.dialog.testing.DomModalPromptNodes;
import com.softicar.platform.dom.elements.dialog.testing.IDomModalPromptNodes;
import com.softicar.platform.dom.elements.testing.node.tester.DomNodeTester;
import com.softicar.platform.dom.event.DomEventType;
import org.junit.Test;

public class DomModalPromptPopupTest extends AbstractDomModalDialogPopupTest {

	private static final String PROMPT_INPUT_DEFAULT_VALUE = "default value";

	// ---------------- basics ---------------- //

	@Test
	public void testShowMessage() {

		var prompt = showPrompt("");
		prompt.getContent().assertContainsText(MESSAGE);
	}

	// ---------------- interaction with "okay" button ---------------- //

	@Test
	public void testClickOnOkayButton() {

		var prompt = showPrompt();
		prompt.getOkayButton().click();
		assertNoDisplayedModalDialog();
		assertPromptInputApplied();
	}

	@Test
	public void testEnterOnOkayButton() {

		var prompt = showPrompt();
		prompt.getOkayButton().sendEvent(DomEventType.ENTER);
		assertNoDisplayedModalDialog();
		assertPromptInputApplied();
	}

	@Test
	public void testSpaceOnOkayButton() {

		var prompt = showPrompt();
		prompt.getOkayButton().sendEvent(DomEventType.SPACE);
		assertNoDisplayedModalDialog();
		assertPromptInputApplied();
	}

	// ---------------- interaction with "cancel" button ---------------- //

	@Test
	public void testClickOnCancelButton() {

		var prompt = showPrompt();
		prompt.getCancelButton().click();
		assertNoDisplayedModalDialog();
		assertPromptInputNotApplied();
	}

	@Test
	public void testEnterOnCancelButton() {

		var prompt = showPrompt();
		prompt.getCancelButton().sendEvent(DomEventType.ENTER);
		assertNoDisplayedModalDialog();
		assertPromptInputNotApplied();
	}

	@Test
	public void testSpaceOnCancelButton() {

		var prompt = showPrompt();
		prompt.getCancelButton().sendEvent(DomEventType.SPACE);
		assertNoDisplayedModalDialog();
		assertPromptInputNotApplied();
	}

	// ---------------- interaction with input element ---------------- //

	@Test
	public void testEnterOnInputElement() {

		var prompt = showPrompt();
		prompt.getInputElement().sendEvent(DomEventType.ENTER);
		assertNoDisplayedModalDialog();
		assertPromptInputApplied();
	}

	// ---------------- input element default value ---------------- //

	@Test
	public void testInputElementWithDefaultValue() {

		var prompt = showPrompt();
		assertEquals(PROMPT_INPUT_DEFAULT_VALUE, prompt.getInputElement().getInputValue());
	}

	@Test
	public void testInputElementWithDifferentDefaultValue() {

		var prompt = showPrompt("different default value");
		assertEquals("different default value", prompt.getInputElement().getInputValue());
	}

	@Test
	public void testInputElementWithEmptyDefaultValue() {

		var prompt = showPrompt("");
		assertEquals("", prompt.getInputElement().getInputValue());
	}

	@Test
	public void testInputElementWithNullDefaultValue() {

		var prompt = showPrompt(null);
		assertEquals("", prompt.getInputElement().getInputValue());
	}

	// ---------------- interaction with frame ---------------- //

	@Test
	public void testEscapeOnFrame() {

		var prompt = showPrompt();
		prompt.getFrame().sendEvent(DomEventType.ESCAPE);
		assertNoDisplayedModalDialog();
		assertPromptInputNotApplied();
	}

	// ---------------- interaction with backdrop ---------------- //

	@Test
	public void testClickOnBackdrop() {

		var prompt = showPrompt();
		prompt.getBackdrop().click();
		assertNoDisplayedModalDialog();
		assertPromptInputNotApplied();
	}

	// ---------------- private ---------------- //

	private IDomModalPromptNodes<DomNodeTester> showPrompt() {

		return showPrompt(PROMPT_INPUT_DEFAULT_VALUE);
	}

	private IDomModalPromptNodes<DomNodeTester> showPrompt(String defaultPromptInputValue) {

		setNodeSupplier(() -> new TestDiv(defaultPromptInputValue));

		findButton(SHOW_BUTTON).click();
		return findDisplayedModalPromptOrFail();
	}

	private IDomModalPromptNodes<DomNodeTester> findDisplayedModalPromptOrFail() {

		return new DomModalPromptNodes<>(this::findNode);
	}

	private void assertPromptInputApplied() {

		assertPromptInputApplied(PROMPT_INPUT_DEFAULT_VALUE);
	}

	private void assertPromptInputApplied(String expected) {

		findNode(OUTPUT_ELEMENT).assertContainsText(expected);
	}

	private void assertPromptInputNotApplied() {

		findNode(OUTPUT_ELEMENT).assertContainsNoText();
	}

	private static class TestDiv extends DomDiv {

		public TestDiv(String promptInputDefaultValue) {

			var outputElement = appendChild(new DomDiv());
			outputElement.setMarker(OUTPUT_ELEMENT);

			appendChild(
				new DomButton()//
					.setLabel("spawn prompt")
					.setClickCallback(new DomModalPromptPopup(outputElement::appendText, MESSAGE, promptInputDefaultValue)::show)
					.setMarker(SHOW_BUTTON));
		}
	}
}

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
	public void testContainsMessage() {

		var prompt = openPrompt("");
		prompt.getContent().assertContainsText(MESSAGE);
	}

	// ---------------- interaction with "okay" button ---------------- //

	@Test
	public void testClickOnOkayButton() {

		var prompt = openPrompt();
		prompt.getOkayButton().click();
		assertNoDisplayedModalDialog();
		assertPromptInputApplied();
	}

	@Test
	public void testEnterOnOkayButton() {

		var prompt = openPrompt();
		prompt.getOkayButton().sendEvent(DomEventType.ENTER);
		assertNoDisplayedModalDialog();
		assertPromptInputApplied();
	}

	@Test
	public void testSpaceOnOkayButton() {

		var prompt = openPrompt();
		prompt.getOkayButton().sendEvent(DomEventType.SPACE);
		assertNoDisplayedModalDialog();
		assertPromptInputApplied();
	}

	// ---------------- interaction with "cancel" button ---------------- //

	@Test
	public void testClickOnCancelButton() {

		var prompt = openPrompt();
		prompt.getCancelButton().click();
		assertNoDisplayedModalDialog();
		assertPromptInputNotApplied();
	}

	@Test
	public void testEnterOnCancelButton() {

		var prompt = openPrompt();
		prompt.getCancelButton().sendEvent(DomEventType.ENTER);
		assertNoDisplayedModalDialog();
		assertPromptInputNotApplied();
	}

	@Test
	public void testSpaceOnCancelButton() {

		var prompt = openPrompt();
		prompt.getCancelButton().sendEvent(DomEventType.SPACE);
		assertNoDisplayedModalDialog();
		assertPromptInputNotApplied();
	}

	// ---------------- interaction with input element ---------------- //

	@Test
	public void testEnterOnInputElement() {

		var prompt = openPrompt();
		prompt.getInputElement().sendEvent(DomEventType.ENTER);
		assertNoDisplayedModalDialog();
		assertPromptInputApplied();
	}

	// ---------------- input element default value ---------------- //

	@Test
	public void testInputElementWithDefaultValue() {

		var prompt = openPrompt();
		assertEquals(PROMPT_INPUT_DEFAULT_VALUE, prompt.getInputElement().getInputValue());
	}

	@Test
	public void testInputElementWithDifferentDefaultValue() {

		var prompt = openPrompt("different default value");
		assertEquals("different default value", prompt.getInputElement().getInputValue());
	}

	@Test
	public void testInputElementWithEmptyDefaultValue() {

		var prompt = openPrompt("");
		assertEquals("", prompt.getInputElement().getInputValue());
	}

	@Test
	public void testInputElementWithNullDefaultValue() {

		var prompt = openPrompt(null);
		assertEquals("", prompt.getInputElement().getInputValue());
	}

	// ---------------- interaction with frame ---------------- //

	@Test
	public void testEscapeOnFrame() {

		var prompt = openPrompt();
		prompt.getFrame().sendEvent(DomEventType.ESCAPE);
		assertNoDisplayedModalDialog();
		assertPromptInputNotApplied();
	}

	// ---------------- interaction with backdrop ---------------- //

	@Test
	public void testClickOnBackdrop() {

		var prompt = openPrompt();
		prompt.getBackdrop().click();
		assertNoDisplayedModalDialog();
		assertPromptInputNotApplied();
	}

	// ---------------- private ---------------- //

	private IDomModalPromptNodes<DomNodeTester> openPrompt() {

		return openPrompt(PROMPT_INPUT_DEFAULT_VALUE);
	}

	private IDomModalPromptNodes<DomNodeTester> openPrompt(String defaultPromptInputValue) {

		setNodeSupplier(() -> new TestDiv(defaultPromptInputValue));

		findButton(OPEN_BUTTON).click();
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
			outputElement.addMarker(OUTPUT_ELEMENT);

			appendChild(
				new DomButton()//
					.setLabel("open prompt")
					.setClickCallback(new DomModalPromptPopup(outputElement::appendText, MESSAGE, promptInputDefaultValue)::open)
					.addMarker(OPEN_BUTTON));
		}
	}
}

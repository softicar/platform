package com.softicar.platform.ajax.dom.input;

import com.softicar.platform.ajax.engine.AjaxDomEngine;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.AbstractAjaxSeleniumLowLevelTest;
import com.softicar.platform.ajax.utils.TestButton;
import com.softicar.platform.ajax.utils.TestConstants;
import com.softicar.platform.dom.engine.IDomEngine;
import com.softicar.platform.dom.input.AbstractDomInput;
import com.softicar.platform.dom.input.DomTextInput;
import java.util.function.BiConsumer;
import org.junit.Test;

/**
 * This class tests various feature of the {@link AjaxDomEngine} related to
 * {@link AbstractDomInput}.
 *
 * @author Oliver Richers
 */
public class AjaxDomInputTest extends AbstractAjaxSeleniumLowLevelTest {

	private AjaxDomInputTestDiv testDiv;
	private DomTextInput input;
	private TestButton button;

	@Test
	public void testFocus() {

		openTestDiv((input, button) -> {
			button.setClickCallback(() -> {
				input.focus();
			});
		});

		// assert focus goes from body to input by clicking button
		assertFocused(testDiv.getParent());
		click(button);
		waitForServer();
		assertFocused(input);
	}

	@Test
	public void testDisable() {

		openTestDiv((input, button) -> {
			button.setClickCallback(() -> {
				input.setDisabled(true);
			});
		});

		// assert is enabled
		assertNull(getAttributeValue(input, "disabled"));

		// assert gets disabled by clicking button
		click(button);
		waitForServer();
		assertEquals("true", getAttributeValue(input, "disabled"));
	}

	@Test
	public void testEnable() {

		openTestDiv((input, button) -> {
			input.setDisabled(true);
			button.setClickCallback(() -> {
				input.setDisabled(false);
			});
		});

		// assert is disabled
		assertEquals("true", getAttributeValue(input, "disabled"));

		// assert gets enabled by clicking button
		click(button);
		waitForServer();
		assertNull(getAttributeValue(input, "disabled"));
	}

	@Test
	public void testGetValue() {

		openTestDiv((input, button) -> {
			// nothing to do
		});

		send(input, TestConstants.SPECIAL_TEXT);
		click(button);
		waitForServer();
		assertEquals(TestConstants.SPECIAL_TEXT, testDiv.getInput().getInputText());
	}

	@Test
	public void testSetValue() {

		openTestDiv((input, button) -> {
			input.setInputText(TestConstants.SPECIAL_TEXT);
		});

		assertEquals(TestConstants.SPECIAL_TEXT, getAttributeValue(input, "value"));
	}

	@Test
	public void testSelect() {

		openTestDiv((input, button) -> {
			button.setClickCallback(() -> {
				input.getDomEngine().select(input);
			});
		});

		// input text then select all text by clicking button and input new text
		send(input, "foo");
		send(input, "baz");
		click(button);
		waitForServer();
		send(input, "bar");

		// verification
		assertEquals("bar", getAttributeValue(input, "value"));
	}

	@Test
	public void testMoveCaretToPosition() {

		openTestDiv((input, button) -> {
			button.setClickCallback(() -> {
				input.getDomEngine().moveCaretToPosition(input, 3);
			});
		});

		// input text then move caret by clicking button and input more text
		send(input, "foobaz");
		click(button);
		waitForServer();
		send(input, "bar");

		// verification
		assertEquals("foobarbaz", getAttributeValue(input, "value"));
	}

	@Test
	public void testInsertTextAtCaret() {

		openTestDiv((input, button) -> {
			button.setClickCallback(() -> {
				IDomEngine engine = input.getDomEngine();
				engine.moveCaretToPosition(input, 3);
				engine.insertTextAtCaret(input, "bar");
			});
		});

		// input text then insert at caret by clicking button
		send(input, "foobaz");
		click(button);
		waitForServer();

		// verification
		assertEquals("foobarbaz", getAttributeValue(input, "value"));
	}

	private void openTestDiv(BiConsumer<DomTextInput, TestButton> setup) {

		this.testDiv = openTestNode(() -> {
			AjaxDomInputTestDiv div = new AjaxDomInputTestDiv();
			setup.accept(div.getInput(), div.getButton());
			return div;
		});
		this.input = testDiv.getInput();
		this.button = testDiv.getButton();
	}
}

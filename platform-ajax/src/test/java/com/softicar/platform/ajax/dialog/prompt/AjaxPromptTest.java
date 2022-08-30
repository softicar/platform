package com.softicar.platform.ajax.dialog.prompt;

import com.softicar.platform.ajax.testing.selenium.engine.level.low.AbstractAjaxSeleniumLowLevelTest;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.AjaxSeleniumLowLevelTestEngineInput.Key;
import com.softicar.platform.dom.elements.dialog.testing.IDomModalPromptNodes;
import com.softicar.platform.dom.node.IDomNode;
import org.junit.Test;

public class AjaxPromptTest extends AbstractAjaxSeleniumLowLevelTest {

	private final AjaxPromptTestDiv testDiv;

	public AjaxPromptTest() {

		this.testDiv = openTestNode(AjaxPromptTestDiv::new);
	}

	@Test
	public void testPrompt() {

		var prompt = showPrompt();
		send(prompt.getFrame(), Key.TAB);
		send("foo");
		click(prompt.getOkayButton());
		waitForServer();

		assertEquals("foo", getText(testDiv.getOutputDiv()));
	}

	@Test
	public void testPromptWithDefaultInput() {

		var prompt = showPrompt();
		click(prompt.getOkayButton());
		waitForServer();

		assertEquals(AjaxPromptTestDiv.DEFAULT_INPUT, getText(testDiv.getOutputDiv()));
	}

	@Test
	public void testPromptCancelation() {

		var prompt = showPrompt();
		click(prompt.getCancelButton());
		waitForServer();

		assertEquals("", getText(testDiv.getOutputDiv()));
	}

	private IDomModalPromptNodes<IDomNode> showPrompt() {

		// click test button
		click(testDiv.getTestButton());
		waitForServer();

		// check prompt message
		var prompt = findModalPromptOrFail();
		assertEquals(AjaxPromptTestDiv.QUESTION, getText(prompt.getContent()));

		return prompt;
	}
}

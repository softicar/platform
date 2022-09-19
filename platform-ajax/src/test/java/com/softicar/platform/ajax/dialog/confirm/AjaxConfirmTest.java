package com.softicar.platform.ajax.dialog.confirm;

import com.softicar.platform.ajax.testing.selenium.engine.level.low.AbstractAjaxSeleniumLowLevelTest;
import org.junit.After;
import org.junit.Test;

public class AjaxConfirmTest extends AbstractAjaxSeleniumLowLevelTest {

	// FIXME temporary
	@After
	public void discardWebDriver() {

		testEngine.discardWebDriver();
	}

	private final AjaxConfirmTestDiv testDiv;

	public AjaxConfirmTest() {

		this.testDiv = openTestNode(AjaxConfirmTestDiv::new);
	}

	@Test
	public void testConfirmationAcceptance() {

		// click test button
		click(testDiv.getTestButton());
		waitForServer();

		// check confirm message
		var confirm = findModalConfirmOrFail();
		assertEquals(AjaxConfirmTestDiv.QUESTION, getText(confirm.getContent()));
		click(confirm.getOkayButton());
		waitForServer();

		assertEquals(AjaxConfirmTestDiv.CONFIRMED, getText(testDiv.getOutputDiv()));
	}

	@Test
	public void testConfirmationDismiss() {

		// click test button
		click(testDiv.getTestButton());
		waitForServer();

		// check confirm message
		var confirm = findModalConfirmOrFail();
		assertEquals(AjaxConfirmTestDiv.QUESTION, getText(confirm.getContent()));
		click(confirm.getCancelButton());
		waitForServer();

		assertEquals("", getText(testDiv.getOutputDiv()));
	}
}

package com.softicar.platform.ajax.dialog.alert;

import com.softicar.platform.ajax.testing.selenium.engine.level.low.AbstractAjaxSeleniumLowLevelTest;
import org.junit.After;
import org.junit.Test;

public class AjaxAlertTest extends AbstractAjaxSeleniumLowLevelTest {

	// FIXME temporary
	@After
	public void discardWebDriver() {

		testEngine.discardWebDriver();
	}

	private final AjaxAlertTestDiv testDiv;

	public AjaxAlertTest() {

		this.testDiv = openTestNode(AjaxAlertTestDiv::new);
	}

	@Test
	public void testAlert() {

		// click test button
		click(testDiv.getTestButton());
		waitForServer();

		// assert that the alert is displayed
		var alert = findModalAlertOrFail();
		assertEquals(AjaxAlertTestDiv.TEXT, getText(alert.getContent()));

		// dismiss the alert
		click(alert.getCloseButton());
		waitForServer();
		assertNoModalDialog();
	}
}

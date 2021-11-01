package com.softicar.platform.ajax.session.timeout;

import com.softicar.platform.ajax.document.AjaxDocument;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.AbstractAjaxSeleniumLowLevelTest;
import com.softicar.platform.ajax.utils.TestButton;
import org.junit.Test;

public class AjaxSessionTimeoutTest extends AbstractAjaxSeleniumLowLevelTest {

	@Test
	public void test() {

		KillSessionButton button = openTestNode(KillSessionButton::new);

		// precondition: timeout-div is hidden
		assertFalse(isSessionTimeoutDivDisplayed());

		// click to kill session
		click(button);
		assertFalse(isSessionTimeoutDivDisplayed());

		// click to trigger session timeout
		click(button);
		waitUntil(() -> isSessionTimeoutDivDisplayed());

		// assert timeout-div is shown
		assertTrue(isSessionTimeoutDivDisplayed());

		// click to reload
		clickSessionTimeoutDiv();
		waitWhile(() -> isSessionTimeoutDivDisplayed());

		// assert timeout-div is hidden
		assertFalse(isSessionTimeoutDivDisplayed());
	}

	private class KillSessionButton extends TestButton {

		public KillSessionButton() {

			setClickCallback(this::trigger);
		}

		private void trigger() {

			AjaxDocument//
				.getCurrentDocument()
				.get()
				.getHttpSession()
				.invalidate();
		}
	}
}

package com.softicar.platform.ajax.event;

import com.softicar.platform.ajax.testing.selenium.engine.level.low.AbstractAjaxSeleniumLowLevelTest;
import com.softicar.platform.ajax.utils.TestButton;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.dialog.DomModalAlertMarker;
import org.junit.Test;

public class AjaxDomEventWithExceptionTest extends AbstractAjaxSeleniumLowLevelTest {

	private final AjaxDomEventWithExceptionTestDiv testDiv;
	private final TestButton button;
	private final DomDiv output;

	public AjaxDomEventWithExceptionTest() {

		this.testDiv = openTestNode(AjaxDomEventWithExceptionTestDiv::new);
		this.button = testDiv.getButton();
		this.output = testDiv.getOutput();
	}

	@Test
	public void test() {

		// check that the document was created properly
		assertEquals("", getText(output));

		// click exception-throwing button
		click(button);
		waitForServer();

		// assert modal alert is displayed
		var alert = findModalAlertOrFail();
		assertFocused(DomModalAlertMarker.CLOSE_BUTTON);
		assertEquals("Exception Text", getText(alert.getContent()));

		// close modal alert
		click(alert.getCloseButton());
		waitForServer();

		// assert modal alert is gone
		assertNoModalDialog();

		// assert text that was appended before the Exception occurred is displayed
		assertEquals("Hello", getText(output));
	}
}

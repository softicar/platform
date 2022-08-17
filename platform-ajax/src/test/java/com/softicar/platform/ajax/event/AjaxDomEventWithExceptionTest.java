package com.softicar.platform.ajax.event;

import com.softicar.platform.ajax.AjaxI18n;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.AbstractAjaxSeleniumLowLevelTest;
import com.softicar.platform.ajax.utils.TestButton;
import com.softicar.platform.dom.DomTestMarker;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.dialog.testing.IDomModalAlertNodes;
import com.softicar.platform.dom.node.IDomNode;
import org.junit.Test;

public class AjaxDomEventWithExceptionTest extends AbstractAjaxSeleniumLowLevelTest {

	private final AjaxDomEventWithExceptionTestDiv testDiv;
	private final TestButton softicarUserExceptionButton;
	private final TestButton nonSofticarUserExceptionButton;
	private final DomDiv output;

	public AjaxDomEventWithExceptionTest() {

		this.testDiv = openTestNode(AjaxDomEventWithExceptionTestDiv::new);
		this.softicarUserExceptionButton = testDiv.getSofticarUserExceptionButton();
		this.nonSofticarUserExceptionButton = testDiv.getNonSofticarUserExceptionButton();
		this.output = testDiv.getOutput();
	}

	@Test
	public void testWithSofticarUserException() {

		validateWithButton(softicarUserExceptionButton, "Exception Text");
	}

	@Test
	public void testWithNonSofticarUserException() {

		validateWithButton(nonSofticarUserExceptionButton, AjaxI18n.AN_INTERNAL_PROGRAM_ERROR_OCCURRED.toString());
	}

	private void validateWithButton(TestButton appendingTextAndExceptionThrowingButton, String expectedModalAlertMessage) {

		// validate that there is no text appended
		validateDocumentContent("");

		clickButtonAndWaitForServer(appendingTextAndExceptionThrowingButton);

		IDomModalAlertNodes<IDomNode> alert = validateModalAlert(expectedModalAlertMessage);

		clickButtonAndWaitForServer(alert.getCloseButton());

		// assert modal dialog is gone
		assertNoModalDialog();

		// validate text that was appended just before exception was triggered
		validateDocumentContent("Hello");
	}

	private void validateDocumentContent(String expectedContent) {

		assertEquals(expectedContent, getText(output));
	}

	private void clickButtonAndWaitForServer(IDomNode button) {

		click(button);
		waitForServer();
	}

	private IDomModalAlertNodes<IDomNode> validateModalAlert(String expectedModalAlertMessage) {

		IDomModalAlertNodes<IDomNode> alert = findModalAlertOrFail();
		assertFocused(DomTestMarker.MODAL_ALERT_CLOSE_BUTTON);
		assertEquals(expectedModalAlertMessage, getText(alert.getContent()));
		return alert;
	}
}

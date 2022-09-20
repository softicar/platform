package com.softicar.platform.ajax.document.creation;

import com.softicar.platform.ajax.testing.selenium.engine.level.low.AbstractAjaxSeleniumLowLevelTest;
import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.DomTestMarker;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.document.DomBody;
import com.softicar.platform.dom.elements.DomDiv;
import org.junit.Test;

public class AjaxDocumentCreationWithExceptionTest extends AbstractAjaxSeleniumLowLevelTest {

	// FIXME temporary
//	@After
//	public void discardWebDriver() {
//
//		testEngine.discardWebDriver();
//	}

	private DomBody body;

	@Test
	public void test() {

		openTestNode(TestDiv::new);

		// assert modal alert is displayed
		var alert = findModalAlertOrFail();
		assertFocused(DomTestMarker.MODAL_ALERT_CLOSE_BUTTON);
		assertEquals("Exception Text", getText(alert.getContent()));

		// close modal alert
		click(alert.getCloseButton());
		waitForServer();
		assertNoModalDialog();

		// assert that the document is blank
		assertEquals(0, body.getChildCount());
		assertEquals("", getText(body));
	}

	private class TestDiv extends DomDiv {

		public TestDiv() {

			body = CurrentDomDocument.get().getBody();

			DomDiv testContent = new DomDiv();
			testContent.setAttribute("name", "foo");
			testContent.appendText("Hello");
			appendChild(testContent);

			throw new SofticarUserException(IDisplayString.create("Exception Text"));
		}
	}
}

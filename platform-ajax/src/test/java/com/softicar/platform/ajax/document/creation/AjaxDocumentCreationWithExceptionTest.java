package com.softicar.platform.ajax.document.creation;

import com.softicar.platform.ajax.testing.selenium.engine.level.low.AbstractAjaxSeleniumLowLevelTest;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.document.DomBody;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.dialog.DomModalAlertMarker;
import org.junit.Test;

public class AjaxDocumentCreationWithExceptionTest extends AbstractAjaxSeleniumLowLevelTest {

	private DomBody body;

	@Test
	public void test() {

		openTestNode(TestDiv::new);

		// assert modal alert is displayed
		var alert = findModalAlertOrFail();
		assertFocused(DomModalAlertMarker.CLOSE_BUTTON);
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

			throw new RuntimeException("Exception Text");
		}
	}
}

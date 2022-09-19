package com.softicar.platform.ajax.document.creation;

import com.softicar.platform.ajax.testing.selenium.engine.level.low.AbstractAjaxSeleniumLowLevelTest;
import com.softicar.platform.ajax.utils.TestConstants;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.document.IDomDocument;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.event.IDomEvent;
import org.junit.After;
import org.junit.Test;

public class AjaxDocumentCreationTest extends AbstractAjaxSeleniumLowLevelTest {

	// FIXME temporary
	@After
	public void discardWebDriver() {

		testEngine.discardWebDriver();
	}

	@Test
	public void test() {

		TestDiv testDiv = openTestNode(TestDiv::new);

		// assert thread-locals are correct
		assertNotNull(testDiv.getDocument());
		assertNotNull(testDiv.getParent());
		assertNull(testDiv.getEvent());

		// assert child creation
		DomDiv childDiv = testDiv.getChildDiv();
		assertNotNull(childDiv);
		assertEquals(TestConstants.SPECIAL_TEXT_WITHOUT_UNICODE_WHITESPACE, getText(childDiv));
	}

	private class TestDiv extends DomDiv {

		private final IDomDocument document;
		private final IDomEvent event;
		private final DomDiv childDiv;

		public TestDiv() {

			this.document = CurrentDomDocument.get();
			this.event = getCurrentEvent();
			this.childDiv = appendChild(new DomDiv());

			childDiv.appendText(TestConstants.SPECIAL_TEXT_WITHOUT_UNICODE_WHITESPACE);
		}

		public IDomDocument getDocument() {

			return document;
		}

		public IDomEvent getEvent() {

			return event;
		}

		public DomDiv getChildDiv() {

			return childDiv;
		}
	}
}

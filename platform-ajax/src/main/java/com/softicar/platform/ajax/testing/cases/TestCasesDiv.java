package com.softicar.platform.ajax.testing.cases;

import com.softicar.platform.ajax.server.development.AjaxDevelopmentServer;
import com.softicar.platform.ajax.testing.cases.entity.AutoCompleteEntityTestCase;
import com.softicar.platform.ajax.testing.cases.entity.AutoCompleteEntityWithChangeHandlerTestCase;
import com.softicar.platform.dom.elements.DomDiv;

public class TestCasesDiv extends DomDiv {

	public TestCasesDiv() {

		appendChild(new AutoCompleteTestCase());
		appendChild(new AutoCompleteWithChangeHandlerTestCase());
		appendChild(new AutoCompleteIndirectEventHandlingTestCase());
		appendChild(new AutoCompleteEntityTestCase());
		appendChild(new AutoCompleteEntityWithChangeHandlerTestCase());
		appendChild(new AutoCompleteInPopupTestCase());
		appendChild(new ChangeEventTestCase());
		appendChild(new HiddenFileInputTestCase());
		appendChild(new EnterEventOnButtonTestCase());
		appendChild(new SpaceEventTestCase1());
		appendChild(new SpaceEventTestCase2());
		appendChild(new TabEventTestCase());
		appendChild(new ClickDisplacementTestCase1());
		appendChild(new ClickDisplacementTestCase2());
		appendChild(new PopupTestCase());
	}

	public static void main(String[] args) {

		new AjaxDevelopmentServer(TestCasesDiv.class)//
			.setPort(9000)
			.startAndJoin();
	}
}

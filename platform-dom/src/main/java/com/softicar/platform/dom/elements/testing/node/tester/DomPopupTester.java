package com.softicar.platform.dom.elements.testing.node.tester;

import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngine;
import org.junit.Assert;

public class DomPopupTester extends AbstractDomNodeTester<DomPopup> {

	public DomPopupTester(IDomTestExecutionEngine engine, DomPopup node) {

		super(engine, node);
	}

	public void assertOpen() {

		Assert.assertTrue("Expected pop-up to be open but it is closed.", isDisplayed());
	}

	public void assertClosed() {

		Assert.assertFalse("Expected pop-up to be closed but it is open.", isDisplayed());
	}
}

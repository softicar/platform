package com.softicar.platform.dom.elements.testing.node.tester;

import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngine;
import org.junit.Assert;

public class DomPopupTester extends AbstractDomNodeTester<DomPopup> {

	public DomPopupTester(IDomTestExecutionEngine engine, DomPopup node) {

		super(engine, node);
	}

	public void assertIsShown() {

		Assert.assertTrue("Expected pop-up to be shown but is hidden.", node.isShown());
	}

	public void assertIsHidden() {

		Assert.assertFalse("Expected pop-up to be hidden but is shown.", node.isShown());
	}
}

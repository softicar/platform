package com.softicar.platform.emf.test.tester;

import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngine;
import com.softicar.platform.dom.elements.testing.node.tester.AbstractDomNodeTester;
import com.softicar.platform.emf.management.EmfManagementDiv;
import com.softicar.platform.emf.management.EmfManagementPopup;

public class EmfManagementPopupTester extends AbstractDomNodeTester<EmfManagementPopup<?, ?, ?>> {

	public EmfManagementPopupTester(IDomTestExecutionEngine engine, EmfManagementPopup<?, ?, ?> node) {

		super(engine, node);
	}

	public EmfManagementDivTester findManagementDiv() {

		return new EmfManagementDivTester(//
			getEngine(),
			findNode(EmfManagementDiv.class).assertType(EmfManagementDiv.class));
	}
}

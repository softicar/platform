package com.softicar.platform.dom.elements.testing.engine.document;

import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngine;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngineMethods;
import com.softicar.platform.dom.elements.testing.node.tester.DomNodeTester;
import org.junit.Rule;
import org.junit.Test;

public class DomDocumentTestExecutionEngineTest extends AbstractTest implements IDomTestExecutionEngineMethods {

	@Rule public final IDomTestExecutionEngine engine = new DomDocumentTestExecutionEngine();

	private final DomDiv parentDiv;
	private final DomDiv childDiv;

	public DomDocumentTestExecutionEngineTest() {

		this.parentDiv = new DomDiv();
		this.childDiv = new DomDiv();
		parentDiv.appendChild(childDiv);

		setNodeSupplier(() -> parentDiv);
	}

	@Override
	public IDomTestExecutionEngine getEngine() {

		return engine;
	}

	@Test
	public void testIsDisplayed() {

		findBody();

		DomNodeTester parent = new DomNodeTester(engine, parentDiv);
		DomNodeTester child = new DomNodeTester(engine, childDiv);

		parent.assertDisplayed();
		child.assertDisplayed();

		// set parent to display none
		parentDiv.setDisplayNone(true);
		parent.assertNotDisplayed();
		child.assertNotDisplayed();
		parentDiv.setDisplayNone(false);

		// set child to display none
		childDiv.setDisplayNone(true);
		parent.assertDisplayed();
		child.assertNotDisplayed();
		childDiv.setDisplayNone(false);

		// remove parent from body
		parentDiv.disappend();
		parent.assertNotDisplayed();
		child.assertNotDisplayed();
	}
}

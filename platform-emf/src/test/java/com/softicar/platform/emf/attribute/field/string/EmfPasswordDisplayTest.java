package com.softicar.platform.emf.attribute.field.string;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngine;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngineMethods;
import com.softicar.platform.dom.elements.testing.engine.document.DomDocumentTestExecutionEngine;
import org.junit.Rule;
import org.junit.Test;

public class EmfPasswordDisplayTest extends AbstractTest implements IDomTestExecutionEngineMethods {

	@Rule public final IDomTestExecutionEngine engine = new DomDocumentTestExecutionEngine();

	@Test
	public void testPasswordDisplayWithString() {

		setNodeSupplier(() -> new EmfPasswordDisplay("testpassword"));
		findNode(EmfPasswordDisplay.class).assertContainsText("••••••••");
	}

	@Test
	public void testPasswordDisplayWithDisplayString() {

		setNodeSupplier(() -> new EmfPasswordDisplay(IDisplayString.create("testpassword")));
		findNode(EmfPasswordDisplay.class).assertContainsText("••••••••");
	}

	@Test
	public void testPasswordDisplayWithEmptyString() {

		setNodeSupplier(() -> new EmfPasswordDisplay(""));
		findNode(EmfPasswordDisplay.class).assertContainsNoText();
	}

	@Test
	public void testPasswordDisplayWithEmptyDisplayString() {

		setNodeSupplier(() -> new EmfPasswordDisplay(IDisplayString.create("")));
		findNode(EmfPasswordDisplay.class).assertContainsNoText();
	}

	@Override
	public IDomTestExecutionEngine getEngine() {

		return engine;
	}
}

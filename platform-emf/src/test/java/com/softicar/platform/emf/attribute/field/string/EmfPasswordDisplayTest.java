package com.softicar.platform.emf.attribute.field.string;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.testing.engine.IDomTestEngine;
import com.softicar.platform.dom.elements.testing.engine.IDomTestEngineMethods;
import com.softicar.platform.dom.elements.testing.engine.document.DomDocumentTestEngine;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

public class EmfPasswordDisplayTest extends Assert implements IDomTestEngineMethods {

	@Rule public final IDomTestEngine engine = new DomDocumentTestEngine();

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
	public IDomTestEngine getEngine() {

		return engine;
	}
}

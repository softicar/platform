package com.softicar.platform.emf.attribute.field.string;

import com.softicar.platform.dom.elements.DomImage;
import com.softicar.platform.dom.elements.DomPasswordInput;
import com.softicar.platform.dom.elements.testing.engine.IDomTestEngine;
import com.softicar.platform.dom.elements.testing.engine.IDomTestEngineMethods;
import com.softicar.platform.dom.elements.testing.engine.document.DomDocumentTestEngine;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

public class EmfPasswordInputTest extends Assert implements IDomTestEngineMethods {

	@Rule public final IDomTestEngine engine = new DomDocumentTestEngine();

	public EmfPasswordInputTest() {

		setNodeSupplier(EmfPasswordInput::new);
	}

	@Test
	public void testVisiblityChange() {

		DomPasswordInput passwordInput = findNode(DomPasswordInput.class).assertType(DomPasswordInput.class);
		assertSame("password", passwordInput.getAttribute("type").getValue());
		findNode(DomImage.class).click();
		assertSame("text", passwordInput.getAttribute("type").getValue());
		findNode(DomImage.class).click();
		assertSame("password", passwordInput.getAttribute("type").getValue());
	}

	@Override
	public IDomTestEngine getEngine() {

		return engine;
	}
}

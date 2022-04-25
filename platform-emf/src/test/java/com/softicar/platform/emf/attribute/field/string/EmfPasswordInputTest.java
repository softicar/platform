package com.softicar.platform.emf.attribute.field.string;

import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.dom.elements.DomImage;
import com.softicar.platform.dom.elements.DomPasswordInput;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngine;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngineMethods;
import com.softicar.platform.dom.elements.testing.engine.document.DomDocumentTestExecutionEngine;
import org.junit.Rule;
import org.junit.Test;

public class EmfPasswordInputTest extends AbstractTest implements IDomTestExecutionEngineMethods {

	@Rule public final IDomTestExecutionEngine engine = new DomDocumentTestExecutionEngine();

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
	public IDomTestExecutionEngine getEngine() {

		return engine;
	}
}

package com.softicar.platform.dom.input;

import com.softicar.platform.common.core.i18n.I18n1;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngine;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngineMethods;
import com.softicar.platform.dom.elements.testing.engine.document.DomDocumentTestExecutionEngine;
import java.util.function.Supplier;
import org.junit.Rule;

public abstract class AbstractDomValueInputDivTest<V> extends AbstractTest implements IDomTestExecutionEngineMethods {

	@Rule public final IDomTestExecutionEngine engine = new DomDocumentTestExecutionEngine();
	protected final IDomValueInput<V> input;

	public AbstractDomValueInputDivTest(Supplier<IDomValueInput<V>> inputFactory) {

		this.input = inputFactory.get();

		setNodeSupplier(() -> input);
	}

	@Override
	public IDomTestExecutionEngine getEngine() {

		return engine;
	}

	protected void assertEmptyResultForGetValue(String valueText) {

		enterValue(valueText);
		assertEmpty(input.getValue());
	}

	protected void assertResultForGetValue(V expectedValue, String valueText) {

		enterValue(valueText);
		assertEquals(expectedValue, input.getValue().get());
	}

	protected void assertExceptionForGetValue(IDisplayString expectedMessage, String valueText) {

		enterValue(valueText);
		assertException(() -> input.getValue(), expectedMessage);
	}

	protected void assertExceptionForGetValue(I18n1 expectedMessage, String valueText) {

		assertExceptionForGetValue(expectedMessage.toDisplay(valueText), valueText);
	}

	protected void enterValue(String valueText) {

		findNode(IDomTextualInput.class).setInputValue(valueText);
	}
}

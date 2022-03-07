package com.softicar.platform.dom.input;

import com.softicar.platform.common.core.i18n.I18n1;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.dom.elements.testing.engine.IDomTestEngine;
import com.softicar.platform.dom.elements.testing.engine.IDomTestEngineMethods;
import com.softicar.platform.dom.elements.testing.engine.document.DomDocumentTestEngine;
import java.util.function.Supplier;
import org.junit.Rule;

public abstract class AbstractDomValueInputTest<V> extends AbstractTest implements IDomTestEngineMethods {

	@Rule public final IDomTestEngine engine = new DomDocumentTestEngine();
	protected final IDomValueInput<V> input;

	public AbstractDomValueInputTest(Supplier<IDomValueInput<V>> inputFactory) {

		this.input = inputFactory.get();

		setNodeSupplier(() -> input);
	}

	@Override
	public IDomTestEngine getEngine() {

		return engine;
	}

	protected void assertEmptyResultForGetValue(String inputText) {

		enterValue(inputText);
		assertEmpty(input.getValue());
	}

	protected void assertResultForGetValue(V expectedValue, String inputText) {

		enterValue(inputText);
		assertEquals(expectedValue, input.getValue().get());
	}

	protected void assertExceptionForGetValue(IDisplayString expectedMessage, String inputText) {

		enterValue(inputText);
		assertException(() -> input.getValue(), expectedMessage);
	}

	protected void assertExceptionForGetValue(I18n1 expectedMessage, String inputText) {

		assertExceptionForGetValue(expectedMessage.toDisplay(inputText), inputText);
	}

	protected void enterValue(String inputText) {

		findNode(IDomTextualInput.class).setInputValue(inputText);
	}
}

package com.softicar.platform.dom.input;

import com.softicar.platform.common.core.i18n.I18n1;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngine;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngineMethods;
import com.softicar.platform.dom.elements.testing.engine.document.DomDocumentTestExecutionEngine;
import java.util.function.Supplier;
import org.junit.Rule;

public abstract class AbstractDomValueInputDivTest<V> extends AbstractTest implements IDomTestExecutionEngineMethods {

	protected static final DayTime TEST_TIME = DayTime.fromYMD_HMS(1999, 12, 31, 14, 30, 59);
	@Rule public final IDomTestExecutionEngine engine = new DomDocumentTestExecutionEngine();
	protected final IDomValueInput<V> input;

	public AbstractDomValueInputDivTest(Supplier<IDomValueInput<V>> inputFactory) {

		testClock.setInstant(TEST_TIME.toInstant());

		this.input = inputFactory.get();
		setNodeSupplier(() -> input);
	}

	@Override
	public IDomTestExecutionEngine getEngine() {

		return engine;
	}

	protected void enterValue(String valueText) {

		findNode(IDomTextualInput.class).setInputValue(valueText);
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
		assertExceptionMessage(expectedMessage, () -> input.getValue());
	}

	protected void assertExceptionForGetValue(I18n1 expectedMessage, String valueText) {

		assertExceptionForGetValue(expectedMessage.toDisplay(valueText), valueText);
	}

	protected void assertDomTextInputValue(String expectedValue) {

		var textInput = asTester(input).findNode(DomTextInput.class).assertType(DomTextInput.class);
		var textInputValue = textInput.getAttributeValue("value").get();
		assertEquals(expectedValue, textInputValue);
	}
}

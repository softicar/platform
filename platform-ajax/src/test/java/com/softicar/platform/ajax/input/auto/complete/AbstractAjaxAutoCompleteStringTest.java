package com.softicar.platform.ajax.input.auto.complete;

import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.document.DomBody;
import com.softicar.platform.dom.input.IDomTextualInput;
import com.softicar.platform.dom.style.CssPixel;
import com.softicar.platform.dom.style.CssStyle;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public abstract class AbstractAjaxAutoCompleteStringTest extends AbstractAjaxAutoCompleteTest {

	protected static final String AMBIGUOUS_INPUT = "ir";
	protected static final String INCOMPLETE_VALUE1_NAME = "fir";
	protected static final String INVALID_INPUT = "xxx";
	protected static final AjaxAutoCompleteTestValue VALUE1 = new AjaxAutoCompleteTestValue("first");
	protected static final AjaxAutoCompleteTestValue VALUE2 = new AjaxAutoCompleteTestValue("second");
	protected static final AjaxAutoCompleteTestValue VALUE3 = new AjaxAutoCompleteTestValue("third");
	protected static final AjaxAutoCompleteTestValue VALUE4 = new AjaxAutoCompleteTestValue("thirdPlusOne");
	protected AjaxAutoCompleteTestInput inputDiv;
	protected IDomTextualInput inputField;

	protected void openTestInput(Consumer<AjaxAutoCompleteTestInput> setup) {

		this.inputDiv = openTestNode(() -> {
			// ensure that screenshots include the auto-complete pop-up
			DomBody body = CurrentDomDocument.get().getBody();
			body.setStyle(CssStyle.WIDTH, new CssPixel(300));
			body.setStyle(CssStyle.HEIGHT, new CssPixel(300));

			// create input
			AjaxAutoCompleteTestInputEngine engine = new AjaxAutoCompleteTestInputEngine();
			AjaxAutoCompleteTestInput input = new AjaxAutoCompleteTestInput(engine);
			setup.accept(input);
			return input;
		});
		this.inputField = inputDiv.getInputField().get();
	}

	protected boolean isValueSubmitted() {

		String clientValue = inputDiv.getValueText();
		String serverValue = inputDiv.getValue().map(value -> value.getName()).orElse("");
		return clientValue.equals(serverValue);
	}

	protected void assertInputValue(AjaxAutoCompleteTestValue value) {

		assertEquals(value.getName(), inputDiv.getValueText());
		assertFalse("Auto-complete pop-up still shown.", isAutoCompletePopupDisplayed());
	}

	protected void assertPopupValues(AjaxAutoCompleteTestValue...values) {

		assertPopupValues(Arrays.asList(values));
	}

	protected void assertPopupValues(List<AjaxAutoCompleteTestValue> values) {

		super.assertPopupValues(//
			AjaxAutoCompleteTestValue::getName,
			values);
	}
}

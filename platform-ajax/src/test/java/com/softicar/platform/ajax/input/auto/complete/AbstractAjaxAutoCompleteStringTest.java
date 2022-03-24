package com.softicar.platform.ajax.input.auto.complete;

import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.document.DomBody;
import com.softicar.platform.dom.input.IDomTextualInput;
import com.softicar.platform.dom.style.CssPixel;
import com.softicar.platform.dom.style.CssStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public abstract class AbstractAjaxAutoCompleteStringTest extends AbstractAjaxAutoCompleteTest {

	protected static final String AMBIGUOUS_INPUT = "ir";
	protected static final String INCOMPLETE_ITEM1_NAME = "fir";
	protected static final String INVALID_INPUT = "xxx";
	protected static final AjaxAutoCompleteTestItem ITEM1 = new AjaxAutoCompleteTestItem("first");
	protected static final AjaxAutoCompleteTestItem ITEM2 = new AjaxAutoCompleteTestItem("second");
	protected static final AjaxAutoCompleteTestItem ITEM3 = new AjaxAutoCompleteTestItem("third");
	protected static final AjaxAutoCompleteTestItem ITEM4 = new AjaxAutoCompleteTestItem("thirdPlusOne");
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
		this.inputField = inputDiv.getInputField();
	}

	protected boolean isValueSubmitted() {

		String clientValue = Optional.ofNullable(getAttributeValue(inputField, "value")).orElse("");
		String serverValue = inputDiv.getValue().map(item -> item.getName()).orElse("");
		return clientValue.equals(serverValue);
	}

	protected void assertInputValue(AjaxAutoCompleteTestItem item) {

		assertEquals(item.getName(), getAttributeValue(inputField, "value"));
		assertFalse("Auto-complete pop-up still shown.", isAutoCompletePopupDisplayed());
	}

	protected void assertPopupItems(AjaxAutoCompleteTestItem...items) {

		assertPopupItems(Arrays.asList(items));
	}

	protected void assertPopupItems(List<AjaxAutoCompleteTestItem> items) {

		super.assertPopupItems(//
			AjaxAutoCompleteTestItem::getName,
			items);
	}
}

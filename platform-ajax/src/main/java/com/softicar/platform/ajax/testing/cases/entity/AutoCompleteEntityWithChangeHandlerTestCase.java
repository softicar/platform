package com.softicar.platform.ajax.testing.cases.entity;

import com.softicar.platform.ajax.testing.cases.AbstractTestCaseDiv;
import com.softicar.platform.common.core.entity.IEntity;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteDefaultInputEngine;
import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteInput;
import com.softicar.platform.dom.styles.CssDisplay;
import java.util.Collection;

public class AutoCompleteEntityWithChangeHandlerTestCase extends AbstractTestCaseDiv {

	private final DomAutoCompleteInput<AjaxTestEntity> input;
	private final DomAutoCompleteInput<IEntity> otherInput;

	public AutoCompleteEntityWithChangeHandlerTestCase() {

		appendChild(new DomDiv()).appendText("IEntity auto complete with change handler:");

		Collection<AjaxTestEntity> items = AjaxTestEntity
			.generate(//
				"foo",
				"bar",
				"baz",
				"bazinga");
		DomAutoCompleteDefaultInputEngine<AjaxTestEntity> engine = new DomAutoCompleteDefaultInputEngine<>(() -> items);
		this.input = new DomAutoCompleteInput<>(engine);
		this.input.addChangeCallback(this::handleChange);
		this.otherInput = new DomAutoCompleteInput<>(new DomAutoCompleteDefaultInputEngine<>());

		DomDiv container = appendChild(new DomDiv());
		container.setStyle(CssDisplay.INLINE_BLOCK);
		container.appendChild(input);
		container.appendNewChild(DomElementTag.P);
		container.appendChild(otherInput);
	}

	private void handleChange() {

		log("callback, value: '%s'", input.getSelection().getValueOrNull());
	}
}

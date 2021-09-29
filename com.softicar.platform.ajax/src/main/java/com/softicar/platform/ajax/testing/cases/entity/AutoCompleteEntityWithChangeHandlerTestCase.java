package com.softicar.platform.ajax.testing.cases.entity;

import com.softicar.platform.ajax.testing.cases.AbstractTestCaseDiv;
import com.softicar.platform.common.core.entity.IEntity;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.input.auto.entity.DomAutoCompleteEntityInMemoryInputEngine;
import com.softicar.platform.dom.elements.input.auto.entity.DomAutoCompleteEntityInput;
import com.softicar.platform.dom.styles.CssDisplay;
import java.util.Collection;

public class AutoCompleteEntityWithChangeHandlerTestCase extends AbstractTestCaseDiv {

	private final DomAutoCompleteEntityInput<AjaxTestEntity> input;
	private final DomAutoCompleteEntityInput<IEntity> otherInput;

	public AutoCompleteEntityWithChangeHandlerTestCase() {

		appendChild(new DomDiv()).appendText("IEntity auto complete with change handler:");

		Collection<AjaxTestEntity> items = AjaxTestEntity
			.generate(//
				"foo",
				"bar",
				"baz",
				"bazinga");
		DomAutoCompleteEntityInMemoryInputEngine<AjaxTestEntity> engine = new DomAutoCompleteEntityInMemoryInputEngine<>(items);
		this.input = new DomAutoCompleteEntityInput<>(engine);
		this.input.setChangeCallback(this::handleChange);
		this.otherInput = new DomAutoCompleteEntityInput<>(new DomAutoCompleteEntityInMemoryInputEngine<>());

		DomDiv container = appendChild(new DomDiv());
		container.setStyle(CssDisplay.INLINE_BLOCK);
		container.appendChild(input);
		container.appendNewChild(DomElementTag.P);
		container.appendChild(otherInput);
	}

	private void handleChange() {

		log("callback, value: '%s'", input.getSelection().getValueOrNull());
		otherInput.refreshFilters();
	}
}

package com.softicar.platform.ajax.testing.cases.entity;

import com.softicar.platform.ajax.testing.cases.AbstractTestCaseDiv;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomElementsImages;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteDefaultInputEngine;
import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteInput;
import com.softicar.platform.dom.styles.CssDisplay;
import java.util.Collection;

public class AutoCompleteEntityTestCase extends AbstractTestCaseDiv {

	private final DomAutoCompleteInput<AjaxTestEntity> input;

	public AutoCompleteEntityTestCase() {

		appendChild(new DomDiv()).appendText("Auto complete without change handler:");

		Collection<AjaxTestEntity> items = AjaxTestEntity
			.generate(//
				"foo",
				"bar",
				"baz",
				"bazinga",
				"five",
				"six",
				"seven",
				"eight",
				"nine",
				"ten",
				"eleven",
				"twelve");
		DomAutoCompleteDefaultInputEngine<AjaxTestEntity> engine = new DomAutoCompleteDefaultInputEngine<>(() -> items);
		this.input = new DomAutoCompleteInput<>(engine);

		DomDiv container = appendChild(new DomDiv());
		container.setStyle(CssDisplay.INLINE_BLOCK);
		container.appendChild(input);
		container.appendChild(new GetValueButton());
	}

	private class GetValueButton extends DomButton {

		public GetValueButton() {

			setIcon(DomElementsImages.INFO.getResource());
			setLabel(IDisplayString.create("get value and show alert"));
			setClickCallback(this::handleClick);
		}

		private void handleClick() {

			executeAlert(IDisplayString.create(input.getValueOrNull() + ""));
		}
	}
}

package com.softicar.platform.ajax.testing.cases.entity;

import com.softicar.platform.ajax.image.AjaxImages;
import com.softicar.platform.ajax.testing.cases.AbstractTestCaseDiv;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.input.auto.entity.DomAutoCompleteEntityInMemoryInputEngine;
import com.softicar.platform.dom.elements.input.auto.entity.DomAutoCompleteEntityInput;
import com.softicar.platform.dom.styles.CssDisplay;
import java.util.Collection;

public class AutoCompleteEntityTestCase extends AbstractTestCaseDiv {

	private final DomAutoCompleteEntityInput<AjaxTestEntity> input;

	public AutoCompleteEntityTestCase() {

		appendChild(new DomDiv()).appendText("IEntity auto complete without change handler:");

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
		DomAutoCompleteEntityInMemoryInputEngine<AjaxTestEntity> engine = new DomAutoCompleteEntityInMemoryInputEngine<>(() -> items);
		this.input = new DomAutoCompleteEntityInput<>(engine);

		DomDiv container = appendChild(new DomDiv());
		container.setStyle(CssDisplay.INLINE_BLOCK);
		container.appendChild(input);
		container.appendChild(new GetValueButton());
	}

	private class GetValueButton extends DomButton {

		public GetValueButton() {

			setIcon(AjaxImages.EMBLEM_AUTO_COMPLETE_VALUE_VALID.getResource());
			setLabel(IDisplayString.create("get value and show alert"));
			setClickCallback(this::handleClick);
		}

		private void handleClick() {

			executeAlert(IDisplayString.create(input.getSelection().getValueOrNull() + ""));
		}
	}
}

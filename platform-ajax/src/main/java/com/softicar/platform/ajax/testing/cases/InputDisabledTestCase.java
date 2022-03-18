package com.softicar.platform.ajax.testing.cases;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.input.DomTextInput;

public class InputDisabledTestCase extends AbstractTestCaseDiv {

	private final DomTextInput input;

	public InputDisabledTestCase() {

		this.input = new DomTextInput().setInputText("some input");

		appendChild(input);
		appendChild(
			new DomActionBar(
				new DomButton()//
					.setLabel(IDisplayString.create("Click to Disable"))
					.setClickCallback(() -> input.setDisabled(true)),
				new DomButton()//
					.setLabel(IDisplayString.create("Click to Enable"))
					.setClickCallback(() -> input.setDisabled(false)),
				new DomButton()//
					.setLabel(IDisplayString.create("Click to Toggle"))
					.setClickCallback(() -> input.setDisabled(!input.isDisabled()))));

	}
}

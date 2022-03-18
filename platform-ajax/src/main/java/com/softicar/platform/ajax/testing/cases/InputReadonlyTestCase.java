package com.softicar.platform.ajax.testing.cases;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.input.DomTextInput;

public class InputReadonlyTestCase extends AbstractTestCaseDiv {

	private final DomTextInput input;

	public InputReadonlyTestCase() {

		this.input = new DomTextInput().setInputText("some input");

		appendChild(input);
		appendChild(
			new DomActionBar(
				new DomButton()//
					.setLabel(IDisplayString.create("Click to Readonly"))
					.setClickCallback(() -> input.setReadonly(true)),
				new DomButton()//
					.setLabel(IDisplayString.create("Click to Read-Write"))
					.setClickCallback(() -> input.setReadonly(false)),
				new DomButton()//
					.setLabel(IDisplayString.create("Click to Toggle"))
					.setClickCallback(() -> input.setReadonly(!input.isReadonly()))));

	}
}

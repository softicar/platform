package com.softicar.platform.ajax.utils;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.dom.elements.button.DomButton;

public class TestButton extends DomButton {

	public TestButton() {

		this(INullaryVoidFunction.NO_OPERATION);
	}

	public TestButton(INullaryVoidFunction callback) {

		setLabel(IDisplayString.create("Test"));
		setClickCallback(callback);
	}
}

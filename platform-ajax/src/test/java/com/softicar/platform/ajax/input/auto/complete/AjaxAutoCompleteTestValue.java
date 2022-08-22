package com.softicar.platform.ajax.input.auto.complete;

import com.softicar.platform.ajax.testing.selenium.engine.level.low.interfaces.elements.IAjaxSeleniumTestDomAutoCompleteTestValue;
import com.softicar.platform.common.core.i18n.IDisplayString;

public class AjaxAutoCompleteTestValue implements IAjaxSeleniumTestDomAutoCompleteTestValue {

	private final String name;

	public AjaxAutoCompleteTestValue(String name) {

		this.name = name;
	}

	@Override
	public String getName() {

		return name;
	}

	@Override
	public IDisplayString toDisplay() {

		return IDisplayString.create(name);
	}
}

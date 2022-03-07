package com.softicar.platform.ajax.input.auto.complete;

import com.softicar.platform.ajax.testing.selenium.engine.level.low.interfaces.elements.IAjaxSeleniumTestDomAutoCompleteTestItem;

public class AjaxAutoCompleteTestItem implements IAjaxSeleniumTestDomAutoCompleteTestItem {

	private final String name;

	public AjaxAutoCompleteTestItem(String name) {

		this.name = name;
	}

	@Override
	public String getName() {

		return name;
	}
}

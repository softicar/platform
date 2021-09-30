package com.softicar.platform.ajax.input.auto.complete;

import com.softicar.platform.ajax.testing.selenium.engine.level.low.interfaces.elements.IAjaxSeleniumTestDomAutoCompleteTestItem;

public class AjaxAutoCompleteTestItem implements IAjaxSeleniumTestDomAutoCompleteTestItem {

	private final String name;
	private final String description;

	public AjaxAutoCompleteTestItem(String name) {

		this(name, "");
	}

	public AjaxAutoCompleteTestItem(String name, String description) {

		this.name = name;
		this.description = description;
	}

	@Override
	public String getName() {

		return name;
	}

	@Override
	public String getDescription() {

		return description;
	}
}

package com.softicar.platform.ajax.testing.cases;

import com.softicar.platform.common.core.thread.sleep.Sleep;
import com.softicar.platform.dom.elements.input.auto.string.DomAutoCompleteStringInMemoryInputEngine;
import com.softicar.platform.dom.elements.input.auto.string.DomAutoCompleteStringInput;
import com.softicar.platform.dom.input.auto.DomAutoCompleteList;
import java.util.Arrays;
import java.util.Collection;

class AutoCompleteTestCaseInput extends DomAutoCompleteStringInput {

	private static final Collection<String> ITEMS = Arrays
		.asList(//
			"abandon",
			"ability",
			"able",
			"abort",
			"about",
			"above",
			"abroad",
			"absence",
			"absent",
			"absolute",
			"abstract",
			"abuse",
			"abusive",
			"academic",
			"accept",
			"acceptable",
			"acceptance",
			"access");

	private final AbstractTestCaseDiv testCaseDiv;

	public AutoCompleteTestCaseInput(AbstractTestCaseDiv testCaseDiv) {

		super(new DomAutoCompleteStringInMemoryInputEngine(ITEMS));
		this.testCaseDiv = testCaseDiv;
	}

	public AutoCompleteTestCaseInput listenToChange() {

		addChangeCallback(() -> testCaseDiv.log("value: '%s'", getSelection().getValueOrNull()));
		return this;
	}

	@Override
	public DomAutoCompleteList getItemList(String pattern) {

		Sleep.sleep(500);
		return super.getItemList(pattern);
	}
}

package com.softicar.platform.ajax.testing.cases;

import com.softicar.platform.common.core.thread.sleep.Sleep;
import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteDefaultInputEngine;
import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteInput;
import com.softicar.platform.dom.input.auto.DomAutoCompleteList;
import java.util.Arrays;
import java.util.Collection;

class AutoCompleteTestCaseInput extends DomAutoCompleteInput<String> {

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

		super(new DomAutoCompleteDefaultInputEngine<>(() -> ITEMS));
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

package com.softicar.platform.dom.elements.testing.node.tester;

import com.softicar.platform.common.date.DateUtils;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.dom.elements.testing.engine.IDomTestEngine;
import com.softicar.platform.dom.elements.time.day.DomDayInput;

public class DomDayInputTester extends AbstractDomNodeTester<DomDayInput> {

	public DomDayInputTester(IDomTestEngine engine, DomDayInput node) {

		super(engine, node);
	}

	public DomDayInputTester inputString(String dateString) {

		Day day = DateUtils.parseDate(dateString);
		node.setDay(day);
		return this;
	}
}

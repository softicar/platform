package com.softicar.platform.dom.elements.testing.node.tester;

import com.softicar.platform.common.date.DateFormat;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.date.Time;
import com.softicar.platform.dom.elements.testing.engine.IDomTestEngine;
import com.softicar.platform.dom.elements.time.daytime.DomDayTimeInput;

public class DomDayTimeInputTester extends AbstractDomNodeTester<DomDayTimeInput> {

	public DomDayTimeInputTester(IDomTestEngine engine, DomDayTimeInput node) {

		super(engine, node);
	}

	public DomDayTimeInputTester inputStrings(String dayString, String timeString) {

		if (dayString != null && timeString != null) {
			Day day = Day.parse(DateFormat.YYYY_MM_DD, dayString);
			Time time = Time.parseTime(timeString);
			DayTime dayTime = new DayTime(day, time);
			node.setDayTime(dayTime);
		} else if (dayString == null && timeString == null) {
			node.clear();
		} else {
			throw new IllegalArgumentException();
		}
		return this;
	}
}

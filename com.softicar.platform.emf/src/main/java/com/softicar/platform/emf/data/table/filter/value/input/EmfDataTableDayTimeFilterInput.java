package com.softicar.platform.emf.data.table.filter.value.input;

import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.dom.elements.time.daytime.DomDayTimeInput;
import com.softicar.platform.emf.data.table.filter.value.IEmfDataTableValueFilterInput;

public class EmfDataTableDayTimeFilterInput extends DomDayTimeInput implements IEmfDataTableValueFilterInput<DayTime> {

	public EmfDataTableDayTimeFilterInput() {

		getDayInput().setDay(null);
		getTimeInput().clear();
	}

	@Override
	public DayTime getFilterValue() {

		return getDayTime();
	}

	@Override
	public void setFilterValue(DayTime value) {

		setDayTime(value);
	}

	@Override
	public void selectFirstInputElement() {

		getDayInput().getTextBoxInput().select();
	}
}

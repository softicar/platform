package com.softicar.platform.emf.data.table.filter.value.input;

import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.dom.elements.time.daytime.DomDayTimeInput;
import com.softicar.platform.emf.data.table.filter.value.IEmfDataTableValueFilterInput;

public class EmfDataTableDayTimeFilterInput extends DomDayTimeInput implements IEmfDataTableValueFilterInput<DayTime> {

	public EmfDataTableDayTimeFilterInput() {

		super(null);
	}

	@Override
	public DayTime getFilterValue() {

		return parseValue().orElse(null);
	}

	@Override
	public void setFilterValue(DayTime value) {

		setDayTime(value);
	}

	@Override
	public void selectFirstInputElement() {

		// TODO tell, don't ask
		getDayInput().getTextBoxInput().select();
	}
}

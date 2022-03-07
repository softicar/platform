package com.softicar.platform.emf.data.table.filter.value.input;

import com.softicar.platform.common.date.Day;
import com.softicar.platform.dom.elements.time.day.DomDayInput;
import com.softicar.platform.emf.data.table.filter.value.IEmfDataTableValueFilterInput;

public class EmfDataTableDayFilterInput extends DomDayInput implements IEmfDataTableValueFilterInput<Day> {

	public EmfDataTableDayFilterInput() {

		super(null);
	}

	@Override
	public Day getFilterValue() {

		return getValue().orElse(null);
	}

	@Override
	public void setFilterValue(Day value) {

		setValue(value);
	}

	@Override
	public void selectFirstInputElement() {

		getTextBoxInput().select();
	}
}

package com.softicar.platform.emf.data.table.filter.value.input;

import com.softicar.platform.dom.elements.input.DomIntegerInput;
import com.softicar.platform.emf.data.table.filter.value.IEmfDataTableValueFilterInput;

public class EmfDataTableIntegerFilterInput extends DomIntegerInput implements IEmfDataTableValueFilterInput<Integer> {

	@Override
	public Integer getFilterValue() {

		return getValue().orElse(null);
	}

	@Override
	public void setFilterValue(Integer value) {

		setValue(value);
	}

	@Override
	public void selectFirstInputElement() {

		select();
	}
}

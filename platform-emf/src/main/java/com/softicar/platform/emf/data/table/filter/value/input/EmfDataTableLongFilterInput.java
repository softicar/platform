package com.softicar.platform.emf.data.table.filter.value.input;

import com.softicar.platform.dom.elements.input.DomLongInput;
import com.softicar.platform.emf.data.table.filter.value.IEmfDataTableValueFilterInput;

public class EmfDataTableLongFilterInput extends DomLongInput implements IEmfDataTableValueFilterInput<Long> {

	@Override
	public Long getFilterValue() {

		return getValue().orElse(null);
	}

	@Override
	public void setFilterValue(Long value) {

		setValue(value);
	}

	@Override
	public void selectFirstInputElement() {

		select();
	}
}

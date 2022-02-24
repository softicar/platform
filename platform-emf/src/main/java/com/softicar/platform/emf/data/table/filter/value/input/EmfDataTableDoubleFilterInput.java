package com.softicar.platform.emf.data.table.filter.value.input;

import com.softicar.platform.dom.elements.input.DomDoubleInput;
import com.softicar.platform.emf.data.table.filter.value.IEmfDataTableValueFilterInput;

public class EmfDataTableDoubleFilterInput extends DomDoubleInput implements IEmfDataTableValueFilterInput<Double> {

	@Override
	public Double getFilterValue() {

		return getValue().orElse(null);
	}

	@Override
	public void setFilterValue(Double value) {

		setValue(value);
	}

	@Override
	public void selectFirstInputElement() {

		select();
	}
}

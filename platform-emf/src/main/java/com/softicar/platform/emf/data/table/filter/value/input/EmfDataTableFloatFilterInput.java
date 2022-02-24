package com.softicar.platform.emf.data.table.filter.value.input;

import com.softicar.platform.dom.elements.input.DomFloatInput;
import com.softicar.platform.emf.data.table.filter.value.IEmfDataTableValueFilterInput;

public class EmfDataTableFloatFilterInput extends DomFloatInput implements IEmfDataTableValueFilterInput<Float> {

	@Override
	public Float getFilterValue() {

		return getValue().orElse(null);
	}

	@Override
	public void setFilterValue(Float value) {

		setValue(value);
	}

	@Override
	public void selectFirstInputElement() {

		select();
	}
}

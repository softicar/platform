package com.softicar.platform.emf.data.table.filter.value.input;

import com.softicar.platform.dom.elements.number.decimal.DomBigDecimalInput;
import com.softicar.platform.emf.data.table.filter.value.IEmfDataTableValueFilterInput;
import java.math.BigDecimal;

public class EmfDataTableBigDecimalFilterInput extends DomBigDecimalInput implements IEmfDataTableValueFilterInput<BigDecimal> {

	@Override
	public BigDecimal getFilterValue() {

		return getValue().orElse(null);
	}

	@Override
	public void setFilterValue(BigDecimal value) {

		setValue(value);
	}

	@Override
	public void selectFirstInputElement() {

		select();
	}
}

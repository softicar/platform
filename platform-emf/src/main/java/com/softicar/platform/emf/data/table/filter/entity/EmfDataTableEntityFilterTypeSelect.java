package com.softicar.platform.emf.data.table.filter.entity;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.AbstractDomValueSelect;
import com.softicar.platform.emf.data.table.filter.IEmfDataTableFilterTypeSelect;

class EmfDataTableEntityFilterTypeSelect extends AbstractDomValueSelect<EmfDataTableEntityFilterType>
		implements IEmfDataTableFilterTypeSelect<EmfDataTableEntityFilterType> {

	public EmfDataTableEntityFilterTypeSelect() {

		addValues(EmfDataTableEntityFilterType.values());
		setSelectedValue(EmfDataTableEntityFilterType.IS);
	}

	@Override
	public EmfDataTableEntityFilterType getSelectedType() {

		return getSelectedValue();
	}

	@Override
	public void setSelectedType(EmfDataTableEntityFilterType type) {

		setSelectedValue(type);
	}

	@Override
	protected Integer getValueId(EmfDataTableEntityFilterType value) {

		return value.ordinal();
	}

	@Override
	protected IDisplayString getValueDisplayString(EmfDataTableEntityFilterType value) {

		return value.toDisplay();
	}
}

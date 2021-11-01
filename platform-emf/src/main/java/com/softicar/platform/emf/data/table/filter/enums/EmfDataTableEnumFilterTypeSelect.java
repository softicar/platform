package com.softicar.platform.emf.data.table.filter.enums;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.AbstractDomValueSelect;
import com.softicar.platform.emf.data.table.filter.IEmfDataTableFilterTypeSelect;

class EmfDataTableEnumFilterTypeSelect extends AbstractDomValueSelect<EmfDataTableEnumFilterType>
		implements IEmfDataTableFilterTypeSelect<EmfDataTableEnumFilterType> {

	public EmfDataTableEnumFilterTypeSelect() {

		addValues(EmfDataTableEnumFilterType.values());
		setSelectedValue(EmfDataTableEnumFilterType.IS);
	}

	@Override
	public EmfDataTableEnumFilterType getSelectedType() {

		return getSelectedValue();
	}

	@Override
	public void setSelectedType(EmfDataTableEnumFilterType type) {

		setSelectedValue(type);
	}

	@Override
	protected Integer getValueId(EmfDataTableEnumFilterType value) {

		return value.ordinal();
	}

	@Override
	protected IDisplayString getValueDisplayString(EmfDataTableEnumFilterType value) {

		return value.toDisplay();
	}
}

package com.softicar.platform.emf.data.table.filter.entity;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.i18n.IDisplayable;
import com.softicar.platform.emf.data.table.EmfDataTableI18n;

public enum EmfDataTableEntityFilterType implements IDisplayable {

	IS(EmfDataTableI18n.IS),
	IS_NOT(EmfDataTableI18n.IS_NOT);

	private final IDisplayString label;

	private EmfDataTableEntityFilterType(IDisplayString label) {

		this.label = label;
	}

	@Override
	public IDisplayString toDisplay() {

		return label;
	}
}

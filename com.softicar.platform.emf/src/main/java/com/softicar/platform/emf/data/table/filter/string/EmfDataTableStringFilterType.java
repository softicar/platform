package com.softicar.platform.emf.data.table.filter.string;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.i18n.IDisplayable;
import com.softicar.platform.emf.data.table.EmfDataTableI18n;

public enum EmfDataTableStringFilterType implements IDisplayable {

	CONTAINS_WORDS(EmfDataTableI18n.CONTAINS_WORDS),
	CONTAINS_TEXT(EmfDataTableI18n.CONTAINS_TEXT),
	EQUALS_TEXT(EmfDataTableI18n.EQUALS_TEXT),
	MATCHES_REGEXP(EmfDataTableI18n.MATCHES_REGULAR_EXPRESSION);

	private final IDisplayString label;

	private EmfDataTableStringFilterType(IDisplayString label) {

		this.label = label;
	}

	@Override
	public IDisplayString toDisplay() {

		return label;
	}
}

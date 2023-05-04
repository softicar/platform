package com.softicar.platform.emf.data.table.filter.string;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.i18n.IDisplayable;
import com.softicar.platform.emf.data.table.EmfDataTableI18n;

public enum EmfDataTableStringFilterType implements IDisplayable {

	CONTAINS_WORDS(EmfDataTableI18n.CONTAINS_WORDS),
	CONTAINS_TEXT(EmfDataTableI18n.CONTAINS_TEXT),
	DOES_NOT_CONTAIN_TEXT(EmfDataTableI18n.DOES_NOT_CONTAIN_TEXT),
	IS_EMPTY(EmfDataTableI18n.IS_EMPTY),
	EQUALS_TEXT(EmfDataTableI18n.EQUALS_TEXT),
	MATCHES_REGEXP(EmfDataTableI18n.MATCHES_REGULAR_EXPRESSION),
	IS_NOT_EMPTY(EmfDataTableI18n.IS_NOT_EMPTY);

	private final IDisplayString label;

	private EmfDataTableStringFilterType(IDisplayString label) {

		this.label = label;
	}

	@Override
	public IDisplayString toDisplay() {

		return label;
	}

	public boolean isEmpty() {

		return equals(IS_EMPTY);
	}

	public boolean isNotEmpty() {

		return equals(IS_NOT_EMPTY);
	}
}

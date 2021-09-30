package com.softicar.platform.emf.data.table.filter.bool;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.elements.AbstractDomValueSelect;
import com.softicar.platform.emf.data.table.EmfDataTableDivMarker;
import com.softicar.platform.emf.data.table.EmfDataTableI18n;

class BooleanSelect extends AbstractDomValueSelect<Boolean> {

	public BooleanSelect() {

		addNilValue(DomI18n.PLEASE_SELECT.encloseInBrackets());
		addValue(true);
		addValue(false);
		setMarker(EmfDataTableDivMarker.FILTER_INPUT_BOOLEAN);
	}

	@Override
	protected Integer getValueId(Boolean value) {

		return value? 1 : 0;
	}

	@Override
	protected IDisplayString getValueDisplayString(Boolean value) {

		return value? EmfDataTableI18n.YES : EmfDataTableI18n.NO;
	}
}

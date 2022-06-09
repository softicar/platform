package com.softicar.platform.emf.data.table.filter.string;

import com.softicar.platform.dom.input.DomTextInput;
import com.softicar.platform.emf.data.table.EmfDataTableDivMarker;

class EmfDataTableStringFilterInput extends DomTextInput {

	public EmfDataTableStringFilterInput() {

		super("");

		addMarker(EmfDataTableDivMarker.FILTER_INPUT_STRING);
	}

	public String getNormalizedFilterText() {

		return new FilterTextNormalizer(getValueTrimmed()).getNormalized();
	}

	public void refresh(EmfDataTableStringFilterType filterType) {

		if (filterType.isEmpty() || filterType.isNotEmpty()) {
			setDisplayNone(true);
			setValue("");
		} else {
			setDisplayNone(false);
		}
	}
}

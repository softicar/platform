package com.softicar.platform.emf.data.table.filter.string;

import com.softicar.platform.dom.input.DomTextInput;
import com.softicar.platform.emf.EmfTestMarker;

class EmfDataTableStringFilterInput extends DomTextInput {

	public EmfDataTableStringFilterInput() {

		super("");

		addMarker(EmfTestMarker.DATA_TABLE_FILTER_INPUT_STRING);
	}

	public String getNormalizedFilterText() {

		return new FilterTextNormalizer(getValueTextTrimmed()).getNormalized();
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

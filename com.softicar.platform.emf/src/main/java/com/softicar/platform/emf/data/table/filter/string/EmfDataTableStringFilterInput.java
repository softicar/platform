package com.softicar.platform.emf.data.table.filter.string;

import com.softicar.platform.dom.input.DomTextInput;
import com.softicar.platform.emf.data.table.EmfDataTableDivMarker;

class EmfDataTableStringFilterInput extends DomTextInput {

	public EmfDataTableStringFilterInput() {

		super("", 100);

		setMarker(EmfDataTableDivMarker.FILTER_INPUT_STRING);
	}

	public String getNormalizedFilterTextOrNull() {

		String text = getTextOrNull();
		return text != null? new FilterTextNormalizer(text).getNormalized() : null;
	}
}

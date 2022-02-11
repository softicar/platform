package com.softicar.platform.emf.data.table.empty;

import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.emf.EmfCssClasses;
import com.softicar.platform.emf.data.table.EmfDataTableI18n;

public class EmfDataTableEmptyTablePlaceholderDiv extends DomDiv {

	public EmfDataTableEmptyTablePlaceholderDiv() {

		setCssClass(EmfCssClasses.EMF_DATA_TABLE_EMPTY_TABLE_PLACEHOLDER_DIV);
		appendText(EmfDataTableI18n.NO_ENTRIES_FOUND.enclose("(", ")"));
	}
}

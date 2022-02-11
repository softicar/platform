package com.softicar.platform.emf.attribute.field.foreign.entity.collection;

import com.softicar.platform.dom.elements.DomCell;
import com.softicar.platform.emf.data.table.empty.EmfDataTableEmptyTablePlaceholderDiv;

public class EmfEmptyTablePlaceholderCell extends DomCell {

	public EmfEmptyTablePlaceholderCell(int columnCount) {

		setColSpan(columnCount);
		appendChild(new EmfDataTableEmptyTablePlaceholderDiv());
	}
}

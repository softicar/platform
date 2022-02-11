package com.softicar.platform.emf.attribute.field.foreign.entity.collection;

import com.softicar.platform.dom.elements.DomRow;

public class EmfEmptyTablePlaceholderRow extends DomRow {

	public EmfEmptyTablePlaceholderRow(int columnCount) {

		appendChild(new EmfEmptyTablePlaceholderCell(columnCount));
	}
}

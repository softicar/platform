package com.softicar.platform.emf.data.table.column.handler;

import com.softicar.platform.dom.elements.label.DomPreformattedLabel;
import com.softicar.platform.emf.data.table.IEmfDataTableCell;

public class EmfDataTablePreformattedLabelColumnHandler extends EmfDataTableValueBasedColumnHandler<String> {

	@Override
	public void buildCell(IEmfDataTableCell<?, String> cell, String value) {

		cell.appendChild(new DomPreformattedLabel(value));
	}
}

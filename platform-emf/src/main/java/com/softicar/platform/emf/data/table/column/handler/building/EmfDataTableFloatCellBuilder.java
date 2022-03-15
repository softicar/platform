package com.softicar.platform.emf.data.table.column.handler.building;

import com.softicar.platform.dom.elements.number.decimal.DomFloatDisplay;
import com.softicar.platform.emf.data.table.IEmfDataTableCell;

class EmfDataTableFloatCellBuilder implements IEmfDataTableCellBuilder<Float> {

	@Override
	public void buildCell(IEmfDataTableCell<?, Float> cell, Float value) {

		cell.appendChild(new DomFloatDisplay().setValue(value));
	}
}

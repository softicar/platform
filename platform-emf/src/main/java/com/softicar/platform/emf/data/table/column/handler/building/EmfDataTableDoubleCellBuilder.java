package com.softicar.platform.emf.data.table.column.handler.building;

import com.softicar.platform.dom.elements.number.decimal.DomDoubleDisplay;
import com.softicar.platform.emf.data.table.IEmfDataTableCell;

class EmfDataTableDoubleCellBuilder implements IEmfDataTableCellBuilder<Double> {

	@Override
	public void buildCell(IEmfDataTableCell<?, Double> cell, Double value) {

		cell.appendChild(new DomDoubleDisplay().setValue(value));
	}
}

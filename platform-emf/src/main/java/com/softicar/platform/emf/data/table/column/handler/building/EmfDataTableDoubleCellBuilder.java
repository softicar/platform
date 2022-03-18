package com.softicar.platform.emf.data.table.column.handler.building;

import com.softicar.platform.common.core.number.formatter.BigDecimalFormatter;
import com.softicar.platform.emf.data.table.IEmfDataTableCell;

class EmfDataTableDoubleCellBuilder implements IEmfDataTableCellBuilder<Double> {

	@Override
	public void buildCell(IEmfDataTableCell<?, Double> cell, Double value) {

		if (value != null) {
			cell.appendText(new BigDecimalFormatter().format(value));
		}
	}
}

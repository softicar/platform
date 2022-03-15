package com.softicar.platform.emf.data.table.column.handler.building;

import com.softicar.platform.common.core.number.formatter.BigDecimalFormatter;
import com.softicar.platform.emf.data.table.IEmfDataTableCell;

class EmfDataTableFloatCellBuilder implements IEmfDataTableCellBuilder<Float> {

	@Override
	public void buildCell(IEmfDataTableCell<?, Float> cell, Float value) {

		if (value != null) {
			cell.appendText(new BigDecimalFormatter().format(value));
		}
	}
}

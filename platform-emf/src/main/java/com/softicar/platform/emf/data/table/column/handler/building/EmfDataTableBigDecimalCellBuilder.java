package com.softicar.platform.emf.data.table.column.handler.building;

import com.softicar.platform.emf.data.table.IEmfDataTableCell;
import java.math.BigDecimal;

class EmfDataTableBigDecimalCellBuilder implements IEmfDataTableCellBuilder<BigDecimal> {

	@Override
	public void buildCell(IEmfDataTableCell<?, BigDecimal> cell, BigDecimal value) {

		if (value != null) {
			cell.appendText(value.toPlainString());
		}
	}
}

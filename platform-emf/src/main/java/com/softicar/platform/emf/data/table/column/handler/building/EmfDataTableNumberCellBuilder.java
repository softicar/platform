package com.softicar.platform.emf.data.table.column.handler.building;

import com.softicar.platform.emf.data.table.IEmfDataTableCell;
import java.math.BigDecimal;

class EmfDataTableNumberCellBuilder implements IEmfDataTableCellBuilder<Number> {

	@Override
	public void buildCell(IEmfDataTableCell<?, Number> cell, Number value) {

		if (value != null) {
			if (value instanceof BigDecimal) {
				cell.appendText(value.toString());
			} else if (value instanceof Double) {
				buildCell(cell, BigDecimal.valueOf(value.doubleValue()));
			} else if (value instanceof Float) {
				buildCell(cell, BigDecimal.valueOf(value.floatValue()));
			} else if (value instanceof Integer) {
				buildCell(cell, BigDecimal.valueOf(value.intValue()));
			}
		}
	}
}

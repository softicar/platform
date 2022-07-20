package com.softicar.platform.emf.data.table.column.handler.building;

import com.softicar.platform.common.core.i18n.IDisplayable;
import com.softicar.platform.emf.data.table.IEmfDataTableCell;

class EmfDataTableDisplayableCellBuilder<V extends IDisplayable> implements IEmfDataTableCellBuilder<V> {

	@Override
	public void buildCell(IEmfDataTableCell<?, V> cell, V value) {

		if (value != null) {
			cell.appendChild(value.toDisplay());
		}
	}
}

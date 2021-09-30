package com.softicar.platform.emf.data.table.column.handler.building;

import com.softicar.platform.common.core.i18n.IDisplayable;
import com.softicar.platform.emf.data.table.IEmfDataTableCell;

class EmfDataTableDisplayableCellBuilder implements IEmfDataTableCellBuilder<IDisplayable> {

	@Override
	public void buildCell(IEmfDataTableCell<?, IDisplayable> cell, IDisplayable value) {

		if (value != null) {
			cell.appendChild(value.toDisplay());
		}
	}
}

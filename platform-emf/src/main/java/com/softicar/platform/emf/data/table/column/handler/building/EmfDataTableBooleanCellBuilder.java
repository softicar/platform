package com.softicar.platform.emf.data.table.column.handler.building;

import com.softicar.platform.dom.elements.checkbox.DomCheckbox;
import com.softicar.platform.emf.data.table.IEmfDataTableCell;

class EmfDataTableBooleanCellBuilder implements IEmfDataTableCellBuilder<Boolean> {

	@Override
	public void buildCell(IEmfDataTableCell<?, Boolean> cell, Boolean value) {

		if (value != null) {
			cell.appendChild(new DomCheckbox().setEnabled(false).setChecked(value));
		}
	}
}

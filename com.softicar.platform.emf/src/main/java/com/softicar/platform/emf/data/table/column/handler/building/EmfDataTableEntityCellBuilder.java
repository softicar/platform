package com.softicar.platform.emf.data.table.column.handler.building;

import com.softicar.platform.common.core.entity.IEntity;
import com.softicar.platform.emf.data.table.IEmfDataTableCell;
import com.softicar.platform.emf.data.table.column.settings.IEmfDataTableColumnSettings;

public class EmfDataTableEntityCellBuilder<T extends IEntity> implements IEmfDataTableCellBuilder<T> {

	@Override
	public void buildCell(IEmfDataTableCell<?, T> cell, T value) {

		IEmfDataTableColumnSettings columnSettings = cell.getColumn().getSettings();

		if (value != null) {
			if (columnSettings.isShowIds()) {
				cell.appendText(value.toDisplay());
			} else {
				cell.appendText(value.toDisplayWithoutId());
			}
		}
	}
}

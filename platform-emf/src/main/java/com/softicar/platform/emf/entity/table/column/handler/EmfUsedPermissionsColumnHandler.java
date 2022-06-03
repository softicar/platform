package com.softicar.platform.emf.entity.table.column.handler;

import com.softicar.platform.emf.data.table.IEmfDataTableCell;
import com.softicar.platform.emf.data.table.column.handler.EmfDataTableValueBasedColumnHandler;
import com.softicar.platform.emf.entity.table.permission.EmfTablePermissionOverviewDiv;
import com.softicar.platform.emf.table.EmfTableWrapper;

public class EmfUsedPermissionsColumnHandler extends EmfDataTableValueBasedColumnHandler<EmfTableWrapper> {

	@Override
	public void buildCell(IEmfDataTableCell<?, EmfTableWrapper> cell, EmfTableWrapper value) {

		cell.appendChild(new EmfTablePermissionOverviewDiv(value.getTable()));
	}
}

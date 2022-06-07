package com.softicar.platform.emf.module.permission;

import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.emf.data.table.IEmfDataTableCell;
import com.softicar.platform.emf.data.table.column.IEmfDataTableColumn;
import com.softicar.platform.emf.data.table.column.handler.EmfDataTableValueBasedColumnHandler;
import com.softicar.platform.emf.entity.table.view.EmfTableViewButton;
import com.softicar.platform.emf.permission.CurrentEmfPermissionRegistry;
import com.softicar.platform.emf.permission.EmfPermissionWrapper;
import com.softicar.platform.emf.table.IEmfTable;

class EmfTableForPermissionColumnHandler extends EmfDataTableValueBasedColumnHandler<EmfPermissionWrapper> {

	@Override
	public void buildCell(IEmfDataTableCell<?, EmfPermissionWrapper> cell, EmfPermissionWrapper permissionWrapper) {

		for (IEmfTable<?, ?, ?> table: CurrentEmfPermissionRegistry.get().getAtomicPermissionTables(permissionWrapper.getPermission())) {
			cell.appendChild(new EmfTableViewButton(table));
			cell.appendNewChild(DomElementTag.BR);
		}
	}

	@Override
	public boolean isSortable(IEmfDataTableColumn<?, EmfPermissionWrapper> column) {

		return false;
	}
}

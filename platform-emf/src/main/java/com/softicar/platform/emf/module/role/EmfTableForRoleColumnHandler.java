package com.softicar.platform.emf.module.role;

import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.emf.authorization.role.CurrentEmfRoleRegistry;
import com.softicar.platform.emf.authorization.role.EmfRoleWrapper;
import com.softicar.platform.emf.data.table.IEmfDataTableCell;
import com.softicar.platform.emf.data.table.column.IEmfDataTableColumn;
import com.softicar.platform.emf.data.table.column.handler.EmfDataTableValueBasedColumnHandler;
import com.softicar.platform.emf.entity.table.view.EmfTableViewButton;
import com.softicar.platform.emf.table.IEmfTable;

class EmfTableForRoleColumnHandler extends EmfDataTableValueBasedColumnHandler<EmfRoleWrapper> {

	@Override
	public void buildCell(IEmfDataTableCell<?, EmfRoleWrapper> cell, EmfRoleWrapper roleWrapper) {

		for (IEmfTable<?, ?, ?> table: CurrentEmfRoleRegistry.get().getAtomicRoleTables(roleWrapper.getRole())) {
			cell.appendChild(new EmfTableViewButton(table));
			cell.appendNewChild(DomElementTag.BR);
		}
	}

	@Override
	public boolean isSortable(IEmfDataTableColumn<?, EmfRoleWrapper> column) {

		return false;
	}
}

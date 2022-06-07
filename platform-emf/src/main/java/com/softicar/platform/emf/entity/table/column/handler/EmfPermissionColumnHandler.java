package com.softicar.platform.emf.entity.table.column.handler;

import com.softicar.platform.dom.styles.CssTextAlign;
import com.softicar.platform.emf.data.table.IEmfDataTableCell;
import com.softicar.platform.emf.data.table.column.handler.EmfDataTableValueBasedColumnHandler;
import com.softicar.platform.emf.permission.EmfPermissionToDisplayVisitor;
import com.softicar.platform.emf.permission.EmfPermissionWrapper;

public class EmfPermissionColumnHandler extends EmfDataTableValueBasedColumnHandler<EmfPermissionWrapper> {

	@Override
	public void buildCell(IEmfDataTableCell<?, EmfPermissionWrapper> cell, EmfPermissionWrapper value) {

		cell.setStyle(CssTextAlign.CENTER);
		cell.appendChild(new EmfPermissionToDisplayVisitor<>(value.getPermission()).toDisplay());
	}
}

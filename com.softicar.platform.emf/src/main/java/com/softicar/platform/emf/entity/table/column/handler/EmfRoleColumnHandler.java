package com.softicar.platform.emf.entity.table.column.handler;

import com.softicar.platform.dom.styles.CssTextAlign;
import com.softicar.platform.emf.authorization.role.EmfRoleToDisplayVisitor;
import com.softicar.platform.emf.authorization.role.EmfRoleWrapper;
import com.softicar.platform.emf.data.table.IEmfDataTableCell;
import com.softicar.platform.emf.data.table.column.handler.EmfDataTableValueBasedColumnHandler;

public class EmfRoleColumnHandler extends EmfDataTableValueBasedColumnHandler<EmfRoleWrapper> {

	@Override
	public void buildCell(IEmfDataTableCell<?, EmfRoleWrapper> cell, EmfRoleWrapper value) {

		cell.setStyle(CssTextAlign.CENTER);
		cell.appendChild(new EmfRoleToDisplayVisitor<>(value.getRole()).toDisplay());
	}
}

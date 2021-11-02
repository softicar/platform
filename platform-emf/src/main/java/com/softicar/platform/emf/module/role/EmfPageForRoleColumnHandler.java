package com.softicar.platform.emf.module.role;

import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.emf.authorization.role.EmfRoleWrapper;
import com.softicar.platform.emf.authorization.role.IEmfRole;
import com.softicar.platform.emf.data.table.IEmfDataTableCell;
import com.softicar.platform.emf.data.table.column.IEmfDataTableColumn;
import com.softicar.platform.emf.data.table.column.handler.EmfDataTableValueBasedColumnHandler;
import com.softicar.platform.emf.page.IEmfPage;
import java.util.Collection;
import java.util.Map;

class EmfPageForRoleColumnHandler extends EmfDataTableValueBasedColumnHandler<EmfRoleWrapper> {

	private final Map<IEmfRole<?>, Collection<IEmfPage<?>>> map;

	public EmfPageForRoleColumnHandler(Map<IEmfRole<?>, Collection<IEmfPage<?>>> pagesUsingRoleMapForModule) {

		this.map = pagesUsingRoleMapForModule;
	}

	@Override
	public void buildCell(IEmfDataTableCell<?, EmfRoleWrapper> cell, EmfRoleWrapper roleWrapper) {

		Collection<IEmfPage<?>> pages = map.get(roleWrapper.getRole());
		if (pages != null) {
			for (IEmfPage<?> page: pages) {
				cell.appendChild(page.getClass().getSimpleName());
				cell.appendNewChild(DomElementTag.BR);
			}
		}
	}

	@Override
	public boolean isSortable(IEmfDataTableColumn<?, EmfRoleWrapper> column) {

		return false;
	}
}

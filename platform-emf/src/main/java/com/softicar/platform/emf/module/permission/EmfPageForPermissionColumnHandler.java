package com.softicar.platform.emf.module.permission;

import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.emf.data.table.IEmfDataTableCell;
import com.softicar.platform.emf.data.table.column.IEmfDataTableColumn;
import com.softicar.platform.emf.data.table.column.handler.EmfDataTableValueBasedColumnHandler;
import com.softicar.platform.emf.page.IEmfPage;
import com.softicar.platform.emf.permission.EmfPermissionWrapper;
import com.softicar.platform.emf.permission.IEmfPermission;
import java.util.Collection;
import java.util.Map;

class EmfPageForPermissionColumnHandler extends EmfDataTableValueBasedColumnHandler<EmfPermissionWrapper> {

	private final Map<IEmfPermission<?>, Collection<IEmfPage<?>>> map;

	public EmfPageForPermissionColumnHandler(Map<IEmfPermission<?>, Collection<IEmfPage<?>>> pagesUsingPermissionMapForModule) {

		this.map = pagesUsingPermissionMapForModule;
	}

	@Override
	public void buildCell(IEmfDataTableCell<?, EmfPermissionWrapper> cell, EmfPermissionWrapper permissionWrapper) {

		Collection<IEmfPage<?>> pages = map.get(permissionWrapper.getPermission());
		if (pages != null) {
			for (IEmfPage<?> page: pages) {
				cell.appendChild(page.getClass().getSimpleName());
				cell.appendNewChild(DomElementTag.BR);
			}
		}
	}

	@Override
	public boolean isSortable(IEmfDataTableColumn<?, EmfPermissionWrapper> column) {

		return false;
	}
}

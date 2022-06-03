package com.softicar.platform.emf.module.permission;

import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.container.data.table.in.memory.AbstractInMemoryDataTable;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.module.IEmfModule;
import com.softicar.platform.emf.permission.CurrentEmfPermissionRegistry;
import com.softicar.platform.emf.permission.EmfPermissionWrapper;
import com.softicar.platform.emf.permission.IEmfPermission;

public class EmfModulePermissionViewTable extends AbstractInMemoryDataTable<IEmfPermission<?>> {

	private final IEmfModule<?> module;
	private final IDataTableColumn<IEmfPermission<?>, IDisplayString> usedPermissionColumn;
	private final IDataTableColumn<IEmfPermission<?>, EmfPermissionWrapper> tablesColumn;
	private final IDataTableColumn<IEmfPermission<?>, EmfPermissionWrapper> pagesColumn;

	public EmfModulePermissionViewTable(IEmfModule<?> module) {

		this.module = module;
		this.usedPermissionColumn = newColumn(IDisplayString.class)//
			.setGetter(permission -> permission.getTitle())
			.setTitle(EmfI18n.PERMISSION)
			.addColumn();
		this.tablesColumn = newColumn(EmfPermissionWrapper.class)//
			.setGetter(permission -> new EmfPermissionWrapper(permission))
			.setTitle(EmfI18n.TABLES)
			.addColumn();
		this.pagesColumn = newColumn(EmfPermissionWrapper.class)//
			.setGetter(permission -> new EmfPermissionWrapper(permission))
			.setTitle(EmfI18n.PAGES)
			.addColumn();
	}

	public IDataTableColumn<IEmfPermission<?>, IDisplayString> getUsedPermissionColumn() {

		return usedPermissionColumn;
	}

	public IDataTableColumn<IEmfPermission<?>, EmfPermissionWrapper> getTablesColumn() {

		return tablesColumn;
	}

	public IDataTableColumn<IEmfPermission<?>, EmfPermissionWrapper> getPagesColumn() {

		return pagesColumn;
	}

	@Override
	protected Iterable<IEmfPermission<?>> getTableRows() {

		return CurrentEmfPermissionRegistry.get().getAtomicPermissions(module);
	}
}

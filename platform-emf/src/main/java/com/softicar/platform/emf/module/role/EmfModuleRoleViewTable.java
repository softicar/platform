package com.softicar.platform.emf.module.role;

import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.container.data.table.in.memory.AbstractInMemoryDataTable;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.authorization.role.CurrentEmfRoleRegistry;
import com.softicar.platform.emf.authorization.role.EmfRoleWrapper;
import com.softicar.platform.emf.authorization.role.IEmfRole;
import com.softicar.platform.emf.module.IEmfModule;

public class EmfModuleRoleViewTable extends AbstractInMemoryDataTable<IEmfRole<?>> {

	private final IEmfModule<?> module;
	private final IDataTableColumn<IEmfRole<?>, IDisplayString> usedRoleColumn;
	private final IDataTableColumn<IEmfRole<?>, EmfRoleWrapper> tablesColumn;
	private final IDataTableColumn<IEmfRole<?>, EmfRoleWrapper> pagesColumn;

	public EmfModuleRoleViewTable(IEmfModule<?> module) {

		this.module = module;
		this.usedRoleColumn = newColumn(IDisplayString.class)//
			.setGetter(role -> role.getTitle())
			.setTitle(EmfI18n.ROLE)
			.addColumn();
		this.tablesColumn = newColumn(EmfRoleWrapper.class)//
			.setGetter(role -> new EmfRoleWrapper(role))
			.setTitle(EmfI18n.TABLES)
			.addColumn();
		this.pagesColumn = newColumn(EmfRoleWrapper.class)//
			.setGetter(role -> new EmfRoleWrapper(role))
			.setTitle(EmfI18n.PAGES)
			.addColumn();
	}

	public IDataTableColumn<IEmfRole<?>, IDisplayString> getUsedRoleColumn() {

		return usedRoleColumn;
	}

	public IDataTableColumn<IEmfRole<?>, EmfRoleWrapper> getTablesColumn() {

		return tablesColumn;
	}

	public IDataTableColumn<IEmfRole<?>, EmfRoleWrapper> getPagesColumn() {

		return pagesColumn;
	}

	@Override
	protected Iterable<IEmfRole<?>> getTableRows() {

		return CurrentEmfRoleRegistry.get().getAtomicRoles(module);
	}
}

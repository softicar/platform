package com.softicar.platform.emf.entity.table.overview;

import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.container.data.table.in.memory.AbstractInMemoryDataTable;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.module.IEmfModule;
import com.softicar.platform.emf.module.registry.IEmfModuleRegistry;
import com.softicar.platform.emf.table.EmfTableWrapper;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.registry.EmfTableRegistry;

public class EmfTableOverviewTable extends AbstractInMemoryDataTable<IEmfTable<?, ?, ?>> {

	private final IEmfModule<?> module;
	private final IDataTableColumn<IEmfTable<?, ?, ?>, IResource> iconColumn;
	private final IDataTableColumn<IEmfTable<?, ?, ?>, String> simpleValueClassNameColumn;
	private final IDataTableColumn<IEmfTable<?, ?, ?>, IDisplayString> titleColumn;
	private final IDataTableColumn<IEmfTable<?, ?, ?>, IDisplayString> pluralTitleColumn;
	private final IDataTableColumn<IEmfTable<?, ?, ?>, String> databaseTableColumn;
	private final IDataTableColumn<IEmfTable<?, ?, ?>, EmfTableWrapper> usedPermissionsColumn;
	private final IDataTableColumn<IEmfTable<?, ?, ?>, EmfTableWrapper> scopeFieldColumn;
	private final IDataTableColumn<IEmfTable<?, ?, ?>, EmfTableWrapper> childTablesColumn;
	private final IDataTableColumn<IEmfTable<?, ?, ?>, Boolean> hasActiveFieldColumn;
	private final IDataTableColumn<IEmfTable<?, ?, ?>, Boolean> hasChangeLoggersColumn;

	public EmfTableOverviewTable(IEmfModule<?> module) {

		this.module = module;
		this.iconColumn = newColumn(IResource.class)//
			.setGetter(IEmfTable::getIcon)
			.setTitle(EmfI18n.ICON)
			.addColumn();
		this.simpleValueClassNameColumn = newColumn(String.class)//
			.setGetter(table -> table.getValueClass().getSimpleName())
			.setTitle(EmfI18n.VALUE_CLASS)
			.addColumn();
		this.titleColumn = newColumn(IDisplayString.class)//
			.setGetter(IEmfTable::getTitle)
			.setTitle(EmfI18n.TITLE)
			.addColumn();
		this.pluralTitleColumn = newColumn(IDisplayString.class)//
			.setGetter(IEmfTable::getPluralTitle)
			.setTitle(EmfI18n.PLURAL_TITLE)
			.addColumn();
		this.databaseTableColumn = newColumn(String.class)//
			.setGetter(table -> table.getFullName().getCanonicalName())
			.setTitle(EmfI18n.DATABASE_TABLE)
			.addColumn();
		this.usedPermissionsColumn = newColumn(EmfTableWrapper.class)//
			.setGetter(EmfTableWrapper::new)
			.setTitle(EmfI18n.USED_PERMISSIONS)
			.addColumn();
		this.scopeFieldColumn = newColumn(EmfTableWrapper.class)//
			.setGetter(EmfTableWrapper::new)
			.setTitle(EmfI18n.SCOPE)
			.addColumn();
		this.childTablesColumn = newColumn(EmfTableWrapper.class)//
			.setGetter(EmfTableWrapper::new)
			.setTitle(EmfI18n.CHILD_TABLES)
			.addColumn();
		this.hasActiveFieldColumn = newColumn(Boolean.class)//
			.setGetter(table -> table.getEmfTableConfiguration().getDeactivationStrategy().isDeactivationSupported())
			.setTitle(EmfI18n.ACTIVE_FIELD_EXISTS)
			.addColumn();
		this.hasChangeLoggersColumn = newColumn(Boolean.class)//
			.setGetter(table -> !table.getChangeLoggers().isEmpty())
			.setTitle(EmfI18n.CHANGE_LOGGERS_EXIST)
			.addColumn();
	}

	public IDataTableColumn<IEmfTable<?, ?, ?>, IResource> getIconColumn() {

		return iconColumn;
	}

	public IDataTableColumn<IEmfTable<?, ?, ?>, String> getSimpleValueClassNameColumn() {

		return simpleValueClassNameColumn;
	}

	public IDataTableColumn<IEmfTable<?, ?, ?>, IDisplayString> getTableTitleColumn() {

		return titleColumn;
	}

	public IDataTableColumn<IEmfTable<?, ?, ?>, IDisplayString> getTablePluralTitleColumn() {

		return pluralTitleColumn;
	}

	public IDataTableColumn<IEmfTable<?, ?, ?>, String> getFullNameColumn() {

		return databaseTableColumn;
	}

	public IDataTableColumn<IEmfTable<?, ?, ?>, EmfTableWrapper> getUsedPermissionsColumn() {

		return usedPermissionsColumn;
	}

	public IDataTableColumn<IEmfTable<?, ?, ?>, EmfTableWrapper> getScopeFieldColumn() {

		return scopeFieldColumn;
	}

	public IDataTableColumn<IEmfTable<?, ?, ?>, EmfTableWrapper> getChildTablesColumn() {

		return childTablesColumn;
	}

	public IDataTableColumn<IEmfTable<?, ?, ?>, Boolean> getHasActiveFieldColumn() {

		return hasActiveFieldColumn;
	}

	public IDataTableColumn<IEmfTable<?, ?, ?>, Boolean> getHasChangeLoggersColumn() {

		return hasChangeLoggersColumn;
	}

	@Override
	protected Iterable<IEmfTable<?, ?, ?>> getTableRows() {

		return EmfTableRegistry//
			.getInstance()
			.getTables(IEmfModuleRegistry.get().getModule(module.getAnnotatedUuid()).get());
	}
}

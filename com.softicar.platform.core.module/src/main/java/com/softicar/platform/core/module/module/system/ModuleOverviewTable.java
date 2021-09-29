package com.softicar.platform.core.module.module.system;

import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.container.data.table.in.memory.AbstractInMemoryDataTable;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.emf.module.IEmfModule;
import com.softicar.platform.emf.module.registry.IEmfModuleRegistry;
import java.util.UUID;

public class ModuleOverviewTable extends AbstractInMemoryDataTable<IEmfModule<?>> {

	private final IDataTableColumn<IEmfModule<?>, String> nameColumn;
	private final IDataTableColumn<IEmfModule<?>, UUID> panicReceiversColumn;

	public ModuleOverviewTable() {

		this.nameColumn = newColumn(String.class)//
			.setGetter(it -> it.toDisplay().toString())
			.setTitle(CoreI18n.NAME)
			.addColumn();
		newColumn(String.class)//
			.setGetter(IEmfModule::getClassName)
			.setTitle(CoreI18n.CLASS)
			.addColumn();
		newColumn(String.class)//
			.setGetter(module -> module.getAnnotatedUuid().toString())
			.setTitle(CoreI18n.UUID)
			.addColumn();
		this.panicReceiversColumn = newColumn(UUID.class)//
			.setGetter(IEmfModule::getAnnotatedUuid)
			.setTitle(CoreI18n.PANIC_RECEIVERS)
			.addColumn();
	}

	@Override
	public Iterable<IEmfModule<?>> getTableRows() {

		return IEmfModuleRegistry.get().getAllModules();
	}

	public IDataTableColumn<IEmfModule<?>, String> getNameColumn() {

		return nameColumn;
	}

	public IDataTableColumn<IEmfModule<?>, UUID> getPanicReceiversColumn() {

		return panicReceiversColumn;
	}
}

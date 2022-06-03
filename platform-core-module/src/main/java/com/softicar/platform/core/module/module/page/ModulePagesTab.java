package com.softicar.platform.core.module.module.page;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.dom.elements.tab.DomTab;
import com.softicar.platform.emf.data.table.EmfDataTableDivBuilder;
import com.softicar.platform.emf.entity.table.column.handler.EmfPermissionColumnHandler;
import com.softicar.platform.emf.module.IEmfModule;

public class ModulePagesTab extends DomTab {

	public ModulePagesTab(IEmfModule<?> module) {

		super(CoreI18n.PAGE_DEFINITIONS);
		ModulePagesTable table = new ModulePagesTable(module);
		appendChild(
			new EmfDataTableDivBuilder<>(table)//
				.setColumnHandler(table.getRequiredPermissionColumn(), new EmfPermissionColumnHandler())
				.build());
	}
}

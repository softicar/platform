package com.softicar.platform.emf.module.permission;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.data.table.EmfDataTableDivBuilder;
import com.softicar.platform.emf.module.IEmfModule;
import com.softicar.platform.emf.page.IEmfPage;
import com.softicar.platform.emf.permission.CurrentEmfPermissionRegistry;
import com.softicar.platform.emf.permission.IEmfPermission;
import java.util.Collection;
import java.util.Map;

public class EmfModulePermissionViewPopup extends DomPopup {

	public EmfModulePermissionViewPopup(IEmfModule<?> module) {

		setCaption(EmfI18n.USED_PERMISSIONS);
		setSubCaption(IDisplayString.create(module.getClassName()));
		EmfModulePermissionViewTable table = new EmfModulePermissionViewTable(module);
		Map<IEmfPermission<?>, Collection<IEmfPage<?>>> permissionToPagesMap = CurrentEmfPermissionRegistry.get().getPermissionToPagesMap(module);
		new EmfDataTableDivBuilder<>(table)//
			.setColumnHandler(table.getTablesColumn(), new EmfTableForPermissionColumnHandler())
			.setColumnHandler(table.getPagesColumn(), new EmfPageForPermissionColumnHandler(permissionToPagesMap))
			.setOrderBy(table.getUsedPermissionColumn(), OrderDirection.ASCENDING)
			.buildAndAppendTo(this);
	}
}

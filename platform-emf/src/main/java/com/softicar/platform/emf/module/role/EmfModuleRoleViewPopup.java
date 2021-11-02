package com.softicar.platform.emf.module.role;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.authorization.role.CurrentEmfRoleRegistry;
import com.softicar.platform.emf.authorization.role.IEmfRole;
import com.softicar.platform.emf.data.table.EmfDataTableDivBuilder;
import com.softicar.platform.emf.module.IEmfModule;
import com.softicar.platform.emf.page.IEmfPage;
import java.util.Collection;
import java.util.Map;

public class EmfModuleRoleViewPopup extends DomPopup {

	public EmfModuleRoleViewPopup(IEmfModule<?> module) {

		setCaption(EmfI18n.USED_ROLES);
		setSubCaption(IDisplayString.create(module.getClassName()));
		EmfModuleRoleViewTable table = new EmfModuleRoleViewTable(module);
		Map<IEmfRole<?>, Collection<IEmfPage<?>>> roleToPagesMap = CurrentEmfRoleRegistry.get().getRoleToPagesMap(module);
		new EmfDataTableDivBuilder<>(table)//
			.setColumnHandler(table.getTablesColumn(), new EmfTableForRoleColumnHandler())
			.setColumnHandler(table.getPagesColumn(), new EmfPageForRoleColumnHandler(roleToPagesMap))
			.setOrderBy(table.getUsedRoleColumn(), OrderDirection.ASCENDING)
			.buildAndAppendTo(this);
	}
}

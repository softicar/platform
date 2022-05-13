package com.softicar.platform.core.module.maintenance;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.CoreRoles;
import com.softicar.platform.core.module.module.instance.system.SystemModuleInstance;
import com.softicar.platform.emf.authorization.role.EmfRoles;
import com.softicar.platform.emf.authorization.role.IEmfRole;
import com.softicar.platform.emf.management.page.AbstractEmfManagementPage;
import com.softicar.platform.emf.page.EmfPagePath;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePointUuid;
import com.softicar.platform.emf.table.IEmfTable;

@EmfSourceCodeReferencePointUuid("3e68157b-990c-4148-a4e8-8c804104adb6")
public class MaintenanceWindowPage extends AbstractEmfManagementPage<SystemModuleInstance> {

	@Override
	public Class<CoreModule> getModuleClass() {

		return CoreModule.class;
	}

	@Override
	protected IEmfTable<?, ?, SystemModuleInstance> getTable() {

		return AGMaintenanceWindow.TABLE;
	}

	@Override
	public IEmfRole<SystemModuleInstance> getAuthorizedRole() {

		return EmfRoles.any(CoreRoles.SUPER_USER, CoreRoles.SYSTEM_ADMINISTRATOR);
	}

	@Override
	public EmfPagePath getPagePath(EmfPagePath modulePath) {

		return modulePath.append(CoreI18n.MAINTENANCE);
	}
}

package com.softicar.platform.core.module.role.permission;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePointUuid;
import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.CorePermissions;
import com.softicar.platform.emf.management.page.AbstractEmfManagementPage;
import com.softicar.platform.emf.module.permission.IEmfModulePermission;
import com.softicar.platform.emf.page.EmfPagePath;
import com.softicar.platform.emf.table.IEmfTable;

@SourceCodeReferencePointUuid("83908377-98ca-4976-9c21-b1330ed16c8b")
public class RolePermissionManagementPage extends AbstractEmfManagementPage<AGCoreModuleInstance> {

	@Override
	public Class<CoreModule> getModuleClass() {

		return CoreModule.class;
	}

	@Override
	public EmfPagePath getPagePath(EmfPagePath modulePath) {

		return modulePath.append(CoreI18n.USERS).append(CoreI18n.ROLES);
	}

	@Override
	protected IEmfTable<?, ?, AGCoreModuleInstance> getTable() {

		return AGRolePermission.TABLE;
	}

	@Override
	public IEmfModulePermission<AGCoreModuleInstance> getRequiredPermission() {

		return CorePermissions.ADMINISTRATION;
	}
}

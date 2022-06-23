package com.softicar.platform.core.module.role.user;

import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.CorePermissions;
import com.softicar.platform.emf.management.page.AbstractEmfManagementPage;
import com.softicar.platform.emf.module.permission.IEmfModulePermission;
import com.softicar.platform.emf.page.EmfPagePath;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePointUuid;
import com.softicar.platform.emf.table.IEmfTable;

@EmfSourceCodeReferencePointUuid("b8318ac9-e1f0-48f2-b03a-add2771364f4")
public class RoleUserManagementPage extends AbstractEmfManagementPage<AGCoreModuleInstance> {

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

		return AGRoleUser.TABLE;
	}

	@Override
	public IEmfModulePermission<AGCoreModuleInstance> getRequiredPermission() {

		return CorePermissions.ACCESS_MANAGEMENT;
	}
}

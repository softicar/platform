package com.softicar.platform.core.module.access.permission.assignment.module.instance;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.CorePermissions;
import com.softicar.platform.core.module.module.instance.system.SystemModuleInstance;
import com.softicar.platform.emf.management.page.AbstractEmfManagementPage;
import com.softicar.platform.emf.module.permission.IEmfModulePermission;
import com.softicar.platform.emf.page.EmfPagePath;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePointUuid;
import com.softicar.platform.emf.table.IEmfTable;

@EmfSourceCodeReferencePointUuid("75e4e284-2256-4fcf-9b94-e2a4bfccb4c4")
public class ModuleInstancePermissionAssignmentPage extends AbstractEmfManagementPage<SystemModuleInstance> {

	@Override
	public Class<CoreModule> getModuleClass() {

		return CoreModule.class;
	}

	@Override
	protected IEmfTable<?, ?, SystemModuleInstance> getTable() {

		return AGModuleInstancePermissionAssignment.TABLE;
	}

	@Override
	public EmfPagePath getPagePath(EmfPagePath modulePath) {

		return modulePath.append(CoreI18n.PERMISSIONS);
	}

	@Override
	public IEmfModulePermission<SystemModuleInstance> getRequiredPermission() {

		return CorePermissions.ACCESS_MANAGEMENT;
	}
}

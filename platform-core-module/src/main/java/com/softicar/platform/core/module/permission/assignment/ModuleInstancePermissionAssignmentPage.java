package com.softicar.platform.core.module.permission.assignment;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePointUuid;
import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.CorePermissions;
import com.softicar.platform.emf.management.page.AbstractEmfManagementPage;
import com.softicar.platform.emf.module.permission.IEmfModulePermission;
import com.softicar.platform.emf.page.EmfPagePath;
import com.softicar.platform.emf.table.IEmfTable;

@SourceCodeReferencePointUuid("75e4e284-2256-4fcf-9b94-e2a4bfccb4c4")
public class ModuleInstancePermissionAssignmentPage extends AbstractEmfManagementPage<AGCoreModuleInstance> {

	@Override
	public Class<CoreModule> getModuleClass() {

		return CoreModule.class;
	}

	@Override
	protected IEmfTable<?, ?, AGCoreModuleInstance> getTable() {

		return AGModuleInstancePermissionAssignment.TABLE;
	}

	@Override
	public EmfPagePath getPagePath(EmfPagePath modulePath) {

		return modulePath.append(CoreI18n.PERMISSIONS);
	}

	@Override
	public IEmfModulePermission<AGCoreModuleInstance> getRequiredPermission() {

		return CorePermissions.ADMINISTRATION;
	}
}

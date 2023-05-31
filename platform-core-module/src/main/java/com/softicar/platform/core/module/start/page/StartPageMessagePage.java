package com.softicar.platform.core.module.start.page;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePointUuid;
import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.CorePermissions;
import com.softicar.platform.emf.management.page.AbstractEmfManagementPage;
import com.softicar.platform.emf.module.permission.IEmfModulePermission;
import com.softicar.platform.emf.table.IEmfTable;

@SourceCodeReferencePointUuid("645a7eaa-8288-4bd6-8187-ff66e6f76d57")
public class StartPageMessagePage extends AbstractEmfManagementPage<AGCoreModuleInstance> {

	@Override
	public Class<CoreModule> getModuleClass() {

		return CoreModule.class;
	}

//	@Override
//	public EmfPagePath getPagePath(EmfPagePath modulePath) {
//
//		return modulePath.append(CoreI18n.SERVER);
//	}

	@Override
	protected IEmfTable<?, ?, AGCoreModuleInstance> getTable() {

		return AGStartPageMessage.TABLE;
	}

	@Override
	public IEmfModulePermission<AGCoreModuleInstance> getRequiredPermission() {

		return CorePermissions.ADMINISTRATION;
	}
}

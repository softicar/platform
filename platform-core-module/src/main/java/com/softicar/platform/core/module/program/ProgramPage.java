package com.softicar.platform.core.module.program;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.CorePermissions;
import com.softicar.platform.core.module.module.instance.system.SystemModuleInstance;
import com.softicar.platform.emf.management.page.AbstractEmfManagementPage;
import com.softicar.platform.emf.module.permission.IEmfModulePermission;
import com.softicar.platform.emf.page.EmfPagePath;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePointUuid;
import com.softicar.platform.emf.table.IEmfTable;

@EmfSourceCodeReferencePointUuid("b08c7fca-1fd5-4ac3-b64d-672223a0d304")
public class ProgramPage extends AbstractEmfManagementPage<SystemModuleInstance> {

	@Override
	public Class<CoreModule> getModuleClass() {

		return CoreModule.class;
	}

	@Override
	protected IEmfTable<?, ?, SystemModuleInstance> getTable() {

		return AGProgram.TABLE;
	}

	@Override
	public IEmfModulePermission<SystemModuleInstance> getRequiredPermission() {

		return CorePermissions.SUPER_USER;
	}

	@Override
	public EmfPagePath getPagePath(EmfPagePath modulePath) {

		return modulePath.append(CoreI18n.PROGRAMS);
	}
}

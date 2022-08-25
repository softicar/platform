package com.softicar.platform.core.module.program.execution;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePointUuid;
import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.CorePermissions;
import com.softicar.platform.emf.management.page.AbstractEmfManagementPage;
import com.softicar.platform.emf.module.permission.IEmfModulePermission;
import com.softicar.platform.emf.page.EmfPagePath;
import com.softicar.platform.emf.table.IEmfTable;

@SourceCodeReferencePointUuid("5ff82872-cd52-46ec-a76a-f35ca142322a")
public class ProgramExecutionPage extends AbstractEmfManagementPage<AGCoreModuleInstance> {

	@Override
	public Class<CoreModule> getModuleClass() {

		return CoreModule.class;
	}

	@Override
	protected IEmfTable<?, ?, AGCoreModuleInstance> getTable() {

		return AGProgramExecution.TABLE;
	}

	@Override
	public IEmfModulePermission<AGCoreModuleInstance> getRequiredPermission() {

		return CorePermissions.OPERATION;
	}

	@Override
	public EmfPagePath getPagePath(EmfPagePath modulePath) {

		return modulePath.append(CoreI18n.PROGRAMS);
	}
}

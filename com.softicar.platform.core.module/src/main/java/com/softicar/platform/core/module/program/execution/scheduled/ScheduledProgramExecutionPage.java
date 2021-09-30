package com.softicar.platform.core.module.program.execution.scheduled;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.CoreRoles;
import com.softicar.platform.core.module.module.instance.system.SystemModuleInstance;
import com.softicar.platform.emf.management.page.AbstractEmfManagementPage;
import com.softicar.platform.emf.module.role.IEmfModuleRole;
import com.softicar.platform.emf.page.EmfPagePath;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePointUuid;
import com.softicar.platform.emf.table.IEmfTable;

@EmfSourceCodeReferencePointUuid("9321845e-7f37-44a0-9a62-12ded9838f27")
public class ScheduledProgramExecutionPage extends AbstractEmfManagementPage<SystemModuleInstance> {

	@Override
	public Class<CoreModule> getModuleClass() {

		return CoreModule.class;
	}

	@Override
	public IEmfModuleRole<SystemModuleInstance> getAuthorizedRole() {

		return CoreRoles.SUPER_USER;
	}

	@Override
	public EmfPagePath getPagePath(EmfPagePath modulePath) {

		return modulePath.append(CoreI18n.PROGRAMS);
	}

	@Override
	protected IEmfTable<?, ?, SystemModuleInstance> getTable() {

		return AGScheduledProgramExecution.TABLE;
	}
}

package com.softicar.platform.core.module.access.module.instance;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.CoreRoles;
import com.softicar.platform.core.module.module.instance.system.SystemModuleInstance;
import com.softicar.platform.emf.management.page.AbstractEmfManagementPage;
import com.softicar.platform.emf.module.role.IEmfModuleRole;
import com.softicar.platform.emf.page.EmfPagePath;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePointUuid;
import com.softicar.platform.emf.table.IEmfTable;

@EmfSourceCodeReferencePointUuid("b443a8f9-d72f-483b-aa90-3482d192e29d")
public class ModuleInstancePage extends AbstractEmfManagementPage<SystemModuleInstance> {

	@Override
	public Class<CoreModule> getModuleClass() {

		return CoreModule.class;
	}

	@Override
	public EmfPagePath getPagePath(EmfPagePath modulePath) {

		return modulePath.append(CoreI18n.MODULES);
	}

	@Override
	protected IEmfTable<?, ?, SystemModuleInstance> getTable() {

		return AGModuleInstance.TABLE;
	}

	// FIXME: remove once the scope is not SystemModuleInstance
	@Override
	public IEmfModuleRole<SystemModuleInstance> getAuthorizedRole() {

		return CoreRoles.SUPER_USER;
	}
}

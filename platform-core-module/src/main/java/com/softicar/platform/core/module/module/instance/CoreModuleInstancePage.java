package com.softicar.platform.core.module.module.instance;

import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.CorePermissions;
import com.softicar.platform.core.module.module.instance.system.SystemModuleInstance;
import com.softicar.platform.emf.management.page.AbstractEmfManagementPage;
import com.softicar.platform.emf.module.permission.IEmfModulePermission;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePointUuid;
import com.softicar.platform.emf.table.IEmfTable;

@EmfSourceCodeReferencePointUuid("40f4a077-076b-472a-ab4e-98cfd13cb803")
public class CoreModuleInstancePage extends AbstractEmfManagementPage<SystemModuleInstance> {

	@Override
	public Class<CoreModule> getModuleClass() {

		return CoreModule.class;
	}

	@Override
	protected IEmfTable<?, ?, SystemModuleInstance> getTable() {

		return AGCoreModuleInstance.TABLE;
	}

	// FIXME: remove once the scope is not SystemModuleInstance
	@Override
	public IEmfModulePermission<SystemModuleInstance> getRequiredPermission() {

		return CorePermissions.SUPER_USER;
	}
}

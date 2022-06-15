package com.softicar.platform.core.module.permission;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.emf.module.permission.IEmfModulePermission;
import com.softicar.platform.emf.permission.IEmfPermission;

class ConvertedCoreModulePermission<R> implements IEmfPermission<R> {

	private final IEmfModulePermission<AGCoreModuleInstance> modulePermission;

	public ConvertedCoreModulePermission(IEmfModulePermission<AGCoreModuleInstance> modulePermission) {

		this.modulePermission = modulePermission;
	}

	@Override
	public IDisplayString getTitle() {

		return modulePermission.getTitle();
	}

	@Override
	public boolean test(R tableRow, IBasicUser user) {

		return AGUser.get(user).hasModulePermission(modulePermission, AGCoreModuleInstance.getInstance().pk());
	}
}

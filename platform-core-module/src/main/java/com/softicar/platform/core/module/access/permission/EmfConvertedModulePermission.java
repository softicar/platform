package com.softicar.platform.core.module.access.permission;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.emf.permission.IEmfPermission;

class EmfConvertedModulePermission<R> implements IEmfPermission<R> {

	private final EmfSystemModulePermission systemModulePermission;

	public EmfConvertedModulePermission(EmfSystemModulePermission systemModulePermission) {

		this.systemModulePermission = systemModulePermission;
	}

	@Override
	public IDisplayString getTitle() {

		return systemModulePermission.getTitle();
	}

	@Override
	public boolean test(R tableRow, IBasicUser user) {

		return AGUser.get(user).hasModulePermission(systemModulePermission);
	}
}

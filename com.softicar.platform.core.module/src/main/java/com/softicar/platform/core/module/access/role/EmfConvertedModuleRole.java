package com.softicar.platform.core.module.access.role;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.emf.authorization.role.IEmfRole;

class EmfConvertedModuleRole<R> implements IEmfRole<R> {

	private final EmfSystemModuleRole systemModuleRole;

	public EmfConvertedModuleRole(EmfSystemModuleRole systemModuleRole) {

		this.systemModuleRole = systemModuleRole;
	}

	@Override
	public IDisplayString getTitle() {

		return systemModuleRole.getTitle();
	}

	@Override
	public boolean test(R tableRow, IBasicUser user) {

		return AGUser.get(user).hasModuleRole(systemModuleRole);
	}
}
